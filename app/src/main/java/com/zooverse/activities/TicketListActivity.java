package com.zooverse.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.zoo.Zoo;
import com.zooverse.zoo.Ticket;
import com.zooverse.activities.adapters.TicketListAdapter;

import java.util.List;

public class TicketListActivity extends AbstractBaseActivity {
	private TicketListAdapter ticketListAdapter;
	
	@Override
	@SuppressLint("MissingSuperCall")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.activity_ticket_list, true);
		
		// Loading RecyclerView with stored tickets
		ticketListAdapter = new TicketListAdapter();
		RecyclerView ticketList = findViewById(R.id.ticketList);
		ticketList.setAdapter(ticketListAdapter);
		ticketList.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		List<Ticket> storedTickets = Zoo.getStoredTickets();
		
		View noTicketLayoutView = findViewById(R.id.noTicketLayout);
		View ticketListLayoutView = findViewById(R.id.ticketListLayout);
		
		ticketListAdapter.notifyDataSetChanged();
		if (storedTickets.isEmpty()) {
			noTicketLayoutView.setVisibility(View.VISIBLE);
			ticketListLayoutView.setVisibility(View.GONE);
		} else {
			noTicketLayoutView.setVisibility(View.GONE);
			ticketListLayoutView.setVisibility(View.VISIBLE);
		}
	}
	
	public void openScanTicket(View view) {
		startActivity(new Intent(MainApplication.getContext(), ZooMenuActivity.class));
	}
}