package com.ascendlearning.jbl.uglys.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sonal.agarwal on 3/30/2016.
 */
public class EpubUtils {
    Context context;

    public EpubUtils(Context context) {
        this.context = context;
    }

    public static String getFileName(String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
        return fileName;
    }

    private boolean isSetup() {
        SharedPreferences pref = this.context.getSharedPreferences("Uglys", 0);
        return pref.getBoolean("isSetup", false);
    }

    public synchronized String copyBookToDevice(String filePath, String targetName) {
        try {
            InputStream localInputStream = null;

            if (filePath.contains("asset")) {
                String fileName = EpubUtils.getFileName(filePath);
                localInputStream = context.getAssets().open("books/" + fileName);
            } else {
                localInputStream = new FileInputStream(filePath);
            }
            String bookDir = Settings.getStorageDirectory() + "/books";
            String path = bookDir + "/" + targetName;
            FileOutputStream localFileOutputStream = new FileOutputStream(path);
            byte[] arrayOfByte = new byte[1024];
            int offset;
            while ((offset = localInputStream.read(arrayOfByte)) > 0) {
                localFileOutputStream.write(arrayOfByte, 0, offset);
            }
            localFileOutputStream.flush();
            localFileOutputStream.close();
            localInputStream.close();
            return path;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            return null;
        }
    }


    public void makeSetup() {
        if (this.isSetup()) return ;

        if (!this.makeDirectory("books")) {
            debug("faild to make books directory");
        }

    }

    public void debug(String msg) {
        Log.d("Uglys", msg);
    }

    public boolean makeDirectory(String dirName) {
        boolean res;
        String filePath = new String(Settings.getStorageDirectory() + "/" + dirName);
        debug(filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            res = file.mkdirs();
        } else {
            res = false;
        }
        return res;
    }
}
