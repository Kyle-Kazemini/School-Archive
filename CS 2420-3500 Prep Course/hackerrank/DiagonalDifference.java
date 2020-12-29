package hackerrank;

import java.io.*;
import java.util.*;
//import java.util.stream.IntStream;
//import java.util.stream.Stream;

/**
 * Diagonal Difference Hacker Rank Problem.
 */
class Result {

    /*
     * Complete the 'diagonalDifference' function below.
     *
     * The function is expected to return an INTEGER. The function accepts
     * 2D_INTEGER_ARRAY arr as parameter.
     */

    public static int diagonalDifference(List<List<Integer>> arr) {
        int leftDiagonal = 0, rightDiagonal = 0;
        int rows = arr.size();
        int columns = arr.get(1).size();

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == (j + 1))
                    leftDiagonal += arr.get(i).get(j);
            }
            rightDiagonal += arr.get(i).get(columns - i);
        }

        return Math.abs(leftDiagonal - rightDiagonal);
    }

    public static class Solution {
        public static void main(String[] args) throws IOException {
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

            //int n = Integer.parseInt(bufferedReader.readLine().trim());

            //  List<List<Integer>> arr = new ArrayList<>();
            //  List<Integer> a = new ArrayList<>();
            //  List<Integer> b = new ArrayList<>();
            //  List<Integer> c = new ArrayList<>();
            //  List<Integer> d = new ArrayList<>();

            //  a.add(3);
            //  b.add(11);
            //  b.add(2);
            //  b.add(4);
            //  c.add(4);
            //  c.add(5);
            //  c.add(6);
            //  d.add(10);
            //  d.add(8);
            //  d.add(-12);

            //  arr.add(a);
            //  arr.add(b);
            //  arr.add(c);
            //  arr.add(d);
            
            // IntStream.range(0, n).forEach(i -> {

            //     try {
            //         arr.add(Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            //                 .map(Integer::parseInt).collect(toList()));
            //     } catch (IOException ex) {
            //         throw new RuntimeException(ex);
            //     }
            // });

            // int result = Result.diagonalDifference(arr);

            // bufferedWriter.write(String.valueOf(result));
            // bufferedWriter.newLine();

            // bufferedReader.close();
            // bufferedWriter.close();
        }
    }
}