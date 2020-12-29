package assign03;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/*
* A generic class that implements the PriorityQueue interface
* that supports access of the minimum element only.
* This class will use Comparator when the user provides it, 
* and Comparable otherwise.
* 
* @author Erin Parker & Kyle Kazemini & Anna Shelukha
* @version January 23, 2020
* 
* @param <E> - the type of elements contained in this priority queue
*/
public class SimplePriorityQueue<E> implements PriorityQueue<E>
{

    private E[] array;
    private int size;
    private Comparator<? super E> comp;

    /*
     * Constructs a simple priority queue using natural ordering.
     */
    @SuppressWarnings("unchecked")
    public SimplePriorityQueue ()
    {
        this.array = (E[]) new Object[16];
        this.size = 0;
        this.comp = null;
    }

    /*
     * Constructs a simple priority queue using ordering from the provided Comparable object.
     */
    @SuppressWarnings("unchecked")
    public SimplePriorityQueue (Comparator<? super E> o1)
    {
        this.array = (E[]) new Object[16];
        this.size = 0;
        this.comp = o1;
    }

    /**
     * Retrieves, but does not remove, the minimum element in this priority queue.
     * 
     * @return the minimum element
     * @throws NoSuchElementException if the priority queue is empty
     */
    public E findMin ()
    {
        if (array.length == 0)
            throw new NoSuchElementException();

        return array[size - 1];
    }

    /**
     * Retrieves and removes the minimum element in this priority queue.
     * 
     * @return the minimum element
     * @throws NoSuchElementException if the priority queue is empty
     */
    public E deleteMin ()
    {
        if (array.length == 0)
            throw new NoSuchElementException();

        E min = array[this.size - 1];
        array[this.size - 1] = null;

        size--;
        
        return min;
    }

    /**
     * Inserts the specified element into this priority queue.
     * 
     * @param item - the element to insert
     */
    public void insert (E item)
    {
        if (this.size == array.length)
            array = Arrays.copyOf(array, size * 2);

        int pos = this.binarySearch(item);

        for (int i = size; i > pos; i--)
            array[i] = array[i - 1];

        array[pos] = item;

        size++;
    }

    /**
     * Inserts the specified elements into this priority queue.
     * 
     * @param coll - the collection of elements to insert
     */
    public void insertAll (Collection<? extends E> coll)
    {
        for (E token : coll)
            insert(token);
    }

    /**
     * @return the number of elements in this priority queue
     */
    public int size ()
    {
        return this.size;
    }

    /**
     * @return true if this priority queue contains no elements, false otherwise
     */
    public boolean isEmpty ()
    {
        if (this.size == 0)
            return true;
        return false;
    }

    /**
     * Removes all of the elements from this priority queue. The queue will be empty when this call returns.
     */
    public void clear ()
    {
        for (int i = 0; i < array.length; i++)
            array[i] = null;
        size= 0;
    }

    /*
     * Performs a binary search.
     * 
     * @return position that's where we need to insert.
     * 
     * @param an array of generic type, a generic type that we're searching for.
     */
    private int binarySearch(E goal) {
		int low = 0, high = size, mid = 0;
		
		while (low <= high) {
			mid = (low + high) / 2;
//			if (mid == 0)
//			    return 0;
			if (array[mid] != null ) {
				if (this.comparison(array[mid], goal) == 0)
				//	return mid - 1;
				    return mid - 1;
				else if (this.comparison(array[mid], goal) < 0)
				    low = mid +1;
				else 
				    return low;
			}
			else
			     return high;
		}
		return low;
		
	}

    /*
     * Private helper method to make the comparison in the binary search.
     * 
     * @return int based on comparison using Comparable or Comparator.
     */
    @SuppressWarnings("unchecked")
    private int comparison (E mid, E goal)
    {
        if (comp == null)
            return (((Comparable<? super E>) goal).compareTo(mid));
        else
            return comp.compare(goal, mid);
    }
}
