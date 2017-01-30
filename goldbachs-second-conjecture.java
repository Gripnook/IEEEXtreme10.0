import java.io.*;
import java.util.*;
import java.math.*;

public class Solution {
    static long p1 = -1, p2 = -1, p3 = -1;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long N = in.nextLong();
        
        for (long K = N-4; K >= 3; K -= 2) {
            if (isPrime(K)) {
                p1 = K;
                break;
            }
        }
        
        long N2 = N - p1;
        for (long K2 = N2-2; K2 >= 2; --K2) {
            if (isPrime(K2) && isPrime(N2-K2)) {
                p2 = K2;
                p3 = N2-K2;
                break;
            }
        }
        if (p1 < 0 || p2 < 0 || p3 < 0)
            System.out.println("counterexample");
        else
            System.out.println(p1 + " " + p2 + " " + p3);
    }
    
    static boolean isPrime(long N) {
        if (N > 2000) {
            return BigInteger.valueOf(N).isProbablePrime(63);
        }
        
        if (N == 1)
            return false;
        if (N == 2)
            return true;
        if (N % 2 == 0)
            return false;
        for (long i = 3; i*i <= N; ++i) {
            if (N % i == 0)
                return false;
        }
        return true;
    }
}