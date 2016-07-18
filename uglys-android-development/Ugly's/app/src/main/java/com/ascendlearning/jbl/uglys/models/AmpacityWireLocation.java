package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 4/29/2016.
 */
public class AmpacityWireLocation {
    private String ampLocation;
    private ArrayList<AmpacityConductorType> conductorTypeArrayList;

    public String getAmpLocation() {
        return ampLocation;
    }

    public void setAmpLocation(String ampLocation) {
        this.ampLocation = ampLocation;
    }

    public ArrayList<AmpacityConductorType> getConductorTypeArrayList() {
        return conductorTypeArrayList;
    }

    public void setConductorTypeArrayList(ArrayList<AmpacityConductorType> conductorTypeArrayList) {
        this.conductorTypeArrayList = conductorTypeArrayList;
    }
}
