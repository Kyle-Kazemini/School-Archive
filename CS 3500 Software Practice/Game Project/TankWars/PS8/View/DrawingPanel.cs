using Model;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Windows.Forms;

namespace TankWars
{
    /// <summary>
    /// Drawing panel for drawing graphics in TankWars.
    /// </summary>
    /// /// <remarks>
    /// Kyle Kazemini & Robert Davidson
    /// November 9, 2020
    /// </remarks>
    public class DrawingPanel : Panel
    {
        // World is a simple container for Players and Powerups
        private readonly World theWorld;

        // wallCoordinates is a list of points used for drawing a set of walls
        private readonly List<Point> wallCoordinates;

        // For determining whether wall locations need to be calculated
        private bool wallsLoaded;

        // Image for World (Tanks = Ships)
        private readonly Image background;

        // Tanks
        private readonly Image blackShip;
        private readonly Image greenShip;
        private readonly Image orangeShip;
        private readonly Image pinkShip;
        private readonly Image purpleShip;
        private readonly Image redShip;
        private readonly Image whiteShip;
        private readonly Image yellowShip;

        // Turrets
        private readonly Image blackTurret;
        private readonly Image greenTurret;
        private readonly Image orangeTurret;
        private readonly Image pinkTurret;
        private readonly Image purpleTurret;
        private readonly Image redTurret;
        private readonly Image whiteTurret;
        private readonly Image yellowTurret;

        // Powerups
        private readonly Image powerupImage;

        // Walls
        private readonly Image wallImage;

        // Projectiles
        private readonly Image blackShell;
        private readonly Image greenShell;
        private readonly Image orangeShell;
        private readonly Image pinkShell;
        private readonly Image purpleShell;
        private readonly Image redShell;
        private readonly Image whiteShell;
        private readonly Image yellowShell;

        // Sink Images
        private readonly Image sunkenShipAlt;

        // Fonts
        private readonly Font drawFont;

        // A delegate for DrawObjectWithTransform
        // Methods matching this delegate can draw whatever they want using e  
        public delegate void ObjectDrawer(object o, PaintEventArgs e);


        /// <summary>
        /// DrawingPanel constructor.
        /// </summary>
        /// <param name="w"></param>
        public DrawingPanel(World w)
        {
            DoubleBuffered = true;
            theWorld = w;

            wallCoordinates = new List<Point>();
            wallsLoaded = false;

            // The following calls are done in the DrawingPanel constructor 
            // so the image only needs to be retreived one time.
            background = Image.FromFile(GetImagePath("Background.png"));
            blackShip = Image.FromFile(GetImagePath("BlackShip.png"));
            greenShip = Image.FromFile(GetImagePath("GreenShip.png"));
            orangeShip = Image.FromFile(GetImagePath("OrangeShip.png"));
            pinkShip = Image.FromFile(GetImagePath("PinkShip.png"));
            purpleShip = Image.FromFile(GetImagePath("PurpleShip.png"));
            redShip = Image.FromFile(GetImagePath("RedShip.png"));
            whiteShip = Image.FromFile(GetImagePath("WhiteShip.png"));
            yellowShip = Image.FromFile(GetImagePath("YellowShip.png"));

            blackTurret = Image.FromFile(GetImagePath("BlackTurret.png"));
            greenTurret = Image.FromFile(GetImagePath("GreenTurret.png"));
            orangeTurret = Image.FromFile(GetImagePath("OrangeTurret.png"));
            pinkTurret = Image.FromFile(GetImagePath("PinkTurret.png"));
            purpleTurret = Image.FromFile(GetImagePath("PurpleTurret.png"));
            redTurret = Image.FromFile(GetImagePath("RedTurret.png"));
            whiteTurret = Image.FromFile(GetImagePath("WhiteTurret.png"));
            yellowTurret = Image.FromFile(GetImagePath("YellowTurret.png"));

            powerupImage = Image.FromFile(GetImagePath("Powerup.png"));

            wallImage = Image.FromFile(GetImagePath("WallSprite.png"));

            blackShell = Image.FromFile(GetImagePath("BlackShell.png"));
            greenShell = Image.FromFile(GetImagePath("GreenShell.png"));
            orangeShell = Image.FromFile(GetImagePath("OrangeShell.png"));
            pinkShell = Image.FromFile(GetImagePath("PinkShell.png"));
            purpleShell = Image.FromFile(GetImagePath("PurpleShell.png"));
            redShell = Image.FromFile(GetImagePath("RedShell.png"));
            whiteShell = Image.FromFile(GetImagePath("WhiteShell.png"));
            yellowShell = Image.FromFile(GetImagePath("YellowShell.png"));

            sunkenShipAlt = Image.FromFile(GetImagePath("SunkenShipAlt.png"));

            drawFont = new Font("Arial", 10);
        }


