package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 6/2/2016.
 */
public class ConduitInsulation {

    private String insulationName;
    private ArrayList<ConduitConductorSize> conductorSizeList;

    public String getInsulationName() {
        return insulationName;
    }

    public void setInsulationName(String insulationName) {
        this.insulationName = insulationName;
    }

    public ArrayList<ConduitConductorSize> getConductorSizeList() {
        return conductorSizeList;
    }

    public void setConductorSizeList(ArrayList<ConduitConductorSize> conductorSizeList) {
        this.conductorSizeList = conductorSizeList;
    }
}
