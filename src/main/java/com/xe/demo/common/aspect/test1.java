package com.xe.demo.common.aspect;

public class test1 {
    public static void main(String[] args) {

        test1 test1 = new test1();
        String palindrome = test1.longestPalindrome("babad");
        System.out.println(palindrome);

    }
    public String longestPalindrome(String s){
        char[] chars = s.toCharArray();
        String a ="";
        String c = "";
        for (int i = chars.length-1;i>=0;i--){
            a+=chars[i];
        }
        char[] chars1 = a.toCharArray();
        for (int i = 0;i<chars.length;i++){
            if (chars[i]==chars1[i]){
                c+=chars[i];
            }
        }
        return c;
    }
}
