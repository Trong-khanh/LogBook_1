package com.example.maytinh;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText resultEditText;
    private String currentInput = "";
    private double firstNumber = 0;
    private char operator = ' ';
    private TextView operationTextView;
    private String currentExpression = "";
    private boolean cleared = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultEditText = findViewById(R.id.resultEditText);
        operationTextView = findViewById(R.id.operationTextView);
    }

    public void onNumberClick(View view) {
        String buttonText = ((Button) view).getText().toString();
        currentInput += buttonText;
        currentExpression += buttonText; // add number to operator
        updateResult();

        if (cleared && currentInput.isEmpty()) {
            // show message error
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Please enter the expression before calculating.")
                    .setPositiveButton("OK", null)
                    .show();
            cleared = false; // Reset cleared to false to allow new expressions to be entered
        }
    }


    public void onOperatorClick(View view) {
        if (!currentInput.isEmpty()) {
            firstNumber = Double.parseDouble(currentInput);
            operator = ((Button) view).getText().toString().charAt(0);

            // update with operator
            currentExpression += " " + operator + " ";
            operationTextView.setText(currentExpression);

            currentInput = "";
        }
    }

    public void onEqualsClick(View view) {
        if (currentInput.isEmpty() && currentExpression.isEmpty()) {
            // show message error
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Please enter the expression before calculating.")
                    .setPositiveButton("OK", null)
                    .show();
        } else if (!currentInput.isEmpty() && operator != ' ') {
            double secondNumber = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case '+':
                    result = firstNumber + secondNumber;
                    break;
                case '-':
                    result = firstNumber - secondNumber;
                    break;
                case '*':
                    result = firstNumber * secondNumber;
                    break;
                case '/':
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        // handler even / 0
                        currentInput = "Infinity";
                        updateResult();
                        return;
                    }
                    break;
            }

            // round the result
            long roundedResult = Math.round(result);
            currentInput = String.valueOf(roundedResult);

            // update result
            currentExpression += " = " + currentInput;
            operationTextView.setText(currentExpression);

            updateResult();
        }
    }


    public void onClearClick(View view) {
        currentInput = ""; // delete value
        firstNumber = 0; // set FirstNumber
        operator = ' '; // set operator

        // delete content TextView
        operationTextView.setText("");

        cleared = true; // deleted old result

        updateResult();
    }


    private void updateResult() {
        resultEditText.setText(currentInput);
    }
}
