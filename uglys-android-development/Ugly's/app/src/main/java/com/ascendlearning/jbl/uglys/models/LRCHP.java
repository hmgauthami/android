package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 6/24/2016.
 */
public class LRCHP {

    private String hp;
    private ArrayList<LRCVoltage> voltageArrayList;

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public ArrayList<LRCVoltage> getVoltageArrayList() {
        return voltageArrayList;
    }

    public void setVoltageArrayList(ArrayList<LRCVoltage> voltageArrayList) {
        this.voltageArrayList = voltageArrayList;
    }
}
