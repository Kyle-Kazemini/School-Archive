/*
 * This program finds solutions to quadratic equations.
 * 
 * If the quadratic has no solutions, the program displays "nan" as the answer.
 * by Kyle Kazemini
 * u1127157
 * August 26, 2019
 */

#include <iostream>
#include <cmath>

int main ()
{

  double a,b,c,x1,x2,discriminant;

std::cout << "Enter a value for the first coefficient of the quadratic: ";
std::cin >> a;
std::cout << "Enter a value for the second coefficient: ";
std::cin >> b;
std::cout << "Enter a value for the third coefficent: ";
std::cin >> c;

 discriminant = (b*b) - (4*a*c);
 x1 = (-b + discriminant)/(2*a);
 x2 = (-b - discriminant)/(2*a);

 std::cout << "The solutions are " << x1 << " and " << x2 << std::endl;
 
}
