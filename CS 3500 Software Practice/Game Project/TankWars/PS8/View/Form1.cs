using Model;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;

namespace TankWars
{
    /// <summary>
    /// Windows Form application for TankWars.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini & Robert Davidson
    /// November 9, 2020
    /// </remarks>
    public partial class Form1 : Form
    {
        // The controller handles updates from the "server"
        // and notifies us via an event
        private readonly Controller theController;

        // World is a simple container for Players and Powerups
        // The controller owns the world, but we have a reference to it
        private readonly World theWorld;

        private readonly Dictionary<char, bool> keyTracker;

        // Form components
        private readonly DrawingPanel drawingPanel;
        private readonly Button startButton;
        private readonly Button getHelpButton;
        private readonly Button aboutButton;
        private readonly Label nameLabel;
        private readonly Label serverLabel;
        private readonly TextBox serverName;
        private readonly TextBox nameText;

        private const int viewSize = 900;
        private const int menuSize = 40;

        public Form1(Controller ctl)
        {
            InitializeComponent();
            theController = ctl;
            theWorld = theController.GetWorld();
            theController.UpdateArrived += OnFrame;
            theController.ConnectionError += ServerConnectionError;

            keyTracker = new Dictionary<char, bool>
            {
                { 'w', false },
                { 'a', false },
                { 's', false },
                { 'd', false }
            };

            // Set up the form.
            // This stuff is usually handled by the drag and drop designer,
            // but it's simple enough for this lab.

            // Set the window size
            ClientSize = new Size(viewSize, viewSize + menuSize);

            // Place and add the button
            startButton = new Button
            {
                Location = new Point(500, 5),
                Size = new Size(70, 20),
                Text = "Connect and Play"
            };
            startButton.Click += StartClick;
            this.Controls.Add(startButton);

            // Place and add the name label
            nameLabel = new Label
            {
                Text = "Name:",
                Location = new Point(5, 10),
                Size = new Size(40, 15)
            };
            this.Controls.Add(nameLabel);

            // Place and add the name textbox
            nameText = new TextBox
            {
                Text = "player",
                MaxLength = 16,
                Location = new Point(50, 5),
                Size = new Size(70, 15)
            };
            this.Controls.Add(nameText);

            // Place and add the server label
            serverLabel = new Label
            {
                Text = "Server:",
                Location = new Point(200, 10),
                Size = new Size(42, 15)
            };
            this.Controls.Add(serverLabel);

            // Place and add the server textbox
            serverName = new TextBox
            {
                Text = "127.0.0.1",
                Location = new Point(245, 5),
                Size = new Size(70, 15)
            };
            this.Controls.Add(serverName);

            // Place and add the help button
            getHelpButton = new Button
            {
                Location = new Point(700, 5),
                Size = new Size(70, 20),
                Text = "Help"
            };
            getHelpButton.Click += GetHelpClick;
            this.Controls.Add(getHelpButton);

            //Place and add the controls button
            aboutButton = new Button
            {
                Location = new Point(800, 5),
                Size = new Size(70, 20),
                Text = "About"
            };
            aboutButton.Click += AboutClick;
            this.Controls.Add(aboutButton);

            // Place and add the drawing panel
            drawingPanel = new DrawingPanel(theWorld)
            {
                Location = new Point(0, menuSize),
                Size = new Size(viewSize, viewSize)
            };
            this.Controls.Add(drawingPanel);

            // Set up key and mouse handlers
            this.KeyUp += HandleKeyUp;
            this.KeyDown += HandleKeyDown;
            drawingPanel.MouseDown += HandleMouseDown;
            drawingPanel.MouseUp += HandleMouseUp;
            drawingPanel.MouseMove += HandleMouseMovement;
        }

        /// <summary>
        /// Handler for the About button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void AboutClick(object sender, EventArgs e)
        {
            DialogResult connectionErrorDialog = MessageBox.Show("TankWars game written by Kyle Kazemini and Robert Davidson. \n" +
                "Graphics by Kyle Kazemini and Robert Davidson. \n" +
                "CS 3500, Fall 2020, University of Utah.",
                "About", MessageBoxButtons.OK);
        }

        /// <summary>
        /// Handler for the Help button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void GetHelpClick(object sender, EventArgs e)
        {
            DialogResult connectionErrorDialog = MessageBox.Show(
                "[W] Move Up \n" +
                "[S] Move Down \n" +
                "[A] Move Left \n" +
                "[D] Move Right \n" +
                "Mouse: Aim \n" +
                "Left Click: Fire Projectile \n" +
                "Right Click: Fire Beam \n" +
                "[Q] Quit",

                "Help", MessageBoxButtons.OK);
        }


        /// <summary>
        /// Simulates connecting to a "server"
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void StartClick(object sender, EventArgs e)
        {
            // Disable the form controls
            startButton.Enabled = false;
            nameText.Enabled = false;
            serverName.Enabled = false;
            // Enable the global form to capture key presses
            KeyPreview = true;
            // "connect" to the "server"
            theController.ConnectionControl(nameText.Text, serverName.Text);
        }

