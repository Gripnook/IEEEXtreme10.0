import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; ++i) {
            int g = in.nextInt();
            long count = 0;
            for (int j = 0; j < g; ++j) {
                int n = in.nextInt();
                for (int k = 0; k < n; ++k) {
                    count += in.nextInt() - 1;
                }
            }
            if (count % 4 == 0) {
                System.out.println("Bob");
            } else {
                System.out.println("Alice");
            }
        }
    }
}
