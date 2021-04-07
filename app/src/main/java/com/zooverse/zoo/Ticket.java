package com.zooverse.zoo;

import android.text.format.DateUtils;

import com.zooverse.MainApplication;
import com.zooverse.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
	private String zooID;
	private Date date;
	
	private Ticket() {
		// Prevent initialisation without arguments
	}
	
	public Ticket(String zooID, Date date) {
		this.zooID = zooID;
		this.date = date;
	}
	
	public String getZooID() {
		return this.zooID;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public String getFormattedDate() {
		return new SimpleDateFormat(MainApplication.getContext().getString(R.string.local_date_format)).format(this.date);
	}
	
	public boolean isForToday() {
		return DateUtils.isToday(this.date.getTime());
	}
	
	public boolean isExpired() {
		return !this.isForToday() && this.date.before(new Date());
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Ticket) {
			Ticket otherTicket = (Ticket) other;
			return this.zooID.equals(otherTicket.getZooID())
					&& this.date.equals(otherTicket.getDate());
		}
		return false;
	}
}
