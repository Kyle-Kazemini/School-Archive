package assign06;

import java.util.NoSuchElementException;

/**
* This class implements an interface that
* specifies the general behavior of a last-in-first-out (LIFO)
* stack of elements.
* 
* @author Erin Parker && Kyle Kazemini && Anna Shelukha
* @version February 20, 2020
* 
* @param <E> - the type of elements contained in the list
*/
public class LinkedListStack<E> implements Stack<E> {

	private SinglyLinkedList<E> list;
	
	public LinkedListStack() {
		 list = new SinglyLinkedList<E>();
	}
	
	/**
	 * Removes all of the elements from the stack.
	 */
	public void clear() {
		list.clear();
	}

	/**
	 * @return true if the stack contains no elements; false, otherwise.
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * Returns, but does not remove, the element at the top of the stack.
	 * 
	 * @return the element at the top of the stack
	 * @throws NoSuchElementException if the stack is empty
	 */
	public E peek() throws NoSuchElementException {
		return list.getFirst();
	}

	/**
	 * Returns and removes the item at the top of the stack.
	 * 
	 * @return the element at the top of the stack
	 * @throws NoSuchElementException if the stack is empty
	 */
	public E pop() throws NoSuchElementException {
		return list.removeFirst();
	}

	/**
	 * Adds a given element to the stack, putting it at the top of the stack.
	 * 
	 * @param element - the element to be added
	 */
	public void push(E element) {
		list.addFirst(element);
	}
	
	/**
	 * @return the number of elements in the stack
	 */
	public int size() {
		return list.size();
	}
}
