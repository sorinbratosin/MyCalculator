package com.sorinbratosin.mycalculator;

// this sorts the List as follows: checks index 0 and 2 from the list and if the index from the list is "-" then it merges with the second index,thus creating a negative number.

import java.util.ArrayList;
import java.util.List;

public class SortNumbersAndOperatorsList {

    private List<String> list = new ArrayList<>();

    SortNumbersAndOperatorsList(List<String> list) {
        this.list.addAll(list);
        sort(this.list);
    }

    private void sort(List<String> list) {
        if(list.get(0).equals("-")) {
            mergeList(list,0);
        }
        if(list.size() > 2) {
            int i = list.size()-1;
            for (int x = 0; x < i; x++) {
                if ((list.get(x).equals("+") || list.get(x).equals("-") || list.get(x).equals("*") || list.get(x).equals("÷")) && list.get(x + 1).equals("-")) {
                    mergeList(list, x + 1);
                    --i;
                }
            }
        }
        /*switch (list.size()) {

            case 2:
                if (list.get(0).equals("-")) {
                    mergeList(list, 0);
                }
                break;
            case 3:
                if (list.get(0).equals("-")) {
                    mergeList(list, 0);
                }
                break;

            case 4:
                if (list.get(0).equals("-")) {
                    mergeList(list, 0);
                } else {
                    mergeList(list, 2);
                }
                break;

            case 5:
                mergeList(list, 0);
                mergeList(list, 2);
                break;
        } */
    }

    private void checkList(List<String> list) {
        if(list.get(0).equals("-")) {
            mergeList(list,0);
        }
        for(int x = 0;x < list.size(); x++) {
            if((list.get(x).equals("+") || list.get(x).equals("-") || list.get(x).equals("*") || list.get(x).equals("÷")) && list.get(x+1).equals("-")) {
                mergeList(list,x+1);
            }
        }
    }

    private void mergeList(List<String> list, int index) {
        String merge = list.get(index) + list.get(index + 1);
        list.set(index + 1, merge);
        list.remove(index);
    }

    List<String> getSortedList() {
        return list;
    }
}
