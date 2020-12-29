using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpreadsheetUtilities;
using System;
using System.Collections.Generic;
using System.Numerics;

namespace FormulaTests
{

    /// <summary>
    /// Tests for the Formula class.
    /// </summary>
    /// <remarks>
    /// Kyle Kazemini
    /// September 17, 2020
    /// </remarks>

    [TestClass]
    public class FormulaTests
    {
        [TestMethod]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestFirstConstructorEmptyFormula()
        {
            Formula f = new Formula("");
        }

        [TestMethod]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestFirstConstructorEmptyFormula2()
        {
            Formula f = new Formula("        ");
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void TestFirstConstructorNullFormula()
        {
            Formula f = new Formula(null);
        }

        [TestMethod]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestFirstConstructorCorrectnessSmall()
        {
            Formula f = new Formula("/ 2");
        }

        [TestMethod]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestFirstConstructorCorrectnessSmall2()
        {
            Formula f = new Formula(" * ");
        }

        [TestMethod]
        public void TestFirstConstructorCorrectnessSmall3()
        {
            Formula f = new Formula("6");
        }

        [TestMethod]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestSecondConstructorEmptyFormula()
        {
            Formula f = new Formula("", s => s, s => true);
        }

        [TestMethod]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestSecondConstructorEmptyFormula2()
        {
            Formula f = new Formula("        ", s => s, s => true);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void TestSecondConstructorNullFormula()
        {
            Formula f = new Formula(null, s => s, s => true);
        }

        [TestMethod]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestSecondConstructorCorrectnessSmall()
        {
            Formula f = new Formula("10 /   ", s => s, s => true);
        }

        [TestMethod]
        [ExpectedException(typeof(FormulaFormatException))]
        public void TestSecondConstructorCorrectnessSmall2()
        {
            Formula f = new Formula("(", s => s, s => true);
        }

        [TestMethod]
        public void TestSecondConstructorCorrectnessSmall3()
        {
            Formula f = new Formula("1023", s => s, s => true);
        }

        [TestMethod]
        public void TestEvaluate()
        {
            Formula a = new Formula("21 + 6");

            Assert.AreEqual((double)27, a.Evaluate(s => 0));
        }

        [TestMethod]
        public void TestEvaluateDivideByZero()
        {
            Formula f = new Formula("2/0");
            Assert.IsInstanceOfType(f.Evaluate(s => 0), typeof(FormulaError));
        }

        [TestMethod]
        public void TestEvaluateDivideByZero2()
        {
            Formula f = new Formula("(9 + 17)/0");
            Assert.IsInstanceOfType(f.Evaluate(s => 0), typeof(FormulaError));
        }

        [TestMethod]
        public void TestEvaluateDivideByZero3()
        {
            Formula f = new Formula("932685734 / 0");
            Assert.IsInstanceOfType(f.Evaluate(s => 0), typeof(FormulaError));
        }

        [TestMethod]
        public void TestGetVariables()
        {
            Formula f = new Formula("3+2-10");
            Assert.AreNotSame(new HashSet<string>(), f.GetVariables());
        }

        [TestMethod]
        public void TestToString()
        {
            Formula a = new Formula("3*5");
            Formula b = new Formula("901 - 264 * 0.2");

            Assert.AreEqual("3*5", a.ToString());
            Assert.AreEqual("901 - 264 * 0.2", b.ToString());
            Assert.AreNotEqual("90 -264*0.2", b.ToString());
        }

        [TestMethod]
        public void TestEquals()
        {
            Formula a = new Formula("3+2");
            Formula b = new Formula("3+2");

            Assert.IsTrue(a.Equals(b));
        }

        [TestMethod]
        public void TestDoubleEquals()
        {
            Formula a = new Formula("7");
            Formula b = new Formula("3");

            Assert.IsFalse(a == b);
        }

    }
}
