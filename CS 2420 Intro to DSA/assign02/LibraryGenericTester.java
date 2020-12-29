package assign02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * This class contains tests for LibraryGeneric.
 * 
 * @author Erin Parker and Kyle Kazemini and Anna Shelukha
 * @version January 16, 2020
 */
public class LibraryGenericTester {
	
	private LibraryGeneric<String> nameLib;  // library that uses names to identify patrons (holders)
	private LibraryGeneric<PhoneNumber> phoneLib;  // library that uses phone numbers to identify patrons
	
	@BeforeEach
	void setUp() throws Exception {
		nameLib = new LibraryGeneric<String>();
		nameLib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		nameLib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		nameLib.add(9780446580342L, "David Baldacci", "Simple Genius");

		phoneLib = new LibraryGeneric<PhoneNumber>();
		phoneLib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		phoneLib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		phoneLib.add(9780446580342L, "David Baldacci", "Simple Genius");		
	}
	
	@Test
	public void testNameLibCheckout() {
		String patron = "Jane Doe";
		assertTrue(nameLib.checkout(9780330351690L, patron, 1, 1, 2008));
		assertTrue(nameLib.checkout(9780374292799L, patron, 1, 1, 2008));
	}

	@Test
	public void testNameLibLookup() {
		String patron = "Jane Doe";
		nameLib.checkout(9780330351690L, patron, 1, 1, 2008);
		nameLib.checkout(9780374292799L, patron, 1, 1, 2008);
		ArrayList<LibraryBookGeneric<String>> booksCheckedOut = nameLib.lookup(patron);
		
		assertNotNull(booksCheckedOut);
		assertEquals(2, booksCheckedOut.size());
		assertTrue(booksCheckedOut.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
		assertTrue(booksCheckedOut.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
		assertEquals(patron, booksCheckedOut.get(0).getHolder());
		assertEquals(patron, booksCheckedOut.get(1).getHolder());
	}
	
	@Test
	public void testNameLibCheckin() {
		String patron = "Jane Doe";
		nameLib.checkout(9780330351690L, patron, 1, 1, 2008);
		nameLib.checkout(9780374292799L, patron, 1, 1, 2008);
		assertTrue(nameLib.checkin(patron));
	}

	@Test
	public void testPhoneLibCheckout() {
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		assertTrue(phoneLib.checkout(9780330351690L, patron, 1, 1, 2008));
		assertTrue(phoneLib.checkout(9780374292799L, patron, 1, 1, 2008));
	}

	@Test
	public void testPhoneLibLookup() {
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		phoneLib.checkout(9780330351690L, patron, 1, 1, 2008);
		phoneLib.checkout(9780374292799L, patron, 1, 1, 2008);
		ArrayList<LibraryBookGeneric<PhoneNumber>> booksCheckedOut = phoneLib.lookup(patron);
		
		assertNotNull(booksCheckedOut);
		assertEquals(2, booksCheckedOut.size());
		assertTrue(booksCheckedOut.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
		assertTrue(booksCheckedOut.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
		assertEquals(patron, booksCheckedOut.get(0).getHolder());
		assertEquals(patron, booksCheckedOut.get(1).getHolder());
	}

	@Test
	public void testPhoneLibCheckin() {
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		phoneLib.checkout(9780330351690L, patron, 1, 1, 2008);
		phoneLib.checkout(9780374292799L, patron, 1, 1, 2008);
		assertTrue(phoneLib.checkin(patron));
	}
	
	@Test
	public void testInventoryList() {
		ArrayList<LibraryBookGeneric> inventoryList = new ArrayList<LibraryBookGeneric>();
		
		inventoryList.add(new LibraryBookGeneric(9780330351690L, "Jon Krakauer", "Into the Wild"));
		inventoryList.add(new LibraryBookGeneric(9780374292799L, "Thomas L. Friedman", "The World is Flat"));
		inventoryList.add(new LibraryBookGeneric(9780446580342L, "David Baldacci", "Simple Genius"));
		
		assertEquals(inventoryList, nameLib.getInventoryList());
	}
	
	@Test
	public void testOverdueList() {
		ArrayList<LibraryBookGeneric> overdueList = new ArrayList<LibraryBookGeneric>();
		
		overdueList.add(new LibraryBookGeneric(9780374292799L, "Thomas L. Friedman", "The World is Flat"));
		overdueList.add(new LibraryBookGeneric(9780330351690L, "Jon Krakauer", "Into the Wild"));
		
		nameLib.checkout(9780330351690L, "Jane Doe", 1, 1, 2020);
		nameLib.checkout(9780374292799L, "Jane Doe", 1, 1, 2020);
		nameLib.checkout(9780446580342L, "Jane Doe", 1, 7, 2020);
		
		assertEquals(overdueList, nameLib.getOverdueList(1, 2, 2020));
	}
	
	@Test
	public void testListByTitle() {
		ArrayList<LibraryBookGeneric> titleList = new ArrayList<LibraryBookGeneric>();
		
		titleList.add(new LibraryBookGeneric(9780330351690L, "Jon Krakauer", "Into the Wild"));
		titleList.add(new LibraryBookGeneric(9780446580342L, "David Baldacci", "Simple Genius"));
		titleList.add(new LibraryBookGeneric(9780374292799L, "Thomas L. Friedman", "The World is Flat"));
		
		assertEquals(titleList, nameLib.getOrderedByTitle());
	}
	
}
