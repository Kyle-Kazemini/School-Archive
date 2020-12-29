using Model;
using System;
using System.Collections.Generic;

namespace TankWars
{
    /// <summary>
    /// Represents a World in TankWars. 
    /// The World is a container for Tanks, Powerups, etc.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini & Robert Davidson
    /// November 9, 2020
    /// </remarks>
    public class World
    {
        public Dictionary<int, Tank> Tanks;
        public Dictionary<int, Powerup> Powerups;
        public Dictionary<int, Wall> Walls;
        public Dictionary<int, Beam> Beams;
        public Dictionary<int, Projectile> Projectiles;

        public Dictionary<SocketState, TankController> Clients;
        public int clientID;

        /// <summary>
        /// Size property for the World.
        /// </summary>
        public int Size { get; set; }

        /// <summary>
        /// Constructor for a World that takes the World size as a parameter.
        /// </summary>
        /// <param name="_size"></param>
        public World(int _size)
        {
            Tanks = new Dictionary<int, Tank>();
            Powerups = new Dictionary<int, Powerup>();
            Walls = new Dictionary<int, Wall>();
            Beams = new Dictionary<int, Beam>();
            Projectiles = new Dictionary<int, Projectile>();
            Clients = new Dictionary<SocketState, TankController>();
            Size = _size;
        }

        // *************** CLIENT SIDE METHODS *************** //

        /// <summary>
        /// Updates a Tank in the World.
        /// If the Tank doesn't exist, it's added.
        /// </summary>
        /// <param name="id"></param>
        /// <param name="tank"></param>
        public void UpdateTank(Tank tank)
        {
            Tanks.Add(tank.GetID(), tank);
        }

        /// <summary>
        /// Updates a Powerup in the World. 
        /// If the Powerup doesn't exist, it's added.
        /// </summary>
        /// <param name="id"></param>
        /// <param name="powerup"></param>
        public void UpdatePowerup(Powerup powerup)
        {
            Powerups.Add(powerup.GetID(), powerup);
        }

        /// <summary>
        /// Adds a Wall to the World.
        /// </summary>
        /// <param name="w"></param>
        public void UpdateWall(Wall w)
        {
            Walls.Add(w.GetID(), w);
        }

        /// <summary>
        /// Adds a Beam to the World.
        /// </summary>
        /// <param name="beam"></param>
        public void UpdateBeam(Beam beam)
        {
            Beams.Add(beam.GetID(), beam);
        }

        /// <summary>
        /// Adds a Projectile to the World.
        /// </summary>
        /// <param name="projectile"></param>
        public void UpdateProjectile(Projectile projectile)
        {
            if (projectile.GetStatus())
                Projectiles.Remove(projectile.GetID());
            else
            {
                Projectiles[projectile.GetID()] = projectile;
            }
        }

        // *************** SERVER SIDE *************** //

        /// <summary>
        /// Used to set Wall ID and add Wall Data
        /// </summary>
        /// <param name="id"></param>
        /// <param name="w"></param>
        public void BuildWall(Wall w)
        {
            Walls[w.GetID()] = w;
        }

        /// <summary>
        /// Helper method for moving a Projectile.
        /// </summary>
        /// <param name="proj"></param>
        /// <returns></returns>
        public bool MoveProjectiles(Projectile proj)
        {
            if (proj.GetStatus())
                return true;

            if (!ProjectileWallCollision(proj) && !ProjectileTankCollision(proj))
            {
                proj.SetLocation(proj.GetVelocity() + proj.GetLocation());
            }

            if (proj.GetLocation().GetX() < -Size / 2.0 || proj.GetLocation().GetX() > Size / 2.0 ||
                proj.GetLocation().GetY() < -Size / 2.0 || proj.GetLocation().GetY() > Size / 2.0)
            {
                proj.Collided(); // Test if projectiles die if they exit the world
            }

            return false;
        }



        // *************** COLLISION CHECKS *************** //

        /// <summary>
        /// Checks for Projectile and Tank Collisions
        /// O(P*T)
        /// @@@@@ MUST LOCK WORLD OBJECT WHEN CALLED
        /// </summary>
        public bool ProjectileTankCollision(Projectile proj)
        {
            foreach (Tank t in Tanks.Values)
            {
                if (!proj.GetStatus() && t.GetHP() > 0 && proj.GetOwner() != t.GetID()) // Only check for collisions if both objects are "alive"
                {
                    if (RadiusIntersectionCheck(proj.GetLocation(), 0, t.GetLocation(), GameConstants.TankSize))
                    {
                        proj.Collided();
                        t.CitadelHit();
                        return true;
                    }
                }
            }

            return false;
        }

