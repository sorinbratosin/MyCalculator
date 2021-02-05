package com.sorinbratosin.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigDecimal;

public class PercentageSplitActivity extends AppCompatActivity {

    private EditText PercentEditText, AmountEditText;
    private TextView ResultEditText;
    private boolean editTextIsEmpty;
    private String percentString, ratePercentForOperation;

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
                try {
                    checkPercentValue();
                    if (PercentEditText.isFocused()) {
                        calculate();
                    }
                } catch (IllegalValueFormatException illegalValueFormatException) {
                    alertFirstNumIsZeroSecondIsNumber();
                } catch (InvalidValueException invalidValueException) {
                    alertValueOver100();
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
                try {
                    checkAmountValue();
                    if (AmountEditText.isFocused()) {
                        calculate();
                    }
                } catch (IllegalValueFormatException illegalValueFormatException) {
                    alertFirstNumIsZeroSecondIsNumber();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void percentRateConverted(String percent) {
        double percentDouble = Double.parseDouble(String.valueOf(PercentEditText.getText()));
        String[] percentSplited = percent.split("\\.");

        if (percentSplited.length == 1) {
            percentString = percentSplited[0];

        } else {
            percentString = percentSplited[0] + percentSplited[1];

        }
        if (percentDouble < 10) {
            ratePercentForOperation = "0.0" + percentString;

        } else if (percentDouble >= 10) {
            ratePercentForOperation = "0." + percentString;
        }
    }

    private void calculate() {
        checkIfEditTextsAreEmpty();
        if (!editTextIsEmpty) {
            percentRateConverted(String.valueOf(PercentEditText.getText()));
            BigDecimal amountBD = new BigDecimal(String.valueOf(AmountEditText.getText()));
            BigDecimal percentForOperationsBD = new BigDecimal(ratePercentForOperation);
            BigDecimal resultBD = amountBD.multiply(percentForOperationsBD);
            ResultEditText.setText(checkIfDecimalNeeded(resultBD));
        } else {
            ResultEditText.setText("");
        }
    }

    private void checkIfEditTextsAreEmpty() {
        String percentString = String.valueOf(PercentEditText.getText());
        String amountString = String.valueOf(AmountEditText.getText());
        if (percentString.equals("") || amountString.equals("")) {
            editTextIsEmpty = true;
        } else {
            editTextIsEmpty = false;
        }
    }

    private String checkIfDecimalNeeded(BigDecimal bd) {
        SetTwoDecimalsMax setTwoDecimalsMax = new SetTwoDecimalsMax(bd);
        return setTwoDecimalsMax.getFormattedNum();
    }

    //clears everything when the user enters a value of 100 or over
    private void alertValueOver100() {
        Toast toast = Toast.makeText(getApplicationContext(), "The value must be less than 100", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        PercentEditText.requestFocus();
        clear();
    }

    private void alertFirstNumIsZeroSecondIsNumber() {
        Toast toast = Toast.makeText(getApplicationContext(), "Illegal value format!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        PercentEditText.requestFocus();
        clear();
    }

    private void checkPercentValue() throws IllegalValueFormatException, InvalidValueException {
        CheckVatAndPercentageSplitValues checkPercentValue = new CheckVatAndPercentageSplitValues(editTextToString(PercentEditText),editTextToString(PercentEditText));
    }

    private void checkAmountValue() throws IllegalValueFormatException {
        CheckVatAndPercentageSplitValues checkAmountValue = new CheckVatAndPercentageSplitValues(editTextToString(AmountEditText));
    }

    private void clear() {
        String empty = "";
        PercentEditText.setText(empty);
        AmountEditText.setText(empty);
    }

    private String editTextToString (EditText editTextExtractString) {
        return String.valueOf(editTextExtractString.getText());
    }
}