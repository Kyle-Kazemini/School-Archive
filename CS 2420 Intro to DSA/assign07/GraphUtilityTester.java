package assign07;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphUtilityTester {
	ArrayList<String> sources;
	ArrayList<String> destinations;
	ArrayList<String> sourcesOutCyc;
	ArrayList<String> destinationsOutCyc;
	ArrayList<String> sourcesAcyc;
	ArrayList<String> destinationsAcyc;

	@BeforeEach
	public void generateAll() {
		sources = new ArrayList<String>();
		destinations = new ArrayList<String>();
		GraphUtilityTimer.buildListsFromDot("src/assign07/graphutility test.txt", sources, destinations);

		sourcesOutCyc = new ArrayList<String>();
		destinationsOutCyc = new ArrayList<String>();
		GraphUtility.buildListsFromDot("src/assign07/graphutility outsidecycle.txt", sourcesOutCyc, destinationsOutCyc);
		
		sourcesAcyc = new ArrayList<String>();
        destinationsAcyc = new ArrayList<String>();
        GraphUtility.buildListsFromDot("src/assign07/graphutility noncyclic.txt", sourcesAcyc, destinationsAcyc);
	}

	@Test
	public void generateDotFile() {
		GraphUtility.generateRandomDotFile("tester", 6);
	}

	@Test
	public void areConnectedOutFalseTest() {
		assertFalse(GraphUtility.areConnected(sourcesOutCyc, destinationsOutCyc, "a", "h"));
	}

	@Test
	public void cyclicTest() {
		assertTrue(GraphUtility.isCyclic(sources, destinations));
	}

	@Test
	public void areConnectedTrueTest() {
		assertTrue(GraphUtility.areConnected(sources, destinations, "a", "g"));
	}

	@Test
	public void areConnectedFalseTest() {
		assertFalse(GraphUtility.areConnected(sources, destinations, "g", "a"));
	}

	@Test
	public void sortTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			GraphUtility.sort(sources, destinations);
		});
	}

	@Test
	public void sortCorrectlyTest() {
		List list = GraphUtility.sort(sourcesAcyc, destinationsAcyc);
		assertEquals(list.get(1), "d");
	}
}
