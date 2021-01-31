package com.zooverse;

import android.util.Log;

import com.zooverse.model.Model;
import com.zooverse.model.Species;
import com.zooverse.model.Ticket;
import com.zooverse.utils.EncryptionHelper;
import static com.zooverse.MainApplication.getContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class Servlet {
	private static final String FIELD_SEPARATOR = "&";
	private static final String KEY_VALUE_SEPARATOR = "=";
	private static final String url =
		getContext().getString(R.string.url_scheme) + "://" +
		getContext().getString(R.string.url_host) +
		getContext().getString(R.string.url_pathPrefix) + "?";
	
	// Common keys
	private static final String TYPE_KEY = "type";
	private static final String ZOO_KEY = "zoo";
	
	// Type values
	private static final String TICKET_TYPE = "ticket";
	private static final String SPECIES_TYPE = "species";
	
	// Ticket keys
	private static final String TICKET_DATE_KEY = "date";
	
	// Species keys
	private static final String SPECIES_ID_KEY = "id";
	
	private static SimpleDateFormat TICKET_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	
	static {
		TICKET_DATE_FORMAT.setLenient(false);
	}
	
	
	private Servlet() {
		// prevent instantiation
	}
	
	public static Object process(String requestURL) {
		String encryptedRequest;
		if (requestURL.trim().toLowerCase().startsWith(url))
			encryptedRequest = requestURL.trim().substring(url.length());
		else
			encryptedRequest = requestURL;
			try {
				String request = EncryptionHelper.decrypt(encryptedRequest).toLowerCase();
				Log.d("decryptedRequest", request);
				Map<String, String> requestMap = new HashMap<>();
				for (String field : request.split(Pattern.quote(FIELD_SEPARATOR))) {
					String key = field.trim().split(Pattern.quote(KEY_VALUE_SEPARATOR))[0];
					String value = field.trim().split(Pattern.quote(KEY_VALUE_SEPARATOR))[1];
					requestMap.put(key, value);
				}
				String requestType = requestMap.get(TYPE_KEY);
				if (TICKET_TYPE.equals(requestType)) {
					return processTicket(requestMap);
				} else if (MainApplication.getContext().getString(R.string.zoo_id).equalsIgnoreCase(requestMap.get(ZOO_KEY))) {
					switch(requestType){
						case SPECIES_TYPE: return processSpecies(requestMap);
						// add new request types here
					}
				}
			} catch (Exception e) {
				return null;
			}
		return null;
	}
	
	private static Ticket processTicket(Map<String, String> requestMap) throws ParseException {
		String zooID = requestMap.get(ZOO_KEY);
		String dateString = requestMap.get(TICKET_DATE_KEY);
		if (zooID != null && dateString.length() == TICKET_DATE_FORMAT.toPattern().length()) {
			return new Ticket(zooID, TICKET_DATE_FORMAT.parse(dateString));
		}
		return null;
	}
	
	private static Species processSpecies(Map<String, String> requestMap) {
		int speciesId = Integer.parseInt(Objects.requireNonNull(requestMap.get(SPECIES_ID_KEY)));
		if (Model.getSpecies().containsKey(speciesId)) {
			return Model.getSpecies().get(speciesId);
		}
		return null;
	}
	
}
