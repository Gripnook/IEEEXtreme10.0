import java.io.*;
import java.util.*;

public class Solution {
    static Map<Character, Integer> mapping = new HashMap<>();
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int b = in.nextInt();
        String chars = in.nextLine();
        String add1 = in.nextLine();
        String add2 = in.nextLine();
        String bar = in.nextLine();
        
        String toAdd = add1.trim();
        String toAdd2 = add2.substring(1).trim();
        if (toAdd.length() > toAdd2.length()) {
            String temp = toAdd2;
            toAdd2 = toAdd;
            toAdd = temp;
        }
        
        int digits = toAdd2.length();
        
        char[] invMap = new char[b];
        for (int i = 0; i < b; ++i) {
            mapping.put(chars.charAt(i+1), i);
            invMap[i] = chars.charAt(i+1);
        }
        
        char[] tempResult = new char[digits];
        int carry = 0;
        for (int i = 0; i < toAdd.length(); ++i) {
            int tempSum = mapping.get(toAdd.charAt(toAdd.length()-1-i)) +
                mapping.get(toAdd2.charAt(toAdd2.length()-1-i)) + carry;
            tempResult[i] = invMap[tempSum % b];
            carry = tempSum / b;
        }
        for (int i = toAdd.length(); i < toAdd2.length(); ++i) {
            int tempSum = mapping.get(toAdd2.charAt(toAdd2.length()-1-i)) + carry;
            tempResult[i] = invMap[tempSum % b];
            carry = tempSum / b;
        }
        
        StringBuilder str = new StringBuilder();
        if (carry > 0)
            str.append(invMap[carry]);
        for (int i = 0; i < digits; ++i) {
            str.append(tempResult[digits-i-1]);
        }
        String result = str.toString();
        
        System.out.println(b + chars);
        System.out.printf("%" + (digits+1) + "s\n", add1);
        System.out.printf("%" + (digits+1) + "s\n", add2);
        System.out.println(bar);
        System.out.printf("%" + (digits+1) + "s\n", result);
    }
}
