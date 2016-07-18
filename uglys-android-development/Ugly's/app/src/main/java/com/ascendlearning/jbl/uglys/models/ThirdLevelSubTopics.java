package com.ascendlearning.jbl.uglys.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 3/28/2016.
 */
public class ThirdLevelSubTopics {
    private String subTopicName;
    private String typeOfContent;
    private String getTypeOfCalculator;
    private int sub_calculator_id =-1;
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
