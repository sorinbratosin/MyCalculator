package com.sorinbratosin.mycalculator;

//Performs operations (add, subtract, multiply, divide) on a given String using BigDecimal for a more accurate result

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Calculate {

    private List<String> numbersAndOperator;
    private String num1, num2, operator, result;
    private BigDecimal num1BD, num2BD, resultBD;

    Calculate(List<String> list) {
        numbersAndOperator = list;
        calculate(list);
    }

    private void calculate(List<String> list) {
        setNumsAndOperator(list);
        setBigDecimalNums();

        switch (operator) {

            case "*":
                resultBD = num1BD.multiply(num2BD);
                break;

            case "÷":
                resultBD = num1BD.divide(num2BD, 2, RoundingMode.HALF_UP);
                break;

            case "+":
                resultBD = num1BD.add(num2BD);
                break;

            case "-":
                resultBD = num1BD.subtract(num2BD);
                break;
        }
        SetTwoDecimalsMax twoDecimalsMax = new SetTwoDecimalsMax(resultBD);
        result = twoDecimalsMax.getFormattedNum();
    }

    private void setNumsAndOperator(List<String> list) {
        num1 = list.get(0);
        num2 = list.get(2);
        operator = list.get(1);
    }

    private void setBigDecimalNums() {
        num1BD = new BigDecimal(num1);
        num2BD = new BigDecimal(num2);
    }

    String getResult() {
        return result;
    }
}

