using System.CodeDom.Compiler;
using System.Collections.Generic;
using System.Collections;
using System.ComponentModel;
using System.Diagnostics.CodeAnalysis;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Runtime.Serialization;
using System.Text.RegularExpressions;
using System.Text;
using System;

class Solution
{

    // Complete the superDigit function below.
    static int superDigit(string n, int k)
    {
        if (n.Length == 1)
            return Convert.ToInt32(n);

        else
        {
            long sum = 0;

            for (int i = 0; i < n.Length; i++)
                sum += n.ElementAt(i) - '0';

            return superDigit((sum * k).ToString(), 1);
        }
    }

    static void Main(string[] args)
    {
        TextWriter textWriter = new StreamWriter(@System.Environment.GetEnvironmentVariable("OUTPUT_PATH"), true);

        string[] nk = Console.ReadLine().Split(' ');

        string n = nk[0];

        int k = Convert.ToInt32(nk[1]);

        int result = superDigit(n, k);

        textWriter.WriteLine(result);

        textWriter.Flush();
        textWriter.Close();
    }
}
