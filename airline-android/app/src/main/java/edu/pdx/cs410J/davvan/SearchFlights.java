package edu.pdx.cs410J.davvan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import edu.pdx.cs410J.ParserException;

public class SearchFlights extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flights);
    }


    public void searchAirline(View view) {
        EditText name_edit_text = findViewById(R.id.airline_name);
        EditText src_et = findViewById(R.id.src);
        EditText dest_et = findViewById(R.id.dest);

        String airline_name = name_edit_text.getText().toString();
        String source = src_et.getText().toString();
        String dest = dest_et.getText().toString();

        Airline found_airline= new Airline();

        File dataDir = this.getDataDir();
        String filename = airline_name + ".txt";
        dataDir = new File(dataDir, filename);
        try{
            TextParser parser = new TextParser(new FileReader(dataDir));
            found_airline = parser.parse();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, airline_name+ " cannot be found in data.", Toast.LENGTH_LONG).show();
            return;
        } catch (ParserException e ) {
            Toast.makeText(this, "Problem parsing airline: " + airline_name + ". " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Airline result_air = null;
        if(source.equals("") || dest.equals("")){
            result_air = found_airline;
        }
        else{
            result_air = found_airline.getMatchingFlights(source, dest);
        }
        if(result_air.getFlights()== null) {
            Toast.makeText(this, "Couldn't find the matching input.", Toast.LENGTH_LONG).show();
            return;
        }

        StringWriter sw = new StringWriter();
        PrettyPrinter printer = new PrettyPrinter(sw);
        printer.dump(result_air);

        TextView prettyView = findViewById(R.id.resultView);
        prettyView.setText(sw.toString());

        

    }
}