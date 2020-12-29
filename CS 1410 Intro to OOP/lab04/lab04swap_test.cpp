// A simple swap test.
//
// Lab #4

#include <iostream>

using namespace std;

/* This function swaps the values in its parameters.
 *
 * Parameters:
 *   int a -- an integer
 *   int b -- an integer
 *
 * Return value:
 *   none:
 */
void swap_integers (int a, int b)
{
  int temp;
  temp = a;
  a = b;
  b = temp;
}

/* Our application entry point.
 */
int main ()
{
  int x = 15;
  int y = 4;

  cout << "x contains " << x << "." << endl;
  cout << "y contains " << y << "." << endl;

  // Your code goes here.
  
  void swap_integers (int x, int y);

}
