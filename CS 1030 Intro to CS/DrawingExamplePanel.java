/**
 * This is the example code for lab 22 for CS 1030, Spring 2019.  This
 * application makes a JFrame and populates it with exactly one panel
 * (made from this code).  Each student will fill in code in the paint
 * method below to experiment with drawing.
 * 
 * This example has many details that are beyond the scope of this class.
 * Students may look through the code if they are interested, but they
 * should focus their attention on the paint method for lab.
 */

package lab22;  // Java programs must indicate what package they're in.

import java.awt.*; // Java programs must indicate what non-default libraries they use
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;  
import javax.swing.*;

/**
 * This statement tells Java that we're creating a class.  (Everything
 * in Java is part of a class.)  Our class will be built as an
 * extension of an existing class (JPanel), and our class will listen for
 * mouse events (for fun).
 */
public class DrawingExamplePanel extends JPanel implements MouseListener
{
    private static final long serialVersionUID = 1L;  // Ignore :(  Useless junk that gets rid of a warning.  

    /**
     * All Java applications begin execution in a main method.
     * Here is where our program starts.
     */
    public static void main (String[] args)
    {
        // Building a new GUI has to happen in a special thread of execution.
        //   This is advanced stuff, so I've simplified it.  It would take weeks
        //   of lecture to give you the background needed for the line below,
        //   so I'm not going to squeeze it into this comment.  ;)
        
        SwingUtilities.invokeLater(() -> buildGUI());
    }
    
    /**
     * I didn't have time to show this in class.  If you declare a variable
     * -outside- of methods, they'll be part of an object, our object.
     * They will last as long as the object does.  Normally, variables inside
     * of a method only last as long as that method is executing.
     * 
     * The following variables will be available to our panel.  They'll last as
     * long as the panel lasts.  I chose what to declare here.
     */
    int paintCounter;  // How many times paint has been called
    int mouseX;        // The last known x location of a mouse press
    int mouseY;        // The last known y location of a mouse press
    
    /**
     * Our main method above just invokes (executes) this method in a separate
     * thread.  Complex, but it needed to be done.  You would not normally
     * write code like the code in main.  This method is where we build our GUI.
     */
    public static void buildGUI()
    {
    	// Note:  I've improved the variable names for readability.
    	//   This is actually important -- it is easy to get lost in code
    	//   when the variable names are not clear.
    	
        // Make a new window, ask it to kill the application when closed.
        
        JFrame ourFrame = new JFrame ("Lab 22 -- Drawing");
        
        ourFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a new JPanel (so that we have something to draw on).
        // This class extends JPanel, so we'll just make a new object
        //   from this code and add it to the frame.
        
        DrawingExamplePanel ourPanel = new DrawingExamplePanel();
        
        ourFrame.setContentPane(ourPanel);
        
        // Listen to mouse events on our panel.  (Ask Java to call our methods
        //   when the mouse is clicked in our panel.)
        
        ourPanel.addMouseListener(ourPanel);
        
        // Pack the frame (so that it figures out it's own size), then display it.
        
        ourFrame.pack();
        ourFrame.setVisible(true);
    }
    
    /**
     * Before a panel is drawn, the enclosing frame asks the panel how 
     * big it is.  Since we are writing code for the panel, we'll write code
     * that the -frame- will execute to determine our size.  This happens
     * automatically (the code below will be executed by someone else's code).
     */
    @Override
    public Dimension getMinimumSize()
    {
        return new Dimension(500,500);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return getMinimumSize();
    }

    @Override
    public Dimension getMaximumSize()
    {
        return getMinimumSize();
    }

    /**
     * The following methods will automatically be called when the mouse is
     * activated in our panel.  The exact form of these methods is
     * required, but I get to choose what to put into each method.
     * 
     * (In most cases, I just left them empty.  They are required, though, to
     * listen for mouse events.)
     */
    @Override public void mouseEntered (MouseEvent e) { }
    @Override public void mouseExited (MouseEvent e) { }
    @Override public void mouseReleased (MouseEvent e) { }
    @Override public void mouseClicked (MouseEvent e) { }

    /** Notice that this method takes a parameter of type MouseEvent.
     *  When the mouse is pressed, this method gets called (from some
     *  other code), and when the method is called the calling code
     *  sends us a MouseEvent object.  Our variable that receives the object
     *  is named 'e'.
     */
    @Override
    public void mousePressed (MouseEvent e)
    {        
        // Copy the mouse click location into our variables.
        
        this.mouseX = e.getX();
        this.mouseY = e.getY();
        
        // Request that the panel be redrawn at some time soon.
        
        this.repaint();
        
        // Just a message for me.
        
        System.out.print ("The mouse was clicked at (");
        System.out.print (this.mouseX);
        System.out.print (", ");
        System.out.print (this.mouseY);
        System.out.println (").");
    }
   
    /** This method is where we'll do all our drawing.  It is automatically called
     * whenever Java detects that our window needs drawing.  This may happen
     * many times each second, or not once for a long while.
     * 
     * Our task is to redraw the entire picture that we want the user to see.
     */
    @Override
    public void paint (Graphics g)
    {
        // Before we start drawing, I'd like to print a message to the console.
        //   You wouldn't normally do this, but I'd like to show you something...
        //   Try resizing the window when you run this code...  ;)
        
        paintCounter = paintCounter + 1;
        System.out.println ("Paint has been called " + paintCounter + " time(s).");
        
        // Before we start drawing, I'll ask 'this' object how big it is, and
        //   I'll store the results in convenient variables.
        
        int width, height;
        width = this.getWidth();
        height = this.getHeight();
        
        // The Graphics object is what we'll use to do all our drawing.
        //   (Remember, abstraction to the rescue!!!)
        //   Our paint method receives this graphics object in a variable
        //   named g.
        
        // Next, I'll clear the drawing surface to black by filling a rectangle
        //   with black pixels.  The nice thing about objects is they're easy
        //   to use.  You use an object by it's name, and then you indicate
        //   the code to execute.  See below.
        
        g.setColor(Color.BLACK);  // Use g, execute it's code for setting a color, send it a parameter.
        g.fillRect(0,  0, width, height);  // Fill all our space with black pixels.
        
        // Have some fun.  Let's use the mouse location and print some text.
        //   You won't see anything until you click the mouse in the frame.
        //   (Change the color, because black text on black pixels is tough to read.)
        
        // *** TODO ***  (Students will do is in lab.)               
        
        // Student code goes here (before the curly braces that closes the paint method and class).
        // Place instructions that change colors or draw shapes.  Assignment #10
        //   will ask you to be a bit creative.
        //
        // Feel free to declare variables, initialize them with a value, use them
        //   when drawing (for computations), use them in loops, etc.  Caution --
        //   infinite loops will lock up the application.
        
        // Basic shapes.
        
        // *** TODO ***  (Students will do is in lab.)                      
        
        // A bit of fun.  Draw a color gradient using 10 rectangles.
        
        // *** TODO ***  (Students will do is in lab.)               
            
        // A stoplight icon, with variables to make moving/sizing it easy.
        
        // *** TODO ***  (Students will do is in lab.)
    }
    

}
