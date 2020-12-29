using SpreadsheetUtilities;
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO.IsolatedStorage;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Text.RegularExpressions;
using System.Xml;

namespace SS
{
    /// <summary>
    /// Class that implements the AbstractSpreadsheet interface.
    /// Represents a Spreadsheet using a DependencyGraph and Cell objects.
    /// </summary>
    public class Spreadsheet : AbstractSpreadsheet
    {
        private readonly DependencyGraph dg;
        private readonly Dictionary<string, Cell> dictionary;
        private readonly string filePath;

        /// <summary>
        /// Constructs an empty spreadsheet an empty spreadsheet that imposes no 
        /// extra validity conditions, normalizes every cell name to itself, 
        /// and has version "default".
        /// </summary>
        public Spreadsheet() : this(s => true, s => s, "default")
        {
        }


        /// <summary>
        /// Constructs a spreadsheet by recording its variable validity test,
        /// its normalization method, and its version information.  The variable validity
        /// test is used throughout to determine whether a string that consists of one or
        /// more letters followed by one or more digits is a valid cell name.  The variable
        /// equality test should be used thoughout to determine whether two variables are
        /// equal.
        /// </summary>
        public Spreadsheet(Func<string, bool> isValid, Func<string, string> normalize, string version) : base(isValid, normalize, version)
        {
            dg = new DependencyGraph();
            dictionary = new Dictionary<string, Cell>();

            this.IsValid = isValid;
            this.Normalize = normalize;
            this.Version = version;
        }

        /// <summary>
        /// Constructs a spreadsheet using the same description as the three a
        /// rgument constructor, but allows the user to enter a string file path 
        /// used for saving the spreadsheet file.
        /// </summary>
        /// <param name="filePath"></param>
        /// <param name="isValid"></param>
        /// <param name="normalize"></param>
        /// <param name="version"></param>
        public Spreadsheet(string filePath, Func<string, bool> isValid, Func<string, string> normalize, string version) : base(isValid, normalize, version)
        {
            dg = new DependencyGraph();
            dictionary = new Dictionary<string, Cell>();

            this.filePath = filePath;
            this.IsValid = isValid;
            this.Normalize = normalize;
            this.Version = version;
        }


        /// <summary>
        /// Enumerates the names of all the non-empty cells in the spreadsheet.
        /// </summary>
        public override IEnumerable<string> GetNamesOfAllNonemptyCells()
        {
            HashSet<string> names = new HashSet<string>();

            foreach (string item in dictionary.Keys)
            {
                if (!String.IsNullOrEmpty(dictionary[item].contents.ToString()))
                    names.Add(item);
            }
            return names;
        }



        /// <summary>
        /// If name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, returns the contents (as opposed to the value) of the named cell.  The return
        /// value should be either a string, a double, or a Formula.
        public override object GetCellContents(string name)
        {
            if (String.IsNullOrEmpty(name))
                throw new InvalidNameException();

            if (!IsValidName(name))
                throw new InvalidNameException();

            if (!dictionary.ContainsKey(name))
                return "";

            return dictionary[name].contents;
        }



        /// <summary>
        /// If name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, the contents of the named cell becomes number.  The method returns a
        /// list consisting of name plus the names of all other cells whose value depends, 
        /// directly or indirectly, on the named cell.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        protected override IList<string> SetCellContents(string name, double number)
        {
            if (!IsValidName(name))
                throw new InvalidNameException();

            if (Object.Equals(number, null))
                throw new ArgumentNullException("Null number.");

            if (!dictionary.ContainsKey(name))
            {
                dictionary.Add(name, new Cell(number));
                return new List<string>(GetCellsToRecalculate(name));
            }
            else
            {
                Cell temp = dictionary[name];

                if (dictionary.TryGetValue(name, out Cell value) && !Object.Equals(value.contents, null) && value.contents is Formula)
                {
                    foreach (string item in (value.contents as Formula).GetVariables())
                    {
                        dg.RemoveDependency(item, name);
                    }
                }
                dictionary[name] = new Cell(number);

                try
                {
                    return new List<string>(GetCellsToRecalculate(name));
                }
                catch (CircularException e)
                {
                    dictionary[name] = temp;
                    throw e;
                }
            }
        }



