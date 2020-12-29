using System;
using System.Collections.Generic;
using System.IO;
class Solution {

    public static Stack<String> stack = new Stack<string>();
    public string s;
    
    static void Main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution */
        Console.ReadLine();
        Console.WriteLine();
    }

    static void append(string w)
    {
        s += w;
    }

    static void delete(int k)
    {
        StringBuilder str = new StringBuilder(s);

        for (int i = k; i <= s.Length; i++)
        {
            str.DeleteCharAt(i);
        }
    }

    static void print(int k)
    {
        Console.WriteLine(s.IndexOf(k));
    }

    static void undo()
    {
        
    }

}