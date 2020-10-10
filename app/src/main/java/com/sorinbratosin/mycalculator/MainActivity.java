package com.sorinbratosin.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton standardCalculatorButton, vatCalculatorButton, percentageSplitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startStandardCalculatorListener();
        startVatCalculatorListener();
        startPercentageSplitButtonListener();
    }

    private void startStandardCalculatorListener() {
        standardCalculatorButton = (ImageButton) findViewById(R.id.calculatorImageButton);
        standardCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStandardCalculator();
            }
        });
    }

    private void openStandardCalculator() {
        Intent standardCalcIntent = new Intent(this, StandardCalculator.class);
        startActivity(standardCalcIntent);
    }

    private void startVatCalculatorListener() {
        vatCalculatorButton = (ImageButton) findViewById(R.id.vatImageButton);
        vatCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVatCalculator();
            }
        });
    }

    private void openVatCalculator() {
        Intent vatCalcIntent = new Intent(this, VatCalculator.class);
        startActivity(vatCalcIntent);
    }

    private void startPercentageSplitButtonListener() {
        percentageSplitButton = (ImageButton) findViewById(R.id.percentageImageButton);
        percentageSplitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPercentageSplit();
            }
        });
    }

    private void openPercentageSplit() {
        Intent percentageSplitIntent = new Intent(this, PercentageSplit.class);
        startActivity(percentageSplitIntent);
    }
}