        /// <summary>
        /// If text is null, throws an ArgumentNullException.
        /// 
        /// Otherwise, if name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, the contents of the named cell becomes text.  The method returns a
        /// list consisting of name plus the names of all other cells whose value depends, 
        /// directly or indirectly, on the named cell.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        protected override IList<string> SetCellContents(string name, string text)
        {
            if (!IsValidName(name))
                throw new InvalidNameException();

            if (Object.Equals(text, null))
                throw new ArgumentNullException("Null text value.");

            if (!dictionary.ContainsKey(name))
            {
                dictionary.Add(name, new Cell(text));
                return new List<string>(GetCellsToRecalculate(name));
            }
            else
            {
                Cell temp = dictionary[name];

                if (dictionary.TryGetValue(name, out Cell value) && Object.Equals(value.contents, null) && value.contents is Formula)
                {
                    foreach (string item in (value.contents as Formula).GetVariables())
                    {
                        dg.RemoveDependency(item, name);
                    }
                }
                dictionary[name] = new Cell(text);


                try
                {
                    return new List<string>(GetCellsToRecalculate(name));
                }
                catch (CircularException e)
                {
                    dictionary[name] = temp;
                    throw e;
                }
            }
        }



        /// <summary>
        /// If the formula parameter is null, throws an ArgumentNullException.
        /// 
        /// Otherwise, if name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, if changing the contents of the named cell to be the formula would cause a 
        /// circular dependency, throws a CircularException, and no change is made to the spreadsheet.
        /// 
        /// Otherwise, the contents of the named cell becomes formula.  The method returns a
        /// list consisting of name plus the names of all other cells whose value depends,
        /// directly or indirectly, on the named cell.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        protected override IList<string> SetCellContents(string name, Formula formula)
        {
            if (!IsValidName(name))
                throw new InvalidNameException();

            if (Object.Equals(formula, null))
                throw new ArgumentNullException("Null formula.");

            if (!dictionary.ContainsKey(name))
            {
                dictionary.Add(name, new Cell(formula));
                foreach (string item in formula.GetVariables())
                {
                    dg.AddDependency(item, name);
                }
                return new List<string>(GetCellsToRecalculate(name));
            }
            else
            {
                Cell temp = dictionary[name];

                dictionary[name] = new Cell(formula);
                foreach (string item in formula.GetVariables())
                {
                    dg.AddDependency(item, name);
                }

                try
                {
                    return new List<string>(GetCellsToRecalculate(name));
                }
                catch (CircularException e)
                {
                    dictionary[name] = temp;
                    throw e;
                }
            }
        }



        /// <summary>
        /// Returns an enumeration, without duplicates, of the names of all cells whose
        /// values depend directly on the value of the named cell.  In other words, returns
        /// an enumeration, without duplicates, of the names of all cells that contain
        /// formulas containing name.
        /// 
        /// For example, suppose that
        /// A1 contains 3
        /// B1 contains the formula A1 * A1
        /// C1 contains the formula B1 + A1
        /// D1 contains the formula B1 - C1
        /// The direct dependents of A1 are B1 and C1
        /// </summary>
        protected override IEnumerable<string> GetDirectDependents(string name)
        {
            return dg.GetDependents(name);
        }


        /// <summary>
        /// Requires that names be non-null.  Also requires that if names contains s,
        /// then s must be a valid non-null cell name.
        /// 
        /// If any of the named cells are involved in a circular dependency,
        /// throws a CircularException.
        /// 
        /// Otherwise, returns an enumeration of the names of all cells whose values must
        /// be recalculated, assuming that the contents of each cell named in names has changed.
        /// The names are enumerated in the order in which the calculations should be done.  
        /// 
        /// For example, suppose that 
        /// A1 contains 5
        /// B1 contains 7
        /// C1 contains the formula A1 + B1
        /// D1 contains the formula A1 * C1
        /// E1 contains 15
        /// 
        /// If A1 and B1 have changed, then A1, B1, and C1, and D1 must be recalculated,
        /// and they must be recalculated in either the order A1,B1,C1,D1 or B1,A1,C1,D1.
        /// The method will produce one of those enumerations.
        /// 
        /// PLEASE NOTE THAT THIS METHOD DEPENDS ON THE ABSTRACT METHOD GetDirectDependents.
        /// IT WON'T WORK UNTIL GetDirectDependents IS IMPLEMENTED CORRECTLY.
        /// </summary>
        protected new IEnumerable<String> GetCellsToRecalculate(ISet<String> names)
        {
            LinkedList<String> changed = new LinkedList<String>();
            HashSet<String> visited = new HashSet<String>();
            foreach (String name in names)
            {
                if (!visited.Contains(name))
                {
                    Visit(name, name, visited, changed);
                }
            }
            return changed;
        }


