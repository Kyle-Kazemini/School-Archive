package assign06;

import java.util.Random;

/**
 * This class collects running times for methods of LinkedListStack and
 * ArrayStack.
 * 
 * @author Erin Parker & Kyle Kazemini & Anna Shelukha
 * @version February 24, 2020
 */
public class StackTimer {

	public static void timeArrayStack() {

		int timesToLoop = 100;

		int incr = 100000;
		for (int probSize = 1000000; probSize <= 3000000; probSize += incr) {

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.

			long stopTime, midpointTime, startTime = System.nanoTime();

			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}
			
			//Fill up an ArrayStack with problem size number of elements
			ArrayStack<String> arr = new ArrayStack<String>();
			String s = createString(10);
			for (int i = 0; i < probSize; i++) {
				s = createString(10);
				arr.push(s);
			}
			
			// Collect running times.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				arr.peek();
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

	public static void timeLinkedListStack() {

		int timesToLoop = 100;

		int incr = 100000;
		for (int probSize = 1000000; probSize <= 3000000; probSize += incr) {

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.

			long stopTime, midpointTime, startTime = System.nanoTime();

			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			//Fill up a LinkedListStack with problem size number of elements
			LinkedListStack<String> list = new LinkedListStack<String>();
			String s = createString(10);
			for (int i = 0; i < probSize; i++) {
				s = createString(10);
				list.push(s);
			}
			
			// Collect running times.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				list.peek();
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

		//timeArrayStack();

		timeLinkedListStack();
	}

	public static String createString(int length) {
		Random rng = new Random();
		String value = "";

		for (int i = 0; i < length; i++)
			value += (char) ('a' + rng.nextInt(26));

		return value;
	}
}