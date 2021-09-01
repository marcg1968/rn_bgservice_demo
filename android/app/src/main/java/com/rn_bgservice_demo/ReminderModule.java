package com.rn_bgservice_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Date;

import javax.annotation.Nonnull;

public class ReminderModule extends ReactContextBaseJavaModule {

    public static final String REACT_CLASS = "Reminder";
    private static final String TAG = "ReminderModule";
    private static ReactApplicationContext reactContext;

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
        this.reactContext.startService(new Intent(this.reactContext, ReminderService.class));
    }

    @ReactMethod
    public void stopService() {
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

    }

}
