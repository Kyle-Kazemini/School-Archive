package assign06;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
* This class implements an interface that
* specifies the general behavior of an ordered list of elements.
* 
* @author Erin Parker && Kyle Kazemini && Anna Shelukha
* @version February 20, 2020
* 
* @param <E> - the type of elements contained in the list
*/
public class SinglyLinkedList<E> implements List<E> {

	private class Node {
		public E data;
		public Node next;
		
		public Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}	
	}
	
	private Node head;
	private int elementCount;
	
	public SinglyLinkedList() {
		this.head = null;
		this.elementCount = 0;
	}
	
	/**
	 * Inserts an element at the beginning of the list.
	 * O(1) for a singly-linked list.
	 * 
	 * @param element - the element to add
	 */
	public void addFirst(E element) {
		head = new Node(element, head);
		elementCount++;
	}
	
	/**
	* Helper method to insert a new node containing newData immediately
	* after prevNode.
	* @param newData 
	* @param prevNode 
	* */
	private void insert(E newData, Node prevNode) {
		prevNode.next = new Node(newData, prevNode.next);
		elementCount++;
	}

	/**
	 * Inserts an element at a specific position in the list.
	 * O(N) for a singly-linked list.
	 * 
	 * @param index - the specified position
	 * @param element - the element to add
	 * @throws IndexOutOfBoundsException if index is out of range 
	 * (index < 0 || index > size())
	 */
	public void add(int index, E element) throws IndexOutOfBoundsException {
		if (index < 0 || index > elementCount)
			throw new IndexOutOfBoundsException();
		if (index == 0)
			addFirst(element);
		else
			insert(element, getPrevNode(index));
	}
	
	/**
	 * Gets the first element in the list.
	 * O(1) for a singly-linked list.
	 * 
	 * @return the first element in the list
	 * @throws NoSuchElementException if the list is empty
	 */
	public E getFirst() throws NoSuchElementException {
		if (elementCount == 0)
			throw new NoSuchElementException();
		return head.data;
	}
	
	/**
	 * Gets the element at a specific position in the list.
	 * O(N) for a singly-linked list.
	 * 
	 * @param index - the specified position
	 * @return the element at the position
	 * @throws IndexOutOfBoundsException if index is out of range 
	 * (index < 0 || index >= size())
	 */
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > elementCount)
			throw new IndexOutOfBoundsException();
		
		Node temp = head;
		
		for (int i = 0; i < index; i++)
			temp = temp.next;
		
		return temp.data;
	}
	
	/**
	 * Removes and returns the first element from the list.
	 * O(1) for a singly-linked list.
	 * 
	 * @return the first element
	 * @throws NoSuchElementException if the list is empty
	 */
	public E removeFirst() throws NoSuchElementException {
		if (elementCount == 0)
			throw new NoSuchElementException();
		elementCount--;
		
		E temp = head.data;
		head = head.next;
		
		return temp;
	}
	
	/**
	 * Helper method to retrieve the node occurring in the list before the node at
	 * the given position. NOTE: It is a precondition that pos > 0.
	 * 
	 * @param pos 0-indexed position of the node
	 * @return node at position pos-1
	 */
	private Node getPrevNode(int pos) {
		Node temp = head;
		for (int i = 0; i < pos - 1; i++)
			temp = temp.next;
		return temp;
	}

	/**
	 * Removes and returns the element at a specific position in the list.
	 * O(N) for a singly-linked list.
	 * 
	 * @param index - the specified position
	 * @return the element at the position
	 * @throws IndexOutOfBoundsException if index is out of range 
	 * (index < 0 || index >= size())
	 */
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= elementCount)
			throw new IndexOutOfBoundsException();
		if (index == 0)
			return removeFirst();
		else {
			Node temp = getPrevNode(index);
			E result = get(index);
			temp.next = temp.next.next;
			elementCount--;
			
			return result;
		}
	}
	
	/**
	 * Determines the index of the first occurrence of the specified 
	 * element in the list, or -1 if this list does not contain the element.
	 * O(N) for a singly-linked list.
	 * 
	 * @param element - the element to search for
	 * @return the index of the first occurrence; -1 if the element is not found
	 */
	public int indexOf(E element) {
		for (int i = 0; i < elementCount; i++)
			if (get(i) == element)
				return i;
		
		return -1;
	}
	
	/**
	 * O(1) for a singly-linked list.
	 * 
	 * @return the number of elements in this list
	 */
	public int size() {
		return elementCount;
	}
	
	/**
	 * O(1) for a singly-linked list.
	 * 
	 * @return true if this collection contains no elements; false, otherwise
	 */
	public boolean isEmpty() {
		return elementCount == 0;
	}
	
	/**
	 * Removes all of the elements from this list.
	 * O(1) for a singly-linked list.
	 */
	public void clear() {
		head = null;
		elementCount = 0;
	}
	
	/**
	 * Generates an array containing all of the elements in this list in proper 
	 * sequence (from first element to last element).
	 * O(N) for a singly-linked list.
	 * 
	 * @return an array containing all of the elements in this list, in order
	 */
	public Object[] toArray() {
		Object[] arr = new Object[elementCount];
		
		for (int i = 0; i < elementCount; i++) 
			arr[i] = this.get(i);
		
		return arr;
	}
	
	/**
	 * @return an iterator over the elements in this list in proper sequence 
	 * (from first element to last element)
	 */
	public Iterator<E> iterator() {
		LLIterator iter = new LLIterator();
		return iter;
	}
	
	private class LLIterator implements Iterator<E> {
		Node index;
		Node prev;
		boolean wasRemoved;
		
		public LLIterator() {
			prev = null;
			index = head;
			wasRemoved = true;
		}
		
		@Override
		public boolean hasNext() {
			if (index == null)
				return false;
			return true;
		}

		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			
			E temp = index.data;
			prev = index;
			index = index.next;
			wasRemoved = false;
			return temp;
		}
		
		@Override
		public void remove() {
			if (wasRemoved == false) {
				prev.next = index.next;
				wasRemoved = true;
				elementCount--;
			} 
			else
				throw new IllegalStateException();

		}
	}
}
