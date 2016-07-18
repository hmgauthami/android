package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 6/8/2016.
 */
public class FLCMotorTypes {

    private String flcTypeName;
    private ArrayList<FLCMotorHP> hpArrayList;

    public String getFlcTypeName() {
        return flcTypeName;
    }

    public void setFlcTypeName(String flcTypeName) {
        this.flcTypeName = flcTypeName;
    }

    public ArrayList<FLCMotorHP> getHpArrayList() {
        return hpArrayList;
    }

    public void setHpArrayList(ArrayList<FLCMotorHP> hpArrayList) {
        this.hpArrayList = hpArrayList;
    }
}
