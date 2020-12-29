using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpreadsheetUtilities;
using SS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Net.Http.Headers;
using System.Xml;

namespace SpreadsheetTests
{
    /// <summary>
    /// Tests for the Spreadsheet class.
    /// </summary>
    [TestClass]
    public class SpreadSheetTests
    {
        [TestMethod()]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestInvalidNames()
        {
            Spreadsheet s = new Spreadsheet();
            s.GetCellContents("   ");
        }

        [TestMethod()]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestInvalidNames2()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("1", 10.ToString());
        }

        [TestMethod()]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestInvalidNames3()
        {
            Spreadsheet s = new Spreadsheet();
            Formula f = new Formula("A1");
            s.SetContentsOfCell("2394857", f.ToString());
        }

        [TestMethod()]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestInvalidNames4()
        {
            Spreadsheet s = new Spreadsheet();
            s.GetCellContents("1__abcde___");
        }

        [TestMethod()]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestInvalidNames5()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("$_abc341", "text");
        }

        [TestMethod()]
        public void TestLotsOfValidNames()
        {
            Spreadsheet s = new Spreadsheet();
            Formula f = new Formula("5");

            s.GetCellContents("_");
            s.GetCellContents("___");
            s.GetCellContents("_2");
            s.GetCellContents("__ab59");
            s.GetCellContents("a");

            s.SetContentsOfCell("AAA61_", 2.ToString());
            s.SetContentsOfCell("_A21abc40__", 100.32.ToString());
            s.SetContentsOfCell("a22298475", "text");
            s.SetContentsOfCell("__1", "text");
            s.SetContentsOfCell("name", f.ToString());
            s.SetContentsOfCell("__variable__1020", f.ToString());
        }

        [TestMethod()]
        public void TestGetNamesOfAllNonemptyCells()
        {
            Spreadsheet s = new Spreadsheet();
            List<string> list = new List<string> { "A1", "B1" };

            s.SetContentsOfCell("A1", 2.ToString());
            s.SetContentsOfCell("B1", 8.ToString());

            Assert.IsTrue(list.SequenceEqual(s.GetNamesOfAllNonemptyCells()));
        }

        [TestMethod()]
        public void TestGetNamesOfAllNonemptyCells2()
        {
            Spreadsheet s = new Spreadsheet();
            List<string> list = new List<string> { "AAA649", "_B23_", "__b" };

            s.SetContentsOfCell("AAA649", 2.ToString());
            s.SetContentsOfCell("_B23_", 8.ToString());
            s.SetContentsOfCell("__b", 3.1415.ToString());

            Assert.IsTrue(list.SequenceEqual(s.GetNamesOfAllNonemptyCells()));
        }

        [TestMethod()]
        public void TestGetDirectDependents3()
        {
            Spreadsheet s = new Spreadsheet();
            List<string> list = new List<string> { "AAA649", "_B23_", "__b" };

            s.SetContentsOfCell("AAA649", 2.ToString());
            s.SetContentsOfCell("_B23_", 8.ToString());
            s.SetContentsOfCell("__b", 3.1415.ToString());

            Assert.IsTrue(s.GetNamesOfAllNonemptyCells().SequenceEqual(list));
        }

        [TestMethod()]
        public void TestSetCellContentsDouble()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("G1", 2.ToString());
            s.SetContentsOfCell("A2", 3.7.ToString());

            Assert.AreEqual(2, s.GetNamesOfAllNonemptyCells().Count());
            Assert.IsTrue(s.SetContentsOfCell("G1", 2.ToString()).SequenceEqual(new List<string> { "G1" }));
            Assert.IsTrue(s.SetContentsOfCell("A2", 5.1.ToString()).SequenceEqual(new List<string> { "A2" }));

            s.SetContentsOfCell("t45", 918.ToString());
            s.SetContentsOfCell("_9", 2.17.ToString());
            Assert.AreEqual(4, s.GetNamesOfAllNonemptyCells().Count());
            Assert.IsTrue(s.SetContentsOfCell("t45", 298.ToString()).SequenceEqual(new List<string> { "t45" }));
            Assert.IsTrue(s.SetContentsOfCell("_9", (-4).ToString()).SequenceEqual(new List<string> { "_9" }));
        }

        [TestMethod()]
        public void TestSetCellContentsDouble2()
        {
            Spreadsheet s = new Spreadsheet();
            Formula f = new Formula("1 + 4");

            s.SetContentsOfCell("A1", 45.2.ToString());
            s.SetContentsOfCell("A2", (-1.8).ToString());

            Assert.AreEqual(2, s.GetNamesOfAllNonemptyCells().Count());
            Assert.IsTrue(s.SetContentsOfCell("A1", f.ToString()).SequenceEqual(new List<string> { "A1" }));
            Assert.IsTrue(s.SetContentsOfCell("__", f.ToString()).SequenceEqual(new List<string> { "__" }));
        }

        [TestMethod()]
        [ExpectedException(typeof(ArgumentNullException))]
        public void TestSetCellContentsDoubleCircular()
        {
            Spreadsheet s = new Spreadsheet();
            Formula g = new Formula("H1");
            Formula h = new Formula("G1");

            s.SetContentsOfCell("G1", g.ToString().Substring(1, g.ToString().Length - 2));
            s.SetContentsOfCell("H1", h.ToString().Substring(1, h.ToString().Length - 2));
        }

        [TestMethod()]
        public void TestSetCellContentsString()
        {
            Spreadsheet s = new Spreadsheet();
            s.SetContentsOfCell("A1", "string");
            s.SetContentsOfCell("A2", "test!!");
            s.SetContentsOfCell("A3", "test AGAIN");

            Assert.AreEqual(3, s.GetNamesOfAllNonemptyCells().Count());
            Assert.IsTrue(s.SetContentsOfCell("_", " ").SequenceEqual(new List<string> { "_" }));
            Assert.IsTrue(s.SetContentsOfCell("__B__", "string").SequenceEqual(new List<string> { "__B__" }));
            Assert.IsTrue(s.SetContentsOfCell("AAA", "hello!").SequenceEqual(new List<string> { "AAA" }));


            s.SetContentsOfCell("AG1", "string//#&7");
            s.SetContentsOfCell("_s2", "112038543445");
            s.SetContentsOfCell("__t", "test");

            Assert.AreEqual(9, s.GetNamesOfAllNonemptyCells().Count());
        }

        [TestMethod()]
        public void TestSetCellContentsStringsAndFormulas()
        {
            Spreadsheet s = new Spreadsheet();
            Formula f = new Formula(" 9 - 2 ");

            s.SetContentsOfCell("A1", "hi");
            s.SetContentsOfCell("A2", "hey!!");
            s.SetContentsOfCell("A3", "hello  ");

            s.SetContentsOfCell("A1", f.ToString());
            s.SetContentsOfCell("A3", f.ToString());

            Assert.AreEqual(3, s.GetNamesOfAllNonemptyCells().Count());
            Assert.IsTrue(s.SetContentsOfCell("E21", f.ToString()).SequenceEqual(new List<string> { "E21" }));
            Assert.IsTrue(s.SetContentsOfCell("__", f.ToString()).SequenceEqual(new List<string> { "__" }));
            Assert.AreEqual(5, s.GetNamesOfAllNonemptyCells().Count());
        }

        [TestMethod()]
        public void TestSetCellContentsFormula()
        {
            Spreadsheet s = new Spreadsheet();
            Formula f = new Formula("3*(9-2)", s => "A1", s => true);
            Formula g = new Formula("2 * A1", s => "A1", s => true);

            s.SetContentsOfCell("ABC3", f.ToString());
            s.SetContentsOfCell("A294", g.ToString());

            Assert.AreEqual(2, s.GetNamesOfAllNonemptyCells().Count());
        }

        [TestMethod()]
        [ExpectedException(typeof(ArgumentNullException))]
        public void TestSetCellContentsFormulaCircular()
        {
            Spreadsheet s = new Spreadsheet();
            Formula f = new Formula("A2");
            Formula g = new Formula("A3");
            Formula h = new Formula("A2");

            s.SetContentsOfCell("A1", f.ToString().Substring(1, f.ToString().Length - 2));
            s.SetContentsOfCell("A2", g.ToString().Substring(1, g.ToString().Length - 2));
            s.SetContentsOfCell("A3", h.ToString().Substring(1, h.ToString().Length - 2));
        }

        [TestMethod]
        public void TestWriteXml()
        {
            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.IndentChars = "  ";
            using (XmlWriter writer = XmlWriter.Create("save.txt", settings))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("contents", "hello");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }

            using (XmlWriter writer = XmlWriter.Create("test.txt", settings))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("contents", "hello");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }


            AbstractSpreadsheet ss = new Spreadsheet("save.txt", s => true, s => s, "");
            ss.Save("save.txt");

            Assert.AreEqual("", ss.GetSavedVersion("save.txt"));

            AbstractSpreadsheet s = new Spreadsheet("test.txt", s => true, s => s, "HELLO");
            s.Save("test.txt");

            Assert.AreEqual("HELLO", s.GetSavedVersion("test.txt"));
        }

        [TestMethod]
        public void TestReadXml()
        {
            AbstractSpreadsheet ss = new Spreadsheet("save.txt", s => true, s => s, "1.2.1.7");
            ss.Save("save.txt");

            Assert.AreEqual("1.2.1.7", ss.GetSavedVersion("save.txt"));

            XmlWriterSettings settings = new XmlWriterSettings();
            settings.Indent = true;
            settings.IndentChars = "  ";
            using (XmlWriter writer = XmlWriter.Create("save.txt", settings))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");
                writer.WriteAttributeString("version", "Version!!");

                writer.WriteStartElement("cell");
                writer.WriteElementString("name", "A1");
                writer.WriteElementString("contents", "hello");
                writer.WriteEndElement();

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }

            Assert.AreEqual("Version!!", ss.GetSavedVersion("save.txt"));
        }
    }
}
