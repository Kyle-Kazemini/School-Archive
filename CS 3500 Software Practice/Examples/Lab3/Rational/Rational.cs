using System;

namespace Lab3
{
  /// <summary>
  /// Provides rational numbers that can be expressed as ratios
  /// of 32-bit integers.
  /// </summary>
  public class Rational
  {
    // Representation invariant:
    //  den > 0
    //  gcd(|num|, den) = 1
    // in other words, the rational number is always stored simplified
    private int num;
    private int den;

    /// <summary>
    /// Creates 0
    /// </summary>
    public Rational()
        : this(0, 1)      // This invokes the 2-argument constructor
    {
    }

    /// <summary>
    /// Creates n
    /// </summary>
    public Rational(int n)
        : this(n, 1)      // This invokes the 2-argument constructor
    {
    }


    // Note the use of the extension method Gcd below.  It works because
    // the of the extension class below.

    /// <summary>
    /// Creates n/d.
    /// </summary>
    public Rational(int n, int d)
    {
      if (d == 0)
      {
        throw new ArgumentException("Zero denominator not allowed");
      }
      int g = n.Gcd(d);
      if (d > 0)
      {
        num = n / g;
        den = d / g;
      }
      else
      {
        num = -n / g;
        den = -d / g;
      }
    }


    // This method overloads the + operator so that
    // we can write expressions adding two Rationals 
    // together as in r1 + r2;
    // Also note the use of the "checked" block. This
    // causes a runtime exception if integer arithmetic
    // overflows within that block (rather than just 
    // letting the overflow happen).

    /// <summary>
    /// Returns the sum of r1 and r2.
    /// </summary>
    public static Rational operator +(Rational r1, Rational r2)
    {
      checked
      {
        return new Rational(r1.num * r2.den + r1.den * r2.num,
                       r1.den * r2.den);
      }
    }

    // Note the use of the override keyword, required if you want to
    // override an inherited method.
    // In this case, we are overriding Object's ToString.

    /// <summary>
    /// Returns a standard string representation of a rational number
    /// </summary
    public override string ToString()
    {
      if (den == 1)
      {
        return num.ToString();
      }
      else
      {
        return num + "/" + den;
      }
    }


    /// <summary>
    /// Reports whether this and o are the same rational number.
    /// </summary>
    public override bool Equals(object o)
    {
      // Cast o to be a Rational.  If the cast fails, we get null back.
      Rational r = o as Rational;

      // Make sure r is non-null and its numerator and denominator
      // the same as those of this.
      return
          !ReferenceEquals(r, null) &&
          this.num == r.num &&
          this.den == r.den;
    }

    /// <summary>
    /// Overload the equality operator
    /// </summary>
    public static bool operator ==(Rational r1, Rational r2)
    {
      if (ReferenceEquals(r1, null))
      {
        return ReferenceEquals(r2, null);
      }
      else
      {
        return r1.Equals(r2);
      }
    }

    /// <summary>
    /// Overload the inequality operator
    /// </summary>
    public static bool operator !=(Rational r1, Rational r2)
    {
      return !(r1 == r2);
    }

    /// <summary>
    /// Returns a hash code for this Rational.
    /// </summary>
    /// <returns></returns>
    public override int GetHashCode()
    {
      return num ^ den;
    }
  }


  /// <summary>
  /// Extention method for the basic int type.
  /// This lets us find the GCD of an int x by simply: x.Gcd(...)
  /// </summary>
  public static class RationalExtentions
  {
    /// <summary>
    /// Returns the GCD of "this" and b. 
    /// </summary>
    public static int Gcd(this int a, int b)
    {
      a = Math.Abs(a);
      b = Math.Abs(b);
      while (b > 0)
      {
        int temp = a % b;
        a = b;
        b = temp;
      }
      return a;
    }

  }

}
