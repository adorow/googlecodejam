package codejam.year2019.qualification;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;


public class C_Cryptopangrams {

    private static Scanner in;
    private static PrintStream out;

    public static void main(String[] args) throws Throwable {
        in = new Scanner(System.in);
        // in = new Scanner(new FileInputStream("./src/main/resources/codejam/year2019/qualification/C/C-small.in"));
        out = System.out;

        int T = in.nextInt();

        Map<BigInteger, Character> prime2char = new HashMap<>(30);
        PriorityQueue<BigInteger> heap = new PriorityQueue<>(30);
        BigInteger[] cypherValues = new BigInteger[130];
        BigInteger[] cypherPrimes = new BigInteger[130];
        for (int t = 1; t <= T; t++) {
            prime2char.clear();
            heap.clear();


            /*BigInteger N = */
            in.next(); // every prime is smaller than this number
            int L = in.nextInt();

            // BigInteger[] cypherValues = new BigInteger[L];
            for (int i = 0; i < L; i++) {
                cypherValues[i] = in.nextBigInteger();
            }

            // find first sequence of different values (because if there are repeated values the GCD will be that value, i.e. the multiplication of two primes - and we want the primes themselves):
            int iFirstDif;
            for (iFirstDif = 0; iFirstDif < L; iFirstDif++) {
                if (!cypherValues[iFirstDif].equals(cypherValues[iFirstDif + 1])) {
                    break;
                }
            }

            // 1. Decompose cypher values into a list of L+1 Prime numbers
            //    - put these primes into a separate list (they will be repeating)

            // on the first difference, we can define what is the prime in common there
            cypherPrimes[iFirstDif + 1] = cypherValues[iFirstDif + 1].gcd(cypherValues[iFirstDif + 0]);

            // for the ones before that match: divide the value by the prime after
            for (int i = iFirstDif; i >= 0 ; i--) {
                cypherPrimes[i] = cypherValues[i].divide(cypherPrimes[i + 1]);
            }
            // for the ones after that match: divide the value by the prime from before
            for (int i = iFirstDif + 2; i <= L ; i++) {
                cypherPrimes[i] = cypherValues[i - 1].divide(cypherPrimes[i - 1]);
            }

            // 2. With all primes, sort them and assign the smallest to A_RobotProgrammingStrategy, 2nd smallest to B, etc.. until highest value is assigned to Z
            for (int i = 0; i <= L; i++) {
                if (!heap.contains(cypherPrimes[i])) {
                    heap.add(cypherPrimes[i]);
                }
            }

            char ch = 'A'; // is assigned to the smallest prime
            while (!heap.isEmpty()) {
                BigInteger prime = heap.remove();
                prime2char.put(prime, ch);
                ch++; // inc char to be the next
            }

            // 3. Go through the list of L+1 Primes, map to the found alphabet, and write the resulting text

            out.print("Case #");
            out.print(t);
            out.print(": ");

            for (int i = 0; i <= L; i++) {
                BigInteger prime = cypherPrimes[i];
                Character printch = prime2char.get(prime);
                out.print(printch);
            }

            out.println();
        }
        out.flush();

    }

}
