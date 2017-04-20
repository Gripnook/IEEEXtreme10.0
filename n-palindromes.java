import java.io.*;
import java.util.*;

public class Solution {
    static final long MOD = 1000000007;
    
    static int n;
    static String s;

    static long[][] h = new long[600][600];
    static long[][] g = new long[600][600];
    static long[][] fodd = new long[600][600];
    static long[][] feven = new long[600][600];
    
    static void precompute() {
        for (int i = 0; i < 600; ++i)
            h[i][0] = 1;
        for (int i = 0; i < 600; ++i)
            h[i][1] = 0;
        for (int j = 2; j < 600; ++j)
            h[0][j] = 0;
        for (int i = 1; i < 600; ++i) {
            for (int j = 2; j < 600; ++j) {
                h[i][j] = (25*h[i-1][j-2] + h[i-1][j]) % MOD;
            }
        }
        
        for (int i = 0; i < 600; ++i)
            g[i][0] = 1;
        for (int i = 0; i < 600; ++i)
            g[i][1] = (25*h[i][0]) % MOD;
        for (int j = 2; j < 600; ++j)
            g[0][j] = 0;
        for (int i = 1; i < 600; ++i) {
            for (int j = 2; j < 600; ++j) {
                g[i][j] = (25*h[i][j-1] + h[i][j]) % MOD;
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        precompute();
        for (int i = 0; i < t; ++i) {
            n = in.nextInt();
            s = in.next().toLowerCase();
            compute();
        }
    }
    
    static void compute() {
        int m = 0;
        for (int i = 0; i < s.length() / 2; ++i) {
            if (s.charAt(i) == s.charAt(s.length()-1-i))
                ++m;
        }
        int nonMatched = s.length() - 2*m;
        int nm = nonMatched/2;
        
        if (s.length() % 2 == 1) {
            computeFOdd(m);
            System.out.println(fodd[nm][n] % MOD);
        } else {
            computeFEven(m);
            System.out.println(feven[nm][n] % MOD);
        }
    }
    
    static void computeFEven(int m) {
        for (int i = 1; i < 600; ++i)
            feven[i][0] = 0;
        for (int j = 0; j < 600; ++j)
            feven[0][j] = h[m][j];
        for (int i = 1; i < 600; ++i)
            feven[i][1] = (2*feven[i-1][0]) % MOD;
        for (int i = 1; i < 600; ++i) {
            for (int j = 2; j < 600; ++j) {
                feven[i][j] = (2*feven[i-1][j-1] + 24*feven[i-1][j-2]) % MOD;
            }
        }
    }
    
    static void computeFOdd(int m) {
        for (int i = 1; i < 600; ++i)
            fodd[i][0] = 0;
        for (int j = 0; j < 600; ++j)
            fodd[0][j] = g[m][j];
        for (int i = 1; i < 600; ++i)
            fodd[i][1] = (2*fodd[i-1][0]) % MOD;
        for (int i = 1; i < 600; ++i) {
            for (int j = 2; j < 600; ++j) {
                fodd[i][j] = (2*fodd[i-1][j-1] + 24*fodd[i-1][j-2]) % MOD;
            }
        }
    }
}
