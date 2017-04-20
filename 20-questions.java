import java.io.*;
import java.util.*;

public class Solution {
    static interface SubQuestion {
        public boolean isTrue(char[] colors);
    }
    
    static class Count implements SubQuestion {
        public int count;
        public char color;
        public Count(char color, int count) {
            this.color = color;
            this.count = count;
        }
        @Override
        public boolean isTrue(char[] colors) {
            int cCount = 0;
            for (char c : colors) {
                if (c == color)
                    ++cCount;
            }
            return cCount == count;
        }
    }
    
    static class Color implements SubQuestion {
        public int index;
        public char color;
        public Color(int index, char color) {
            this.index = index;
            this.color = color;
        }
        @Override
        public boolean isTrue(char[] colors) {
            return colors[index] == color;
        }
    }
    
    static enum Operator {none, and, or};
    static class Question {
        List<SubQuestion> subquestions = new ArrayList<>();
        Operator operator = Operator.none;
        boolean answer = true;
        public boolean isTrue(char[] colors) {
            boolean result = subquestions.get(0).isTrue(colors);
            for (int i = 1; i < subquestions.size(); ++i) {
                if (operator == Operator.and)
                    result &= subquestions.get(i).isTrue(colors);
                else if (operator == Operator.or)
                    result |= subquestions.get(i).isTrue(colors);
            }
            return answer ? result : !result;
        }
    }
    
    static int q, n;
    static List<Question> questions;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; ++i) {
            q = in.nextInt();
            n = in.nextInt();
            questions = new ArrayList<>();
            for (int j = 0; j < q; ++j) {
                Question question = new Question();
                in.nextLine();
                String questionString = in.nextLine();
                
                String[] subQ = new String[1];
                subQ[0] = questionString;
                if (questionString.contains("and")) {
                    question.operator = Operator.and;
                    subQ = questionString.split(" and ");
                } else if (questionString.contains("or")) {
                    question.operator = Operator.or;
                    subQ = questionString.split(" or ");
                }
                
                for (String s : subQ) {
                    String[] params = s.split(" ");
                    if (params[0].equals("count")) {
                        char color = params[1].charAt(0);
                        int count = Integer.parseInt(params[2]);
                        question.subquestions.add(new Count(color, count));
                    } else if (params[0].equals("color")) {
                        int index = Integer.parseInt(params[1]);
                        char color = params[2].charAt(0);
                        question.subquestions.add(new Color(index-1, color));
                    }
                }
                
                String ans = in.next();
                if (ans.equals("no"))
                    question.answer = false;
                else
                    question.answer = true;
                
                questions.add(question);
            }
            testCase();
        }
    }
    
    static char[] intToColor = new char[] {'r', 'g', 'b'};
    static void testCase() {
        int[] solution = new int[10];
        char[] colors = new char[10];
        findSolution(solution, colors, 0);
        for (int i = 0; i < 10; ++i) {
            int index = 0;
            while (solution[i] > 0) {
                if (solution[i] % 2 == 1) {
                    System.out.print(intToColor[index]);
                }
                solution[i] >>= 1;
                ++index;
            }
            System.out.print(" ");
        }
        System.out.println();
    }
    
    static void findSolution(int[] solution, char[] colors, int depth) {
        if (depth == 10) {
            int lieCount = 0;
            for (Question q : questions) {
                if (!q.isTrue(colors))
                    ++lieCount;
            }
            if (lieCount == n) {
                for (int i = 0; i < 10; ++i) {
                    int bit = -1;
                    if (colors[i] == 'r')
                        bit = 0;
                    if (colors[i] == 'g')
                        bit = 1;
                    if (colors[i] == 'b')
                        bit = 2;
                    solution[i] |= (1 << bit);
                }
            }
            return;
        }
        for (int i = 0; i < 3; ++i) {
            colors[depth] = intToColor[i];
            findSolution(solution, colors, depth+1);
        }
    }
}
