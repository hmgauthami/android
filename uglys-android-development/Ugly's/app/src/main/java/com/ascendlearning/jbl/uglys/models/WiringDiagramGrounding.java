package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 7/11/2016.
 */
public class WiringDiagramGrounding {

    private String groundingType;
    private ArrayList<WiringDiagramVoltage> voltageArrayList;

    public String getGroundingType() {
        return groundingType;
    }

    public void setGroundingType(String groundingType) {
        this.groundingType = groundingType;
    }

    public ArrayList<WiringDiagramVoltage> getVoltageArrayList() {
        return voltageArrayList;
    }

    public void setVoltageArrayList(ArrayList<WiringDiagramVoltage> voltageArrayList) {
        this.voltageArrayList = voltageArrayList;
    }
}
