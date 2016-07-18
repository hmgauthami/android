package com.ascendlearning.jbl.uglys.controllers;


import com.ascendlearning.jbl.uglys.models.Ampacity;
import com.ascendlearning.jbl.uglys.models.Bending;
import com.ascendlearning.jbl.uglys.models.Calculators;
import com.ascendlearning.jbl.uglys.models.ConduitFill;
import com.ascendlearning.jbl.uglys.models.ConduitInsulation;
import com.ascendlearning.jbl.uglys.models.Conversions;
import com.ascendlearning.jbl.uglys.models.FLCMotor;
import com.ascendlearning.jbl.uglys.models.FLCMotorOtherData;
import com.ascendlearning.jbl.uglys.models.FLCTransformer;
import com.ascendlearning.jbl.uglys.models.LRC;
import com.ascendlearning.jbl.uglys.models.OffsetBending;
import com.ascendlearning.jbl.uglys.models.PFCorrection;
import com.ascendlearning.jbl.uglys.models.Symbols;
import com.ascendlearning.jbl.uglys.models.ToFind;
import com.ascendlearning.jbl.uglys.models.Topics;
import com.ascendlearning.jbl.uglys.models.VoltageDrop;
import com.ascendlearning.jbl.uglys.models.WiringDiagrams;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 07-01-2016.
 */
public class UglysResponse {

    private ArrayList<Topics> topicsList;
    private ArrayList<Conversions> conversionsList;
    private ArrayList<Calculators> calculatorList;
    private ArrayList<Symbols> symbolList;
    private ArrayList<Bending> bendingList;
    private ArrayList<ToFind> toFindList;
    private ArrayList<Ampacity> ampacityList;
    private ArrayList<OffsetBending> offsetBendingArrayList;
    private ArrayList<PFCorrection> pfCorrectionArrayList;
    private ArrayList<ConduitFill> conduitfillArrayList;
    private ArrayList<ConduitInsulation> conduitfill1ArrayList;
    private ArrayList<FLCMotor> flcMotorArrayList;
    private ArrayList<FLCMotorOtherData> flcMotorOtherDataArrayList;
    private ArrayList<FLCTransformer> flcTransformerArrayList;
    private ArrayList<LRC> lrcArrayList;
    private ArrayList<VoltageDrop> voltageDropArrayList;
    private ArrayList<WiringDiagrams> wiringArrayList;

    public ArrayList<WiringDiagrams> getWiringArrayList() {
        return wiringArrayList;
    }

    public void setWiringArrayList(ArrayList<WiringDiagrams> wiringArrayList) {
        this.wiringArrayList = wiringArrayList;
    }

    public ArrayList<VoltageDrop> getVoltageDropArrayList() {
        return voltageDropArrayList;
    }

    public void setVoltageDropArrayList(ArrayList<VoltageDrop> voltageDropArrayList) {
        this.voltageDropArrayList = voltageDropArrayList;
    }

    public ArrayList<LRC> getLrcArrayList() {
        return lrcArrayList;
    }

    public void setLrcArrayList(ArrayList<LRC> lrcArrayList) {
        this.lrcArrayList = lrcArrayList;
    }

    public ArrayList<FLCTransformer> getFlcTransformerArrayList() {
        return flcTransformerArrayList;
    }

    public void setFlcTransformerArrayList(ArrayList<FLCTransformer> flcTransformerArrayList) {
        this.flcTransformerArrayList = flcTransformerArrayList;
    }

    public ArrayList<FLCMotorOtherData> getFlcMotorOtherDataArrayList() {
        return flcMotorOtherDataArrayList;
    }

    public void setFlcMotorOtherDataArrayList(ArrayList<FLCMotorOtherData> flcMotorOtherDataArrayList) {
        this.flcMotorOtherDataArrayList = flcMotorOtherDataArrayList;
    }

    public ArrayList<FLCMotor> getFlcMotorArrayList() {
        return flcMotorArrayList;
    }

    public void setFlcMotorArrayList(ArrayList<FLCMotor> flcMotorArrayList) {
        this.flcMotorArrayList = flcMotorArrayList;
    }

    public ArrayList<ConduitInsulation> getConduitfill1ArrayList() {
        return conduitfill1ArrayList;
    }

    public void setConduitfill1ArrayList(ArrayList<ConduitInsulation> conduitfill1ArrayList) {
        this.conduitfill1ArrayList = conduitfill1ArrayList;
    }

    public ArrayList<ConduitFill> getConduitfillArrayList() {
        return conduitfillArrayList;
    }

    public void setConduitfillArrayList(ArrayList<ConduitFill> conduitfillArrayList) {
        this.conduitfillArrayList = conduitfillArrayList;
    }

    public ArrayList<PFCorrection> getPfCorrectionArrayList() {
        return pfCorrectionArrayList;
    }

    public void setPfCorrectionArrayList(ArrayList<PFCorrection> pfCorrectionArrayList) {
        this.pfCorrectionArrayList = pfCorrectionArrayList;
    }

    public ArrayList<OffsetBending> getOffsetBendingArrayList() {
        return offsetBendingArrayList;
    }

    public void setOffsetBendingArrayList(ArrayList<OffsetBending> offsetBendingArrayList) {
        this.offsetBendingArrayList = offsetBendingArrayList;
    }

    public ArrayList<Ampacity> getAmpacityList() {
        return ampacityList;
    }

    public void setAmpacityList(ArrayList<Ampacity> ampacityList) {
        this.ampacityList = ampacityList;
    }

    public ArrayList<ToFind> getToFindList() {
        return toFindList;
    }

    public void setToFindList(ArrayList<ToFind> toFindList) {
        this.toFindList = toFindList;
    }

    public ArrayList<Bending> getBendingList() {
        return bendingList;
    }

    public void setBendingList(ArrayList<Bending> bendingList) {
        this.bendingList = bendingList;
    }

    public ArrayList<Symbols> getSymbolList() {
        return symbolList;
    }

    public void setSymbolList(ArrayList<Symbols> symbolList) {
        this.symbolList = symbolList;
    }

    public ArrayList<Calculators> getCalculatorList() {
        return calculatorList;
    }

    public void setCalculatorList(ArrayList<Calculators> calculatorList) {
        this.calculatorList = calculatorList;
    }

    public ArrayList<Conversions> getConversionsList() {
        return conversionsList;
    }

    public void setConversionsList(ArrayList<Conversions> conversionsList) {
        this.conversionsList = conversionsList;
    }

    public ArrayList<Topics> getTopicsList() {
        return topicsList;
    }

    public void setTopicsList(ArrayList<Topics> topicsList) {
        this.topicsList = topicsList;
    }
}
