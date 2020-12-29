package assign03;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * A priority queue that supports access of the minimum element only.
 * 
 * @author Erin Parker
 * @version January 23, 2020
 * 
 * @param <E> - the type of elements contained in this priority queue
 */
public interface PriorityQueue<E> {

	/**
	 * Retrieves, but does not remove, the minimum element in this priority
	 * queue.
	 * 
	 * @return the minimum element
	 * @throws NoSuchElementException if the priority queue is empty
	 */
	public E findMin() throws NoSuchElementException;

	/**
	 * Retrieves and removes the minimum element in this priority queue.
	 * 
	 * @return the minimum element
	 * @throws NoSuchElementException if the priority queue is empty
	 */
	public E deleteMin() throws NoSuchElementException;

	/**
	 * Inserts the specified element into this priority queue.
	 * 
	 * @param item - the element to insert
	 */
	public void insert(E item);

	/**
	 * Inserts the specified elements into this priority queue.
	 * 
	 * @param coll - the collection of elements to insert
	 */
	public void insertAll(Collection<? extends E> coll);

	/**
	 * @return the number of elements in this priority queue
	 */
	public int size();

	/**
	 * @return true if this priority queue contains no elements, false otherwise
	 */
	public boolean isEmpty();

	/**
	 * Removes all of the elements from this priority queue. The queue will be
	 * empty when this call returns.
	 */
	public void clear();
}