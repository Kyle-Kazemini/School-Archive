using Model;
using NetworkUtil;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;
using System.Xml;

namespace TankWars
{
    /// <summary>
    /// Server controller for TankWars.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini and Robert Davidson
    /// November 26, 2020
    /// </remarks>
    public class ServerController
    {
        // World is a simple container for Players and Powerups
        private World theUniverse;

        // ID Managers
        private int projIDAssigner;
        private int beamIDAssigner;
        private int powerupIDAssigner;

        private Stack<Tank> newClientsAdded;


        /// <summary>
        /// ServerController default constructor.
        /// </summary>
        public ServerController()
        {
            projIDAssigner = 0;
            beamIDAssigner = 0;
            powerupIDAssigner = 0;
            newClientsAdded = new Stack<Tank>();
        }

        /// <summary>
        /// Reads from the settings XML file and then starts the server using
        /// HandleNewClient as the method to call upon connection.
        /// </summary>
        public void StartConnection()
        {
            ReadSettingsXml(Path.Combine(
                Directory.GetParent(Directory.GetCurrentDirectory()).Parent.Parent.Parent.FullName,
                @"Resources\settings.xml"));

            Networking.StartServer(HandleNewClient, 11000);
        }

        /// <summary>
        /// Checks for SocketState errors and then calls the Networking GetData method
        /// in order to invoke HandlePlayerName.
        /// </summary>
        /// <param name="state"></param>
        private void HandleNewClient(SocketState state)
        {
            if (state.ErrorOccured)
            {
                Console.WriteLine("ERROR IN HandleNewClient()");
                return;
            }

            Console.WriteLine("New connection from player {" + state.ID + "}.");

            state.OnNetworkAction = HandlePlayerName;

            lock (state)
            {
                Networking.GetData(state);
            }
        }


        /// <summary>
        /// Gets the player name from the client and creates a tank for the player.
        /// Sends the client their ID and the World size.
        /// Sends the client the Walls.
        /// </summary>
        /// <param name="state"></param>
        private void HandlePlayerName(SocketState state)
        {
            if (state.ErrorOccured)
            {
                Console.WriteLine("ERROR IN HandlePlayerName()");
                return;
            }

            // Get Each Player Trying to Connect
            String[] playerData = state.GetData().Split('\n');

            int playerID = (int)state.ID;
            string playerName = "";

            if (playerData.Length > 0 && !String.IsNullOrEmpty(playerData[0]))
            {
                playerName = playerData[0];
                state.RemoveData(0, playerData.Length + 1); // +1 for '\n'
            }


            // Add a new tank for this player
            Tank tank = new Tank(playerName, playerID);
            tank.SetLocation(theUniverse.SpawnObject(GameConstants.TankSize));

            lock (theUniverse)
            {
                // Remember this tank and add it in the next frame
                newClientsAdded.Push(tank);
            }

            // Send startup info to the client
            Networking.Send(state.TheSocket, playerID.ToString() + '\n');
            Networking.Send(state.TheSocket, theUniverse.Size.ToString() + '\n');

            state.OnNetworkAction = HandleDataFromClient;

            lock (theUniverse)
            {
                // Send the Walls to the client
                foreach (Wall wall in theUniverse.Walls.Values)
                {
                    Networking.Send(state.TheSocket, JsonConvert.SerializeObject(wall) + '\n');
                }

                // Add the client to the Dictionary of clients
                theUniverse.Clients.Add(state, new TankController());
            }

            lock (state)
            {
                Networking.GetData(state);
            }
        }


        /// <summary>
        /// Handles movement data from the client.
        /// Calls the Networking GetData method to continue this event loop.
        /// </summary>
        /// <param name="state"></param>
        private void HandleDataFromClient(SocketState state)
        {
            if (state.ErrorOccured)
            {
                Console.WriteLine("Lost connection from player {" + state.ID + "}.");
                return;
            }

            string data = state.GetData();
            bool readLoop = true;

            lock (theUniverse)
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

            lock (state)
            {
                Networking.GetData(state);
            }
        }

        /// <summary>
        /// On update, this method processes the data sent by the clients and updates the World.
        /// </summary>
        private void ProcessClientTankControllers(SocketState state)
        {
            //TODO: Comment this method, what goes on
            int ID = (int)state.ID;
            Tank tank = theUniverse.Tanks[ID];
            TankController mechanics = theUniverse.Clients[state];


            // If Tank has 0 HP, do nothing.
            if (tank.GetHP() <= 0) return;


            ReadClientMovement(ID, mechanics);


            tank.SetTurretOrienation(new Vector2D(mechanics.GetTurretOrientation()));


            if (mechanics.GetFireType().Equals("alt"))
            {
                if (tank.CanFireBeam())
                {
                    int beamID = beamIDAssigner++;
                    theUniverse.Beams.Add(beamID, new Beam(beamID, new Vector2D(tank.GetLocation()),
                        new Vector2D(tank.GetTurretOrientation()),
                        tank.GetID()));
                }
            }

            else if (mechanics.GetFireType().Equals("main") && tank.ReadyToFire()) // TODO: MOVE THIS TO THE FRAME LOOP (ReadyToFire())
            {
                theUniverse.UpdateProjectile(new Projectile(projIDAssigner++,
                    new Vector2D(tank.GetLocation()),
                    new Vector2D(tank.GetTurretOrientation()),
                    ID));
            }
        }


