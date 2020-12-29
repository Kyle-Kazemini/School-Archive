#include <string>
#include <sstream>
#include "Fraction.h"

/* Fraction constructor:
 *
 * This default constructor creates the fraction
 * representing the number 0.
 *
 * (Notice that no return type is specified.  The
 * function name is the class name for a constructor.)
 */
Fraction::Fraction ()
{
  // Set this object's variables to represent 0.
  
  numerator = 0;
  denominator = 1;
}

/* Fraction constructor:
 *
 * This constructor creates the fraction
 * representing the number specified by the numerator
 * and denominator parameters, i.e. numerator/denominator.
 *
 * The denominator is not allowed to be 0.  (No error
 * checking is done -- you haven't learned exceptions yet.)
 *
 * (Notice that no return type is specified.  The
 * function name is the class name for a constructor.)
 *
 * Parameters:
 *   long long _numerator -- some numerator
 *   long long _denominator -- some denominator
 */
Fraction::Fraction (long long _numerator, long long _denominator)
{
  // Copy the parameter values into this object's variables.
  
  numerator = _numerator;
  denominator = _denominator;

  // Ensure the denominator is not negative.

  // (Students will add statements here for the assignment)

  // Reduce the fraction (as required by the class contract).
  //   We'll just use our private function to help us.
  
  reduce();
}

/* This function reduces this fraction object.  It first
 * computes the greatest common divisor of the numerator
 * and denominator.  Then, it divides both the numerator
 * and denominator by that greatest common divisor.
 */


void Fraction::reduce ()
{
  // Compute the greatest common divisor using a
  //  well-known algorithm.

  long long gcd = numerator;
  long long remainder = denominator;

  while (remainder != 0)
  {
    long long temp = remainder;
    remainder = gcd % remainder;
    gcd = temp;
  }

  // Divide both the numerator and denominator by the
  //   greatest common divisor

  numerator /= gcd;
  denominator /= gcd;
}


/* Returns a string that contains text representing
 * this fraction in the form "numerator/denominator".
 *
 * Returns:
 *   a string -- the fraction converted to a string
 */
std::string Fraction::to_string ()
{
  std::stringstream buffer;
  buffer << numerator << "/" << denominator;

  return buffer.str();
}

/* Returns a double value that is the approximation
 * of this fraction object.
 *
 * Returns:
 *   a double -- a floating point approximation of
 *               the number represented by this Fraction
 */
double Fraction::to_double ()
{
  return numerator/(double)denominator;
}


/* Adds this fraction object to the provided fraction
 * object (without changing this object).  The sum
 * is returned in a separate fraction object.
 *
 * Parameters:
 *   Fraction right -- some other fraction object
 *
 * Returns:
 *   A Fraction object -- represents the value equal to
 *                        this object + right object.
 */

Fraction Fraction::add (Fraction right)
{
  // Compute the sum
  
  long long sum_numerator, sum_denominator;
  
  sum_numerator = numerator * right.denominator +
                  right.numerator * denominator;
  
  sum_denominator = denominator * right.denominator;

  // Create the resulting fraction.
  
  Fraction result(sum_numerator, sum_denominator);

  // Return it.
  
  return result;
}

/* Multiplies this fraction object with the provided fraction
 * object (without changing this object).  The product
 * is returned in a separate fraction object.
 *
 * Parameters:
 *   Fraction right -- some other fraction object
 *
 * Returns:
 *   A Fraction object -- represents the value equal to
 *                        this object * right object.
 */

Fraction Fraction::multiply (Fraction right)
{
  // Compute the product
  
  long long product_numerator, product_denominator;
  
  product_numerator   = numerator   * right.numerator;
  product_denominator = denominator * right.denominator;

  // Create the resulting fraction.
  
  Fraction result(product_numerator, product_denominator);

  // Return it.
  
  return result;
}

/*
 * Subtracts this fraction object with the provided fraction object.
 * 
 * Parameters: some other fraction
 * 
 * Returns: a fraction object
 */
Fraction Fraction::subtract (Fraction right)
{
  
  long long sub_numerator, sub_denominator;
  
  sub_numerator = numerator * right.denominator -
                  right.numerator * denominator;
  
  sub_denominator = denominator * right.denominator;

  // Create the resulting fraction.
  
  Fraction result(sub_numerator, sub_denominator);

  // Return it.
  
  return result;
}

/*
 * Divides this fraction object with the provided fraction object.
 * 
 * Parameters: some other fraction
 * 
 * Returns: a fraction object
 */

Fraction Fraction::divide (Fraction right)
{
  
  long long division_numerator, division_denominator;
  
  division_numerator   = numerator   * right.denominator;
  division_denominator = denominator * right.numerator;

  // Create the resulting fraction.
  
  Fraction result(division_numerator, division_denominator);

  // Return it.
  
  return result;
}

/*
 * Raises the fraction to the power of x
 * 
 * Parameters: x, the power you want to raise the fraction to
 * 
 * Returns: a fraction object
 */

Fraction Fraction::power (int x)
{
  
  long long power_numerator, power_denominator;
  power_numerator = numerator;
  power_denominator = denominator;

  if (x == 0)
  {
    Fraction result(1,1);
    return result;
  }

  int count = 0;
  while (count < (x - 1))
  {
    power_numerator = power_numerator * numerator;
    power_denominator = power_denominator * denominator;
    count ++;
  }

  if (x > 0)
  {
    Fraction result(power_numerator, power_denominator);
    return result;
  }

  else
  {
    Fraction result(power_denominator, power_numerator);
    return result;
  }
}
