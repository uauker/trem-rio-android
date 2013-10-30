package com.uauker.apps.tremrio.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesHelper {

    static String PREFERENCES_NAME;

    Context context;

    SharedPreferences sharedPreferences;

    private static SharedPreferencesHelper instance = null;

    public SharedPreferencesHelper(Context context) {
        this.context = context;

        PREFERENCES_NAME = this.getPackageName();
        this.sharedPreferences = this.context.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesHelper(context);
        }

        return instance;
    }

    public String getPackageName() {
        return this.context.getApplicationContext().getPackageName();
    }

    public String getString(String key, String defaultValue) {
        return this.sharedPreferences.getString(key, defaultValue);
    }

    public String getString(String key) {
        return this.getString(key, null);
    }

    public boolean setString(String key, String value) {
        Editor edit = this.sharedPreferences.edit();
        edit.putString(key, value);
        return edit.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.sharedPreferences.getBoolean(key, defaultValue);
    }

    public boolean setBoolean(String key, boolean value) {
        Editor edit = this.sharedPreferences.edit();
        edit.putBoolean(key, value);
        return edit.commit();
    }

    public boolean setFloat(String key, float value) {
        Editor edit = this.sharedPreferences.edit();
        edit.putFloat(key, value);
        return edit.commit();
    }

    public float getFloat(String key, float defaultValue) {
        return this.sharedPreferences.getFloat(key, defaultValue);
    }
}