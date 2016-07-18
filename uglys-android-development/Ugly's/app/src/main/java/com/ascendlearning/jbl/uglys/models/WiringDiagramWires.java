package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 7/11/2016.
 */
public class WiringDiagramWires {

    private String wiresName;
    private ArrayList<WiringDiagramGrounding> groundingTypeArrayList;

    public String getWiresName() {
        return wiresName;
    }

    public void setWiresName(String wiresName) {
        this.wiresName = wiresName;
    }

    public ArrayList<WiringDiagramGrounding> getGroundingTypeArrayList() {
        return groundingTypeArrayList;
    }

    public void setGroundingTypeArrayList(ArrayList<WiringDiagramGrounding> groundingTypeArrayList) {
        this.groundingTypeArrayList = groundingTypeArrayList;
    }
}
