package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 6/9/2016.
 */
public class FLCTransformerKVA {

    private int kvaValue;
    private ArrayList<FLCTransformerAmperes> amperesList;

    public int getKvaValue() {
        return kvaValue;
    }

    public void setKvaValue(int kvaValue) {
        this.kvaValue = kvaValue;
    }

    public ArrayList<FLCTransformerAmperes> getAmperesList() {
        return amperesList;
    }

    public void setAmperesList(ArrayList<FLCTransformerAmperes> amperesList) {
        this.amperesList = amperesList;
    }
}
