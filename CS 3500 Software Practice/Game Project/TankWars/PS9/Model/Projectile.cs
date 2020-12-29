using Newtonsoft.Json;
using TankWars;

namespace Model
{
    /// <summary>
    /// Represents a Projectile in TankWars.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini & Robert Davidson
    /// November 9, 2020
    /// </remarks>
    [JsonObject(MemberSerialization.OptIn)]
    public class Projectile
    {
        [JsonProperty(PropertyName = "proj")]
        private int ID;

        [JsonProperty(PropertyName = "loc")]
        private Vector2D location;

        [JsonProperty(PropertyName = "dir")]
        private Vector2D orientation;

        [JsonProperty(PropertyName = "died")]
        private bool died;

        [JsonProperty(PropertyName = "owner")]
        private int ownerID;


        private Vector2D velocity;


        /// <summary>
        /// Default constructor for a Projectile.
        /// </summary>
        public Projectile() : this(0, new Vector2D(), new Vector2D(), 0)
        {
            // Calls the other constructor with 
            // ID = 0,
            // location = new Vector2D(), 
            // orientation = new Vector2D(), 
            // ownerID = 0
        }

        /// <summary>
        /// Projectile constructor.
        /// </summary>
        /// <param name="idNum"></param>
        /// <param name="loc"></param>
        /// <param name="dir"></param>
        /// <param name="owner"></param>
        public Projectile(int idNum, Vector2D loc, Vector2D dir, int owner)
        {
            ID = idNum;
            location = new Vector2D(loc);
            orientation = new Vector2D(dir);
            died = false;
            ownerID = owner;
            velocity = new Vector2D(orientation);
            velocity.Normalize();
            velocity *= GameConstants.ProjectileSpeed;
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
        /// Returns this Objects's orientation.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetOrientation()
        {
            orientation.Normalize();
            return orientation;
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
        /// Returns this Objects's owner.
        /// </summary>
        /// <returns></returns>
        public int GetOwner()
        {
            return ownerID;
        }


        // ********************* SERVER SPECIFIC METHODS ********************* //

        /// <summary>
        /// On collision, sets the projectile died to true.
        /// </summary>
        public void Collided()
        {
            died = true;
        }

        /// <summary>
        /// Returns this Projectile's velocity.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetVelocity()
        {
            return velocity;
        }

        /// <summary>
        /// Sets this Projectile's location.
        /// </summary>
        /// <param name="vector2D"></param>
        internal void SetLocation(Vector2D vector2D)
        {
            location = vector2D;
        }
    }
}
