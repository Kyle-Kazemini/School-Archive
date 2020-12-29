// Skeleton implementation written by Joe Zachary for CS 3500, September 2013.
// Version 1.1 (Fixed error in comment for RemoveDependency.)
// Version 1.2 - Daniel Kopta 
//               (Clarified meaning of dependent and dependee.)
//               (Clarified names in solution/project structure.)

using System.Collections.Generic;
using System.Linq;

namespace SpreadsheetUtilities
{

    /// <summary>
    /// (s1,t1) is an ordered pair of strings
    /// t1 depends on s1; s1 must be evaluated before t1
    /// 
    /// A DependencyGraph can be modeled as a set of ordered pairs of strings.  Two ordered pairs
    /// (s1,t1) and (s2,t2) are considered equal if and only if s1 equals s2 and t1 equals t2.
    /// Recall that sets never contain duplicates.  If an attempt is made to add an element to a 
    /// set, and the element is already in the set, the set remains unchanged.
    /// 
    /// Given a DependencyGraph DG:
    /// 
    ///    (1) If s is a string, the set of all strings t such that (s,t) is in DG is called dependents(s).
    ///        (The set of things that depend on s)    
    ///        
    ///    (2) If s is a string, the set of all strings t such that (t,s) is in DG is called dependees(s).
    ///        (The set of things that s depends on) 
    //
    // For example, suppose DG = {("a", "b"), ("a", "c"), ("b", "d"), ("d", "d")}
    //     dependents("a") = {"b", "c"}
    //     dependents("b") = {"d"}
    //     dependents("c") = {}
    //     dependents("d") = {"d"}
    //     dependees("a") = {}
    //     dependees("b") = {"a"}
    //     dependees("c") = {"a"}
    //     dependees("d") = {"b", "d"}
    /// </summary>
    public class DependencyGraph
    {
        // Key is dependee, value is list of its dependents.
        private Dictionary<string, HashSet<string>> dependents;

        // Key is dependent, value is list of its dependees.
        private Dictionary<string, HashSet<string>> dependees;

        // Number of ordered pairs in the dependency graph.
        private int count;

        /// <summary>
        /// Creates an empty DependencyGraph.
        /// </summary>
        public DependencyGraph()
        {
            dependents = new Dictionary<string, HashSet<string>>();
            dependees = new Dictionary<string, HashSet<string>>();
            count = 0;
        }


        /// <summary>
        /// The number of ordered pairs in the DependencyGraph.
        /// </summary>
        public int Size
        {
            get { return count; }
        }


        /// <summary>
        /// The size of dependees(s).
        /// This property is an example of an indexer.  If dg is a DependencyGraph, you would
        /// invoke it like this:
        /// dg["a"]
        /// It should return the size of dependees("a")
        /// </summary>
        public int this[string s]
        {
            get
            {
                if (dependees.ContainsKey(s))
                    return dependees[s].Count;

                return 0;
            }
        }


        /// <summary>
        /// Reports whether dependents(s) is non-empty.
        /// </summary>
        public bool HasDependents(string s)
        {
            if (dependents.ContainsKey(s))
                return dependents[s].Count > 0;

            return false;
        }


        /// <summary>
        /// Reports whether dependees(s) is non-empty.
        /// </summary>
        public bool HasDependees(string s)
        {
            if (dependees.ContainsKey(s))
                return dependees[s].Count > 0;

            return false;
        }


        /// <summary>
        /// Enumerates dependents(s).
        /// </summary>
        public IEnumerable<string> GetDependents(string s)
        {
            if (dependents.ContainsKey(s))
                return dependents[s];

            return new HashSet<string>();
        }

        /// <summary>
        /// Enumerates dependees(s).
        /// </summary>
        public IEnumerable<string> GetDependees(string s)
        {
            if (dependees.ContainsKey(s))
                return dependees[s];

            return new HashSet<string>();
        }


        /// <summary>
        /// <para>Adds the ordered pair (s,t), if it doesn't exist</para>
        /// 
        /// <para>This should be thought of as:</para>   
        /// 
        ///   t depends on s
        ///
        /// </summary>
        /// <param name="s"> s must be evaluated first. T depends on S</param>
        /// <param name="t"> t cannot be evaluated until s is</param>        /// 
        public void AddDependency(string s, string t)
        {
            if (!(dependents.ContainsKey(s) && dependees.ContainsKey(t)))
                count++;

            if (dependents.ContainsKey(s))
            {
                dependents[s].Add(t);
            }
            else
            {
                HashSet<string> newDependents = new HashSet<string> { t };
                dependents.Add(s, newDependents);
            }

            if (dependees.ContainsKey(t))
                dependees[t].Add(s);
            else
            {
                HashSet<string> newDependees = new HashSet<string> { s };
                dependees.Add(t, newDependees);
            }
        }


        /// <summary>
        /// Removes the ordered pair (s,t), if it exists
        /// </summary>
        /// <param name="s"></param>
        /// <param name="t"></param>
        public void RemoveDependency(string s, string t)
        {
            if (dependents.ContainsKey(s) && dependees.ContainsKey(t))
            {
                dependents[s].Remove(t);
                dependees[t].Remove(s);
                count--;

                if (dependents[s].Count == 0)
                    dependents.Remove(s);
                if (dependees[t].Count == 0)
                    dependees.Remove(t);
            }
        }


        /// <summary>
        /// Removes all existing ordered pairs of the form (s,r).  Then, for each
        /// t in newDependents, adds the ordered pair (s,t).
        /// </summary>
        public void ReplaceDependents(string s, IEnumerable<string> newDependents)
        {
            if (dependents.ContainsKey(s))
            {
                int index = dependents[s].Count();

                for (int i = 1; i <= index; i++)
                {
                    RemoveDependency(s, dependents[s].ElementAt(0));
                }

                for (int i = 0; i < newDependents.Count(); i++)
                {
                    AddDependency(s, newDependents.ElementAt(i));
                }
            }
            else
            {
                dependents.Add(s, (HashSet<string>)newDependents);
                if (newDependents.Count() > 0)
                    count++;

                foreach (string item in newDependents)
                {
                    if (dependees.ContainsKey(item))
                        dependees[item].Add(s);
                    else
                    {
                        dependees.Add(item, new HashSet<string>());
                        dependees[item].Add(s);
                    }
                }
            }
        }


        /// <summary>
        /// Removes all existing ordered pairs of the form (r,s).  Then, for each 
        /// t in newDependees, adds the ordered pair (t,s).
        /// </summary>
        public void ReplaceDependees(string s, IEnumerable<string> newDependees)
        {
            if (dependees.ContainsKey(s))
            {
                int index = dependees[s].Count();

                for (int i = 1; i <= index; i++)
                {
                    RemoveDependency(dependees[s].ElementAt(0), s);
                }

                for (int i = 0; i < newDependees.Count(); i++)
                {
                    AddDependency(newDependees.ElementAt(i), s);
                }
            }
            else
            {
                dependees.Add(s, (HashSet<string>)newDependees);
                if (newDependees.Count() > 0)
                    count++;

                foreach (string item in newDependees)
                {
                    if (dependents.ContainsKey(item))
                        dependents[item].Add(s);
                    else
                    {
                        dependents.Add(item, new HashSet<string>());
                        dependents[item].Add(s);
                    }
                }
            }
        }

    }

}
