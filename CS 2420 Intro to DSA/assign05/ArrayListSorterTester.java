package assign05;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import assign05.ArrayListSorter;

/**
 * Tester class for ArrayListSorter
 * 
 * @author Kyle Kazemini & Anna Shelukha
 */
public class ArrayListSorterTester
{

    private ArrayList<Integer> listOne;
    private ArrayList<Integer> listTwo;
    private ArrayList<String> stringOne;
    private ArrayList<String> stringTwo;
    private ArrayList<Integer> longListOne;
    private ArrayList<Integer> longListTwo;
    private ArrayList<String> longStringOne;
    private ArrayList<String> longStringTwo;
    private ArrayList<Integer> empty;

    //added
    private ArrayList<Integer> sortedListOne;
    private ArrayList<String> sortedStringOne;
    
    @BeforeEach
    void setup ()
    {

        listOne = new ArrayList<Integer>();
        listOne.add(1);
        listOne.add(3);
        listOne.add(-2);
        listOne.add(0);

        sortedListOne = new ArrayList<Integer>();
        sortedListOne.add(-2);
        sortedListOne.add(0);
        sortedListOne.add(1);
        sortedListOne.add(3);

        listTwo = new ArrayList<Integer>();
        listTwo.add(7);
        listTwo.add(2);
        listTwo.add(-6);
        listTwo.add(4);

        stringOne = new ArrayList<String>();
        stringOne.add("Hello");
        stringOne.add("Hi");
        stringOne.add("Who");
        stringOne.add("They");
        
        sortedStringOne = new ArrayList<String>();
        sortedStringOne.add("Hello");
        sortedStringOne.add("Hi");
        sortedStringOne.add("They");
        sortedStringOne.add("Who");

        stringTwo = new ArrayList<String>();
        stringTwo.add("John");
        stringTwo.add("Dave");
        stringTwo.add("Daniel");
        stringTwo.add("Buzz");

        longListOne = new ArrayList<Integer>();
        longListOne.add(1);
        longListOne.add(6);
        longListOne.add(7);
        longListOne.add(-3);
        longListOne.add(-4);
        longListOne.add(2);
        longListOne.add(2);
        longListOne.add(2);
        longListOne.add(2);
        longListOne.add(2);

        longListTwo = new ArrayList<Integer>();
        longListTwo.add(3);
        longListTwo.add(3);
        longListTwo.add(3);
        longListTwo.add(3);
        longListTwo.add(3);
        longListTwo.add(3);
        longListTwo.add(3);
        longListTwo.add(3);
        longListTwo.add(3);
        longListTwo.add(3);

        longStringOne = new ArrayList<String>();
        longStringOne.add("Whom");
        longStringOne.add("helicopter");
        longStringOne.add("dog");
        longStringOne.add("cat");
        longStringOne.add("Kevin");
        longStringOne.add("Michael");
        longStringOne.add("Scott");
        longStringOne.add("Will");
        longStringOne.add("spider");
        longStringOne.add("dinosaur");

        longStringTwo = new ArrayList<String>();
        longStringTwo.add("Exactly");
        longStringTwo.add("Excited");
        longStringTwo.add("Sofa");
        longStringTwo.add("chair");
        longStringTwo.add("loop");
        longStringTwo.add("java");
        longStringTwo.add("hydro");
        longStringTwo.add("frame");
        longStringTwo.add("mouse");
        longStringTwo.add("ring");

        empty = new ArrayList<Integer>();
    }

	@Test
	public void testIntMerge() {
		ArrayListSorter.mergesort(listOne);
		assertEquals(listOne, sortedListOne);
	}

	@Test
	public void testIntQuick() {
		ArrayListSorter.quicksort(listOne);
		assertEquals(listOne, sortedListOne);
	}

	@Test
	public void testStringMerge() {
		ArrayListSorter.mergesort(stringOne);
		assertEquals(stringOne, sortedStringOne);
	}

	@Test
	public void testStringQuick() {
		ArrayListSorter.quicksort(stringOne);
		assertEquals(stringOne, sortedStringOne);
	}

	@Test
	public void testIntIdenticalMerge() {
		ArrayListSorter.mergesort(longListTwo);
		assertEquals(longListTwo, longListTwo);
	}

	@Test
	public void testIntIdenticalQuick() {
		ArrayListSorter.quicksort(longListTwo);
		assertEquals(longListTwo, longListTwo);
	}

	@Test
	public void testIntEmptyMerge() {
		ArrayListSorter.mergesort(empty);
		assertEquals( empty, empty);
	}

	@Test
	public void testIntEmptyQuick() {
		ArrayListSorter.quicksort(empty);
		assertEquals(empty,empty);
	}

	@Test
	public void testGenerateAscending() {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList = ArrayListSorter.generateAscending(5);
		assertTrue(intList.get(1) < intList.get(4));
	}

	@Test
	public void testGenerateDescending() {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList = ArrayListSorter.generateDescending(5);
		assertTrue(intList.get(1) > intList.get(4));
	}

	@Test
	public void testGeneratePermuted() {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList = ArrayListSorter.generatePermuted(5);
		assertFalse(
				intList.get(1) < intList.get(2) && intList.get(2) < intList.get(3) && intList.get(3) < intList.get(4));
	}

	@Test
	public void testGenerateAscendingLarge() {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList = ArrayListSorter.generateAscending(500);
		assertTrue(intList.get(0) < intList.get(499));
	}

	@Test
	public void testGenerateDescendingLarge() {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList = ArrayListSorter.generateDescending(500);
		assertTrue(intList.get(0) > intList.get(499));
	}

}
