package com.sorinbratosin.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//display - stores the numbers pressed
//historyDisplay - after an operator is pressed the number from display and the operator pressed are stored in the historyDisplay
/*displayForCalculate - stores the first and second operand and the operator.(after the first cycle, the first operand is the last result and the second is the current number from display)
 This variable is passed to the calculate method and to StandardCalculator which calculates the result */
//result - the result returned by StandardCalculator after the calculation
//displayTextView - the TextView on which display variable is shown
//historyDisplayTextView - the TextView on which historyDisplay variable is shown
//dividedByZero - is true when divide by 0 happens (alertDividedByZero() method is called, the calculator resets to default and a Toast is shown)
//operatorAlreadyPressed - is true when the value is > 1 and the method replaceOperator() is called, replacing the last operator from historyDisplay with the one pressed
//dotAlreadyPressed - is true when a dot is already in the display variable, preventing multiple dot button presses (resets when an operator and the method checkOperatorPressed() is called)
/*operatorPressed - is true whenever an operator is pressed. Helps when a number is pressed after an operator is pressed so that the display will get replaced with the new number pressed (because the display still
shows the number from before pressing the operator */
//resultReturned - is true when StandardCalculator returns a result so that displayTextView can show it
//firstPress - is true by default so that display can replace the 0 with the number pressed (is set to false afterwards)
//firstCycle - is true by default so that at first displayForCalculate can get the operator, afterwards it only needs the second operand, as the first operand is the result and the operator gets set when displaying the result
//operatorPressedCounter - helps checkIfOperatorWasAlreadyPressed() method to check if the operator was pressed more than once consecutively so that it can set boolean operatorAlreadyPressed to true
//lastIndexOperator - is true if the last index of a list is an operator, and false otherwise
//calculatedWithEqual - is true after equal button is pressed and a calculation is performed


public class StandardCalculatorActivity extends AppCompatActivity {

