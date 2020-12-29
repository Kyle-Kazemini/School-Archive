package lec02;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class containing static methods for reversing the elements in
 * arrays and ArrayLists.
 * 
 * @author Erin Parker & CS 2420 class
 * @version January 9, 2020
 */
public class ArrayAndArrayListDemo {

	/**
	 * Reverses the ordering of items in the given integer array.
	 * 
	 * @param arr - The array to be reversed.
	 */
	public static void reverseArray(int[] arr) {
		for(int i = 0; i < arr.length / 2; i++) {
			int temp = arr[i];
			arr[i] = arr[arr.length - 1 - i];
			arr[arr.length - 1 - i] = temp;
		}
	}

	/**
	 * Reverses the ordering of items in the given integer ArrayList.
	 * 
	 * @param arr - The ArrayList to be reversed.
	 */
	public static void reverseArrayList(ArrayList<Integer> list) {
		for(int i = 0; i < list.size() / 2; i++) {
			int temp = list.get(i);
			list.set(i, list.get(list.size() - 1 - i));
			list.set(list.size() - 1 - i, temp);
		}
	}
	
	public static void main(String[] args) {
		// Is this a good way to TEST our methods?
		
		int jennyArray[] = { 8, 6, 7, 5, 3, 0, 9 };
		System.out.println("Basic array: " + Arrays.toString(jennyArray));
		reverseArray(jennyArray);
		System.out.println("Basic array, reversed: " + Arrays.toString(jennyArray));

		ArrayList<Integer> jennyArrayList = new ArrayList<Integer>();
		jennyArrayList.add(8);
		jennyArrayList.add(6);
		jennyArrayList.add(7);
		jennyArrayList.add(5);
		jennyArrayList.add(3);
		jennyArrayList.add(0);
		jennyArrayList.add(9);
		System.out.println("ArrayList: " + jennyArrayList);
		reverseArrayList(jennyArrayList);
		System.out.println("ArrayList, reversed: " + jennyArrayList);
	}
}