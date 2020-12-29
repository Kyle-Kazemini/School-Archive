package assign06;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tester class for SinglyLinkedList class that implements List interface.
 * 
 * @author Erin Parker && Kyle Kazemini && Anna Shelukha
 * @version February 20, 2020
 * 
 */
public class LinkedListTester {
	private SinglyLinkedList<Integer> intList;

	@BeforeEach
	void setup() {
		intList = new SinglyLinkedList<Integer>();
		intList.add(0, 0);
		intList.add(1, 2);
		intList.add(2, 4);
		intList.add(3, 5);
		intList.add(4, 6);
	}

	@Test
	public void getFirstTest() {
		intList.add(0, 2);
		int test = intList.getFirst();
		assertEquals(2, test);
	}

	@Test
	public void addFirstTest() {
		intList.addFirst(15);
		int test = intList.getFirst();
		assertEquals(15, test);
	}

	@Test
	public void getTest() {
		int test = intList.get(1);
		assertEquals(2, test);
	}

	@Test
	public void removeFirstTest() {
		int test = intList.removeFirst();
		assertEquals(0, test);
		int test2 = intList.getFirst();
		assertEquals(2, test2);
	}

	@Test
	public void removeTest() {
		int test = intList.remove(1);
		assertEquals(2, test);
		int test2 = intList.get(1);
		assertEquals(4, test2);
	}

	@Test
	public void indexOfTrueTest() {
		int index = intList.indexOf(4);
		assertEquals(2, index);
	}

	@Test
	public void indexOfFalseTest() {
		int index = intList.indexOf(9);
		assertEquals(-1, index);
	}

	@Test
	public void sizeTest() {
		int index = intList.size();
		assertEquals(5, index);
	}

	@Test
	public void isEmptyTest() {
		assertEquals(false, intList.isEmpty());
	}

	@Test
	public void clearTest() {
		intList.clear();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			intList.get(1);
		});
	}

	@Test
	public void toArrayTest() {
		Object[] intArr = new Object[] { 0, 2, 4, 5, 6 };
		Object[] resultArr = intList.toArray();
		assertArrayEquals(intArr, resultArr);
	}
	
	@Test
	public void testIteratorHasNext() {
		Iterator<Integer> iter = intList.iterator();
		iter.next();
		assertTrue(iter.hasNext());
		iter.next();
		iter.next();
		iter.next();
		assertTrue(iter.hasNext());
		iter.next();
		assertTrue(!iter.hasNext());
	}

	@Test
	public void testIteratorNext() {
		Iterator<Integer> iter = intList.iterator();
		iter.next();
		iter.next();
		int element = iter.next();
		assertEquals(4, element);
	}
	
	@Test 
	public void testIteratorRemove() {
		Iterator<Integer> iter = intList.iterator();
		iter.next();
		iter.next();
		iter.remove();
		int element = intList.get(2);
		assertEquals(element, 5);
	}
	
	
}
