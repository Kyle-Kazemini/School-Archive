package assign02;

import java.util.GregorianCalendar;

/**
 * This class represents a library book.
 * 
 * @author Kyle Kazemini and Anna Shelukha
 * @version January 16, 2020
 */

public class LibraryBook extends Book {
	
	private String holder;
	private GregorianCalendar dueDate;

	public LibraryBook (long isbn, String author, String title) {
		super (isbn, author, title);
	}
	
	public String getHolder () {
		return holder;
	}
	
	public GregorianCalendar getDueDate () {
		return dueDate;
	}
	
	public void checkIn () {
		this.holder = null;
		this.dueDate = null;
	}
	
	public void checkOut (String holder, GregorianCalendar dueDate) {
		this.holder = holder;
		this.dueDate = dueDate;
	}
}
