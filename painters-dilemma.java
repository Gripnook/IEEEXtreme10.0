import java.io.*;
import java.util.*;

public class Solution {
    static int N;
    static List<Integer> colors;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; ++i) {
            N = in.nextInt();
            colors = new ArrayList<>();
            for (int j = 0; j < N; ++j) {
                colors.add(in.nextInt());
            }
            testCase();
        }
    }
    
    static void testCase() {
        int[] colorCount = new int[21];
        int color = 0;
        for (int c : colors) {
            if (c == color)
                continue;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < 21; ++i) {
                if (colorCount[i] != 0 && i != color && i != c) {
                    if (colorCount[i] < min)
                        min = colorCount[i];
                    ++colorCount[i];
                }
            }
            if (colorCount[c] != 0) {
                colorCount[color] = Math.min(colorCount[c], Math.max(min+1, 1));
            } else  {
                colorCount[color] = Math.max(min+1, 1);
            }
            colorCount[c] = 0;
            color = c;
        }
        
        int min = Integer.MAX_VALUE;
        for (int count : colorCount) {
            if (count != 0 && count < min)
                min = count;
        }
        
        if (min == Integer.MAX_VALUE)
            min = 0;
        
        System.out.println(min);
    }
}