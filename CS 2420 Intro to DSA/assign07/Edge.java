package assign07;

/**
 * This class represents an edge between a source vertex and a destination
 * vertex in a directed graph.
 * 
 * The source of this edge is the Vertex whose object has an adjacency list
 * containing this edge.
 * 
 * @author Erin Parker
 * @version February 25, 2020
 */
public class Edge<E> {

	// destination of this directed edge
	private Vertex<E> dst;

	public Edge(Vertex<E> dst) {
		this.dst = dst;
	}

	public Vertex<E> getOtherVertex() {
		return this.dst;
	}

	public String toString() {
		return this.dst.getData().toString();
	}
}