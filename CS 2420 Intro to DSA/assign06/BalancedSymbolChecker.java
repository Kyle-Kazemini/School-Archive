package assign06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class containing the checkFile method for checking if the (, [, and { symbols in an input file are correctly matched.
 * 
 * @author Erin Parker && Kyle Kazemini && Anna Shelukha
 * @version February 24, 2020
 */
public class BalancedSymbolChecker
{

    /**
     * Generates a message indicating whether the input file has unmatched symbols. (Use the helper methods below for
     * constructing messages.)
     * 
     * @param filename - name of the input file to check
     * @return a message indicating whether the input file has unmatched symbols
     * @throws FileNotFoundException if the file does not exist
     */
    public static String checkFile (String filename) throws FileNotFoundException
    {
        // stack variable
        LinkedListStack<Character> stack = new LinkedListStack<Character>();
        // stores popped element
        char pop = 0;
        // stores line count
        int count = 1;
        // true when inside a comment
        boolean comment = false;
        // true when inside a String
        boolean string = false;
        // scanner to extract Strings from file
        Scanner scn;

        // open a scanner to work through the text file
        scn = new Scanner(new File(filename));

        // for every line in the file
        while (scn.hasNextLine())
        {
            // use the next line of code
            String line = scn.nextLine();

            // for every char in the line
            for (int i = 0; i < line.length(); i++)
            {

                // Check for unfinished comments, Strings, and chars

                // if comment line is found, skip the rest of the line
                if (line.charAt(i) == '/' && line.charAt(i + 1) == '/')
                    break;

                // if multi-line comment is hit, ignore values until it is exited
                else if (line.charAt(i) == '/' && line.charAt(i + 1) == '*' && string == false)
                {
                    comment = true;
                    i += 2;
                }

                // if char element is found, skip two chars ahead
                else if (line.charAt(i) == '\'' && string == false && comment == false)
                    i += 2;

                // if quotes are hit, ignore elements until it ends
                else if (line.charAt(i) == '\"' && string == false && line.charAt(i - 1) != '\\' && comment == false)
                {
                    string = true;
                    i++;
                }

                // if inside a comment, check for end
                if (comment == true && i < line.length() - 1 && string == false)
                {
                    if (line.charAt(i) == '*' && line.charAt(i + 1) == '/')
                    {
                        comment = false;
                        i++;
                    }
                }

                // if inside a string, check for end of String
                else if (string == true)
                {
                    if (line.charAt(i) == '\"' && line.charAt(i - 1) != '\\')
                    {
                        string = false;
                    }
                }

                // Else, check for unmatched symbols
                else if (string == false && comment == false)
                {
                    // if there's an opening symbol, add to stack
                    if (line.charAt(i) == '(' || line.charAt(i) == '[' || line.charAt(i) == '{')
                        stack.push(line.charAt(i));

                    // if there's a closing symbol, pop from stack
                    else if (line.charAt(i) == ')' || line.charAt(i) == ']' || line.charAt(i) == '}')
                    {
                        // try popping- if unsuccessful, return error message
                        try
                        {
                            pop = stack.pop();
                        }
                        catch (NoSuchElementException e)
                        {
                            return unmatchedSymbol(count, i + 1, line.charAt(i), ' ');
                        }

                        // if incorrect match was popped, returns corresponding error message
                        if (pop != opposite(line.charAt(i)))
                            return unmatchedSymbol(count, i + 1, line.charAt(i), opposite(pop));
                    }
                }
            }
            // increase line counter
            count++;
        }

        // close the scanner
        scn.close();

        // Check if a comment was never closed
        if (comment == true)
            return unfinishedComment();

        // Check for unmatched symbols at the end of the file
        if (stack.isEmpty() == false)
        {
            return unmatchedSymbolAtEOF(opposite(stack.peek()));
        }

        // if no errors are hit, everything matches
        return allSymbolsMatch();
    }

    /**
     * Use this error message in the case of an unmatched symbol.
     * 
     * @param lineNumber     - the line number of the input file where the matching symbol was expected
     * @param colNumber      - the column number of the input file where the matching symbol was expected
     * @param symbolRead     - the symbol read that did not match
     * @param symbolExpected - the matching symbol expected
     * @return the error message
     */
    private static String unmatchedSymbol (int lineNumber, int colNumber, char symbolRead, char symbolExpected)
    {
        return "ERROR: Unmatched symbol at line " + lineNumber + " and column " + colNumber + ". Expected "
                + symbolExpected + ", but read " + symbolRead + " instead.";
    }

    /**
     * Use this error message in the case of an unmatched symbol at the end of the file.
     * 
     * @param symbolExpected - the matching symbol expected
     * @return the error message
     */
    private static String unmatchedSymbolAtEOF (char symbolExpected)
    {
        return "ERROR: Unmatched symbol at the end of file. Expected " + symbolExpected + ".";
    }

    /**
     * Use this error message in the case of an unfinished comment (i.e., a file that ends with an open /* comment).
     * 
     * @return the error message
     */
    private static String unfinishedComment ()
    {
        return "ERROR: File ended before closing comment.";
    }

    /**
     * Use this message when no unmatched symbol errors are found in the entire file.
     * 
     * @return the success message
     */
    private static String allSymbolsMatch ()
    {
        return "No errors found. All symbols match.";
    }

    /**
     * Method for obtaining the opposite char of symbols; returns empty if an unexpected char is entered Ex: returns
     * closing parentheses when given opening parentheses
     * 
     * @return opposite char
     */
    private static char opposite (char c)
    {
        switch (c)
        {
            case '(':
                return ')';
            case '{':
                return '}';
            case '[':
                return ']';
            case ')':
                return '(';
            case '}':
                return '{';
            case ']':
                return '[';
        }
        return ' ';
    }
}