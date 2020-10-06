package com.sorinbratosin.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class VatCalculator extends AppCompatActivity {

    private EditText VatRate,AmountWithoutVat,Vat,Total;
    private float vatRateFloat,amountWithoutVatFloat,vatFloat,totalFloat,convertedVatRate,vatRateConvertedForOperations;
    String vatRateString;
    boolean editTextsEmpty;

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
            vatRateFloat = Float.parseFloat(String.valueOf(VatRate.getText()));
            String [] vatRateWithoutDot = String.valueOf(VatRate.getText()).split("\\.");
            if(vatRateWithoutDot.length == 1) {
                vatRateString = vatRateWithoutDot[0];
            } else {
                 vatRateString = vatRateWithoutDot[0] + vatRateWithoutDot[1];
            }
            if(vatRateFloat <= 10) {
            String rate = "1.0" + vatRateString;
            vatRateConvertedForOperations = Float.parseFloat(rate);
            } else if (vatRateFloat > 10) {
            String rate = "1." + vatRateString;
            vatRateConvertedForOperations = Float.parseFloat(rate);
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

      /*  VatRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkIfEditTextsAreEmpty();
            if(editTextsEmpty = true) {
                //do nothing
            } else {
                //calculateUsingAmountWithoutVat();
            }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        }); */
        }



    private void calculateUsingAmountWithoutVat() {
        //calculate the VAT and Total
        String amountWithoutVatString = String.valueOf(AmountWithoutVat.getText());
        if(amountWithoutVatString.equals("")) {
            //do nothing
        } else {
            setVatRate();
            amountWithoutVatFloat = Float.parseFloat(String.valueOf(AmountWithoutVat.getText()));
            vatFloat = amountWithoutVatFloat * vatRateConvertedForOperations;
            Vat.setText(String.valueOf(vatFloat));
            totalFloat = amountWithoutVatFloat + vatFloat;
            Total.setText(String.valueOf(totalFloat));
        }
    }

    private void calculateUsingVat() {
        // calculate AmountWithoutVat and Total
        String vatString = String.valueOf(Vat.getText());
        if(vatString.equals("")) {
            //do nothing
        } else {
            setVatRate();
            vatFloat = Float.parseFloat(String.valueOf(Vat.getText()));
            amountWithoutVatFloat = 100 / vatRateFloat * vatFloat;
            AmountWithoutVat.setText(String.valueOf(amountWithoutVatFloat));
            totalFloat = amountWithoutVatFloat + vatFloat;
            Total.setText(String.valueOf(totalFloat));
        }
    }

    private void calculateUsingTotal() {
        //calculate AmountWithoutVat and VAT
        String totalString = String.valueOf(Total.getText());
        if(totalString.equals("")) {
            //do nothing
        } else {
            setVatRate();
            totalFloat = Float.parseFloat(String.valueOf(Total.getText()));
            amountWithoutVatFloat = totalFloat / vatRateConvertedForOperations;
            AmountWithoutVat.setText(String.valueOf(amountWithoutVatFloat));
            vatFloat = totalFloat - amountWithoutVatFloat;
            Vat.setText(String.valueOf(vatFloat));
        }
    }

    private void checkIfEditTextsAreEmpty() {
        String amountWithoutVatString = AmountWithoutVat.getText().toString();
        String vatString = Vat.getText().toString();
        String totalString = Total.getText().toString();
        if(amountWithoutVatString.equals("") || vatString.equals("") || totalString.equals("")) {
            editTextsEmpty = true;
        } else {
            editTextsEmpty = false;
        }
    }
}