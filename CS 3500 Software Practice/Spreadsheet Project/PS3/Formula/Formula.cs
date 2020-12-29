// Skeleton written by Joe Zachary for CS 3500, September 2013
// Read the entire skeleton carefully and completely before you
// do anything else!

// Version 1.1 (9/22/13 11:45 a.m.)

// Change log:
//  (Version 1.1) Repaired mistake in GetTokens
//  (Version 1.1) Changed specification of second constructor to
//                clarify description of how validation works

// (Daniel Kopta) 
// Version 1.2 (9/10/17) 

// Change log:
//  (Version 1.2) Changed the definition of equality with regards
//                to numeric tokens


using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Xml.Serialization;

namespace SpreadsheetUtilities
{
    /// <summary>
    /// Represents formulas written in standard infix notation using standard precedence
    /// rules.  The allowed symbols are non-negative numbers written using double-precision 
    /// floating-point syntax (without unary preceeding '-' or '+'); 
    /// variables that consist of a letter or underscore followed by 
    /// zero or more letters, underscores, or digits; parentheses; and the four operator 
    /// symbols +, -, *, and /.  
    /// 
    /// Spaces are significant only insofar that they delimit tokens.  For example, "xy" is
    /// a single variable, "x y" consists of two variables "x" and y; "x23" is a single variable; 
    /// and "x 23" consists of a variable "x" and a number "23".
    /// 
    /// Associated with every formula are two delegates:  a normalizer and a validator.  The
    /// normalizer is used to convert variables into a canonical form, and the validator is used
    /// to add extra restrictions on the validity of a variable (beyond the standard requirement 
    /// that it consist of a letter or underscore followed by zero or more letters, underscores,
    /// or digits.)  Their use is described in detail in the constructor and method comments.
    /// </summary>
    public class Formula
    {
        // Holds tokens
        private readonly List<string> tokens;

        // Holds normalized variables
        private readonly List<string> normalizedVars;

        // String representation of the formula
        private readonly string str;

        /// <summary>
        /// Creates a Formula from a string that consists of an infix expression written as
        /// described in the class comment.  If the expression is syntactically invalid,
        /// throws a FormulaFormatException with an explanatory Message.
        /// 
        /// The associated normalizer is the identity function, and the associated validator
        /// maps every string to true.  
        /// </summary>
        public Formula(String formula) :
            this(formula, s => s, s => true)
        {
        }

