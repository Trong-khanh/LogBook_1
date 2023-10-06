package com.example.maytinh;

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
        updateResult();
    }

    public void onOperatorClick(View view) {
        if (!currentInput.isEmpty()) {
            firstNumber = Double.parseDouble(currentInput);
            operator = ((Button) view).getText().toString().charAt(0);

            // update textview with operator
            operationTextView.setText(currentInput + " " + operator);

            currentInput = "";
        }
    }

    public void onEqualsClick(View view) {
        if (!currentInput.isEmpty()) {
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
                        // handler with operator /  0
                        currentInput = "Infinity";
                        updateResult();
                        return;
                    }
                    break;
            }

            int integerResult = (int) result;

            if (!Double.isInfinite(result)) {
                currentInput = String.valueOf(result);
            }

            currentInput = String.valueOf(integerResult);

            updateResult();
        }
    }

    public void onClearClick(View view) {
        currentInput = ""; // delete value
        firstNumber = 0; // set FirstNumber
        operator = ' '; // set operator

        // delete content TextView
        operationTextView.setText("");

        updateResult();
    }

    private void updateResult() {
        resultEditText.setText(currentInput);
    }
}
