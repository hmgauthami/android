package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 4/29/2016.
 */
public class AmpacityWireInsulation {

    private String wireInsulationName;
    private ArrayList<AmpacityWireSize> ampacityWireSizeArrayList;

    public String getWireInsulationName() {
        return wireInsulationName;
    }

    public void setWireInsulationName(String wireInsulationName) {
        this.wireInsulationName = wireInsulationName;
    }

    public ArrayList<AmpacityWireSize> getAmpacityWireSizeArrayList() {
        return ampacityWireSizeArrayList;
    }

    public void setAmpacityWireSizeArrayList(ArrayList<AmpacityWireSize> ampacityWireSizeArrayList) {
        this.ampacityWireSizeArrayList = ampacityWireSizeArrayList;
    }
}
