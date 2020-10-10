package com.sorinbratosin.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class PercentageSplit extends AppCompatActivity {

    private EditText PercentEditText,AmountEditText;
    private TextView ResultEditText;
    private boolean editTextIsEmpty;
    private String percentString,ratePercentForOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage_split);
        initializeEditTexts();
        editTextListeners();
    }

    private void initializeEditTexts() {
        PercentEditText = (EditText) findViewById(R.id.percentageEditTextNumbers);
        AmountEditText = (EditText) findViewById(R.id.amountEditTextNumbers);
        ResultEditText = (TextView) findViewById(R.id.resultEditTextNumbers);
    }

    private void editTextListeners() {
        PercentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(PercentEditText.isFocused()) {
                calculate();
            }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        AmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(AmountEditText.isFocused()) {
                calculate();
            }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void percentRateConverted (String percent) {
        Double percentDouble = Double.parseDouble(String.valueOf(PercentEditText.getText()));
        String[] percentSplited = percent.split("\\.");

        if(percentSplited.length == 1) {
            percentString = percentSplited[0];

        } else {
            percentString = percentSplited[0] + percentSplited[1];

        }
        if(percentDouble < 10) {
             ratePercentForOperation = "0.0" + percentString;

        } else if (percentDouble >= 10) {
             ratePercentForOperation = "0." + percentString;
        }
    }

    private void calculate() {
        checkIfEditTextsAreEmpty();
        if(!editTextIsEmpty) {
            percentRateConverted(String.valueOf(PercentEditText.getText()));
            BigDecimal amountBD = new BigDecimal(String.valueOf(AmountEditText.getText()));
            BigDecimal percentForOperationsBD = new BigDecimal(ratePercentForOperation);
            BigDecimal resultBD = amountBD.multiply(percentForOperationsBD);
            ResultEditText.setText(checkIfDecimalNeeded(resultBD));
        }
    }

    private void checkIfEditTextsAreEmpty() {
        String percentString = String.valueOf(PercentEditText.getText());
        String amountString = String.valueOf(AmountEditText.getText());
        if(percentString.equals("") || amountString.equals("")) {
            editTextIsEmpty = true;
        } else {
            editTextIsEmpty = false;
        }
    }

    private String checkIfDecimalNeeded(BigDecimal bd) {
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);
        return df.format(bd);
    }
}