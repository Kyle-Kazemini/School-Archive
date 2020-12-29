package comprehensive;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class RandomPhraseGeneratorTest {

    public Hashtable<String, ArrayList<String>> table = new Hashtable<String, ArrayList<String>>();
    public RandomPhraseGenerator rpg = new RandomPhraseGenerator();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generatePhrase_super_simple() {
        rpg.parseFile("src/comprehensive/poetic_sentence.g");
        String s = rpg.generatePhrase();

        rpg.parseFile("src/comprehensive/poetic_sentence.g");
        String x = rpg.generatePhrase();

        rpg.parseFile("src/comprehensive/poetic_sentence.g");
        String y = rpg.generatePhrase();

        rpg.parseFile("src/comprehensive/poetic_sentence.g");
        String z = rpg.generatePhrase();

        rpg.parseFile("src/comprehensive/poetic_sentence.g");
        String w = rpg.generatePhrase();

        rpg.parseFile("src/comprehensive/poetic_sentence.g");
        String g = rpg.generatePhrase();

        assertTrue(true);
        //assertEquals(rpg.queue.poll(), "The");
    }

    @Test
    void generatePhrase_poetic_sentence() {
        RandomPhraseGenerator.main(new String[] {"src/comprehensive/assignment_extension_request.g", "2"});
        System.out.println();
        RandomPhraseGenerator.main(new String[] {"src/comprehensive/mathematical_expression.g", "3"});
        System.out.println();
        RandomPhraseGenerator.main(new String[] {"src/comprehensive/poetic_sentence.g", "3"});
        System.out.println();
        RandomPhraseGenerator.main(new String[] {"src/comprehensive/my_supersimple.g", "20"});
    }

    @Test
    void generatePhrase_mathematical_expression() {
    }

    @Test
    void generatePhrase_assignment_extension_request() {
    }

    @Test
    void parseFile_super_simple() {
    }

    @Test
    void parseFile_poetic_sentence() {
    }

    @Test
    void parseFile_mathematical_expression() {
    }

    @Test
    void parseFile_assignment_extension_request() {
    }
}