        /// <summary>
        /// Creates a Formula from a string that consists of an infix expression written as
        /// described in the class comment.  If the expression is syntactically incorrect,
        /// throws a FormulaFormatException with an explanatory Message.
        /// 
        /// The associated normalizer and validator are the second and third parameters,
        /// respectively.  
        /// 
        /// If the formula contains a variable v such that normalize(v) is not a legal variable, 
        /// throws a FormulaFormatException with an explanatory message. 
        /// 
        /// If the formula contains a variable v such that isValid(normalize(v)) is false,
        /// throws a FormulaFormatException with an explanatory message.
        /// 
        /// Suppose that N is a method that converts all the letters in a string to upper case, and
        /// that V is a method that returns true only if a string consists of one letter followed
        /// by one digit.  Then:
        /// 
        /// new Formula("x2+y3", N, V) should succeed
        /// new Formula("x+y3", N, V) should throw an exception, since V(N("x")) is false
        /// new Formula("2x+y3", N, V) should throw an exception, since "2x+y3" is syntactically incorrect.
        /// </summary>
        public Formula(String formula, Func<string, string> normalize, Func<string, bool> isValid)
        {
            str = formula;
            tokens = new List<string>(GetTokens(formula));
            normalizedVars = new List<string>();

            // One token rule
            if (tokens.Count <= 0 || tokens.Equals(null))
                throw new FormulaFormatException("The formula is empty or null.");

            // Starting token rule
            if (!(tokens.ElementAt(0).Equals("(") || IsVariable(tokens.ElementAt(0)) || IsNumber(tokens.ElementAt(0))))
                throw new FormulaFormatException("The first thing in the formula must be a variable, a number, or an opening parentheses.");

            // Ending token rule
            if (!(tokens.ElementAt(tokens.Count - 1).Equals(")") || IsVariable(tokens.ElementAt(tokens.Count - 1))
                  || IsNumber(tokens.ElementAt(tokens.Count - 1))))
                throw new FormulaFormatException("The last thing in the formula must be a variable, a number, or a closing parentheses.");


            int openingParenth = 0;
            int closingParenth = 0;

            for (int i = 0; i < tokens.Count; i++)
            {
                if (IsVariable(tokens.ElementAt(i)))
                {
                    if (!IsVariable(normalize(tokens.ElementAt(i))))
                        throw new FormulaFormatException("The normalized variable is not valid.");

                    if (!isValid(normalize(tokens.ElementAt(i))))
                        throw new FormulaFormatException("The validated and normalized variable is not valid.");

                    normalizedVars.Add(normalize(tokens.ElementAt(i)));
                }

                // Right parentheses rule
                if (closingParenth > openingParenth)
                    throw new FormulaFormatException("The number of closing parentheses must be less than or equal to the number of opening parentheses.");

                // Parsing rule
                if (!IsValidToken(tokens.ElementAt(i)))
                    throw new FormulaFormatException("There is an invalid token in the formula.");

                // Opening parenthesis rule
                if (tokens.ElementAt(i).Equals("("))
                {
                    openingParenth++;

                    if (i + 1 < tokens.Count)
                    {
                        if (!(tokens.ElementAt(i + 1).Equals("(") || tokens.ElementAt(i + 1).Equals("(") ||
                            IsNumber(tokens.ElementAt(i + 1)) || IsVariable(tokens.ElementAt(i + 1))))
                            throw new FormulaFormatException("Invalid token following an opening parenthesis.");
                    }
                    else
                        throw new FormulaFormatException("The formula is missing a closing parenthesis.");
                }

                // Operator following rule
                if (IsOperator(tokens.ElementAt(i)))
                {
                    if (i + 1 < tokens.Count)
                    {
                        if (!(tokens.ElementAt(i + 1).Equals("(") ||
                            IsNumber(tokens.ElementAt(i + 1)) || IsVariable(tokens.ElementAt(i + 1))))
                            throw new FormulaFormatException("Invalid token following an operator.");
                    }
                    else
                        throw new FormulaFormatException("The formula is missing a closing parenthesis.");
                }

                // Extra following rule for parenthesis
                if (tokens.ElementAt(i).Equals(")"))
                {
                    closingParenth++;

                    if (i + 1 < tokens.Count)
                    {
                        if (!(tokens.ElementAt(i + 1).Equals(")") || IsOperator(tokens.ElementAt(i + 1))))
                            throw new FormulaFormatException("Invalid token following a number, variable, or closing parenthesis");
                    }
                }

                // Extra following rule
                if (IsNumber(tokens.ElementAt(i)) || IsVariable(tokens.ElementAt(i)))
                {
                    if (i + 1 < tokens.Count)
                    {
                        if (!(tokens.ElementAt(i + 1).Equals(")") || IsOperator(tokens.ElementAt(i + 1)) /*|| tokens.ElementAt(i + 1).Equals("e")*/))
                            throw new FormulaFormatException("Invalid token following a number, variable, or closing parenthesis");
                    }
                }
            }

            // Balanced parentheses rule
            if (openingParenth != closingParenth)
                throw new FormulaFormatException(
                    "The number of opening and closing parentheses must be the same.");
        }

        /// <summary>
        /// Evaluates this Formula, using the lookup delegate to determine the values of
        /// variables.  When a variable symbol v needs to be determined, it should be looked up
        /// via lookup(normalize(v)). (Here, normalize is the normalizer that was passed to 
        /// the constructor.)
        /// 
        /// For example, if L("x") is 2, L("X") is 4, and N is a method that converts all the letters 
        /// in a string to upper case:
        /// 
        /// new Formula("x+7", N, s => true).Evaluate(L) is 11
        /// new Formula("x+7").Evaluate(L) is 9
        /// 
        /// Given a variable symbol as its parameter, lookup returns the variable's value 
        /// (if it has one) or throws an ArgumentException (otherwise).
        /// 
        /// If no undefined variables or divisions by zero are encountered when evaluating 
        /// this Formula, the value is returned.  Otherwise, a FormulaError is returned.  
        /// The Reason property of the FormulaError should have a meaningful explanation.
        ///
        /// This method should never throw an exception.
        /// </summary>
        public object Evaluate(Func<string, double> lookup)
        {
            Stack<double> values = new Stack<double>();
            Stack<string> operators = new Stack<string>();

