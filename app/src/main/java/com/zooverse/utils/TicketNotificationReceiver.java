package com.zooverse.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zooverse.MainApplication;

public class TicketNotificationReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		TicketNotificationHelper.notify(intent.getStringExtra(MainApplication.INTENT_EXTRA_TICKET_DATE));
	}
}