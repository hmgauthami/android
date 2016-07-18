package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 6/9/2016.
 */
public class FLCTransformer {

    private String flcName;
    private ArrayList<FLCTransformerKVA> kvaList;

    public String getFlcName() {
        return flcName;
    }

    public void setFlcName(String flcName) {
        this.flcName = flcName;
    }

    public ArrayList<FLCTransformerKVA> getKvaList() {
        return kvaList;
    }

    public void setKvaList(ArrayList<FLCTransformerKVA> kvaList) {
        this.kvaList = kvaList;
    }
}