        /// <summary>
        /// Checks for Projectile and Wall Collisions
        /// O(P*T)
        /// @@@@@ MUST LOCK WORLD OBJECT WHEN CALLED
        /// </summary>
        public bool ProjectileWallCollision(Projectile proj)
        {
            foreach (Wall w in Walls.Values)
            {
                if (BoxIntersectionCheck(w, proj.GetLocation(), 0))
                {
                    proj.Collided();
                    return true;
                }
            }
            return false;
        }

        /// <summary>
        /// Checks for Tank and Wall Collisions
        /// O(W*T)
        /// @@@@@ MUST LOCK WORLD OBJECT WHEN CALLED
        /// </summary>
        /// <returns> true if a collision occurs </returns>
        public bool WallTankCollisionCheck(Vector2D moveLocation)
        {
            // TODO: Should we check for this only when a client sends info?
            bool collision = false;

            foreach (Wall w in Walls.Values)
            {
                if (BoxIntersectionCheck(w, moveLocation, GameConstants.TankSize)) // TODO: Is Tank Radius the right size? Is it 30 / 2?
                    collision = true;
            }

            return collision;
        }

        // TODO: where should we spawn Powerups?

        /// <summary>
        /// Checks for Tank and Wall Collisions to set new spawn location.
        /// @@@@@ MUST LOCK WORLD OBJECT WHEN CALLED
        /// </summary>
        public Vector2D SpawnObject(int objSize)
        {
            Vector2D spawnLocation = location();

            for (bool validSpawn = false; validSpawn == false;)
            {

                spawnLocation = location();
                validSpawn = true;
                foreach (Wall w in Walls.Values)
                {
                    if (BoxIntersectionCheck(w, spawnLocation, objSize))
                        validSpawn = false;
                }
            }

            Vector2D location()
            {
                Random r = new Random();
                return new Vector2D(r.Next(Size) - (Size / 2), r.Next(Size) - (Size / 2));
            }

            return spawnLocation;
        }


        /// <summary>
        /// Intersection checking for non-wall objects.
        /// </summary>
        /// <param name="p1"> Center point of object 1. </param>
        /// <param name="p1Radius"> Radius / Half Length of object 1. </param>
        /// <param name="p2"> Center point of object 2. </param>
        /// <param name="p2Radius"> Radius / Half Length of object 2. </param>
        /// <returns> True if collision detected. </returns>
        public bool RadiusIntersectionCheck(Vector2D p1, double p1Radius, Vector2D p2, double p2Radius)
        {
            return (p2 - p1).Length() < (p1Radius + p2Radius); // TODO: Double check my math here.
        }


        /// <summary>
        /// Intersection checking for walls vs. non-wall objects.
        /// </summary>
        /// <param name="w"> Wall </param>
        /// <param name="obj"> Center point of object. </param>
        /// <param name="objSize"> Length / Width of object. [In this project they are the same] </param>
        /// <returns> Returns true if a collision is detected, false otherwise. </returns>
        public bool BoxIntersectionCheck(Wall w, Vector2D obj, double objSize)
        {
            double wallLeft;
            double wallRight;
            double wallBottom;
            double wallTop;
            double objLeft;
            double objRight;
            double objBottom;
            double objTop;

            double wallOffset = GameConstants.WallSize / 2; //Size / 2;

            if (w.GetP1().GetX() < w.GetP2().GetX())
            {
                wallLeft = w.GetP1().GetX() - wallOffset;
                wallRight = w.GetP2().GetX() + wallOffset;
            }
            else
            {
                wallLeft = w.GetP2().GetX() - wallOffset;
                wallRight = w.GetP1().GetX() + wallOffset;
            }

            if (w.GetP1().GetY() > w.GetP2().GetY())
            {
                wallBottom = w.GetP1().GetY() + wallOffset;
                wallTop = w.GetP2().GetY() - wallOffset;
            }
            else
            {
                wallTop = w.GetP1().GetY() - wallOffset;
                wallBottom = w.GetP2().GetY() + wallOffset;
            }

            if (objSize > 1)
            {
                double offset = objSize / 2.0;
                objLeft = obj.GetX() - offset;
                objRight = obj.GetX() + offset;
                objTop = obj.GetY() - offset;
                objBottom = obj.GetY() + offset;
            }
            else
            {
                objLeft = obj.GetX();
                objRight = obj.GetX();
                objTop = obj.GetY();
                objBottom = obj.GetY();
            }

            // Collision
            if ((objLeft > wallLeft && objLeft < wallRight) || (objRight > wallLeft && objRight < wallRight) || (objLeft < wallLeft && objRight > wallRight))
            {
                if ((objTop < wallBottom && objTop > wallTop) || (objBottom < wallBottom && objBottom > wallTop) || (objBottom > wallBottom && objTop < wallTop))
                    return true;
            }

            return false;
        }
    }
}