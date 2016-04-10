package codejam.year2016.qualification;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Counting Sheep (https://code.google.com/codejam/contest/6254486/dashboard#s=p0)
 *
 * Problem

 Bleatrix Trotter the sheep has devised a strategy that helps her fall asleep faster. First, she picks a number N. Then she starts naming N, 2 × N, 3 × N, and so on. Whenever she names a number, she thinks about all of the digits in that number. She keeps track of which digits (0, 1, 2, 3, 4, 5, 6, 7, 8, and 9) she has seen at least once so far as part of any number she has named. Once she has seen each of the ten digits at least once, she will fall asleep.

 Bleatrix must start with N and must always name (i + 1) × N directly after i × N. For example, suppose that Bleatrix picks N = 1692. She would count as follows:

 N = 1692. Now she has seen the digits 1, 2, 6, and 9.
 2N = 3384. Now she has seen the digits 1, 2, 3, 4, 6, 8, and 9.
 3N = 5076. Now she has seen all ten digits, and falls asleep.
 What is the last number that she will name before falling asleep? If she will count forever, print INSOMNIA instead.

 Input

 The first line of the input gives the number of test cases, T. T test cases follow. Each consists of one line with a single integer N, the number Bleatrix has chosen.

 Output

 For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y is the last number that Bleatrix will name before falling asleep, according to the rules described in the statement.

 Limits

 1 ≤ T ≤ 100.
 Small dataset

 0 ≤ N ≤ 200.
 Large dataset

 0 ≤ N ≤ 106.
 Sample


 Input

 Output

 5
 0
 1
 2
 11
 1692

 Case #1: INSOMNIA
 Case #2: 10
 Case #3: 90
 Case #4: 110
 Case #5: 5076


 In Case #1, since 2 × 0 = 0, 3 × 0 = 0, and so on, Bleatrix will never see any digit other than 0, and so she will count forever and never fall asleep. Poor sheep!

 In Case #2, Bleatrix will name 1, 2, 3, 4, 5, 6, 7, 8, 9, 10. The 0 will be the last digit needed, and so she will fall asleep after 10.

 In Case #3, Bleatrix will name 2, 4, 6... and so on. She will not see the digit 9 in any number until 90, at which point she will fall asleep. By that point, she will have already seen the digits 0, 1, 2, 3, 4, 5, 6, 7, and 8, which will have appeared for the first time in the numbers 10, 10, 2, 30, 4, 50, 6, 70, and 8, respectively.

 In Case #4, Bleatrix will name 11, 22, 33, 44, 55, 66, 77, 88, 99, 110 and then fall asleep.

 Case #5 is the one described in the problem statement. Note that it would only show up in the Large dataset, and not in the Small dataset.
 */
public class A {

    private static Scanner in;
    private static PrintStream out;

    private static final String INSOMNIA = "INSOMNIA";
    private static final String CASE_N = "Case #";
    private static final String COLON_SPACE = ": ";

    private static final boolean[] nums = new boolean[10];

    public static void main(String[] args) throws Throwable {
        //in = new Scanner(System.in);
        in = new Scanner(A.class.getResourceAsStream("A-large.in"));
        //out = System.out;
        out = new PrintStream(new FileOutputStream("./resource/qualification_round/A-large.out"));

        int T = in.nextInt();

        for (int t = 1; t <= T; t++) {
            out.print(CASE_N);
            out.print(t);
            out.print(COLON_SPACE);

            int N = in.nextInt();

            long result = findCount(N);
            if (result < 0) {
                out.println(INSOMNIA);
            } else {
                out.println(result);
            }
        }
        out.flush();
    }

    private static long findCount(long n) {
        if (n <= 0) {
            return -1;
        }

        long maxNum = maxNumFrom(n);

        long cur = n;
        setAll(nums, false);
        while (cur <= maxNum) {
            updateNums(cur, nums);
            if (all(nums)) {
                return cur;
            }

            cur = cur + n;
        }

        return -1;
    }

    // the maximum number that will be checked for input n; this calculation is kind of arbitrary, and just considers basically that, if you have a number n (let's say 2), you will like to check at least the end of the next larger digit (because for 2, for example, 9 only appears at 90, so *100 is good enough in such cases, and is still quick to calculate until that)
    private static long maxNumFrom(long n) {
        return n*100;
    }

    // update nums, setting to true all the digits found in cur
    private static void updateNums(long cur, boolean[] nums) {
        while (cur > 0) {
            int thisNum = (int) (cur % 10);
            nums[thisNum] = true;
            cur /= 10;
        }
    }

    // returns true if all booleans are true; false otherwise
    private static boolean all(boolean[] nums) {
        for (boolean bool : nums) {
            if (!bool) return false;
        }
        return true;
    }

    // set all items in nums to b
    private static void setAll(boolean[] nums, boolean b) {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = b;
        }
    }

}
