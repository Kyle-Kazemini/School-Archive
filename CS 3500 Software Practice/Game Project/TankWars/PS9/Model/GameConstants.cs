namespace TankWars
{
    /// <summary>
    /// Class to hold constants in the TankWars game.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini & Robert Davidson
    /// November 9, 2020
    /// </remarks>
    public class GameConstants
    {
        public static int MaxHP = 3;
        public static int TankSpeed = 3;
        public static int ViewSize = 900;
        public static int MSPerFrame = 17;
        public static int RespawnRate = 300;
        public static int ProjectileSpeed = 30;
        public static int MaxAvailablePowerups = 3;

        // Object Sizes
        public static int TankSize = 60;
        public static int ProjectileSize = 30;
        public static int WallSize = 50;
        public static int PowerupSize = 30;

        // Timers
        public static int ProjectileFrames = 3000;
        public static int BeamFrames;
        public static int ProjectileFireRate = 30;
        
    }
}
