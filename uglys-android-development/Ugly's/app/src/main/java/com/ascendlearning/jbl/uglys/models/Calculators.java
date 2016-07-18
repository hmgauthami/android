package com.ascendlearning.jbl.uglys.models;

/**
 * Created by sonal.agarwal on 4/1/2016.
 */
public class Calculators {

    private String calculatorName;
    private int calculatorType;
    private int chapterIndex;
    private String hashLocation;

    public int getCalculatorType() {
        return calculatorType;
    }

    public void setCalculatorType(int calculatorType) {
        this.calculatorType = calculatorType;
    }

    public String getCalculatorName() {
        return calculatorName;
    }

    public void setCalculatorName(String calculatorName) {
        this.calculatorName = calculatorName;
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public String getHashLocation() {
        return hashLocation;
    }

    public void setHashLocation(String hashLocation) {
        this.hashLocation = hashLocation;
    }
}
