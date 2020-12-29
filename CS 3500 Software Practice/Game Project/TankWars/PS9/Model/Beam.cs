using Newtonsoft.Json;

namespace TankWars
{
    /// <summary>
    /// Represents a Beam in TankWars.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini & Robert Davidson
    /// November 9, 2020
    /// </remarks>
    [JsonObject(MemberSerialization.OptIn)]
    public class Beam
    {
        [JsonProperty(PropertyName = "beam")]
        private int ID;

        [JsonProperty(PropertyName = "org")]
        private Vector2D origin;

        [JsonProperty(PropertyName = "dir")]
        private Vector2D direction;

        [JsonProperty(PropertyName = "owner")]
        private int ownerID;

        private int timer;

        /// <summary>
        /// Default constructor for a Beam.
        /// </summary>
        public Beam()
        {
            ID = 0;
            origin = new Vector2D();
            direction = new Vector2D();
            ownerID = 0;
            timer = 60;
        }

        /// <summary>
        /// Constructor for a Beam.
        /// </summary>
        /// <param name="id"></param>
        /// <param name="vector2D1"></param>
        /// <param name="vector2D2"></param>
        /// <param name="owner"></param>
        public Beam(int id, Vector2D vector2D1, Vector2D vector2D2, int owner)
        {
            ID = id;
            origin = vector2D1;
            direction = vector2D2;
            ownerID = owner;
        }

        /// <summary>
        /// Returns this Objects's location.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetDirection()
        {
            return direction;
        }

        /// <summary>
        /// Returns this Objects's orientation.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetOrigin()
        {
            return origin;
        }

        /// <summary>
        /// Returns this Objects's ID.
        /// </summary>
        /// <returns></returns>
        public int GetID()
        {
            return ID;
        }

        /// <summary>
        /// Returns this Objects's owner.
        /// </summary>
        /// <returns></returns>
        public int GetOwner()
        {
            return ownerID;
        }

        /// <summary>
        /// Decrements the Beam timer.
        /// </summary>
        public void Countdown()
        {
            --timer;
        }

        /// <summary>
        /// Returns the Beam timer.
        /// </summary>
        /// <returns></returns>
        public int GetTimer()
        {
            return timer;
        }
    }
}
