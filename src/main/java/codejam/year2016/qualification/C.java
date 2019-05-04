package codejam.year2016.qualification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

/*
    the difference between this and the BruteForce solution is that this one checks against only the 10 thousand first prime numbers on the part of checking for dividers.
    the idea behind this is:
        + calculating against the 10 thousand first prime numbers is really quick, and covers a lot of ground
        + some of the numbers might have very few dividers, and might take very long to get there (especially when the numbers are 32 digits long)
        + because the numbers are 32 digits long, we have so many options to look for, that does not make sense to keep insisting in one that is taking us so long (one second, for instance) to calculate
 */

/**
 * Coin Jam (https://code.google.com/codejam/contest/6254486/dashboard#s=p2)
 *
 * Problem

 A_RobotProgrammingStrategy jamcoin is a string of N ≥ 2 digits with the following properties:

 Every digit is either 0 or 1.
 The first digit is 1 and the last digit is 1.
 If you interpret the string in any base between 2 and 10, inclusive, the resulting number is not prime.
 Not every string of 0s and 1s is a jamcoin. For example, 101 is not a jamcoin; its interpretation in base 2 is 5, which is prime. But the string 1001 is a jamcoin: in bases 2 through 10, its interpretation is 9, 28, 65, 126, 217, 344, 513, 730, and 1001, respectively, and none of those is prime.

 We hear that there may be communities that use jamcoins as a form of currency. When sending someone a jamcoin, it is polite to prove that the jamcoin is legitimate by including a nontrivial divisor of that jamcoin's interpretation in each base from 2 to 10. (A_RobotProgrammingStrategy nontrivial divisor for a positive integer K is some positive integer other than 1 or K that evenly divides K.) For convenience, these divisors must be expressed in base 10.

 For example, for the jamcoin 1001 mentioned above, a possible set of nontrivial divisors for the base 2 through 10 interpretations of the jamcoin would be: 3, 7, 5, 6, 31, 8, 27, 5, and 77, respectively.

 Can you produce J different jamcoins of length N, along with proof that they are legitimate?

 Input

 The first line of the input gives the number of test cases, T. T test cases follow; each consists of one line with two integers N and J.

 Output

 For each test case, output J+1 lines. The first line must consist of only Case #x:, where x is the test case number (starting from 1). Each of the last J lines must consist of a jamcoin of length N followed by nine integers. The i-th of those nine integers (counting starting from 1) must be a nontrivial divisor of the jamcoin when the jamcoin is interpreted in base i+1.

 All of these jamcoins must be different. You cannot submit the same jamcoin in two different lines, even if you use a different set of divisors each time.

 Limits

 T = 1. (There will be only one test case.)
 It is guaranteed that at least J distinct jamcoins of length N exist.

 Small dataset

 N = 16.
 J = 50.
 Large dataset

 N = 32.
 J = 500.
 Note that, unusually for a Code Jam problem, you already know the exact contents of each input file. For example, the Small dataset's input file will always be exactly these two lines:

 1
 16 50
 So, you can consider doing some computation before actually downloading an input file and starting the clock.

 Sample


 Input

 Output

 1
 6 3

 Case #1:
 100011 5 13 147 31 43 1121 73 77 629
 111111 21 26 105 1302 217 1032 513 13286 10101
 111001 3 88 5 1938 7 208 3 20 11

 In this sample case, we have used very small values of N and J for ease of explanation. Note that this sample case would not appear in either the Small or Large datasets.

 This is only one of multiple valid solutions. Other sets of jamcoins could have been used, and there are many other possible sets of nontrivial base 10 divisors. Some notes:

 110111 could not have been included in the output because, for example, it is 337 if interpreted in base 3 (1*243 + 1*81 + 0*27 + 1*9 + 1*3 + 1*1), and 337 is prime.
 010101 could not have been included in the output even though 10101 is a jamcoin, because jamcoins begin with 1.
 101010 could not have been included in the output, because jamcoins end with 1.
 110011 is another jamcoin that could have also been used in the output, but could not have been added to the end of this output, since the output must contain exactly J examples.
 For the first jamcoin in the sample output, the first number after 100011 could not have been either 1 or 35, because those are trivial divisors of 35 (100011 in base 2).
 */
public class C {

    private static Scanner in;
    private static PrintStream out;

    private static final String CASE_N = "Case #";
    private static final String COLON = ":";
    private static final String SPACE = " ";

