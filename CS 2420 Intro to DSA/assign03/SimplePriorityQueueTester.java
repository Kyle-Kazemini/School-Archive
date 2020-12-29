package assign03;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
* Tester class for the SimplePriorityQueue.
* 
* @author Kyle Kazemini & Anna Shelukha
* @version January 23, 2020
* 
*/

public class SimplePriorityQueueTester
{

    private ArrayList<Integer> intArr;
    private ArrayList<String> stringArray;
    private SimplePriorityQueue<Integer> intQueue;
    private SimplePriorityQueue<String> stringQueue;
    private SimplePriorityQueue<Integer> compIntQueue;

    @BeforeEach
    void setUp ()
    {
        intArr = new ArrayList<Integer>();
        intArr.add(10);
        intArr.add(9);
        intArr.add(8);
        intArr.add(6);
        intArr.add(3);
        intArr.add(0);
        intArr.add(-2);
        intArr.add(-3);

        stringArray = new ArrayList<String>();
        stringArray.add("Adjacent");
        stringArray.add("Helicopter");
        stringArray.add("Juice");
        stringArray.add("Potato");
        stringArray.add("Santa");
        stringArray.add("Zero");

        intQueue = new SimplePriorityQueue<Integer>();
        stringQueue = new SimplePriorityQueue<String>();
        compIntQueue = new SimplePriorityQueue<Integer>( (a1, a2) -> a2 - a1);
    }

    @Test
    public void testIntFindMin ()
    {
        intQueue.insertAll(intArr);
        int min = intQueue.findMin();
        assertEquals(-3, min);
    }

    @Test
    public void testSize ()
    {
        intQueue.insertAll(intArr);
        int size = intQueue.size();
        assertEquals(8, size);
    }

    @Test
    public void testIsEmpty ()
    {
        intQueue.clear();
        assertEquals(true, intQueue.isEmpty());
    }

    @Test
    public void clearTester ()
    {
        intQueue.insertAll(intArr);
        intQueue.clear();
        assertEquals(0, intQueue.size());
    }

    @Test
    public void testStringInsert ()
    {
        stringQueue.insertAll(stringArray);
        stringQueue.insert("Abate");
        assertEquals("Abate", stringQueue.findMin());
    }

    @Test
    public void testStringClear ()
    {
        stringQueue.insertAll(stringArray);
        stringQueue.clear();
        assertEquals(0, stringQueue.size());
    }

    @Test
    public void testStringRegFindMin ()
    {
        stringQueue.insertAll(stringArray);
        assertEquals(stringQueue.findMin(), "Adjacent");
    }

    @Test
    public void testStringRegDeleteMin ()
    {
        stringQueue.insertAll(stringArray);
        stringQueue.deleteMin();
        assertEquals(stringQueue.findMin(), "Helicopter");
    }

    @Test
    public void testCompFindMin ()
    {
        compIntQueue.insertAll(intArr);
        assertEquals(compIntQueue.findMin(), 10);
    }

    @Test
    public void testCompDeleteMin ()
    {
        compIntQueue.insertAll(intArr);
        compIntQueue.deleteMin();
        assertEquals(compIntQueue.findMin(), 9);
    }
    
    @Test
    public void testCompSize ()
    {
        compIntQueue.insertAll(intArr);
        assertEquals(compIntQueue.size(), 8);
    }
    
    @Test
    public void testCompClear ()
    {
        compIntQueue.insertAll(intArr);
        compIntQueue.clear();
        assertEquals(compIntQueue.size(), 0);
    }

    @Test
    public void testCompInsert ()
    {
        compIntQueue.insertAll(intArr);
        compIntQueue.insert(11);
        assertEquals(compIntQueue.findMin(), 11);
    }
}
