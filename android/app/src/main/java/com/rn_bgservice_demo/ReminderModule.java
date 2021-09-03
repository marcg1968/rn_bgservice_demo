package com.rn_bgservice_demo;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nonnull;

public class ReminderModule extends ReactContextBaseJavaModule {

    public static final String REACT_CLASS = "Reminder";
    private static final String TAG = "ReminderModule";
    private static ReactApplicationContext reactContext;
    private AlarmManager alarmMgr;

    public ReminderModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void startService() {
        // Starting the reminder service
        Log.d(TAG, "……………………………………………………………………………………………………………………………………………………………………………………………………………");
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber() + ": Starting " + REACT_CLASS);
        Log.d(TAG, "……………………………………………………………………………………………………………………………………………………………………………………………………………");
        this.reactContext.startService(new Intent(this.reactContext, ReminderService.class));
    }

    @ReactMethod
    public void stopService() {
        Log.d(TAG, "……………………………………………………………………………………………………………………………………………………………………………………………………………");
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber() + ": Stopping " + REACT_CLASS);
        Log.d(TAG, "……………………………………………………………………………………………………………………………………………………………………………………………………………");
        this.reactContext.stopService(new Intent(this.reactContext, ReminderService.class));
    }

    /**
     * Respond to "ping" from React Native layer
     * @return key-value pair with key "pong" and value epoch timestamp as string
     */
    @ReactMethod
    public void ping() {
        Log.d(TAG, "……………………………………………………………………………………………………………………………………………………………………………………………………………");
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber() + " ping " + REACT_CLASS);
        Log.d(TAG, "……………………………………………………………………………………………………………………………………………………………………………………………………………");

        /* METHOD 1 to send events to RN layer */
        WritableMap params = Arguments.createMap();
        params.putDouble("pong_ts", (new Date()).getTime());
        /* this emission signals a "ping" and is caught by NativeEventEmitter eventListener, e.g. in App.js */
        String emission = "ReminderServicePing";
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(emission,null);

        /* METHOD 2 to send events to RN layer VIA ReminderEventService */
        // Intent mIntent = new Intent(this.reactContext, ReminderEventService.class);
        Intent mIntent = new Intent(this.reactContext, ReminderEventService.class); /* NB: class extending HeadlessJsTaskService */
        /* data sent via .putString(key, value) is available in RN layer,
         * specifically as param to function BackgroundTask in the following call
         * AppRegistry.registerHeadlessTask('ReminderService', () => BackgroundTask) */
        Bundle bundle = new Bundle();
        bundle.putString("PONG", (new Date()).getTime() + "");
        mIntent.putExtras(bundle);
        this.reactContext.startService(mIntent);

        /* can also send to ReminderService */
        mIntent = new Intent(this.reactContext, ReminderService.class);
        bundle = new Bundle();
        bundle.putString("PONG", (new Date()).getTime() + "");
        mIntent.putExtras(bundle);
        this.reactContext.startService(mIntent);

    }

    @ReactMethod
    public void setAlarm() {
        Log.d(TAG, "……………………………………………………………………………………………………………………………………………………………………………………………………………");
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber() + " setAlarm " + REACT_CLASS);
        Log.d(TAG, this.getName());
        Log.d(TAG, "……………………………………………………………………………………………………………………………………………………………………………………………………………");

        // Context context = getReactApplicationContext(); /* not needed same as this.reactContext */

        // // AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // // Intent intent;
        // // int requestId = 987;
        // // PendingIntent pIntent = PendingIntent.getService(context, requestId, intent, PendingIntent.FLAG_NO_CREATE);
        // // if (pIntent != null && am != null) {
        // //     am.cancel(pIntent);
        // // }

        Intent mIntent;
        PendingIntent pIntent;
        Bundle bundle;
        int requestId = 1;

        alarmMgr = (AlarmManager) reactContext.getSystemService(Context.ALARM_SERVICE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        /* attempt 1 using getReactApplicationContext() */
        requestId = 1000;
        // Intent mIntent = new Intent(this.reactContext, ReminderService.class);
        // Intent mIntent = new Intent(context, ReminderService.class);
        calendar.add(Calendar.MINUTE, 2);
        mIntent = new Intent(reactContext, AlarmReceiver.class);
        bundle = new Bundle();
        bundle.putString("PONG", (new Date()).getTime() + "");
        bundle.putString("via", "alarm 1000");
        mIntent.putExtras(bundle);
        // PendingIntent pIntent = PendingIntent.getService(context, requestId, mIntent, PendingIntent.FLAG_NO_CREATE);
        // PendingIntent pIntent = PendingIntent.getService(this.reactContext, requestId, mIntent, PendingIntent.FLAG_NO_CREATE);
        pIntent = PendingIntent.getBroadcast(reactContext, requestId, mIntent, 0);
        Log.d(TAG, "đđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđ");
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber() + " setAlarm ");
        Log.d(TAG, "pIntent = " + pIntent);
        Log.d(TAG, "pIntent.toString() = " + pIntent.toString());
        Log.d(TAG, "calendar = " + sdf.format(calendar.getTime()));
        Log.d(TAG, "calendar.getTimeInMillis() = " + calendar.getTimeInMillis());
        calendar.setTimeInMillis(System.currentTimeMillis());
        Log.d(TAG, "now: " + sdf.format(calendar.getTime()));
        Log.d(TAG, "alarmMgr: " + alarmMgr.toString());
        Log.d(TAG, "đđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđ");

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), pIntent);
        // alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        Toast.makeText(reactContext, String.format("Alarm %s set!", requestId), Toast.LENGTH_LONG).show();

        /* cancel */
        pIntent = PendingIntent.getService(reactContext, requestId, mIntent, PendingIntent.FLAG_NO_CREATE);
        alarmMgr.cancel(pIntent);

        /* attempt 2 using
         - this.reactContext
         - PendingIntent.getBroadcast()
         -
         */
        requestId = 2000;
        calendar.add(Calendar.MINUTE, 4);
        alarmMgr = (AlarmManager) reactContext.getSystemService(Context.ALARM_SERVICE);
        mIntent = new Intent(reactContext, AlarmReceiver.class);
        bundle = new Bundle();
        bundle.putString("PONG", (new Date()).getTime() + "");
        bundle.putString("via", "alarm " + requestId);
        mIntent.putExtras(bundle);
        pIntent = PendingIntent.getBroadcast(reactContext, requestId, mIntent, 0);
        Log.d(TAG, "đđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđ");
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber() + " setAlarm ");
        Log.d(TAG, "pIntent = " + pIntent);
        Log.d(TAG, "pIntent.toString() = " + pIntent.toString());
        // Log.d(TAG, "calendar = " + sdf.format(calendar.getTime()));
        // Log.d(TAG, "calendar.getTimeInMillis() = " + calendar.getTimeInMillis());
        calendar.setTimeInMillis(System.currentTimeMillis());
        Log.d(TAG, "now: " + sdf.format(calendar.getTime()));
        Log.d(TAG, "alarm in SystemClock.elapsedRealtime() 60 * 1000: " + SystemClock.elapsedRealtime() + 60 * 1000);
        Log.d(TAG, "alarmMgr: " + alarmMgr.toString());
        Log.d(TAG, "đđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđ");

        // alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), pIntent);
        // alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() +
                60 * 1000, pIntent);
        Toast.makeText(reactContext, String.format("Alarm %s set!", requestId), Toast.LENGTH_LONG).show();

        // AlarmManager.AlarmClockInfo next = alarmMgr.getNextAlarmClock();
        // if (next != null) {
        //     Log.d(TAG, "łłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłł");
        //     Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber() + " AlarmManager.AlarmClockInfo next ");
        //     Log.d(TAG, "next.getTriggerTime() = " + next.getTriggerTime());
        //     Log.d(TAG, "next.getShowIntent() = " + next.getShowIntent());
        //     Log.d(TAG, "łłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłłł");
        // }
        
    }

}
