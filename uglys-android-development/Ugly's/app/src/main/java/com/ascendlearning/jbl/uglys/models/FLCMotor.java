package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 6/8/2016.
 */
public class FLCMotor {

    private String flcName;
    private ArrayList<FLCMotorHP> hpArrayList;
    private ArrayList<FLCMotorTypes> flcMotorTypesArrayList;

    public String getFlcName() {
        return flcName;
    }

    public void setFlcName(String flcName) {
        this.flcName = flcName;
    }

    public ArrayList<FLCMotorHP> getHpArrayList() {
        return hpArrayList;
    }

    public void setHpArrayList(ArrayList<FLCMotorHP> hpArrayList) {
        this.hpArrayList = hpArrayList;
    }

    public ArrayList<FLCMotorTypes> getFlcMotorTypesArrayList() {
        return flcMotorTypesArrayList;
    }

    public void setFlcMotorTypesArrayList(ArrayList<FLCMotorTypes> flcMotorTypesArrayList) {
        this.flcMotorTypesArrayList = flcMotorTypesArrayList;
    }
}
