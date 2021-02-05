package com.sorinbratosin.mycalculator;

import java.util.List;

public class StandardCalculator extends Calculator {

    StandardCalculator(String input) {
        checkIfReadyToCalculate(input);
    }

    @Override
    void calculate(List<String> list) {
        Calculate calculate = new Calculate(list);
        result = calculate.getResult();
    }

    private void checkIfReadyToCalculate(String input) {
        Splitter splitter = new Splitter(input);
        numbersAndOperator = splitter.getSplitByOperatorsListSorted();

        if (readyToCalculate = numbersAndOperator.size() == 3) {
            calculate(numbersAndOperator);
        }
    }
}
