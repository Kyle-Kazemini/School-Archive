package lec04;

import java.util.Comparator;

/**
 * Class for demonstrating how to compare objects in the Shape hierarchy.
 * 
 * @author Erin Parker & CS 2420 class
 * @version January 16, 2020
 */
public class ShapeDemo {

	public static void main(String[] args) {
		Shape myCircle = new Circle(14);
		Shape myRectangle = new Rectangle(6, 30);

		// Compare myCircle and myRectangle:
		if(myCircle.compareTo(myRectangle) < 0)
			System.out.println("circle is less than rectangle");
		else if(myCircle.compareTo(myRectangle) > 0)
			System.out.println("circle is greater than rectangle");
		else
			System.out.println("circle is same as rectangle");
		
		// Generate an array of random shapes:
		Shape[] myShapes = generateRandArr();
		
		// Find the largest shape, using Comparable (i.e., by area):
		System.out.println(findMax(myShapes));
		
		// Find the largest shape, using the OrderByPerimeter Comparator:
		System.out.println(findMax(myShapes, new OrderByPerimeter()));
		
		// This is an example of using a lambda expression as a shortcut for a function object.
		// Find the largest shape, by perimeter, but without need of a Comparator:
		System.out.println(findMax(myShapes, (o1, o2) -> o1.perimeter() - o2.perimeter()));
	}
	
	/**
	 * Non-generic method for finding maximum item in an integer array.
	 * 
	 * @param arr - array containing items to be compared
	 * @return maximum item in the array
	 */
	public static int findMax(int[] arr) {
		int result = arr[0];
		for(int i = 1; i < arr.length; i++)
			if(arr[i] > result)
				result = arr[i];
		return result;
	}
	
	/**
	 * Generic method for finding maximum item in an array.
	 * 
	 * @param <T> - placeholder for type of the array and item
	 * @param arr - array containing items to be compared
	 * @param c - Comparator defining how to compare to items of type T
	 * @return maximum item in the array
	 */
	public static <T extends Comparable<? super T>> T findMax(T[] arr) {
		T result = arr[0];
		for(int i = 1; i < arr.length; i++)
			if(arr[i].compareTo(result) > 0)
				result = arr[i];
		return result;
	}
	
	/**
	 * Generic method for finding maximum item in an array.
	 * 
	 * @param <T> - placeholder for type of the array and item
	 * @param arr - array containing items to be compared
	 * @param c - Comparator defining how to compare to items of type T
	 * @return maximum item in the array
	 */
	public static <T> T findMax(T[] arr, Comparator<? super T> c) {
		T result = arr[0];
		for(int i = 1; i < arr.length; i++)
			if(c.compare(arr[i], result) > 0)
				result = arr[i];
		return result;
	}
	
	/**
	 * Generates and returns an array of 25 random shapes.
	 */
	public static Shape[] generateRandArr() {
		// Generate a "random" Shape array
		Shape[] arr = new Shape[25];

		for(int i = 0; i < arr.length; i++) {
			if(Math.random() < 0.5) {
				// radius between 1 and 50
				int radius = (int) (Math.random() * 50) + 1;
				arr[i] = new Circle(radius);
			}
			else {
				// height between 1 and 50
				int height = (int) (Math.random() * 50) + 1;
				// width between 1 and 50
				int width = (int) (Math.random() * 50) + 1;
				arr[i] = new Rectangle(height, width);
			}
		}
		
		return arr;
	}
}