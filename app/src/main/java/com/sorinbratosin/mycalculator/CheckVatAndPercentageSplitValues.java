package com.sorinbratosin.mycalculator;

public class CheckVatAndPercentageSplitValues {

    CheckVatAndPercentageSplitValues(String checkIfValueIsOver100, String checkIfTheFirstTwoCharsAreNums) throws InvalidValueException, IllegalValueFormatException {
        if (!checkIfValueIsOver100.isEmpty() && !checkIfTheFirstTwoCharsAreNums.isEmpty()) {
            checkTheValue(checkIfValueIsOver100);
            checkIfFirstNumIsZeroAndSecondIsNotPoint(checkIfTheFirstTwoCharsAreNums);
        }
    }

    CheckVatAndPercentageSplitValues(String checkIfTheFirstTwoCharsAreNums) throws IllegalValueFormatException {
        if(!checkIfTheFirstTwoCharsAreNums.isEmpty()) {
            checkIfFirstNumIsZeroAndSecondIsNotPoint(checkIfTheFirstTwoCharsAreNums);
        }
    }

    private void checkTheValue(String stringToBeChecked) throws InvalidValueException {
        double valueConvertedToDouble = Double.parseDouble(stringToBeChecked);

        if (valueConvertedToDouble > 100) {
            throw new InvalidValueException("The value must be less than 100");
        }
    }

    private void checkIfFirstNumIsZeroAndSecondIsNotPoint(String stringToBeChecked) throws IllegalValueFormatException {
        if (stringToBeChecked.length() >= 2 && (stringToBeChecked.startsWith("0") && !String.valueOf(stringToBeChecked.charAt(1)).equals("."))) {
            throw new IllegalValueFormatException("Illegal value format!");
        }
    }
}
