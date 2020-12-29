package assign06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LinkedListStackTester
{
    private LinkedListStack<Integer> intStack;
    private LinkedListStack<String> stringStack;

    @BeforeEach
    void setup ()
    {
        intStack = new LinkedListStack<Integer>();
        stringStack = new LinkedListStack<String>();

        intStack.push(2);
        intStack.push(4);
        intStack.push(6);
        intStack.push(8);
        intStack.push(10);
        intStack.push(12);

        stringStack.push("2");
        stringStack.push("4");
        stringStack.push("6");
        stringStack.push("8");
        stringStack.push("10");
        stringStack.push("12");
    }

    @Test
    public void peekIntTest ()
    {
        assertEquals(12, intStack.peek());
    }

    @Test
    public void peekStringTest ()
    {
        assertEquals("12", stringStack.peek());
    }

    @Test
    public void removeIntTest ()
    {
        int test = intStack.pop();
        assertEquals(12, test);
        int test2 = intStack.pop();
        assertEquals(10, test2);
    }

    @Test
    public void removeStringTest ()
    {
        String test = stringStack.pop();
        assertEquals("12", test);
        String test2 = stringStack.pop();
        assertEquals("10", test2);
    }

    @Test
    public void sizeIntTest ()
    {
        int index = intStack.size();
        assertEquals(6, index);
    }

    @Test
    public void sizeStringTest ()
    {
        int index = stringStack.size();
        assertEquals(6, index);
    }

    @Test
    public void isEmptyClearIntTest ()
    {
        intStack.clear();
        assertEquals(true, intStack.isEmpty());
    }

    @Test
    public void isEmptyClearStringTest ()
    {
        stringStack.clear();
        assertEquals(true, stringStack.isEmpty());
    }

    @Test
    public void clearIntTest ()
    {
        intStack.clear();
        assertThrows(NoSuchElementException.class, () -> {
            intStack.peek();
        });
    }

    @Test
    public void clearStringTest ()
    {
        stringStack.clear();
        assertThrows(NoSuchElementException.class, () -> {
            stringStack.peek();
        });
    }
}