        public void PowerupManager()
        {
            while (theUniverse.Powerups.Count < GameConstants.MaxAvailablePowerups)
            {
                int newPowerupID = powerupIDAssigner++;
                theUniverse.Powerups.Add(newPowerupID,
                    new Powerup(newPowerupID, theUniverse.SpawnObject(GameConstants.PowerupSize)));
            }
        }

        /// <summary>
        /// Updates the World. 
        /// This method is invoked every iteration of the frame loop.
        /// </summary>
        public void Update()
        {
            lock (theUniverse)
            {
                // Add the new Tanks on this frame by popping them from the new clients stack
                while (newClientsAdded.Count > 0)
                {
                    Tank tank = newClientsAdded.Pop();
                    theUniverse.Tanks.Add(tank.GetID(), tank);
                }

                PowerupManager();

                List<Projectile> projToRemove = new List<Projectile>();
                List<Powerup> powerupToRemove = new List<Powerup>();

                foreach (SocketState state in theUniverse.Clients.Keys)
                {
                    ProcessClientTankControllers(state);

                    // ----------------------- Tanks ----------------------- //
                    foreach (Tank tank in theUniverse.Tanks.Values)
                    {
                        Networking.Send(state.TheSocket, JsonConvert.SerializeObject(tank) + '\n');
                    }

                    // ----------------------- Powerups ----------------------- //
                    foreach (Powerup powerup in theUniverse.Powerups.Values)
                    {
                        if (powerup.GetStatus())
                        {
                            powerupToRemove.Add(powerup);
                        }

                        if (theUniverse.RadiusIntersectionCheck(theUniverse.Tanks[(int)state.ID].GetLocation(),
                            GameConstants.TankSize / 2.0, powerup.GetLocation(), GameConstants.PowerupSize / 2.0))
                        {
                            powerup.PickedUp();
                            theUniverse.Tanks[(int)state.ID].CollectedPowerup();
                        }

                        Networking.Send(state.TheSocket, JsonConvert.SerializeObject(powerup) + '\n');
                    }

                    // ----------------------- Projectiles ----------------------- //
                    foreach (Projectile proj in theUniverse.Projectiles.Values)
                    {
                        if (theUniverse.MoveProjectiles(proj))
                            projToRemove.Add(proj);

                        Networking.Send(state.TheSocket, JsonConvert.SerializeObject(proj) + '\n');
                    }

                    // ----------------------- Beams ----------------------- //
                    foreach (Beam beam in theUniverse.Beams.Values)
                    {
                        Networking.Send(state.TheSocket, JsonConvert.SerializeObject(beam) + '\n');
                    }
                }

                // Remove Objects from World
                foreach (Projectile proj in projToRemove)
                {
                    theUniverse.Projectiles.Remove(proj.GetID());
                }

                foreach (Powerup powerup in powerupToRemove)
                {
                    theUniverse.Powerups.Remove(powerup.GetID());
                }
            }
        }

        /// <summary>
        /// Processes tank movement sent from client.
        ///   1. Determine next location
        ///   2. Check for Collision
        ///   3. Update projectiles if fired and change location if no collision.
        /// </summary>
        /// <param name="stateId"></param>
        /// <param name="tankMove"></param>
        private void ReadClientMovement(int stateId, TankController tankCommands)
        {
            string movement = tankCommands.GetMovement();
            Tank tank = theUniverse.Tanks[stateId];
            Vector2D location = theUniverse.Tanks[stateId].GetLocation();

            if (movement.Equals("right"))
            {
                tank.SetVelocity(new Vector2D(1, 0) * GameConstants.TankSpeed);
                tank.SetOrientation(new Vector2D(1, 0));
            }
            else if (movement.Equals("left"))
            {
                tank.SetVelocity(new Vector2D(-1, 0) * GameConstants.TankSpeed);
                tank.SetOrientation(new Vector2D(1, 0));
            }
            else if (movement.Equals("up"))
            {
                tank.SetVelocity(new Vector2D(0, -1) * GameConstants.TankSpeed);
                tank.SetOrientation(new Vector2D(0, -1));
            }
            else if (movement.Equals("down"))
            {
                tank.SetVelocity(new Vector2D(0, 1) * GameConstants.TankSpeed);
                tank.SetOrientation(new Vector2D(0, 1));
            }
            else if (movement.Equals("none"))
            {
                tank.SetVelocity(new Vector2D(0, 0));
            }

            tank.SetTurretOrienation(tankCommands.GetTurretOrientation());

            if (!theUniverse.WallTankCollisionCheck(TankMovement(location, tank.GetVelocity())))
            {
                Vector2D newLocation = TankMovement(location, tank.GetVelocity());
                tank.SetLocation(newLocation);
            }

            // Loops tank location so that if it exits one side of the world, it re-appears on the other side.
            Vector2D TankMovement(Vector2D loc, Vector2D vel)
            {
                double x;
                double y;
                if (loc.GetX() + vel.GetX() > (theUniverse.Size / 2))
                {
                    x = (loc.GetX() + vel.GetX()) - theUniverse.Size;
                }
                else if (loc.GetX() + vel.GetX() < -(theUniverse.Size / 2))
                {
                    x = (loc.GetX() + vel.GetX()) + theUniverse.Size;
                }
                else
                {
                    x = loc.GetX() + vel.GetX();
                }

                if (loc.GetY() + vel.GetY() > (theUniverse.Size / 2))
                {
                    y = (loc.GetY() + vel.GetY()) - theUniverse.Size;
                }
                else if (loc.GetY() + vel.GetY() < -(theUniverse.Size / 2))
                {
                    y = (loc.GetY() + vel.GetY()) + theUniverse.Size;
                }
                else
                {
                    y = loc.GetY() + vel.GetY();
                }

                return new Vector2D(x, y);
            }
        }


