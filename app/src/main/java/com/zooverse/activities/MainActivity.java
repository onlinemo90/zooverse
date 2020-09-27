package com.zooverse.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zooverse.MainApplication;
import com.zooverse.model.Model;
import com.zooverse.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//DB test only
		ArrayList<String> species = Model.getInstance().getSpecies();
		for (String dbentry : species) {
			Log.d("dbtesting", dbentry);
		}
	}
	
	//open screen for ticket scan
	public void openScanTicket(View view) {
		String lastTicket = getSharedPreferences("tickets", Context.MODE_PRIVATE).getString("lastTicketDate", "no ticket date available");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		//ticket date = today? then jump to main menu
		if (today.equals(lastTicket)) {
			Toast.makeText(MainApplication.getContext(), "call main menu now", Toast.LENGTH_SHORT).show();
		} else { // open ticket scanner
			Intent intent = new Intent(MainApplication.getContext(), ScanTicketActivity.class);
			startActivity(intent);
		}
	}
}