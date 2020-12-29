/*
  Blink

  Turns an LED on for one second, then off for one second, repeatedly.

  Most Arduinos have an on-board LED you can control. On the UNO, MEGA and ZERO
  it is attached to digital pin 13, on MKR1000 on pin 6. LED_BUILTIN is set to
  the correct LED pin independent of which board is used.
  If you want to know what pin the on-board LED is connected to on your Arduino
  model, check the Technical Specs of your board at:
  https://www.arduino.cc/en/Main/Products

  modified 8 May 2014
  by Scott Fitzgerald
  modified 2 Sep 2016
  by Arturo Guadalupi
  modified 8 Sep 2016
  by Colby Newman

  This example code is in the public domain.

  http://www.arduino.cc/en/Tutorial/Blink
*/
#include <Servo.h>
Servo my_servo;
bool short_delay = false;
bool last_button_was_out = true;
unsigned long start_time;
bool button_is_out = true;
int button_count = 0;
int button_max_count = 500;

// the setup function runs once when you press reset or power the board
void setup() {
  // initialize digital pin LED_BUILTIN as an output.
  pinMode(7, OUTPUT);
  pinMode(5, INPUT);      // Set up pin #5 as an input -- MUST BE INPUT
  digitalWrite(5, HIGH);  // Next, activate an internal 'pull-up' resistor
  start_time = millis();
 my_servo.attach(9);
}

// the loop function runs over and over again forever
void loop() {
  unsigned long current_time;

current_time = millis();

unsigned long elapsed_time;

elapsed_time = current_time - start_time;

int delay_amount = short_delay ? 300 : 1500;

  if (elapsed_time < delay_amount)
  {
    digitalWrite(7, LOW);    // turn the LED off by making the voltage LOW
    my_servo.write(160);  // Go to 160 degrees
  }
  else 
  {
    digitalWrite(7, HIGH);   // turn the LED on (HIGH is the voltage level)
    my_servo.write(20); // Go to 20 degrees
  }
  if (elapsed_time > delay_amount * 2)
    start_time = current_time;
 
  bool button_is_out;

if (last_button_was_out && !button_is_out)  // Button has just been pressed

  short_delay = ! short_delay;              // Reverse the short delay Boolean

last_button_was_out = button_is_out;        // Record the button state for use next time

if (digitalRead(5) == HIGH && button_count > 0)
{
    button_count--;
    if (button_count == 0)
       button_is_out = true;
}
else if (digitalRead(5) == LOW && button_count < button_max_count)
{
    button_count++;
    if (button_count == button_max_count)
       button_is_out = false;
}
 
}
