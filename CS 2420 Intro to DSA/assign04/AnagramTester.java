package assign04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnagramTester
{
    private String[] shortAnagramArr;
    private String[] shortArr;
    private String[] longArr;
    private String[] longSortedArr;
    private String[] multipleAnagramArr;
    private String[] emptyArr;
    private String[] singleAnagramArr;
    private String[] longerAnagramArr;
    private String[] twoEqualAnagramArr;


    @BeforeEach
    void setUp ()
    {
        shortAnagramArr = new String[] {"Apple", "Elppa"};

        shortArr = new String[] {"Apple", "Banana"};
        
        longArr = new String[] {"Adjacent", "Helicopter", "Juice", "Potato", "Santa", "Zero", "Satan"};
        
        longSortedArr = new String[]{"Adjacent", "Helicopter", "Juice", "Potato", "Santa", "Satan", "Zero"};
   
        multipleAnagramArr = new String[] {"Apple", "Elppa", "carets", "Caters", "caster", "crates", "Reacts", "recast", "traces"};
   
        emptyArr = new String[] {};
        
        singleAnagramArr = new String[] {"Santa, Satan"};
        
        longerAnagramArr = new String[] {"carets", "Caters", "caster", "crates", "Reacts", "recast", "traces"};
   
        twoEqualAnagramArr = new String[] {"caster", "Crates", "Santa", "satan"};
    }
    
    @Test
    public void testSort ()
    {
        assertEquals(AnagramChecker.sort(shortArr[0]), "Aelpp");
        
    }
    
    @Test
    public void testEmptySort ()
    {
        assertEquals(AnagramChecker.sort(""), "");
        
    }
    
    @Test
    public void testAreAnagramsTrue ()
    {
        assertTrue(AnagramChecker.areAnagrams(shortAnagramArr[0], shortAnagramArr[1]));
    }
    
    @Test
    public void testAreAnagramsFalse ()
    {
        assertFalse(AnagramChecker.areAnagrams(shortArr[0], shortArr[1]));
    }
    
    @Test
    public void testAreAnagramsEmpty ()
    {
        assertTrue(AnagramChecker.areAnagrams("", ""));
    }
    
    @Test
    public void testInsertionSort ()
    {
        AnagramChecker.insertionSort(longArr, ((String a1, String a2) -> a1.compareTo(a2)));
        assertEquals(longArr[5], "Satan");
        
    }
    
    @Test
    public void testOneAnagramGroup ()
    {
        assertEquals((AnagramChecker.getLargestAnagramGroup(longArr)).length, 2);
    }
    
    @Test
    public void testMultipleAnagramGroup ()
    {
        assertEquals((AnagramChecker.getLargestAnagramGroup(multipleAnagramArr)).length, 7);
    }
    
    @Test
    public void testEmptyAnagramGroup ()
    {
        assertEquals((AnagramChecker.getLargestAnagramGroup(emptyArr)).length, 0);
    }
    
    @Test
    public void testZeroAnagramGroup ()
    {
        assertEquals((AnagramChecker.getLargestAnagramGroup(shortArr)).length, 0);
    }
    
    @Test
    public void testFileAnagramGroup () throws FileNotFoundException
    {
        assertEquals((AnagramChecker.getLargestAnagramGroup("src/assign04/sample_word_list.txt")).length, 7);
    }
    
    @Test
    public void testAreAngramsLengths ()
    {
        assertFalse(AnagramChecker.areAnagrams("Apple", "Apples"));
    }
    @Test
    public void testtwoEqualAnagrams ()
    {
        String[] tempArr = new String[2];
        tempArr = AnagramChecker.getLargestAnagramGroup(twoEqualAnagramArr);
        assertEquals(tempArr[1], "satan");
    }
}
