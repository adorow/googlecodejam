import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * https://codingcompetitions.withgoogle.com/codejam/round/000000000019fef4/00000000003172d1
 */
public class Solution {

    private static Scanner in;
    private static PrintStream out;


    private static final String CASE_N = "Case #";
    private static final String COLON_SPACE = ": ";

    public static void main(String[] args) throws Throwable {
        in = new Scanner(System.in);
        in = new Scanner(new FileInputStream("./src/main/resources/codejam/year2020/round1c/C/C.in"));
        out = System.out;
        //out = new PrintStream(new FileOutputStream("A_RobotProgrammingStrategy.out"));

        int T = in.nextInt();
        long[] A = new long[10001];

        for (int t = 1; t <= T; t++) {
            int N = in.nextInt();
            int D = in.nextInt();
            for (int i = 0; i < N; i++) {
                A[i] = in.nextLong();
            }

            out.print(CASE_N);
            out.print(t);
            out.print(COLON_SPACE);

            out.print(solve(A, N, D));

            out.println();
        }
        out.flush();
    }

    private static int solve(long[] A, int N, int D) {
        Arrays.sort(A, 0, N);

        if (hasSequence(A, N, D)) {
            return 0;
        }

        for (int i = 0; i < N; i++) {
            long sz = A[i];

            for (int j = 0; j < N; j++) {
                if (A[j] > sz) {
                    long d2 = A[j] - sz;
                    if (count(A, N, sz) + (d2 == sz ? 2 : 1) == D) {
                        return 1;
                    }
                }
            }
        }

        // worst case
        return D-1;
    }

    private static int count(long[] a, int n, long sz) {
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] == sz) cnt++;
        }
        return cnt;
    }

    private static long[] longestSequence(long[] a, int n) {
        long maxDist = 0;
        long maxV = 0;
        for (int i = 0; i < n;) {
            for (int j = i+1; j <= n; j++) {
                if (a[i] != a[j] || j==n) {
                    int dist = j-i;
                    if (dist > maxDist) {
                        maxDist = dist;
                        maxV = a[i];
                    }

                    i = j;
                    break;
                }
            }
        }

        return new long[] {maxV, maxDist};
    }

    private static boolean hasASliceDoubleTheSize(long[] a, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = n-1; j > i; j--) {
                if (a[i]*2 == a[j]) return true;
                if (a[i]*2 > a[j]) break;
            }
        }
        return false;
    }

    private static boolean hasSequenceOfNotLargest(long[] a, int n, int d) {
        for (int i = 0; i < n-d+1; i++) {
            if (a[i] == a[i+d-1] && a[i] != a[n-1]) return true;
        }
        return false;
    }

    private static boolean hasSequence(long[] a, int n, int d) {
        for (int i = 0; i < n-d+1; i++) {
            if (a[i] == a[i+d-1]) return true;
        }
        return false;
    }


}
