package lec04;

/**
 * Class representing a rectangle.
 * NOTE: Methods comments are left for students to write.
 * 
 * @author Erin Parker
 * @version January 14, 2020
 */
public class Rectangle extends Shape {

	private int height;

	private int width;

	public Rectangle(int height, int width) {
		super(height == width ? "Square" : "Rectangle");
		this.height = height;
		this.width = width;
	}

	public int area() {
		return height * width;
	}

	public int perimeter() {
		return 2 * height + 2 * width;
	}
}