package codejam.year2019.round1c;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class A_RobotProgrammingStrategy {

    private static Scanner in;
    private static PrintStream out;

    private static final String CASE_N = "Case #";
    private static final String COLON_SPACE = ": ";

    private static final char ROCK = 'R';
    private static final char PAPER = 'P';
    private static final char SCISSORS = 'S';

    private static final String IMPOSSIBLE = "IMPOSSIBLE";

    private static int A;
    private static final String[] C = new String[300];

    public static void main(String[] args) throws Throwable {
        in = new Scanner(System.in);
        in = new Scanner(new FileInputStream("./src/main/resources/codejam/year2019/round1c/A_RobotProgrammingStrategy/A_RobotProgrammingStrategy.in"));
        out = System.out;
        //out = new PrintStream(new FileOutputStream("A_RobotProgrammingStrategy.out"));

        int T = in.nextInt();
        for (int t = 1; t <= T; t++) {
            A = in.nextInt();
            for (int i = 0; i < A; i++) {
                C[i] = in.next();
            }

            out.print(CASE_N);
            out.print(t);
            out.print(COLON_SPACE);

            out.print(solve());

            out.println();
        }
        out.flush();
    }

    private static final List<String> remainingAdversaries = new ArrayList<>(300);

    private static String solve() {
        StringBuilder wip = new StringBuilder(500);

        remainingAdversaries.clear();
        for (int i = 0; i < A; i++) {
            remainingAdversaries.add(C[i]);
        }

        for (int round = 0; round < 500; round++) {
            Set<Character> nextPlays = nextAdversaryPlays(round);
            // not possible to win going against all possible plays
            if (nextPlays.size() == 3) {
                return IMPOSSIBLE;
            }

            // we go against only one play, we can win all
            if (nextPlays.size() == 1) {
                char play = nextPlays.iterator().next();
                wip.append(playToWin(play));
                remainingAdversaries.clear();
            }

            if (nextPlays.size() == 2) {
                // we go against two plays - we use the one we can tie and win, and eliminate the ones we defeat from the list
                char myPlay = canTieAndWin(nextPlays);
                wip.append(myPlay);
                Iterator<String> iterator = remainingAdversaries.iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    // if this guy chose the other play he loses
                    if (play(next, round) != myPlay) {
                        iterator.remove();
                    }
                }
            }

            if (remainingAdversaries.isEmpty()) {
                return wip.toString();
            }
        }

        // can't win in 500 plays
        return IMPOSSIBLE;
    }

    private static char canTieAndWin(Set<Character> nextPlays) {
        if (nextPlays.contains(ROCK) && nextPlays.contains(SCISSORS)) {
            return ROCK;
        }
        if (nextPlays.contains(PAPER) && nextPlays.contains(SCISSORS)) {
            return SCISSORS;
        }
        // if (nextPlays.contains(PAPER) && nextPlays.contains(ROCK)) {
        return PAPER;
        //}
    }

    private static char playToWin(char play) {
        switch (play) {
            case ROCK:
                return PAPER;
            case PAPER:
                return SCISSORS;
            case SCISSORS:
                return ROCK;
            default:
                throw new RuntimeException();

        }
    }

    private static Set<Character> nextAdversaryPlays(int round) {
        Set<Character> set = new HashSet<>();

        for (String alg : remainingAdversaries) {
            char play = alg.charAt(round % alg.length());
            set.add(play);
        }

        return set;
    }

    private static char play(String alg, int round) {
        return alg.charAt(round % alg.length());
    }

}
