package edu.pdx.cs410J.davvan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView view = findViewById(R.id.textContent);

        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("README.txt")));
            String my_readme;
            while ((my_readme = reader.readLine()) != null) {
                System.out.println(my_readme);
                buffer.append(my_readme + "\n");
            }
        } catch (IOException e) {
            Toast.makeText(this, "Cannot find README.txt", Toast.LENGTH_LONG).show();
            return;
        }
        view.setText(buffer);
    }
}