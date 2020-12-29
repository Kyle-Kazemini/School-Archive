#include <iostream>
#include <sstream>
#include "Fraction.h"

using namespace std;

int main ()
{

  Fraction total_sum(0, 1);

  int count = 0;

  for (int k = 0; k < 4; k++)
  {
  Fraction one(1, 16);
  Fraction one_power = one.power(k);
  Fraction two(4, 8*k+1);
  Fraction three(2, 8*k+4);
  Fraction four(1, 8*k+5);
  Fraction five(1, 8*k+6);


  Fraction inside = three.add(four);
  inside = inside.add(five);
  inside = two.subtract(inside);
  inside = inside.multiply(one_power);
  total_sum = inside.add(total_sum);

  count ++;
  }
   
  cout << "The total of the summation is " << total_sum.to_string()
       << ". " << endl;
  cout << "The maximum value of k is " << count << endl;
 
  double total_sum_two = 0;

  int count_two = 0;

  for (int k = 0; k < 4; k++)
  {
  Fraction one(1, 16);
  Fraction one_power = one.power(k);
  Fraction two(4, 8*k+1);
  Fraction three(2, 8*k+4);
  Fraction four(1, 8*k+5);
  Fraction five(1, 8*k+6);


  Fraction inside = three.add(four);
  inside = inside.add(five);
  inside = two.subtract(inside);
  inside = inside.multiply(one_power);
  total_sum_two += inside.to_double();
  
  count_two ++;
  }

  cout << "The total of the summation is " << total_sum_two
       << ". " << endl;
  cout << "The maximum value of k is " << count_two << endl;

}
