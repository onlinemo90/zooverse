package com.zooverse.utils;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.zooverse.MainApplication;
import com.zooverse.R;
import com.zooverse.activities.ZooMenuActivity;

import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static com.zooverse.MainApplication.getContext;

public class TicketNotificationHelper {
	
	private static final String CHANNEL_ID = "TICKET_NOTIFICATION_CHANNEL";
	private static final int NOTIFICATION_ID = 1;
	
	public static void notify (String ticketDate) {
		Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.logo_splash);
		
		Intent intent = new Intent(getContext(), ZooMenuActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
		
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
				.setSmallIcon(R.drawable.icon_individuals)
				.setContentTitle(getContext().getString(R.string.notification_title))
				.setContentText(ticketDate)
				.setLargeIcon(icon)
				.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);
		NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
		notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
	}
	
	public static void setNotification(Date date, String formattedDate) {
		Intent intent = new Intent(getContext(), TicketNotificationReceiver.class);
		intent.putExtra(MainApplication.INTENT_EXTRA_TICKET_DATE, formattedDate);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
	}
	
	public static void createNotificationChannel() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = getContext().getString(R.string.notification_channel_name);
			String description = getContext().getString(R.string.notification_channel_description);
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
			channel.setDescription(description);
			NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}
}
