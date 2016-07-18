package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 4/29/2016.
 */
public class Ampacity {

    private String ambientTemp;
    private ArrayList<AmpacityWireLocation> ampacityWireLocationArrayList;

    public String getAmbientTemp() {
        return ambientTemp;
    }

    public void setAmbientTemp(String ambientTemp) {
        this.ambientTemp = ambientTemp;
    }

    public ArrayList<AmpacityWireLocation> getAmpacityWireLocationArrayList() {
        return ampacityWireLocationArrayList;
    }

    public void setAmpacityWireLocationArrayList(ArrayList<AmpacityWireLocation> ampacityWireLocationArrayList) {
        this.ampacityWireLocationArrayList = ampacityWireLocationArrayList;
    }
}