            for (int i = 0; i < tokens.Count; i++)
            {
                if (String.IsNullOrWhiteSpace(tokens.ElementAt(i)))
                    continue;

                // If tokens.ElementAt(i) is an integer.
                if (Double.TryParse(tokens.ElementAt(i), out double result))
                {
                    if (IsMultOrDiv(operators))
                    {
                        values.Push(result);
                        try
                        {
                            DoMultOrDiv(operators, values);
                        }
                        catch (ArgumentException e) { return new FormulaError(e.Message); }
                    }
                    else if (!IsMultOrDiv(operators))
                        values.Push(result);

                    else
                        return new FormulaError("Invalid infix expression.");

                }

                // If tokens.ElementAt(i) is a variable. 
                else if (IsVariable(tokens.ElementAt(i)))
                {
                    try
                    {
                        result = lookup(tokens.ElementAt(i));
                    }
                    catch { return new FormulaError("Lookup did not return a variable."); }

                    if (IsMultOrDiv(operators) && values.Count >= 2 && operators.Count < 2)
                    {
                        try
                        {
                            DoMultOrDiv(operators, values);
                        }
                        catch (ArgumentException e) { return new FormulaError(e.Message); }
                    }
                    else
                        values.Push(result);
                }

                // If tokens.ElementAt(i) is a "+" or "-".
                else if (tokens.ElementAt(i).Equals("+") || tokens.ElementAt(i).Equals("-"))
                {
                    if (IsAddOrSub(operators) && values.Count >= 2)
                    {
                        try
                        {
                            DoAddOrSub(operators, values);
                        }
                        catch (ArgumentException e) { return new FormulaError(e.Message); }

                        operators.Push(tokens.ElementAt(i));
                    }
                    else
                        operators.Push(tokens.ElementAt(i));

                }

                // If tokens.ElementAt(i) is a "*" or "/".
                else if (tokens.ElementAt(i).Equals("*") || tokens.ElementAt(i).Equals("/"))
                {
                    if (IsMultOrDiv(operators) && values.Count >= 2)
                    {
                        try
                        {
                            DoMultOrDiv(operators, values);
                        }
                        catch (ArgumentException e) { return new FormulaError(e.Message); }

                        operators.Push(tokens.ElementAt(i));
                    }
                    else
                        operators.Push(tokens.ElementAt(i));
                }

                // If tokens.ElementAt(i) is "(".
                else if (tokens.ElementAt(i).Equals("("))
                {
                    operators.Push(tokens.ElementAt(i));
                }

                // If tokens.ElementAt(i) is ")".
                else if (tokens.ElementAt(i).Equals(")"))
                {
                    if (IsAddOrSub(operators) && values.Count >= 2)
                    {
                        try
                        {
                            DoAddOrSub(operators, values);
                        }
                        catch (ArgumentException e) { return new FormulaError(e.Message); }
                    }

                    else if (IsAddOrSub(operators))
                        return new FormulaError("Invalid infix expression");

                    else if (i < tokens.Count - 1 && Double.TryParse(tokens.ElementAt(i + 1), out _))
                        return new FormulaError("Invalid infix expression.");

                    if (operators.Count > 0 && operators.Peek().Equals("("))
                        operators.Pop();

                    else
                        return new FormulaError("Invalid infix expression.");


                    if (IsMultOrDiv(operators) && values.Count >= 2)
                    {
                        try
                        {
                            DoMultOrDiv(operators, values);
                        }
                        catch (ArgumentException e) { return new FormulaError(e.Message); }
                    }

                    else if (IsMultOrDiv(operators))
                        return new FormulaError("Invalid infix expression.");

                }
            }

