package com.ascendlearning.jbl.uglys.models;

/**
 * Created by sonal.agarwal on 3/31/2016.
 */
public class Bookmarks {

    private String bookmarkName;
    private int bookmarkType;
    private int bookmarkCode;
    private int bookmarkCalType = -1;
    private int bookmarkToFindKnown = -1;
    private int bookmarkToFindCurrentType = -1;
    private int bookmarkConversionToSubtype = -1;
    private int bookmarkConversionFromSubtype = -1;

    public int getBookmarkCalType() {
        return bookmarkCalType;
    }

    public void setBookmarkCalType(int bookmarkCalType) {
        this.bookmarkCalType = bookmarkCalType;
    }

    public int getBookmarkToFindKnown() {
        return bookmarkToFindKnown;
    }

    public void setBookmarkToFindKnown(int bookmarkToFindKnown) {
        this.bookmarkToFindKnown = bookmarkToFindKnown;
    }

    public int getBookmarkToFindCurrentType() {
        return bookmarkToFindCurrentType;
    }

    public void setBookmarkToFindCurrentType(int bookmarkToFindCurrentType) {
        this.bookmarkToFindCurrentType = bookmarkToFindCurrentType;
    }

    public String getBookmarkName() {
        return bookmarkName;
    }

    public void setBookmarkName(String bookmarkName) {
        this.bookmarkName = bookmarkName;
    }

    public int getBookmarkType() {
        return bookmarkType;
    }

    public void setBookmarkType(int bookmarkType) {
        this.bookmarkType = bookmarkType;
    }

    public int getBookmarkCode() {
        return bookmarkCode;
    }

    public void setBookmarkCode(int bookmarkCode) {
        this.bookmarkCode = bookmarkCode;
    }
    public int getBookmarkConversionToSubtype() {
        return bookmarkConversionToSubtype;
    }

    public void setBookmarkConversionToSubtype(int bookmarkConversionToSubtype) {
        this.bookmarkConversionToSubtype = bookmarkConversionToSubtype;
    }

    public int getBookmarkConversionFromSubtype() {
        return bookmarkConversionFromSubtype;
    }

    public void setBookmarkConversionFromSubtype(int bookmarkConversionFromSubtype) {
        this.bookmarkConversionFromSubtype = bookmarkConversionFromSubtype;
    }
}
