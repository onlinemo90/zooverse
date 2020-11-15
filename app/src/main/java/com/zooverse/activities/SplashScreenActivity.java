package com.zooverse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.zooverse.R;
import com.zooverse.model.Model;
import com.zooverse.model.Ticket;

public class SplashScreenActivity extends AbstractBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			String[] ticketContent = getIntent().getData().toString().split("ticket=");
			Ticket ticket = new Ticket(ticketContent[1]);
			if (ticket.isValid() && ticket.getZooID().equals(getString(R.string.zoo_id)) && !Model.getStoredTickets().contains(ticket)) {
				Model.storeTicket(ticket);
			}
			Log.d("entryPoint", "Browser");
		} catch (Exception e) {
			Log.d("entryPoint", "Launcher");
		}
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}
