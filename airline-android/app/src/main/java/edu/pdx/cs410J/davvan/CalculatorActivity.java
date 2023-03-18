package edu.pdx.cs410J.davvan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    public void returnToMain() {
        finish();
    }

    public void computeSum(View view) {
        EditText leftOperandEditText = findViewById(R.id.leftOperand);
        EditText rightOperandEditText = findViewById(R.id.rightOperand);

        String leftOperandString= leftOperandEditText.getText().toString();
        String rightOperandString = rightOperandEditText.getText().toString();

        int leftOperand;
        int rightOperand;
        try {
            leftOperand = Integer.parseInt(leftOperandString);
            rightOperand = Integer.parseInt(rightOperandString);
        }catch(NumberFormatException e){
            Toast.makeText(this, "Invalid number: ", Toast.LENGTH_SHORT).show();
            return;

        }

        int sum = leftOperand + rightOperand;

        TextView sumEditText = findViewById(R.id.sum);

        sumEditText.setText(String.valueOf(sum), TextView.BufferType.EDITABLE);
    }
}