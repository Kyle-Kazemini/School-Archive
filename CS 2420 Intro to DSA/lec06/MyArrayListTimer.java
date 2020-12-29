package lec06;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple utility class for timing the MyArrayList.add() method
 * 
 * @author  Matthew Hooper
 * @version January 23, 2020
 */
public class MyArrayListTimer {
	
	static int timesToLoop = 10_000;
	
	/**
	 * Prints the time it takes to execute MyArrayList.add() for various MyArrayList sizes n,
	 * up to n < bound
	 * @param bound - the largest size n will be less than bound
	 */
	public static void time(int bound) {
		// For each problem size n		
		for (int n = 10; n < bound; n *= 2) {
			
			// Generate a list of random strings
			ArrayList<String> randomStringList = generateStringList(bound);
			
			long startTime, stopTime, totalTime;
			
			// Busy loop for 1 second to allow thread to stabilize
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1_000_000_000);
			
			totalTime = 0;
			
			for (int i = 0; i < timesToLoop; i++) {
				
				// Instantiate a new empty MyArrayList
				MyArrayList<String> testList = new MyArrayList<>();
				
				// Populate with n items;
				for (int j = 0; j < n; j++) {
					testList.add(randomStringList.get(j));
				}
				
				String s = "CS2420";
				// Time how long it takes to add a new item
				//  when the list already has n items in it
				startTime = System.nanoTime();
				testList.add(s);
				stopTime = System.nanoTime();
				
				totalTime += stopTime - startTime;
			}
			
			double averageTime = totalTime / timesToLoop;
			
			System.out.println("n: " + n + "\t" + "time: " + averageTime);
		}
	}
	
	/**
	 * Generates a list of randomized numeric Strings
	 * @param size - size of the list to return
	 * @return - a list of randomized numbers
	 */
	private static ArrayList<String> generateStringList(int size) {
		ArrayList<String> list = new ArrayList<>();
		Random rng = new Random();
		
		for (int i = 0; i < size; i++) {
			list.add(rng.nextInt(size) + "");
		}
		
		return list;
	}
	
	public static void main(String[] args) {
		time(10241);
		System.out.println("done timing");
	}
}
