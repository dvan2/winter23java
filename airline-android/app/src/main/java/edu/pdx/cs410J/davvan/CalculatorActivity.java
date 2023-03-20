package edu.pdx.cs410J.davvan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CalculatorActivity extends AppCompatActivity {

    static final String SUM_VALUE = "SUM";
    private Integer sum;
    private Airline airline;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

    }


   public void addAirline(View view) {
       EditText name_edit_text = findViewById(R.id.airline_name);
       EditText flight_edit_text = findViewById(R.id.flight_num);
       EditText src_et = findViewById(R.id.src);
       EditText depart_et = findViewById(R.id.depart);
       EditText dest_et = findViewById(R.id.dest);
       EditText arrive_et = findViewById(R.id.arrive);

       String airline_name = name_edit_text.getText().toString();
       String flight_num_string = flight_edit_text.getText().toString();
       String source = src_et.getText().toString();
       String depart = depart_et.getText().toString();
       String dest = dest_et.getText().toString();
       String arrive = arrive_et.getText().toString();

       Flight flight = new Flight();
       try {
           flight.createFlight(flight_num_string, source, depart, dest, arrive);
       }catch( ParseException | IOException e ) {
           Toast.makeText(this, e.getMessage() , Toast.LENGTH_SHORT).show();
           return;
       }

       this.airline = new Airline(airline_name, flight);



       Toast.makeText(this, "Added " + this.airline, Toast.LENGTH_SHORT).show();
   }



    public void returnToMain(View view) {
        Intent data = new Intent();
        data.putExtra(SUM_VALUE, this.airline);
        setResult(RESULT_OK, data);

        finish();
    }
}