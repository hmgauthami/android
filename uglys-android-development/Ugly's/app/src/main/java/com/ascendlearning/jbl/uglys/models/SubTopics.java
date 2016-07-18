package com.ascendlearning.jbl.uglys.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 3/28/2016.
 */
public class SubTopics implements Parcelable {
    private String subTopicName;
    private String typeOfContent;
    private String getTypeOfCalculator;
    private int sub_calculator_id = -1;
    private ArrayList<SubsubTopics> subSubTopicsArrayList;

    public SubTopics() {

    }

    public SubTopics(Parcel in) {
        subTopicName = in.readString();
        typeOfContent = in.readString();
        getTypeOfCalculator = in.readString();
        sub_calculator_id = in.readInt();
        subSubTopicsArrayList = in.readArrayList(SubTopics.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subTopicName);
        dest.writeString(typeOfContent);
        dest.writeString(getTypeOfCalculator);
        dest.writeInt(sub_calculator_id);
        dest.writeList(subSubTopicsArrayList);
    }

    public static final Creator CREATOR = new Creator() {
        public SubTopics createFromParcel(Parcel in) {
            return new SubTopics(in);
        }

        public SubTopics[] newArray(int size) {
            return new SubTopics[size];
        }
    };

    public ArrayList<SubsubTopics> getSubSubTopicsArrayList() {
        return subSubTopicsArrayList;
    }

    public void setSubSubTopicsArrayList(ArrayList<SubsubTopics> subSubTopicsArrayList) {
        this.subSubTopicsArrayList = subSubTopicsArrayList;
    }

    public String getSubTopicName() {
        return subTopicName;
    }

    public void setSubTopicName(String subTopicName) {
        this.subTopicName = subTopicName;
    }

    public String getTypeOfContent() {
        return typeOfContent;
    }

    public void setTypeOfContent(String typeOfContent) {
        this.typeOfContent = typeOfContent;
    }

    public String getGetTypeOfCalculator() {
        return getTypeOfCalculator;
    }

    public void setGetTypeOfCalculator(String getTypeOfCalculator) {
        this.getTypeOfCalculator = getTypeOfCalculator;
    }

    public int getSub_calculator_id() {
        return sub_calculator_id;
    }

    public void setSub_calculator_id(int sub_calculator_id) {
        this.sub_calculator_id = sub_calculator_id;
    }
}
