package com.animalsoundsldn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.animalsoundsldn.model.Model;
import com.animalsoundsldn.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DB test only
        model = Model.getInstance(getApplicationContext());
        ArrayList<String> species = model.getSpecies();
        for (String dbentry : species) {
            Log.d("dbtest", dbentry);
        }
    }//test end

//open screen for ticket scan
    public void openScanTicket(View view) {

        Intent intent = new Intent(getApplicationContext(), ScanTicketActivity.class);
        startActivity(intent);
    }
}