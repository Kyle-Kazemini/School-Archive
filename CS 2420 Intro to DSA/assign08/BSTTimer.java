package assign08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class BSTTimer {

	public static void timeRandom() {

		int timesToLoop = 100;

		int incr = 10000;
		for (int probSize = 10000; probSize <= 200000; probSize += incr) {

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.

			long stopTime, midpointTime, startTime = System.nanoTime();

			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			BinarySearchTree<Integer> inOrderTree = new BinarySearchTree<Integer>();
			BinarySearchTree<Integer> randomTree = new BinarySearchTree<Integer>();
			ArrayList<Integer> list = new ArrayList<Integer>();

			for (int i = 0; i < probSize; i++) {
//				inOrderTree.add(i);
				list.add(i);
			}
			Collections.shuffle(list);
			randomTree.addAll(list);

			// Collect running times.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				
//				for (int j = 0; j < probSize; j++)
//					inOrderTree.contains(j);

				for (int j = 0; j < probSize; j++)
					randomTree.contains(j);
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

	public static void timeBalanced() {

		int timesToLoop = 100;

		int incr = 1000;
		for (int probSize = 1000; probSize <= 20000; probSize += incr) {

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.

			long stopTime, midpointTime, startTime = System.nanoTime();

			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			TreeSet<Integer> set = new TreeSet<Integer>();
			BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
			ArrayList<Integer> list = new ArrayList<Integer>();

			for (int i = 0; i < probSize; i++)
				list.add(i);
			
			Collections.shuffle(list);

			// Collect running times.
			startTime = System.nanoTime();
			for (int i = 0; i < timesToLoop; i++) {
				
				for (int j = 0; j < probSize; j++) {
//					set.add(list.get(i));
//					tree.add(list.get(i));
//					
//					set.contains(list.get(i));
					tree.contains(list.get(i));
				}
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

		timeRandom();

		timeBalanced();
	}

}
