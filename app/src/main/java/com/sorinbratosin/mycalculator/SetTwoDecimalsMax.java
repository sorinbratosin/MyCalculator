package com.sorinbratosin.mycalculator;

//Sets the limit of a num to a max 2 decimals if they're not 0

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

class SetTwoDecimalsMax {

    private String formattedNum;

    SetTwoDecimalsMax(BigDecimal bigDecimal) {
        checkIfDecimalNeeded(bigDecimal);
    }

    private void checkIfDecimalNeeded(BigDecimal bd) {
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);
        formattedNum = df.format(bd);
    }

    String getFormattedNum() {
        return formattedNum;
    }
}
