package com.sorinbratosin.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public TextView displayTextView,historyDisplayTextView;
    private Button c,backspace,plusminus,dot,equal,multiply,divide,plus,minus,zero,one,two,three,four,five,six,seven,eight,nine;

    private String input;
    private float result;
    private String historyDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onStartDisplayTextView();
    }

    public void onStartDisplayTextView() {
        displayTextView = (TextView) findViewById(R.id.displayTextView);
        historyDisplayTextView = (TextView) findViewById(R.id.historyDisplayTextView);
        input = "0";
        displayTextView.setText(input);
        initializeButtons();
    }

    public void buttonListener(View view) {
        Button button = (Button) view;
        String data = button.getText().toString();

        switch (data) {
            case "C":
                input = "0";
                historyDisplay = "";
                break;
            case "⌫":
                String theTextMinusOneChar = input.substring(0,input.length()-1);
                input = theTextMinusOneChar;
                break;
            case "=":
                calculate();
                break;
            case "-/+":
                checkIfPositiveOrNegative();
                break;
            default:
                if(input==null) {
                    input="";
                    historyDisplay = "";
                }
                if(data.equals("+") || data.equals("-") || data.equals("*") || data.equals("÷")) {
                    calculate();
                }
                if(input.equals("0")) {
                    input = data;
                    historyDisplay = data;
                } else {
                    input += data;
                    historyDisplay += data;
                }
        }
            displayTextView.setText(input);
            historyDisplayTextView.setText(historyDisplay);
    }

    private void calculate() {
        String[] stringCheck = input.split("(?<=[-+*÷])|(?=[-+*÷])");
        if (stringCheck.length == 3) {
            switch (stringCheck[1]) {
                case "*":
                    result = Float.parseFloat(stringCheck[0]) * Float.parseFloat(stringCheck[2]);
                    checkIfDecimalNeeded();
                    break;
                case "÷":
                    result = Float.parseFloat(stringCheck[0]) / Float.parseFloat(stringCheck[2]);
                    checkIfDecimalNeeded();
                    break;
                case "+":
                    result = Float.parseFloat(stringCheck[0]) + Float.parseFloat(stringCheck[2]);
                    checkIfDecimalNeeded();
                    break;
                case "-":
                    result = Float.parseFloat(stringCheck[0]) - Float.parseFloat(stringCheck[2]);
                    checkIfDecimalNeeded();
                    break;
            }
        }
        if (stringCheck.length == 4 && stringCheck[0].equals("-")) {
            switch (stringCheck[2]) {
                case "*":
                    result = -Float.parseFloat(stringCheck[1]) * Float.parseFloat(stringCheck[3]);
                    checkIfDecimalNeeded();
                    break;
                case "÷":
                    result = -Float.parseFloat(stringCheck[1]) / Float.parseFloat(stringCheck[3]);
                    checkIfDecimalNeeded();
                    break;
                case "+":
                    result = -Float.parseFloat(stringCheck[1]) + Float.parseFloat(stringCheck[3]);
                    checkIfDecimalNeeded();
                    break;
                case "-":
                    result = -Float.parseFloat(stringCheck[1]) - Float.parseFloat(stringCheck[3]);
                    checkIfDecimalNeeded();
                    break;
            }
        } else if (stringCheck.length == 4) {
            switch (stringCheck[1]) {
                case "*":
                    result = Float.parseFloat(stringCheck[0]) * -Float.parseFloat(stringCheck[3]);
                    checkIfDecimalNeeded();
                    break;
                case "÷":
                    result = Float.parseFloat(stringCheck[0]) / -Float.parseFloat(stringCheck[3]);
                    checkIfDecimalNeeded();
                    break;
                case "+":
                    result = Float.parseFloat(stringCheck[0]) + -Float.parseFloat(stringCheck[3]);
                    checkIfDecimalNeeded();
                    break;
                case "-":
                    result = Float.parseFloat(stringCheck[0]) - -Float.parseFloat(stringCheck[3]);
                    checkIfDecimalNeeded();
                    break;
            }
        } else if (stringCheck.length == 5) {
            switch (stringCheck[3]) {
                case "*":
                    result = -Float.parseFloat(stringCheck[1]) * -Float.parseFloat(stringCheck[4]);
                    checkIfDecimalNeeded();
                    break;
                case "÷":
                    result = -Float.parseFloat(stringCheck[1]) / -Float.parseFloat(stringCheck[4]);
                    checkIfDecimalNeeded();
                    break;
                case "+":
                    result = -Float.parseFloat(stringCheck[1]) + -Float.parseFloat(stringCheck[4]);
                    checkIfDecimalNeeded();
                    break;
                case "-":
                    result = -Float.parseFloat(stringCheck[1]) - -Float.parseFloat(stringCheck[4]);
                    checkIfDecimalNeeded();
                    break;
            }
        }
    }

    private void checkIfPositiveOrNegative() {
        String [] splitedString = input.split("(?<=[-+*/])|(?=[-+*/])");
        List<String> theList = new LinkedList<>(Arrays.asList(splitedString));
        if(splitedString.length == 1) {
            input = "-" + input;
        }
        else if(splitedString.length == 2) {
            input = splitedString[1];
        }
        else if(splitedString.length > 2) {
            String temporary = splitedString[splitedString.length-3];
            if(temporary.equals("*") || temporary.equals("/") || temporary.equals("+") || temporary.equals("-")) {
                theList.remove(theList.size()-2);
                //Textutils.join removes the [] , from the display
                input = TextUtils.join("",theList);
            }
            else {
                String theLastNum = "-" + theList.get(theList.size()-1);
                theList.remove(theList.size()-1);
                theList.add(theLastNum);
                //Textutils.join removes the [] , from the display
                input = TextUtils.join("",theList);
            }
        }
    }

    private void checkIfDecimalNeeded() {
            String resultToString = Float.toString(result);
            String[] resultToBeChecked = resultToString.split("(?<=\\.)|(?=\\.)");
            if (resultToBeChecked.length == 3) {
            if (resultToBeChecked[2].equals("0")) {
                int intResult = Integer.parseInt(resultToBeChecked[0]);
                input = intResult + "";
            } else {
                input = result + "";
            }
        }
    }
     public void initializeButtons() {
        c = (Button) findViewById(R.id.cButton);
        backspace = (Button) findViewById(R.id.backspaceButton);
        plusminus = (Button) findViewById(R.id.negativePositiveButton);
        dot = (Button) findViewById(R.id.dotButton);
        equal = (Button) findViewById(R.id.equalButton);
        multiply = (Button) findViewById(R.id.multiplyButton);
        divide = (Button) findViewById(R.id.divideButton);
        plus = (Button) findViewById(R.id.plusButton);
        minus = (Button) findViewById(R.id.minusButton);
        zero = (Button) findViewById(R.id.zeroButton);
        one = (Button) findViewById(R.id.oneButton);
        two = (Button) findViewById(R.id.twoButton);
        three = (Button) findViewById(R.id.threeButton);
        four = (Button) findViewById(R.id.fourButton);
        five = (Button) findViewById(R.id.fiveButton);
        six = (Button) findViewById(R.id.sixButton);
        seven = (Button) findViewById(R.id.sevenButton);
        eight = (Button) findViewById(R.id.eightButton);
        nine = (Button) findViewById(R.id.nineButton);
    }
}




