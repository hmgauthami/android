package com.ascendlearning.jbl.uglys.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ascendlearning.jbl.uglys.UglysApplication;

public class PrefrenceUtil {

	public static boolean contains(String key) {
		boolean deleted = false;
		SharedPreferences prefs = UglysApplication.getAppContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
		deleted = prefs.contains(key);
		return deleted;
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		boolean deleted = false;
		SharedPreferences prefs = UglysApplication.getAppContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
		deleted = prefs.getBoolean(key, defaultValue);
		return deleted;
	}
	
	public static void setBoolean(String key, boolean value) {
		SharedPreferences prefs = UglysApplication.getAppContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static long getLong(String key, long defaultValue) {
		long value = 0;
		SharedPreferences prefs = UglysApplication.getAppContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
		value = prefs.getLong(key, defaultValue);
		return value;
	}
	
	public static void setLong(String key, long value) {
		SharedPreferences prefs = UglysApplication.getAppContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public static String getString(String key, String defaultValue) {
		String value = "";
		SharedPreferences prefs = UglysApplication.getAppContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
		value = prefs.getString(key, defaultValue);
		return value;
	}
	
	public static void setString(String key, String value) {
		SharedPreferences prefs = UglysApplication.getAppContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static int getInt(String key, int defaultValue) {
		int value = 0;
		SharedPreferences prefs = UglysApplication.getAppContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
		value = prefs.getInt(key, defaultValue);
		return value;
	}
	
	public static void setInt(String key, int value) {
		SharedPreferences prefs = UglysApplication.getAppContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong(key, value);
		editor.commit();
	}

}