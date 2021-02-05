package com.sorinbratosin.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VatCalculatorActivity extends AppCompatActivity {

    private EditText VatRate, AmountWithoutVat, Vat, Total;
    private double vatRateDouble, vatRateForAmountWithoutVatConvertedForOperations, vatRateForTotalConvertedForOperations;
    String vatRateString;
    boolean editTextsEmpty, vatRateEmpty;
    private BigDecimal amountWithoutVatBD, vatBD, totalBD;
    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

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

    //sets the rate for operations using Total to 1.0xx for VAT < 10 or 1.xxx for VAT >= 10
    //sets the rate for operations using AmountWithoutVat to 0.0xx for VAT < 10 or 0.xxx for VAT >= 10
    private void setVatRate() {
        vatRateDouble = Double.parseDouble(String.valueOf(VatRate.getText()));
        String[] vatRateWithoutDot = String.valueOf(VatRate.getText()).split("\\.");
        if (vatRateWithoutDot.length == 1) {
            vatRateString = vatRateWithoutDot[0];

        } else {
            vatRateString = vatRateWithoutDot[0] + vatRateWithoutDot[1];

        }
        if (vatRateDouble < 10) {
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

    //Listeners for All EditTexts that update as the user types using the one you're typing into to update the others
    private void editTextListeners() {
        AmountWithoutVat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    checkAmountWithoutVat();
                    if (AmountWithoutVat.isFocused()) {
                        calculateUsingAmountWithoutVat();
                    }
                } catch (IllegalValueFormatException illegalValueFormatException) {
                    alertFirstNumIsZeroSecondIsNumber();
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
                try {
                    checkVat();
                    if (Vat.isFocused()) {
                        calculateUsingVat();
                    }
                } catch (IllegalValueFormatException illegalValueFormatException) {
                    alertFirstNumIsZeroSecondIsNumber();
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
                try {
                    checkTotal();
                    if (Total.isFocused()) {
                        calculateUsingTotal();
                    }
                } catch (IllegalValueFormatException illegalValueFormatException) {
                    alertFirstNumIsZeroSecondIsNumber();
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
                try {
                    checkVatRate();
                    checkIfEditTextsAreEmpty();
                    if (!editTextsEmpty) {
                        calculateUsingAmountWithoutVat();
                    }
                } catch (IllegalValueFormatException illegalValueFormatException) {
                    alertFirstNumIsZeroSecondIsNumber();
                } catch (InvalidValueException invalidValueException) {
                    alertVatRateOver100();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //calculate the VAT and Total using AmountWithoutVat and VatRate
    private void calculateUsingAmountWithoutVat() {
        String amountWithoutVatString = String.valueOf(AmountWithoutVat.getText());
        VatRateIsEmpty();
        if (amountWithoutVatString.equals("")) {
            String empty = "";
            Vat.setText(empty);
            Total.setText(empty);
        } else if (!vatRateEmpty) {
            setVatRate();

            amountWithoutVatBD = new BigDecimal(String.valueOf(AmountWithoutVat.getText()));
            BigDecimal vatRateForAmountWithoutVatConvertedForOperationsBD = new BigDecimal(Double.toString(vatRateForAmountWithoutVatConvertedForOperations));
            vatBD = amountWithoutVatBD.multiply(vatRateForAmountWithoutVatConvertedForOperationsBD);
            Vat.setText(checkIfDecimalNeeded(vatBD));

            totalBD = amountWithoutVatBD.add(vatBD);
            Total.setText(checkIfDecimalNeeded(totalBD));

        } else {
            alertCompleteVatRate();
        }
    }

    // calculate AmountWithoutVat and Total using only the Vat and VatRate
    private void calculateUsingVat() {
        String vatString = String.valueOf(Vat.getText());
        VatRateIsEmpty();
        if (vatString.equals("")) {
            String empty = "";
            AmountWithoutVat.setText(empty);
            Total.setText(empty);
        } else if (!vatRateEmpty) {
            setVatRate();

            vatBD = new BigDecimal(String.valueOf(Vat.getText()));
            BigDecimal vatRateBD = new BigDecimal(String.valueOf(vatRateDouble));
            amountWithoutVatBD = ONE_HUNDRED.divide(vatRateBD, 2, RoundingMode.HALF_UP).multiply(vatBD);
            AmountWithoutVat.setText(checkIfDecimalNeeded(amountWithoutVatBD));

            totalBD = amountWithoutVatBD.add(vatBD);
            Total.setText(checkIfDecimalNeeded(totalBD));

        } else {
            alertCompleteVatRate();
        }
    }

    //calculate AmountWithoutVat and VAT using the Total and VatRate
    private void calculateUsingTotal() {
        String totalString = String.valueOf(Total.getText());
        VatRateIsEmpty();
        if (totalString.equals("")) {
            String empty = "";
            AmountWithoutVat.setText(empty);
            Vat.setText(empty);
        } else if (!vatRateEmpty) {
            setVatRate();

            totalBD = new BigDecimal(String.valueOf(Total.getText()));
            BigDecimal vatRateForTotalConvertedForOperationsBD = new BigDecimal(String.valueOf(vatRateForTotalConvertedForOperations));
            amountWithoutVatBD = totalBD.divide(vatRateForTotalConvertedForOperationsBD, 2, RoundingMode.HALF_UP);
            AmountWithoutVat.setText(checkIfDecimalNeeded(amountWithoutVatBD));

            vatBD = totalBD.subtract(amountWithoutVatBD);
            Vat.setText(checkIfDecimalNeeded(vatBD));

        } else {
            alertCompleteVatRate();
        }
    }

    //checks if EditTexts(except VatRate) are empty
    private void checkIfEditTextsAreEmpty() {
        String amountWithoutVatString = String.valueOf(AmountWithoutVat.getText());
        String vatString = String.valueOf(Vat.getText());
        String totalString = String.valueOf(Total.getText());
        if (amountWithoutVatString.equals("") || vatString.equals("") || totalString.equals("")) {
            editTextsEmpty = true;
        } else {
            editTextsEmpty = false;
        }
    }

    //sets the max decimals to 2 and if the last digit is 0 it removes it
    private String checkIfDecimalNeeded(BigDecimal bd) {
        SetTwoDecimalsMax setTwoDecimalsMax = new SetTwoDecimalsMax(bd);
        return setTwoDecimalsMax.getFormattedNum();
    }

    //check if VatRate is empty
    private void VatRateIsEmpty() {
        if (String.valueOf(VatRate.getText()).isEmpty()) {
            vatRateEmpty = true;
        } else {
            vatRateEmpty = false;
        }
    }

    //if VatRate is empty and in the other EditTexts the user types a number, then call this method that shows a toast, sets focus to VatRate and clears whatever the user typed
    private void alertCompleteVatRate() {
        Toast toast = Toast.makeText(getApplicationContext(), "Set a VAT rate first!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        VatRate.requestFocus();
        clear();
    }

    //clears the EditTexts, except VatRate
    private void clear() {
        String empty = "";
        AmountWithoutVat.setText(empty);
        Vat.setText(empty);
        Total.setText(empty);
    }

    //clears everything and shows a Toast when the user enters a VatRate of 100 or over
    private void alertVatRateOver100() {
        Toast toast = Toast.makeText(getApplicationContext(), "The value must be less than 100", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        VatRate.requestFocus();
        clear();
        VatRate.setText("");
    }

    //clears everything and shows a Toast when the user enters the first number 0 and the second another number
    private void alertFirstNumIsZeroSecondIsNumber() {
        Toast toast = Toast.makeText(getApplicationContext(), "Illegal value format!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        VatRate.requestFocus();
        clear();
        VatRate.setText("");
    }

    //checks if the value entered in VatRate contains two 0's consecutively or if the value exceeds 100
    private void checkVatRate() throws IllegalValueFormatException, InvalidValueException {
        CheckVatAndPercentageSplitValues checkVatRateValue = new CheckVatAndPercentageSplitValues(editTextToString(VatRate), editTextToString(VatRate));
    }

    private void checkAmountWithoutVat() throws IllegalValueFormatException {
        CheckVatAndPercentageSplitValues checkAmountValue = new CheckVatAndPercentageSplitValues(editTextToString(AmountWithoutVat));
    }

    private void checkVat() throws IllegalValueFormatException {
        CheckVatAndPercentageSplitValues checkVatValue = new CheckVatAndPercentageSplitValues(editTextToString(Vat));
    }

    private void checkTotal() throws IllegalValueFormatException {
        CheckVatAndPercentageSplitValues checkTotalValue = new CheckVatAndPercentageSplitValues(editTextToString(Total));
    }

    private String editTextToString (EditText editTextExtractString) {
        return String.valueOf(editTextExtractString.getText());
    }

}