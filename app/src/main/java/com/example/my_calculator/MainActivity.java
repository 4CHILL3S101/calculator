package com.example.my_calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button[] buttons = new Button[19];
    TextView inputDisplay, outputDisplay;
    int[] buttonIDs = {R.id.percent, R.id.zero, R.id.dot, R.id.one, R.id.two, R.id.three,
            R.id.four, R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine,
            R.id.add, R.id.minus, R.id.divide, R.id.multiply, R.id.total,
            R.id.delete, R.id.clearButton};

    StringBuilder inputData = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputDisplay = findViewById(R.id.inputView);
        outputDisplay = findViewById(R.id.textView);


        //i loop ang mga buttons tas i set ang onclick listeners
        for (int i = 0; i < buttonIDs.length; i++) {
            buttons[i] = findViewById(buttonIDs[i]);

            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String buttonText = ((Button) v).getText().toString();

                    // i check niya if unsa na actions ang gi click sa user
                    switch (buttonText) {

                        case "=": // tawgon ang external class or function tas i pasa ang inputted data para ma process
                            double answer = CalculateProcesses.evaluateExpression(inputData.toString());
                            displayResult(answer);
                            inputData.setLength(0);
                            inputData.append(answer % 1 == 0 ? (int) answer : answer);
                            break;

                        case "Del": // i remove niya ang last inputted character
                            removeLastCharacter();
                            break;

                        case "AC": // i set niya ang length sa inputted data into 0 para ma reset
                            clearAll();
                            break;

                        case "%": // i parse niya ang value into double
                            if (inputData.length() > 0) {
                                double value = Double.parseDouble(inputData.toString());
                                double percentageValue = CalculateProcesses.getPercentage(value);

                                inputData.setLength(0);
                                inputData.append(percentageValue % 1 == 0 ? (int) percentageValue : percentageValue);

                                outputDisplay.setText(inputData);
                                inputDisplay.setText(inputData);
                            }
                            break;

                        default: // mag append ug data
                            AppendInputData(buttonText);
                            break;
                    }
                }
            });
        }
    }



    //function para mag append ug data
    public void AppendInputData(String dataToAppend) {
        if (dataToAppend.equals(".") && (inputData.length() == 0 || lastNumberContainsDecimal())) {
            return;
        }
        inputData.append(dataToAppend);
        inputDisplay.setText(inputData);
    }


    // i check niya if ang last number is naay decimal point
    private boolean lastNumberContainsDecimal() {
        String[] parts = inputData.toString().split("[+\\-*/]");
        String lastPart = parts[parts.length - 1];
        return lastPart.contains(".");
    }

    // function para sa delete which is i delete niya ang last inputted character
    public void removeLastCharacter() {
        if (inputData.length() > 0) {
            inputData.deleteCharAt(inputData.length() - 1);
            inputDisplay.setText(inputData.length() > 0 ? inputData.toString() : "");
        }
    }

    // function para i reset niya ang inputData into 0 para ma reset ang stored value

    public void clearAll() {
        inputData.setLength(0);
        outputDisplay.setText("");
        inputDisplay.setText("");
    }

    //mag display sa answer

    public void displayResult(double result) {
        if (Double.isNaN(result)) {
            outputDisplay.setText("Error");
        } else if (result == (int) result) {
            inputDisplay.setText(String.valueOf((int) result));
            outputDisplay.setText(String.valueOf((int) result));
        } else {
            inputDisplay.setText(String.valueOf(result));
            outputDisplay.setText(String.valueOf(result));
        }
    }
}
