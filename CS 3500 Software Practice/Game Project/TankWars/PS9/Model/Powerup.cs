using Newtonsoft.Json;

namespace TankWars
{
    /// <summary>
    /// Represents a Powerup in TankWars.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini & Robert Davidson
    /// November 9, 2020
    /// </remarks>
    [JsonObject(MemberSerialization.OptIn)]
    public class Powerup
    {
        [JsonProperty(PropertyName = "power")]
        private int ID;

        [JsonProperty(PropertyName = "loc")]
        private Vector2D location;

        [JsonProperty(PropertyName = "died")]
        private bool died;

        /// <summary>
        /// Default Powerup constructor.
        /// </summary>
        public Powerup() : this(0, new Vector2D())
        {
        }

        public Powerup(int id, Vector2D loc)
        {
            ID = id;
            location = loc;
            died = false;
        }

        /// <summary>
        /// Returns this Objects's location.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetLocation()
        {
            return location;
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
        /// Returns this Objects's died status.
        /// </summary>
        /// <returns>true if dead, false if alive</returns>
        public bool GetStatus()
        {
            return died;
        }

        /// <summary>
        /// Sets died to true when picked up by tank.
        /// </summary>
        public void PickedUp()
        {
            died = true;
        }
    }
}
