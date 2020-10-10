package com.sorinbratosin.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;

public class VatCalculator extends AppCompatActivity {

    private EditText VatRate,AmountWithoutVat,Vat,Total;
    private double vatRateDouble,amountWithoutVatDouble,vatDouble,totalDouble,vatRateForAmountWithoutVatConvertedForOperations,vatRateForTotalConvertedForOperations;
    String vatRateString, resultWithTwoDecimalsMax;
    boolean editTextsEmpty,vatRateEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_calculator);
        initializeEditTexts();
        editTextListeners();
    }

    private void initializeEditTexts() {
        VatRate = (EditText) findViewById(R.id.vatRateEditTextNumbers);
        AmountWithoutVat = (EditText) findViewById(R.id.amountWithoutVatEditTextNumbers);
        Vat = (EditText) findViewById(R.id.vatEditTextNumbers);
        Total = (EditText) findViewById(R.id.totalEditTextNumbers);
    }

    private void setVatRate() {
        //set the convertedVatRate 1.0xx for VAT <= 10 or 1.xxx for VAT > 10 =?without dot for future operations
            vatRateDouble = Double.parseDouble(String.valueOf(VatRate.getText()));
            String [] vatRateWithoutDot = String.valueOf(VatRate.getText()).split("\\.");
            if(vatRateWithoutDot.length == 1) {
                vatRateString = vatRateWithoutDot[0];

            } else {
                 vatRateString = vatRateWithoutDot[0] + vatRateWithoutDot[1];

            }
            if(vatRateDouble < 10) {
            String rateForAmountWithoutVat = "0.0" + vatRateString;
            vatRateForAmountWithoutVatConvertedForOperations = Double.parseDouble(rateForAmountWithoutVat);
            String rateForTotal = "1.0" + vatRateString;
            vatRateForTotalConvertedForOperations = Double.parseDouble(rateForTotal);


            } else if (vatRateDouble >= 10) {
                String rateForAmountWithoutVat = "0." + vatRateString;
                vatRateForAmountWithoutVatConvertedForOperations = Double.parseDouble(rateForAmountWithoutVat);
                String rateForTotal = "1." + vatRateString;
                vatRateForTotalConvertedForOperations = Double.parseDouble(rateForTotal);

            }
        }

        private void editTextListeners() {
        AmountWithoutVat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(AmountWithoutVat.isFocused()) {
                    calculateUsingAmountWithoutVat();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Vat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Vat.isFocused()) {
                    calculateUsingVat();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Total.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Total.isFocused()) {
                    calculateUsingTotal();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        VatRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkIfEditTextsAreEmpty();
            if(editTextsEmpty) {
                //do nothing
            } else {
                calculateUsingAmountWithoutVat();
            }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        }



    private void calculateUsingAmountWithoutVat() {
        //calculate the VAT and Total
        String amountWithoutVatString = String.valueOf(AmountWithoutVat.getText());
        VatRateIsEmpty();
        if(amountWithoutVatString.equals("")) {
            String empty = "";
            Vat.setText(empty);
            Total.setText(empty);
        } else if (!vatRateEmpty) {
            setVatRate();
            amountWithoutVatDouble = Double.parseDouble(String.valueOf(AmountWithoutVat.getText()));
            vatDouble = amountWithoutVatDouble * vatRateForAmountWithoutVatConvertedForOperations;
            checkIfDecimalNeeded(vatDouble);
            Vat.setText(resultWithTwoDecimalsMax);
            totalDouble = amountWithoutVatDouble + vatDouble;
            checkIfDecimalNeeded(totalDouble);
            Total.setText(resultWithTwoDecimalsMax);
        } else {
            alertCompleteVatRate();
        }
    }

    private void calculateUsingVat() {
        // calculate AmountWithoutVat and Total
        String vatString = String.valueOf(Vat.getText());
        VatRateIsEmpty();
        if(vatString.equals("")) {
            String empty = "";
            AmountWithoutVat.setText(empty);
            Total.setText(empty);
        } else if (!vatRateEmpty) {
            setVatRate();
            vatDouble = Double.parseDouble(String.valueOf(Vat.getText()));
            amountWithoutVatDouble = 100 / vatRateDouble * vatDouble;
            checkIfDecimalNeeded(amountWithoutVatDouble);
            AmountWithoutVat.setText(resultWithTwoDecimalsMax);
            totalDouble = amountWithoutVatDouble + vatDouble;
            checkIfDecimalNeeded(totalDouble);
            Total.setText(resultWithTwoDecimalsMax);
        } else  {
            alertCompleteVatRate();
        }
    }

    private void calculateUsingTotal() {
        //calculate AmountWithoutVat and VAT
        String totalString = String.valueOf(Total.getText());
        VatRateIsEmpty();
        if(totalString.equals("")) {
            String empty = "";
            AmountWithoutVat.setText(empty);
            Vat.setText(empty);
        } else if (!vatRateEmpty){
            setVatRate();
            totalDouble = Double.parseDouble(String.valueOf(Total.getText()));
            amountWithoutVatDouble = totalDouble / vatRateForTotalConvertedForOperations;
            checkIfDecimalNeeded(amountWithoutVatDouble);
            AmountWithoutVat.setText(resultWithTwoDecimalsMax);
            vatDouble = totalDouble - amountWithoutVatDouble;
            checkIfDecimalNeeded(vatDouble);
            Vat.setText(resultWithTwoDecimalsMax);
        } else {
            alertCompleteVatRate();
        }
    }

    private void checkIfEditTextsAreEmpty() {
        String amountWithoutVatString = String.valueOf(AmountWithoutVat.getText());
        String vatString = String.valueOf(Vat.getText());
        String totalString = String.valueOf(Total.getText());
        if(amountWithoutVatString.equals("") || vatString.equals("") || totalString.equals("")) {
            editTextsEmpty = true;
        } else {
            editTextsEmpty = false;
        }
    }

    private void checkIfDecimalNeeded(double toBeChecked) {
        String resultToString = Double.toString(toBeChecked);
        String[] resultToBeChecked = resultToString.split("(?<=\\.)|(?=\\.)");
        if (resultToBeChecked.length == 3) {
            if (resultToBeChecked[2].equals("0")) {
                int intResult = Integer.parseInt(resultToBeChecked[0]);
                resultWithTwoDecimalsMax = intResult + "";
            } else {
                char[] decimals = resultToBeChecked[2].toCharArray();
                if(decimals.length <= 2) {
                    resultWithTwoDecimalsMax = Double.toString(toBeChecked);
                } else {
                    resultWithTwoDecimalsMax = resultToBeChecked[0] + resultToBeChecked[1] + decimals[0] + decimals[1];
                }
            }
        }
    }

    private void VatRateIsEmpty() {
        if(String.valueOf(VatRate.getText()).equals("")) {
            vatRateEmpty = true;
        } else {
            vatRateEmpty = false;
        }
    }

    private void alertCompleteVatRate() {
        Toast toast = Toast.makeText(getApplicationContext(), "Set a VAT rate first!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        VatRate.requestFocus();
        clear();
    }

    private void clear() {
        String empty = "";
        AmountWithoutVat.setText(empty);
        Vat.setText(empty);
        Total.setText(empty);
    }
}