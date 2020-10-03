package com.sorinbratosin.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class VatCalculator extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText VatRate,AmountWithoutVat,Vat,Total;
    private float vatRateFloat,amountWithoutVatFloat,vatFloat,totalFloat,convertedVatRate,vatRateConvertedForOperations;
    String vatRateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_calculator);
        initializeEditTexts();
    }

    private void initializeEditTexts() {
        VatRate = (EditText) findViewById(R.id.vatRateEditTextNumbers);
        AmountWithoutVat = (EditText) findViewById(R.id.amountWithoutVatEditTextNumbers);
        Vat = (EditText) findViewById(R.id.vatEditTextNumbers);
        Total = (EditText) findViewById(R.id.totalEditTextNumbers);
        AmountWithoutVat.setOnFocusChangeListener(this);
        Vat.setOnFocusChangeListener(this);
        Total.setOnFocusChangeListener(this);
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            setVatRate();
            switch (v.getId()) {
                case R.id.amountWithoutVatEditTextNumbers:
                    calculateUsingAmountWithoutVat();
                    break;
                case R.id.vatEditTextNumbers:
                    calculateUsingVat();
                    break;
                case R.id.totalEditTextNumbers:
                    calculateUsingTotal();
                    break;
            }
        }
    }

    private void calculateUsingAmountWithoutVat() {
        //calculate the VAT and Total
        String amountWithoutVatString = String.valueOf(AmountWithoutVat.getText());
        if(amountWithoutVatString.equals("")) {
            //do nothing
        } else {
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
            totalFloat = Float.parseFloat(String.valueOf(Total.getText()));
            amountWithoutVatFloat = totalFloat / vatRateConvertedForOperations;
            AmountWithoutVat.setText(String.valueOf(amountWithoutVatFloat));
            vatFloat = totalFloat - amountWithoutVatFloat;
            Vat.setText(String.valueOf(vatFloat));
        }
    }
}