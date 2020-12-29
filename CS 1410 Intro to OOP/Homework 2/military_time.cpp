/*
 * This program converts military time to standard time.
 * 
 * by Kyle Kazemini
 * u1127157
 * September 1, 2019
 */

#include <iostream>
#include <cmath>

int main ()
{

  std::string military;
  std::cout << "This program converts military time to standard time." << std::endl;
  std::cout << "Enter a time in the format 00:00: ";
  std::cin >> military;
  
  char ch0 = military[0];
  char ch1 = military[1];
  char ch3 = military[3];
  char ch4 = military[4];
  
  int value_1 = ch0 - '0';
  int value_2 = ch1 - '0';
  int value_3 = ch3 - '0';
  int value_4 = ch4 - '0';

  int hours = 10*value_1 + value_2;
  int minutes = 10*value_3 + value_4;
 

  if (hours >= 13 && hours <= 23)
    {
      hours = hours-12;
      std::cout << "Military time " << military << " is " << hours << ":" << minutes << "PM.";
      return 0;
    }
  else if (hours == 00)
    {
      std::cout << " Military time " << military << " is " << "12:" << minutes << "AM.";
      return 0;
    }
  else if (hours >= 1 && hours <= 11)
    {
      std::cout << "Military time " << military << " is " << hours << ":" << minutes << "AM.";
      return 0;
    }
  else if (hours == 12)
    {
      std::cout << "Military time " << military << " is " << hours << ":" << minutes << "PM.";
      return 0;
    }

  else
    return 0;

  }
