package assign03;

import java.util.Random;

public class MinTimer {

	public static void main(String[] args) {
		Random rng = new Random();

		int timesToLoop = 10000;

		int incr = 100000;
		for(int probSize = 100000; probSize <= 2000000; probSize += incr) {

			SimplePriorityQueue<Integer> s = new SimplePriorityQueue<Integer>();

			for(int i = 0; i < probSize; i++) 
				s.insert(probSize - i);    // best case of insert: logN + 1 -> O(logN)

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.

			long stopTime, midpointTime, startTime = System.nanoTime();

			while(System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Collect running times.
			startTime = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++)
				s.findMin();

			midpointTime = System.nanoTime();

			// Capture the cost of running the loop and any other operations done
			// above that are not the essential method call being timed.
			for(int i = 0; i < timesToLoop; i++) {
				
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

}
