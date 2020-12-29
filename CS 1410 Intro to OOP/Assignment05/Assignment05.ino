
#define sensorPin  A0

void setup() 
{
 pinMode (7, OUTPUT); // First
 pinMode (8, OUTPUT); // Second
 pinMode (9, OUTPUT); // Third
 pinMode (10, OUTPUT); // Red

 Serial.begin(9600);

}

void loop() 
{
  int read = analogRead(sensorPin);
  float volts = read * 5.0;
  
  //Cite: This code is from youTube
  volts = volts / 1024.0;
  float celsius = 100 * (volts - 0.5);
  // End citation
 
  float fahrenheit = (9.0 / 5.0) * celsius + 32;

  Serial.print(" Volts - ");
  Serial.print(volts);
  Serial.print(" Degrees Celsius - ");
  Serial.print(celsius);
  Serial.print(" Degrees Fahrenheit - ");
  Serial.print(fahrenheit);
  Serial.print('\n');

  if (fahrenheit >= 75)
  {
    digitalWrite(7, LOW);
    digitalWrite(8, LOW);
    digitalWrite(9, LOW);
    digitalWrite(10, HIGH);
  }
  
  else if (fahrenheit >= 70 && fahrenheit < 75)
  {
    digitalWrite(7, LOW);
    digitalWrite(8, LOW);
    digitalWrite(9, HIGH);
    digitalWrite(10, LOW);
  }
  
else if (fahrenheit >= 60 && fahrenheit < 70)
  {
    digitalWrite(7, LOW);
    digitalWrite(8, HIGH);
    digitalWrite(9, LOW);
    digitalWrite(10, LOW);
  }

  else
  {
    digitalWrite(7, HIGH);
    digitalWrite(8, LOW);
    digitalWrite(9, LOW);
    digitalWrite(10, LOW);
  }
  
  delay(2000); 
}
