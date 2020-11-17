package com.zooverse.activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.Theme;
import com.zooverse.model.Model;
import com.zooverse.model.Ticket;
import com.zooverse.activities.adapters.TicketListAdapter;

import java.util.List;

public class MainActivity extends AbstractBaseActivity implements TicketListAdapter.TicketOnClickListener {
	private TicketListAdapter ticketListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Loading RecyclerView with stored tickets
		ticketListAdapter = new TicketListAdapter(this);
		RecyclerView ticketList = findViewById(R.id.ticketList);
		ticketList.setAdapter(ticketListAdapter);
		ticketList.setLayoutManager(new LinearLayoutManager(MainApplication.getContext()));
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		ticketListAdapter.notifyDataSetChanged();
		
		View noTicketLayoutView = findViewById(R.id.noTicketLayout);
		View ticketListLayoutView = findViewById(R.id.ticketListLayout);
		
		List<Ticket> storedTickets = Model.getStoredTickets();
		if (storedTickets.isEmpty()) {
			noTicketLayoutView.setVisibility(View.VISIBLE);
			ticketListLayoutView.setVisibility(View.GONE);
		} else {
			noTicketLayoutView.setVisibility(View.GONE);
			ticketListLayoutView.setVisibility(View.VISIBLE);
		}
	}
	
	public void openScanTicket(View view) {
		startActivity(new Intent(MainApplication.getContext(), ScanTicketActivity.class));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_bar_main, menu);
		for (int i = 0; i < menu.size(); i++) {
			Drawable drawable = menu.getItem(i).getIcon();
			if (drawable != null) {
				drawable.mutate().setColorFilter(Theme.getColor(R.attr.themeColorBackground), PorterDuff.Mode.SRC_ATOP);
			}
		}
		return true;
	}
	
	@SuppressLint("NonConstantResourceId")
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.settingsMenuItem:
				startActivity(new Intent(MainApplication.getContext(), SettingsActivity.class));
				break;
			case R.id.scanTicketMenuItem:
				startActivity(new Intent(MainApplication.getContext(), ScanTicketActivity.class));
				break;
		}
		return true;
	}
	
	@Override
	public void onTicketClick(int position) {
		if (Model.getStoredTickets().get(position).isForToday()) {
			startActivity(new Intent(MainApplication.getContext(), ZooMenuActivity.class));
		}
	}
}