            // Check for final operations.
            while (operators.Count != 0 && values.Count > 1)
            {
                if (IsAddOrSub(operators))
                {
                    try
                    {
                        DoAddOrSub(operators, values);
                    }
                    catch (ArgumentException e) { return new FormulaError(e.Message); }
                }
                if (IsMultOrDiv(operators))
                {
                    try
                    {
                        DoMultOrDiv(operators, values);
                    }
                    catch (ArgumentException e) { return new FormulaError(e.Message); }
                }
            }

            if (operators.Count > 0)
                return new FormulaError("Invalid expression, extra operator(s).");

            return values.Pop();
        }

        /// <summary>
        /// Enumerates the normalized versions of all of the variables that occur in this 
        /// formula.  No normalization may appear more than once in the enumeration, even 
        /// if it appears more than once in this Formula.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        /// 
        /// new Formula("x+y*z", N, s => true).GetVariables() should enumerate "X", "Y", and "Z"
        /// new Formula("x+X*z", N, s => true).GetVariables() should enumerate "X" and "Z".
        /// new Formula("x+X*z").GetVariables() should enumerate "x", "X", and "z".
        /// </summary>
        public IEnumerable<String> GetVariables()
        {
            // Returns a copy
            return new HashSet<string>(normalizedVars);
        }

        /// <summary>
        /// Returns a string containing no spaces which, if passed to the Formula
        /// constructor, will produce a Formula f such that this.Equals(f).  All of the
        /// variables in the string should be normalized.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        /// 
        /// new Formula("x + y", N, s => true).ToString() should return "X+Y"
        /// new Formula("x + Y").ToString() should return "x+Y"
        /// </summary>
        public override string ToString()
        {
            return str;
        }

        /// <summary>
        /// If obj is null or obj is not a Formula, returns false.  Otherwise, reports
        /// whether or not this Formula and obj are equal.
        /// 
        /// Two Formulae are considered equal if they consist of the same tokens in the
        /// same order.  To determine token equality, all tokens are compared as strings 
        /// except for numeric tokens and variable tokens.
        /// Numeric tokens are considered equal if they are equal after being "normalized" 
        /// by C#'s standard conversion from string to double, then back to string. This 
        /// eliminates any inconsistencies due to limited floating point precision.
        /// Variable tokens are considered equal if their normalized forms are equal, as 
        /// defined by the provided normalizer.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        ///  
        /// new Formula("x1+y2", N, s => true).Equals(new Formula("X1  +  Y2")) is true
        /// new Formula("x1+y2").Equals(new Formula("X1+Y2")) is false
        /// new Formula("x1+y2").Equals(new Formula("y2+x1")) is false
        /// new Formula("2.0 + x7").Equals(new Formula("2.000 + x7")) is true
        /// </summary>
        public override bool Equals(object obj)
        {
            if (Object.Equals(obj, null) || !(obj is Formula))
                return false;

            Formula temp = obj as Formula;
            bool equal = false;
            int variablesSeen = 0;

            for (int i = 0; i < tokens.Count; i++)
            {
                if (IsVariable(tokens.ElementAt(i)))
                {
                    if (variablesSeen < normalizedVars.Count &&
                        (normalizedVars.ElementAt(variablesSeen).Equals(temp.normalizedVars.ElementAt(variablesSeen), StringComparison.InvariantCultureIgnoreCase)))
                    {
                        equal = true;
                        variablesSeen++;
                    }
                    else
                        equal = false;
                }
                else if (IsNumber(tokens.ElementAt(i)))
                {
                    Double.TryParse(tokens.ElementAt(i), out double thisResult);

                    // This gets the ToString() of obj, then gets the ith element of the string, then converts it from a char to a string.
                    Double.TryParse(temp.tokens.ElementAt(i), out double objResult);
                    equal = thisResult.ToString().Equals(objResult.ToString());
                }
                else
                {
                    equal = tokens.ElementAt(i).Equals(temp.tokens.ElementAt(i));
                }

                if (equal == false)
                    return false;
            }

            return equal;
        }

