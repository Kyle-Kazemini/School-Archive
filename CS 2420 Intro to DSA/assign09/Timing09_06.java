package assign09;

import java.sql.Array;
import java.util.*;

/**
 * Timer for Assignment 09: Analysis 6, HashTable
 *
 * @author Robert Davidson
 * @author Kyle Kazemini
 * @version March 25, 2020
 */
public class Timing09_06 {


    private static final String abcz = "abcdefghijklmnopqrstuvwxyz";
    private static Random random = new Random();

    public static void main(String[] args) {

        int timesToLoop = 500000;

        int incr = 1000;
        int min = 1000;
        int max = 60000;


        ArrayList<String> s = new ArrayList<>();
        ArrayList<String> data_m = new ArrayList<>();
        ArrayList<String> data_t = new ArrayList<>();
        ArrayList<String> add = new ArrayList<>();

        for (int i = 0; i < max; ++i) {
            int j = 3 + random.nextInt(8);
            s.add(randomStringGenerator(j));
        }
        for (int i = 0; i < max; ++i) {
            int j = 3 + random.nextInt(8);
            add.add(randomStringGenerator(j));
        }


        for (int probSize = min; probSize <= max; probSize += incr) {

            HashTable<String, Integer> table = new HashTable<>();
            HashMap<String, Integer> mtable = new HashMap<>();

            long mstopTime, mstartTime;
            long maddRunTime = 0;

            int x = random.nextInt(max - 1);

            for (int i = 0; i < probSize; i++) {
                mtable.put(s.get(i), s.get(i).length());
            }

            //////////// HASHMAP ////////////
            for (int j = 0; j < timesToLoop; j++) {
                mstartTime = System.nanoTime();

                // Process Here
                mtable.put(add.get(x), add.get(x).length());
                // Process Stops

                mstopTime = System.nanoTime();
                maddRunTime += mstopTime - mstartTime;
//                mtable.clear();
            }

            double mavgAddTime = maddRunTime / (timesToLoop /*probSize*/);
            data_m.add(probSize + "\t" + mavgAddTime);


            for (int i = 0; i < probSize; i++) {
                table.put(s.get(i), s.get(i).length());
            }

            long tstopTime, tstartTime;
            long taddRunTime = 0;

            //////////// HASHTABLE ////////////
            for (int j = 0; j < timesToLoop; j++) {
                tstartTime = System.nanoTime();

                // Process Here
                table.put(add.get(x), add.get(x).length());
                // Process Stops

                tstopTime = System.nanoTime();
                taddRunTime += tstopTime - tstartTime;
//                table.clear();
            }

            double tavgAddTime = taddRunTime / (timesToLoop /** probSize*/);
            data_t.add(probSize + "\t" + tavgAddTime);
        }

        System.out.println("\n \n HashMap \n");
        for (int i = 0; i < data_m.size(); ++i)
            System.out.println(data_m.get(i));
        System.out.println("\n \n HashTable \n");
        for (int i = 0; i < data_t.size(); ++i)
            System.out.println(data_t.get(i));
    }

    public static String randomStringGenerator(int length) {
        if (length < 1) {
            throw new IllegalArgumentException();
        }

        StringBuilder randomString = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomCharIndex = random.nextInt(abcz.length());
            char randomChar = abcz.charAt(randomCharIndex);
            randomString.append(randomChar);
        }
        return randomString.toString();
    }
}