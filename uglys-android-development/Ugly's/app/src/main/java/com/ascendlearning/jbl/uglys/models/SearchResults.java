package com.ascendlearning.jbl.uglys.models;

/**
 * Created by sonal.agarwal on 5/18/2016.
 */
public class SearchResults {

    private String topicName;
    private int resultType;
    private int calculatorType;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    public int getCalculatorType() {
        return calculatorType;
    }
    public void setCalculatorType(int calculatorType) {
        this.calculatorType = calculatorType;
    }
}
