package com.ascendlearning.jbl.uglys.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 3/31/2016.
 */
public class Conversions implements Parcelable {

    private String conversionName;
    private String conversionId;
    private String conversionFrom;
    private String conversionTo;
    private ArrayList<ConversionParameters> paramList;

    public Conversions() {

    }

    public Conversions(Parcel in) {
        conversionName = in.readString();
        conversionId = in.readString();
        conversionFrom = in.readString();
        conversionTo = in.readString();
        paramList = in.readArrayList(Conversions.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(conversionName);
        dest.writeString(conversionId);
        dest.writeString(conversionFrom);
        dest.writeString(conversionTo);
        dest.writeList(paramList);
    }

    public static final Creator CREATOR = new Creator() {
        public Conversions createFromParcel(Parcel in) {
            return new Conversions(in);
        }

        public Conversions[] newArray(int size) {
            return new Conversions[size];
        }
    };

    public ArrayList<ConversionParameters> getParamList() {
        return paramList;
    }

    public void setParamList(ArrayList<ConversionParameters> paramList) {
        this.paramList = paramList;
    }

    public String getConversionName() {
        return conversionName;
    }

    public void setConversionName(String conversionName) {
        this.conversionName = conversionName;
    }

    public String getConversionId() {
        return conversionId;
    }

    public void setConversionId(String conversionId) {
        this.conversionId = conversionId;
    }

    public String getConversionFrom() {
        return conversionFrom;
    }

    public void setConversionFrom(String conversionFrom) {
        this.conversionFrom = conversionFrom;
    }

    public String getConversionTo() {
        return conversionTo;
    }

    public void setConversionTo(String conversionTo) {
        this.conversionTo = conversionTo;
    }
}
