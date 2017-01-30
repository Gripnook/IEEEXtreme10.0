import java.io.*;
import java.util.*;

public class Solution {
    static int p, s, n;
    static List<Integer> pages;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; ++i) {
            p = in.nextInt();
            s = in.nextInt();
            n = in.nextInt();
            pages = new ArrayList<>();
            for (int j = 0; j < n; ++j) {
                pages.add(in.nextInt()/s);
            }
            testCase();
        }
    }
    
    static void testCase() {
        Deque<Integer> pQue = new LinkedList<>();
        int pCount = 0;
        Queue<Integer> que = new LinkedList<>();
        int qCount = 0;
        for (int page : pages) {
            if (pQue.contains(page)) {
                pQue.remove(page);
                pQue.addFirst(page);
            } else  {
                if (pQue.size() == p) {
                    ++pCount;
                    pQue.removeLast();
                }
                pQue.addFirst(page);
            }
            
            if (que.contains(page)) {
                // nothing
            } else {
                if (que.size() == p) {
                    ++qCount;
                    que.remove();
                }
                que.add(page);
            }
        }
        System.out.println((pCount < qCount ? "yes" : "no") + " " + qCount + " " + pCount);
    }
}