package assign09;

import java.sql.Array;
import java.util.*;

/**
 * Timer for Assignment 09, HashTable
 *
 * @author Robert Davidson
 * @author Kyle Kazemini
 * @version March 25, 2020
 */
public class Timer09 {


    private static final String abcz = "abcdefghijklmnopqrstuvwxyz";
    private static Random random = new Random();

    public static void main(String[] args) {

        int timesToLoop = 4000;

        int incr = 250;
        int min = 1000;
        int max = 7000;

        ArrayList<StudentBadHash> bad = new ArrayList<>();
        ArrayList<StudentMediumHash> medium = new ArrayList<>();
        ArrayList<StudentGoodHash> good = new ArrayList<>();

        ArrayList<Integer> year = new ArrayList<>();

        ArrayList<Integer> uid = new ArrayList<>();
        ArrayList<String> last = new ArrayList<>();
        ArrayList<String> first = new ArrayList<>();

        ArrayList<String> dataBad = new ArrayList<>();
        dataBad.add("[N] [Time] [Avg Collisions]");

        ArrayList<String> dataMedium = new ArrayList<>();
        dataMedium.add("[N] [Time] [Avg Collisions]");

        ArrayList<String> dataGood = new ArrayList<>();
        dataGood.add("[N] [Time] [Avg Collisions]");

        // Generate UID
        int number;
        int go = 1;

        for (int i = 0; i < max; ++i) {
            go = 1;
            while (go == 1) {
                number = 1000000 + random.nextInt(8999999);
                if (!uid.contains(number)) {
                    go = 0;
                    uid.add(number);
                }
            }
        }

        // Generate Last Name
        int lengthLast;

        for (int i = 0; i < max; ++i) {
            lengthLast = 3 + random.nextInt(12);
            last.add(randomStringGenerator(lengthLast));
        }

        // Generate First Name
        int lengthFirst;

        for (int i = 0; i < max; ++i) {
            lengthFirst = 2 + random.nextInt(8);
            first.add(randomStringGenerator(lengthFirst));
        }

        // Add to Hash
        for (int i = 0; i < max; ++i) {
            bad.add(new StudentBadHash(uid.get(i), last.get(i), first.get(i)));
            medium.add(new StudentMediumHash(uid.get(i), last.get(i), first.get(i)));
            good.add(new StudentGoodHash(uid.get(i), last.get(i), first.get(i)));

            year.add(random.nextInt(3) + 1);
        }

        for (int probSize = min; probSize <= max; probSize += incr) {

            HashTable<StudentBadHash, Integer> badTable = new HashTable<StudentBadHash, Integer>();
            HashTable<StudentMediumHash, Integer> mediumTable = new HashTable<StudentMediumHash, Integer>();
            HashTable<StudentGoodHash, Integer> goodTable = new HashTable<StudentGoodHash, Integer>();

            long stopTime_bad, startTime_bad;
            long addRunTime_bad = 0;

            //////////// BAD HASH ////////////
            for (int j = 0; j < timesToLoop; j++) {
                for (int i = 0; i < probSize; i++) {
                    startTime_bad = System.nanoTime();

                    // Process Here
                    badTable.put(bad.get(i), year.get(i));
                    // Process Stops

                    stopTime_bad = System.nanoTime();
                    addRunTime_bad += stopTime_bad - startTime_bad;
                }
                badTable.clear();
            }

            double avgAddTime_bad = addRunTime_bad / (timesToLoop * probSize);
            int AvgBadCol = badTable.getCollisions() / timesToLoop;
            dataBad.add(probSize + "\t" + avgAddTime_bad + "\t" + AvgBadCol);



            long stopTime_medium, startTime_medium;
            long addRunTime_medium = 0;

            //////////// MEDIUM HASH ////////////
            for (int j = 0; j < timesToLoop; j++) {
                for (int i = 0; i < probSize; i++) {
                    startTime_medium = System.nanoTime();

                    // Process Here
                    mediumTable.put(medium.get(i), year.get(i));
                    // Process Stops

                    stopTime_medium = System.nanoTime();
                    addRunTime_medium += stopTime_medium - startTime_medium;
                }
                mediumTable.clear();
            }

            double avgAddTime_medium = addRunTime_medium / (timesToLoop * probSize);

            int AvgMediumCol = mediumTable.getCollisions() / timesToLoop;
            dataMedium.add(probSize + "\t" + avgAddTime_medium + "\t" + AvgMediumCol);

            long stopTime_good, startTime_good;
            long addRunTime_good = 0;

            //////////// GOOD HASH ////////////
            for (int j = 0; j < timesToLoop; j++) {
                for (int i = 0; i < probSize; i++) {
                    startTime_good = System.nanoTime();

                    // Process Here
                    goodTable.put(good.get(i), year.get(i));
                    // Process Stops

                    stopTime_good = System.nanoTime();
                    addRunTime_good += stopTime_good - startTime_good;
                }
                goodTable.clear();
            }

            double avgAddTime_good = addRunTime_good / (timesToLoop * probSize);
            int AvgGoodCol = goodTable.getCollisions() / timesToLoop;
            dataGood.add(probSize + "\t" + avgAddTime_good + "\t" + AvgGoodCol);
        }

        System.out.println("\n \n Bad \n");
        for (int i = 0; i < dataBad.size(); ++i)
            System.out.println(dataBad.get(i));
        System.out.println("\n \n Medium \n");
        for (int i = 0; i < dataMedium.size(); ++i)
            System.out.println(dataMedium.get(i));
        System.out.println("\n \n Good \n");
        for (int i = 0; i < dataGood.size(); ++i)
            System.out.println(dataGood.get(i));
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