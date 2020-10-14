package com.zooverse.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.model.Model;
import com.zooverse.model.Ticket;
import com.zooverse.utils.TicketListAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TicketListAdapter.OnClickTicketListener{
	private TicketListAdapter ticketListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Loading RecyclerView with stored tickets
		ticketListAdapter = new TicketListAdapter(this);
		RecyclerView ticketList = findViewById(R.id.ticketList);
		ticketList.setAdapter(ticketListAdapter);
		ticketList.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		ticketListAdapter.notifyDataSetChanged();
		
		Button scanTicketButton = (Button)findViewById(R.id.scanTicketButton);
		TextView upComingTickets = findViewById(R.id.upcomingTicketsTextView);
		
		boolean ticketForTodayStored = false;
		List<Ticket> storedTickets = Model.getInstance().getStoredTickets();
		for (Ticket ticket : storedTickets) {
			if (ticket.isForToday()) {
				ticketForTodayStored = true;
				break;
			}
		}
		if (storedTickets.isEmpty())
			upComingTickets.setVisibility(View.GONE);
		else
			upComingTickets.setVisibility(View.VISIBLE);
		if (ticketForTodayStored)
			scanTicketButton.setVisibility(View.GONE);
		else
			scanTicketButton.setVisibility(View.VISIBLE);
	}
	
	public void openScanTicket(View view) {
		startActivity(new Intent(MainApplication.getContext(), ScanTicketActivity.class));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_bar_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()){
			case R.id.settingsMenuItem:
				startActivity(new Intent(MainApplication.getContext(), SettingsActivity.class));
				break;
			case R.id.scanTicketMenuItem:
				startActivity(new Intent(MainApplication.getContext(), ScanTicketActivity.class));
				break;
		}
		return true;
	}
	
	@Override // on click method for individual recycler view item
	public void onTicketClick(int position) {
		// if ticket for today then let user open zoo menu
		if (Model.getInstance().getStoredTickets().get(position).isForToday()) {
			startActivity(new Intent(MainApplication.getContext(), ZooMenuActivity.class));
		}
	}
}