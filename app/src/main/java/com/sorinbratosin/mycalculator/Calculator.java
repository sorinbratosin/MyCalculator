package com.sorinbratosin.mycalculator;

import java.util.ArrayList;
import java.util.List;

public abstract class Calculator {
    String result;
    List<String> numbersAndOperator = new ArrayList<>();
    boolean readyToCalculate;

    abstract void calculate(List<String> list);

     String getResult() {
         return result;
     }

     boolean getReadyToCalculate() {
         return readyToCalculate;
     }
}
