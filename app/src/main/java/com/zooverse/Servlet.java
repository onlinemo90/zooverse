package com.zooverse;

import android.util.Log;

import com.zooverse.model.Model;
import com.zooverse.model.Species;
import com.zooverse.model.Ticket;
import com.zooverse.utils.EncryptionHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Servlet {
	private static final String FIELD_SEPARATOR = "|";
	private static final String TICKET_ID = "T";
	private static final String INFO_POINT_ID = "I";
	
	private static SimpleDateFormat TICKET_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	
	static {
		TICKET_DATE_FORMAT.setLenient(false);
	}
	
	
	private Servlet() {
		// prevent instantiation
	}
	
	public static Object process(String encryptedRequest) {
		try {
			String request = EncryptionHelper.decrypt(encryptedRequest);
			Log.d("decryptedRequest", request);
			String[] requestFields = request.split(Pattern.quote(FIELD_SEPARATOR));
			String requestTypeId = requestFields[0];
			if (TICKET_ID.equals(requestTypeId)) {
				return processTicket(requestFields);
			} else if (INFO_POINT_ID.equals(requestTypeId)) {
				return processInfoPoint(requestFields);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	private static Ticket processTicket(String[] requestFields) throws ParseException {
		String zooID = requestFields[1];
		String dateString = requestFields[2];
		if (dateString.length() == TICKET_DATE_FORMAT.toPattern().length() && dateString.matches("[0-9]+")) {
			return new Ticket(zooID, TICKET_DATE_FORMAT.parse(dateString));
		}
		return null;
	}
	
	private static Species processInfoPoint(String[] requestFields) {
		int speciesId = Integer.parseInt(requestFields[1]);
		for (Species species : Model.getSpeciesList()) {
			if (species.getId() == speciesId) {
				return species;
			}
		}
		return null;
	}
}
