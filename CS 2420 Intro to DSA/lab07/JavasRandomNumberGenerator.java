package lab07;

import java.util.Random;

/**
 * Builds an RNG simply by wrapping the java.util.Random class.
 * 
 * @author Erin Parker
 * @version February 21, 2020
 */
public class JavasRandomNumberGenerator implements RandomNumberGenerator {
	
	// a Random object to do the actual "work" of this class
	private Random rng;
	
	public JavasRandomNumberGenerator() {
		rng = new Random();
	}

	public int nextInt(int max) {
		return rng.nextInt(max);
	}

	public void setSeed(long seed) {
		rng.setSeed(seed);
	}

	public void setConstants(long const1, long const2) {
		// Body purposely omitted since constants not used.
	}
}