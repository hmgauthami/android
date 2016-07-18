package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 6/21/2016.
 */
public class LRC {

    private String name;
    private ArrayList<LRCHP> hpArrayList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<LRCHP> getHpArrayList() {
        return hpArrayList;
    }

    public void setHpArrayList(ArrayList<LRCHP> hpArrayList) {
        this.hpArrayList = hpArrayList;
    }
}