    private static final int BASE_2 = 2;
    private static final int BASE_10 = 10;

    private static final int MAX_PRIMES_TO_CHECK = 10000;

    public static void main(String[] args) throws Throwable {
        long ini = System.currentTimeMillis();

        in = new Scanner(System.in);
        //in = new Scanner(new FileInputStream("C-test.in"));
//        in = new Scanner(new FileInputStream("C-small-attempt0.in"));
        in = new Scanner(new FileInputStream("C-large.in"));
        out = System.out;
        out = new PrintStream(new FileOutputStream("C-large.out"));

        int T = in.nextInt();

        BigInteger[] numbersPerBase = new BigInteger[11];
        BigInteger[] dividersPerBase = new BigInteger[11];
        BigInteger[] primeNumbers = calculateFirstPrimeNumbers(MAX_PRIMES_TO_CHECK);

        for (int t = 1; t <= T; t++) {
            out.print(CASE_N);
            out.print(t);
            out.println(COLON);

            int N = in.nextInt();
            int J = in.nextInt();

            int j = 0;
            char[] number = new char[N];
            number[0] = number[N - 1] = '1';
            for (int i = 1; i < N - 1; i++) {
                number[i] = '0';
            }
            while (j < J) {
                String numberStr = new String(number);
                BigInteger numBase10 = new BigInteger(numberStr);
                numbersPerBase[10] = numBase10;

                boolean foundPrime = isPrime(numBase10);
                for (int base = BASE_2; base < BASE_10 && !foundPrime; base++) {
                    BigInteger thisNumber = numbersPerBase[base] = numberAsBase(numberStr, base);

                    // todo: add logic to skip when we tried too many calculations

                    if (isPrime(thisNumber)) {
                        foundPrime = true;
                    }
                }
                if (!foundPrime) {
                    // found jamcoin!!
                    boolean skip = false;
                    for (int base = BASE_2; base <= BASE_10 && !skip; base++) {
                        BigInteger numberAtBase = numbersPerBase[base];
                        BigInteger half = numberAtBase.divide(BigInteger.valueOf(2));

                        BigInteger divider = primeNumbers[0];
                        int iPrime = 0;
                        while (divider.compareTo(half) < 0) {
                            if (isDivisible(numberAtBase, divider)) {
                                // we found the first possible divider
                                dividersPerBase[base] = divider;
                                break;
                            }
                            if (iPrime >= MAX_PRIMES_TO_CHECK-1) {
                                skip = true;
                                break;
                            }
                            divider = primeNumbers[++iPrime];
                        }
                    }
                    if (!skip) {
                        // found another jamcoin! (that we could compute) print it!
                        printSingleResult(dividersPerBase, numberStr);

                        j++;

                        System.out.print(j);
                        System.out.print(": ");
                        System.out.println(numberStr);
                    }
                }

                updateToNext(number);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Run took: " + (end-ini) + "ms");

        out.flush();
    }

    private static void printSingleResult(BigInteger[] dividersPerBase, String numberStr) {
        out.print(numberStr);
        out.print(SPACE);
        for (int base = BASE_2; base <= BASE_10; base++) {
            out.print(dividersPerBase[base]);
            if (base != BASE_10) {
                out.print(SPACE);
            }
        }
        out.println();
    }

    private static BigInteger[] calculateFirstPrimeNumbers(int amount) {
        BigInteger[] primes = new BigInteger[amount];
        primes[0] = BigInteger.valueOf(2);
        for (int i = 1; i < amount; i++) {
            primes[i] = primes[i-1].nextProbablePrime();
        }
        return primes;
    }

    private static boolean isDivisible(BigInteger numberAtBase, BigInteger divider) {
        BigInteger[] divisionAndRemainder = numberAtBase.divideAndRemainder(divider);
        return divisionAndRemainder[1].equals(BigInteger.ZERO);
    }

    private static void updateToNext(char[] number) {
        int i = number.length - 2;
        for (;;) {
            if (number[i] == '0') {
                number[i] = '1';
                return;
            } else {
                // number[i] == '1'
                number[i] = '0';
                i--;
                // and let continue, so the next digit is generated
            }
        }
    }

    static BigInteger numberAsBase(String number, int base) {
        return new BigInteger(number, base);
    }

    static boolean isPrime(BigInteger number) {
        return number.isProbablePrime(1000);
    }

}
