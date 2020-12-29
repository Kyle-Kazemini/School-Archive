/* A program that receives characters from another Arduino unit by means of the IR transmitter and receiver.
 * The program is modified to be more robust by rejecting interference and continuing to send a message
 * if there's an interuption.
 *  
 *  By: Alexander Pirouznia and Kyle Kazemini
 *  11/14/2019
 */

/*
   Global variables for keeping the 'state' of the Arduino.
*/
/* Constants */


const unsigned long pulse_width      = 316;  // In microseconds
const unsigned long pulse_half_width = 158;  // In microseconds
const bool is_uno  = true;  
const int IR_LED_PIN = is_uno ? 11 : 10;
unsigned char char_to_send = 0;
unsigned char our_seq_num = 0;
unsigned char their_seq_num = 0;
unsigned long last_send_time;
unsigned long next_send_delay;
bool should_resend = false; 
bool should_confirm = false; 
unsigned long resend_start_time; 
unsigned long resend_wait_time;

/*
 * Turns off the infrared transmitter (LED).  (The PWM
 * timer is disabled.)
 */
void turn_off_IR ()
{
  // Instead of just adjusting the output on pin 11, this code also
  //   turns off the timer controlling the PWM output on pin 11
  
  TCCR2A = 0; // Disconnect PWM
  TCCR2B = 0; // Stops the timer
  OCR2A = 0;  // No timer top
  digitalWrite(IR_LED_PIN, LOW);  // Ensure output is off
}


/*
 * Turns on the infrared transmitter (LED).  This function
 * uses a PWM timer to flicker the LED at a 38 kHz rate (so
 * that the detector will detect it.)
 */
 void turn_on_IR ()
{
  // Set up Timer2 (which can be connected to pins 3 and 11 on UNO boards)
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
}

/*
 * Returns true if the board is receiving a 38 kHz IR signal, and false
 * otherwise.  It should be noted that it takes 8 full cycles of a
 * 38 kHz signal for this function to return true.  Also, the receiver
 * will stop receiving a signal if the signal lasts for more than 1/2 
 * second.
 * 
 * Returns:
 *   true iff the board is detecting an IR signal
 */
bool detect_IR()
{
  // Determine if we are receiving an IR signal.
  //   if pin 12 is false = receiving
  //   if pin 12 is true = not receiving
  
  return (! digitalRead(12));  
}

/*
 * Setup - Runs once when the Arduino boots up.
 */

void setup ()
{
  Serial.begin(9600);  // Enable the serial monitor input/output
    
  pinMode (IR_LED_PIN, OUTPUT); // IR LED
  pinMode (12, INPUT);  // IR receiver
  
  turn_off_IR();  // My helper function for controlling the IR LED
}


/*
 * Sends a byte across the IR channel.  The byte is stored in an int
 * for convenience.  The value must be between 0 and 255 (so that it
 * fits within 8 bits).  Out-of-bounds characters will not be sent.
 * 
 * Parameters:
 *   b - the byte to send (as an int)
 */
void transmit_byte(int b)
{
  // Double-check.  If the byte is not in the range
  // [0..255], issue a debugging message and abort.

  if (b < 0 || b > 255)
  {
    Serial.println();
    Serial.print("Cannot send out-of-range byte value: ");
    Serial.println(b);
    return;
  }
  
  // Keep track of the start time at the beginning of this
  //   transmission.
  
  unsigned long start_time = micros();
  unsigned long elapsed_micros;
  unsigned long required_micros = 0;  // I increase this as bits are sent.

  // Turn on the start bit.

  turn_on_IR();
  required_micros += pulse_width;  // Next bit will be one period later.


  for (int i = 0; i < 10; i++) // Send 8 bits, one quiet bit, and one more
  {
    // Extract the desired bit.

    int current_bit = (b >> i) & 1;
    
    // Make sure it's time for the next bit.

    do
    {
      elapsed_micros = micros() - start_time;
    } while (elapsed_micros < required_micros);

    // Send it.  (The 9th and 10th iterations will be 0.)

    if (current_bit == 1)
      turn_on_IR();
    else
      turn_off_IR();

    // Advance the required time (so we'll wait longer next time).

    required_micros += pulse_width;
  }

}

