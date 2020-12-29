package assign09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/**
 * Tester class for Hash Table
 *
 * @author Kyle Kazemini && Robert Davidson
 */
public class HashTableTester {

    public HashTable<Integer, String> table;

    @BeforeEach
    void setUp() {
        table = new HashTable<Integer, String>();

        table.put(0, "hi");
        table.put(5, "hello");
        table.put(10, "greetings");
    }

    @Test
    public void testClear() {
        table.clear();

        assertFalse(table.containsValue("hi"));
        assertFalse(table.containsValue("hello"));
        assertFalse(table.containsValue("greetings"));
    }

    @Test
    public void testContainsKey() {
        assertTrue(table.containsKey(0));
        assertTrue(table.containsKey(5));

        assertFalse(table.containsKey(2));
    }

    @Test
    public void testContainsValue() {
        assertTrue(table.containsValue("hi"));
        assertTrue(table.containsValue("hello"));

        assertFalse(table.containsValue("3"));
    }

    @Test
    public void testEntries() {
        ArrayList<MapEntry<Integer, String>> list = new ArrayList<MapEntry<Integer, String>>();
        list.add(new MapEntry<Integer, String>(0, "hi"));
        list.add(new MapEntry<Integer, String>(5, "hello"));
        list.add(new MapEntry<Integer, String>(10, "greetings"));

        assertEquals(list, table.entries());
    }

    @Test
    public void testGet() {
        assertEquals("hi", table.get(0));
        assertEquals("hello", table.get(5));
    }

    @Test
    public void testIsEmpty() {
        assertFalse(table.isEmpty());

        table.remove(0);

        assertFalse(table.isEmpty());

        table.remove(5);
        table.remove(10);

        assertTrue(table.isEmpty());
    }

    @Test
    public void testPut() {
        table.put(2, "one");
        table.put(3, "two");

        assertEquals(table.get(0), "hi");
        assertEquals(table.get(2), "one");
        assertEquals(table.get(3), "two");
    }

    @Test
    public void testRemove() {
        table.put(2, "one");
        table.put(3, "two");

        table.remove(3);
        table.remove(2);

        assertFalse(table.isEmpty());

        table.remove(5);
        table.remove(0);
        table.remove(10);

        assertTrue(table.isEmpty());
    }

    @Test
    public void testQuadPro() {
        //table.put(0, "0a");
        table.put(1, "1");
        table.put(4, "4");
        table.put(9, "9");
        table.put(499, "0b");
        table.put(499, "0c");


        assertTrue("0c".equals(table.put(499, "test")));
    }
    
    @Test
    public void testCollisions() {
    	table.put(1, "1");
        table.put(1, "4");
        table.put(1, "9");
        table.put(1, "0b");
        table.put(1, "0c");
        
        assertTrue("0c".equals(table.put(1, "test")));
    }

    @Test
    public void testNullMapping() {
        table.put(499, null);
        assertNull(table.put(499, "test"));
    }

    @Test
    public void testSize() {
        assertNotEquals(0, table.size());

        assertEquals(3, table.size());
    }

}