        /// <summary>
        /// Reads data from the client
        /// </summary>
        /// <param name="state"></param>
        /// <param name="data"></param>
        private void ReadData(SocketState state, String data)
        {
            string[] objects = Regex.Split(data, @"[\n]");

            foreach (string item in objects)
            {
                if (!String.IsNullOrEmpty(item))
                {
                    try
                    {
                        if (item[0].Equals('{') && item[item.Length - 1].Equals('}'))
                        {
                            theUniverse.Clients[state] = JsonConvert.DeserializeObject<TankController>(item);

                            state.RemoveData(0, item.Length + 1);
                        }
                    }
                    catch (Exception)
                    {
                    }
                }
            }
        }


        /// <summary>
        /// Reads Settings.xml file. This operation should only be done
        /// one time.
        /// </summary>
        /// <param name="fileName"></param>
        private void ReadSettingsXml(string fileName)
        {
            // Save Game Data
            bool gameSettings = false;

            // Save Wall Data
            int WallID = 0;
            Vector2D p1 = new Vector2D();
            Vector2D p2 = new Vector2D();
            int x1 = 0;
            int y1 = 0;
            int x2 = 0;
            int y2 = 0;

            try
            {
                using (XmlReader reader = XmlReader.Create(fileName))
                {
                    while (reader.Read())
                    {
                        if (reader.IsStartElement())
                        {
                            if (reader.Name.Equals("GameSettings"))
                            {
                                gameSettings = true;
                            }
                            else if (reader.Name.Equals("UniverseSize") && gameSettings)
                            {
                                theUniverse = new World(Int32.Parse(reader.ReadString()));
                            }
                            else if (reader.Name.Equals("MSPerFrame") && gameSettings)
                            {
                                Int32.TryParse(reader.ReadString(), out GameConstants.MSPerFrame);
                            }
                            else if (reader.Name.Equals("FramesPerShot") && gameSettings)
                            {
                                Int32.TryParse(reader.ReadString(), out GameConstants.ProjectileFireRate);
                            }
                            else if (reader.Name.Equals("RespawnRate") && gameSettings)
                            {
                                Int32.TryParse(reader.ReadString(), out GameConstants.RespawnRate);
                            }
                            else if (reader.Name.Equals("Wall") && gameSettings)
                            {
                                ReadWall(reader);
                                theUniverse.BuildWall(new Wall(new Vector2D(x1, y1), new Vector2D(x2, y2), WallID));
                                WallID++;
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

            // Helper method for reading a Wall
            // in the ReadSettings method.
            void ReadWall(XmlReader wallRead)
            {
                bool inp1 = false;
                bool inp2 = false;
                bool p1x = false;
                bool p2x = false;
                bool p1y = false;
                bool p2y = false;

                while (wallRead.Read())
                {
                    if (wallRead.IsStartElement())
                    {
                        if (wallRead.Name.Equals("p1"))
                        {
                            inp1 = true;
                            inp2 = false;
                        }
                        else if (wallRead.Name.Equals("p2"))
                        {
                            inp2 = true;
                            inp1 = false;
                        }
                        else if (wallRead.Name.Equals("x") && inp1)
                        {
                            Int32.TryParse(wallRead.ReadString(), out x1);
                            p1x = true;
                        }
                        else if (wallRead.Name.Equals("y") && inp1)
                        {
                            Int32.TryParse(wallRead.ReadString(), out y1);
                            p1y = true;
                        }
                        else if (wallRead.Name.Equals("x") && inp2)
                        {
                            Int32.TryParse(wallRead.ReadString(), out x2);
                            p2x = true;
                        }
                        else if (wallRead.Name.Equals("y") && inp2)
                        {
                            Int32.TryParse(wallRead.ReadString(), out y2);
                            p2y = true;
                        }

                        if (p1x && p2x && p1y && p2y)
                        {
                            return;
                        }
                    }
                }
            }
        }
    }
}