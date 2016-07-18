package com.ascendlearning.jbl.uglys.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 3/28/2016.
 */
public class Topics implements Parcelable {
    private String topicName;
    private ArrayList<SubTopics> subTopicsArrayList;

    public Topics() {

    }

    public Topics(Parcel in) {
        topicName = in.readString();
        subTopicsArrayList = in.readArrayList(Topics.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(topicName);
        dest.writeList(subTopicsArrayList);
    }

    public static final Creator CREATOR = new Creator() {
        public Topics createFromParcel(Parcel in) {
            return new Topics(in);
        }

        public Topics[] newArray(int size) {
            return new Topics[size];
        }
    };

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public ArrayList<SubTopics> getSubTopicsArrayList() {
        return subTopicsArrayList;
    }

    public void setSubTopicsArrayList(ArrayList<SubTopics> subTopicsArrayList) {
        this.subTopicsArrayList = subTopicsArrayList;
    }
}
