package codejam.year2016.qualification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Revenge of the  (https://code.google.com/codejam/contest/6254486/dashboard#s=p1)
 *
 * Problem

 The Infinite House of Pancakes has just introduced a new kind of pancake! It has a happy face made of chocolate chips on one side (the "happy side"), and nothing on the other side (the "blank side").

 You are the head waiter on duty, and the kitchen has just given you a stack of pancakes to serve to a customer. Like any good pancake server, you have X-ray pancake vision, and you can see whether each pancake in the stack has the happy side up or the blank side up. You think the customer will be happiest if every pancake is happy side up when you serve them.

 You know the following maneuver: carefully lift up some number of pancakes (possibly all of them) from the top of the stack, flip that entire group over, and then put the group back down on top of any pancakes that you did not lift up. When flipping a group of pancakes, you flip the entire group in one motion; you do not individually flip each pancake. Formally: if we number the pancakes 1, 2, ..., N from top to bottom, you choose the top i pancakes to flip. Then, after the flip, the stack is i, i-1, ..., 2, 1, i+1, i+2, ..., N. Pancakes 1, 2, ..., i now have the opposite side up, whereas pancakes i+1, i+2, ..., N have the same side up that they had up before.

 For example, let's denote the happy side as + and the blank side as -. Suppose that the stack, starting from the top, is --+-. One valid way to execute the maneuver would be to pick up the top three, flip the entire group, and put them back down on the remaining fourth pancake (which would stay where it is and remain unchanged). The new state of the stack would then be -++-. The other valid ways would be to pick up and flip the top one, the top two, or all four. It would not be valid to choose and flip the middle two or the bottom one, for example; you can only take some number off the top.

 You will not serve the customer until every pancake is happy side up, but you don't want the pancakes to get cold, so you have to act fast! What is the smallest number of times you will need to execute the maneuver to get all the pancakes happy side up, if you make optimal choices?

 Input

 The first line of the input gives the number of test cases, T. T test cases follow. Each consists of one line with a string S, each character of which is either + (which represents a pancake that is initially happy side up) or - (which represents a pancake that is initially blank side up). The string, when read left to right, represents the stack when viewed from top to bottom.

 Output

 For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y is the minimum number of times you will need to execute the maneuver to get all the pancakes happy side up.

 Limits

 1 ≤ T ≤ 100.
 Every character in S is either + or -.

 Small dataset

 1 ≤ length of S ≤ 10.
 Large dataset

 1 ≤ length of S ≤ 100.
 Sample


 Input

 Output

 5
 -
 -+
 +-
 +++
 --+-

 Case #1: 1
 Case #2: 1
 Case #3: 2
 Case #4: 0
 Case #5: 3

 In Case #1, you only need to execute the maneuver once, flipping the first (and only) pancake.

 In Case #2, you only need to execute the maneuver once, flipping only the first pancake.

 In Case #3, you must execute the maneuver twice. One optimal solution is to flip only the first pancake, changing the stack to --, and then flip both pancakes, changing the stack to ++. Notice that you cannot just flip the bottom pancake individually to get a one-move solution; every time you execute the maneuver, you must select a stack starting from the top.

 In Case #4, all of the pancakes are already happy side up, so there is no need to do anything.

 In Case #5, one valid solution is to first flip the entire stack of pancakes to get +-++, then flip the top pancake to get --++, then finally flip the top two pancakes to get ++++.
 */
public class B {

    private static Scanner in;
    private static PrintStream out;

    private static final String CASE_N = "Case #";
    private static final String COLON_SPACE = ": ";

    static char HAPPY = '+';
    static char SAD = '-';

    public static void main(String[] args) throws Throwable {
        //in = new Scanner(System.in);
        in = new Scanner(new FileInputStream("B-large.in"));
        //out = System.out;
        out = new PrintStream(new FileOutputStream("B-large.out"));

        int T = in.nextInt();

        for (int t = 1; t <= T; t++) {
            out.print(CASE_N);
            out.print(t);
            out.print(COLON_SPACE);

            char[] S = in.next().toCharArray();

            int length = S.length;
            int flips = 0;

            while (needsFlipping(S, length)) {
                char firstCh = S[0];
                char lastCh = S[length-1];
                int sameInStart = countFirstEquals(S, length);
                int sameInEnd = sameInStart == length ? length : countLastEquals(S, length);
                if (lastCh == HAPPY) {
                    // [1] the last is happy, so we will ignore those from now, we don't touch this part anymore
                    length = length - sameInEnd;
                } else if (sameInStart == length) {
                    // [2] all the same, all will be SAD, so just flip and we will be finished
                    flipStack(S, length);
                    flips++;
                } else if (firstCh != lastCh) {
                    // [3] first and last are different (means last is SAD, because of [1], and therefore first part is HAPPY),so we flip so that all is accumulated as sad in the end, and can be flipped together later
                    flipStack(S, length - sameInEnd);
                    flips++;
                } else {
                    // if (firstCh == lastCh) {
                    // [4] first and last are the same, and SAD (because this did not pass [1], so last is SAD, and didn't meet [3], so end and start are SAD)
                    flipStack(S, length);
                    flips++;
                }

                //if (lastCh == SAD && firstCh == HAPPY) {
                //    // we do this because otherwise we have still the same case once we flip -> and we can ignore this last part, because it will be flipped once the new SAD part is flipped (and if this is just switching over and over then means all these parts accumulating will be flipped later on when the last one does
                //    length = length - sameInEnd;
                //}
            }

            out.println(flips);
        }

    }

    private static int countFirstEquals(char[] s, int length) {
        for (int i = 1; i < length; i++) {
            if (s[0] != s[i]) {
                return i;
            }
        }
        return length;
    }

    private static int countLastEquals(char[] s, int length) {
        for (int i = length-2; i >= 0; i--) {
            if (s[length-1] != s[i]) {
                return length - i - 1;
            }
        }
        return length;
    }

    private static void flipStack(char[] cs, int untilLength) {
        for (int i = 0; i < (untilLength+1)/2; i++) {
            int ia = i;
            int ib = untilLength - i - 1;

            char ca = cs[ia];
            char cb = cs[ib];

            cs[ia] = flip(cb);
            if (ia != ib) {
                cs[ib] = flip(ca);
            }
        }
    }

    private static char flip(char c) {
        return c == HAPPY ? SAD : HAPPY;
    }

    private static boolean needsFlipping(char[] cs, int length) {
        for (int i = 0; i < length; i++) {
            if (cs[i] == SAD) return true;
        }
        return false;
    }

    static class OppositeString implements CharSequence {

        private final int increment;
        private final int length;
        private String original;

        OppositeString(String original, int increment, int length) {
            this.original = original;
            this.increment = increment;
            this.length = length;
        }

        @Override
        public int length() {
            return length;
        }

        @Override
        public char charAt(int index) {
            return 0;
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return null;
        }
    }

}
