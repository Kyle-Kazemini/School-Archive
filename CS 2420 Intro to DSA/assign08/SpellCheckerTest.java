package assign08;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.io.File;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tester class for Spell Checker
 * 
 * @author Erin Parker && Kyle Kazemini && Joseph Norton
 */
public class SpellCheckerTest {

	SpellChecker spellCheck;
	List<String> list;
	File file;
	
	@BeforeEach
	void setup() {
		 spellCheck = new SpellChecker(new File("C:/Users/utesp/eclipse-workspace/CS2420/dictionary.txt"));
		 list = new ArrayList<String>();
		 file = new File("C:/Users/utesp/eclipse-workspace/CS2420/dictionary.txt");
	}
	
	@Test
	public void testOne() {
		list.add("dagger");
		
		spellCheck.addToDictionary("thief");
		spellCheck.addToDictionary("luck");
		spellCheck.addToDictionary("daggre");
		
		assertFalse(list.equals(spellCheck.spellCheck(file))); 
		
		spellCheck.removeFromDictionary("dagger");
		
		ArrayList<String> wrongList = (ArrayList<String>) spellCheck.spellCheck(file);
		
		assertTrue(wrongList.get(0).equals(list.get(0)));
		assertTrue(wrongList.size() == list.size());
	}
	
	@Test
	public void testTwo() {
		list.add("did");
		list.add("fat");
		
		spellCheck.addToDictionary("salesperson");
		spellCheck.addToDictionary("grape");
		spellCheck.addToDictionary("did");
		spellCheck.addToDictionary("fat");
		
		spellCheck.addToDictionary("rgpae");
		
		assertFalse(list.equals(spellCheck.spellCheck(file)));
		
		spellCheck.removeFromDictionary("did");
		spellCheck.removeFromDictionary("fat");
		
		list.remove("salespresno");
		
		assertTrue(list.equals(spellCheck.spellCheck(file)));
	}
	
	@Test
	public void testThree() {
		spellCheck.addToDictionary("deaf");
		spellCheck.addToDictionary("melted");
		spellCheck.addToDictionary("lab");
		
		spellCheck.addToDictionary("fead");
		list.add("fead");
		
		assertFalse(list.equals(spellCheck.spellCheck(file)));
		
		list.remove("fead");
		spellCheck.addToDictionary("telmed");
		spellCheck.addToDictionary("lba");
		
		ArrayList<String> wrongList = (ArrayList<String>) spellCheck.spellCheck(file);
		
		assertTrue(list.equals(spellCheck.spellCheck(file)));
		
	}
	
	@Test
	public void testFour() {
		ArrayList<String> wrongList = (ArrayList<String>) spellCheck.spellCheck(new File("C:/Users/utesp/eclipse-workspace/CS2420/hello_world.txt"));
		
		assertEquals(0, wrongList.size());
		
		wrongList = (ArrayList<String>) spellCheck.spellCheck(new File("C:/Users/utesp/eclipse-workspace/CS2420/good_luck.txt"));
		
		assertEquals(0, wrongList.size());
	}
}
