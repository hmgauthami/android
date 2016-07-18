package com.ascendlearning.jbl.uglys.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 4/15/2016.
 */
public class ToFind implements Parcelable {
    private String findName;
    private ArrayList<ToFindKnownValues> knownValues;
    private ArrayList<String> inputValues;
    private String type;
    private String symbol;

    public ToFind() {

    }

    public ToFind(Parcel in) {
        findName = in.readString();
        type = in.readString();
        symbol = in.readString();
        knownValues = in.readArrayList(ToFind.class.getClassLoader());
        inputValues = in.readArrayList(ToFind.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(findName);
        dest.writeString(type);
        dest.writeString(symbol);
        dest.writeList(knownValues);
        dest.writeList(inputValues);
    }

    public static final Creator CREATOR = new Creator() {
        public ToFind createFromParcel(Parcel in) {
            return new ToFind(in);
        }

        public ToFind[] newArray(int size) {
            return new ToFind[size];
        }
    };

    public String getFindName() {
        return findName;
    }

    public void setFindName(String findName) {
        this.findName = findName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<ToFindKnownValues> getKnownValues() {
        return knownValues;
    }

    public void setKnownValues(ArrayList<ToFindKnownValues> knownValues) {
        this.knownValues = knownValues;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public ArrayList<String> getInputValues() {
        return inputValues;
    }

    public void setInputValues(ArrayList<String> inputValues) {
        this.inputValues = inputValues;
    }
}
