using FormulaEvaluator;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;

namespace EvaluatorTester
{
    /// <summary>
    /// Tester class for the Evaluator in the Spreadsheet solution.
    /// The method names are really dumn but it's ok because I'm not turing in this tester.
    /// </summary>
    [TestClass]
    public class UnitTest1
    {
        public static int Lookup(string s)
        {
            if (s == "a4")
                return 2;
            else if (s == "b5")
                return 7;
            else if (s == "cd22")
                return 100;

            throw new ArgumentException("Unknown variable");
        }

        [TestMethod]
        public void TestMethod1()
        {
            String s = "4 * 3";
            Assert.AreEqual(12, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod2()
        {
            String s = "5 - (8 * 3) + 12 / 3";
            Assert.AreEqual(-15, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod3()
        {
            String s = "(4*2) / 3";
            Assert.AreEqual(2, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod4()
        {
            String s = "5 + 2";
            Assert.AreEqual(7, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod5()
        {
            String s = "5 + 2 - 3 * 3";
            Assert.AreEqual(-2, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod6()
        {
            String s = "4 * 3 / 2";
            Assert.AreEqual(6, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod7()
        {
            String s = "(9) / (2) + 4";
            Assert.AreEqual(8, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void TestMethod8()
        {
            String s = "1 / 0";
            Evaluator.Evaluate(s, Lookup);
        }

        [TestMethod]
        public void TestMethod9()
        {
            String s = "0";
            Assert.AreEqual(0, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod10()
        {
            String s = "100";
            Assert.AreEqual(100, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod11()
        {
            String s = "4 * 0";
            Assert.AreEqual(0, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void TestMethod12()
        {
            String s = "10/0";
            Evaluator.Evaluate(s, Lookup);
        }

        [TestMethod]
        public void TestMethod13()
        {
            String s = "0 + 1/3";
            Assert.AreEqual(0, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod14()
        {
            String s = "(2*2)/4";
            Assert.AreEqual(1, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod15()
        {
            String s = "(4*3)/(3*2)";
            Assert.AreEqual(2, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod16()
        {
            String s = "(4*3)/(3*2) - 2";
            Assert.AreEqual(0, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod17()
        {
            String s = "1+6-2*9";
            Assert.AreEqual(-11, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod18()
        {
            String s = "81/9-10";
            Assert.AreEqual(-1, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod19()
        {
            String s = "(8/2)*5";
            Assert.AreEqual(20, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod20()
        {
            String s = "(9*3)/27 + 5*2";
            Assert.AreEqual(11, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod21()
        {
            String s = "(10 / 2) / (2 * 4)";
            Assert.AreEqual(0, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod22()
        {
            String s = "(10 - 2) / (2 + 4)";
            Assert.AreEqual(1, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod23()
        {
            String s = "(10 / 2) * (4 / 2)";
            Assert.AreEqual(10, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod24()
        {
            String s = "(10 - 2)*3 / ((2 * 3)*2)";
            Assert.AreEqual(2, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod25()
        {
            String s = "(((3)))";
            Assert.AreEqual(3, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        [ExpectedException(typeof(InvalidOperationException))]
        public void TestMethod26()
        {
            String s = "((()))";
            Evaluator.Evaluate(s, Lookup);
        }

        [TestMethod]
        public void TestMethod27()
        {
            String s = "(3 + 4 + 9 - 5) * (6 - 1 + 8 - 4)";
            Assert.AreEqual(99, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestMethod28()
        {
            String s = "(7 + 7 - 3 + 1) / (14 + 20 - 20 - 2)";
            Assert.AreEqual(1, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void TestException1()
        {
            String s = "A1";
            Evaluator.Evaluate(s, Lookup);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void TestException2()
        {
            String s = "C21";
            Evaluator.Evaluate(s, Lookup);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentException))]
        public void TestException3()
        {
            String s = "AB33";
            Evaluator.Evaluate(s, Lookup);
        }

        [TestMethod]
        public void TestException4()
        {
            String s = "a4";
            Assert.AreEqual(2, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestException5()
        {
            String s = "b5";
            Assert.AreEqual(7, Evaluator.Evaluate(s, Lookup));
        }

        [TestMethod]
        public void TestException6()
        {
            String s = "cd22";
            Assert.AreEqual(100, Evaluator.Evaluate(s, Lookup));
        }

    }
}
