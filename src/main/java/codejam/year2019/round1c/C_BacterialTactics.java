package codejam.year2019.round1c;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Scanner;


public class C_BacterialTactics {

    private static Scanner in;
    private static PrintStream out;

    private static final String CASE_N = "Case #";
    private static final String COLON_SPACE = ": ";

    private static int R;
    private static int C;

    private static int EMPTY = 0;
    private static int RADIOACTIVE = 1;
    private static int BACTERIA = 2;

    private static final int[][] BOARD = new int[20][20];

    public static void main(String[] args) throws Throwable {
        in = new Scanner(System.in);
        // in = new Scanner(new FileInputStream("./src/main/resources/codejam/year2019/round1c/C/C.in"));
        out = System.out;
        //out = new PrintStream(new FileOutputStream("A_RobotProgrammingStrategy.out"));

        int T = in.nextInt();

        for (int t = 1; t <= T; t++) {
            R = in.nextInt();
            C = in.nextInt();

            for (int i = 0; i < R; i++) {
                String line = in.next();
                for (int j = 0; j < line.length(); j++) {
                    BOARD[i][j] = pictToNum(line.charAt(j));
                }
            }

            out.print(CASE_N);
            out.print(t);
            out.print(COLON_SPACE);

            out.print(solve());

            out.println();
        }
        out.flush();
    }

    private static int solve() {
        int waysToWin = 0;

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                // may attempt
                if (BOARD[i][j] == EMPTY) {
                    BoardState state = BoardState.copyInitialBoard();
                    if (canWinPlayingVertical(state, i, j)) {
                        waysToWin++;
                    }
                    if (canWinPlayingHorizontal(state, i, j)) {
                        waysToWin++;
                    }
                }
            }
        }

        return waysToWin;
    }

    private static boolean canWinPlaying(BoardState state, int i, int j) {
        if (canWinPlayingHorizontal(state, i, j) || canWinPlayingVertical(state, i, j)) {
            return true;
        }

        return false;
    }

    private static boolean canWinPlayingHorizontal(BoardState state, int i, int j) {
        // play horizontal piece
        BoardState newState = state.copy();
        // play in the selected position
        newState.setBacteria(i, j);
        // spread left
        if (!newState.spreadBacteria(i, j, 0, -1)) {
            return false;
        }
        // spread right
        if (!newState.spreadBacteria(i, j, 0, +1)) {
            return false;
        }

        // we win if the adversary cannot
        if (!adversaryCanWin(newState)) {
            return true;
        }

        return false;
    }

    private static boolean canWinPlayingVertical(BoardState state, int i, int j) {
        // play vertical piece
        BoardState newState = state.copy();
        // play in the selected position
        newState.setBacteria(i, j);
        // spread up
        if (!newState.spreadBacteria(i, j, -1, 0)) {
            return false;
        }
        // spread down
        if (!newState.spreadBacteria(i, j, +1, 0)) {
            return false;
        }

        // we win if the adversary cannot
        if (!adversaryCanWin(newState)) {
            return true;
        }

        return false;
    }

    private static boolean isRadioactive(BoardState state, int i, int j) {
        return withinBounds(i, j) && state.get(i, j) == RADIOACTIVE;
    }

    private static boolean isBacteria(BoardState state, int i, int j) {
        return withinBounds(i, j) && state.get(i, j) == BACTERIA;
    }

    private static boolean withinBounds(int i, int j) {
        return i >= 0 && i < R && j >= 0 && j < C;
    }

    private static boolean adversaryCanWin(BoardState state) {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                // may attempt
                if (state.get(i, j) == EMPTY) {
                    if (canWinPlaying(state, i, j)) {
                        return true;
                    }
                }
            }
        }

        // no plays left to try
        return false;
    }

    private static int pictToNum(char charAt) {
        return charAt == '.' ? EMPTY : RADIOACTIVE;
    }

    static class BoardState {

        final int[][] state = new int[R][C];

        BoardState() {
        }

        static BoardState copyInitialBoard() {
            BoardState state = new BoardState();
            for (int i = 0; i < R; i++) {
                System.arraycopy(BOARD[i], 0, state.state[i], 0, C);
            }
            return state;
        }

        BoardState copy() {
            BoardState copy = new BoardState();
            for (int i = 0; i < R; i++) {
                System.arraycopy(state[i], 0, copy.state[i], 0, C);
            }
            return copy;
        }

        int get(int i, int j) {
            return state[i][j];
        }

        void setBacteria(int i, int j) {
            state[i][j] = BACTERIA;
        }

        public boolean spreadBacteria(int i, int j, int deltaI, int deltaJ) {
            while (withinBounds(i + deltaI, j + deltaJ)) {
                i += deltaI;
                j += deltaJ;
                if (isRadioactive(this, i, j)) {
                    return false;
                }
                if (isBacteria(this, i, j)) {
                    break; // stop spreading
                }
                setBacteria(i, j);
            }
            return true;
        }
    }

}
