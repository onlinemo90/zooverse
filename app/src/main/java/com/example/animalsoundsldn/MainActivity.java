package com.example.animalsoundsldn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //DB test only
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        ArrayList<String> species = databaseAccess.getSpecies();
        for (String dbentry : species) {
            Log.d("dbtest", dbentry);
        }
        databaseAccess.close();
    }//test end

//open screen for ticket scan
    public void openScanTicket(View view) {

        Intent intent = new Intent(getApplicationContext(), ScanTicketActivity.class);
        startActivity(intent);
    }
}