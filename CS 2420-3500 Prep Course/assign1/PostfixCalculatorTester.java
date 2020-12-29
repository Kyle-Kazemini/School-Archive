package assign1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

/**
 * Tester class for PostfixCalculator
 * 
 * @author Kyle Kazemini
 * @version 27 June 2020
 */
public class PostfixCalculatorTester {

    String one = "2 3 +";
    String two = "4 6 * 3 6 + -";
    String three = "3 2 / 2 1 + *";
    String four = "9 2 / 3 - 12 2 * +";

    @Test
    public void testOne() {
        assertEquals(5, PostfixCalculator.evaluate(one));
    }

    @Test
    public void testTwo() {
        assertEquals(15, PostfixCalculator.evaluate(two));
    }

    @Test
    public void testThree() {
        assertEquals(3, PostfixCalculator.evaluate(three));
    }

    @Test
    public void testFour() {
        assertEquals(25, PostfixCalculator.evaluate(four));
    }
}