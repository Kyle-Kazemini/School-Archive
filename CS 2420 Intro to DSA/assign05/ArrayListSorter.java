package assign05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Class for implementing recursive merge sort and quick sort
 * 
 * @author Kyle Kazemini & Anna Shelukha
 * @version February 6, 2020
 */
public class ArrayListSorter<T> {
	
	private static final int threshold = 3;
	
	/**
	 * Performs a merge sort on the generic ArrayList
	 * 
	 * @param Generic ArrayList
	 */
	public static <T extends Comparable<? super T>> void mergesort(ArrayList<T> list) {
		ArrayList<T> temp = new ArrayList<T>();
		
		for (int i = 0; i < list.size(); i++)
			temp.add(null);
		
		int left = 0;
		int right = list.size() - 1;
		
		mergesort(list, temp, left, right);
	}
	
	/**
	 * Private recursive method for executing merge sort
	 * 
	 * @param ArrayList, left, right
	 */
	private static <T extends Comparable<? super T>> void mergesort(ArrayList<T> list, ArrayList<T> temp, int left, int right) {
		if (right - left <= (list.size()/5000))
			insertionSort(list, (T a, T b) -> a.compareTo(b), left, right);
		
		if (left < right) {
			int mid = (left + right) / 2;
			mergesort(list, temp, left, mid);
			mergesort(list, temp, mid + 1, right);
			merge(list, temp, left, mid + 1, right);
		}
		
	}
	
	/**
	 * Private recursive method for executing merge step
	 * @param ArrayList, temporary ArrayList, start, end
	 */
	private static <T extends Comparable<? super T>> void merge(ArrayList<T> list, ArrayList<T> temp, int startLeft, int startRight, int endRight) {
		int index = startLeft;
		int currLeft = startLeft;
		int currRight = startRight;
		int endLeft = startRight;
		
		while (currLeft <= endLeft && currRight <= endRight) {
			if (list.get(currLeft).compareTo(list.get(currRight)) <= 0) {
				temp.set(index, list.get(currLeft));
				currLeft++;
			}

			else {
				temp.set(index, list.get(currRight));
				currRight++;
			}
			
			index++;
		}	
			if (currLeft >= endLeft)
				for (int i = currRight; i <= endRight; i++)
					temp.set(i, list.get(i));
			
			else
				for (int i = currLeft; i <= endLeft; i++)
					temp.set(i, list.get(i));
			
			for (int i = startLeft; i <= endRight; i++) {
			    if (temp.get(i) == null)
			        System.out.println("null");
				list.set(i, temp.get(i));
			}
	}
	
	/**
	 * Performs a quick sort on the generic ArrayList
	 * 
	 * @param Generic ArrayList
	 */
	public static <T extends Comparable<? super T>> void quicksort(ArrayList<T> list) {
		int start = 0;
		int end = list.size() - 1;
		
		quicksort(list, start, end);
	}
	
	/**
	 * Private recursive method for executing quick sort
	 * 
	 * @param ArrayList, partition
	 */
	private static <T extends Comparable<? super T>> void quicksort(ArrayList<T> list, int start, int end) {
		if (start < end) {
			int pivot = partition(list, start, end);
			
			quicksort(list, start, pivot - 1);
			quicksort(list, pivot + 1, end);
		}
	}
	
	/**
	 * Sorts ArrayList around the chosen pivot position
	 * 
	 * @param list, start, end 
	 */
	private static <T extends Comparable<? super T>> int partition(ArrayList<T> list, int start, int end) {
		int index = getMiddle(start, end);
		T pivot = list.get(index);
		
		T temp = list.get(end);
		list.set(end, pivot);
		list.set(index, temp);
		
		int left = start;
		int right = end-1;
		
		while (left <= right) {
			while (left <= right && list.get(left).compareTo(pivot) <= 0)
				left++;
			while (left <= right && list.get(right).compareTo(pivot) > 0)
				right--;
		  if (left <= right) {
			T temp2 = list.get(left);
			list.set(left, list.get(right));
			list.set(right, temp2);
		}
		}
		
		T temp3 = list.get(left);
		list.set(list.indexOf(temp3), pivot);
		list.set(end, temp3);
		
		return left;
	}
	    
	/**
	 * This method generates and returns an ArrayList 
	 * of integers 1 to size in ascending order
	 * 
	 * @param size
	 * @return ArrayList of integers
	 */
	public static ArrayList<Integer> generateAscending(int size) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int element = 1;

		for(int i = 0; i < size; i++) {
			list.add(null);
			list.set(i, element);
			element++;
		}
		return list;
	}
	
	/**
	 * This method generates and returns an ArrayList 
	 * of integers 1 to size in permuted order
	 * 
	 * @param size
	 * @return ArrayList of integers
	 */
	public static ArrayList<Integer> generatePermuted(int size) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int element = 1;

		for(int i = 0; i < size; i++) {
			list.add(null);
			list.set(i, element);
			element++;
		}
		Collections.shuffle(list);
		return list;
	}
	
	/**
	 * This method generates and returns an ArrayList
	 * of integers 1 to size in descending order
	 * 
	 * @param size
	 * @return ArrayList of integers
	 */
	public static ArrayList<Integer> generateDescending(int size) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int element = size;

		for(int i = 0; i < size; i++) {
			list.add(null);
			list.set(i, element);
			element--;
		}
		return list;
	}
	
	private static <T> void insertionSort (ArrayList<T> arr, Comparator<? super T> comp, int left, int right) {
        // for every element in the array
        for (int i = left; i <= right; i++) {
            // initialize variables
            T val = arr.get(i);
            int j;
            // if value isn't in the appropriate place in the array
            for (j = i - 1; j >= 0 && comp.compare(arr.get(j), val) > 0; j--) {
                // shift all values greater than over one
            	arr.set(j + 1, arr.get(j));
            }
         // insert value at the new, appropriate location
        	arr.set(j + 1, val);
        }
    }
	
	/**
	 * Returns the middle value of the array
	 * 
	 * @param ArrayList<T>
	 * @return <T>
	 */
	private static int getMiddle(int start, int end) {
		return (end + start) / 2;
	}

	/**
	 * Returns the median value of the first, middle, and last value of the array
	 * 
	 * @param ArrayList<T>
	 * @return <T>
	 */
	public static int getMedian(int start, int end) {
		int mid = (end + start) / 2;
		if ((start > mid && start < end) || (start < mid && start > end))
			return start;

		else if ((mid > start && mid < end) || (mid < start && mid > end))
			return mid;

		return end;
	}

	/**
	 * Returns a random value from the ArrayList
	 * 
	 * @param ArrayList<T>
	 * @return <T>
	 */
	public static int getRandom(int start, int end) {
		Random rng = new Random();
		int rand = rng.nextInt(end - start);
		return start + rand;
	}
}
