package com.ascendlearning.jbl.uglys.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 3/29/2016.
 */
public class SubsubTopics implements Parcelable {
    private String subSubTopicName;
    private String typeOfContent;
    private String getTypeOfCalculator;
    private int sub_calculator_id = -1;
    private ArrayList<ThirdLevelSubTopics> thirdLevelTopicsArrayList;

    public SubsubTopics() {

    }

    public SubsubTopics(Parcel in) {
        subSubTopicName = in.readString();
        typeOfContent = in.readString();
        getTypeOfCalculator = in.readString();
        sub_calculator_id = in.readInt();
        thirdLevelTopicsArrayList = in.readArrayList(SubsubTopics.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subSubTopicName);
        dest.writeString(typeOfContent);
        dest.writeString(getTypeOfCalculator);
        dest.writeInt(sub_calculator_id);
        dest.writeList(thirdLevelTopicsArrayList);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SubsubTopics createFromParcel(Parcel in) {
            return new SubsubTopics(in);
        }

        public SubsubTopics[] newArray(int size) {
            return new SubsubTopics[size];
        }
    };

    public String getSubSubTopicName() {
        return subSubTopicName;
    }

    public void setSubSubTopicName(String subSubTopicName) {
        this.subSubTopicName = subSubTopicName;
    }

    public ArrayList<ThirdLevelSubTopics> getThirdLevelTopicsArrayList() {
        return thirdLevelTopicsArrayList;
    }

    public void setThirdLevelTopicsArrayList(ArrayList<ThirdLevelSubTopics> thirdLevelTopicsArrayList) {
        this.thirdLevelTopicsArrayList = thirdLevelTopicsArrayList;
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
