package lec04;

/**
 * Class representing a circle.
 * NOTE: Methods comments are left for students to write.
 * 
 * @author Erin Parker
 * @version January 14, 2020
 */
public class Circle extends Shape {

	private int radius;

	public Circle(int radius) {
		super("Circle");
		this.radius = radius;
	}

	public int area() {
		return (int) (Math.PI * radius * radius);
	}

	public int perimeter() {
		return (int) (Math.PI * 2 * radius);
	}
}