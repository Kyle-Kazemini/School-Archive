/*
 * This program converts a temperature from Fahrenheit to Celsius
 * 
 * by Kyle Kazemini
 * u1127157
 * August 26, 2019
 */

#include <iostream>
#include <cmath>

int main ()
{

int fahrenheit, celsius;

std::cout << "Enter a temperature in degrees fahrenheit: ";
 std::cin >> fahrenheit;

 celsius = 5*(fahrenheit - 32)/9;

 std::cout << "The temperature in degrees celsius is: " << celsius << std::endl;

}
