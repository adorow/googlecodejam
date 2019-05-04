package codejam;

import java.math.BigInteger;


public class TestLeastCommonDenominator {

    public static void main(String[] args) {

        // 265371653 * 79 = 20964360587
        // 265371653 * 53 = 14064697609

        BigInteger a = new BigInteger("20964360587");
        BigInteger b = new BigInteger("14064697609");

        System.out.println(a.gcd(b));
    }

}
