package com.example.animalsoundsldn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//open screen for ticket scan
    public void openScanTicket(View view) {
        Intent intent = new Intent(getApplicationContext(), ScanTicketActivity.class);
        startActivity(intent);
    }
}