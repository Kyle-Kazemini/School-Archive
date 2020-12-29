package lec04;

import java.util.Comparator;

/**
 * Comparator for comparing two shapes, using their perimeters.
 * 
 * @author Erin Parker & CS 2420 class
 * @version January 16, 2020
 */
public class OrderByPerimeter implements Comparator<Shape> {

	/**
	 * Compares two shapes using their perimeters.
	 * 
	 * @return negative value if "o1" shape's perimeter is less than "o2" shape's perimeter
	 *         positive value if "o1" shape's perimeter is greater than "o2" shape's perimeter
	 *         0 if "o1" shape's perimeter is the same as "o2" shape's perimeter
	 */
	public int compare(Shape o1, Shape o2) {
		if(o1.perimeter() > o2.perimeter())
			return 1;
		if(o1.perimeter() < o2.perimeter())
			return -1;
		return 0;
		
		// also correct:
		// return o1.perimeter() - o2.perimeter()
	}
}