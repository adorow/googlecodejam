package codejam.year2017.qualification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
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
public class B {

    private static Scanner in;
    private static PrintStream out;

    private static final String CASE_N = "Case #";
    private static final String COLON_SPACE = ": ";

    public static void main(String[] args) throws Throwable {
        in = new Scanner(System.in);
        in = new Scanner(new FileInputStream("./src/main/resources/codejam/year2017/qualification/B/B-large.in"));
        out = System.out;
//        out = new PrintStream(new FileOutputStream("./src/main/resources/codejam/year2017/qualification/B/B-large.out"));

        int T = in.nextInt();

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

    private static String solve() {
        String N = in.next();
        StringBuilder solution = new StringBuilder(N);

        int decreasingIndex;
        while ((decreasingIndex = findDecreasingIndex(N)) > -1) {
            for (int i = decreasingIndex; i < N.length(); i++) {
                solution.setCharAt(i, '9');
            }
            int toReduce = decreasingIndex - 1;
            while (toReduce > -1) {
                char ch = solution.charAt(toReduce);

                if (ch > '0') {
                    solution.setCharAt(toReduce, charMinusOne(ch));
                    break;
                }
                if (toReduce == 0) {
                    solution.deleteCharAt(0);
                    break;
                }
                solution.setCharAt(toReduce, '9');
                toReduce--;
            }

            // delete starting zeros
            while (solution.length() > 1 && solution.charAt(0) == '0') {
                solution.deleteCharAt(0);
            }
            N = solution.toString();
        }

        return N;
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
