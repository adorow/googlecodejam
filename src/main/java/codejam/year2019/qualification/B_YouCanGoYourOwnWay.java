package codejam.year2019.qualification;

import java.io.PrintStream;
import java.util.Scanner;


public class B_YouCanGoYourOwnWay {

    private static Scanner in;
    private static PrintStream out;

    public static void main(String[] args) throws Throwable {
        in = new Scanner(System.in);
        // in = new Scanner(new FileInputStream("./src/main/resources/codejam/year2019/qualification/A_RobotProgrammingStrategy/A_RobotProgrammingStrategy-small.in"));
        out = System.out;
        // out = new PrintStream(new FileOutputStream("./src/main/resources/codejam/year2019/qualification/A_RobotProgrammingStrategy/A_RobotProgrammingStrategy-small.out"));

        int T = in.nextInt();

        for (int t = 1; t <= T; t++) {
            int N = in.nextInt();
            String P = in.next(); // (2N -2 characters) of E (east) or S (south)

            out.print("Case #");
            out.print(t);
            out.print(": ");

            out.print(invertedPath(P));

            out.println();
        }
        out.flush();
    }

    private static String invertedPath(String P) {
        StringBuilder myPath = new StringBuilder(P.length());
        for (int i = 0; i < P.length(); i++) {
            char c = P.charAt(i);
            myPath.append((c == 'E' ? 'S' : 'E'));
        }

        return myPath.toString();
    }

}
