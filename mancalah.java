import java.io.*;
import java.util.*;

public class Solution {
    static List<Integer> board = new ArrayList<>();
    static long maxDepth = 0, maxHeight = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            board.add(in.nextInt());
        }
        computeDepth();
        computeHeight(board, 0L);
        System.out.println(maxDepth + " " + maxHeight);
    }
    
    static Map<List<Integer>, Long> depthMap = new HashMap<>();
    static void computeDepth() {
        long count = 1;
        depthMap.put(board, 0L);
        List<Integer> nextBoard = new ArrayList<>(board);
        while (true) {
            nextBoard = new ArrayList<>(nextBoard);
            for (int i = 1; i <= nextBoard.get(0); ++i) {
                if (i < nextBoard.size())
                    nextBoard.set(i, nextBoard.get(i) + 1);
                else
                    nextBoard.add(1);
            }
            nextBoard.remove(0);
            if (depthMap.containsKey(nextBoard)) {
                maxDepth = depthMap.get(nextBoard);
                return;
            }
            depthMap.put(nextBoard, count);
            ++count;
        }
    }
    
    static Set<List<Integer>> heightSet = new HashSet<>();
    static void computeHeight(List<Integer> board, long height) {
        if (heightSet.contains(board)) {
            if (height-1 > maxHeight)
                maxHeight = height-1;
            return;
        }
        heightSet.add(board);

        List<Integer> nextBoard = new ArrayList<>(board);
        nextBoard.add(0, 0);
        int index = 1;
        boolean end = true;
        outer:
        while (index < nextBoard.size()) {
            if (nextBoard.get(index) > 1) {
                nextBoard.set(index, nextBoard.get(index)-1);
                nextBoard.set(0, nextBoard.get(0)+1);
                end = false;
                computeHeight(new ArrayList<>(nextBoard), height+1);
                ++index;
            } else {
                for (int i = index; i < nextBoard.size(); ++i) {
                    if (nextBoard.get(i) != 1)
                        break outer;
                }
                nextBoard.set(0, nextBoard.get(0)+nextBoard.size()-index);
                nextBoard = nextBoard.subList(0, index);
                end = false;
                computeHeight(new ArrayList<>(nextBoard), height+1);
            }
        }
        if (end) {
            if (height > maxHeight)
                maxHeight = height;
        }
    }
}