package assign02;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a library, which is a collection of library books.
 * 
 * @author Erin Parker and Kyle Kazemini and Anna Shelukha
 * @version January 16, 2020
 */
public class LibraryTimer {
	
	/**
	 * Returns a library of "dummy" books (random ISBN and placeholders for
	 * author and title).
	 * 
	 * Useful for collecting running times for operations on libraries of
	 * varying size.
	 * 
	 * @param size -- size of the library to be generated
	 */
	private static ArrayList<LibraryBook> generateLibrary(int size) {
		ArrayList<LibraryBook> result = new ArrayList<LibraryBook>();

		for(int i = 0; i < size; i++) {
			result.add(new LibraryBook(generateIsbn(), "An author", "A title"));
		}

		return result;
	}

	/**
	 * Returns a randomly-generated ISBN (a long with 13 digits).
	 * 
	 * Useful for collecting running times for operations on libraries of
	 * varying size.
	 */
	private static long generateIsbn() {
		Random randomNumGen = new Random();

		String isbn = "";
		for(int j = 0; j < 13; j++)
			isbn += randomNumGen.nextInt(10);

		return Long.parseLong(isbn);
	}

	public static void main(String[] args) {
		// Do 10000 lookups and use the average running time.
	    int timesToLoop = 10000;
	    
	    // For each problem size n . . .
	    for (int n = 1000; n <= 10000; n += 1000) {

	      // Generate a new "random" library of size n.
	      Library randLib = new Library();
	      randLib.addAll(generateLibrary(n));

	      long startTime, midpointTime, stopTime;

	      // First, spin computing stuff until one second has gone by.
	      // This allows this thread to stabilize.
	      startTime = System.nanoTime();
	      while (System.nanoTime() - startTime < 1000000000) { // empty block 
	      }

	      // Now, run the test.  
	      startTime = System.nanoTime();

	      for (int i = 0; i < timesToLoop; i++) {
	        randLib.lookup(generateIsbn());
	      }

	      midpointTime = System.nanoTime();

	      // Run a loop to capture the cost of running the "timesToLoop" loop and 
	      // generating a random ISBN.
	      for (int i = 0; i < timesToLoop; i++) { 
	        generateIsbn();
	      }

	      stopTime = System.nanoTime();

	      // Compute the time, subtract the cost of running the loop
	      // from the cost of running the loop and doing the lookups.
	      // Average it over the number of runs.
	      double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime))
	          / (double) timesToLoop;

	      System.out.println(n + "\t" + averageTime);
	  }

	}

}
