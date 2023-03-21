package edu.pdx.cs410J.davvan;

import static edu.pdx.cs410J.davvan.AddFlightActivity.SUM_VALUE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int GET_SUM = 42;
    private ArrayAdapter<Airline> airlines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        ListView listOfSums= findViewById(R.id.sums);
        try {
            TextParser parser = new TextParser(new FileReader(getSumsFile()));
        } catch (IOException | ParserException e) {

            Toast.makeText(this, "While reading file: " + e, Toast.LENGTH_SHORT).show();
        }
        airlines = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, airlinesFromFile);
        listOfSums.setAdapter(airlines);
         */
    }

    public void launchCalculator(View view) {
        startActivityForResult(new Intent(this, AddFlightActivity.class), GET_SUM);

    }

    public void searchAirlineFromMain(View view) {
        startActivity(new Intent(this, SearchFlights.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GET_SUM) {
                if (data != null) {
                    Airline airline = data.getSerializableExtra(SUM_VALUE, Airline.class);
                    if (airline != null) {
                        try {
                            writeSumsToInternalStorage(airline);
                        } catch (IOException e) {
                            Toast.makeText(this, "While writing file: " + e, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }

    private void writeSumsToInternalStorage(Airline airline) throws IOException {
        File dataDir = this.getDataDir();
        String fileName = airline.getName() + ".txt";

        dataDir = new File(dataDir, fileName);
        boolean newfile= dataDir.length()== 0;


        TextDumper dumper;
        if(newfile) {
            dumper = new TextDumper(new FileWriter(dataDir, true), true);
            dumper.dump(airline);
        }else{
            dumper = new TextDumper(new FileWriter(dataDir, true), false);
        }
        dumper.dump(airline);
    }


}