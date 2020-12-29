package assign07;

import java.util.LinkedList;
import java.util.Iterator;

/**
 * This class represents a vertex (AKA node) in a directed graph. The vertex is
 * not generic, assumes that a string name is stored there.
 * 
 * @author Erin Parker
 * @version February 25, 2020
 */
public class Vertex<E> {

	// used to id the Vertex
	private E data;

	// adjacency list
	private LinkedList<Edge<E>> adj;
	
	public double distanceFromS;
	public boolean visited;
	public boolean currentPath;
	public int inDegree;

	public Vertex(E name) {
		this.data = name;
		this.adj = new LinkedList<Edge<E>>();
		this.distanceFromS = 0;
		this.visited = false;
		this.currentPath = false;
		this.inDegree = 0;
	}

	public E getData() {
		return data;
	}

	public void addEdge(Vertex<E> otherVertex) {
		adj.add(new Edge<E>(otherVertex));
	}

	public Iterator<Edge<E>> edges() {
		return adj.iterator();
	}

	public String toString() {
		String s = "Vertex " + data + " adjacent to vertices ";
		Iterator<Edge<E>> itr = adj.iterator();
		while(itr.hasNext())
			s += itr.next() + "  ";
		return s;
	}
}