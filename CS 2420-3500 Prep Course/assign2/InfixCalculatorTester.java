package assign2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class InfixCalculatorTester {

    String one = "3 + 2";
    String two = "(3 * 8) / 4";
    String three = "(4 / 3) * 2 - 6 + 1";
    
    @BeforeEach
    public void setup() {

    }

    @Test
    public void testOne() {
        assertEquals(5, InfixCalculator.evaluate(one));
    }

    @Test
    public void testTwo() {
        assertEquals(6, InfixCalculator.evaluate(two));
    }

    @Test
    public void testThree() {
        assertEquals(-3, InfixCalculator.evaluate(three));
    }
}