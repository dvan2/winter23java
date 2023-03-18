package edu.pdx.cs410J.davvan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listofSums= findViewById(R.id.sums);
        Integer [] numbers = {1,2,3,4};
        ArrayAdapter<Integer> sums = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numbers);
        listofSums.setAdapter(sums);
    }



    public void launchCalculator(View view) {
        startActivity(new Intent(this, CalculatorActivity.class));
    }


}