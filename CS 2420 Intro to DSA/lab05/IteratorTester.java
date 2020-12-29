package lab05;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class tests the use of iterators in Assignment 3's 
 * SimplePriorityQueue, for Lab 5.
 * 
 * @author Ryan Sargent & Erin Parker
 * @version February 7, 2020
 */
public class IteratorTester {
	
	private SimplePriorityQueue<Integer> smallSetOfEvens;
	private SimplePriorityQueue<Integer> largeSet;
	private Iterator<Integer> smallIterator;

	@BeforeEach
	void setUp() throws Exception {
		smallSetOfEvens = new SimplePriorityQueue<Integer>();
		for(int half = 1; half <= 10; half++) 
			smallSetOfEvens.insert(half*2); 
		// priority queue's backing array should be { 20, 18, 16, ..., 6, 4, 2 }
		
		smallIterator = smallSetOfEvens.iterator();
		
		largeSet = new SimplePriorityQueue<>();
		// Fills set with numbers 1 - 100 using Java 8 Streams!
		IntStream.rangeClosed(1, 100).forEach(largeSet::insert);
		// priority queue's backing array should be { 100, 99, 98, ..., 3, 2, 1 }
	}

	@Test
	public void iterateOverEvenSet() {
		for(int expected = 20; expected >= 2; expected -= 2) 
			assertEquals(expected, (int)smallIterator.next());
	}
	
	@Test
	public void expectedNoSuchElementThrown() {
		for(int count = 0; count < 10; count++) {
			smallIterator.next();
		}
		assertThrows(NoSuchElementException.class, () -> { smallIterator.next(); });
	}
	
	@Test
	public void hasNextReturnsTrue() {
		assertTrue(smallIterator.hasNext());
	}
	
	@Test
	public void hasNextReturnsFalseAtEnd() {
		for(int count = 0; count < 10; count++) {
			smallIterator.next();
		}
		assertFalse(smallIterator.hasNext());
	}
	
	@Test
	public void removeMinElement() {
		for(int i = 0; i < 10; i++) 
			smallIterator.next();
		smallIterator.remove();  // removes 2
		assertEquals(4, (int)smallSetOfEvens.findMin());  // now min is 4
	}
	
	@Test
	public void removeWithoutCallToNext() {
		assertThrows(IllegalStateException.class, () -> { smallIterator.remove(); });
	}
	
	@Test
	public void removeEverything() {
		while(smallIterator.hasNext()) {
			smallIterator.next();
			smallIterator.remove();
		}
		assertEquals(0, smallSetOfEvens.size());
	}
	
	@Test
	public void removeEveryOtherElement() {
		Iterator<Integer> iterator = largeSet.iterator();
		while(iterator.hasNext()) {
			iterator.next();
			iterator.remove();
			if(iterator.hasNext()) {
				iterator.next();
			} 
		}
		assertEquals(50, largeSet.size());
		// priority queue's backing array should now be { 99, 97, 95, ..., 5, 3, 1 }
		for(int half = 0; half < 50; half++) {
			assertEquals(half*2+1, (int)largeSet.deleteMin());
		}
	}
}