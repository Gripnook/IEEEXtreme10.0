import java.io.*;
import java.util.*;
import java.math.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; ++i) {
            BigInteger n = in.nextBigInteger();
            int bitLength = n.bitLength();
            System.out.println(n.clearBit(bitLength-1).shiftLeft(1).add(BigInteger.ONE));
        }
    }
}
