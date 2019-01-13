package com.learning.networking.javanio.server;

public class ExclusiveOR {
    public static void main(String[] args) {
        Boolean trueVal = true;

        Boolean falseVal = false;

        System.out.println(trueVal ^ falseVal);
        System.out.println(trueVal ^ trueVal);
        System.out.println(falseVal ^ falseVal);
        System.out.println(falseVal ^ trueVal);

        char c = 'K';
        char space = ' ';
        System.out.println((byte)c);
        System.out.println((byte)space);

    }
}