        /// <summary>
        /// Reports whether f1 == f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return true.  If one is
        /// null and one is not, this method should return false.
        /// </summary>
        public static bool operator ==(Formula f1, Formula f2)
        {
            if (Object.Equals(f1, null) && Object.Equals(f2, null))
                return true;

            if (Object.Equals(f1, null) || Object.Equals(f2, null))
                return false;

            return f1.Equals(f2);
        }

        /// <summary>
        /// Reports whether f1 != f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return false.  If one is
        /// null and one is not, this method should return true.
        /// </summary>
        public static bool operator !=(Formula f1, Formula f2)
        {
            if (Object.Equals(f1, null) && Object.Equals(f2, null))
                return true;

            if (Object.Equals(f1, null) || Object.Equals(f2, null))
                return false;

            return !f1.Equals(f2);
        }

        /// <summary>
        /// Returns a hash code for this Formula.  If f1.Equals(f2), then it must be the
        /// case that f1.GetHashCode() == f2.GetHashCode().  Ideally, the probability that two 
        /// randomly-generated unequal Formulae have the same hash code should be extremely small.
        /// </summary>
        public override int GetHashCode()
        {
            string copy = "";

            for (int i = 0; i < tokens.Count; i++)
            {
                if (Double.TryParse(tokens.ElementAt(i), out double result))
                    copy += result.ToString();
                else if (!String.IsNullOrWhiteSpace(tokens.ElementAt(i)))
                    copy += tokens.ElementAt(i);
            }

            return copy.GetHashCode();
        }

        /// <summary>
        /// Given an expression, enumerates the tokens that compose it.  Tokens are left paren;
        /// right paren; one of the four operator symbols; a string consisting of a letter or underscore
        /// followed by zero or more letters, digits, or underscores; a double literal; and anything that doesn't
        /// match one of those patterns.  There are no empty tokens, and no token contains white space.
        /// </summary>
        private static IEnumerable<string> GetTokens(String formula)
        {
            // Patterns for individual tokens
            String lpPattern = @"\(";
            String rpPattern = @"\)";
            String opPattern = @"[\+\-*/]";
            String varPattern = @"[a-zA-Z_](?:[a-zA-Z_]|\d)*";

            // This double pattern isn't the same as the original. It was built through trial and error while debugging.
            //String doublePattern = @"[\d]*\.?[\d]+(?: [Ee]\d*)?";
            //String doublePattern = @"[\d]*\.?[\d]+(e[-+][\d{0,2}])?";
            //String doublePattern = @"[\d.]+e[-+]?\d+";
            String doublePattern = @"[\d]*\.?[\d]+e[-+]?\d+";
            String spacePattern = @"\s +";

            // Overall pattern
            String pattern = String.Format("({0}) | ({1}) | ({2}) | ({3}) | ({4}) | ({5})",
                                            lpPattern, rpPattern, opPattern, varPattern, doublePattern, spacePattern);

            // Enumerate matching tokens that don't consist solely of white space.
            foreach (String s in Regex.Split(formula, pattern, RegexOptions.IgnorePatternWhitespace))
            {
                if (!Regex.IsMatch(s, @"^\s*$", RegexOptions.Singleline))
                {
                    yield return s;
                }
            }
        }

        // Helper methods for Evaluate method.

        /// <summary>
        /// Extension method for determining if the top of a stack is
        /// a multiply or divide operator.
        /// </summary>
        /// <param name="stack"></param>
        /// <param name="s"></param>
        /// <returns></returns>
        private static bool IsMultOrDiv(Stack<string> stack)
        {
            if (stack.Count == 0)
                return false;

            if (stack.Peek().Equals("*") || stack.Peek().Equals("/"))
                return true;

            return false;
        }


        /// <summary>
        /// Extension method for logic of doing a multiply or divide operation.
        /// </summary>
        /// <param name="op"></param>
        /// <param name="val"></param>
        /// <param name="result"></param>
        private static void DoMultOrDiv(Stack<string> op, Stack<double> val)
        {
            if (op.Count > 0 && op.Peek().Equals("*"))
            {
                if (val.Count == 0)
                    throw new ArgumentException("Invalid infix expression.");

                else
                {
                    op.Pop();
                    val.Push(val.Pop() * val.Pop());
                }
                return;
            }
            else if (op.Count > 0 && val.Count > 0)
            {
                op.Pop();
                double temp1 = val.Pop();
                double temp2 = val.Pop();

                if (!temp1.Equals(0))
                {

                    val.Push(temp2 / temp1);
                    return;
                }
                else
                    throw new ArgumentException("Can't divide by 0.");
            }
        }