        /// <summary>
        /// Handler for the controller's UpdateArrived event
        /// </summary>
        private void OnFrame()
        {
            // Invalidate this form and all its children
            // This will cause the form to redraw as soon as it can
            MethodInvoker invoker = new MethodInvoker(() => this.Invalidate(true));
            Invoke(invoker);
        }


        /// <summary>
        /// Handler for invalid server
        /// </summary>
        private void ServerConnectionError(string e)
        {
            DialogResult connectionErrorDialog = MessageBox.Show(e,
                "Confirmation", MessageBoxButtons.OK, MessageBoxIcon.Error);
            if (connectionErrorDialog == DialogResult.OK)
            {
                MessageBox.Show("Please check and re-enter your player and connection data.");

                // Re-Enable the form controls
                nameText.Enabled = true;
                startButton.Enabled = true;
                serverName.Enabled = true;
            }
        }

        /// <summary>
        /// Key down handler.
        /// Checks for which move key was pressed and sends the corresponding string to the Controller
        /// via a Controller method call.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void HandleKeyDown(object sender, KeyEventArgs e)
        {
            lock (keyTracker)
            {
                if (e.KeyCode == Keys.Escape || e.KeyCode == Keys.Q)
                    Application.Exit();

                else if (e.KeyCode == Keys.W)
                {
                    keyTracker['w'] = true;
                    theController.HandleMoveRequest("up");
                }

                else if (e.KeyCode == Keys.D)
                {
                    keyTracker['d'] = true;
                    theController.HandleMoveRequest("right");
                }

                else if (e.KeyCode == Keys.S)
                {
                    keyTracker['s'] = true;
                    theController.HandleMoveRequest("down");
                }

                else if (e.KeyCode == Keys.A)
                {
                    keyTracker['a'] = true;
                    theController.HandleMoveRequest("left");
                }

                // Prevent other key handlers from running
                e.SuppressKeyPress = true;
                e.Handled = true;
            }
        }


        /// <summary>
        /// Key up handler.
        /// Informs the controller that a movement key is no longer held down.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void HandleKeyUp(object sender, KeyEventArgs e)
        {
            lock (keyTracker)
            {
                keyTracker.TryGetValue('w', out bool wPressed);
                keyTracker.TryGetValue('a', out bool aPressed);
                keyTracker.TryGetValue('s', out bool sPressed);
                keyTracker.TryGetValue('d', out bool dPressed);
                if (e.KeyCode == Keys.W)
                {
                    keyTracker['w'] = false;
                    if (theController.GetMoving() == "up" && (aPressed || sPressed || dPressed))
                    {
                        SetPriorMove();
                    }
                }
                else if (e.KeyCode == Keys.A)
                {
                    keyTracker['a'] = false;
                    if (theController.GetMoving() == "left" && (wPressed || sPressed || dPressed))
                    {
                        SetPriorMove();
                    }
                }
                else if (e.KeyCode == Keys.S)
                {
                    keyTracker['s'] = false;
                    if (theController.GetMoving() == "down" && (aPressed || wPressed || dPressed))
                    {
                        SetPriorMove();
                    }
                }
                else if (e.KeyCode == Keys.D)
                {
                    keyTracker['d'] = false;
                    if (theController.GetMoving() == "right" && (aPressed || sPressed || wPressed))
                    {
                        SetPriorMove();
                    }
                }

                keyTracker.TryGetValue('w', out bool wTemp);
                keyTracker.TryGetValue('a', out bool aTemp);
                keyTracker.TryGetValue('s', out bool sTemp);
                keyTracker.TryGetValue('d', out bool dTemp);
                if (!wTemp && !aTemp && !sTemp && !dTemp)
                {
                    theController.HandleMoveRequest("none");
                }
            }
        }

        private void SetPriorMove()
        {
            foreach (char key in keyTracker.Keys)
            {
                keyTracker.TryGetValue(key, out bool down);
                if (down)
                {
                    if (key == 'w')
                        theController.HandleMoveRequest("up");
                    if (key == 'a')
                        theController.HandleMoveRequest("left");
                    if (key == 's')
                        theController.HandleMoveRequest("down");
                    if (key == 'd')
                        theController.HandleMoveRequest("right");
                }
            }
        }
        /// <summary>
        /// Handle mouse down.
        /// Checks for which mouse key was pressed and sends the corresponding string to the Controller
        /// via a Controller method call.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void HandleMouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
                theController.HandleMouseRequest("main");

            if (e.Button == MouseButtons.Right)
                theController.HandleMouseRequest("alt");
        }

        /// <summary>
        /// Mouse up handler.
        /// Informs the controller that the mouse button is no longer held down.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void HandleMouseUp(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left || e.Button == MouseButtons.Right)
                theController.HandleMouseRequest("none");
        }

        /// <summary>
        /// Cursor Movement Handler.
        /// Informs the controller of cursor position.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void HandleMouseMovement(object sender, MouseEventArgs e)
        {
            if (!startButton.Enabled)
                theController.CursorTracker(e.X, e.Y);
        }
    }
}
