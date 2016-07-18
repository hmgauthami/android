package com.ascendlearning.jbl.uglys.utils;

public class Settings {
    public String fontName;
    public int fontSize;

    public static String storageDirectory = null;

    public static String getStorageDirectory() {
        return storageDirectory;
    }

    public static void setStorageDirectory(String directory, String appName) {
        storageDirectory = directory + "/" + appName;
    }
}
