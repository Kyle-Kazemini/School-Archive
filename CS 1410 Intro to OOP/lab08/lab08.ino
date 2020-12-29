/*
   Global variables for keeping the 'state' of the Arduino.
*/

const unsigned long period = 20000;
bool transmitting_IR;
bool receiving_IR;
bool bit_started = false;
unsigned long start_time;
unsigned long end_time;
unsigned long start_time_dark;
unsigned long end_time_dark;

/*
   IR helper functions
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


bool detect_IR()
{
  // Determine if we are receiving an IR signal.
  //   if pin 12 is false = receiving
  //   if pin 12 is true = not receiving

  return receiving_IR = ! digitalRead(12);
}

int transmit_data(char inChar)
{
  Serial.write(inChar);
  return inChar;
}

int receive_data(int bit)
{     
  int x;
  for (int i = 0; i < 9; i++)
  {
    x = bit;
    (x >> 1) & 1;
  }
  return x;
}

/*
   Setup
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
  // Check button state
  //   0 = pressed
  //   1 = not pressed
  
  bool button_pressed = ! digitalRead(4);

  if (transmitting_IR && ! button_pressed)
    turn_off_IR();

  if (!transmitting_IR && button_pressed)
    turn_on_IR();
  // See if the IR is on.

  detect_IR();

  // Keep track of the total receiving time in
  //   microseconds.

  // If we're not started, but a bit is coming in...

 unsigned long elapsed_time;
 unsigned long required_time = period * 8;
 
  if (!bit_started && receiving_IR)
  { 
    char inChar = Serial.read();
    int i = 0;
    while (Serial.read())
    {
     receive_data(inChar);
     Serial.print(inChar);
     bit_started = true;
     start_time = micros();
    }
    do
    {
      elapsed_time = (i + 1) * period;
    } while (elapsed_time < required_time);
    i++;
  }
 
 
  // If we've started, but the bit stopped...

  if (bit_started && !receiving_IR)
  {
    int inChar = Serial.write(inChar);
    int i = 0;
    transmit_data(inChar);
    bit_started = false;
    end_time = micros();
  
   do
   {
     elapsed_time = (i + 1) * period;
   } while (elapsed_time < required_time);
   i++;
  }
}