/*
 * Attempts to receive a byte for a short period of time.  If
 * a transmission is detected (IR signal start bit), this function
 * reads the data byte and returns it.  Otherwise, this function
 * returns -1 after the short period of time expires.
 * 
 * Parameters:
 *   wait_time - a number of signal periods to wait for inbound data
 *   
 * Returns:
 *   a received byte (as an int), or -1 if no data received
 */
int receive_byte (unsigned long wait_time)
{
  // Prepare the timer.
  
  unsigned long start_time = micros();
  unsigned long elapsed_micros;
  unsigned long required_micros = wait_time * pulse_width;

  // Wait for a transmission to start.  If one does not
  //   start, give up after the required time.

  do
  {
    if (detect_IR())
      break;
      
      elapsed_micros = micros() - start_time;
  } while (elapsed_micros < required_micros);

  // If the IR is not on, we timed out (abort).

  if (!detect_IR())
    return -1;

  
  start_time += elapsed_micros;
  required_micros = pulse_width + pulse_half_width;
  
  int data_bits = 0;

  // Receive all eight bits.

  for (int i = 0; i < 8; i++)
  {
    // Make sure it's time to receive a bit.

    do
    {
      elapsed_micros = micros() - start_time;
    } while (elapsed_micros < required_micros);

    // Receive a bit, combine it with our data value.

    int received_bit = detect_IR() ? 1 : 0;

    data_bits = data_bits | (received_bit << i);

    // Advance the required time (so we'll wait longer next time).

    required_micros += pulse_width;
  }


  while (detect_IR())
  {
    // Do nothing but wait. It should
    //   complete in no more than 1/2 period.
  }

  // Return the data byte.

  return data_bits;
}

/*
 * Uses the transmit_byte function to send an entire packet, 
 * byte by byte.
 * 
 * Parameters:
 *   unsigned char - the character we want to send
 *   
 * Returns:
 *   no return
 */
void transmit_packet(unsigned char char_to_send)
{
  int preamble = 2;
  int postamble = 3;
  
  transmit_byte(preamble);

  transmit_byte(char_to_send);
  
  transmit_byte(our_seq_num);

  transmit_byte(their_seq_num);
  
  transmit_byte(postamble);

  unsigned char check_sum = (unsigned char) (preamble + char_to_send + our_seq_num + their_seq_num + postamble);

  transmit_byte(check_sum);

  // Since we just sent, should_confirm should be false
  // Then wait some time for resending
  
  should_confirm = false;
  resend_start_time = micros();
  resend_wait_time = 250000 + random(250000);
}

/*
 * Uses the receive_byte function to receive an entire packet,
 * byte by byte.
 * 
 * Parameters:
 *   none
 *   
 * Returns:
 *   none
 */
void receive_packet()
{

  int preamble = receive_byte(10);
  if (preamble != 2)
    return;
  
  unsigned char packet_char = receive_byte(2);
  if (packet_char == -1)
    return;
    
  int packet_their_num = receive_byte(2);
  if (packet_their_num == -1)
    return;
    
  int packet_our_num = receive_byte(2);
  if (packet_our_num == -1)
    return;
    
  int postamble = receive_byte(2);
  if (postamble != 3)
    return;
    
  int check_sum_theirs = receive_byte(2);
  if (check_sum_theirs == -1)
    return;
    
  int check_sum_ours = preamble + packet_char + packet_our_num + packet_their_num + postamble;
  if (check_sum_theirs != check_sum_ours)
    return;

  // Logic for properly confirming, resending, and receiving packets
   
  if (packet_char != 0 && their_seq_num != packet_their_num)
    Serial.print((char)packet_char);
    
  their_seq_num = packet_their_num;
      
  if (packet_char != 0)
  {
    should_confirm = true;
    // A hack to kill two periods
    receive_byte(2);  
  }

  if (packet_our_num == our_seq_num)
  {
    should_resend = false;
    char_to_send = 0;
  }
}

/*
 * Loop - Arduino main loop.
 */
void loop ()
{
  // Check for inbound packet
  
    receive_packet();

  // Check for typed character
  
  if (char_to_send == 0)
  {
    int temp = Serial.read();
    if (temp > 0)
    {
      char_to_send = temp;
      our_seq_num ++;

      resend_start_time = micros();
      resend_wait_time = 0;
      should_resend = true;
    } 
  }
  // See if we need to send a packet
  
  unsigned long elapsed_time = micros() - resend_start_time;

  if (should_confirm || (should_resend && elapsed_time >= resend_wait_time))
    transmit_packet(char_to_send);
  
}
