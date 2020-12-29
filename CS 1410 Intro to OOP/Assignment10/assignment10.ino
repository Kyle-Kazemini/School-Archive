/* A program that receives characters from another Arduino unit by means of the IR transmitter and receiver.
 *  
 *  By: Alexander Pirouznia and Kyle Kazemini
 *  11/14/2019
 */

/*
   Global variables for keeping the 'state' of the Arduino.
*/

const unsigned long period = 20;
bool transmitting_IR;
bool receiving_IR;
bool bit_started = false;
unsigned long start_time;
unsigned long end_time;
char char_to_send;
int our_seq_num;
int their_seq_num;
unsigned long last_send_time;
unsigned long next_send_delay;

/*
   IR helper functions
*/

/* This function will Turn off the IR transmitter.
 *
 * Parameters:   None
 *
 * Returns:      None
 *   
 */
void turn_off_IR ()
{
  // Instead of just adjusting the output on pin 11, this code also
  //   turns off the timer controlling the PWM output on pin 11

  TCCR2A = 0; // Disconnect PWM
  TCCR2B = 0; // Stops the timer
  OCR2A = 0;  // No timer top
  digitalWrite(11, LOW);  // Ensure output is off

  transmitting_IR = false;
}

/* This function turns off the IR transmitter.
 *
 * Parameters:   None
 *
 * Returns:      None
 */
void turn_on_IR ()
{
  // Set up Timer2 (which can be connected to pins 3 and 11)
  // For full details, see:
  //   arduino.cc/en/Tutorial/SecretsOfArduinoPWM
  // The syntax here has me baffled, but the patterns of usage
  //   are clear if you look at the ATMega328 diagrams.
  //   _BV appears to stand for 'bit value'
  //   Different bits need to be set to control each timer
  //   Refer to diagrams for clarity

  TCCR2A = _BV(WGM21) | _BV(COM2A0); // This mode toggles output once per timer cycle
  TCCR2B = _BV(CS20);  // Do not scale the clock down - use 16 MHz timer rate.
  OCR2A = 210; // Divide sys. clock by 210, 1/2 cycle = 76 khz, 1 cycle = 38 khz

  // Output pin 11 should now be emitting a 38 khz signal.

  transmitting_IR = true;
}

/* This function detects if an IR signal is being transmitted.
 *
 * Parameters:    None
 *
 * Returns:       None
 */
bool detect_IR()
{
  // Determine if we are receiving an IR signal.
  //   if pin 12 is false = receiving
  //   if pin 12 is true = not receiving

  return receiving_IR = ! digitalRead(12);
}

/* This function transmits the user's input to the opposing Arduino board in the form of binary.
 * The user's input is required before using this function.
 *
 * Parameters: 
 *   char inChar -- The character that the user inputs.
 *
 * Returns:
 *   int bits -- The translated binary ready to be transmitted.
 */
int transmit_data(char inChar)
{
  turn_on_IR();
  
  delay (period);
  int bits = 0;
  
  bits = (int)inChar;
  
  for (int i = 0; i < 9; i++)
  {
    bits = (inChar >> i) & 1;
    if (bits == 1)
      turn_on_IR();
    else 
      turn_off_IR();
    delay(period);
  }
  turn_off_IR();
  delay(period);
  
  return bits;
}

/* This function decodes the opposing Arduino board's transmitted bit and then return it as a character.
 * Before this is used, the other user will have had to sent the message and the current board has to detect the inbound message.
 *
 * Parameters:   None
 *
 * Returns:
 *   char temp -- The character that has been received and assembled from the opposing Arduino board.
 */
char receive_data()
{   
  int temp = 0; 
  int bits = 0;
  
  delay(period * 1.5);

  for (int i = 0; i < 9; i++)
  {
    if (detect_IR())
      bits = 1;
    else 
      bits = 0;
    
    temp = (bits << i) | temp;
    delay(period);
  }
  
  return (char)temp;
}

/*   
 * . Setup
*/

void setup ()
{
  Serial.begin(9600);

  pinMode (11, OUTPUT); // IR LED
  pinMode (12, INPUT);  // IR receiver

  turn_off_IR();  // My helper function for controlling the IR LED
  detect_IR();    // Ensure the state is correct.
}

/*
  Loop
*/

void loop ()
{
  char inChar = Serial.read();

  if (inChar != -1)
    transmit_data(inChar);

  unsigned long start_time = micros();
  unsigned long required_time = 2 * period;
  unsigned long elapsed_time;
  unsigned long current_time;
  
  do
   {
     current_time = micros();
     elapsed_time = current_time - start_time;

     if (detect_IR() == true)
     {
       turn_off_IR();
       char value = receive_data();
       Serial.print(value);
     }
       
   } while (elapsed_time < required_time);

   
   
}
