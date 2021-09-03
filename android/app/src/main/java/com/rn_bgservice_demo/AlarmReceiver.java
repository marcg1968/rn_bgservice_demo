package com.rn_bgservice_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "ReminderAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm worked!!!", Toast.LENGTH_LONG).show();
        Bundle extras = intent.getExtras();
        if (extras != null && extras.keySet().size() > 0) {
            Log.d(TAG, "→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→");
            for (String key : extras.keySet()) {
                Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber()
                        + ": Bundle key: " + key + ", value: " + extras.get(key));
            }
            Log.d(TAG, "→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→");
        }
        context.startService(new Intent(context, ReminderService.class));
    }
}