        /// <summary>
        /// This method performs a translation and rotation to draw an object in the world.
        /// </summary>
        /// <param name="e">PaintEventArgs to access the graphics (for drawing)</param>
        /// <param name="o">The object to draw</param>
        /// <param name="worldSize">The size of one edge of the world (assuming the world is square)</param>
        /// <param name="worldX">The X coordinate of the object in world space</param>
        /// <param name="worldY">The Y coordinate of the object in world space</param>
        /// <param name="angle">The orientation of the objec, measured in degrees clockwise from "up"</param>
        /// <param name="drawer">The drawer delegate. After the transformation is applied, the delegate is invoked to draw whatever it wants</param>
        private void DrawObjectWithTransform(PaintEventArgs e, object o, int worldSize, double worldX, double worldY,
            double angle, ObjectDrawer drawer)
        {
            // "push" the current transform
            System.Drawing.Drawing2D.Matrix oldMatrix = e.Graphics.Transform.Clone();

            int x = WorldSpaceToImageSpace(worldSize, worldX);
            int y = WorldSpaceToImageSpace(worldSize, worldY);
            e.Graphics.TranslateTransform(x, y);
            e.Graphics.RotateTransform((float)angle);
            drawer(o, e);

            // "pop" the transform
            e.Graphics.Transform = oldMatrix;
        }


        /// <summary>
        /// Helper method for DrawObjectWithTransform
        /// </summary>
        /// <param name="size">The world (and image) size</param>
        /// <param name="w">The worldspace coordinate</param>
        /// <returns></returns>
        private static int WorldSpaceToImageSpace(int size, double w)
        {
            return (int)w + size / 2;
        }


