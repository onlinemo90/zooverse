package com.zooverse.model;

import android.text.format.DateUtils;
import android.util.Log;

import com.zooverse.utils.EncryptionHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Ticket {
	private static final String FIELD_SEPARATOR = "|";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	
	private boolean isValid;
	private String zooID;
	private Date date;
	
	private Ticket() {
		boolean isValid;
	}
	
	public Ticket(String encryptedTicketString) {
		try {
			this.isValid = true;
			Log.d("decrypted ticket->", EncryptionHelper.decrypt(encryptedTicketString));
			String[] fields = EncryptionHelper.decrypt(encryptedTicketString).split(Pattern.quote(Ticket.FIELD_SEPARATOR));
			this.zooID = fields[0];
			this.date = DATE_FORMAT.parse(fields[1]);
		} catch (Exception e) {
			this.isValid = false;
			this.zooID = null;
			this.date = null;
		}
	}
	
	public Ticket(String zooID, String date) {
		try {
			this.isValid = true;
			this.zooID = zooID;
			this.date = DATE_FORMAT.parse(date);
		} catch (Exception e) {
			this.isValid = false;
			this.zooID = null;
			this.date = null;
		}
	}
	
	public boolean isValid() {
		return this.isValid;
	}
	
	public String getZooID() {
		return this.zooID;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public String getFormattedDate() {
		return Ticket.DATE_FORMAT.format(this.date);
	}
	
	public boolean isForToday() {
		return this.isValid && DateUtils.isToday(this.date.getTime());
	}
	
	public boolean isExpired() {
		return !this.isForToday() && this.date.before(new Date());
	}
	
	public static String getTodayFormattedDate() {
		return DATE_FORMAT.format(new Date());
	}
	
	public boolean equals(Object other) {
		if (other instanceof Ticket) {
			Ticket otherTicket = (Ticket) other;
			return this.isValid == otherTicket.isValid()
					&& this.zooID.equals(otherTicket.getZooID())
					&& this.date.equals(otherTicket.getDate());
		}
		return false;
	}
}