        /// <summary>
        /// A convenience method for invoking the other version of GetCellsToRecalculate
        /// with a singleton set of names.  See the other version for details.
        /// </summary>
        protected new IEnumerable<String> GetCellsToRecalculate(String name)
        {
            return GetCellsToRecalculate(new HashSet<String>() { name });
        }


        /// <summary>
        /// A helper for the GetCellsToRecalculate method.
        /// Detects circular logic and recursively checks for visited cells.
        ///   
        /// </summary>
        private void Visit(String start, String name, ISet<String> visited, LinkedList<String> changed)
        {
            visited.Add(name);
            foreach (String n in GetDirectDependents(name))
            {
                if (n.Equals(start))
                {
                    throw new CircularException();
                }
                else if (!visited.Contains(n))
                {
                    Visit(start, n, visited, changed);
                }
            }
            changed.AddFirst(name);
        }


        /// <summary>
        /// True if this spreadsheet has been modified since it was created or saved                  
        /// (whichever happened most recently); false otherwise.
        /// </summary>
        public override bool Changed
        {
            get => Version.Equals(GetSavedVersion(filePath));
            protected set => Version = GetSavedVersion(filePath);
        }

        /// <summary>
        /// Method used to determine whether a string that consists of one or more letters
        /// followed by one or more digits is a valid variable name.
        /// </summary>
        public new Func<string, bool> IsValid
        {
            get; protected set;
        }

        /// <summary>
        /// Method used to convert a cell name to its standard form.  For example,
        /// Normalize might convert names to upper case.
        /// </summary>
        public new Func<string, string> Normalize
        {
            get; protected set;
        }

        /// <summary>
        /// Version information
        /// </summary>
        public new string Version
        {
            get; protected set;
        }


        /// <summary>
        /// Returns the version information of the spreadsheet saved in the named file.
        /// If there are any problems opening, reading, or closing the file, the method
        /// should throw a SpreadsheetReadWriteException with an explanatory message.
        /// </summary>
        public override string GetSavedVersion(String filename)
        {
            using (XmlReader reader = XmlReader.Create(filename))
            {
                while (reader.Read())
                {
                    if (reader.IsStartElement() && reader.Name.Equals("spreadsheet"))
                        return reader.GetAttribute("version");
                }

                throw new SpreadsheetReadWriteException("No version information found.");
            }
        }

        /// <summary>
        /// Writes the contents of this spreadsheet to the named file using an XML format.
        /// The XML elements should be structured as follows:
        /// 
        /// <spreadsheet version="version information goes here">
        /// 
        /// <cell>
        /// <name>cell name goes here</name>
        /// <contents>cell contents goes here</contents>    
        /// </cell>
        /// 
        /// </spreadsheet>
        /// 
        /// There should be one cell element for each non-empty cell in the spreadsheet.  
        /// If the cell contains a string, it should be written as the contents.  
        /// If the cell contains a double d, d.ToString() should be written as the contents.  
        /// If the cell contains a Formula f, f.ToString() with "=" prepended should be written as the contents.
        /// 
        /// If there are any problems opening, writing, or closing the file, the method should throw a
        /// SpreadsheetReadWriteException with an explanatory message.
        /// </summary>
        public override void Save(string filename)
        {
            // Use indentation to make the XML more readable.
            XmlWriterSettings settings = new XmlWriterSettings
            {
                Indent = true,
                IndentChars = "  "
            };

            using (XmlWriter writer = XmlWriter.Create(filename, settings))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("spreadsheet");

                // Add an attribute to the spreadsheet element.
                writer.WriteAttributeString("version", this.Version);

                foreach (string item in dictionary.Keys)
                {
                    writer.WriteStartElement("cell");
                    writer.WriteAttributeString("name", item);

                    if (dictionary[item].GetType().Equals(typeof(double)) || dictionary[item].GetType().Equals(typeof(string)))
                        writer.WriteAttributeString("contents", dictionary[item].ToString());
                    else
                        writer.WriteAttributeString("contents", "=" + dictionary[item].ToString());

                    writer.WriteEndElement();
                }

                writer.WriteEndElement();
                writer.WriteEndDocument();
            }
        }


