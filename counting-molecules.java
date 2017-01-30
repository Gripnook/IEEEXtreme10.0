import java.io.*;
import java.util.*;

public class Solution {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long c = in.nextLong();
        long h = in.nextLong();
        long o = in.nextLong();
        long denom = 24;
        long numH2O = 6*h + 12*o - 24*c;
        long numCO2 = -6*h + 12*o;
        long numGlu = h - 2*o + 4*c;
        if (numH2O % denom != 0 || numCO2 % denom != 0 || numGlu % denom != 0
            || numH2O < 0 || numCO2 < 0 || numGlu < 0)
            System.out.println("Error");
        else
            System.out.println(numH2O / denom + " " + numCO2 / denom + " " + numGlu / denom);
    }
}