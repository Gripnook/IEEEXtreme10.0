import java.io.*;
import java.util.*;

public class Solution {
    static int n, k;
    static int[] dogs;
    static int[] diff;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; ++i) {
            n = in.nextInt();
            k = in.nextInt();
            dogs = new int[n];
            for (int j = 0; j < n; ++j) {
                dogs[j] = in.nextInt();
            }
            Arrays.sort(dogs);
            testCase();
        }
    }
    
    static void testCase() {
        diff = new int[n-1];
        for (int i = 0; i < n-1; ++i) {
            diff[i] = dogs[i+1]-dogs[i];
        }
        System.out.println(f());
    }
    
    static int f() {
        Arrays.sort(diff);
        int sum = dogs[n-1]-dogs[0];
        for (int i = n-2; i >= n-k; --i) {
            sum -= diff[i];
        }
        return sum;
    }
}