import java.io.*;
import java.util.*;

public class Solution {
    static class Ellipse {
        public int x1, y1, x2, y2, r;
        public Ellipse(int x1, int y1, int x2, int y2, int r) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.r = r;
        }
    }
    static List<Ellipse> ellipses;
    
    static int n;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; ++i) {
            n = in.nextInt();
            ellipses = new ArrayList<>(n);
            for (int j = 0; j < n; ++j) {
                int x1 = in.nextInt();
                int y1 = in.nextInt();
                int x2 = in.nextInt();
                int y2 = in.nextInt();
                int r = in.nextInt();
                ellipses.add(new Ellipse(100*x1, 100*y1, 100*x2, 100*y2, 100*r));
            }
            compute();
        }
    }
    
    static void compute() {
        int cellCount = 0;
        for (int x = -4995; x <= 4995; x += 10) {
            outer: for (int y = -4995; y <= 4995; y += 10) {
                for (Ellipse e : ellipses) {
                    if (inEllipse(e, x, y)) {
                        ++cellCount;
                        continue outer;
                    }
                }
                
            }
        }
        double percent = 100.0*cellCount/(1000*1000);
        System.out.println(Math.round(100 - percent) + "%");
    }
    
    static boolean inEllipse(Ellipse e, int x, int y) {
        int term1 = x - e.x1;
        term1 = term1*term1;
        int term2 = y - e.y1;
        term2 = term2*term2;
        int term3 = x - e.x2;
        term3 = term3*term3;
        int term4 = y - e.y2;
        term4 = term4*term4;
        return Math.sqrt(term1 + term2) + Math.sqrt(term3 + term4) <= e.r;
    }
}