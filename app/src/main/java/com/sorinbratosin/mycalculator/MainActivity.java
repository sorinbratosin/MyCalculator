package com.sorinbratosin.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton standardCalculatorButton, vatCalculatorButton, percentageSplitImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startStandardCalculatorListener();
        startVatCalculatorListener();
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

}