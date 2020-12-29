package assign05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import assign05.ArrayListSorter;

/**
 * This program collects running times for insertion sort in the best, average, and worst cases.
 * 
 * Notice that best case = sorted array, average case = permuted array, worst case = reverse-sorted array. This is not
 * necessarily the same for all sorting algorithms.
 * 
 * @author Erin Parker & Kyle Kazemini & Anna Shelukha
 * @version January 30, 2020
 */
public class SortTimer
{

    public static void main (String[] args)
    {

        final int NSTART = 1000;
        final int NSTOP = 20000;
        final int NINCR = 1000;

        System.out.println("Insertion sort\nN\tBest case\tAverage case\tWorst case\n");

        for (int N = NSTART; N <= NSTOP; N += NINCR)
        {
            System.out.print(N + "\t");

            // Build three arrays of N integers, two sorted and one
            // reverse-sorted.
            ArrayList<Integer> sortedArr = new ArrayList<Integer>();
            sortedArr = ArrayListSorter.generateAscending(N);
            ArrayList<Integer> permutedArr = new ArrayList<Integer>();
            permutedArr = ArrayListSorter.generatePermuted(N);
            ArrayList<Integer> reverseSortedArr = new ArrayList<Integer>();
            reverseSortedArr = ArrayListSorter.generateDescending(N);

            System.out.println(getTimeMerge(sortedArr) + "\t" + getTimeMerge(permutedArr) + "\t"
                    + getTimeMerge(reverseSortedArr) + "\t" + getTimeQuick(sortedArr) + "\t"
                    + getTimeQuick(permutedArr) + "\t" + getTimeQuick(reverseSortedArr));
        }

    }

    private static long getTimeMerge (ArrayList<Integer> arr)
    {

        final int TIMES_TO_LOOP = 10;

        // Let things stabilize
        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 1000000000)
            ;

        // Time the sort
        startTime = System.nanoTime();
        for (int i = 0; i < TIMES_TO_LOOP; i++)
        {
            // Don't sort an already sorted array!
            ArrayList<Integer> temp = new ArrayList<Integer>();

            for (int j = 0; j < arr.size(); j++)
            {
                temp.add(null);
                temp.set(j, arr.get(j));
            }
            ArrayListSorter.mergesort(temp);
        }

        long midTime = System.nanoTime();

        // Time the "overhead"
        for (int i = 0; i < TIMES_TO_LOOP; i++)
        {
            ArrayList<Integer> temp = new ArrayList<Integer>();

            for (int j = 0; j < arr.size(); j++)
            {
                temp.add(null);
                temp.set(j, arr.get(j));
            }
        }

        long endTime = System.nanoTime();

        return ((midTime - startTime) - (endTime - midTime)) / TIMES_TO_LOOP;
    }

    private static long getTimeQuick (ArrayList<Integer> arr)
    {

        final int TIMES_TO_LOOP = 10;

        // Let things stabilize
        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 1000000000)
            ;

        // Time the sort
        startTime = System.nanoTime();
        for (int i = 0; i < TIMES_TO_LOOP; i++)
        {
            // Don't sort an already sorted array!
            ArrayList<Integer> temp = new ArrayList<Integer>();

            for (int j = 0; j < arr.size(); j++)
            {
                temp.add(null);
                temp.set(j, arr.get(j));
            }
            ArrayListSorter.quicksort(temp);
        }

        long midTime = System.nanoTime();

        // Time the "overhead"
        for (int i = 0; i < TIMES_TO_LOOP; i++)
        {
            ArrayList<Integer> temp = new ArrayList<Integer>();

            for (int j = 0; j < arr.size(); j++)
            {
                temp.add(null);
                temp.set(j, arr.get(j));
            }
        }

        long endTime = System.nanoTime();

        return ((midTime - startTime) - (endTime - midTime)) / TIMES_TO_LOOP;
    }

}