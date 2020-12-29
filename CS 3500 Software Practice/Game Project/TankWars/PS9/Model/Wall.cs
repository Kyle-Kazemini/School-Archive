using Newtonsoft.Json;
using TankWars;

namespace TankWars
{
    /// <summary>
    /// Represents a Wall in TankWars.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini & Robert Davidson
    /// November 9, 2020
    /// </remarks>
    [JsonObject(MemberSerialization.OptIn)]
    public class Wall
    {
        [JsonProperty(PropertyName = "wall")]
        private int ID;

        [JsonProperty(PropertyName = "p1")]
        private Vector2D firstEndPoint;

        [JsonProperty(PropertyName = "p2")]
        private Vector2D secondEndPoint;

        /// <summary>
        /// Default constructor for a Wall.
        /// </summary>
        public Wall() : this(new Vector2D(), new Vector2D(), 0)
        {
            // Calls the other constructor with
            // firstEndPoint = new Vector2D(),
            // secondEndPoint = new Vector2D(), 
            // ID = 0
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="pointOne"></param>
        /// <param name="pointTwo"></param>
        public Wall(Vector2D pointOne, Vector2D pointTwo, int WallID)
        {
            ID = WallID;
            firstEndPoint = pointOne;
            secondEndPoint = pointTwo;
        }

        /// <summary>
        /// Returns this Wall's ID.
        /// </summary>
        /// <returns></returns>
        public int GetID()
        {
            return ID;
        }

        /// <summary>
        /// Returns this Wall's p1 position.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetP1()
        {
            return firstEndPoint;
        }

        /// <summary>
        /// Returns this Wall's p2 position.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetP2()
        {
            return secondEndPoint;
        }
    }
}
