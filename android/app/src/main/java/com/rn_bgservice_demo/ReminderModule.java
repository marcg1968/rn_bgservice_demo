package com.rn_bgservice_demo;

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
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");

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
     *
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
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(emission, null);

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

    /**
     * This will set an alarm to run in about one minute's time
     * which through an intent, triggers the BroadcastReceiver AlarmReceiver.class
     * to start ReminderService.
     * It uses AlarmManager.ELAPSED_REALTIME_WAKEUP and SystemClock.elapsedRealtime()
     * to determine the time to set the alarm.
     */
    @ReactMethod
    public void setAlarmOne() {
        Log.d(TAG, "……………………………………………………………………………………………………………………………………………………………………………………………………………");
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber() + ": "
                + (new Throwable().getStackTrace())[0].getMethodName());
        Log.d(TAG, "using");
        Log.d(TAG, "- `SystemClock.elapsedRealtime() + (60 * 1000)`");
        Log.d(TAG, "- `AlarmManager.ELAPSED_REALTIME_WAKEUP`");
        Log.d(TAG, "……………………………………………………………………………………………………………………………………………………………………………………………………………");

        Intent mIntent;
        PendingIntent pIntent;
        Bundle bundle;
        int requestId = 1;
        /* trigger is dependent on using AlarmManager.ELAPSED_REALTIME_WAKEUP to set the alarm */
        long triggerMs = SystemClock.elapsedRealtime() + (60 * 1000); /* in one minute */
        alarmMgr = (AlarmManager) reactContext.getSystemService(Context.ALARM_SERVICE);
        mIntent = new Intent(reactContext, AlarmReceiver.class);
        bundle = new Bundle();
        bundle.putString("PONG", (new Date()).getTime() + "");
        bundle.putString("via", "alarm " + requestId);
        bundle.putString("triggerMs", "" + triggerMs);
        mIntent.putExtras(bundle);
        pIntent = PendingIntent.getBroadcast(reactContext, requestId, mIntent, 0);
        alarmMgr.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerMs,
                pIntent
        );
        Log.d(TAG, "đđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđ");
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber() + " setAlarm ");
        Log.d(TAG, "alarm #: " + requestId);
        Log.d(TAG, "alarm in: " + triggerMs);
        Log.d(TAG, "đđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđ");
        Toast.makeText(
                reactContext,
                String.format("Alarm %s for %s set!", requestId, triggerMs),
                Toast.LENGTH_LONG
        ).show();
    }

    /**
     * This will set an alarm to run in about two minutes' time
     * which through an intent, triggers the BroadcastReceiver AlarmReceiver.class
     * to start ReminderService.
     * It uses AlarmManager.RTC_WAKEUP and System.currentTimeMillis() + 60 * 1000 * 2
     * to determine the time to set the alarm.
     */
    @ReactMethod
    public void setAlarmTwo() {
        Log.d(TAG, "……………………………………………………………………………………………………………………………………………………………………………………………………………");
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber() + ": "
                + (new Throwable().getStackTrace())[0].getMethodName());
        Log.d(TAG, "using");
        Log.d(TAG, "- `System.currentTimeMillis() + 60 * 1000 * 2`");
        Log.d(TAG, "- `AlarmManager.RTC_WAKEUP`");
        Log.d(TAG, "……………………………………………………………………………………………………………………………………………………………………………………………………………");

        Intent mIntent;
        PendingIntent pIntent;
        Bundle bundle;
        int requestId = 2;
        /* trigger is dependent on using AlarmManager.RTC_WAKEUP to set the alarm */
        long triggerMs = System.currentTimeMillis() + 60 * 1000 * 2; /* in two minutes */
        alarmMgr = (AlarmManager) reactContext.getSystemService(Context.ALARM_SERVICE);
        mIntent = new Intent(reactContext, AlarmReceiver.class);
        bundle = new Bundle();
        bundle.putString("PONG", (new Date()).getTime() + "");
        bundle.putString("via", "alarm " + requestId);
        bundle.putString("triggerMs", "" + triggerMs);
        bundle.putString("at", sdf.format(triggerMs));
        mIntent.putExtras(bundle);
        pIntent = PendingIntent.getBroadcast(reactContext, requestId, mIntent, 0);
        alarmMgr.set(
            AlarmManager.RTC_WAKEUP,
            triggerMs,
            pIntent
        );
        Log.d(TAG, "đđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđ");
        Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber() + " setAlarm ");
        Log.d(TAG, "alarm #: " + requestId);
        Log.d(TAG, "alarm in: " + triggerMs);
        Log.d(TAG, "alarm at: " + sdf.format(triggerMs));
        Log.d(TAG, "đđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđđ");
        Toast.makeText(
            reactContext,
            String.format("Alarm %s for %s set!", requestId, sdf.format(triggerMs)),
            Toast.LENGTH_LONG
        ).show();

    }

}