package lec06;

import java.util.Arrays;

/**
 * A simple implementation of a dynamic array list,
 *  used for demonstrating principles of using an array as a backing structure
 *  and some timing subtleties
 * 
 * @author	Matthew Hooper
 * @version January 23, 2020
 *
 * @param <Type> - Type of Object contained in this MyArrayList
 */
public class MyArrayList<Type> {
	private Type[] array;
	private int size;
	
	/**
	 * Constructs an empty MyArrayList
	 */
	@SuppressWarnings("unchecked")
	public MyArrayList() {
		array = (Type[]) new Object[16];
		size = 0;
	}
	
	/**
	 * @return - the number of elements in this MyArrayList
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * @param obj - adds this element to this MyArrayList
	 */
	public void add(Type obj) {
		if (this.size == array.length) {
			// this is the worse way to resize
			// array = Arrays.copyOf(array, size * 2);
			
			// this is the right way
			array = Arrays.copyOf(array, size * 2);
		}
		
		this.array[size] = obj;
		size++;
	}
	
	// A real MyArrayList class would include functions for getting items,
	//  or for searching for items
}
