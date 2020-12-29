package comprehensive;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Timer for RandomPhraseGenerator
 * 
 * @author Kyle Kazemini && Robert Davidson
 */
public class RPGTimer_PoeticSentence {
	
	public static void time() {

		int timesToLoop = 500000;

		int incr = 10;
		for (int probSize = 90; probSize <= 150; probSize += incr) {

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.

			long stopTime, midpointTime = System.nanoTime();
			long startTime = 0;

			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Setup
//			nonTerminals(probSize);
//			productions(probSize);

			String[] inputs = {"src/comprehensive/poetic_sentence.g", "1"};

			// Collect running times.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {

				RandomPhraseGenerator.main(inputs);
				
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
	
	private static void nonTerminals(int size) {
		try {
			PrintWriter file = new PrintWriter(new FileWriter("poetic_sentence.g"));
			
			for (int i = 0; i < size; i++) {
				file.println("{");
				file.println(i);
				file.println("}");
			}
			file.close();
		} 
		catch (IOException e) {
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
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
