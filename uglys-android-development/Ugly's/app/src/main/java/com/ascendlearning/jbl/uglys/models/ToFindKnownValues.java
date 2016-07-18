package com.ascendlearning.jbl.uglys.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 4/15/2016.
 */
public class ToFindKnownValues implements Parcelable {

    private String knownValueName;
    private ArrayList<ToFindTypes> findTypesArrayList;
    private ArrayList<String> inputValues;

    public ToFindKnownValues() {

    }

    public ToFindKnownValues(Parcel in) {
        knownValueName = in.readString();
        findTypesArrayList = in.readArrayList(ToFindKnownValues.class.getClassLoader());
        inputValues = in.readArrayList(ToFindKnownValues.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(knownValueName);
        dest.writeList(findTypesArrayList);
        dest.writeList(inputValues);
    }

    public static final Creator CREATOR = new Creator() {
        public ToFindKnownValues createFromParcel(Parcel in) {
            return new ToFindKnownValues(in);
        }

        public ToFindKnownValues[] newArray(int size) {
            return new ToFindKnownValues[size];
        }
    };

    public String getKnownValueName() {
        return knownValueName;
    }

    public void setKnownValueName(String knownValueName) {
        this.knownValueName = knownValueName;
    }

    public ArrayList<ToFindTypes> getFindTypesArrayList() {
        return findTypesArrayList;
    }

    public void setFindTypesArrayList(ArrayList<ToFindTypes> findTypesArrayList) {
        this.findTypesArrayList = findTypesArrayList;
    }

    public ArrayList<String> getInputValues() {
        return inputValues;
    }

    public void setInputValues(ArrayList<String> inputValues) {
        this.inputValues = inputValues;
    }
}
