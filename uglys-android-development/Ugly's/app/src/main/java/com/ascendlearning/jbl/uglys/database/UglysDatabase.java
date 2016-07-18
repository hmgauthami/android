package com.ascendlearning.jbl.uglys.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ascendlearning.jbl.uglys.models.Bookmarks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class UglysDatabase {
    private DBHelper opener;
    private SQLiteDatabase db;
    Context context;

    public UglysDatabase(Context context) {
        this.context = context;
        this.opener = new DBHelper(context);
        db = opener.getWritableDatabase();
    }

    class DBHelper extends SQLiteOpenHelper {
        public static final int version = 1;

        public String stringFromAssets(String fileName) {
            StringBuilder ReturnString = new StringBuilder();
            InputStream fIn = null;
            InputStreamReader isr = null;
            BufferedReader input = null;
            try {
                fIn = context.getResources().getAssets().open(fileName, Context.MODE_WORLD_READABLE);
                isr = new InputStreamReader(fIn);
                input = new BufferedReader(isr);
                String line = "";
                while ((line = input.readLine()) != null) {
                    ReturnString.append(line);
                }
            } catch (Exception e) {
                e.getMessage();
            } finally {
                try {
                    if (isr != null)
                        isr.close();
                    if (fIn != null)
                        fIn.close();
                    if (input != null)
                        input.close();
                } catch (Exception e2) {
                    e2.getMessage();
                }
            }
            return ReturnString.toString();
        }

        public DBHelper(Context context) {
            super(context, "Books.db", null, DBHelper.version);
        }

        // onCreate is called once if database not exists.
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w("EPub", "DB onCreate");
            db.execSQL(this.stringFromAssets("sql/bookmark.ddl"));
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public String getDateString() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    // Using db method
    public void insertBookmark(Bookmarks pi) {

        ContentValues values = new ContentValues();
        values.put("Description", pi.getBookmarkName());
        values.put("Type", pi.getBookmarkType());
        values.put("SubType", pi.getBookmarkCalType());
        values.put("ToFindKnownVal", pi.getBookmarkToFindKnown());
        values.put("ToFindCurrentType", pi.getBookmarkToFindCurrentType());
        values.put("BookmarkCode", pi.getBookmarkCode());
        values.put("ConversionTo", pi.getBookmarkConversionToSubtype());
        values.put("ConversionFrom", pi.getBookmarkConversionFromSubtype());
        db.insert("Bookmark", null, values);

    }


    public void deleteBookmarkByCode(int code) {
        String sql = String.format(Locale.US, "DELETE FROM Bookmark where Code = %d", code);
        db.execSQL(sql);
    }

    public int getBookmarkCodeBySubType(Bookmarks pi) {
        String selectSql = null;
        if (pi.getBookmarkToFindKnown() != -1 && pi.getBookmarkToFindCurrentType() != -1) {
            selectSql = String.format(Locale.US, "SELECT Code from Bookmark where BookmarkCode = %d and SubType=%d and ToFindKnownVal=%d and ToFindCurrentType=%d", pi.getBookmarkCode(), pi.getBookmarkCalType(), pi.getBookmarkToFindKnown(), pi.getBookmarkToFindCurrentType());
        } else if (pi.getBookmarkToFindKnown() == -1 && pi.getBookmarkToFindCurrentType() != -1) {
            selectSql = String.format(Locale.US, "SELECT Code from Bookmark where BookmarkCode = %d and SubType=%d and ToFindCurrentType=%d", pi.getBookmarkCode(), pi.getBookmarkCalType(), pi.getBookmarkToFindCurrentType());
        }else if (pi.getBookmarkConversionFromSubtype() != -1) {
            selectSql = String.format(Locale.US, "SELECT Code from Bookmark where BookmarkCode = %d and ConversionFrom=%d and ConversionTo=%d", pi.getBookmarkCode(), pi.getBookmarkConversionFromSubtype(), pi.getBookmarkConversionToSubtype());
        }else if (pi.getBookmarkToFindKnown() != -1 && pi.getBookmarkCalType() != -1) {
            selectSql = String.format(Locale.US, "SELECT Code from Bookmark where BookmarkCode = %d and SubType=%d and ToFindKnownVal=%d", pi.getBookmarkCode(), pi.getBookmarkCalType(), pi.getBookmarkToFindKnown());
        }else {
            selectSql = String.format(Locale.US, "SELECT Code from Bookmark where BookmarkCode = %d and SubType=%d", pi.getBookmarkCode(), pi.getBookmarkCalType());
        }
        Cursor cursor = db.rawQuery(selectSql, null);
        while (cursor.moveToNext()) {
            int code = cursor.getInt(0);
            return code;

        }
        cursor.close();
        return -1;
    }

    public ArrayList<Bookmarks> fetchBookmarks() {
        ArrayList<Bookmarks> pis = new ArrayList<Bookmarks>();
        String selectSql = String.format(Locale.US, "SELECT * from Bookmark");
        Cursor cursor = db.rawQuery(selectSql, null);
        while (cursor.moveToNext()) {
            Bookmarks pi = new Bookmarks();
            pi.setBookmarkName(cursor.getString(1));
            pi.setBookmarkType(cursor.getInt(2));
            pi.setBookmarkCalType(cursor.getInt(3));
            pi.setBookmarkToFindKnown(cursor.getInt(4));
            pi.setBookmarkToFindCurrentType(cursor.getInt(5));
            pi.setBookmarkCode(cursor.getInt(6));
            pi.setBookmarkConversionToSubtype(cursor.getInt(7));
            pi.setBookmarkConversionFromSubtype(cursor.getInt(8));
            pis.add(pi);
        }
        cursor.close();
        return pis;
    }

    public void toggleBookmark(Bookmarks pi) {
        int code = -1;

        code = this.getBookmarkCodeBySubType(pi);
        if (code == -1) { // if not exist
            this.insertBookmark(pi);

        } else {
            this.deleteBookmarkByCode(code); // if exist, delete it
        }
    }

    public boolean isBookmarked(Bookmarks pi) {
        int code = -1;
        code = this.getBookmarkCodeBySubType(pi);
        return code != -1;
    }


}
