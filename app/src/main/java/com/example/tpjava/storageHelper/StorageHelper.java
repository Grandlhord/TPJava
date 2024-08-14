package com.example.tpjava.storageHelper;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageHelper {

        private static final String PREF_NAME = "app_preferences";
        private SharedPreferences sharedPreferences;
        private static SharedPreferences.Editor editor;

        // Constructor
        public StorageHelper(Context context) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }

        // Method to save a String value
        public  void  saveString(String key, String value) {
            editor.putString(key, value);
            editor.apply();
        }

        // Method to get a String value
        public String getString(String key, String defaultValue) {
            return sharedPreferences.getString(key, defaultValue);
        }

        // Method to save an integer value
        public void saveInt(String key, int value) {
            editor.putInt(key, value);
            editor.apply();
        }

        // Method to get an integer value
        public int getInt(String key, int defaultValue) {
            return sharedPreferences.getInt(key, defaultValue);
        }

        // Method to save a boolean value
        public void saveBoolean(String key, boolean value) {
            editor.putBoolean(key, value);
            editor.apply();
        }

        // Method to get a boolean value
        public boolean getBoolean(String key, boolean defaultValue) {
            return sharedPreferences.getBoolean(key, defaultValue);
        }

        // Method to save a float value
        public void saveFloat(String key, float value) {
            editor.putFloat(key, value);
            editor.apply();
        }

        // Method to get a float value
        public float getFloat(String key, float defaultValue) {
            return sharedPreferences.getFloat(key, defaultValue);
        }

        // Method to save a long value
        public void saveLong(String key, long value) {
            editor.putLong(key, value);
            editor.apply();
        }

        // Method to get a long value
        public long getLong(String key, long defaultValue) {
            return sharedPreferences.getLong(key, defaultValue);
        }

        // Method to remove a specific key-value pair
        public void removeKey(String key) {
            editor.remove(key);
            editor.apply();
        }

        // Method to clear all stored preferences
        public void clearPreferences() {
            editor.clear();
            editor.apply();
        }
    }

