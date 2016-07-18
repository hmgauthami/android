package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 6/8/2016.
 */
public class FLCMotorOtherData {

    private String motorpower;
    private ArrayList<FLCMotorHpData> hpDataArrayList;

    public String getMotorpower() {
        return motorpower;
    }

    public void setMotorpower(String motorpower) {
        this.motorpower = motorpower;
    }

    public ArrayList<FLCMotorHpData> getHpDataArrayList() {
        return hpDataArrayList;
    }

    public void setHpDataArrayList(ArrayList<FLCMotorHpData> hpDataArrayList) {
        this.hpDataArrayList = hpDataArrayList;
    }
}