    private HorizontalScrollView scrollView;
    private TextView displayTextView, historyDisplayTextView;
    private String display, historyDisplay, displayForCalculate, result;
    private boolean dividedByZero, operatorAlreadyPressed, operatorPressed, resultReturned, dotLastIndex, lastIndexOperator, calculatedWithEqual;
    private boolean firstPress = true;
    private boolean firstCycle = true;
    private int operatorPressedCounter;
    private static final String ADD = "+";
    private static final String SUBTRACT = "-";
    private static final String MULTIPLY = "*";
    private static final String DIVIDE = "÷";
    private static final String DOT = ".";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_calculator);
        onStartDisplayTextView();
    }

    public void onStartDisplayTextView() {
        displayTextView = (TextView) findViewById(R.id.displayTextView);
        historyDisplayTextView = (TextView) findViewById(R.id.historyDisplayTextView);
        scrollView = (HorizontalScrollView) findViewById(R.id.scroller);
        display = "0";
        historyDisplay = "";
        displayForCalculate = "";
        displayTextView.setText(display);
        historyDisplayTextView.setText(historyDisplay);
        historyDisplayTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void buttonListener(View view) {
        Button button = (Button) view;
        String data = button.getText().toString();

        switch (data) {
            case "C":
                clear();
                break;
            case "⌫":
                backspaceButtonAction();
                break;
            case "=":
                equalButtonAction();
                break;
            case "-/+":
                checkIfPositiveOrNegative();
                break;
            case ".":
                checkDot();
                break;
            default:
                //when pressing an operator first check if the operator was already pressed or not. If the operator wasn't pressed then the value from display and the operator can be assigned to the historyDisplay
                //next check if it's the first time when the operator was pressed
                //if the operator was already pressed, and the user pressed it again, then call the replaceOperator method which will replace the last operator from historyDisplay with the one pressed
                if (data.equals(ADD) || data.equals(SUBTRACT) || data.equals(MULTIPLY) || data.equals(DIVIDE)) {
                    checkOperatorPressed();
                    checkDisplayLastIndex();

                    if (dotLastIndex) {
                        operatorPressed = false;
                        dotLastIndex = false;
                    } else if (!operatorAlreadyPressed) {
                        historyDisplay += display + data;
                        if (firstCycle) {
                            displayForCalculate += display + data;
                            firstCycle = false;
                        } else {
                            displayForCalculate += display;
                        }
                        calculate();
                    } else if (operatorAlreadyPressed) {
                        replaceOperator(data);
                    }
                    //the end of operator press logic

                    //the start of number press
                    //at first press when the display shows 0, the display gets replaced by the number pressed, otherwise the display adds the number to the existing one
                    //after an operator was pressed(operatorPressed = true), display gets replaced by the number pressed
                } else if (display.equals("0") || firstPress) {
                    display = data;
                    operatorPressed = false;
                    operatorAlreadyPressed = false;
                    operatorPressedCounter = 0;
                    firstPress = false;
                    resultReturned = false;
                } else if (operatorPressed) {
                    display = data;
                    operatorPressed = false;
                    operatorAlreadyPressed = false;
                    operatorPressedCounter = 0;
                    resultReturned = false;
                } else if (calculatedWithEqual) {
                    display = data;
                    historyDisplay = "";
                    displayForCalculate = "";
                    result = "";
                    operatorPressed = false;
                    operatorPressedCounter = 0;
                    operatorAlreadyPressed = false;
                    firstCycle = true;
                    resultReturned = false;
                    calculatedWithEqual = false;
                } else {
                    display += data;
                    operatorAlreadyPressed = false;
                    operatorPressedCounter = 0;
                    resultReturned = false;
                }
        }
        //end of number press


        //start of setting the Text of the result, history and display(that shows the numbers) to the appropriate TextViews
        if (!resultReturned) {
            displayTextView.setText(display);
            historyDisplayTextView.setText(historyDisplay);
        } else if (resultReturned) {
            displayTextView.setText(result);
            historyDisplayTextView.setText(historyDisplay);
            display = "";
            checkDisplayForCalculateLastIndex(data);
        } else if (dividedByZero) {
            clear();
            dividedByZero = false;
        }
        autoScrollHistoryDisplayTextView();
    }
    //end of setting the Text

    private void equalButtonAction() {
        try {
            checkDisplayLastIndex();
            if (!dotLastIndex) {
                setAndCheckCalculateForEqual();
            }
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            alertCalculateWithASingleNumber();
        }
    }


    private void backspaceButtonAction() {
        if (!display.equals("0") && display.length() > 1) {
            checkBackspace();
        } else {
            display = "0";
            operatorAlreadyPressed = true;
        }
    }


    //create a StandardCalculator object that checks if displayForCalculate is ready to be calculated and returns the result if it's true. Wrapped in a try catch if divided by 0
    private void calculate() {
        try {
            StandardCalculator standardCalc = new StandardCalculator(displayForCalculate);
            if (standardCalc.getReadyToCalculate()) {
                displayForCalculate = standardCalc.getResult();
                result = displayForCalculate;
                resultReturned = true;
            }
        } catch (ArithmeticException e) {
            alertDividedByZero();
        }
    }

    //method that gets called when the +/- button is clicked. If the number from display is positive makes it negative and vice versa
    private void checkIfPositiveOrNegative() {
        if (!display.equals("0") && !calculatedWithEqual && !operatorPressed) {
            if (display.startsWith(SUBTRACT)) {
                display = display.substring(1);
            } else {
                display = SUBTRACT + display;
            }
        }
    }

    //show a Toast when you try to divide by zero
    private void alertDividedByZero() {
        dividedByZero = true;
        Context context = getApplicationContext();
        CharSequence text = getResources().getString(R.string.ToastDivideByZero);
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        clear();
    }


    //method called when an operator is pressed
    private void checkOperatorPressed() {
        operatorPressedCounter++;
        operatorPressed = true;
        if (operatorPressedCounter > 1) {
            operatorAlreadyPressed = true;
        }
    }

    private void checkBackspace() {
        if (!resultReturned) {
            display = display.substring(0, display.length() - 1);
        }
    }


    //check if the dot button was already pressed in display to restrict from displaying again if it already had
    private void checkDot() {
        if (!display.contains(DOT) && !resultReturned) {
            display += DOT;
        }
        firstPress = false;
    }

    private void checkDisplayLastIndex() {
        if (display.length() > 0 && display.endsWith(DOT)) {
            dotLastIndex = true;
        } else {
            dotLastIndex = false;
        }
    }

    private void checkDisplayForCalculateLastIndex(String data) {
        if (!data.equals("⌫") && !data.equals("=") && !data.equals(DOT) && !data.equals("-/+") && !displayForCalculate.endsWith(ADD) && !displayForCalculate.endsWith(SUBTRACT) && !displayForCalculate.endsWith(MULTIPLY) && !displayForCalculate.endsWith(DIVIDE)) {
            displayForCalculate += data;
        }
    }

    //method called when Equal button is pressed
    //Creates a List with the two operators and the operator which are going to be calculated by the calculateForEqual() method
    private void setAndCheckCalculateForEqual() {
        List<String> forCalc = new ArrayList<>();
        List<String> listWithoutLastOperator = new ArrayList<>();
        String stringWithoutLastOperator = "";
        if (!historyDisplay.equals("")) {
            Splitter splitter = new Splitter(historyDisplay);
            String operator = splitter.getSplitByOperatorsListUnsortedIndex(splitter.splitByOperatorsListUnsortedLength() - 1);
            if (operator.equals(ADD) || operator.equals(SUBTRACT) || operator.equals(MULTIPLY) || operator.equals(DIVIDE)) {
                listWithoutLastOperator = splitter.getSplitByOperatorsUnsortedList();
                listWithoutLastOperator.remove(listWithoutLastOperator.size() - 1);
                lastIndexOperator = true;
            }
            if (listWithoutLastOperator.size() != 0) {
                stringWithoutLastOperator = TextUtils.join("", listWithoutLastOperator);
            } else {
                stringWithoutLastOperator = historyDisplay;
                lastIndexOperator = false;
            }
            Splitter splitter1 = new Splitter(stringWithoutLastOperator);
            if (resultReturned && splitter1.splitByOperatorsListSortedLength() < 2) {
                forCalc.add(result);
                forCalc.add(operator);
                forCalc.add(splitter1.getSplitByOperatorsListSortedIndex(0));
            } else if (!resultReturned && splitter1.splitByOperatorsListSortedLength() < 2) {
                forCalc.add(splitter1.getSplitByOperatorsListSortedIndex(0));
                forCalc.add(operator);
                forCalc.add(display);
            } else if (resultReturned && splitter1.splitByOperatorsListSortedLength() > 2) {
                forCalc.add(result);
                if (lastIndexOperator) {
                    forCalc.add(operator);
                } else {
                    forCalc.add(splitter1.getSplitByOperatorsListSortedIndex(splitter1.splitByOperatorsListSortedLength() - 2));
                }
                forCalc.add(splitter1.getSplitByOperatorsListSortedIndex(splitter1.splitByOperatorsListSortedLength() - 1));
            } else if (!resultReturned && splitter1.splitByOperatorsListSortedLength() > 2) {
                forCalc.add(result);
                if (lastIndexOperator) {
                    forCalc.add(operator);
                } else {
                    forCalc.add(splitter1.getSplitByOperatorsListSortedIndex(splitter1.splitByOperatorsListSortedLength() - 2));
                }
                forCalc.add(display);
            }
        }
        calculateForEqual(forCalc);
    }

    //method called when setAndCheckCalculateForEqual() has the List ready to be calculated
    private void calculateForEqual(List<String> list) {
        displayForCalculate = TextUtils.join("", list);
        calculate();
        if (!dividedByZero) {
            historyDisplay = TextUtils.join("", list);
            list.set(0, result);
            operatorPressedCounter = 0;
        }
        calculatedWithEqual = true;
    }

    //method called when pressing the C button or when dividing by 0, resetting the calculator to the default state
    private void clear() {
        display = "0";
        historyDisplay = "";
        displayForCalculate = "";
        result = "";
        operatorPressedCounter = 0;
        firstPress = true;
        operatorAlreadyPressed = false;
        operatorPressed = false;
        resultReturned = false;
        firstCycle = true;
        historyDisplayTextView.setText(historyDisplay);
        displayTextView.setText(display);
    }

    //method called when pressing the operator more than one time, replacing the last operator from history display with the one pressed
    private void replaceOperator(String operator) {
        historyDisplay = historyDisplay.substring(0, historyDisplay.length() - 1) + operator;
        displayForCalculate = displayForCalculate.substring(0, displayForCalculate.length() - 1) + operator;
        operatorAlreadyPressed = false;
        operatorPressedCounter = 1;
    }

    private void alertCalculateWithASingleNumber() {
        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.ToastAlertCalculateWithASingleNum), Toast.LENGTH_LONG);
        //toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void autoScrollHistoryDisplayTextView() {
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });
    }
}