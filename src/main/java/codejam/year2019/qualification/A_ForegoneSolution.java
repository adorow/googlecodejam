package codejam.year2019.qualification;

import java.io.PrintStream;
import java.util.Scanner;


public class A_ForegoneSolution {

    private static Scanner in;
    private static PrintStream out;

    public static void main(String[] args) throws Throwable {
        in = new Scanner(System.in);
        // in = new Scanner(new FileInputStream("./src/main/resources/codejam/year2019/qualification/A_RobotProgrammingStrategy/A_RobotProgrammingStrategy-small.in"));
        out = System.out;
        // out = new PrintStream(new FileOutputStream("./src/main/resources/codejam/year2019/qualification/A_RobotProgrammingStrategy/A_RobotProgrammingStrategy-small.out"));

        int T = in.nextInt();

        for (int t = 1; t <= T; t++) {
            out.print("Case #");
            out.print(t);
            out.print(": ");

            String amount = in.next();

            String fourToThree = turnFoursToThrees(amount);
            String leftover = getLeftoverWithOnes(amount);

            out.print(fourToThree);
            out.print(" ");
            out.print(leftover);

            out.println();
        }
        out.flush();
    }

    private static String getLeftoverWithOnes(String amount) {
        String onesAndZeros = amount
                .replaceAll("[^4]", "0")
                .replaceAll("4", "1");

        int firstOneIx = onesAndZeros.indexOf('1');

        if (firstOneIx < 0) {
            return "0";
        }

        return onesAndZeros.substring(firstOneIx);

    }

    private static String turnFoursToThrees(String amount) {
        return amount
                .replaceAll("4", "3");
    }

}
