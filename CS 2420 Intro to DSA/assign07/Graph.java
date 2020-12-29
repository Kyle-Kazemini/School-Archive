package assign07;

import java.util.Collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Represents a sparse, unweighted, directed graph (a set of vertices and a set of edges). 
 * The graph is not generic and assumes that a string name is stored at each vertex.
 * 
 * @author Erin Parker && Kyle Kazemini && Anna Shelukha
 * @version February 25, 2020
 */
public class Graph<E> {

	// the graph -- a set of vertices (Type E mapped to Vertex instance)
	HashMap<E, Vertex<E>> vertices;
	
	/**
	 * Constructs an empty graph.
	 */
	public Graph() {
		vertices = new HashMap<E, Vertex<E>>();
	}

	/**
	 * Adds to the graph a directed edge from the vertex with name "name1" 
	 * to the vertex with name "name2".  (If either vertex does not already 
	 * exist in the graph, it is added.)
	 * 
	 * @param name1 - string name for source vertex
	 * @param name2 - string name for destination vertex
	 */
	public void addEdge(E name1, E name2) {
		Vertex<E> vertex1;
		// if vertex already exists in graph, get its object
		if(vertices.containsKey(name1))
			vertex1 = vertices.get(name1);
		// else, create a new object and add to graph
		else {
			vertex1 = new Vertex<E>(name1);
			vertices.put(name1, vertex1);
		}

		Vertex<E> vertex2;
		if(vertices.containsKey(name2))
			vertex2 = vertices.get(name2);
		else {
			vertex2 = new Vertex<E>(name2);
			vertices.put(name2, vertex2);
		}

		// add new directed edge from vertex1 to vertex2
		vertex1.addEdge(vertex2);
	}
	
	/**
	 * Generates the DOT encoding of this graph as string, which can be 
	 * pasted into http://www.webgraphviz.com to produce a visualization.
	 */
	public String generateDot() {
		StringBuilder dot = new StringBuilder("digraph d {\n");
		
		// for every vertex 
		for(Vertex<E> v : vertices.values()) {
			// for every edge
			Iterator<Edge<E>> edges = v.edges();
			while(edges.hasNext()) 
				dot.append("\t" + v.getData() + " -> " + edges.next() + "\n");
			
		}
		
		return dot.toString() + "}";
	}
	
	/**
	 * Generates a simple textual representation of this graph.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		for(Vertex<E> v : vertices.values()) 
			result.append(v + "\n");
		
		return result.toString();
	}
	
	/**
	 * Returns a vertex at position data in the map.
	 * 
	 * @param data
	 * @return Vertex
	 */
	public Vertex<E> getVertex(E data) {
		return vertices.get(data);
	}
	
	/**
	 * Returns a collection of all vertices in the map.
	 * 
	 * @return Collection
	 */
	public Collection<Vertex<E>> getVertices(){
		return vertices.values();
	}
	
	/**
	 * Creates a graph by adding edges between corresponding
	 * vertices in the sources and destinations lists.
	 * @param <Type>
	 * 
	 * @param sources
	 * @param destinations
	 */
	public void buildGraph(List<E> sources, List<E> destinations) {
		//for every element, connect vertices
	    for (int i = 0; i < sources.size(); i++) {
			addEdge(sources.get(i), destinations.get(i));
			
			//adds to indegree value of the vertex for toposort functionality
			getVertex(destinations.get(i)).inDegree++; 
		}
	}

	/**
	 * Driver method for isCyclic in GraphUtility
	 * Returns whether a graph has a cycle
	 * @param g
	 * @return
	 */
	public boolean isCyclic(Graph<E> g) {
		return depthFirstSearch(g);
	}

	/**
	 * Driver method for areConnected in GraphUtility
	 * Returns whether there is a path from start to the goal value
	 * 
	 * @param g
	 * @param start
	 * @param goal
	 * @throws IllegalArgumentException if entered data points are not present on the graph
	 */
	public boolean areConnected(Graph<E> g, E start, E goal) {
		if (g.getVertex(start) == null || g.getVertex(goal) == null)
			throw new IllegalArgumentException();
		
		return breadthFirstSearch(g, start, goal);
	}

	/**
	 * Driver method for sort in GraphUtility
	 * Returns sorted List of graph values
	 * @param g
	 */
	public List<E> sort(Graph<E> g) {
		return topoSort(g);
	}
	
