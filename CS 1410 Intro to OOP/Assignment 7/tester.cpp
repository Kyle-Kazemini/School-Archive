#include <iostream>
#include "Fraction.h"

using namespace std;

int main ()
{
  cout << "Hello, working..." << endl;

  // Create a few fraction objects

  Fraction a(1, 2);
  Fraction b(1, 6);
  Fraction g(4, 9);
  Fraction h(3, 5);
  Fraction j(2, 11);
  
  // Create a fraction as the result of computation
  
  int x = 7;
  int y = x + 1;
  int x_two = 0;
  int x_three = -2;
  Fraction c(x, y);
  Fraction s(x, y);
  Fraction d(x, y);
  Fraction p(x, y);
  Fraction p_two(x_two, y);
  Fraction p_three(x_three, y);
  Fraction m_two(x, y);
  Fraction add_two(x, y);
  Fraction sub_two (x,y);

  // Print them out
  
  cout << "Fraction A is: " << a.to_string() << endl;
  cout << "Fraction B is: " << b.to_string() << endl;
  cout << "Fraction C is: " << c.to_string() << endl;

  // Perform a bit of computation with fractions
  
  c = a.add(b);

  cout << a.to_string() << " plus " << b.to_string()
       << " equals " << c.to_string() << endl;

  c = a.multiply(b);
  
  cout << a.to_string() << " multiplied by "
       << b.to_string() << " equals " << c.to_string()
       << endl;

  s = a.subtract(b);

  cout << a.to_string() << " subtracted by " 
       << b.to_string() << " equals " << s.to_string()
       << endl;

  d = a.divide(b);

  cout << a.to_string() << " divided by " 
       << b.to_string() << " equals " << d.to_string()
       << endl;

  p = a.power(x);

  cout << a.to_string() << " to the power of "
       << x << " equals " << p.to_string()
       << endl;
  
  p_two = g.power(x_two);

  cout << g.to_string() << " to the power of " 
       << x_two << " equals " << p_two.to_string()
       << endl;

  p_three = h.power(x_three);

  cout << h.to_string() << " to the power of " 
       << x_three << " equals " << p_three.to_string()
       << endl;

  m_two = h.multiply(j);

  cout << h.to_string() << " multiplied by " 
       << j.to_string() << " equals " << m_two.to_string()
       << endl;

  add_two = g.add(j);

  cout << g.to_string() << " plus " 
       << j.to_string() << " equals " << add_two.to_string()
       << endl;

  sub_two = a.subtract(j);

  cout << a.to_string() << " minus " 
       << j.to_string() << " equals " << sub_two.to_string()
       << endl;


  // Convert a fraction to a double
  
  double r = c.to_double();

  cout << "The real number closest to " << c.to_string()
       << " is " << r << endl;

  // Done.
}
