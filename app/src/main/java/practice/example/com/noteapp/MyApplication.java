package practice.example.com.noteapp;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.parse.Parse;

/**
 * Created by mooqoo on 15/8/22.
 *
 * My First Parse App
 *
 * How to setup Parse in android studio
 * https://parse.com/apps/quickstart#parse_data/mobile/android/native/existing
 *
 * This app is based on the sample from:
 * http://www.sitepoint.com/creating-cloud-backend-android-app-using-parse/
 *
 */
public class MyApplication extends Application {
    public static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate()");

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "lI89AGwxowrp99KhkDxy3NJSRfyFhpRwGWLOYy1R", "frgoyJedoPNghxfoUAu5Vf9sLUF665WVys8YO20S");

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG,"onTerminate()");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG,"onConfigurationChanged(newConfig) newConfig="+newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG,"onLowMemory()");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i(TAG,"onTrimMemory(level) level="+level);
    }
}
