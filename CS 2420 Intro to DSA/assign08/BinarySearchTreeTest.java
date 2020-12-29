package assign08;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tester class for Binary Search Tree
 * 
 * @author Kyle Kazemini && Joseph Norton
 */
class BinarySearchTreeTest {
	
	public BinarySearchTree<Integer> intTree = new BinarySearchTree<Integer>();
	public ArrayList<Integer> intArray = new ArrayList<Integer>();

	@BeforeEach
	void setup() {		
		intArray.add(1);
		intArray.add(3);
		intArray.add(2);
		intArray.add(5);
		intArray.add(4);
		intArray.add(10);
		intArray.add(0);
		
		intTree.addAll(intArray);
	}
	
	@Test
	void testFindMax() {
		int max = intTree.findMax();
		int last = intTree.last();
		assertEquals(10, max);
		assertEquals(10, last);
	}
	
	@Test
	void testFindMin() {
		int min = intTree.findMin();
		int first = intTree.first();
		assertEquals(0, min);
		assertEquals(0, first);
		intTree.remove(0);
		min = intTree.findMin();
		assertEquals(1, min);
	}
	
	@Test
	void testAddAndContains() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
		assertTrue(tree.add(5));
		assertTrue(tree.add(6));
		assertTrue(tree.add(7));
		assertTrue(tree.add(3));
		assertFalse(tree.add(5));
		
		assertEquals(4, tree.size());
		assertTrue(tree.contains(7));
		assertTrue(tree.contains(3));
		assertFalse(tree.contains(8));
		
		assertTrue(intTree.containsAll(intArray));
	}
	
	@Test
	void testClear() {
		assertFalse(intTree.isEmpty());
		intTree.clear();
		assertTrue(intTree.isEmpty());
		assertTrue(intTree.size() == 0);
	}
	
	@Test
	void testRemove() {
		assertEquals(7, intTree.size());
		assertTrue(intTree.remove(5));
		assertEquals(6, intTree.size());
		assertFalse(intTree.contains(5));
		assertFalse(intTree.remove(5));
		assertFalse(intTree.contains(5));
		
		intArray.remove(3);
		assertTrue(intTree.removeAll(intArray));
		System.out.println(intTree.toString());
		assertTrue(intTree.isEmpty());
	}
	
	@Test
	void testToArrayList() {
		ArrayList<Integer> list = intTree.toArrayList();
		ArrayList<Integer> trueList = new ArrayList<Integer>();
		
		trueList.add(0);
		trueList.add(1);
		trueList.add(2);
		trueList.add(3);
		trueList.add(4);
		trueList.add(5);
		trueList.add(10);
		
		for(int i = 0; i < list.size(); i++) {
			assertEquals(trueList.get(i), list.get(i));
		}
			
	}

}