        /// <summary>
        /// Extension method for determining if the top of a stack 
        /// is an addition or subtraction operator.
        /// </summary>
        /// <param name="stack"></param>
        /// <param name="s"></param>
        /// <returns></returns>
        private static bool IsAddOrSub(Stack<string> stack)
        {
            if (stack.Count == 0)
                return false;

            if (stack.Peek().Equals("+") || stack.Peek().Equals("-"))
                return true;

            return false;
        }


        /// <summary>
        /// Extension method for logic of doing an addition or subtraction operation.
        /// </summary>
        /// <param name="op"></param>
        /// <param name="val"></param>
        private static void DoAddOrSub(Stack<string> op, Stack<double> val)
        {
            if (op.Count > 0 && op.Peek().Equals("+"))
            {
                if (val.Count == 0)
                    throw new ArgumentException("Invalid infix expression.");

                else if (val.Count >= 2)
                {
                    op.Pop();
                    val.Push(val.Pop() + val.Pop());
                }
            }
            else if (op.Count > 0 && val.Count >= 2)
            {
                op.Pop();
                double temp1 = val.Pop();
                double temp2 = val.Pop();

                val.Push(temp2 - temp1);
            }
        }


        // Helper methods for error checking in the constructors.

        /// <summary>
        /// Helper method for determining if a string is a valid token.
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        private static bool IsValidToken(string s)
        {
            if (IsNumber(s) || IsSymbol(s) || IsVariable(s))
                return true;

            return false;
        }


        /// <summary>
        /// Helper method for determining if a string is a valid symbol.
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        private static bool IsSymbol(string s)
        {
            // These patterns are taken from the given GetTokens() method

            String lpPattern = @"\(";
            String rpPattern = @"\)";
            String opPattern = @"[\+\-*/]";
            String spacePattern = @"\s+";

            if (Regex.IsMatch(s, lpPattern) || Regex.IsMatch(s, rpPattern)
                || Regex.IsMatch(s, opPattern) || Regex.IsMatch(s, spacePattern))
                return true;

            return false;

        }


        /// <summary>
        /// Helper method for determining if a string is a valid operator.
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        private static bool IsOperator(string s)
        {
            // This pattern is taken from the given GetTokens() method
            if (Regex.IsMatch(s, @"^[\+\-*/]$"))
                return true;

            return false;
        }


        /// <summary>
        /// Helper method for determining if a string is a valid number.
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        private static bool IsNumber(string s)
        {
            if (Regex.IsMatch(s, @"[\d]*\.?[\d]+(e[-+][\d]+)?"))
                return true;

            return false;
        }


        /// <summary>
        /// Helper method for determining if a string is a valid variable.
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        private static bool IsVariable(string s)
        {
            // This pattern is taken from the given GetTokens() method
            if (Regex.IsMatch(s, @"^[a-zA-Z_$][a-zA-Z_$0-9]*$"))
                return true;

            return false;
        }

    }

    /// <summary>
    /// Used to report syntactic errors in the argument to the Formula constructor.
    /// </summary>
    public class FormulaFormatException : Exception
    {
        /// <summary>
        /// Constructs a FormulaFormatException containing the explanatory message.
        /// </summary>
        public FormulaFormatException(String message)
            : base(message)
        {
        }
    }

    /// <summary>
    /// Used as a possible return value of the Formula.Evaluate method.
    /// </summary>
    public struct FormulaError
    {
        /// <summary>
        /// Constructs a FormulaError containing the explanatory reason.
        /// </summary>
        /// <param name="reason"></param>
        public FormulaError(String reason)
            : this()
        {
            Reason = reason;
        }

        /// <summary>
        ///  The reason why this FormulaError was created.
        /// </summary>
        public string Reason { get; private set; }
    }
}

