package com.sorinbratosin.mycalculator;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Splits a given string by "." or by operators "-","+","*","÷"(includes operators and numbers) or by dot AND operators(including both) and stores them in a List
//splitByDotList contains the String split by dot (excluding dot)
//splitByOperatorsListUnsorted contains the String split by operators (including the operators)
//splitByOperatorsListSorted contains the string sorted by the SortNumbersAndOperatorsList class, that separates operators from numbers but includes "-" + "the number" in the same index of the list for negative numbers
//splitByDotAndOperatorsList contains the String split by operators and dot (including both)


class Splitter {

    private List<String> splitByDotList = new ArrayList<>();
    private List<String> splitByOperatorsListUnsorted = new ArrayList<>();
    private List<String> splitByOperatorsListSorted = new ArrayList<>();
    private List<String> splitByDotAndOperatorsList = new ArrayList<>();

    Splitter(String stringToSplit) {
        splitByOperators(stringToSplit);
        splitByDot(stringToSplit);
        splitByDotAndOperators(stringToSplit);
    }

    Splitter() {};

    void splitByDot(String toBeSplit) {
        String[] splitArrString = toBeSplit.split("\\.");
        splitByDotList.addAll(Arrays.asList(splitArrString));
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

    void splitByDotAndOperators(String toBeSplit) {
        String[] splitArrString = toBeSplit.split("(?<=[-+*÷.])|(?=[-+*÷.])");
        splitByDotAndOperatorsList.addAll(Arrays.asList(splitArrString));
        if (splitArrString[0].equals("")) {
            splitByDotAndOperatorsList.remove(0);
        }
    }

    String getSplitByDotListIndex(int index) {
        return splitByDotList.get(index);
    }

    int splitByDotListLength() {
        return splitByDotList.size();
    }

    List<String> getSplitByOperatorsListSorted() {
        return splitByOperatorsListSorted;
    }

    List<String> getSplitByDotAndOperatorsList() { return splitByDotAndOperatorsList;}

    List<String> getSplitByOperatorsUnsortedList() { return splitByOperatorsListUnsorted;}

    int splitByDotAndOperatorsLength() {
        return splitByDotAndOperatorsList.size();
    }

    String getSplitByOperatorsListUnsortedIndex(int index) {
        return splitByOperatorsListUnsorted.get(index);
    }

     void setSplitByOperatorsListUnsortedIndex(int index, String value) {
        splitByOperatorsListUnsorted.set(index, value);
    }

    String getSplitByOperatorsListUnsortedAsString() {
        return TextUtils.join("", splitByOperatorsListUnsorted);
    }

    int splitByOperatorsListUnsortedLength() {
        return splitByOperatorsListUnsorted.size();
    }

    String getSplitByOperatorsListSortedIndex(int index) {
        return splitByOperatorsListSorted.get(index);
    }

    void setSplitByOperatorsListSortedIndex(int index, String value) {
        splitByOperatorsListSorted.set(index, value);
    }

    void addSplitByOperatorsListSorted(String value) {
        splitByOperatorsListSorted.add(value);
    }


    int splitByOperatorsListSortedLength() {
        return splitByOperatorsListSorted.size();
    }

    void removeSplitByOperatorsListSortedIndex(int index) {
        splitByOperatorsListSorted.remove(index);
    }
}
