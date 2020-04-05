package codejam.year2020.qualification;

import java.io.PrintStream;
import java.util.Scanner;


public class D_EsabAtad {

    private static Scanner in;
    private static PrintStream out;


    private static final String CASE_N = "Case #";
    private static final String COLON_SPACE = ": ";

    private static final char ZERO = '0';
    private static final char ONE = '1';
    private static final char UNKNOWN = '?';

    static int B;
    static char[] db = new char[101];
    static int q;

    public static void main(String[] args) throws Throwable {
        in = new Scanner(System.in);
//        in = new Scanner(new FileInputStream("./src/main/resources/codejam/year2020/qualification/D/D.in"));
        out = System.out;
        //out = new PrintStream(new FileOutputStream("A_RobotProgrammingStrategy.out"));

//        int T = in.nextInt();
//        B = in.nextInt();
        String[] s = in.nextLine().split(" ");
        int T = Integer.parseInt(s[0]);
        B = Integer.parseInt(s[1]);

        for (int t = 1; t <= T; t++) {
            // clear
            for (int i = 0; i <= B; i++) {
                db[i] = UNKNOWN;
            }
            // between 1 and B inclusive

            q = 0;
            int queriesMade = 0;
            for (; ; ) {
                if (q > 0 && q % 10 == 0) {
                    // figure out what happened
//                    25% of the time, the array is complemented: every 0 becomes a 1, and vice versa.
//                    25% of the time, the array is reversed: the first bit swaps with the last bit, the second bit swaps with the second-to-last bit, and so on.
//                    25% of the time, both of the things above (complementation and reversal) happen to the array. (Notice that the order in which they happen does not matter.)
//                    25% of the time, nothing happens to the array.

                    int iSame = -1;
                    int iDif = -1;
                    // find case where extremes are the same value:
                    for (int i = 0; i < queriesMade / 2; i++) {
                        int k = otherSide(i);
                        if (db[i] == db[k]) {
                            iSame = i;
                        } else {
                            iDif = i;
                        }
                    }

                    if (iSame > -1) {
                        char res = query(iSame);
                        if (res != db[iSame]) {
                            // all 'sames' should swap
                            for (int i = 0; i < queriesMade/2; i++) {
                                int k = otherSide(i);
                                if (db[i] == db[k]) {
                                    db[i] = swap(db[i]);
                                    db[k] = swap(db[k]);
                                }
                            }
                        }
                    } else {
                        query(1);// random query so we always have even number of elements
                    }

                    if (iDif > -1) {
                        char res = query(iDif);
                        if (res != db[iDif]) {
                            // all 'diffs' should swap
                            for (int i = 0; i < queriesMade/2; i++) {
                                int k = otherSide(i);
                                if (db[i] != db[k]) {
                                    db[i] = swap(db[i]);
                                    db[k] = swap(db[k]);
                                }
                            }
                        }
                    } else {
                        query(1);// random query so we always have even number of elements
                    }

                }
                int ix = (queriesMade / 2);
                if (queriesMade % 2 == 1) {
                    ix = otherSide(ix);
                }

                db[ix] = query(ix);

                queriesMade++;
                // if all are known
                if (queriesMade >= B) break;
                if (q >= 150) throw new RuntimeException("could not find solution in time");
            }

            // print the answer
            out.println(new String(db, 0, B));
            out.flush();

            String answer = in.next();
            // success?
            if ("N".equalsIgnoreCase(answer)) {
                return;
            }
            assert "Y".equalsIgnoreCase(answer);
        }

        out.flush();
    }

    private static char query(int ix) {
        ix++;
        // just read what is in that position
        out.println(ix);out.flush();
        char bit = in.next().charAt(0);

        q++;

        return bit;
    }


    private static char swap(char ch) {
        return ch == '0' ? '1' : '0';
    }

    private static int otherSide(int ix) {
        return (B - 1) - ix;
    }

}
