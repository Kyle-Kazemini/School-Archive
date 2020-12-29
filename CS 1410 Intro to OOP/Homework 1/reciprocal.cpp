/*
 * This program computes the reciprocal of a user input
 * 
 * by Kyle Kazemini
 * u1127157
 * August 24, 2019
*/


#include <iostream>
#include <cmath>

int main ()
{
  float number;
  std::cout << "This program changes your input into its reciprocal.";
  std::cout << std::endl << "Enter a value: ";
  std::cin >> number;
  number = 1/number;
  std::cout << "The reciprocal is: " << number;
  

  }
