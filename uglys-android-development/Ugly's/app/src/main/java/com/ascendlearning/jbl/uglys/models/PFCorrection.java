package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 5/25/2016.
 */
public class PFCorrection {

    private int epf;
    private ArrayList<CorrectedPF> correctedPfArrayList;

    public int getEpf() {
        return epf;
    }

    public void setEpf(int epf) {
        this.epf = epf;
    }

    public ArrayList<CorrectedPF> getCorrectedPfArrayList() {
        return correctedPfArrayList;
    }

    public void setCorrectedPfArrayList(ArrayList<CorrectedPF> correctedPfArrayList) {
        this.correctedPfArrayList = correctedPfArrayList;
    }
}
