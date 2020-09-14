package com.sorinbratosin.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView displayTextView,historyDisplayTextView;
    private String input;
    private float result;
    private String historyDisplay;
    private boolean firstPress;
    private boolean dividedByZero;
    private int NumOfOperatorsPressedConsecutively;

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
        historyDisplay = "0";
        displayTextView.setText(input);
        historyDisplayTextView.setText(historyDisplay);
    }

    public void buttonListener(View view) {
        Button button = (Button) view;
        String data = button.getText().toString();

        switch (data) {
            case "C":
                input = "0";
                historyDisplay = "0";
                firstPress = false;
                NumOfOperatorsPressedConsecutively = 0;
                break;
            case "⌫":
                if(!input.equals("0") && input.length() > 1) {
                    checkBackspace();
                    NumOfOperatorsPressedConsecutively = 0;
                } else {
                        input = "0";
                        historyDisplay = "0";
                        firstPress = false;
                        NumOfOperatorsPressedConsecutively = 0;
                    }
                break;
            case "=":
                calculate();
                break;
            case "-/+":
                checkIfPositiveOrNegative();
                break;
            case ".":
                checkDot(data);
                break;
            default:
                if(input==null) {
                    input = "";
                    historyDisplay = "";
                }
                if(data.equals("+") || data.equals("-") || data.equals("*") || data.equals("÷")) {
                    checkIfOperatorWasAlreadyPressed();
                    calculate();
                }
                if(input.equals("0") && !firstPress) {
                        input = data;
                        historyDisplay = data;
                } else if(input.equals("0")) {
                        input += data;
                        historyDisplay += data;
                } else if (NumOfOperatorsPressedConsecutively >= 1) {
                    input = input;
                    historyDisplay = historyDisplay;
                    NumOfOperatorsPressedConsecutively = 0;
                }
                else {
                    input += data;
                    historyDisplay += data;
                }
        }
            if(!dividedByZero) {
                displayTextView.setText(input);
                historyDisplayTextView.setText(historyDisplay);
            } else {
                input = "0";
                historyDisplay = "0";
                firstPress = false;
                displayTextView.setText(input);
                historyDisplayTextView.setText(historyDisplay);
                dividedByZero = false;
            }
    }

    private void calculate() {
        firstPress = true;
        String[] stringCheck = input.split("(?<=[-+*÷])|(?=[-+*÷])");
        List<String> stringCheckList = new LinkedList<>(Arrays.asList(stringCheck));
        if (stringCheck[0].equals("")) {
            stringCheckList.remove(0);
        }
            if (stringCheckList.size() == 3) {
                switch (stringCheckList.get(1)) {
                    case "*":
                        result = Float.parseFloat(stringCheckList.get(0)) * Float.parseFloat(stringCheckList.get(2));
                        checkIfDecimalNeeded();
                        break;
                    case "÷":
                        result = Float.parseFloat(stringCheckList.get(0)) / Float.parseFloat(stringCheckList.get(2));
                        checkIfDecimalNeeded();
                        checkDivideByZero();
                        break;
                    case "+":
                        result = Float.parseFloat(stringCheckList.get(0)) + Float.parseFloat(stringCheckList.get(2));
                        checkIfDecimalNeeded();
                        break;
                    case "-":
                        result = Float.parseFloat(stringCheckList.get(0)) - Float.parseFloat(stringCheckList.get(2));
                        checkIfDecimalNeeded();
                        break;
                }
            }
            if (stringCheckList.size() == 4 && stringCheckList.get(0).equals("-")) {
                switch (stringCheckList.get(2)) {
                    case "*":
                        result = -Float.parseFloat(stringCheckList.get(1)) * Float.parseFloat(stringCheckList.get(3));
                        checkIfDecimalNeeded();
                        break;
                    case "÷":
                        result = -Float.parseFloat(stringCheckList.get(1)) / Float.parseFloat(stringCheckList.get(3));
                        checkIfDecimalNeeded();
                        break;
                    case "+":
                        result = -Float.parseFloat(stringCheckList.get(1)) + Float.parseFloat(stringCheckList.get(3));
                        checkIfDecimalNeeded();
                        break;
                    case "-":
                        result = -Float.parseFloat(stringCheckList.get(1)) - Float.parseFloat(stringCheckList.get(3));
                        checkIfDecimalNeeded();
                        break;
                }
            } else if (stringCheckList.size() == 4) {
                switch (stringCheckList.get(1)) {
                    case "*":
                        result = Float.parseFloat(stringCheckList.get(0)) * -Float.parseFloat(stringCheckList.get(3));
                        checkIfDecimalNeeded();
                        break;
                    case "÷":
                        result = Float.parseFloat(stringCheckList.get(0)) / -Float.parseFloat(stringCheckList.get(3));
                        checkIfDecimalNeeded();
                        break;
                    case "+":
                        result = Float.parseFloat(stringCheckList.get(0)) + -Float.parseFloat(stringCheckList.get(3));
                        checkIfDecimalNeeded();
                        break;
                    case "-":
                        result = Float.parseFloat(stringCheckList.get(0)) - -Float.parseFloat(stringCheckList.get(3));
                        checkIfDecimalNeeded();
                        break;
                }
            } else if (stringCheckList.size() == 5) {
                switch (stringCheckList.get(2)) {
                    case "*":
                        result = -Float.parseFloat(stringCheckList.get(1)) * -Float.parseFloat(stringCheckList.get(4));
                        checkIfDecimalNeeded();
                        break;
                    case "÷":
                        result = -Float.parseFloat(stringCheckList.get(1)) / -Float.parseFloat(stringCheckList.get(4));
                        checkIfDecimalNeeded();
                        break;
                    case "+":
                        result = -Float.parseFloat(stringCheckList.get(1)) + -Float.parseFloat(stringCheckList.get(4));
                        checkIfDecimalNeeded();
                        break;
                    case "-":
                        result = -Float.parseFloat(stringCheckList.get(1)) - -Float.parseFloat(stringCheckList.get(4));
                        checkIfDecimalNeeded();
                        break;
                }
            }
    }

    private void checkIfPositiveOrNegative() {
        String [] splitedString = input.split("(?<=[-+*÷])|(?=[-+*÷])");
        String [] splitedHistory = historyDisplay.split("(?<=[-+*÷])|(?=[-+*÷])");
        String lastChar = splitedString[splitedString.length-1];
        List<String> theList = new LinkedList<>(Arrays.asList(splitedString));
        List<String> theHistoryList = new LinkedList<>(Arrays.asList(splitedHistory));

        if (splitedString[0].equals("")) {
            theList.remove(0);
            theHistoryList.remove(0);
        }
        if(theList.size() == 1 && !theList.get(0).equals("0")) {
            input = "-" + input;
            theHistoryList.remove(theHistoryList.get(theHistoryList.size()-1));
            theHistoryList.add(input);
            historyDisplay = TextUtils.join("",theHistoryList);
        }
        else if (lastChar.equals("*") || lastChar.equals("÷") || lastChar.equals("+") || lastChar.equals("-")) {
            input = input;
            historyDisplay = historyDisplay;
        }
        else if (theList.size() == 2 && theHistoryList.size() > 2) {
            theList.remove(theList.get(0));
            input = TextUtils.join("",theList);
            historyDisplay = input;
        }
        else if(theList.size() == 2) {
            theList.remove(theList.get(0));
            theHistoryList.remove(theHistoryList.size()-2);
            input = TextUtils.join("",theList);
            historyDisplay = TextUtils.join("",theHistoryList);
        }
        else if(theList.size() > 2) {
            String mainOperator = theList.get(theList.size()-3);
            if(mainOperator.equals("*") || mainOperator.equals("÷") || mainOperator.equals("+") || mainOperator.equals("-")) {
                theList.remove(theList.size()-2);
                theHistoryList.remove(theHistoryList.size()-2);
                //Textutils.join removes the [] , from the display
                input = TextUtils.join("",theList);
                historyDisplay = TextUtils.join("", theHistoryList);
            }
            else {
                String theLastNum = "-" + theList.get(theList.size()-1);
                theList.remove(theList.size()-1);
                theHistoryList.remove(theHistoryList.size()-1);
                theList.add(theLastNum);
                theHistoryList.add(theLastNum);
                //Textutils.join removes the [] , from the display
                input = TextUtils.join("",theList);
                historyDisplay = TextUtils.join("",theHistoryList);
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
                char[] decimals = resultToBeChecked[2].toCharArray();
                if(decimals.length <= 2) {
                    input = result + "";
                } else {
                    input = resultToBeChecked[0] + resultToBeChecked[1] + decimals[0] + decimals[1];
                }
            }
        }
    }

    private void checkDivideByZero() {
        char[] lasttTwoChars = input.toCharArray();
        if(lasttTwoChars.length > 2) {
        String first = Character.toString(lasttTwoChars[lasttTwoChars.length-2]);
        String second = Character.toString(lasttTwoChars[lasttTwoChars.length-1]);
        if(first.equals("÷") && second.equals("0")) {
            dividedByZero = true;
            Context context = getApplicationContext();
            CharSequence text = "You cannot divide by 0!!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        }
    }

    private void checkIfOperatorWasAlreadyPressed() {
        String [] splitedString = input.split("(?<=[-+*÷])|(?=[-+*÷])");
        String lastChar = splitedString[splitedString.length-1];
        if(lastChar.equals("*") || lastChar.equals("÷") || lastChar.equals("+") || lastChar.equals("-")) {
            NumOfOperatorsPressedConsecutively++;
        }
    }

    private void checkBackspace() {
        String [] splitedString = input.split("(?<=[-+*÷])|(?=[-+*÷])");
        String [] splitedHistory = historyDisplay.split("(?<=[-+*÷])|(?=[-+*÷])");

        if(splitedString[splitedString.length-1].equals(splitedHistory[splitedHistory.length-1])) {
            input = input.substring(0, input.length()-1);
            historyDisplay = historyDisplay.substring(0,historyDisplay.length()-1);
        }
        //if the last index from input corresponds with the last index from history then delete, if not, delete only from input until the result from all the operations then don't delete anymore
    }

    private void checkDot(String dot) {
        String [] splitedString = input.split("(?<=[-+*÷.])|(?=[-+*÷.])");
        String lastChar = splitedString[splitedString.length-1];

        if(lastChar.equals("*") || lastChar.equals("÷") || lastChar.equals("+") || lastChar.equals("-") || lastChar.equals(".")) {
            input = input;
            historyDisplay = historyDisplay;
        } else {
            if(splitedString.length > 1 && splitedString[splitedString.length-2].equals(dot)) {
                input = input;
                historyDisplay = historyDisplay;
            } else {
                input += dot;
                historyDisplay += dot;
            }
        }
    }
}