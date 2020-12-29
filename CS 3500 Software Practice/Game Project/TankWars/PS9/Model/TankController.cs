using Newtonsoft.Json;
using TankWars;

namespace TankWars
{
    /// <summary>
    /// Wrapper class for some of the Tank mechanics.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini & Robert Davidson
    /// November 9, 2020
    /// </remarks>
    public class TankController
    {
        [JsonProperty(PropertyName = "moving")]
        private readonly string movement;

        [JsonProperty(PropertyName = "fire")]
        private string fireType;

        [JsonProperty(PropertyName = "tdir")]
        private Vector2D aiming;

        /// <summary>
        /// Default constructor for Client Command Controller.
        /// </summary>
        public TankController() : this("none", "none", new Vector2D(0, -1))
        {
            // Calls the other constructor with
            // movement = "none",
            // fireType = "none",
            // aiming = new Vector2D(0, -1)
        }

        /// <summary>
        /// TankController constructor.
        /// </summary>
        /// <param name="moving"></param>
        /// <param name="fire"></param>
        /// <param name="tdir"></param>
        public TankController(string moving, string fire, Vector2D tdir)
        {
            movement = moving;
            fireType = fire;
            aiming = tdir;
        }

        /// <summary>
        /// Returns this TankController's movement.
        /// </summary>
        /// <returns></returns>
        public string GetMovement()
        {
            return movement;
        }

        /// <summary>
        /// Returns this TankController's turret orientation.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetTurretOrientation()
        {
            return aiming;
        }

        /// <summary>
        /// Returns this TankController's fire type.
        /// </summary>
        /// <returns></returns>
        public string GetFireType()
        {
            return fireType;
        }
    }
}