package assign04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/*
 * This class contains methods to check if two words are
 * anagrams and find the largest set of anagrams in a list.
 * 
 * @author Kyle Kazemini & Anna Shelukha
 * @version January 30, 2020
 */
public class AnagramChecker
{

    /*
     * This method returns the lexicographically-sorted version of the input string using insertion sort.
     * 
     * @param String
     * 
     * @return String.
     */
    public static String sort (String s)
    {
        // create building String
        String anagram = "";

        // char array for String breakdown
        Character[] arr = new Character[s.length()];

        // break apart string into array of chars
        for (int i = 0; i < s.length(); i++)
            arr[i] = s.charAt(i);

        // sort the char array
        insertionSort(arr, ( (Character a, Character b) -> a.compareTo(b)));

        // rebuild char array into String
        for (int i = 0; i < arr.length; i++)
            anagram += arr[i];

        // return new String
        return anagram;
    }

    /*
     * This generic method sorts the input array using an insertion sort and the input Comparator object.
     * 
     * @param generic type array & comparator object.
     *
     */
    public static <T> void insertionSort (T[] arr, Comparator<? super T> comp)
    {
        // for every element in the array
        for (int i = 1; i < arr.length; i++)
        {
            // initialize variables
            T val = arr[i];
            int j;

            // if value isn't in the appropriate place in the array
            for (j = i - 1; j >= 0 && comp.compare(arr[j], val) > 0; j--)
                // shift all values greater than over one
                arr[j + 1] = arr[j];

            // insert value at the new, appropriate location
            arr[j + 1] = val;
        }
    }

    /*
     * This method returns true if the two input strings are anagrams of each other, otherwise returns false.
     * 
     * @param two input strings to compare.
     */
    public static boolean areAnagrams (String a, String b)
    {
        // sort both strings in lower case to avoid sorting capital letters separately
        String aSorted = sort(a.toLowerCase());
        String bSorted = sort(b.toLowerCase());

        // if sorted strings are equal, they're anagrams
        if (aSorted.equals(bSorted))
            return true;

        // if previous return isn't triggered, strings aren't anagrams
        return false;
    }

    /*
     * This method returns the largest group of anagrams in the input array of words, in no particular order. It returns
     * an empty array if there are no anagrams in the input array. If there are multiple anagram groups of identical
     * sizes, returns the first group (positioning of arrays being decided lexicographically).
     * 
     * @param array of Strings.
     * 
     * @return array of Strings.
     */
    public static String[] getLargestAnagramGroup (String[] arr)
    {
        // sort the array in relation to the sorted versions of Strings
        insertionSort(arr, ( (String a, String b) -> sort(a.toLowerCase()).compareTo(sort(b.toLowerCase()))));

        // initialize counters and building String, with count set at 1 in order to account for every word tested
        int max = 0, count = 1;
        String anagramKey = "";

        // comparing every element in the array
        for (int i = 0; i < arr.length - 2; i++)
        {
            // compare the String and the String after it
            if (areAnagrams(arr[i], arr[i + 1]))
            {
                // if anagrams, increase anagram count
                count++;

                // if the anagram streak is larger
                if (count > max)
                {
                    // reset max
                    max = count;
                    // set String key for later anagram retrieval
                    anagramKey = arr[i];
                }
            }
            // resets count if anagram streak is broken
            else
                count = 1;
        }

        // initialize return String
        String[] anagramArray = new String[max];
        // counter for array insertion
        int i = 0;

        // for every element in the array
        for (String key : arr)
            // if the word is an anagram of the set key
            if (areAnagrams(key, anagramKey))
            {
                // add it to the list
                anagramArray[i] = key;
                // increment count for the next insertion
                i++;
            }

        // return the built array
        return anagramArray;
    }

    /*
     * This method behaves the same as the previous method, but reads the list of words from the input filename. It is
     * assumed that the file contains one word per line. If the file does not exist or is empty, the method returns an
     * empty array because there are no anagrams.
     * 
     * @param String - name of file.
     * 
     * @return array of Strings.
     */
    public static String[] getLargestAnagramGroup (String filename) throws FileNotFoundException
    {
        //temporary array to store Strings in
        ArrayList<String> tempArr = new ArrayList<String>();

        //scanner to extract Strings from file
        Scanner scn = new Scanner(new File(filename));
        
        //for every element in the scanner/file, add the element to the list
        while (scn.hasNext())
            tempArr.add(scn.next());
        scn.close();

        //creates String array that will be passed to find anagrams with
        String[] fileArray = new String[tempArr.size()];
        
        //adds every element over to the String array
        int i = 0;
        for (String key : tempArr)
        {
            fileArray[i] = key;
            i++;
        }
        

        //finds the largest anagram group and returns it
        return getLargestAnagramGroup(fileArray);
    }
}
