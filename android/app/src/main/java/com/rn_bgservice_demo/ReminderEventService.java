/**
 * This service loops in the background, passing any data received via an intent
 * to the React Native layer.
 * Requires the following invocation (usually in index.js):
 *      AppRegistry.registerHeadlessTask('ReminderService', () => BackgroundTask)
 * and the BackgroundTask.js file should have
 *      module.exports = async (taskData) => { ... }
 * where the variable "taskData" holds data passed from this service.
 */

package com.rn_bgservice_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

public class ReminderEventService extends HeadlessJsTaskService {
    private static final String TAG = "ReminderEventService";
    private static final long TIMEOUT = 5000;

    @Nullable
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        Bundle extras = intent.getExtras();

        WritableMap data = extras != null ? Arguments.fromBundle(extras): Arguments.createMap();

        if (extras != null && extras.keySet().size() > 0) {
            Log.d(TAG, "→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→");
            Log.d(TAG, Thread.currentThread().getStackTrace()[2].getLineNumber() + "");
            Log.d(TAG, "data: " + data);
            Log.d(TAG, "→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→→");
        }

        return new HeadlessJsTaskConfig(
            "ReminderService",
            data,
            TIMEOUT,
            true
        );
    }
}
