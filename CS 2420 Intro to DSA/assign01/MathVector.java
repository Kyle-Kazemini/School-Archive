package assign01;

/**
 * This class represents a simple row or column vector of numbers.
 * In a row vector, the numbers are written horizontally (i.e., along the columns).
 * In a column vector, the numbers are written vertically (i.e., along the rows).
 * 
 * @author Erin Parker & Kyle Kazemini
 * @version January 9, 2020
 */
public class MathVector {

	// 2D array to hold the numbers of the vector, either along the columns or rows
	private double[][] data;      
	// set to true for a row vector and false for a column vector
	private boolean isRowVector;
	// count of elements in the vector
	private int vectorSize;
	
	/**
	 * Creates a new row or column vector.
	 * For a row vector, the input array is expected to have 1 row and a positive number of columns,
	 * and this number of columns represents the vector's length.
	 * For a column vector, the input array is expected to have 1 column and a positive number of rows,
	 * and this number of rows represents the vector's length.
	 * 
	 * @param data - a 2D array to hold the numbers of the vector
	 * @throws IllegalArgumentException if the numbers of rows and columns in the input 2D array is not 
	 *         compatible with a row or column vector
	 */
	public MathVector(double[][] data) {
		if(data.length == 0)
			throw new IllegalArgumentException("Number of rows must be positive.");
		if(data[0].length == 0)
			throw new IllegalArgumentException("Number of columns must be positive.");
		
		if(data.length == 1) {
			// This is a row vector with length = number of columns.
			this.isRowVector = true;
			this.vectorSize = data[0].length;
		}
		else if(data[0].length == 1) {
			// This is a column vector with length = number of rows.
			this.isRowVector = false;
			this.vectorSize = data.length;
		}
		else
			throw new IllegalArgumentException("Either the number of rows or the number of columns must be 1.");
		
		// Create the array and copy data over.
		if(this.isRowVector)
			this.data = new double[1][vectorSize];
		else
			this.data = new double[vectorSize][1];
		for(int i=0; i < this.data.length; i++) { 
			for(int j=0; j < this.data[0].length; j++) {
				this.data[i][j] = data[i][j];
			}
		}
	}
	
	/**
	 * Determines whether this vector is "equal to" another vector, where equality is
	 * defined as both vectors being row (or both being column), having the same 
	 * vector length, and containing the same numbers in the same positions.
	 * 
	 * @param other - another vector to compare
	 */
	public boolean equals(Object other) {
		if(!(other instanceof MathVector))
			return false;
		
		MathVector otherVec = (MathVector)other;
		
		if (isRowVector) {
			for (int i = 0; i < vectorSize; i++) {
				if (otherVec.data[0][i] != this.data[0][i])
					return false;
		    }
		}
		else {
			for (int j = 0; j < vectorSize; j++) {
				if (otherVec.data[j][0] != this.data[j][0])
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Generates a returns a new vector that is the transposed version of this vector.
	 */
	public MathVector transpose() {
		MathVector transpose = new MathVector(new double[vectorSize][1]);
		
		if (isRowVector) {
			for (int i=0; i < this.data.length; i++) { 
				for (int j=0; j < this.data[0].length; j++) {
					transpose.data[j][i] = this.data[i][j];
				}
			}
		}
		else {
			transpose = new MathVector(new double[1][vectorSize]);
			for (int i = 0; i < this.data.length; i++) { 
				for (int j = 0; j < this.data[0].length; j++) {
					transpose.data[j][i] = this.data[i][j];
				}
	    	}
	    }
		return transpose;
    }
	
	/**
	 * Generates and returns a new vector representing the sum of this vector and another vector.
	 * 
	 * @param other - another vector to be added to this vector
	 * @throws IllegalArgumentException if the other vector and this vector are not both row vectors of
	 *         the same length or column vectors of the same length
	 */
	public MathVector add(MathVector other) {
		if (other.vectorSize != this.vectorSize || other.isRowVector != this.isRowVector)
			throw new IllegalArgumentException("The two vectors need to have the same dimensions.");
		
		if (isRowVector) {
		  MathVector sum = new MathVector(new double[1][vectorSize]);
		  for (int i = 0; i < vectorSize; i++)
				sum.data[0][i] = this.data[0][i] + other.data[0][i];
		  return sum;
	    }
		else {
			MathVector sum = new MathVector(new double[vectorSize][1]);
			for (int i = 0; i < vectorSize; i++)
				sum.data[i][0] = this.data[i][0] + other.data[i][0];
			return sum;
		}
	}
	
	/**
	 * Computes and returns the dot product of this vector and another vector.
	 * 
	 * @param other - another vector to be combined with this vector to produce the dot product
	 * @throws IllegalArgumentException if the other vector and this vector are not both row vectors of
	 *         the same length or column vectors of the same length
	 */	
	public double dotProduct(MathVector other) {
		if (other.vectorSize != this.vectorSize || other.isRowVector != this.isRowVector)
			throw new IllegalArgumentException("The two vectors need to have the same dimensions.");
		
		double dotProduct = 0;
		
		if (isRowVector)
			for (int i = 0; i < vectorSize; i++)
				dotProduct = dotProduct + this.data[0][i] * other.data[0][i];
		else
			for (int i = 0; i < vectorSize; i++)
				dotProduct = dotProduct + this.data[i][0] * other.data[i][0];
		
		return dotProduct;
	}
	
	/**
	 * Computes and returns this vector's magnitude (also known as a vector's length) .
	 */
	public double magnitude() {
		double magnitude = 0;
		
		if (isRowVector) {
			for (int i = 0; i < vectorSize; i++) {
				magnitude = magnitude + this.data[0][i] * this.data[0][i];
			}
		}
		else {
			for (int i = 0; i < vectorSize; i++) {
				magnitude = magnitude + this.data[i][0] * this.data[i][0];
			}
		}
		
		magnitude = Math.sqrt(magnitude);
		return magnitude;
	}
	
	/** 
	 * Generates and returns a normalized version of this vector.
	 */
	public MathVector normalize() {
		MathVector normal = new MathVector(new double[1][vectorSize]);
		
		if (isRowVector) {
			for (int i = 0; i < vectorSize; i++)
				normal.data[0][i] = this.data[0][i] / this.magnitude();
		}
		else {
			normal = new MathVector(new double[vectorSize][1]);
			for (int i = 0; i < vectorSize; i++)
				normal.data[i][0] = this.data[i][0] / this.magnitude();		
		}
		return normal;
	}
	
	/**
	 * Generates and returns a textual representation of this vector.
	 * For example, "1.0 2.0 3.0 4.0 5.0" for a sample row vector of length 5 and 
	 * "1.0
	 *  2.0
	 *  3.0
	 *  4.0
	 *  5.0" for a sample column vector of length 5. 
	 *  In both cases, notice the lack of a newline or space after the last number.
	 */
	public String toString() {
		String s = "";
		
		if (isRowVector) {
			for (int i = 0; i < vectorSize; i++) 
				s = s + this.data[0][i] + " ";
			s = s.trim();
		}
		else {
			for (int i = 0; i < vectorSize; i++) 
			    s = s + this.data[i][0] + "\n";
			s = s.trim(); 
			// The trim method is used after the for loop to get rid of the last space.
		}
		return s;
	}
}