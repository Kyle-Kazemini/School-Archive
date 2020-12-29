package assign01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This tester class assesses the correctness of the Vector class.
 * 
 * IMPORTANT NOTE: The tests provided to get you started rely heavily on a 
 *                 correctly implemented equals method.  Be careful of false
 *                 positives (i.e., tests that pass because your equals method
 *                 incorrectly returns true). 
 * 
 * @author Erin Parker & Kyle Kazemini
 * @version January 9, 2020
 */
class MathVectorJUnitTester {
	
	private MathVector rowVec, rowVecTranspose, unitVec, sumVec, colVec;

	@BeforeEach
	void setUp() throws Exception {
		// Creates a row vector with three elements: 3.0, 1.0, 2.0
		rowVec = new MathVector(new double[][]{{3, 1, 2}});
		
		// Creates a column vector with three elements: 3.0, 1.0, 2.0
		rowVecTranspose = new MathVector(new double[][]{{3}, {1}, {2}});
		
		// Creates a row vector with three elements: 1.0, 1.0, 1.0
		unitVec = new MathVector(new double[][]{{1, 1, 1}});
		
		// Creates a row vector with three elements: 4.0, 2.0, 3.0
		sumVec = new MathVector(new double[][]{{4, 2, 3}});
		
		// Creates a column vector with five elements: -11.0, 2.5, 36.0, -3.14, 7.1
		colVec = new MathVector(new double[][]{{-11}, {2.5}, {36}, {-3.14}, {7.1}});		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void smallRowVectorEquality() {
		assertTrue(rowVec.equals(new MathVector(new double[][]{{3, 1, 2}})));
	}
	
	@Test
	void smallRowVectorInequality() {
		assertFalse(rowVec.equals(unitVec));
	}

	@Test
	public void createVectorFromBadArray() {
	  double arr[][] = {{1, 2}, {3, 4}};
	  assertThrows(IllegalArgumentException.class, () -> { new MathVector(arr); });
	  // NOTE: The code above is an example of a lambda expression. See Lab 1 for more info.
	}
	
	@Test
	void transposeSmallRowVector() {
		MathVector transposeResult = rowVec.transpose();
		assertTrue(transposeResult.equals(rowVecTranspose));
	}
	
	@Test
	public void addRowAndColVectors() {
	  assertThrows(IllegalArgumentException.class, () -> { rowVec.add(colVec); });
	  // NOTE: The code above is an example of a lambda expression. See Lab 1 for more info.
	}
	
	@Test
	void addSmallRowVectors() {
		MathVector addResult = rowVec.add(unitVec);
		assertTrue(addResult.equals(sumVec));		
	}

	@Test
	void dotProductSmallRowVectors() {
		double dotProdResult = rowVec.dotProduct(unitVec);
		assertEquals(dotProdResult, 3.0 * 1.0 + 1.0 * 1.0 + 2.0 * 1.0);		
	}
	
	@Test
	void smallRowVectorLength() {
		double vecLength = rowVec.magnitude();
		assertEquals(vecLength, Math.sqrt(3.0 * 3.0 + 1.0 * 1.0 + 2.0 * 2.0));		
	}
	
	@Test
	void smallRowVectorNormalize() {
		MathVector normalVec = rowVec.normalize();
		double length = Math.sqrt(3.0 * 3.0 + 1.0 * 1.0 + 2.0 * 2.0);
		assertTrue(normalVec.equals(new MathVector(new double[][]{{3.0 / length, 1.0 / length, 2.0 / length}})));		
	}
	
	@Test
	void smallColVectorToString() {
		String resultStr = "-11.0\n2.5\n36.0\n-3.14\n7.1";
		assertEquals(resultStr, colVec.toString());		
	}

	@Test
	void rowAndColVectorInequality () {
		MathVector equality = new MathVector(new double[][] {{12}, {3.29}, {-2.3}});
		assertFalse(rowVec.equals(equality));
	}
		
	@Test
	void differentSizeRowVectorInequality () {
		MathVector longerRow = new MathVector(new double[][] {{1.3, 1, 2, 3, -5}});
		assertFalse(rowVec.equals(longerRow));
	}
	
	@Test
    void longRowVectorTranspose () {
		MathVector longerRow = new MathVector(new double [][] {{1.3, 1, 2, 3, -5}});
        assertTrue(longerRow.transpose().equals(new MathVector(new double[][] {{1.3}, {1}, {2}, {3}, {-5}})));
	}
    
	@Test
	void longColVectorTranspose () {
		MathVector longerCol = new MathVector(new double[][] {{1.3}, {1}, {2}, {3}, {-5}});
		assertTrue(longerCol.transpose().equals(new MathVector(new double[][] {{1.3, 1, 2, 3, -5}})));
	}
	
	@Test
	void addDifferentDimensions () {
		MathVector longerCol = new MathVector(new double[][] {{5}, {1}, {-3.1}, {3}, {-5}});
		MathVector shortCol = new MathVector(new double[][] {{2}, {-1}, {4}});
		assertThrows(IllegalArgumentException.class, () -> { shortCol.add(longerCol); });
	}
	
	@Test
	void addNegatives () {
        MathVector rowOne = new MathVector(new double[][] {{-1, 1, 2}});
		MathVector rowTwo = new MathVector(new double[][] {{4, -3, -2}});
		MathVector sum = new MathVector(new double[][] {{3, -2, 0}});
        assertTrue(rowOne.add(rowTwo).equals(sum));
	}
	
	@Test
	void addZeroVector () {
		MathVector columnVec = new MathVector(new double[][] {{-1}, {4}, {3.4}});
		MathVector zeroVector = new MathVector(new double[][] {{0}, {0}, {0}});
		assertTrue(columnVec.add(zeroVector).equals(columnVec));
	}
	
	@Test
	void negativesInVectorDotProduct () {
		MathVector longerCol = new MathVector(new double[][] {{5}, {1}, {-3.1}, {3}, {-5}});
		double dotProdResult = longerCol.dotProduct(longerCol);
		double dotProduct = 5 * 5 + 1 * 1 + (-3.1 * -3.1) + 3 * 3 + (-5 * -5);
		assertEquals(dotProdResult, dotProduct);		
	}
	
	@Test
	void singleEntryDotProduct () {
		MathVector singleEntry = new MathVector(new double[][] {{2}});
		double dotProdResult = singleEntry.dotProduct(singleEntry);
		double dotProduct = 4;
		assertEquals(dotProdResult, dotProduct);
	}
	
	@Test
	void longRowVectorMagnitude () {
		MathVector longerRow = new MathVector(new double[][] {{1.3, 1, 2, 3, -5}});
		double magnitude = Math.sqrt(1.3 * 1.3 + 1 * 1 + 2 * 2 + 3 * 3 + (-5 * -5));
		double vectorLength = longerRow.magnitude();
        assertEquals(magnitude, vectorLength);
	}
	
	@Test
	void singleEntryMagnitude () {
		MathVector singleEntry = new MathVector(new double[][] {{2}});
		double magnitude = Math.sqrt(2 * 2);
		double vectorLength = singleEntry.magnitude();
		assertEquals(magnitude, vectorLength);
	}
	
	@Test
	void normalizeRowVector () {
		MathVector vector = new MathVector(new double[][] {{2, 3.5, -4}});
		double length = Math.sqrt(2 * 2 + 3.5 * 3.5 + (-4 * -4));
		MathVector normalVec = vector.normalize();
		assertTrue(normalVec.equals(new MathVector(new double[][]{{2 / length, 3.5 / length, -4 / length}})));
	}
	
	@Test
	void normalizeColVector () {
		MathVector colVector = new MathVector(new double [][] {{1.3}, {2}, {-6}});
		double length = Math.sqrt(1.3 * 1.3 + 2 * 2 + (-6 * -6));
		MathVector normalVec = colVector.normalize();
		assertTrue(normalVec.equals(new MathVector(new double[][]{{1.3 / length}, {2 / length}, {-6 / length}})));
	}
	
	@Test
	void longRowVectorToString () {
		MathVector longerRow = new MathVector(new double [][] {{1.3, 1, 2, 3, -5}});
		String result = "1.3 1.0 2.0 3.0 -5.0";
		assertEquals(result, longerRow.toString());
	}
	
	@Test
	void longColVectorToString () {
		MathVector longerCol = new MathVector(new double[][] {{5}, {1}, {-3.1}, {3}, {-5}});
        String result = "5.0\n1.0\n-3.1\n3.0\n-5.0";
        assertEquals(result, longerCol.toString());		
	}
	
}