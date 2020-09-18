package com.animalsoundsldn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.animalsoundsldn.model.Model;
import com.animalsoundsldn.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
            Log.d("dbtesting", dbentry);
        }
    }//test end

//open screen for ticket scan
    public void openScanTicket(View view) {
        String lastTicket = getSharedPreferences("tickets", Context.MODE_PRIVATE).getString("lastTicketDate", "no ticket date available");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(new Date());
        //ticket date = today? then jump to main menu
        if (today.equals(lastTicket)){
            Toast.makeText(getApplicationContext(), "call main menu now", Toast.LENGTH_SHORT).show();
        }
        else { // open ticket scanner
            Intent intent = new Intent(getApplicationContext(), ScanTicketActivity.class);
            startActivity(intent);
        }
    }
}