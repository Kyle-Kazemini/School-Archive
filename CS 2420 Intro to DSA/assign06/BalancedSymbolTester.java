package assign06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

public class BalancedSymbolTester {

	@Test
	public void testNoErrors() throws FileNotFoundException {
		assertEquals("No errors found. All symbols match.",
				BalancedSymbolChecker.checkFile("src/assign06/Class13.java"));
	}
	
	@Test
	public void testNoErrors2() throws FileNotFoundException {
		assertEquals("No errors found. All symbols match.",
				BalancedSymbolChecker.checkFile("src/assign06/Class12.java"));
	}
	
	@Test
	public void testunmatchedSymbol() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at line 6 and column 1. Expected ), but read } instead.",
				BalancedSymbolChecker.checkFile("src/assign06/Class1.java"));
	}
	
	@Test
	public void testunmatchedSymbol2() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at line 7 and column 1. Expected  , but read } instead.",
				BalancedSymbolChecker.checkFile("src/assign06/Class2.java"));
	}
	
	@Test
	public void testunmatchedSymbol3() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at line 3 and column 33. Expected ], but read ) instead.",
				BalancedSymbolChecker.checkFile("src/assign06/Class7.java"));
	}
	
	@Test
	public void testunmatchedSymbol4() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at line 3 and column 33. Expected ), but read ] instead.",
				BalancedSymbolChecker.checkFile("src/assign06/Class9.java"));
	}
	
	@Test
	public void testUnfinishedComment() throws FileNotFoundException {
		assertEquals("ERROR: File ended before closing comment.",
				BalancedSymbolChecker.checkFile("src/assign06/Class4.java"));
	}
	
	@Test
	public void testUnfinishedComment2() throws FileNotFoundException {
		assertEquals("ERROR: File ended before closing comment.",
				BalancedSymbolChecker.checkFile("src/assign06/closingcomment.txt"));
	}
	
	@Test
	public void testEndOfFile() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at the end of file. Expected }.",
				BalancedSymbolChecker.checkFile("src/assign06/Class11.java"));
	}
	
	@Test
	public void testEndOfFile2() throws FileNotFoundException {
		assertEquals("ERROR: Unmatched symbol at the end of file. Expected }.",
				BalancedSymbolChecker.checkFile("src/assign06/unmatchedAEOF.txt"));
	}
}	
