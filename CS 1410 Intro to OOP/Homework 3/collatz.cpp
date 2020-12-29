/*
 * This program computes the hailstone sequence for a user input.
 *
 * by Kyle Kazemini
 * u1127157
 * September 9, 2019
 */

#include <iostream>
#include <cmath>

using namespace std;

int main()
{
  int input;

  cout << "This program computes a hailstone sequence." << endl;
  cout << "Enter the starting number: " << endl;
  cin >> input;
  cout << endl << input << " ";

  while (input != 1)
  {
    if (input % 2 == 0)
    {
      input = input/2;
      cout << input << " ";
    }
    else
    {
      input = 3*input +1;
      cout << input << " ";
    }

  }


}
