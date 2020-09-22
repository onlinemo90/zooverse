package com.animalsoundsldn.ticket;

import android.util.Log;

import com.animalsoundsldn.utils.EncryptionHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
	private static final String FIELD_SEPARATOR = "\\|"; // pipe character, escaped because regex
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
			String[] fields = EncryptionHelper.decrypt(encryptedTicketString).split(Ticket.FIELD_SEPARATOR);
			this.zooID = fields[0];
			this.date = DATE_FORMAT.parse(fields[1]);
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
}
