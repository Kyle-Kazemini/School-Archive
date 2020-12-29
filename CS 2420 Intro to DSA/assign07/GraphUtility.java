package assign07;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Contains several methods for solving problems on generic, directed,
 * unweighted, sparse graphs.
 * 
 * @author Erin Parker & Kyle Kazemini && Anna Shelukha
 * @version February 27, 2020
 */
public class GraphUtility {
/**
 * Tests whether the provided graph contains a cycle and returns appropriate boolean
 * @Throws IllegalArgumentException if source and destination files are not equal sizes
 */
	public static <Type> boolean isCyclic(List<Type> sources, List<Type> destinations) throws IllegalArgumentException {
		//check for list size congruence
	    if (sources.size() != destinations.size())
			throw new IllegalArgumentException();
		
		//graph variable
		Graph<Type> graph = new Graph<Type>();

		//creates a graph from the given sources and destinations
		graph.buildGraph(sources, destinations);
		
		//call to driver method in Graph class
		return graph.isCyclic(graph);
	}
	
/**
 * Tests whether two variables are connected on the graph and returns the appropriate boolean
 * @Throws IllegalArgumentException if data points cannot be found in either List
 */
	public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData)
			throws IllegalArgumentException {

	    //creates new graph variable
		Graph<Type> graph = new Graph<Type>();

		//builds graph from given Lists
		graph.buildGraph(sources, destinations);

		//call to driver method in Graph class
		return graph.areConnected(graph, srcData, dstData);
	}

	/**
	 * Uses topological sort to sort the values within a given acyclic graph
	 * @Throws IllegalArgumentException if the graph given is cyclic
	 */
	public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) throws IllegalArgumentException {
        //builds graph from given Lists
		Graph<Type> graph = new Graph<Type>();

		graph.buildGraph(sources, destinations);
		//call to driver method in Graph class
		return graph.sort(graph);
	}

	/**
	 * This method generates a random Dot file from a file name and number of
	 * vertices.
	 * 
	 * @param filename
	 * @param vertexCount
	 */
	public static void generateRandomDotFile(String filename, int vertexCount) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(filename);
		} catch (IOException e) {
			System.out.println(e);
		}

		Random rng = new Random();

		// randomly construct either a graph or a digraph
		String edgeOp = "--";
		if (rng.nextBoolean()) {
			out.print("di");
			edgeOp = "->";
		}
		out.println("graph G {");

		// generate a list of vertices
		String[] vertex = new String[vertexCount];
		for (int i = 0; i < vertexCount; i++)
			vertex[i] = "v" + i;

		// randomly connect the vertices using 2 * |V| edges
		for (int i = 0; i < 2 * vertexCount; i++)
			out.println("\t" + vertex[rng.nextInt(vertexCount)] + edgeOp + vertex[rng.nextInt(vertexCount)]);

		out.println("}");
		out.close();
	}
	
	   /**
     * Builds "sources" and "destinations" lists according to the edges
     * specified in the given DOT file (e.g., "a -> b"). Assumes that the vertex
     * data type is String.
     * 
     * Accepts many valid "digraph" DOT files (see examples posted on Canvas).
     * --accepts \\-style comments 
     * --accepts one edge per line or edges terminated with ; 
     * --does not accept attributes in [] (e.g., [label = "a label"])
     * 
     * @param filename - name of the DOT file
     * @param sources - empty ArrayList, when method returns it is a valid
     *        "sources" list that can be passed to the public methods in this
     *        class
     * @param destinations - empty ArrayList, when method returns it is a valid
     *        "destinations" list that can be passed to the public methods in
     *        this class
     */
    public static void buildListsFromDot(String filename, ArrayList<String> sources, ArrayList<String> destinations) {

        Scanner scan = null;
        try {
            scan = new Scanner(new File(filename));
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        scan.useDelimiter(";|\n");

        // Determine if graph is directed (i.e., look for "digraph id {").
        String line = "", edgeOp = "";
        while(scan.hasNext()) {
            line = scan.next();

            // Skip //-style comments.
            line = line.replaceFirst("//.*", "");

            if(line.indexOf("digraph") >= 0) {
                edgeOp = "->";
                line = line.replaceFirst(".*\\{", "");
                break;
            }
        }
        if(edgeOp.equals("")) {
            System.out.println("DOT graph must be directed (i.e., digraph).");
            scan.close();
            System.exit(0);

        }

        // Look for edge operator -> and determine the source and destination
        // vertices for each edge.
        while(scan.hasNext()) {
            String[] substring = line.split(edgeOp);

            for(int i = 0; i < substring.length - 1; i += 2) {
                // remove " and trim whitespace from node string on the left
                String vertex1 = substring[0].replace("\"", "").trim();
                // if string is empty, try again
                if(vertex1.equals(""))
                    continue;

                // do the same for the node string on the right
                String vertex2 = substring[1].replace("\"", "").trim();
                if(vertex2.equals(""))
                    continue;

                // indicate edge between vertex1 and vertex2
                sources.add(vertex1);
                destinations.add(vertex2);
            }

            // do until the "}" has been read
            if(substring[substring.length - 1].indexOf("}") >= 0)
                break;

            line = scan.next();

            // Skip //-style comments.
            line = line.replaceFirst("//.*", "");
        }

        scan.close();
    }

}