package com.ascendlearning.jbl.uglys.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 4/15/2016.
 */
public class ToFindTypes implements Parcelable{
    private String toFindTypeName;
    private ArrayList<String> inputValues;
    public ToFindTypes() {

    }

    public ToFindTypes(Parcel in) {
        toFindTypeName = in.readString();
        inputValues = in.readArrayList(ToFindTypes.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(toFindTypeName);
        dest.writeList(inputValues);
    }

    public static final Creator CREATOR = new Creator() {
        public ToFindTypes createFromParcel(Parcel in) {
            return new ToFindTypes(in);
        }

        public ToFindTypes[] newArray(int size) {
            return new ToFindTypes[size];
        }
    };

    public String getToFindTypeName() {
        return toFindTypeName;
    }

    public void setToFindTypeName(String toFindTypeName) {
        this.toFindTypeName = toFindTypeName;
    }

    public ArrayList<String> getInputValues() {
        return inputValues;
    }

    public void setInputValues(ArrayList<String> inputValues) {
        this.inputValues = inputValues;
    }
}
