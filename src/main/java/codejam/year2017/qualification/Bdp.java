package codejam.year2017.qualification;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Problem

 Tatiana likes to keep things tidy. Her toys are sorted from smallest to largest, her pencils are sorted from shortest to longest and her computers from oldest to newest. One day, when practicing her counting skills, she noticed that some integers, when written in base 10 with no leading zeroes, have their digits sorted in non-decreasing order. Some examples of this are 8, 123, 555, and 224488. She decided to call these numbers tidy. Numbers that do not have this property, like 20, 321, 495 and 999990, are not tidy.

 She just finished counting all positive integers in ascending order from 1 to N. What was the last tidy number she counted?

 Input

 The first line of the input gives the number of test cases, T. T lines follow. Each line describes a test case with a single integer N, the last number counted by Tatiana.

 Output

 For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y is the last tidy number counted by Tatiana.

 Limits

 1 ≤ T ≤ 100.
 Small dataset

 1 ≤ N ≤ 1000.
 Large dataset

 1 ≤ N ≤ 1018.
 Sample


 Input

 Output

 4
 132
 1000
 7
 111111111111111110

 Case #1: 129
 Case #2: 999
 Case #3: 7
 Case #4: 99999999999999999

 Note that the last sample case would not appear in the Small dataset.
 */
public class Bdp {

    private static Scanner in;
    private static PrintStream out;

    private static final String CASE_N = "Case #";
    private static final String COLON_SPACE = ": ";

    private static String[] dp = new String[5_000_000];
    private static int dplen;

    public static void main(String[] args) throws Throwable {
        in = new Scanner(System.in);
        in = new Scanner(new FileInputStream("./src/main/resources/codejam/year2017/qualification/B/B-large.in"));
        out = System.out;
//        out = new PrintStream(new FileOutputStream("./src/main/resources/codejam/year2017/qualification/B/B-large.out"));

        int T = in.nextInt();

        precompute();

//        System.out.println("precomputed ended at " + dplen);

        for (int t = 1; t <= T; t++) {
            out.print(CASE_N);
            out.print(t);
            out.print(COLON_SPACE);

            String solution = solve();
            out.print(solution);

            out.println();
        }
        out.flush();
    }

    private static void precompute() {
//        int dpi = 0;
        dplen = 0;

        for (int i = 1; i <= 9; i++) {
            dp[dplen++] = String.valueOf(i);
        }

        int prevStepStart = 0;
        int prevStepEnd = dplen;
        for (int dploop = 2; dploop <= 18; dploop++) {

            for (int dpi = prevStepStart; dpi < prevStepEnd; dpi++) {
                String ref = dp[dpi];
                char lastChar = ref.charAt(ref.length()-1);

                for (char num = lastChar; num <= '9'; num++) {
                    dp[dplen++] = ref + num;
                }

            }

            prevStepStart = prevStepEnd;
            prevStepEnd = dplen;
        }

    }

    private static int numericCompare(String a, String b) {
        if (a.length() == b.length()) {
            return a.compareTo(b);
        }
        return a.length() - b.length();
    }

    /*
     * In this solution we simply precompute all the existing tidy numbers, and put them into an array, in order,
     * and for every query we find out where that number is in the list (that's the result), and if it is not in the
     * list, we should find the largest number smaller than the number we got.
     */
    private static String solve() {
        String N = in.next();

        int index = Arrays.binarySearch(dp, 0, dplen, N, Bdp::numericCompare);

        if (index > -1) {
            return dp[index];
        }

        index = Math.abs(index);
        // index-2 because: Arrays.binarySearch returns negatives with "position where the number would be inserted - 1", so 0 is -1, 1 is -2...
        // and we need to return the smaller number that is a "tidy number"
        // so we return "abs(index) /*what was returned*/ - 1 /*normalize to the positive index where it would be*/ - 1 /*to get the number just below it*/
        return dp[index-2];

    }

    private static char charMinusOne(char c) {
        return (char) (c - 1);
    }

    private static boolean decreasing(char left, char right) {
        return right < left;
    }

    private static int findDecreasingIndex(String n) {
        for (int i = 1; i < n.length(); i++) {
            if (decreasing(n.charAt(i-1), n.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

}
