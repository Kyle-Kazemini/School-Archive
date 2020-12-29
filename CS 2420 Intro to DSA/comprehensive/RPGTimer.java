package comprehensive;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Timer for RandomPhraseGenerator
 *
 * @author Kyle Kazemini && Robert Davidson
 */
public class RPGTimer {

    public static void time() {

        int timesToLoop = 100000;

        int incr = 100;
        for (int probSize = 100; probSize <= 2000; probSize += incr) {

            // First, spin computing stuff until one second has gone by.
            // This allows this thread to stabilize.

            long stopTime, midpointTime, startTime = System.nanoTime();

            while (System.nanoTime() - startTime < 1000000000) { // empty block
            }

            // Setup
//            analysisPartA(probSize, 2000);
//            productions(probSize);
            String[] inputs = {"poetic_sentence.g", "" + 1};
            String[] inputs2 = {"src/comprehensive/poetic_sentence.g", "" + probSize / 10};

            // Collect running times.
            startTime = System.nanoTime();
            for (int i = 0; i < timesToLoop; i++) {

//                RandomPhraseGenerator.main(inputs);
                RandomPhraseGenerator.main(inputs2);

            }
            midpointTime = System.nanoTime();

            // Capture the cost of running the loop and any other operations done
            // above that are not the essential method call being timed.
            for (int i = 0; i < timesToLoop; i++) {

            }

            stopTime = System.nanoTime();

            // Compute the time, subtract the cost of running the loop
            // from the cost of running the loop and searching.
            // Average it over the number of runs.
            double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / (double) timesToLoop;
            System.out.println(probSize + "  " + averageTime);

        }
    }

    public static void main(String[] args) {
        time();
    }

    private static void analysisPartA(int size, int maxProbSize) {
        try {
            PrintWriter file = new PrintWriter(new FileWriter("poetic_sentence.g"));

            Random rng = new Random();
			file.println("{");
			file.println("<start>");
			file.println("<1> <1> <1> <1> <1> <1> <1> <1> <1> <1>");
			file.println("}");

            int count = 0;
			int factor = size / 100;


            for (int i = 0; i < 25; i++) {
				file.println("{");

				// Key
                file.println("<" + i + ">");

                // Terminal
//                file.println(i);

//                int x = Math.abs(rng.nextInt(25) - 1);
                int x = i + 1;
                if (count < factor) {
                    ++count;
                	file.println(i + " FILLER FILLER FILLER FILLER FILLER" + "<" + x + ">");
                    file.println(i + " FILLER FILLER FILLER FILLER FILLER" + "<" + x + ">");
                    file.println(i + " FILLER FILLER FILLER FILLER FILLER" + "<" + x + ">");
                    file.println(i + " FILLER FILLER FILLER FILLER FILLER" + "<" + x + ">");
                    file.println(i + " FILLER FILLER FILLER FILLER FILLER" + "<" + x + ">");
//                    file.println(i + " FILLER FILLER FILLER FILLER FILLER");
				} else {
                    count = 0;
                    file.println(i + " FILLER FILLER FILLER FILLER FILLER");
				}

				file.println("}");
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void productions(int size) {
        try {
            PrintWriter file = new PrintWriter(new FileWriter("poetic_sentence.g"));

            for (int i = 0; i < size; i++) {
                file.println("{");
                file.println("<start>");
                file.println(i);
                file.println("}");
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
