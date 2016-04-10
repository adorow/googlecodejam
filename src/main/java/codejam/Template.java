package codejam;

import java.io.PrintStream;
import java.util.Scanner;

public class Template {

    private static Scanner in;
    private static PrintStream out;

    private static final String CASE_N = "Case #";
    private static final String COLON_SPACE = ": ";

    public static void main(String[] args) throws Throwable {
        in = new Scanner(System.in);
        //in = new Scanner(new FileInputStream("Template.in"));
        out = System.out;
        //out = new PrintStream(new FileOutputStream("Template.out"));

        int T = in.nextInt();

        for (int t = 1; t <= T; t++) {
            out.print(CASE_N);
            out.print(t);
            out.print(COLON_SPACE);

            // todo

            out.println();
        }
        out.flush();
    }

}
