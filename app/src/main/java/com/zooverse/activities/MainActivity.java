package com.zooverse.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.ticket.Ticket;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		// Check if valid saved ticket in preferences
		Button startVisitButton = (Button)findViewById(R.id.startVisitButton);
		Button scanTicketButton = (Button)findViewById(R.id.scanTicketButton);
		String storedTicketString = MainApplication.getLastTicketPreference();
		Log.d("Loading saved ticket", storedTicketString);
		Ticket storedTicket = new Ticket(storedTicketString);
		if (storedTicket.isValid() && DateUtils.isToday(storedTicket.getDate().getTime())){
			startVisitButton.setVisibility(View.VISIBLE);
			scanTicketButton.setVisibility(View.GONE);
		}
		else{
			startVisitButton.setVisibility(View.INVISIBLE);
			scanTicketButton.setVisibility(View.VISIBLE);
		}
	}
	
	public void openScanTicket(View view) {
		startActivity(new Intent(MainApplication.getContext(), ScanTicketActivity.class));
	}
	
	public void startVisit(View view){
		startActivity(new Intent(MainApplication.getContext(), ZooMenuActivity.class));
	}
}