/*
 * This program computes the surface area of a rectangular solid
 * with lengths given by user input
 * 
 * by Kyle Kazemini
 * u1127157
 * August 26, 2019
*/

#include <iostream>
#include <cmath>

int main ()
{

double length;
double width;
double depth;
double surfaceArea;

 std::cout << "This program computes the surface area of a rectangular solid." << std::endl;
  std::cout << " Enter a length value (in inches): ";
  std::cin >> length;
  std::cout << "Enter a width value (in inches): ";
  std::cin >> width;
  std::cout << "Enter a depth value (in inches): ";
  std::cin >> depth;

surfaceArea = 2*(length*width + length*depth + depth*width);

  std::cout << "The surface area is " << surfaceArea;

  }
