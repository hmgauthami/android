package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 4/29/2016.
 */
public class AmpacityConductorType {

    private String conductorTypeName;
    private ArrayList<AmpacityWireInsulation> ampacityWireInsulationArrayList;

    public String getConductorTypeName() {
        return conductorTypeName;
    }

    public void setConductorTypeName(String conductorTypeName) {
        this.conductorTypeName = conductorTypeName;
    }

    public ArrayList<AmpacityWireInsulation> getAmpacityWireInsulationArrayList() {
        return ampacityWireInsulationArrayList;
    }

    public void setAmpacityWireInsulationArrayList(ArrayList<AmpacityWireInsulation> ampacityWireInsulationArrayList) {
        this.ampacityWireInsulationArrayList = ampacityWireInsulationArrayList;
    }
}
