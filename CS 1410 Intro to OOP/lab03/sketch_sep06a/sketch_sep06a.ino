


/* Lab #3
 *
 * The code is a state machine that will turn on three LEDs in sequence,
 * then turn them off in the same sequence.
 *
 * Pin assignment:
 *    First LED output:  pin 2 (digital 2)
 *    Second LED output: pin 3 (digital 3)
 *    Third LED output:  pin 4 (digital 4)
 *
 *    Analog input:     pin A0 (analog in 0)
 *    Digital input:     pin 8  (digital 8)
 *
 * Students will add:
 *    Code to read an analog input and adjust a delay
 *    Code to read a digital input and start / stop the animation
 *
 * Based on Chad Hokama's solution, updated by Peter Jensen 9/2/2019
 */
 
 // Notice the lack of include statements.  The Arduino environment
 //   defines some stuff for us so we don't have to include files.
 
 // We created these variables to keep track of the program's 'state'.
 // You will add one more during lab.
 
 int led_state;

/*
 * The setup function executes once when your board is powered up.
 */

 void setup ()
 {
   // Set up the digital pins as inputs or outputs
     
   pinMode (2, OUTPUT);  // First LED output  
   pinMode (3, OUTPUT);  // Second LED output
   pinMode (4, OUTPUT);  // Third LED output
   
   // You will set up the digital input pin here during lab
   
   // Make sure we start in a known state
   
   led_state = 0;
 }
 
/*
 * This 'loop' function is repeatedly executed by the built-in Arduino
 *   main function.  That's why it's called loop.  When it ends,
 *   it will quickly be started again.  
 */

 void loop ()
{
     // Read the inputs - you will add this during lab.
   
     // Do state activity
     
     if (led_state == 0)
     {
         digitalWrite(2, LOW);
         digitalWrite(3, LOW);
         digitalWrite(4, LOW);
         led_state = 1;
     }
     else if (led_state == 1)
     {
         digitalWrite(2, HIGH);
         digitalWrite(3, LOW);
         digitalWrite(4, LOW);
         led_state = 2;
     }
     else if (led_state == 2)
     {
         digitalWrite(2, HIGH);
         digitalWrite(3, HIGH);
         digitalWrite(4, LOW);
         led_state = 3;
     }
     else if (led_state == 3)
     {
         digitalWrite(2, HIGH);
         digitalWrite(3, HIGH);
         digitalWrite(4, HIGH);
         led_state = 4;
     }
     else if (led_state == 4)
     {
         digitalWrite(2, LOW);
         digitalWrite(3, HIGH);
         digitalWrite(4, HIGH);
         led_state = 5;
     }
     else if (led_state == 5)
     {
         digitalWrite(2, LOW);
         digitalWrite(3, LOW);
         digitalWrite(4, HIGH);
         led_state = 0;
     }
   
     // Delay to slow down the process.
   
     delay(1000);
   
}
