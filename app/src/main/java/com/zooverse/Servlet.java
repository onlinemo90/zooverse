package com.zooverse;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zooverse.activities.SubjectActivity;
import com.zooverse.activities.ZooMenuActivity;
import com.zooverse.zoo.Group;
import com.zooverse.zoo.Individual;
import com.zooverse.zoo.Subject;
import com.zooverse.zoo.Zoo;
import com.zooverse.zoo.Species;
import com.zooverse.zoo.Ticket;
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
	private static final String url =
			getContext().getString(R.string.url_scheme) + "://" +
					getContext().getString(R.string.url_host) +
					getContext().getString(R.string.url_pathPrefix) + "?";
	
	// Delimiters
	private static final String FIELD_SEPARATOR = "&";
	private static final String KEY_VALUE_SEPARATOR = "=";
	
	// Common keys
	private static final String TYPE_KEY = "type";
	private static final String ZOO_KEY = "zoo";
	
	// Type values
	private static final String TICKET_TYPE = "ticket";
	private static final String SPECIES_TYPE = "species";
	private static final String INDIVIDUAL_TYPE = "individual";
	private static final String GROUP_TYPE = "group";
	
	// Ticket keys
	private static final String TICKET_DATE_KEY = "date";
	
	// Subject Keys
	private static final String SUBJECT_ID_KEY = "id";
	
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
				if (SPECIES_TYPE.equals(requestType)) {
					processSubject(requestMap, activity, Species.class);
				} else if (INDIVIDUAL_TYPE.equals(requestType)) {
					processSubject(requestMap, activity, Individual.class);
				} else if (GROUP_TYPE.equals(requestType)) {
					processSubject(requestMap, activity, Group.class);
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
						if (!Zoo.getStoredTickets().contains(ticket)) {
							Zoo.storeTicket(ticket);
						}
						activity.finish();
						activity.startActivity(new Intent(activity, ZooMenuActivity.class));
					} else { // Future Ticket
						if (Zoo.getStoredTickets().contains(ticket)) {
							Toast.makeText(activity, R.string.scan_qr_code_error_already_stored, Toast.LENGTH_SHORT).show();
						} else {
							Zoo.storeTicket(ticket);
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
	
	private static void processSubject(Map<String, String> requestMap, AppCompatActivity activity, Class subjectClass) throws Exception {
		int subjectId = Integer.parseInt(Objects.requireNonNull(requestMap.get(SUBJECT_ID_KEY)));
		for (Subject subject : Zoo.getSubjectList(subjectClass)) {
			if (subjectId == subject.getDatabaseID()) {
				if (Zoo.hasTodayTicket()) {
					Intent intent = new Intent(MainApplication.getContext(), SubjectActivity.class);
					intent.putExtra(SubjectActivity.IntentExtras.SUBJECT_UUID_ARRAY.toString(), new String[]{subject.getUuid()});
					activity.startActivity(intent);
				} else {
					Toast.makeText(activity, R.string.scan_qr_code_subject_search_without_ticket, Toast.LENGTH_SHORT).show();
				}
				return;
			}
		}
		throw new Exception(); // subject not found - invalid QR code
	}
	
}
