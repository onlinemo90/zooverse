package com.zooverse;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zooverse.activities.SpeciesActivity;
import com.zooverse.activities.ZooMenuActivity;
import com.zooverse.model.Model;
import com.zooverse.model.Species;
import com.zooverse.model.Ticket;
import com.zooverse.notifications.TicketNotificationHandler;
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
	
	public static void process(String externalRequest, AppCompatActivity activity) {
		String encryptedRequest = externalRequest.trim().toLowerCase().startsWith(url) ? externalRequest.trim().substring(url.length()) : externalRequest;
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
				processTicket(requestMap, activity);
			} else if (activity.getString(R.string.zoo_id).equalsIgnoreCase(requestMap.get(ZOO_KEY))) {
				switch (requestType) {
					case SPECIES_TYPE:
						processSpecies(requestMap, activity);
					// add new request types here
				}
			} else
				Toast.makeText(activity, R.string.scan_qr_code_error_invalid_qr, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(activity, R.string.scan_qr_code_error_invalid_qr, Toast.LENGTH_SHORT).show();
		}
	}
	
	private static void processTicket(Map<String, String> requestMap, AppCompatActivity activity) throws ParseException {
		String zooID = requestMap.get(ZOO_KEY);
		String dateString = requestMap.get(TICKET_DATE_KEY);
		if (zooID != null && dateString.length() == TICKET_DATE_FORMAT.toPattern().length()) {
			Ticket ticket = new Ticket(zooID, TICKET_DATE_FORMAT.parse(dateString));
			if (ticket.getZooID().equalsIgnoreCase(activity.getString(R.string.zoo_id))) {
				if (ticket.isExpired()) {
					Toast.makeText(activity, R.string.scan_qr_code_error_past_ticket, Toast.LENGTH_SHORT).show();
				} else {
					if (ticket.isForToday()) {
						if (!Model.getStoredTickets().contains(ticket)) {
							Model.storeTicket(ticket);
						}
						activity.finish();
						activity.startActivity(new Intent(activity, ZooMenuActivity.class));
					} else { // Future Ticket
						if (Model.getStoredTickets().contains(ticket)) {
							Toast.makeText(activity, R.string.scan_qr_code_error_already_stored, Toast.LENGTH_SHORT).show();
						} else {
							Model.storeTicket(ticket);
							TicketNotificationHandler.setNotification(ticket.getDate(), ticket.getFormattedDate());
							Toast.makeText(activity, R.string.scan_qr_code_future_ticket_stored, Toast.LENGTH_SHORT).show();
						}
					}
				}
			} else {
				Toast.makeText(MainApplication.getContext(), R.string.scan_qr_code_error_wrong_zoo, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private static void processSpecies(Map<String, String> requestMap, AppCompatActivity activity) {
		int speciesId = Integer.parseInt(Objects.requireNonNull(requestMap.get(SPECIES_ID_KEY)));
		if (Model.getSpecies().containsKey(speciesId)) {
			Species species = Model.getSpecies().get(speciesId);
			if (Model.hasTodayTicket()) {
				Intent intent = new Intent(MainApplication.getContext(), SpeciesActivity.class);
				intent.putExtra(MainApplication.INTENT_EXTRA_SPECIES_ID, species.getId());
				activity.startActivity(intent);
			} else {
				Toast.makeText(activity, R.string.scan_qr_code_species_search_without_ticket, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
}
