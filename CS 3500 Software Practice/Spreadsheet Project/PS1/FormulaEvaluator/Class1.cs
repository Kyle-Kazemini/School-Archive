using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace FormulaEvaluator
{
    /// <summary>
    /// Static class containing a method to evaluate infix expressions.
    /// </summary>
    /// <remarks>
    /// Author: Kyle Kazemini
    /// Date: August 31, 2020
    /// </remarks>
    public static class Evaluator
    {
        // This is a delegate that will be defined later in the Spreadsheet project.
        public delegate int Lookup(String v);


        /// <summary>
        /// Evaluates infix expressions.
        /// </summary>
        /// <param name="exp"></param>
        /// <param name="variableEvaluator"></param>
        /// <returns></returns>
        public static int Evaluate(String exp, Lookup variableEvaluator)
        {
            if (System.String.IsNullOrWhiteSpace(exp))
                throw new ArgumentException("Empty string.");

            Stack<int> values = new Stack<int>();
            Stack<string> operators = new Stack<string>();
            exp = exp.Trim();

            string[] substrings = Regex.Split(exp, "(\\()|(\\))|(-)|(\\+)|(\\*)|(/)");

            for (int i = 0; i < substrings.Length; i++)
            {
                // If substrings[i] is " " or "".
                if (substrings[i].Equals(" ") || substrings[i].Equals(""))
                    continue;

                // If substrings[i] is an integer.
                else if (Int32.TryParse(substrings[i], out int result))
                {
                    if (operators.IsMultOrDiv())
                    {
                        values.Push(result);
                        operators.DoMultOrDiv(values);
                    }
                    else if (!operators.IsMultOrDiv())
                        values.Push(result);

                    else
                        throw new ArgumentException("Invalid infix expression.");

                }

                // If substrings[i] is a variable. 
                else if (IsVar(substrings[i]))
                {
                    try
                    {
                        result = variableEvaluator(substrings[i]);
                    }
                    catch
                    {
                        throw new ArgumentException("This variable doesn't have a value.");
                    }

                    if (operators.IsMultOrDiv() && values.Count >= 2 && operators.Count < 2)
                        operators.DoMultOrDiv(values);

                    else
                        values.Push(result);
                }

                // If substrings[i] is a "+" or "-".
                else if (substrings[i].Equals("+") || substrings[i].Equals("-"))
                {
                    if (operators.IsAddOrSub() && values.Count >= 2)
                    {
                        operators.DoAddOrSub(values);
                        operators.Push(substrings[i]);
                    }
                    else
                        operators.Push(substrings[i]);

                }

                // If substrings[i] is a "*" or "/".
                else if (substrings[i].Equals("*") || substrings[i].Equals("/"))
                {
                    if (operators.IsMultOrDiv() && values.Count >= 2)
                    {
                        operators.DoMultOrDiv(values);
                        operators.Push(substrings[i]);
                    }
                    else
                        operators.Push(substrings[i]);
                }

                // If substrings[i] is "(".
                else if (substrings[i].Equals("("))
                {
                    operators.Push(substrings[i]);
                }

                // If substrings[i] is ")".
                else if (substrings[i].Equals(")"))
                {
                    if (operators.IsAddOrSub() && values.Count >= 2)
                        operators.DoAddOrSub(values);

                    else if (operators.IsAddOrSub())
                        throw new ArgumentException("Invalid infix expression");

                    else if (i < substrings.Length - 1 && Int32.TryParse(substrings[i + 1], out _))
                        throw new ArgumentException("Invalid infix expression.");

                    if (operators.Count > 0 && operators.Peek().Equals("("))
                        operators.Pop();

                    else
                        throw new ArgumentException("Invalid infix expression.");


                    if (operators.IsMultOrDiv() && values.Count >= 2)
                        operators.DoMultOrDiv(values);

                    else if (operators.IsMultOrDiv())
                        throw new ArgumentException("Invalid infix expression.");

                }
            }

            // Check for final operations.
            while (operators.Count != 0 && values.Count > 1)
            {
                if (operators.IsAddOrSub())
                    operators.DoAddOrSub(values);

                if (operators.IsMultOrDiv())
                    operators.DoMultOrDiv(values);
            }

            if (operators.Count > 0)
                throw new ArgumentException("Invalid expression, extra operator(s).");

            return values.Pop();
        }


        /// <summary>
        /// Determines if a string is a valid variable.
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        private static bool IsVar(string s)
        {
            bool foundLetter = false;
            bool foundDigit = false;
            int i;

            for (i = 0; i < s.Length - 1; i++)
            {
                if (Char.IsLetter(s[i]))
                {
                    foundLetter = true;

                    if (foundLetter == true && (s[i] == s[i + 1]))
                        throw new ArgumentException("Invalid variable.");
                }

            }

            for (; i < s.Length; i++)
            {
                if (Char.IsDigit(s[i]))
                    foundDigit = true;

                else
                    return false;
            }

            return (foundLetter && foundDigit);
        }


        /// <summary>
        /// Extension method for determining if the top of a stack is
        /// a multiply or divide operator.
        /// </summary>
        /// <param name="stack"></param>
        /// <param name="s"></param>
        /// <returns></returns>
        private static bool IsMultOrDiv(this Stack<string> stack)
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
        private static void DoMultOrDiv(this Stack<string> op, Stack<int> val)
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
            else if (op.Count > 0 && val.Count >= 2)
            {
                op.Pop();
                int temp1 = val.Pop();
                int temp2 = val.Pop();
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
        private static bool IsAddOrSub(this Stack<string> stack)
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
        private static void DoAddOrSub(this Stack<string> op, Stack<int> val)
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
                int temp1 = val.Pop();
                int temp2 = val.Pop();

                val.Push(temp2 - temp1);
            }
        }

    }
}