        /// <summary>
        /// If name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, returns the value (as opposed to the contents) of the named cell.  The return
        /// value should be either a string, a double, or a SpreadsheetUtilities.FormulaError.
        /// </summary>
        public override object GetCellValue(string name)
        {
            if (!IsValidName(name))
                throw new InvalidNameException();

            if (!dictionary.ContainsKey(name))
                throw new ArgumentException("This cell name is not in the spreadsheet.");

            if (Double.TryParse(dictionary[name].contents.ToString(), out double result))
                return result;

            // If it's a formula, evaluate it
            if (dictionary[name].ToString().ElementAt(0).Equals("="))
            {
                object temp = dictionary[name].contents.ToString().Substring(1, dictionary[name].contents.ToString().Length - 2);
                return (temp as Formula).Evaluate(Lookup);
            }

            // Else, return the string in the cell
            return dictionary[name].contents.ToString();
        }


        /// <summary>
        /// If content is null, throws an ArgumentNullException.
        /// 
        /// Otherwise, if name is null or invalid, throws an InvalidNameException.
        /// 
        /// Otherwise, if content parses as a double, the contents of the named
        /// cell becomes that double.
        /// 
        /// Otherwise, if content begins with the character '=', an attempt is made
        /// to parse the remainder of content into a Formula f using the Formula
        /// constructor.  There are then three possibilities:
        /// 
        ///   (1) If the remainder of content cannot be parsed into a Formula, a 
        ///       SpreadsheetUtilities.FormulaFormatException is thrown.
        ///       
        ///   (2) Otherwise, if changing the contents of the named cell to be f
        ///       would cause a circular dependency, a CircularException is thrown,
        ///       and no change is made to the spreadsheet.
        ///       
        ///   (3) Otherwise, the contents of the named cell becomes f.
        /// 
        /// Otherwise, the contents of the named cell becomes content.
        /// 
        /// If an exception is not thrown, the method returns a list consisting of
        /// name plus the names of all other cells whose value depends, directly
        /// or indirectly, on the named cell. The order of the list should be any
        /// order such that if cells are re-evaluated in that order, their dependencies 
        /// are satisfied by the time they are evaluated.
        /// 
        /// For example, if name is A1, B1 contains A1*2, and C1 contains B1+A1, the
        /// list {A1, B1, C1} is returned.
        /// </summary>
        public override IList<string> SetContentsOfCell(string name, string content)
        {
            if (!IsValid(name))
                throw new InvalidNameException();

            if (string.IsNullOrEmpty(content))
                throw new ArgumentNullException("Content is null or empty.");

            if (Double.TryParse(content, out double result))
                return SetCellContents(name, result);

            else if (content.ElementAt(0).Equals("="))
                return SetCellContents(name, new Formula(content.Substring(1, content.Length - 2)));

            else
                return SetCellContents(name, content);

        }


        /// <summary>
        /// Helper method for determining if a string is a valid name.
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        private static bool IsValidName(string s)
        {
            if (String.IsNullOrEmpty(s) || String.IsNullOrWhiteSpace(s))
                return false;

            if (Regex.IsMatch(s, @"^[a-zA-Z_]([a-zA-Z_]|\d)*$"))
                return true;

            return false;
        }


        /// <summary>
        /// Private Lookup method used for evaluating a formula.
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        private static double Lookup(string s)
        {
            return s.GetHashCode();
        }


        /// <summary>
        /// Private nested class to represent a cell in the Spreadsheet.
        /// </summary>
        private class Cell
        {
            public object contents;

            /// <summary>
            /// Constructors a Cell with a Formula in it.
            /// </summary>
            /// <param name="f"></param>
            public Cell(Formula f)
            {
                contents = f;
            }

            /// <summary>
            /// Constructs a Cell with a string in it.
            /// </summary>
            /// <param name="s"></param>
            public Cell(string s)
            {
                contents = s;
            }

            /// <summary>
            /// Constructs a Cell with a double in it.
            /// </summary>
            /// <param name="val"></param>
            public Cell(double val)
            {
                contents = val;
            }
        }
    }
}
