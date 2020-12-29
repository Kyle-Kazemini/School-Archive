package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class for generating random phrases based on a given grammar and number of
 * words.
 *
 * @author Kyle Kazemini & Robert Davidson
 * @version April 20, 2020
 */
public class RandomPhraseGenerator {

    public static HashMap<String, ArrayList<String>> table = new HashMap<>();
    private static Random rng = new Random();

    public static void main(String[] args) {
        String fileName = args[0];
        parseFile(fileName);
        int numOfWords = Integer.parseInt(args[1]);

        for (int i = 0; i < numOfWords; i++)
            System.out.println(generatePhrase());
    }

    /**
     * This method generates a random phrase based on the information given by the user.
     *
     * @return String
     */
    public static String generatePhrase() {
        int productionPos = rng.nextInt(table.get("<start>").size());
        String production = table.get("<start>").get(productionPos);

        return nonTerminalReplacer(production);
    }


    /**
     * Driver Method
     *
     * @param p - production
     * @return Terminal string segment
     */
    public static String nonTerminalReplacer(String p) {
        StringBuilder sb = new StringBuilder();
        StringBuilder nt = new StringBuilder();
        int marker = 0;
        return nonTerminalReplacer(p, sb, nt, false, marker);
    }

    public static String nonTerminalReplacer(String p, StringBuilder sb, StringBuilder nt, boolean pause, int marker) {
        nt.delete(0, nt.length());
        for (int i = 0; i <= p.length(); ++i) {

            // End of String Append
            if (i == p.length()) {
                sb.append(p, marker, i);
                break;
            }

            // Terminal Append
            if (p.charAt(i) == '<') {
                if (i > 0) {
                    sb.append(p, marker, i);
                    marker = i;
                }
                pause = true;
            }

            // Non Terminal Append
            if (pause && p.charAt(i) == '>') {
                nt.append(p, marker, i + 1);
                marker = i + 1;
                nonTerminalReplacer(toTerminal(nt), sb, nt, false, 0);
            }
        }
        return sb.toString();
    }

    /**
     * toTerminal replaces non terminals with values from the correct arrayList
     *
     * @param nt - Non Terminal to be replaced
     * @return terminal string
     */
    private static String toTerminal(StringBuilder nt) {
        String nonTerminal = nt.toString();

        // If non-terminal is not a table key
        if (table.get(nonTerminal) != null)
            return table.get(nonTerminal).get(rng.nextInt(table.get(nonTerminal).size()));
        else
            // Returns empty string if key does not exist
            // This would be the result of an invalid non-terminal in the grammar file.
            return "";
    }


    /**
     * This method parses through the input file and adds each
     * nonterminal's possible values to a HashMap.
     *
     * @param fileName - Location and name of file
     */
    public static void parseFile(String fileName) {
        try {
            String currentLine;
            String key;
            Scanner scn = new Scanner(new File(fileName));

            ArrayList<String> list;

            while (scn.hasNextLine()) {
                currentLine = scn.nextLine();

                // Check for an open bracket
                if (currentLine.compareTo("{") == 0) {
                    list = new ArrayList<>();
                    if (scn.hasNextLine()) {
                        currentLine = scn.nextLine();
                        // Set key [non-terminal]
                        key = currentLine;

                        while (scn.hasNextLine()) {
                            currentLine = scn.nextLine();

                            // Check for closed bracket & add to HashMap.
                            if (currentLine.compareTo("}") == 0) {
                                assert key != null;
                                table.put(key, list);
                                break;
                            }

                            // Build List [terminal]
                            list.add(currentLine);
                        }
                    }
                }
            }
            scn.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage() + " File Not Found.");
        }
    }
}