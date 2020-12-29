/*
 * This program detects overflow.
 * When two numbers are added and the answer overflows, the output's sign is 
 * reversed. (It's negative)
 * Other explanations are in the code.
 *
 * by Kyle Kazemini
 * u1127157
 * August 27, 2019
 */

#include <iostream>
#include <cmath>

int main ()
{

  int x = 3594895912;
  int y = 3290570484;

  int overflow1 = x+y;
  std::cout << "Addition output is: " << overflow1 << std::endl;

  int u = 3085409801;
  int v = 3405986054;

  int overflow2 = x*y;
  std::cout << "Multiplication output is: " << overflow2 << std::endl;
  //This same method demonstrates that detecting overflow
  //for multiplication is more difficult.

  //To fix this, we can try to use a conditional statement.

  int a = 3465968762;
  int b = 3456874593;

  if (a*b > 230454564 && a > 230454564/b)
    std::cout << "a*b has an overflow error.";
}
