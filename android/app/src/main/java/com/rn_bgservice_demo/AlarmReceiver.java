/**
 * This broadcast receiver receives the setting of an alarm with intent
 * and passes the intent as appropriate to a service which in turn passes
 * the bundle (intent data) to the React Native layer.
 */

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
            Log.d(TAG, this.getClass() + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ": "
                    + (new Throwable().getStackTrace())[0].getMethodName());
            for (String key : extras.keySet()) {
                Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber()
                        + ": Bundle key: " + key + ", value: " + extras.get(key));
                /* A key with value "ACTION" triggers ReminderEventService */
                /* which will pass the bundle data to HeadlessJs, */
                /* the background "service" of the React Native layer */
                if (key.equals("ACTION")) {
                    Intent mIntent = new Intent(context, ReminderEventService.class);
                    mIntent.putExtras(extras); /* pass bundle received through */
                    context.startService(mIntent);
                }
            }
            Log.d(TAG, "→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→");
        }
        context.startService(new Intent(context, ReminderService.class));
    }
}
