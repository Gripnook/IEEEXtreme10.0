import java.io.*;
import java.util.*;

public class Solution {
    static final double[] AMAP = {
        -1.0,
        -1.0,
        1.880,
        1.023,
        0.729,
        0.577,
        0.483,
        0.419,
        0.373,
        0.337,
        0.308
    };
    
    static int x, n;
    static List<Integer> values;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; ++i) {
            x = in.nextInt();
            n = in.nextInt();
            values = new ArrayList<>(x);
            for (int j = 0; j < x; ++j) {
                values.add(in.nextInt());
            }
            evaluateControl();
        }
    }
    
    static void evaluateControl() {
        int fullGroups = x / n;
        double Xave = 0, Rave = 0;
        for (int i = 0; i < fullGroups; ++i) {
            int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
            double ave = 0;
            for (int j = 0; j < n; ++j) {
                int value = values.get(i*n + j);
                ave += value;
                if (value > max)
                    max = value;
                if (value < min)
                    min = value;
            }
            Rave += max-min;
            Xave += ave/n;
        }
        if (x % n != 0) {
            int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
            double ave = 0;
            for (int i = n*fullGroups; i < x; ++i) {
                int value = values.get(i);
                ave += value;
                if (value > max)
                    max = value;
                if (value < min)
                    min = value;
            }
            Rave += max-min;
            Xave += ave/(x%n);
            
            Rave /= fullGroups+1;
            Xave /= fullGroups+1;
        } else {
            Rave /= fullGroups;
            Xave /= fullGroups;
        }
        double UCL = Xave + AMAP[n]*Rave, LCL = Xave - AMAP[n]*Rave, CL = Xave;
        
        for (int value : values) {
            if (value > UCL || value < LCL) {
                System.out.println("Out of Control");
                return;
            }
        }
        
        double UCL2 = 2*UCL/3+CL/3;
        double LCL2 = 2*LCL/3+CL/3;
        for (int i = 0; i < x-2; ++i) {
            int val1 = values.get(i);
            int val2 = values.get(i+1);
            int val3 = values.get(i+2);
            int count = 0;
            if (val1 > UCL2)
                ++count;
            if (val2 > UCL2)
                ++count;
            if (val3 > UCL2)
                ++count;
            if (count > 1) {
                System.out.println("Out of Control");
                return;
            }
            count = 0;
            if (val1 < LCL2)
                ++count;
            if (val2 < LCL2)
                ++count;
            if (val3 < LCL2)
                ++count;
            if (count > 1) {
                System.out.println("Out of Control");
                return;
            }
        }
        
        double UCL1 = UCL/3+2*CL/3;
        double LCL1 = LCL/3+2*CL/3;
        for (int i = 0; i < x-4; ++i) {
            int val1 = values.get(i);
            int val2 = values.get(i+1);
            int val3 = values.get(i+2);
            int val4 = values.get(i+3);
            int val5 = values.get(i+4);
            int count = 0;
            if (val1 > UCL1)
                ++count;
            if (val2 > UCL1)
                ++count;
            if (val3 > UCL1)
                ++count;
            if (val4 > UCL1)
                ++count;
            if (val5 > UCL1)
                ++count;
            if (count > 3) {
                System.out.println("Out of Control");
                return;
            }
            count = 0;
            if (val1 < LCL1)
                ++count;
            if (val2 < LCL1)
                ++count;
            if (val3 < LCL1)
                ++count;
            if (val4 < LCL1)
                ++count;
            if (val5 < LCL1)
                ++count;
            if (count > 3) {
                System.out.println("Out of Control");
                return;
            }
        }
        
        for (int i = 0; i < x-7; ++i) {
            int val1 = values.get(i);
            int val2 = values.get(i+1);
            int val3 = values.get(i+2);
            int val4 = values.get(i+3);
            int val5 = values.get(i+4);
            int val6 = values.get(i+5);
            int val7 = values.get(i+6);
            int val8 = values.get(i+7);
            int count = 0;
            if (val1 > CL)
                ++count;
            if (val2 > CL)
                ++count;
            if (val3 > CL)
                ++count;
            if (val4 > CL)
                ++count;
            if (val5 > CL)
                ++count;
            if (val6 > CL)
                ++count;
            if (val7 > CL)
                ++count;
            if (val8 > CL)
                ++count;
            if (count == 8) {
                System.out.println("Out of Control");
                return;
            }
            count = 0;
            if (val1 < CL)
                ++count;
            if (val2 < CL)
                ++count;
            if (val3 < CL)
                ++count;
            if (val4 < CL)
                ++count;
            if (val5 < CL)
                ++count;
            if (val6 < CL)
                ++count;
            if (val7 < CL)
                ++count;
            if (val8 < CL)
                ++count;
            if (count == 8) {
                System.out.println("Out of Control");
                return;
            }
        }
        
        System.out.println("In Control");
    }
}