	/**
	 * Performs a breadth first search, used in the 
	 * areConnected method.
	 * 
	 * @param Graph g
	 */
	private boolean breadthFirstSearch(Graph<E> g, E start, E goal) {
		
	    //getVertex(goal).visited = false;
		
	    //put all vertices in a Collection
		Collection<Vertex<E>> col = g.getVertices();
		
		//assign the start vertex
		Vertex<E> s = getVertex(start);

		//set every distance from the start to infinity
		for (Vertex<E> v : col)
			v.distanceFromS = Double.POSITIVE_INFINITY;

		//initialize the queue
		Queue<Vertex<E>> queue = new LinkedList<Vertex<E>>();

		//add first element to the queue, marking it as having a 0 distance and being visited
		queue.offer(s);
		s.distanceFromS = 0;
		s.visited = true;

		//for every element in the queue
		while (!queue.isEmpty()) {
		    //set var x to the next element
			Vertex<E> x = queue.poll();
			//iterate through its edges
			Iterator<Edge<E>> iter = x.edges();
			//for every edge
			 while (iter.hasNext()) {
			     //get vertex that is connected
				Vertex<E> w = iter.next().getOtherVertex();
				//if it hasn't been visited
				if (w.distanceFromS == Double.POSITIVE_INFINITY) {
				    //set distance one more than its predecessor
					w.distanceFromS = x.distanceFromS + 1;
					//mark as visited
					w.visited = true;
					//add element to the queue
					queue.offer(w);
				}
			}
		}
		return getVertex(goal).visited;
	}
	/**
	 * Driver method for depth first search, utilized in isCyclic
	 * Contains call to recursive method
	 * Returns whether a cycle is located
	 * @param Graph g
	 */
	private boolean depthFirstSearch(Graph<E> g) {
	    //assign a collection to all vertices in the graph
		Collection<Vertex<E>> col = g.getVertices();
		
		//for every vertex in the Collection, set distance to infinity and mark as unvisited
		for (Vertex<E> v : col) {
			v.distanceFromS = Double.POSITIVE_INFINITY;
			v.visited = false;
		}

		// call recursive method
		for (Vertex<E> v : col) {
			if (v.visited == false && depthFirstSearch(v) == true)
				return true;
		}
		return false;
	}

	/**
	 * Recursive method for depth first search
	 * Returns whether a cycle is located
	 * @param s
	 */
	private boolean depthFirstSearch(Vertex<E> s) {
		//if we see an element again while on the current path, a cycle has been fount
		if (s.currentPath == true && s.visited == true)
			return true;
		
		//mark vertex as on the current path and visited
		s.visited = true;
		s.currentPath = true;
		
		//iterate through the vertex's edges
		Iterator<Edge<E>> iter = s.edges();
		while (iter.hasNext()) {
			Vertex<E> w = iter.next().getOtherVertex();
			if (depthFirstSearch(w) == true)
				return true;	
		}
		//if every edge has been analyzed, the element can be removed from the current path
		s.currentPath = false;
		
		return false;
	}
	
	/**
	 * Helper method for running toposort on a graph
	 * Returns a list of the sorted elements
	 * @param g
	 */
	private List<E> topoSort(Graph<E> g) {
	    //initialize queue for storage
		Queue<Vertex<E>> queue = new LinkedList<Vertex<E>>();
		
		//put all vertices into a Collection
		Collection<Vertex<E>> col = g.getVertices();
		//for every vertex in the collection, add elements with a 0 indegree to the queue
		for (Vertex<E> v : col)
			if (v.inDegree == 0)
				queue.offer(v);
		
		//create a list to return
		List<E> list = new LinkedList<E>();
				
		//while the queue isn't empty
		while (!queue.isEmpty()) {
		    //set x to the next value in the queue
			Vertex<E> x = queue.poll();
			//iterate through the vertex's edges
			Iterator<Edge<E>> iter = x.edges();
			
			list.add(x.getData());
			
			while (iter.hasNext()){
			    //get connected vertex
				Vertex<E> w = iter.next().getOtherVertex();
				//decrease its indegree by 1
				w.inDegree -= 1;
				//if it becomes the root element, add it to the queue
				if (w.inDegree == 0)
					queue.offer(w);
			}
		}
		
		//check if an exception needs to be thrown
		if (list.size() != vertices.size())
			throw new IllegalArgumentException();

		//return the filled list
		return list;
	}
	
}