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
import com.zooverse.model.Model;
import com.zooverse.model.Ticket;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Go directly to Zoo Menu if a ticket for today is stored
		Button startVisitButton = (Button)findViewById(R.id.startVisitButton);
		boolean ticketForTodayStored = false;
		List<Ticket> storedTickets = Model.getInstance().getStoredTickets();
		for (Ticket ticket : storedTickets){
			if (ticket.isForToday()){
				ticketForTodayStored = true;
				break;
			}
		}
		if (ticketForTodayStored){
			startActivity(new Intent(MainApplication.getContext(), ZooMenuActivity.class));
			startVisitButton.setVisibility(View.VISIBLE);
		}
		else{
			startVisitButton.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	public void openScanTicket(View view) {
		startActivity(new Intent(MainApplication.getContext(), ScanTicketActivity.class));
	}
	
	public void startVisit(View view){
		startActivity(new Intent(MainApplication.getContext(), ZooMenuActivity.class));
	}
}