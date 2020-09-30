package com.zooverse.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.SurfaceView;
import android.widget.Toast;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.ticket.Ticket;

import java.util.Date;

public class ScanTicketActivity extends AbstractQRCodeReaderActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_ticket);
		
		SurfaceView surfaceCamera = findViewById(R.id.cameraSurface);
		assignCamera(surfaceCamera);
	}
	
	@Override
	public void processQRCode(String qrContent) {
		Ticket ticket = new Ticket(qrContent);
		if (ticket.isValid()) {
			if (ticket.getZooID().equals(getString(R.string.zoo_id))) {
				Date today = new Date();
				if (DateUtils.isToday(ticket.getDate().getTime())) { //ticket date is today
					//save ticket date for shortcut access to menu
					SharedPreferences preferences = getSharedPreferences("tickets", Context.MODE_PRIVATE);
					SharedPreferences.Editor preferenceEditor = preferences.edit();
					preferenceEditor.putString("lastTicketDate", ticket.getFormattedDate());
					preferenceEditor.apply();
					Toast.makeText(MainApplication.getContext(), "you're going to the Zoo", Toast.LENGTH_SHORT).show();
					//TODO: @Pedro to call main menu here and remove above toast
				} else if (ticket.getDate().before(today)) {
					Toast.makeText(MainApplication.getContext(), getString(R.string.scan_ticket_error_past_ticket), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainApplication.getContext(), getString(R.string.scan_ticket_error_future_ticket), Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(MainApplication.getContext(), getString(R.string.scan_ticket_error_wrong_zoo), Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(MainApplication.getContext(), getString(R.string.scan_ticket_error_invalid_qr), Toast.LENGTH_SHORT).show();
		}
	}
}