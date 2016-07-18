package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 7/11/2016.
 */
public class WiringDiagrams {

    private String poles;
    private ArrayList<WiringDiagramWires> wiresArrayList;

    public String getPoles() {
        return poles;
    }

    public void setPoles(String poles) {
        this.poles = poles;
    }

    public ArrayList<WiringDiagramWires> getWiresArrayList() {
        return wiresArrayList;
    }

    public void setWiresArrayList(ArrayList<WiringDiagramWires> wiresArrayList) {
        this.wiresArrayList = wiresArrayList;
    }
}
