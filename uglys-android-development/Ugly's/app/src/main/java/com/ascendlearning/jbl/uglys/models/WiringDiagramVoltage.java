package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 7/11/2016.
 */
public class WiringDiagramVoltage {

    private String voltage;
    private ArrayList<WiringDiagramPhases> phaseArrayList;

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public ArrayList<WiringDiagramPhases> getPhaseArrayList() {
        return phaseArrayList;
    }

    public void setPhaseArrayList(ArrayList<WiringDiagramPhases> phaseArrayList) {
        this.phaseArrayList = phaseArrayList;
    }
}
