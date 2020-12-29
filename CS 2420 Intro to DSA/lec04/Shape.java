package lec04;

/**
 * Class representing a generic shape.
 * 
 * @author Erin Parker & CS 2420 class
 * @version January 16, 2020
 */
public abstract class Shape implements Comparable<Shape> {
	
	private String name; // String representation, such as "Circle"

	public Shape(String name) {
		this.name = name;
	}

	/**
	 * @return the area of a shape
	 */
	public abstract int area();

	/**
	 * @return the perimeter of a shape
	 */
	public abstract int perimeter();

	/**
	 * @return the String that describes a shape
	 */
	public final String toString() {
		return name + " with area " + area() + " and perimeter " + perimeter() + ".";
	}
	
	/**
	 * Compares two shapes using their areas.
	 * 
	 * @return negative value if "this" shape's area is less than "other" shape's area
	 *         positive value if "this" shape's area is greater than "other" shape's area
	 *         0 if "this" shape's area is the same as "other" shape's area
	 */
	public int compareTo(Shape other) {
		if(this.area() > other.area())
			return 1;
		if(this.area() < other.area())
			return -1;
		return 0;
		
		// also correct:
		// return this.area() - other.area();
	}
}