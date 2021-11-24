package com.sorinbratosin.mycalculator;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Splits a given string by operators "-","+","*","÷"(includes operators and numbers) and stores them in a List
//splitByOperatorsListUnsorted contains the String split by operators (including the operators)
//splitByOperatorsListSorted contains the string sorted by the SortNumbersAndOperatorsList class, that separates operators from numbers but includes "-" + "the number" in the same index of the list for negative numbers-


class Splitter {

    private List<String> splitByOperatorsListUnsorted = new ArrayList<>();
    private List<String> splitByOperatorsListSorted = new ArrayList<>();

    Splitter(String stringToSplit) {
        splitByOperators(stringToSplit);
    }

    void splitByOperators(String toBeSplit) {
        String[] splitArrString = toBeSplit.split("(?<=[-+*÷])|(?=[-+*÷])");
        splitByOperatorsListUnsorted.addAll(Arrays.asList(splitArrString));
        if (splitArrString[0].equals("")) {
            splitByOperatorsListUnsorted.remove(0);
        }
        //call the SortNumbersAndOperatorsList object method sort
        SortNumbersAndOperatorsList sortNumsAndOperators = new SortNumbersAndOperatorsList(splitByOperatorsListUnsorted);
        splitByOperatorsListSorted = sortNumsAndOperators.getSortedList();

    }

    List<String> getSplitByOperatorsListSorted() {
        return splitByOperatorsListSorted;
    }

    List<String> getSplitByOperatorsUnsortedList() { return splitByOperatorsListUnsorted;}


    String getSplitByOperatorsListUnsortedIndex(int index) {
        return splitByOperatorsListUnsorted.get(index);
    }

    int splitByOperatorsListUnsortedLength() {
        return splitByOperatorsListUnsorted.size();
    }

    String getSplitByOperatorsListSortedIndex(int index) {
        return splitByOperatorsListSorted.get(index);
    }

    int splitByOperatorsListSortedLength() {
        return splitByOperatorsListSorted.size();
    }

}
