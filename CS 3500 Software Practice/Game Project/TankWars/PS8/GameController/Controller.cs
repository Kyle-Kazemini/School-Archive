using Model;
using NetworkUtil;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Text.RegularExpressions;

namespace TankWars
{
    /// <summary>
    /// The Controller for the TankWars game. Sends and receives information from the View
    /// and Model based on the MVC framework.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini & Robert Davidson
    /// November 9, 2020
    /// </remarks>
    public class Controller
    {
        private string _playerName;
        public int playerID;

        // World is a simple container for Players and Powerups
        private readonly World theWorld;
        public int worldSize;

        // TankController Items
        private string moving;
        private string fire;
        private Vector2D tdir;

        // Delegate and event for informing the View of a connection error
        public delegate void ConnectionErrorHandler(string error);
        public event ConnectionErrorHandler ConnectionError;

        // Delegate and event for informing the View that the server has sent updates
        // and the world needs to be redrawn
        public delegate void ServerUpdateHandler();
        public event ServerUpdateHandler UpdateArrived;


        /// <summary>
        /// Default constructor for Controller.
        /// </summary>
        public Controller()
        {
            theWorld = new World(worldSize);
            moving = "none";
            fire = "none";
            tdir = new Vector2D(0, -1);
        }


        /// <summary>
        /// Returns the World associated with this controller.
        /// </summary>
        /// <returns>
        /// World object.
        /// </returns>
        public World GetWorld()
        {
            return theWorld;
        }

        /// <summary>
        /// Gets the moving string.
        /// </summary>
        /// <returns></returns>
        public String GetMoving()
        {
            return moving;
        }


        /// <summary>
        /// Notifies the server about the player's current inputs.
        /// </summary>
        private void ProcessInputs(SocketState ss)
        {
            TankController tankController = new TankController(moving, fire, new Vector2D(tdir));
            Networking.Send(ss.TheSocket, JsonConvert.SerializeObject(tankController) + '\n');
        }


        /// <summary>
        /// Starts the connection to the server.
        /// </summary>
        /// <param name="name"></param>
        /// <param name="hostName"></param>
        public void ConnectionControl(string name, string hostName)
        {
            _playerName = name;
            Networking.ConnectToServer(OnBeginConnection, hostName, 11000);
        }


        /// <summary>
        /// Callback for BeginConnection that sends the player name to the server.
        /// </summary>
        /// <param name="state"></param>
        private void OnBeginConnection(SocketState state)
        {
            if (state.ErrorOccured)
            {
                ConnectionError("Server could not connect to provided hostname / ip.");
                return;
            }
            Networking.Send(state.TheSocket, _playerName + '\n');

            state.OnNetworkAction = EndBeginConnection;

            lock (state)
            {
                Networking.GetData(state);
            }
        }

        /// <summary>
        /// SocketState OnNetworkAction that receives the player ID and world size from the server.
        /// </summary>
        /// <param name="state"></param>
        private void EndBeginConnection(SocketState state)
        {
            if (state.ErrorOccured)
            {
                ConnectionError("Error sending player data to server.");
                return;
            }

            string[] receivedData = state.GetData().Split('\n');

            if (receivedData.Length > 1 && !String.IsNullOrEmpty(receivedData[0]) && !String.IsNullOrEmpty(receivedData[1]))
            {
                // Remove data (+2 for both '\n')
                state.RemoveData(0, receivedData[0].Length + receivedData[1].Length + 2);
                Int32.TryParse(receivedData[0], out playerID);
                Int32.TryParse(receivedData[1], out worldSize);

                theWorld.clientID = playerID;
                theWorld.Size = worldSize;
            }

            state.OnNetworkAction = UpdateCameFromServer;

            lock (state)
            {
                Networking.GetData(state);
            }
        }

        /// <summary>
        /// Begins the event loop and receives objects as JSON from the server.
        /// </summary>
        /// <param name="state"></param>
        public void UpdateCameFromServer(SocketState state)
        {
            if (state.ErrorOccured)
            {
                ConnectionError("Error receiving game data from server.");
                return;
            }

            string data = state.GetData();
            bool readLoop = true;

            lock (theWorld)
            {
                while (readLoop)
                {
                    if (data.Length == 4096)
                    {
                        ReadData(state, data);
                        data = state.GetData();
                    }
                    else
                    {
                        ReadData(state, data);
                        readLoop = false;
                    }
                }
            }

            // For whatever user inputs happened during the last frame,
            // process them.
            ProcessInputs(state);

            // Notify any listeners (the view) that a new game world has arrived from the server
            UpdateArrived?.Invoke(); // shortcut to check if UpdateArrived is null.

            lock (state)
            {
                Networking.GetData(state);
            }
        }


        /// <summary>
        /// Reads Data from Server and Updates World
        /// </summary>
        /// <param name="state"></param>
        /// <param name="data"></param>
        private void ReadData(SocketState state, String data)
        {
            string[] objects = Regex.Split(data, @"[\n]");

            foreach (string item in objects)
            {
                bool remove = false;
                if (!String.IsNullOrEmpty(item))
                {
                    try
                    {
                        if (item[0] == '{' && item[item.Length - 1] == '}')
                        {
                            JObject obj = JObject.Parse(item);

                            JToken token = obj["wall"];
                            if (token != null)
                            {
                                Wall w = JsonConvert.DeserializeObject<Wall>(item);
                                theWorld.UpdateWall(w);
                                remove = true;
                            }

                            token = obj["tank"];
                            if (token != null)
                            {
                                Tank t = JsonConvert.DeserializeObject<Tank>(item);
                                theWorld.UpdateTank(t);
                                remove = true;
                            }

                            token = obj["power"];
                            if (token != null)
                            {
                                Powerup p = JsonConvert.DeserializeObject<Powerup>(item);
                                theWorld.UpdatePowerup(p);
                                remove = true;
                            }

                            token = obj["beam"];
                            if (token != null)
                            {
                                theWorld.UpdateBeam(JsonConvert.DeserializeObject<Beam>(item));
                                remove = true;
                            }

                            token = obj["proj"];
                            if (token != null)
                            {
                                theWorld.UpdateProjectile(JsonConvert.DeserializeObject<Projectile>(item));
                                remove = true;
                            }
                        }
                    }
                    catch (Exception)
                    {
                    }
                    if (remove)
                        state.RemoveData(0, item.Length + 1); // Remove data (+1 for '\n')
                }
            }
        }


        /// <summary>
        /// Handles a movement request from the View.
        /// </summary>
        public void HandleMoveRequest(string moveType)
        {
            moving = moveType;
        }


        /// <summary>
        /// Handles a mouse request from the View.
        /// </summary>
        public void HandleMouseRequest(string fireType)
        {
            fire = fireType;
        }


        /// <summary>
        /// Handles cursor tracking for the turret direction.
        /// </summary>
        public void CursorTracker(int X, int Y)
        {
            if (theWorld.Tanks.ContainsKey(playerID))
            {
                X -= GameConstants.ViewSize / 2;
                Y -= GameConstants.ViewSize / 2;
            }

            tdir = new Vector2D(X, Y);
            tdir.Normalize();
        }
    }
}
