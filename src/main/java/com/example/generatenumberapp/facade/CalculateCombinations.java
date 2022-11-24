package com.example.generatenumberapp.facade;

import org.springframework.stereotype.Component;

@Component
public class CalculateCombinations {


    public static long howManyCombinations(long num) {

        if (num > 0) {
            return num * (howManyCombinations(num - 1));
        } else if (num == 0 || num == 1) {
            return 1;
        } else {
            throw new ArithmeticException("error");
        }
    }
}
