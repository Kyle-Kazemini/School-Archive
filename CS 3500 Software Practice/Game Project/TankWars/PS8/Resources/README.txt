TANK WARS



ABOUT

This is a Tank Wars solution designed and implemented by Kyle Kazemini and Robert Davidson for CS 3500, Fall 2020 at the University of Utah. 
The code is our own, but uses examples given to us in lectures, labs, recitations, and the examples repository.



FEATURES

	Naval Tank Wars - We decided to use Tank Wars functionality in a totally different setting: naval combat. This design includes the following.
ocean background,
beach themed walls,
ships in place of tanks,
a cannon as the turret,
a battery image as the powerup,
a futuristic rail gun animation as the beam,
a broken ship animation when a ship dies.

	Graphics - The graphics we used are fair use. Most of them were either custom made or heavily edited as well. 
We decided to do this to give our game a unique feel on top of the functionality.

	Beam - The beam attack is a red lazer/rail gun animation. This was created by using a timer and strategically drawing rectangles using that timer.

	Death animation - When a ship is killed, the game shows a sinking ship. This was done by creating an image of a sinking ship and overlaying blue to look like water.



DESIGN DECISIONS

	Networking - We decided to use NetworkController.dll over copying our PS7 code. 
If we find errors we can fix them in PS7 and update our dll in PS8. This is preferable to having two sets of the same code to update.

	Using networking code - We first use the ConnectToServer method and send the server the player name. 
We then use two methods for receiving the player ID and world size and then starting the event loop to receive JSON from the server.
If JSON objects are received properly, they're deserialized and added into the proper world collection.

	Assigning colors - We used the ID given by the server to assign a color to each tank, projectile, etc.

	Drawing images - we used a helper method to get the full path name for each image file. 
We then create an Image object and pass it to the Graphics DrawImage method.

	Drawing walls - We used a boolean to check whether or not the walls have already been drawn because it only needs to happen once. 
We used a helper method for getting a list of coordinates of the walls to be drawn. We then iterated through the list to draw the walls.

	Objects disappearing in the world - We used helper methods and boolean conditions to check which objects need to be removed on this frame.
If need be, we remove the object from the world so it only shows up once per frame.

	Drawing player info and health - We handled this in the OnPaint method where we draw each tank. 
We found this to be the easiest way to associate the name, score, and health with each tank.

	Help and about - We used two Button objects in the Windows Form to give the user a help and about section.
The help button lists the controls for the game.
The aboout button gives an overview of the Tank Wars project.



IMPLEMENTATION

The Tank Wars project is implemented using the MVC framework. 
There is a view containing a Windows Forms application and code for drawing the graphics of the game.
There is a model that contains JSON properties, methods, etc. for the objects in the game (tanks, walls, projectiles, and so on). 
Finally, there is a controller that links the view and model together. The view communicates with the controller through method calls and the controller communicates with the view through events.
The model and controller communicate through deserialized JSON objects sent via networking code.