package com.ascendlearning.jbl.uglys.models;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 6/2/2016.
 */
public class ConduitFill {

    private String conduitName;
    private ArrayList<ConduitTradeSize> tradeSizeArrayList;

    public String getConduitName() {
        return conduitName;
    }

    public void setConduitName(String conduitName) {
        this.conduitName = conduitName;
    }

    public ArrayList<ConduitTradeSize> getTradeSizeArrayList() {
        return tradeSizeArrayList;
    }

    public void setTradeSizeArrayList(ArrayList<ConduitTradeSize> tradeSizeArrayList) {
        this.tradeSizeArrayList = tradeSizeArrayList;
    }
}
