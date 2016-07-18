package com.ascendlearning.jbl.uglys;

import android.app.Application;
import android.content.Context;

import com.ascendlearning.jbl.uglys.database.UglysDatabase;
import com.ascendlearning.jbl.uglys.utils.Constants;
import com.ascendlearning.jbl.uglys.utils.EpubUtils;
import com.ascendlearning.jbl.uglys.utils.PrefrenceUtil;
import com.ascendlearning.jbl.uglys.utils.Settings;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import org.readium.sdk.android.Container;
import org.readium.sdk.android.EPub3;
import org.readium.sdk.android.Package;
import org.readium.sdk.android.components.navigation.NavigationElement;
import org.readium.sdk.android.components.navigation.NavigationTable;
import org.readium.sdk.android.launcher.ContainerHolder;
import org.readium.sdk.android.launcher.model.BookmarkDatabase;

import java.util.List;

/**
 * Created by sonal.agarwal on 3/16/2016.
 */

public class UglysApplication extends Application {
    private static UglysApplication mInstance = null;
    private EpubUtils utils;
    private Package pckg;
    public NavigationTable navigationTable;
    public Container container;
    public UglysDatabase sd;
    public int fontSize[] = {-1};

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        TextUtil.loadFonts(getAssets());

        utils = new EpubUtils(this);
        sd = new UglysDatabase(this);
        loadFontSize();
        if (Settings.getStorageDirectory() == null) {
            // All book related data will be stored /data/data/com....../files/appName/
            Settings.setStorageDirectory(getFilesDir().getAbsolutePath(), this.getApplicationName());
        }
        utils.makeSetup();
        BookmarkDatabase.initInstance(getApplicationContext());
        String path = utils.copyBookToDevice("file://android_asset/books/9781449690779.epub", EpubUtils.getFileName("file://android_asset/books/9781449690779.epub"));
        container = EPub3.openBook(path);

        ContainerHolder.getInstance().put(container.getNativePtr(), container);
        pckg = container.getDefaultPackage();
        // Loads the native lib and sets the path to use for cache
        EPub3.setCachePath(getCacheDir().getAbsolutePath());
        getNavigationTableContent();
    }

    public int[] getFontSize() {
        return fontSize;
    }

    public void setFontSize(int[] fontSize) {
        this.fontSize = fontSize;
    }

    public void loadFontSize() {
        setFontSize(new int[]{Integer.parseInt(PrefrenceUtil.getString(Constants.FONT_SIZE, Constants.DEFAULT_FONT))});
    }

    public void setNavigationTable(NavigationTable navigationTable) {
        this.navigationTable = navigationTable;
    }

    public NavigationTable getNavigationTable() {
        return this.navigationTable;
    }

    public List<NavigationElement> flatNavigationTable(NavigationElement parent,
                                                       List<NavigationElement> list) {
        for (NavigationElement ne : parent.getChildren()) {
            list.add(ne);
            flatNavigationTable(ne, list);
        }
        return list;
    }

    private String getApplicationName() {
        int stringId = this.getApplicationInfo().labelRes;
        return this.getString(stringId);
    }

    public void getNavigationTableContent() {
        navigationTable = null;
        if (pckg != null) {
            navigationTable = pckg.getTableOfContents();
        }
        setNavigationTable((navigationTable != null) ? navigationTable : new NavigationTable("toc", "", ""));

    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
