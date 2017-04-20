import java.io.*;
import java.util.*;
import java.math.*;

public class Solution {
    static long N, A, B;
    static long MOD = 1000000007;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int Q = in.nextInt();
        for (int i = 0; i < Q; ++i) {
            N = in.nextLong();
            A = in.nextLong();
            B = in.nextLong();
            testCase();
        }
    }
    
    static void testCase() {
        List<Long> primes = getPrimes(N);
        
        BigInteger sum = BigInteger.valueOf(B-A+1).multiply(BigInteger.valueOf(B+A)).divide(BigInteger.valueOf(2));
        int numberOfCombinations = 1 << primes.size();
        for (int i = 1; i < numberOfCombinations; ++i) {
            long productOfPrimes = 1;
            int primeCount = 0;
            for (int j = 0; j < primes.size(); ++j) {
                if ((i >> j) % 2 == 1) {
                    ++primeCount;
                    productOfPrimes *= primes.get(j);
                }
            }
            
            if (primeCount % 2 == 1)
                sum = sum.subtract(sumOfMultiples(productOfPrimes));
            else
                sum = sum.add(sumOfMultiples(productOfPrimes));
        }
        
        System.out.println(sum.mod(BigInteger.valueOf(MOD)));
    }
    
    static List<Long> getPrimes(long N) {
        List<Long> primes = new ArrayList<>();
        
        if (N % 2 == 0) {
            primes.add(2L);
            N /= 2;
            while (N % 2 == 0)
                N /= 2;
        }
        
        long prime = 3;
        while (N > 1 && prime*prime <= N) {
            if (N % prime == 0) {
                primes.add(prime);
                N /= prime;
                while (N % prime == 0)
                    N /= prime;
            }
            prime += 2;
        }
        
        if (N > 1) {
            primes.add(N);
        }
        
        return primes;
    }
    
    static BigInteger sumOfMultiples(long base) {
        long P1 = ((A-1)/base + 1)*base;
        long P2 = (B/base)*base;
        long count = (P2-P1)/base + 1;
        if (count <= 0)
            return BigInteger.ZERO;
        return BigInteger.valueOf(count).multiply(BigInteger.valueOf(P1+P2)).divide(BigInteger.valueOf(2));
    }
}
