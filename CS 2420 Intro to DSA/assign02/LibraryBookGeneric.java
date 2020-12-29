package assign02;

import java.util.GregorianCalendar;

/**
 * This class represents a generic library book.
 * 
 * @author Kyle Kazemini and Anna Shelukha
 * @version January 16, 2020
 */

public class LibraryBookGeneric<Type> extends Book {
	
	private Type holder;
	private GregorianCalendar dueDate;

	public LibraryBookGeneric (long isbn, String author, String title) {
		super (isbn, author, title);
	}
	
	public Type getHolder () {
		return holder;
	}
	
	public GregorianCalendar getDueDate () {
		return dueDate;
	}
	
	public void checkIn () {
		this.holder = null;
		this.dueDate = null;
	}
	
	public void checkOut (Type holder, GregorianCalendar dueDate) {
		this.holder = holder;
		this.dueDate = dueDate;
	}
}
