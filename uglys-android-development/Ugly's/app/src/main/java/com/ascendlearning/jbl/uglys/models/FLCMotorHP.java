package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 6/8/2016.
 */
public class FLCMotorHP {

    private String hpName;
    private ArrayList<FLCMotorVoltageRating> voltageRatingArrayList;

    public String getHpName() {
        return hpName;
    }

    public void setHpName(String hpName) {
        this.hpName = hpName;
    }

    public ArrayList<FLCMotorVoltageRating> getVoltageRatingArrayList() {
        return voltageRatingArrayList;
    }

    public void setVoltageRatingArrayList(ArrayList<FLCMotorVoltageRating> voltageRatingArrayList) {
        this.voltageRatingArrayList = voltageRatingArrayList;
    }
}
