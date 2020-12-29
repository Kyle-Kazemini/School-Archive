#include <string>


/* Fraction
 *
 * A fraction object represents a single rational number (a fraction).
 * If a fraction is negative, the numerator will be negative.  A
 * fraction object should not have a non-positive denominator. Also,
 * fraction objects are always stored using their most reduced
 * representation.
 *
 * The user of a fraction object is not allowed to change the numerator
 * or denominator of a fraction object.
 *
 * Additional fraction objects can be formed by multiplying or adding
 * a fraction object with another fraction object.
 *
 * Fractions can also be converted to strings or doubles.
 */
class Fraction
{
  private:
    long long numerator;
    long long denominator;
    void reduce ();
  
  public:
    Fraction (); 
    Fraction (long long _numerator, long long _denominator);
    std::string to_string ();
    double to_double ();
    Fraction add (Fraction right);
    Fraction multiply (Fraction right);
    Fraction subtract (Fraction right);
    Fraction divide (Fraction right);
    Fraction power (int x);
};