        /// <summary>
        /// Private helper method for accessing the Images directory in Resources. 
        /// Returns the full file path for the input string, which is the name of an image in the Images directory.
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        private String GetImagePath(string s)
        {
            string path = Path.Combine(Directory.GetParent(Directory.GetCurrentDirectory()).Parent.Parent.FullName,
                @"Resources\Images\");

            return path + s;
        }


        /// <summary>
        /// This method is invoked when the DrawingPanel needs to be re-drawn.
        /// It redraws all of the objects in the World in a thread safe manner.
        /// </summary>
        /// <param name="e"></param>
        protected override void OnPaint(PaintEventArgs e)
        {
            if (theWorld.Tanks.Count > 0 && theWorld.Size > 0 && !String.IsNullOrEmpty(theWorld.clientID.ToString()))
            {
                int worldSize = theWorld.Size;
                int viewSize = GameConstants.ViewSize;

                lock (theWorld)
                {
                    double playerX =
                        theWorld.Tanks[theWorld.clientID].GetLocation()
                            .GetX(); // (the player's world-space X coordinate)
                    double playerY =
                        theWorld.Tanks[theWorld.clientID].GetLocation()
                            .GetY(); // (the player's world-space Y coordinate)

                    // calculate view/world size ratio
                    double ratio = (double)viewSize / (double)worldSize;
                    int halfSizeScaled = (int)(worldSize / 2.0 * ratio);

                    double inverseTranslateX = -WorldSpaceToImageSpace(worldSize, playerX) + halfSizeScaled;
                    double inverseTranslateY = -WorldSpaceToImageSpace(worldSize, playerY) + halfSizeScaled;

                    e.Graphics.TranslateTransform((float)inverseTranslateX, (float)inverseTranslateY);


                    // draw the background image
                    e.Graphics.DrawImage(background, 0, 0, worldSize, worldSize);

                    // draw the walls
                    if (!wallsLoaded)
                    {
                        foreach (Wall wall in theWorld.Walls.Values)
                        {
                            GetWallsToDraw(wall);
                        }

                        wallsLoaded = true;
                    }

                    foreach (Point point in wallCoordinates)
                    {
                        DrawObjectWithTransform(e, new object(), theWorld.Size, point.X, point.Y, 0, WallDrawer);
                    }

                    // draw the tanks
                    foreach (Tank tank in theWorld.Tanks.Values)
                    {
                        if (tank.IsConnected())
                        {
                            // Draw Body
                            DrawObjectWithTransform(e, tank, theWorld.Size, tank.GetLocation().GetX(),
                                tank.GetLocation().GetY(),
                                tank.GetOrientation().ToAngle(), TankDrawer);


                            // Draw Turret
                            DrawObjectWithTransform(e, tank, theWorld.Size, tank.GetLocation().GetX(),
                                tank.GetLocation().GetY(),
                                tank.GetTurretOrientation().ToAngle(), TurretDrawer);


                            // Draw Info
                            String info = tank.GetName() + ": " + tank.GetScore();
                            DrawObjectWithTransform(e, info, theWorld.Size, tank.GetLocation().GetX(),
                                tank.GetLocation().GetY(),
                                0, InfoDrawer);


                            // Draw Hull Integrity
                            DrawObjectWithTransform(e, tank, theWorld.Size, tank.GetLocation().GetX(),
                                tank.GetLocation().GetY(),
                                0, HullIntegrityDrawer);
                        }
                    }


                    // draw the powerups
                    List<int> powerupsToRemove = new List<int>();
                    foreach (Powerup powerup in theWorld.Powerups.Values)
                    {
                        DrawObjectWithTransform(e, powerup, theWorld.Size, powerup.GetLocation().GetX(),
                            powerup.GetLocation().GetY(), 0, PowerupDrawer);

                        if (powerup.GetStatus())
                        {
                            powerupsToRemove.Add(powerup.GetID());
                        }
                    }

                    foreach (int ID in powerupsToRemove)
                        theWorld.Powerups.Remove(ID);


                    // draw the projectiles
                    foreach (Projectile proj in theWorld.Projectiles.Values)
                    {
                        DrawObjectWithTransform(e, proj, theWorld.Size, proj.GetLocation().GetX(),
                            proj.GetLocation().GetY(), proj.GetOrientation().ToAngle(), ProjectileDrawer);
                    }


                    // draw the beams
                    List<int> beamToRemove = new List<int>();
                    foreach (Beam beam in theWorld.Beams.Values)
                    {
                        DrawObjectWithTransform(e, beam, theWorld.Size, beam.GetOrigin().GetX(),
                            beam.GetOrigin().GetY(), beam.GetDirection().ToAngle() + 180, BeamDrawer);

                        beam.Countdown();

                        if (beam.GetTimer() == 0)
                        {
                            beamToRemove.Add(beam.GetID());
                        }
                    }

                    foreach (int ID in beamToRemove)
                        theWorld.Beams.Remove(ID);
                }
            }

            // Do anything that Panel (from which we inherit) needs to do
            base.OnPaint(e);
        }


        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method to draw a Tank.
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void TankDrawer(object o, PaintEventArgs e)
        {
            Tank tank = o as Tank;
            int h = 60;
            int w = 60;

            if (tank.GetHP() == 0)
            {
                e.Graphics.DrawImage(sunkenShipAlt, -30, -30, w, h);
            }
            else
            {
                if (tank.GetID() % 8 == 0)
                {
                    e.Graphics.DrawImage(blackShip, -30, -30, w, h);
                }
                else if (tank.GetID() % 8 == 1)
                {
                    e.Graphics.DrawImage(greenShip, -30, -30, w, h);
                }
                else if (tank.GetID() % 8 == 2)
                {
                    e.Graphics.DrawImage(orangeShip, -30, -30, h, w);
                }
                else if (tank.GetID() % 8 == 3)
                {
                    e.Graphics.DrawImage(pinkShip, -30, -30, h, w);
                }
                else if (tank.GetID() % 8 == 4)
                {
                    e.Graphics.DrawImage(purpleShip, -30, -30, h, w);
                }
                else if (tank.GetID() % 8 == 5)
                {
                    e.Graphics.DrawImage(redShip, -30, -30, h, w);
                }
                else if (tank.GetID() % 8 == 6)
                {
                    e.Graphics.DrawImage(whiteShip, -30, -30, h, w);
                }
                else
                {
                    e.Graphics.DrawImage(yellowShip, -30, -30, h, w);
                }
            }
        }

        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method to draw a turret.
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void TurretDrawer(object o, PaintEventArgs e)
        {
            Tank tank = o as Tank;
            if (tank.GetHP() > 0)
            {
                if (tank.GetID() % 8 == 0)
                {
                    e.Graphics.DrawImage(blackTurret,
                        -25, -25, 50, 50);
                }
                else if (tank.GetID() % 8 == 1)
                {
                    e.Graphics.DrawImage(greenTurret,
                        -25, -25, 50, 50);
                }
                else if (tank.GetID() % 8 == 2)
                {
                    e.Graphics.DrawImage(orangeTurret,
                        -25, -25, 50, 50);
                }
                else if (tank.GetID() % 8 == 3)
                {
                    e.Graphics.DrawImage(pinkTurret,
                        -25, -25, 50, 50);
                }
                else if (tank.GetID() % 8 == 4)
                {
                    e.Graphics.DrawImage(purpleTurret,
                        -25, -25, 50, 50);
                }
                else if (tank.GetID() % 8 == 5)
                {
                    e.Graphics.DrawImage(redTurret,
                        -25, -25, 50, 50);
                }
                else if (tank.GetID() % 8 == 6)
                {
                    e.Graphics.DrawImage(whiteTurret,
                        -25, -25, 50, 50);
                }
                else
                {
                    e.Graphics.DrawImage(yellowTurret,
                        -25, -25, 50, 50);
                }
            }
        }

        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method to draw a Powerup.
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void PowerupDrawer(object o, PaintEventArgs e)
        {
            e.Graphics.DrawImage(powerupImage, -15, -15, 30, 30);
        }


        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method to draw a Wall.
        /// </summary>
        /// <param name="o"></param>
        /// <param name="e"></param>
        private void WallDrawer(object o, PaintEventArgs e)
        {
            e.Graphics.DrawImage(wallImage, -25, -25, 50, 50);
        }


        /// <summary>
        /// Helper method for getting the points used to draw a set of walls in the World.
        /// </summary>
        /// <param name="w"></param>
        /// <returns></returns>
        private void GetWallsToDraw(Wall w)
        {
            double x1 = w.GetP1().GetX();
            double x2 = w.GetP2().GetX();

            double y1 = w.GetP1().GetY();
            double y2 = w.GetP2().GetY();

            double xDiff = Math.Abs(x2 - x1);
            double yDiff = Math.Abs(y2 - y1);

            for (int xIndex = 0; xIndex <= xDiff; xIndex += 50)
            {
                for (int yIndex = 0; yIndex <= yDiff; yIndex += 50)
                {
                    if (x2 >= x1 && y2 >= y1)
                    {
                        wallCoordinates.Add(new Point((int)(xIndex + x1), (int)(yIndex + y1)));
                    }
                    else if (x2 <= x1 && y2 <= y1)
                    {
                        wallCoordinates.Add(new Point((int)(xIndex + x2), (int)(yIndex + y2)));
                    }
                    else if (x2 > x1 && y2 < y1)
                    {
                        wallCoordinates.Add(new Point((int)(xIndex + x1), (int)(yIndex + y2)));
                    }
                    else
                    {
                        wallCoordinates.Add(new Point((int)(xIndex + x2), (int)(yIndex + y1)));
                    }
                }
            }
        }


        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method to draw a Projectile.
        /// </summary>
        /// <param name="o"></param>
        /// <param name="e"></param>
        private void ProjectileDrawer(object o, PaintEventArgs e)
        {
            Projectile proj = o as Projectile;

            if (proj.GetID() % 8 == 0)
            {
                e.Graphics.DrawImage(blackShell,
                    -15, -15, 30, 30);
            }
            else if (proj.GetID() % 8 == 1)
            {
                e.Graphics.DrawImage(greenShell,
                    -15, -15, 30, 30);
            }
            else if (proj.GetID() % 8 == 2)
            {
                e.Graphics.DrawImage(orangeShell,
                    -15, -15, 30, 30);
            }
            else if (proj.GetID() % 8 == 3)
            {
                e.Graphics.DrawImage(pinkShell,
                    -15, -15, 30, 30);
            }
            else if (proj.GetID() % 8 == 4)
            {
                e.Graphics.DrawImage(purpleShell,
                    -15, -15, 30, 30);
            }
            else if (proj.GetID() % 8 == 5)
            {
                e.Graphics.DrawImage(redShell,
                    -15, -15, 30, 30);
            }
            else if (proj.GetID() % 8 == 6)
            {
                e.Graphics.DrawImage(whiteShell,
                    -15, -15, 30, 30);
            }
            else
            {
                e.Graphics.DrawImage(yellowShell,
                    -15, -15, 30, 30);
            }
        }


        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method to draw a Beam.
        /// </summary>
        /// <param name="o"></param>
        /// <param name="e"></param>
        private void BeamDrawer(object o, PaintEventArgs e)
        {
            Beam beam = o as Beam;

            if (beam.GetTimer() % 3 == 0)
            {
                e.Graphics.FillRectangle(Brushes.Chocolate, -4, 0, 2, 1273);
                e.Graphics.FillRectangle(Brushes.Brown, 0, 0, 2, 1273);
                e.Graphics.FillRectangle(Brushes.Coral, 4, 0, 2, 1273);
            }
            else if (beam.GetTimer() % 3 == 1)
            {
                e.Graphics.FillRectangle(Brushes.Coral, -4, 0, 2, 1273);
                e.Graphics.FillRectangle(Brushes.Chocolate, 0, 0, 2, 1273);
                e.Graphics.FillRectangle(Brushes.Brown, 4, 0, 2, 1273);
            }
            else
            {
                e.Graphics.FillRectangle(Brushes.Brown, -4, 0, 2, 1273);
                e.Graphics.FillRectangle(Brushes.Coral, 0, 0, 2, 1273);
                e.Graphics.FillRectangle(Brushes.Chocolate, 4, 0, 2, 1273);
            }
        }


        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method to draw Player Info.
        /// </summary>
        /// <param name="o"></param>
        /// <param name="e"></param>
        private void InfoDrawer(object o, PaintEventArgs e)
        {
            string s = o as string;
            e.Graphics.DrawString(s.ToUpper(), drawFont, Brushes.Black, -29, 36);
            e.Graphics.DrawString(s.ToUpper(), drawFont, Brushes.Aquamarine, -30, 35);
        }

        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method to draw Player Integrity Info.
        /// </summary>
        /// <param name="o"></param>
        /// <param name="e"></param>
        private void HullIntegrityDrawer(object o, PaintEventArgs e)
        {
            Tank tank = o as Tank;
            if (tank.GetHP() > 2)
            {
                e.Graphics.FillRectangle(Brushes.Black, 7, 53, 8, 4);
                e.Graphics.FillRectangle(Brushes.Aquamarine, 6, 52, 8, 4);
            }
            if (tank.GetHP() > 1)
            {
                e.Graphics.FillRectangle(Brushes.Black, -3, 53, 8, 4);
                e.Graphics.FillRectangle(Brushes.Aquamarine, -4, 52, 8, 4);
            }
            if (tank.GetHP() > 0)
            {
                e.Graphics.FillRectangle(Brushes.Black, -13, 53, 8, 4);
                e.Graphics.FillRectangle(Brushes.Aquamarine, -14, 52, 8, 4);
            }
        }
    }
}