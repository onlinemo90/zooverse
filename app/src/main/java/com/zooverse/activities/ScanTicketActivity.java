package com.zooverse.activities;


import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.SurfaceView;
import android.widget.Toast;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.model.Model;
import com.zooverse.model.Ticket;

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
				if (ticket.isExpired()) {
					Toast.makeText(MainApplication.getContext(), getString(R.string.scan_ticket_error_past_ticket), Toast.LENGTH_SHORT).show();
				}
				else {
					if (ticket.isForToday()) {
						if (!Model.getInstance().getStoredTickets().contains(ticket)){
							Model.getInstance().storeTicket(ticket);
						}
						finish();
						startActivity(new Intent(MainApplication.getContext(), ZooMenuActivity.class));
					}
					else { // Future Ticket
						if (Model.getInstance().getStoredTickets().contains(ticket)){
							Toast.makeText(MainApplication.getContext(), getString(R.string.scan_ticket_error_already_stored), Toast.LENGTH_SHORT).show();
						} else {
							Model.getInstance().storeTicket(ticket);
							Toast.makeText(MainApplication.getContext(), getString(R.string.scan_ticket_future_ticket_stored), Toast.LENGTH_SHORT).show();
						}
					}
				}
			} else {
				Toast.makeText(MainApplication.getContext(), getString(R.string.scan_ticket_error_wrong_zoo), Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(MainApplication.getContext(), getString(R.string.scan_ticket_error_invalid_qr), Toast.LENGTH_SHORT).show();
		}
	}
}