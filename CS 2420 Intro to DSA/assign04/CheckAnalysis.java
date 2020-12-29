package assign04;

import java.text.DecimalFormat;

/**
 * This program demonstrates how use formulas to compare the empirically observed
 * running time of a method/algorithm to the expected Big-O behavior.
 * 
 * Let T(N) be the running time observed, and let F(N) be the Big-O expected.
 * 
 * If T(N) / F(N) converges to a positive value, then F(N) correctly represents
 * the growth rate of the running times.
 * 
 * If T(N) / F(N) converges to 0, then F(N) is an overestimate of the growth
 * rate of the running times.
 * 
 * If T(N) / F(N) converges to infinity, then F(N) is an underestimate of the
 * growth rate of the running times.
 * 
 * @author Erin Parker & Kyle Kazemini & Anna Shelukha
 * @version January 28, 2020
 */
public class CheckAnalysis {
	
	// All of these values are purposely small to keep the lecture demo quick.
	// Values for your own timing experiments should be larger.
	private final static int TIMES_TO_LOOP = 10;   // In practice, this value should be larger.    
	private final static int NSTART = 1000;
	private final static int NSTOP = 10000;
	private final static int NINCR = 500;

	public static void main(String[] args) {
		
		//checkAreAnagrams();
		
		checkLargestAnagramGroup();
	}
	
	/*
	 * Check analysis method for areAnagrams
	 * Commented out code shows other cases
	 */
	public static void checkAreAnagrams() {
		DecimalFormat formatter = new DecimalFormat("00000E00");

		System.out.println("\nN\t|  T(N)/1\tT(N)/logN\tT(N)/N\t\tT(N)/NlogN\tT(N)/N^2");
		System.out.println("-----------------------------------------------------------------------------------");

		for(int N = NSTART; N <= NSTOP; N += NINCR) { 
			String testAverage1 = AnagramTimer.createString(N);
			String testAverage2 = AnagramTimer.createString(N);
			
			System.out.print(N + "\t|  ");

			// Let things stabilize
			long startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000)
				;

			// Time the routine
			startTime = System.nanoTime();
			for(int i = 0; i < TIMES_TO_LOOP; i++) {
				AnagramChecker.areAnagrams(testAverage1, testAverage2);
				//AnagramChecker.areAnagrams(testAverage1, testAverage1);
			}
			
			long midTime = System.nanoTime();

			// Time the empty loop
			for(int i = 0; i < TIMES_TO_LOOP; i++) {
				;
			}

			long stopTime = System.nanoTime();

			double avgTime = ((midTime - startTime) - (stopTime - midTime)) / (double) TIMES_TO_LOOP;

			System.out.println(formatter.format(avgTime) + "\t" + 
					formatter.format(avgTime / (Math.log10(N) / Math.log10(2))) + "\t" + 
					formatter.format(avgTime / N) + "\t" + 
					formatter.format(avgTime / (N * N)) + "\t" + 
					formatter.format(avgTime / N * (Math.log10(N) / Math.log10(2))));
		}
	}
	
	/*
	 * Check analysis method for getLargestAnagramGroup 
	 * Commented out code represents other cases
	 */
	public static void checkLargestAnagramGroup() {
		DecimalFormat formatter = new DecimalFormat("00000E00");

		System.out.println("\nN\t|  T(N)/1\tT(N)/logN\tT(N)/N\t\tT(N)/NlogN\tT(N)/N^2");
		System.out.println("-----------------------------------------------------------------------------------");

		for(int N = NSTART; N <= NSTOP; N += NINCR) { 

			System.out.print(N + "\t|  ");

			// Let things stabilize
			long startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000)
				;

			// Time the routine
			startTime = System.nanoTime();
			for(int i = 0; i < TIMES_TO_LOOP; i++) {
				String [] arr = AnagramTimer.createArray(N);
				
				AnagramChecker.getLargestAnagramGroup(arr);
			}
			
			long midTime = System.nanoTime();

			// Time the empty loop
			for(int i = 0; i < TIMES_TO_LOOP; i++) {
				;
			}

			long stopTime = System.nanoTime();

			double avgTime = ((midTime - startTime) - (stopTime - midTime)) / (double) TIMES_TO_LOOP;

			System.out.println(formatter.format(avgTime) + "\t" + 
					formatter.format(avgTime / (Math.log10(N) / Math.log10(2))) + "\t" + 
					formatter.format(avgTime / N) + "\t" + 
					formatter.format(avgTime / (N * N)) + "\t" + 
					formatter.format(avgTime / N * (Math.log10(N) / Math.log10(2))));
		}
	}
}