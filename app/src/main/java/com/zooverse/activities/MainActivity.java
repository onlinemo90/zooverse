package com.zooverse.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zooverse.MainApplication;
import com.zooverse.model.Model;
import com.zooverse.R;
import com.zooverse.ticket.Ticket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Check if valid saved ticket in preferences
		Button startVisitButton = (Button)findViewById(R.id.start_visit_btn);
		String storedTicketString = getSharedPreferences("tickets", Context.MODE_PRIVATE).getString("scannedTicket", "");
		Log.d("Loading saved ticket", storedTicketString);
		Ticket storedTicket = new Ticket(storedTicketString);
		if (storedTicket.isValid() && DateUtils.isToday(storedTicket.getDate().getTime())){
			startVisitButton.setVisibility(View.VISIBLE);
		}
		else{
			startVisitButton.setVisibility(View.INVISIBLE);
		}
	}
	
	public void openScanTicket(View view) {
		startActivity(new Intent(MainApplication.getContext(), ScanTicketActivity.class));
	}
	
	public void startVisit(View view){
		startActivity(new Intent(MainApplication.getContext(), ZooMenuActivity.class));
	}
}