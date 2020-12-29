/*
 * This program demonstrates inaccuracy of double variables.
 * 
 * by Kyle Kazemini
 * u1127157
 * September 3, 2019
 */

#include <iostream>
#include <cmath>
using namespace std;

int main ()
{

  double f = 1e17;

  f = f+1;

  double expected = 100000000000000001;
  double error = abs(f - expected);

  if (expected == f)
    {
      cout << fixed  << "The expected result was computed." << endl;
    }
  else
    cout << fixed <<  "They differ by " << error << endl;


  double g = 2;
  double h = 2.000000000000000000001;

  g = sqrt(g);
  h = sqrt(h);


  if (g == h)
    cout << "The results are the same. They should differ after six decimal places." << endl;
  else
    cout << "The results differ as they should." << endl;
  h *= h;
  if (h == 2)
    cout << "Rouding error";
  else
    cout << "Original.";

}
