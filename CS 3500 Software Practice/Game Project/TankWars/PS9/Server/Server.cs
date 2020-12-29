using System;
using System.Diagnostics;
using System.Threading;
using Model;

namespace TankWars
{
    /// <summary>
    /// Server for TankWars.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini and Robert Davidson
    /// November 26, 2020
    /// </remarks>
    public class Server
    {
        /// <summary>
        /// Server application entry point.
        /// </summary>
        /// <param name="args"></param>
        static void Main(string[] args)
        {
            ServerController controller = new ServerController();

            controller.StartConnection();

            Console.WriteLine("Server is running. Accepting clients.");

            // Start a new Thread for the frame loop
            Thread thread = new Thread(FrameLoop);
            thread.Start(controller);

            Console.ReadLine();
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="obj"></param>
        private static void FrameLoop(object obj)
        {
            ServerController controller = obj as ServerController;

            Stopwatch timer = new Stopwatch();

            while (true)
            {
                timer.Start();

                while (timer.ElapsedMilliseconds < GameConstants.MSPerFrame)
                { /* Do nothing here */ }

                timer.Restart();

                controller.Update();
            }
        }
    }
}
