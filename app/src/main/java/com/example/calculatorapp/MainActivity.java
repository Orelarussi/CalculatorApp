package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        setNumericButtonListeners();
        setOperatorListeners();
    }

    private void setNumericButtonListeners() {
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            if (isNewOperation) {
                currentInput = "";
                isNewOperation = false;
            }
            currentInput += b.getText().toString();
            tvResult.setText(currentInput);
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            currentInput = "";
            operator = "";
            firstNumber = 0;
            isNewOperation = true;
            tvResult.setText("0");
        });
    }

    private void setOperatorListeners() {
        findViewById(R.id.btnAdd).setOnClickListener(v -> handleOperator("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> handleOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> handleOperator("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> handleOperator("/"));

        findViewById(R.id.btnEquals).setOnClickListener(v -> calculateResult());
    }

    private void handleOperator(String op) {
        if (!currentInput.isEmpty()) {
            firstNumber = Double.parseDouble(currentInput);
            operator = op;
            isNewOperation = true;
        }
    }

    private void calculateResult() {
        if (operator.isEmpty() || currentInput.isEmpty()) return;

        double secondNumber = Double.parseDouble(currentInput);
        double result = 0;

        switch (operator) {
            case "+": result = firstNumber + secondNumber; break;
            case "-": result = firstNumber - secondNumber; break;
            case "*": result = firstNumber * secondNumber; break;
            case "/":
                if (secondNumber == 0) {
                    tvResult.setText("Cannot divide by zero");
                    return;
                }
                result = firstNumber / secondNumber;
                break;
        }

        tvResult.setText(String.valueOf(result));
        currentInput = String.valueOf(result);
        operator = "";
        isNewOperation = true;
    }
}
