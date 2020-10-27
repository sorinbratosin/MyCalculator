package com.sorinbratosin.mycalculator;

import java.util.List;

public abstract class Calculator {

    String firstOperand,secondOperand,operator,result;

    abstract void calculate(List<String> list);

     String getResult() {
         return result;
     }
}
