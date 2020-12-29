using Newtonsoft.Json;
using System.Collections.Generic;

namespace TankWars
{
    /// <summary>
    /// Represents a Tank in TankWars.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini & Robert Davidson
    /// November 9, 2020
    /// </remarks>
    [JsonObject(MemberSerialization.OptIn)]
    public class Tank
    {
        [JsonProperty(PropertyName = "tank")]
        private int ID;

        [JsonProperty(PropertyName = "loc")]
        private Vector2D location;

        [JsonProperty(PropertyName = "bdir")]
        private Vector2D orientation;

        [JsonProperty(PropertyName = "tdir")]
        private Vector2D aiming;

        [JsonProperty(PropertyName = "name")]
        private string name;

        [JsonProperty(PropertyName = "hp")]
        private int hitPoints;

        [JsonProperty(PropertyName = "score")]
        private int score;

        [JsonProperty(PropertyName = "died")]
        private bool died;

        [JsonProperty(PropertyName = "dc")]
        private bool disconnected;

        [JsonProperty(PropertyName = "join")]
        private bool joined;

        private Vector2D velocity;
        private int fireTimer;

        private int beamCharges;


        /// <summary>
        /// Default constructor for a Tank.
        /// </summary>
        public Tank() : this("", 0)
        {
            // Calls the other constructor with 
            // name = "" 
            // ID = 0
        }

        /// <summary>
        /// Constructor for a Tank.
        /// </summary>
        /// <param name="s"></param>
        /// <param name="id"></param>
        public Tank(string s, int id)
        {
            name = s;
            ID = id;
            location = new Vector2D();
            orientation = new Vector2D();
            aiming = new Vector2D();
            hitPoints = GameConstants.MaxHP;
            hitPoints = GameConstants.MaxHP;
            beamCharges = 0;
            score = 0;
            died = false;
            disconnected = false;
            joined = true;
            fireTimer = 0;
        }

        /// <summary>
        /// Returns this Tank's ID.
        /// </summary>
        /// <returns></returns>
        public int GetID()
        {
            return ID;
        }

        /// <summary>
        /// Returns this Tank's location.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetLocation()
        {
            return location;
        }

        /// <summary>
        /// Returns this Tank's orientation.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetOrientation()
        {
            return orientation;
        }

        public void SetOrientation(Vector2D v)
        {
            v.Normalize();
            orientation = v;
        }

        /// <summary>
        /// Returns this Tank's orientation.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetTurretOrientation()
        {
            return aiming;
        }

        /// <summary>
        /// Sets this Tank's turret orientation.
        /// </summary>
        /// <param name="v"></param>
        public void SetTurretOrienation(Vector2D v)
        {
            v.Normalize();
            aiming = v;
        }

        /// <summary>
        /// Returns this Tank's Name.
        /// </summary>
        /// <returns></returns>
        public string GetName()
        {
            return name;
        }

        /// <summary>
        /// Returns this Tank's Hit Points.
        /// </summary>
        /// <returns></returns>
        public int GetHP()
        {
            return hitPoints;
        }

        /// <summary>
        /// Returns this Tank's Score.
        /// </summary>
        /// <returns></returns>
        public int GetScore()
        {
            return score;
        }

        /// <summary>
        /// Returns this Tank's velocity.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetVelocity()
        {
            return velocity;
        }

        /// <summary>
        /// Sets this Tank's velocity.
        /// </summary>
        /// <param name="v"></param>
        public void SetVelocity(Vector2D v)
        {
            velocity = v;
        }

        /// <summary>
        /// Set this Tank's location.
        /// </summary>
        /// <param name="loc"></param>
        public void SetLocation(Vector2D loc)
        {
            location = loc;
        }

        /// <summary>
        /// Returns this Tank's Alive Status.
        /// </summary>
        /// <returns></returns>
        public bool GetStatus()
        {
            return died;
        }


        /// <summary>
        /// Returns this Tank's Alive Status.
        /// </summary>
        /// <returns></returns>
        public bool IsConnected()
        {
            return !disconnected;
        }


        // ********************* SERVER SPECIFIC METHODS ********************* //

        /// <summary>
        /// Decreases ship's hit points by 1 and evaluate died to true if ship's hit points equals one.
        /// </summary>
        public void CitadelHit()
        {
            --hitPoints;

            if (hitPoints == 0)
            {
                died = true;
            }
        }

        /// <summary>
        /// Checks if this Tank is ready to fire using the
        /// Projectile fire rate.
        /// </summary>
        /// <returns></returns>
        public bool ReadyToFire()
        {
            if (fireTimer > 0)
            {
                --fireTimer;
                return false;
            }
            else
            {
                fireTimer = GameConstants.ProjectileFireRate;
                return true;
            }
        }

        /// <summary>
        /// Attempts alt fire. Only fired if has a beam charge.
        /// </summary>
        /// <returns> True if beam fired. </returns>
        public bool CanFireBeam()
        {
            if (beamCharges > 0)
            {
                beamCharges--;
                return true;
            }

            return false;
        }

        /// <summary>
        /// Adds a beam charge to the tank.
        /// </summary>
        public void CollectedPowerup()
        {
            beamCharges++;
        }
    }
}