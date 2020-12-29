package assign04;

import java.util.Random;

/**
 * This class collects running times for methods of AnagramChecker.
 * 
 * @author Erin Parker & Kyle Kazemini & Anna Shelukha
 * @version January 28, 2020
 */
public class AnagramTimer {
	
	public static void timeAreAnagrams () {

		int timesToLoop = 10;

		int incr = 1000;
		for(int probSize = 1000; probSize <= 20000; probSize += incr) {

			
			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.

			long stopTime, midpointTime, startTime = System.nanoTime();

			while(System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Collect running times.
			startTime = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++) {
				String testerOne = createString(probSize);
				String testerTwo = createString(probSize);
				
				AnagramChecker.areAnagrams(testerOne, testerTwo);
			}

			midpointTime = System.nanoTime();

			// Capture the cost of running the loop and any other operations done
			// above that are not the essential method call being timed.
			for(int i = 0; i < timesToLoop; i++) {
				String testerOne = createString(probSize);
				String testerTwo = createString(probSize);
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and searching.
			// Average it over the number of runs.
			double averageTime = ((midpointTime - startTime) - 
						(stopTime - midpointTime)) / (double) timesToLoop;
			System.out.println(probSize + "  " + averageTime);
			
		}
	}

	public static void timeLargestAnagramGroup () {

		int timesToLoop = 10;

		int incr = 1000;
		for(int probSize = 1000; probSize <= 20000; probSize += incr) {

			
			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.

			long stopTime, midpointTime, startTime = System.nanoTime();

			while(System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Collect running times.
			startTime = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++) {
				String[] array = createArray(probSize);
				
				AnagramChecker.getLargestAnagramGroup(array);
			}

			midpointTime = System.nanoTime();

			// Capture the cost of running the loop and any other operations done
			// above that are not the essential method call being timed.
			for(int i = 0; i < timesToLoop; i++) {
				String[] array = createArray(probSize);
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and searching.
			// Average it over the number of runs.
			double averageTime = ((midpointTime - startTime) - 
						(stopTime - midpointTime)) / (double) timesToLoop;
			System.out.println(probSize + "  " + averageTime);
			
		}
	}
	
	
	public static void main(String[] args) {
		
		timeAreAnagrams();
		
		timeLargestAnagramGroup();
	}
	
	public static String createString(int length) {
		Random rng = new Random();
		String value = "";
		
		for (int i = 0; i < length; i ++)
			value += (char)('a' + rng.nextInt(26));
		
		return value;
	}
	
	public static String[] createArray(int length) {
		Random rng = new Random();
		
		String[] array = new String[length];
		
		for (int i = 0; i < length; i ++)
			array[i] = createString(5);
		
		// After the array is created, permute it. 
		
		for(int i = 0; i < array.length; i++) {
			int randIdx = rng.nextInt(array.length);
			String temp = array[i];
			array[i] = array[randIdx];
			array[randIdx] = temp;
		}
		
		return array;
	}
}