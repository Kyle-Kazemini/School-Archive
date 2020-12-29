package lab08;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Represents a directed graph (a set of vertices and a set of edges). The graph
 * is not generic and assumes that a string name is stored at each vertex.
 * 
 * Dijkstra's shortest-path algorithm is included.
 * 
 * @author Erin Parker
 * @version February 28, 2020
 */
public class Graph {

	// the graph -- a set of vertices (String name mapped to Vertex instance)
	HashMap<String, Vertex> vertices;

	/**
	 * Constructs an empty graph.
	 */
	public Graph() {
		vertices = new HashMap<String, Vertex>();
	}

	/**
	 * Adds to the graph an edge from the vertex with name "name1" to the vertex
	 * with name "name2". The edge is associated with the "weight". If either
	 * vertex does not already exist in the graph, it is added.
	 */
	public void addEdge(String name1, String name2, double weight) {
		Vertex vertex1;
		if(vertices.containsKey(name1))
			vertex1 = vertices.get(name1);
		else {
			vertex1 = new Vertex(name1);
			vertices.put(name1, vertex1);
		}

		Vertex vertex2;
		if(vertices.containsKey(name2))
			vertex2 = vertices.get(name2);
		else {
			vertex2 = new Vertex(name2);
			vertices.put(name2, vertex2);
		}

		vertex1.addEdge(vertex2, weight);
	}

	/**
	 * Uses Dijkstra's algorithm to determine the shortest paths from the
	 * starting vertex to every other vertex in the graph.
	 * 
	 * Use Java's PriorityQueue class to find the "unvisited vertex with
	 * smallest distance from s". In order to associate each vertex with a
	 * priority (related to its distance from s), you must either use the
	 * Comparable interface or the Comparator interface. See the API for
	 * PriorityQueue, and ask the course staff if you need help.
	 */
	public void dijkstras(String startName) {
		// get starting vertex
		Vertex start = vertices.get(startName);
		if(start == null)
			throw new UnsupportedOperationException("Vertex " + startName + " does not exist.");

		// set up priority queue of vertices prioritized by smallest distance
		// from
		// start (CHANGE if using Comparator)
		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();

		// for all vertices: set distance from start to INF
		for(Vertex v : vertices.values())
			v.setDistanceFromStart(Double.POSITIVE_INFINITY);

		start.setDistanceFromStart(0);
		pq.offer(start);

		// while there are vertices left to consider . . .
		while(!pq.isEmpty()) {
			// get the unvisited vertex with the smallest distance from start
			Vertex x = pq.poll();

			// for all of the edges from vertex x . . .
			for(Edge e : x.getEdges()) {
				// get the destination vertex for the edge and consider the cost
				// of this
				// path
				Vertex dest = e.getOtherVertex();
				double cost = e.getWeight() + x.getDistanceFromStart();

				// if the cost of this path is smaller than the previously
				// considered
				// path, update
				if(dest.getDistanceFromStart() > cost) {
					dest.setDistanceFromStart(cost);
					dest.setPrevious(x);
					// if the destination vertex has not been visited, add to
					// the priority
					// queue
					if(!dest.getVisited())
						pq.offer(dest);
				}
			}

			x.setVisited(true);
		}

		// print the results of the algorithm
		for(Vertex v : vertices.values()) {
			System.out.print("Shortest path from " + start.getName() + " to " + v.getName() + " has length "
					+ v.getDistanceFromStart() + " and is ");
			String s = "";
			while(!v.getName().equals(start.getName())) {
				s = " --> " + v.getName() + s;
				v = v.getPrevious();
			}
			System.out.println(start.getName() + s);
		}
	}
}