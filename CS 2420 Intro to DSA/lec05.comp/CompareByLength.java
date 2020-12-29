package lec05.comp;

import java.util.Comparator;

/**
 * The purpose of this class is to provide a method for comparing
 * strings by length (instead of the natural ordering that is 
 * lexicographical and encoded via Comparable).
 * 
 * Method comment is left for students to provide.
 * 
 * @author Erin Parker & CS 2420 class
 * @version January 21, 2020
 */
public class CompareByLength implements Comparator<String> {

	public int compare(String s1, String s2) {
		if(s1.length() > s2.length())
			return 1;
		if(s1.length() < s2.length())
			return -1;
		// to break any ties in length, use the natural ordering of Strings
		return s1.compareTo(s2);
		
		/* also correct:
		 
		int diff = s1.length() - s2.length();
		
		if(diff == 0)
			return s1.compareTo(s2);
		return diff;
		*/
	}
}