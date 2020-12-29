package lec05.comp;

import java.util.Comparator;

/**
 * The purpose of this class is to provide a method for comparing
 * strings by the sum of the ASCII/Unicode integer codes of the 
 * characters in each string (instead of the natural ordering that is 
 * lexicographical and encoded via Comparable).
 * 
 * Method comments are left for students to provide.
 * 
 * @author Erin Parker & CS 2420 class
 * @version January 21, 2020
 */
public class CompareByAsciiSum implements Comparator<String> {

	public int compare(String o1, String o2) {
		return sumOfAsciiCodes(o1) - sumOfAsciiCodes(o2);
	}
	
	private int sumOfAsciiCodes(String s) {
		int total = 0;
		for(int i = 0; i < s.length(); i++)
			total += s.charAt(i);
		return total;
	}
}