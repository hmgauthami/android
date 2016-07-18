package com.ascendlearning.jbl.uglys.controllers;

import com.ascendlearning.jbl.uglys.models.Ampacity;
import com.ascendlearning.jbl.uglys.models.AmpacityConductorType;
import com.ascendlearning.jbl.uglys.models.AmpacityWireInsulation;
import com.ascendlearning.jbl.uglys.models.AmpacityWireLocation;
import com.ascendlearning.jbl.uglys.models.AmpacityWireSize;
import com.ascendlearning.jbl.uglys.models.Bending;
import com.ascendlearning.jbl.uglys.models.Calculators;
import com.ascendlearning.jbl.uglys.models.ConduitConductorSize;
import com.ascendlearning.jbl.uglys.models.ConduitFill;
import com.ascendlearning.jbl.uglys.models.ConduitInsulation;
import com.ascendlearning.jbl.uglys.models.ConduitTradeSize;
import com.ascendlearning.jbl.uglys.models.ConversionParameters;
import com.ascendlearning.jbl.uglys.models.Conversions;
import com.ascendlearning.jbl.uglys.models.CorrectedPF;
import com.ascendlearning.jbl.uglys.models.FLCMotor;
import com.ascendlearning.jbl.uglys.models.FLCMotorHP;
import com.ascendlearning.jbl.uglys.models.FLCMotorHpData;
import com.ascendlearning.jbl.uglys.models.FLCMotorOtherData;
import com.ascendlearning.jbl.uglys.models.FLCMotorTypes;
import com.ascendlearning.jbl.uglys.models.FLCMotorVoltageRating;
import com.ascendlearning.jbl.uglys.models.FLCTransformer;
import com.ascendlearning.jbl.uglys.models.FLCTransformerAmperes;
import com.ascendlearning.jbl.uglys.models.FLCTransformerKVA;
import com.ascendlearning.jbl.uglys.models.LRC;
import com.ascendlearning.jbl.uglys.models.LRCHP;
import com.ascendlearning.jbl.uglys.models.LRCVoltage;
import com.ascendlearning.jbl.uglys.models.OffsetBending;
import com.ascendlearning.jbl.uglys.models.PFCorrection;
import com.ascendlearning.jbl.uglys.models.SubTopics;
import com.ascendlearning.jbl.uglys.models.SubsubTopics;
import com.ascendlearning.jbl.uglys.models.Symbols;
import com.ascendlearning.jbl.uglys.models.ThirdLevelSubTopics;
import com.ascendlearning.jbl.uglys.models.ToFind;
import com.ascendlearning.jbl.uglys.models.ToFindKnownValues;
import com.ascendlearning.jbl.uglys.models.ToFindTypes;
import com.ascendlearning.jbl.uglys.models.Topics;
import com.ascendlearning.jbl.uglys.models.VoltageDrop;
import com.ascendlearning.jbl.uglys.models.WiringDiagramGrounding;
import com.ascendlearning.jbl.uglys.models.WiringDiagramPhases;
import com.ascendlearning.jbl.uglys.models.WiringDiagramVoltage;
import com.ascendlearning.jbl.uglys.models.WiringDiagramWires;
import com.ascendlearning.jbl.uglys.models.WiringDiagrams;
import com.ascendlearning.jbl.uglys.utils.CompositeKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 07-01-2016.
 */
public class UglysController extends DataController {
    public static final String FILTER_TOPICS = "topics";
    public static final String FILTER_CONVERSIONS = "conversions";
    public static final String FILTER_CALCULATORS = "calculators";
    public static final String FILTER_SYMBOLS = "symbols";
    public static final String FILTER_BENDING = "bending";
    public static final String FILTER_TO_FIND1 = "to_find1";
    public static final String FILTER_TO_FIND2 = "to_find2";
    public static final String FILTER_AMPACITY = "ampacity";
    public static final String FILTER_OFFSET_BENDING = "offset_bending";
    public static final String FILTER_PF_CORRECTION = "pf_correction";
    public static final String FILTER_CONDUIT_FILL = "conduit_fill";
    public static final String FILTER_CONDUIT_FILL1 = "conduit_fill1";
    public static final String FILTER_FLC_MOTOR_CALCULATOR = "flc_motor_calculator";
    public static final String FILTER_FLC_MOTOR_OTHER_DATA_CALCULATOR = "flc_motor_other_data_calculator";
    public static final String FILTER_FLC_TRANSFORMER_CALCULATOR = "flc_transformer_calculator";
    public static final String FILTER_LRC_CALCULATOR = "lrc_calculator";
    public static final String FILTER_VOLTAGE_DROP_CALCULATOR = "vr_calculator";
    public static final String FILTER_WIRING_DIAGRAMS_INTERACTIVES = "wiring_diagrams";

    UglysResponse mDataResponse;

    @Override
    public String getUrlForType(CompositeKey requestKey) {
        if (requestKey != null) {
            if (requestKey.getFilter().equals(FILTER_TOPICS)) {
                return "json/topics.json";
            } else if (requestKey.getFilter().equals(FILTER_CONVERSIONS)) {
                return "json/conversions.json";
            } else if (requestKey.getFilter().equals(FILTER_CALCULATORS)) {
                return "json/calculators.json";
            } else if (requestKey.getFilter().equals(FILTER_SYMBOLS)) {
                return "json/symbols.json";
            } else if (requestKey.getFilter().equals(FILTER_BENDING)) {
                return "json/bending.json";
            } else if (requestKey.getFilter().equals(FILTER_TO_FIND1)) {
                return "json/tofind1.json";
            } else if (requestKey.getFilter().equals(FILTER_TO_FIND2)) {
                return "json/tofind2.json";
            } else if (requestKey.getFilter().equals(FILTER_AMPACITY)) {
                return "json/ampacity.json";
            } else if (requestKey.getFilter().equals(FILTER_OFFSET_BENDING)) {
                return "json/offsetbending.json";
            } else if (requestKey.getFilter().equals(FILTER_PF_CORRECTION)) {
                return "json/pfcorrection.json";
            } else if (requestKey.getFilter().equals(FILTER_CONDUIT_FILL)) {
                return "json/conduitfill.json";
            } else if (requestKey.getFilter().equals(FILTER_CONDUIT_FILL1)) {
                return "json/conduitfilldimension.json";
            } else if (requestKey.getFilter().equals(FILTER_FLC_MOTOR_CALCULATOR)) {
                return "json/flc_motor_calculator.json";
            } else if (requestKey.getFilter().equals(FILTER_FLC_MOTOR_OTHER_DATA_CALCULATOR)) {
                return "json/flc_motor_other_calculator.json";
            } else if (requestKey.getFilter().equals(FILTER_FLC_TRANSFORMER_CALCULATOR)) {
                return "json/flc_transformer_calculator.json";
            } else if (requestKey.getFilter().equals(FILTER_LRC_CALCULATOR)) {
                return "json/lrc_calculator.json";
            } else if (requestKey.getFilter().equals(FILTER_VOLTAGE_DROP_CALCULATOR)) {
                return "json/voltage_drop_calculator.json";
            } else if (requestKey.getFilter().equals(FILTER_WIRING_DIAGRAMS_INTERACTIVES)) {
                return "json/wiring_diagrams.json";
            }
        }

        return null;
    }

    @Override
    public boolean handleData(String response) throws Exception {
        if (mCompositeKey != null) {
            if (mCompositeKey.getFilter().equals(FILTER_TOPICS)) {
                mDataResponse = parseTopicsData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_CONVERSIONS)) {
                mDataResponse = parseConversionsData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_CALCULATORS)) {
                mDataResponse = parseCalculatorData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_SYMBOLS)) {
                mDataResponse = parseSymbolData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_BENDING)) {
                mDataResponse = parseBendingData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_TO_FIND1)) {
                mDataResponse = parseToFind1Data(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_TO_FIND2)) {
                mDataResponse = parseToFind2Data(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_AMPACITY)) {
                mDataResponse = parseAmpacityData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_OFFSET_BENDING)) {
                mDataResponse = parseOffsetBendingData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_PF_CORRECTION)) {
                mDataResponse = parsePfCorrectionData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_CONDUIT_FILL)) {
                mDataResponse = parseConduitFillData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_CONDUIT_FILL1)) {
                mDataResponse = parseConduitFill1Data(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_FLC_MOTOR_CALCULATOR)) {
                mDataResponse = parseFLCMotorData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_FLC_MOTOR_OTHER_DATA_CALCULATOR)) {
                mDataResponse = parseFLCMotorOtherData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_FLC_TRANSFORMER_CALCULATOR)) {
                mDataResponse = parseFLCTransformerData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_LRC_CALCULATOR)) {
                mDataResponse = parseLRCData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_VOLTAGE_DROP_CALCULATOR)) {
                mDataResponse = parseVoltageDropData(response);
                return true;

            } else if (mCompositeKey.getFilter().equals(FILTER_WIRING_DIAGRAMS_INTERACTIVES)) {
                mDataResponse = parseWiringDiagramData(response);
                return true;

            }
        }
        return false;
    }

    private UglysResponse parseWiringDiagramData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray wiringDiagramJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject wiringDiagramJsonObject = jsonArray.getJSONObject(i);
                if (wiringDiagramJsonObject != null)
                    wiringDiagramJsonArray = wiringDiagramJsonObject.getJSONArray("Wiring Diagrams");
            }
            ArrayList<WiringDiagrams> wiringDiagramArrayList = new ArrayList<>();

            if (wiringDiagramJsonArray != null) {
                for (int i = 0; i < wiringDiagramJsonArray.length(); i++) {
                    JSONObject polesJsonObject = wiringDiagramJsonArray.getJSONObject(i);
                    JSONArray polesJsonArray = polesJsonObject.getJSONArray("Poles");
                    for (int j = 0; j < polesJsonArray.length(); j++) {
                        JSONObject wiresJsonObject = polesJsonArray.getJSONObject(j);
                        WiringDiagrams wiringDiagramObj = new WiringDiagrams();
                        ArrayList<WiringDiagramWires> wiresArrayList = new ArrayList<>();
                        wiringDiagramObj.setPoles(wiresJsonObject.getString("name"));
                        JSONArray wiresJsonArray = wiresJsonObject.getJSONArray("wire");
                        for (int k = 0; k < wiresJsonArray.length(); k++) {
                            JSONObject groundJsonObject = wiresJsonArray.getJSONObject(k);
                            WiringDiagramWires wireObj = new WiringDiagramWires();
                            ArrayList<WiringDiagramGrounding> groundingTypeArrayList = new ArrayList<>();
                            wireObj.setWiresName(groundJsonObject.getString("name"));
                            JSONArray groundTypeJsonArray = groundJsonObject.getJSONArray("ground");
                            for (int l = 0; l < groundTypeJsonArray.length(); l++) {
                                JSONObject voltageJsonObject = groundTypeJsonArray.getJSONObject(l);
                                WiringDiagramGrounding groundingObj = new WiringDiagramGrounding();
                                ArrayList<WiringDiagramVoltage> voltageArrayList = new ArrayList<>();
                                groundingObj.setGroundingType(voltageJsonObject.getString("name"));
                                JSONArray voltageJsonArray = voltageJsonObject.getJSONArray("voltage");
                                for (int m = 0; m < voltageJsonArray.length(); m++) {
                                    JSONObject phaseJsonObject = voltageJsonArray.getJSONObject(m);
                                    WiringDiagramVoltage voltageObj = new WiringDiagramVoltage();
                                    ArrayList<WiringDiagramPhases> phaseArrayList = new ArrayList<>();
                                    voltageObj.setVoltage(phaseJsonObject.getString("name"));
                                    JSONArray phaseJsonArray = phaseJsonObject.getJSONArray("phase");
                                    for (int n = 0; n < phaseJsonArray.length(); n++) {
                                        JSONObject imageJsonObject = phaseJsonArray.getJSONObject(n);
                                        WiringDiagramPhases phaseObject = new WiringDiagramPhases();
                                        phaseObject.setPhase(imageJsonObject.getString("name"));
                                        phaseObject.setDiagramImageUrl(imageJsonObject.getString("image"));
                                        phaseArrayList.add(phaseObject);
                                    }
                                    voltageObj.setPhaseArrayList(phaseArrayList);
                                    voltageArrayList.add(voltageObj);
                                }
                                groundingObj.setVoltageArrayList(voltageArrayList);
                                groundingTypeArrayList.add(groundingObj);
                            }
                            wireObj.setGroundingTypeArrayList(groundingTypeArrayList);
                            wiresArrayList.add(wireObj);
                        }
                        wiringDiagramObj.setWiresArrayList(wiresArrayList);
                        wiringDiagramArrayList.add(wiringDiagramObj);
                    }
                }
            }
            dataResponse = new UglysResponse();
            dataResponse.setWiringArrayList(wiringDiagramArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataResponse;
    }

    private UglysResponse parseVoltageDropData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray voltageDropJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject voltageDropJsonObject = jsonArray.getJSONObject(i);
                if (voltageDropJsonObject != null)
                    voltageDropJsonArray = voltageDropJsonObject.getJSONArray("Voltage Drop");
            }
            ArrayList<VoltageDrop> voltageDropArrayList = new ArrayList<>();

            if (voltageDropJsonArray != null) {
                for (int i = 0; i < voltageDropJsonArray.length(); i++) {
                    JSONObject voltageDropJsonObject = voltageDropJsonArray.getJSONObject(i);
                    VoltageDrop voltageDropObject = new VoltageDrop();
                    voltageDropObject.setSize(voltageDropJsonObject.getString("size"));
                    voltageDropObject.setArea(voltageDropJsonObject.getLong("area"));
                    voltageDropArrayList.add(voltageDropObject);
                }
            }
            dataResponse = new UglysResponse();
            dataResponse.setVoltageDropArrayList(voltageDropArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataResponse;
    }

    private UglysResponse parseLRCData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray lrcJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject lrcJsonObject = jsonArray.getJSONObject(i);
                if (lrcJsonObject != null)
                    lrcJsonArray = lrcJsonObject.getJSONArray("LRC");
            }
            ArrayList<LRC> lrcArrayList = new ArrayList<>();

            if (lrcJsonArray != null) {
                for (int i = 0; i < lrcJsonArray.length(); i++) {
                    JSONObject lrcJsonObject = lrcJsonArray.getJSONObject(i);
                    LRC lrcObject = new LRC();
                    lrcObject.setName(lrcJsonObject.getString("name"));
                    JSONArray lrcHpJsonArray = lrcJsonObject.getJSONArray("values");
                    ArrayList<LRCHP> lrcHpArrayList = new ArrayList<>();
                    for (int j = 0; j < lrcHpJsonArray.length(); j++) {
                        JSONObject lrcHPJsonObject = lrcHpJsonArray.getJSONObject(j);
                        LRCHP hpObject = new LRCHP();
                        hpObject.setHp(lrcHPJsonObject.getString("hp"));
                        JSONArray lrcVoltageJsonArray = lrcHPJsonObject.getJSONArray("voltage");
                        ArrayList<LRCVoltage> lrcVoltageArrayList = new ArrayList<>();
                        for (int k = 0; k < lrcVoltageJsonArray.length(); k++) {
                            JSONObject lrcVoltageJsonObject = lrcVoltageJsonArray.getJSONObject(k);
                            LRCVoltage voltageObject = new LRCVoltage();
                            voltageObject.setVoltage(lrcVoltageJsonObject.getString("volts"));
                            voltageObject.setCurrent(lrcVoltageJsonObject.getString("current"));
                            lrcVoltageArrayList.add(voltageObject);
                        }
                        hpObject.setVoltageArrayList(lrcVoltageArrayList);
                        lrcHpArrayList.add(hpObject);
                    }
                    lrcObject.setHpArrayList(lrcHpArrayList);
                    lrcArrayList.add(lrcObject);
                }
            }
            dataResponse = new UglysResponse();
            dataResponse.setLrcArrayList(lrcArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataResponse;
    }

    private UglysResponse parseFLCTransformerData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray flcJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject flcJsonObject = jsonArray.getJSONObject(i);
                if (flcJsonObject != null)
                    flcJsonArray = flcJsonObject.getJSONArray("FLC Calculator");
            }
            ArrayList<FLCTransformer> flcArrayList = new ArrayList<>();

            for (int i = 0; i < flcJsonArray.length(); i++) {
                JSONObject flcJsonObject = flcJsonArray.getJSONObject(i);
                FLCTransformer transformerObject = new FLCTransformer();
                transformerObject.setFlcName(flcJsonObject.getString("name"));
                JSONArray flcKVAJsonArray = flcJsonObject.getJSONArray("KVA");
                ArrayList<FLCTransformerKVA> flcKVAArrayList = new ArrayList<>();
                for (int k = 0; k < flcKVAJsonArray.length(); k++) {
                    JSONObject flcKVAJsonObject = flcKVAJsonArray.getJSONObject(k);
                    FLCTransformerKVA kvaObject = new FLCTransformerKVA();
                    kvaObject.setKvaValue(flcKVAJsonObject.getInt("value"));
                    JSONArray flcAmpsJsonArray = flcKVAJsonObject.getJSONArray("amperes");
                    ArrayList<FLCTransformerAmperes> flcMotorAmpsArrayList = new ArrayList<>();
                    for (int l = 0; l < flcAmpsJsonArray.length(); l++) {
                        JSONObject flcDataJsonObject = flcAmpsJsonArray.getJSONObject(l);
                        FLCTransformerAmperes ampsObject = new FLCTransformerAmperes();
                        ampsObject.setVoltage(flcDataJsonObject.getInt("amps"));
                        ampsObject.setAmpsValue(flcDataJsonObject.getDouble("value"));
                        flcMotorAmpsArrayList.add(ampsObject);
                    }
                    kvaObject.setAmperesList(flcMotorAmpsArrayList);
                    flcKVAArrayList.add(kvaObject);
                }
                transformerObject.setKvaList(flcKVAArrayList);
                flcArrayList.add(transformerObject);
            }
            dataResponse = new UglysResponse();
            dataResponse.setFlcTransformerArrayList(flcArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private UglysResponse parseFLCMotorOtherData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray flcJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject flcJsonObject = jsonArray.getJSONObject(i);
                if (flcJsonObject != null)
                    flcJsonArray = flcJsonObject.getJSONArray("FLC Calculator");
            }
            ArrayList<FLCMotorOtherData> flcArrayList = new ArrayList<>();

            for (int i = 0; i < flcJsonArray.length(); i++) {
                JSONObject flcJsonObject = flcJsonArray.getJSONObject(i);
                JSONArray flcHPJsonArray = flcJsonObject.getJSONArray("HP");
                for (int k = 0; k < flcHPJsonArray.length(); k++) {
                    JSONObject flcHPJsonObject = flcHPJsonArray.getJSONObject(k);
                    FLCMotorOtherData hpObject = new FLCMotorOtherData();
                    hpObject.setMotorpower(flcHPJsonObject.getString("name"));
                    JSONArray flcotherdataJsonArray = flcHPJsonObject.getJSONArray("values");
                    ArrayList<FLCMotorHpData> flcMotorHPArrayList = new ArrayList<>();
                    for (int l = 0; l < flcotherdataJsonArray.length(); l++) {
                        JSONObject flcDataJsonObject = flcotherdataJsonArray.getJSONObject(l);
                        FLCMotorHpData hpDataObject = new FLCMotorHpData();
                        hpDataObject.setHp(flcDataJsonObject.getString("name"));
                        hpDataObject.setMotor_ampere(flcDataJsonObject.getString("motor_ampere"));
                        hpDataObject.setWire_size(flcDataJsonObject.getString("wire_size"));
                        hpDataObject.setBreaker_size(flcDataJsonObject.getInt("breaker_size"));
                        hpDataObject.setStarter_size(flcDataJsonObject.getString("starter_size"));
                        hpDataObject.setHeater_amps(flcDataJsonObject.getString("heater_amps"));
                        hpDataObject.setConduit_size(flcDataJsonObject.getString("conduit_size"));
                        flcMotorHPArrayList.add(hpDataObject);
                    }
                    hpObject.setHpDataArrayList(flcMotorHPArrayList);
                    flcArrayList.add(hpObject);
                }
            }
            dataResponse = new UglysResponse();
            dataResponse.setFlcMotorOtherDataArrayList(flcArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private UglysResponse parseFLCMotorData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray flcJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject flcJsonObject = jsonArray.getJSONObject(i);
                if (flcJsonObject != null)
                    flcJsonArray = flcJsonObject.getJSONArray("FLC Calculator");
            }
            ArrayList<FLCMotor> flcArrayList = new ArrayList<>();

            for (int i = 0; i < flcJsonArray.length(); i++) {
                JSONObject flcJsonObject = flcJsonArray.getJSONObject(i);
                FLCMotor flcObject = new FLCMotor();
                flcObject.setFlcName(flcJsonObject.getString("name"));
                ArrayList<FLCMotorTypes> flcMotorTypesArrayList = new ArrayList<>();
                ArrayList<FLCMotorHP> flcMotorHPArrayList = new ArrayList<>();
                if (flcJsonObject.has("type")) {
                    JSONArray flcTypeJsonArray = flcJsonObject.getJSONArray("type");
                    for (int j = 0; j < flcTypeJsonArray.length(); j++) {
                        JSONObject flcTypesJsonObject = flcTypeJsonArray.getJSONObject(j);
                        FLCMotorTypes typesObject = new FLCMotorTypes();
                        typesObject.setFlcTypeName(flcTypesJsonObject.getString("name"));
                        JSONArray flcHPJsonArray = flcTypesJsonObject.getJSONArray("HP");
                        ArrayList<FLCMotorHP> flcMotorHPArrayList1 = new ArrayList<>();
                        for (int k = 0; k < flcHPJsonArray.length(); k++) {
                            JSONObject flcHPJsonObject = flcHPJsonArray.getJSONObject(k);
                            FLCMotorHP hpObject = new FLCMotorHP();
                            hpObject.setHpName(flcHPJsonObject.getString("name"));
                            JSONArray flcarmatureJsonArray = flcHPJsonObject.getJSONArray("Armature Voltage Rating");
                            ArrayList<FLCMotorVoltageRating> flcVoltageArrayList = new ArrayList<>();
                            for (int l = 0; l < flcarmatureJsonArray.length(); l++) {
                                JSONObject flcVoltageJsonObject = flcarmatureJsonArray.getJSONObject(l);
                                FLCMotorVoltageRating voltageObject = new FLCMotorVoltageRating();
                                voltageObject.setName(flcVoltageJsonObject.getString("name"));
                                voltageObject.setValue(flcVoltageJsonObject.getDouble("value"));
                                flcVoltageArrayList.add(voltageObject);
                            }
                            flcMotorHPArrayList1.add(hpObject);
                            hpObject.setVoltageRatingArrayList(flcVoltageArrayList);
                        }
                        typesObject.setHpArrayList(flcMotorHPArrayList1);
                        flcMotorTypesArrayList.add(typesObject);
                    }
                } else {
                    JSONArray flcHPJsonArray = flcJsonObject.getJSONArray("HP");
                    for (int k = 0; k < flcHPJsonArray.length(); k++) {
                        JSONObject flcHPJsonObject = flcHPJsonArray.getJSONObject(k);
                        FLCMotorHP hpObject = new FLCMotorHP();
                        hpObject.setHpName(flcHPJsonObject.getString("name"));
                        JSONArray flcarmatureJsonArray = flcHPJsonObject.getJSONArray("Armature Voltage Rating");
                        ArrayList<FLCMotorVoltageRating> flcVoltageArrayList = new ArrayList<>();
                        for (int l = 0; l < flcarmatureJsonArray.length(); l++) {
                            JSONObject flcVoltageJsonObject = flcarmatureJsonArray.getJSONObject(l);
                            FLCMotorVoltageRating voltageObject = new FLCMotorVoltageRating();
                            voltageObject.setName(flcVoltageJsonObject.getString("name"));
                            voltageObject.setValue(flcVoltageJsonObject.getDouble("value"));
                            flcVoltageArrayList.add(voltageObject);
                        }
                        flcMotorHPArrayList.add(hpObject);
                        hpObject.setVoltageRatingArrayList(flcVoltageArrayList);
                    }
                }
                flcObject.setFlcMotorTypesArrayList(flcMotorTypesArrayList);
                flcObject.setHpArrayList(flcMotorHPArrayList);
                flcArrayList.add(flcObject);
            }
            dataResponse = new UglysResponse();
            dataResponse.setFlcMotorArrayList(flcArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private UglysResponse parseConduitFill1Data(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray conduitfillJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject conduitFillJsonObject = jsonArray.getJSONObject(i);
                if (conduitFillJsonObject != null)
                    conduitfillJsonArray = conduitFillJsonObject.getJSONArray("Insulation Type");
            }
            ArrayList<ConduitInsulation> conduitInsulationArrayList = new ArrayList<>();

            for (int i = 0; i < conduitfillJsonArray.length(); i++) {
                JSONObject conduitFillJsonObject = conduitfillJsonArray.getJSONObject(i);
                ConduitInsulation conduitInsulationObject = new ConduitInsulation();
                conduitInsulationObject.setInsulationName(conduitFillJsonObject.getString("name"));
                JSONArray conductorSizeJsonArray = conduitFillJsonObject.getJSONArray("sizes");
                ArrayList<ConduitConductorSize> conductorSizeArrayList = new ArrayList<>();
                for (int l = 0; l < conductorSizeJsonArray.length(); l++) {
                    JSONObject tradeSizeJsonObject = conductorSizeJsonArray.getJSONObject(l);
                    ConduitConductorSize conductorSizeObject = new ConduitConductorSize();
                    conductorSizeObject.setConductorSize(tradeSizeJsonObject.getString("size"));
                    conductorSizeObject.setValue(tradeSizeJsonObject.getDouble("value"));
                    conductorSizeArrayList.add(conductorSizeObject);
                }
                conduitInsulationObject.setConductorSizeList(conductorSizeArrayList);
                conduitInsulationArrayList.add(conduitInsulationObject);
            }
            dataResponse = new UglysResponse();
            dataResponse.setConduitfill1ArrayList(conduitInsulationArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private UglysResponse parseConduitFillData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray conduitfillJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject conduitFillJsonObject = jsonArray.getJSONObject(i);
                if (conduitFillJsonObject != null)
                    conduitfillJsonArray = conduitFillJsonObject.getJSONArray("Conduit Fill");
            }
            ArrayList<ConduitFill> conduitFillArrayList = new ArrayList<>();

            for (int i = 0; i < conduitfillJsonArray.length(); i++) {
                JSONObject conduitFillJsonObject = conduitfillJsonArray.getJSONObject(i);
                ConduitFill conduitFillObject = new ConduitFill();
                conduitFillObject.setConduitName(conduitFillJsonObject.getString("name"));
                JSONArray tradeSizeJsonArray = conduitFillJsonObject.getJSONArray("trade size");
                ArrayList<ConduitTradeSize> tradeSizeArrayList = new ArrayList<>();
                for (int l = 0; l < tradeSizeJsonArray.length(); l++) {
                    JSONObject tradeSizeJsonObject = tradeSizeJsonArray.getJSONObject(l);
                    ConduitTradeSize tradeSizeObject = new ConduitTradeSize();
                    tradeSizeObject.setName(tradeSizeJsonObject.getString("name"));
                    tradeSizeObject.setNipple(tradeSizeJsonObject.getDouble("nipple"));
                    tradeSizeObject.setOneWire(tradeSizeJsonObject.getDouble("1 wire"));
                    tradeSizeObject.setTwoWire(tradeSizeJsonObject.getDouble("two wires"));
                    tradeSizeObject.setOverTwoWire(tradeSizeJsonObject.getDouble("over two wires"));
                    tradeSizeArrayList.add(tradeSizeObject);
                }
                conduitFillObject.setTradeSizeArrayList(tradeSizeArrayList);
                conduitFillArrayList.add(conduitFillObject);
            }
            dataResponse = new UglysResponse();
            dataResponse.setConduitfillArrayList(conduitFillArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private UglysResponse parsePfCorrectionData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray pfCorrectionJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject pfCorrectionJsonObject = jsonArray.getJSONObject(i);
                if (pfCorrectionJsonObject != null)
                    pfCorrectionJsonArray = pfCorrectionJsonObject.getJSONArray("PF Correction");
            }
            ArrayList<PFCorrection> pfCorrectionArrayList = new ArrayList<>();

            for (int i = 0; i < pfCorrectionJsonArray.length(); i++) {
                JSONObject pfCorrectionJsonObject = pfCorrectionJsonArray.getJSONObject(i);
                PFCorrection pfCorrectionObject = new PFCorrection();
                pfCorrectionObject.setEpf(pfCorrectionJsonObject.getInt("EPF"));
                JSONArray correctedPfJsonArray = pfCorrectionJsonObject.getJSONArray("CPF");
                ArrayList<CorrectedPF> correctedPfArrayList = new ArrayList<>();
                for (int j = 0; j < correctedPfJsonArray.length(); j++) {
                    JSONObject correctedJsonObject = correctedPfJsonArray.getJSONObject(j);
                    CorrectedPF correctedPfObject = new CorrectedPF();
                    correctedPfObject.setName(correctedJsonObject.getString("name"));
                    correctedPfObject.setValue(correctedJsonObject.getDouble("value"));
                    correctedPfArrayList.add(correctedPfObject);
                }
                pfCorrectionObject.setCorrectedPfArrayList(correctedPfArrayList);
                pfCorrectionArrayList.add(pfCorrectionObject);
            }
            dataResponse = new UglysResponse();
            dataResponse.setPfCorrectionArrayList(pfCorrectionArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private UglysResponse parseOffsetBendingData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray offsetBendingJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject offsetBendingJsonObject = jsonArray.getJSONObject(i);
                if (offsetBendingJsonObject != null)
                    offsetBendingJsonArray = offsetBendingJsonObject.getJSONArray("Offset Bending");
            }
            ArrayList<OffsetBending> offsetBendingArrayList = new ArrayList<>();

            for (int i = 0; i < offsetBendingJsonArray.length(); i++) {
                JSONObject offsetBendingJsonObject = offsetBendingJsonArray.getJSONObject(i);
                OffsetBending offsetBendingObject = new OffsetBending();
                offsetBendingObject.setAngle(offsetBendingJsonObject.getInt("angle"));
                offsetBendingObject.setMultiplier(offsetBendingJsonObject.getDouble("multiplier"));
                offsetBendingArrayList.add(offsetBendingObject);
            }
            dataResponse = new UglysResponse();
            dataResponse.setOffsetBendingArrayList(offsetBendingArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private UglysResponse parseAmpacityData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray ampacityJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ampacityJsonObject = jsonArray.getJSONObject(i);
                if (ampacityJsonObject != null)
                    ampacityJsonArray = ampacityJsonObject.getJSONArray("Ampacity");
            }
            ArrayList<Ampacity> ampacityArrayList = new ArrayList<>();
            if (ampacityArrayList != null) {
                for (int i = 0; i < ampacityJsonArray.length(); i++) {
                    JSONObject ampacityJsonObject = ampacityJsonArray.getJSONObject(i);
                    Ampacity ampacityObject = new Ampacity();
                    ampacityObject.setAmbientTemp(ampacityJsonObject.getString("Ambient Temperature"));
                    JSONArray wirelocactionJsonArray = ampacityJsonObject.getJSONArray("Wire Location");
                    ArrayList<AmpacityWireLocation> ampWireLocationArrayList = new ArrayList<>();
                    for (int j = 0; j < wirelocactionJsonArray.length(); j++) {
                        JSONObject ampWireLocJsonObject = wirelocactionJsonArray.getJSONObject(j);
                        AmpacityWireLocation ampWireLocObject = new AmpacityWireLocation();
                        ampWireLocObject.setAmpLocation(ampWireLocJsonObject.getString("name"));
                        ArrayList<AmpacityConductorType> ampConductorTypeArrayList = new ArrayList<>();
                        JSONArray ampConductorTypeJsonArray = ampWireLocJsonObject.getJSONArray("Conductor Type");
                        for (int l = 0; l < ampConductorTypeJsonArray.length(); l++) {
                            JSONObject ampConductorTypeJsonObject = ampConductorTypeJsonArray.getJSONObject(l);
                            AmpacityConductorType ampConductorTypeObject = new AmpacityConductorType();
                            ampConductorTypeObject.setConductorTypeName(ampConductorTypeJsonObject.getString("name"));
                            JSONArray ampwireInsulationJsonArray = ampConductorTypeJsonObject.getJSONArray("Wire Insulation");
                            ArrayList<AmpacityWireInsulation> ampwireInsulationArrayList = new ArrayList<>();
                            for (int m = 0; m < ampwireInsulationJsonArray.length(); m++) {
                                JSONObject ampwireInsulationJsonObject = ampwireInsulationJsonArray.getJSONObject(m);
                                AmpacityWireInsulation ampwireInsulationObject = new AmpacityWireInsulation();
                                ampwireInsulationObject.setWireInsulationName(ampwireInsulationJsonObject.getString("name"));
                                JSONArray ampwireSizeJsonArray = ampwireInsulationJsonObject.getJSONArray("Wire Size");
                                ArrayList<AmpacityWireSize> ampwiresizeArrayList = new ArrayList<>();
                                for (int n = 0; n < ampwireSizeJsonArray.length(); n++) {
                                    JSONObject ampwireSizeJsonObject = ampwireSizeJsonArray.getJSONObject(n);
                                    AmpacityWireSize ampwireSizeObject = new AmpacityWireSize();
                                    ampwireSizeObject.setAmpacity(ampwireSizeJsonObject.getString("ampacity"));
                                    ampwireSizeObject.setWireSize(ampwireSizeJsonObject.getString("name"));
                                    ampwiresizeArrayList.add(ampwireSizeObject);
                                }
                                ampwireInsulationObject.setAmpacityWireSizeArrayList(ampwiresizeArrayList);
                                ampwireInsulationArrayList.add(ampwireInsulationObject);
                            }
                            ampConductorTypeObject.setAmpacityWireInsulationArrayList(ampwireInsulationArrayList);
                            ampConductorTypeArrayList.add(ampConductorTypeObject);

                        }
                        ampWireLocObject.setConductorTypeArrayList(ampConductorTypeArrayList);
                        ampWireLocationArrayList.add(ampWireLocObject);
                    }
                    ampacityObject.setAmpacityWireLocationArrayList(ampWireLocationArrayList);
                    ampacityArrayList.add(ampacityObject);
                }
            }
            dataResponse = new UglysResponse();
            dataResponse.setAmpacityList(ampacityArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private UglysResponse parseToFind1Data(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray toFindJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject toFindJsonObject = jsonArray.getJSONObject(i);
                if (toFindJsonObject != null)
                    toFindJsonArray = toFindJsonObject.getJSONArray("ToFind");
            }
            ArrayList<ToFind> toFindArrayList = new ArrayList<>();
            if (toFindArrayList != null) {
                for (int i = 0; i < toFindJsonArray.length(); i++) {
                    JSONObject toFindJsonObject = toFindJsonArray.getJSONObject(i);
                    ToFind toFindObject = new ToFind();
                    toFindObject.setFindName(toFindJsonObject.getString("name"));
                    toFindObject.setType(toFindJsonObject.getString("type"));
                    toFindObject.setSymbol(toFindJsonObject.getString("symbol"));
                    if (toFindJsonObject.has("inputValues")) {
                        ArrayList<String> inputValuesArrayList = new ArrayList<>();
                        JSONArray inputValuesJsonArray = toFindJsonObject.getJSONArray("inputValues");
                        for (int l = 0; l < inputValuesJsonArray.length(); l++) {
                            inputValuesArrayList.add(inputValuesJsonArray.getString(l));
                        }
                        toFindObject.setInputValues(inputValuesArrayList);

                    }
                    if (toFindJsonObject.has("knownValue")) {
                        JSONArray knownValueJsonArray = toFindJsonObject.getJSONArray("knownValue");
                        ArrayList<ToFindKnownValues> toFindKnowValsArrayList = new ArrayList<>();
                        for (int j = 0; j < knownValueJsonArray.length(); j++) {
                            JSONObject knownValueJsonObject = knownValueJsonArray.getJSONObject(j);
                            ToFindKnownValues knownValueObject = new ToFindKnownValues();
                            knownValueObject.setKnownValueName(knownValueJsonObject.getString("name"));
                            if (knownValueJsonObject.has("inputValues")) {
                                ArrayList<String> inputValuesArrayList = new ArrayList<>();
                                JSONArray inputValuesJsonArray = knownValueJsonObject.getJSONArray("inputValues");
                                for (int l = 0; l < inputValuesJsonArray.length(); l++) {
                                    inputValuesArrayList.add(inputValuesJsonArray.getString(l));
                                }
                                knownValueObject.setInputValues(inputValuesArrayList);

                            }
                            if (knownValueJsonObject.has("types")) {
                                ArrayList<ToFindTypes> typesArrayList = new ArrayList<>();
                                JSONArray typesJsonArray = knownValueJsonObject.getJSONArray("types");
                                for (int k = 0; k < typesJsonArray.length(); k++) {
                                    JSONObject typeJsonObject = typesJsonArray.getJSONObject(k);
                                    ToFindTypes typeObject = new ToFindTypes();
                                    typeObject.setToFindTypeName(typeJsonObject.getString("name"));
                                    if (typeJsonObject.has("inputValues")) {
                                        ArrayList<String> inputValuesArrayList = new ArrayList<>();
                                        JSONArray inputValuesJsonArray = typeJsonObject.getJSONArray("inputValues");
                                        for (int l = 0; l < inputValuesJsonArray.length(); l++) {
                                            inputValuesArrayList.add(inputValuesJsonArray.getString(l));
                                        }
                                        typeObject.setInputValues(inputValuesArrayList);

                                    }
                                    typesArrayList.add(typeObject);
                                }
                                knownValueObject.setFindTypesArrayList(typesArrayList);
                            }
                            toFindKnowValsArrayList.add(knownValueObject);
                        }
                        toFindObject.setKnownValues(toFindKnowValsArrayList);
                    }
                    toFindArrayList.add(toFindObject);
                }
            }
            dataResponse = new UglysResponse();
            dataResponse.setToFindList(toFindArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private UglysResponse parseToFind2Data(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray toFindJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject toFindJsonObject = jsonArray.getJSONObject(i);
                if (toFindJsonObject != null)
                    toFindJsonArray = toFindJsonObject.getJSONArray("ToFind");
            }
            ArrayList<ToFind> toFindArrayList = new ArrayList<>();
            if (toFindArrayList != null) {
                for (int i = 0; i < toFindJsonArray.length(); i++) {
                    JSONObject toFindJsonObject = toFindJsonArray.getJSONObject(i);
                    ToFind toFindObject = new ToFind();
                    toFindObject.setFindName(toFindJsonObject.getString("name"));
                    toFindObject.setType(toFindJsonObject.getString("type"));
                    JSONArray typeOfJsonArray = toFindJsonObject.getJSONArray("typeOf");
                    ArrayList<ToFindKnownValues> toFindtypeOfArrayList = new ArrayList<>();
                    for (int j = 0; j < typeOfJsonArray.length(); j++) {
                        JSONObject knownValueJsonObject = typeOfJsonArray.getJSONObject(j);
                        ToFindKnownValues typeOfObject = new ToFindKnownValues();
                        typeOfObject.setKnownValueName(knownValueJsonObject.getString("name"));
                        if (knownValueJsonObject.has("inputValues")) {
                            ArrayList<String> inputValuesArrayList = new ArrayList<>();
                            JSONArray inputValuesJsonArray = knownValueJsonObject.getJSONArray("inputValues");
                            for (int l = 0; l < inputValuesJsonArray.length(); l++) {
                                inputValuesArrayList.add(inputValuesJsonArray.getString(l));
                            }
                            typeOfObject.setInputValues(inputValuesArrayList);

                        }
                        toFindtypeOfArrayList.add(typeOfObject);
                    }
                    toFindObject.setKnownValues(toFindtypeOfArrayList);

                    toFindArrayList.add(toFindObject);
                }
            }
            dataResponse = new UglysResponse();
            dataResponse.setToFindList(toFindArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private UglysResponse parseBendingData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray bendingJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bendingJsonObject = jsonArray.getJSONObject(i);
                if (bendingJsonObject != null)
                    bendingJsonArray = bendingJsonObject.getJSONArray("Bending");
            }
            ArrayList<Bending> bendingArrayList = new ArrayList<>();
            if (bendingArrayList != null) {
                for (int i = 0; i < bendingJsonArray.length(); i++) {
                    JSONObject bendingJsonObject = bendingJsonArray.getJSONObject(i);
                    Bending bendingObject = new Bending();
                    bendingObject.setBendingName(bendingJsonObject.getString("Name"));
                    bendingObject.setType(bendingJsonObject.getInt("type"));
                    bendingObject.setImageName(bendingJsonObject.getString("image"));
                    bendingArrayList.add(bendingObject);
                }
            }
            dataResponse = new UglysResponse();
            dataResponse.setBendingList(bendingArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

    private UglysResponse parseSymbolData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray symbolJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject symbolsJsonObject = jsonArray.getJSONObject(i);
                if (symbolsJsonObject != null)
                    symbolJsonArray = symbolsJsonObject.getJSONArray("Symbols");
            }
            ArrayList<Symbols> symbolsArrayList = new ArrayList<>();
            if (symbolsArrayList != null) {
                for (int i = 0; i < symbolJsonArray.length(); i++) {
                    JSONObject symbolJsonObject = symbolJsonArray.getJSONObject(i);
                    Symbols symbolObject = new Symbols();
                    symbolObject.setSymbolName(symbolJsonObject.getString("name"));
                    symbolObject.setSymbolImage(symbolJsonObject.getString("image"));
                    symbolsArrayList.add(symbolObject);
                }

            }
            dataResponse = new UglysResponse();
            dataResponse.setSymbolList(symbolsArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataResponse;
    }

    private UglysResponse parseCalculatorData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray calculatorJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject calculatorJsonObject = jsonArray.getJSONObject(i);
                if (calculatorJsonObject != null)
                    calculatorJsonArray = calculatorJsonObject.getJSONArray("Calculators");
            }
            ArrayList<Calculators> calculatorArrayList = new ArrayList<>();

            if (calculatorJsonArray != null) {
                for (int i = 0; i < calculatorJsonArray.length(); i++) {
                    JSONObject calculatorJsonObject = calculatorJsonArray.getJSONObject(i);
                    Calculators calculatorObject = new Calculators();
                    calculatorObject.setCalculatorName(calculatorJsonObject.getString("name"));
                    if (calculatorJsonObject.has("hash_location"))
                        calculatorObject.setHashLocation(calculatorJsonObject.getString("hash_location"));
                    if (calculatorJsonObject.has("chapterIndex"))
                        calculatorObject.setChapterIndex(calculatorJsonObject.getInt("chapterIndex"));
                    if (calculatorJsonObject.has("type"))
                        calculatorObject.setCalculatorType(calculatorJsonObject.getInt("type"));
                    calculatorArrayList.add(calculatorObject);
                }
            }
            dataResponse = new UglysResponse();
            dataResponse.setCalculatorList(calculatorArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataResponse;
    }

    private UglysResponse parseConversionsData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray conversionsJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject conversionsJsonObject = jsonArray.getJSONObject(i);
                if (conversionsJsonObject != null)
                    conversionsJsonArray = conversionsJsonObject.getJSONArray("Conversions");
            }
            ArrayList<Conversions> conversionsArrayList = new ArrayList<>();

            if (conversionsJsonArray != null) {
                for (int i = 0; i < conversionsJsonArray.length(); i++) {
                    JSONObject conversionJsonObject = conversionsJsonArray.getJSONObject(i);
                    JSONArray parametersJsonArray = conversionJsonObject.getJSONArray("parameters");
                    ArrayList<ConversionParameters> coversionParamList = new ArrayList<>();
                    Conversions conversionObject = new Conversions();
                    conversionObject.setConversionName(conversionJsonObject.getString("name"));
                    for (int j = 0; j < parametersJsonArray.length(); j++) {
                        ConversionParameters conversionParamsObject = new ConversionParameters();
                        JSONObject subTopicJsonObject = parametersJsonArray.getJSONObject(j);
                        conversionParamsObject.setParameterName(subTopicJsonObject.getString("name"));
                        coversionParamList.add(conversionParamsObject);
                        conversionObject.setParamList(coversionParamList);
                    }
                    conversionsArrayList.add(conversionObject);
                }
            }
            dataResponse = new UglysResponse();
            dataResponse.setConversionsList(conversionsArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataResponse;
    }

    private UglysResponse parseTopicsData(String jsonData) {
        UglysResponse dataResponse = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            JSONArray topicsJsonArray = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject topicsJsonObject = jsonArray.getJSONObject(i);
                if (topicsJsonObject != null)
                    topicsJsonArray = topicsJsonObject.getJSONArray("Topics");
            }
            ArrayList<Topics> topicsList = new ArrayList<>();

            if (topicsJsonArray != null) {
                for (int i = 0; i < topicsJsonArray.length(); i++) {
                    JSONObject topicJsonObject = topicsJsonArray.getJSONObject(i);
                    JSONArray subTopicsJsonArray = topicJsonObject.getJSONArray("subtopics");
                    ArrayList<SubTopics> subTopicsList = new ArrayList<>();
                    Topics topicsObject = new Topics();
                    topicsObject.setTopicName(topicJsonObject.getString("name"));
                    for (int j = 0; j < subTopicsJsonArray.length(); j++) {
                        ArrayList<SubsubTopics> subSubTopicsList = new ArrayList<>();
                        SubTopics subTopicsObject = new SubTopics();
                        JSONObject subTopicJsonObject = subTopicsJsonArray.getJSONObject(j);
                        subTopicsObject.setSubTopicName(subTopicJsonObject.getString("name"));
                        if (subTopicJsonObject.has("type")) {
                            subTopicsObject.setTypeOfContent(subTopicJsonObject.getString("type"));
                        }
                        if (subTopicJsonObject.has("calculator_id")) {
                            subTopicsObject.setGetTypeOfCalculator(subTopicJsonObject.getString("calculator_id"));
                        }
                        if (subTopicJsonObject.has("sub_calculator_id")) {
                            subTopicsObject.setSub_calculator_id(subTopicJsonObject.getInt("sub_calculator_id"));
                        }
                        subTopicsList.add(subTopicsObject);
                        JSONObject subSubTopicJsonObject = null;
                        SubsubTopics subSubTopicsObject = null;
                        if (subTopicJsonObject.has("subtopics")) {
                            ArrayList<ThirdLevelSubTopics> thirdLevelTopicsList = new ArrayList<>();
                            JSONArray subSubTopicsJsonArray = subTopicJsonObject.getJSONArray("subtopics");
                            for (int k = 0; k < subSubTopicsJsonArray.length(); k++) {
                                subSubTopicsObject = new SubsubTopics();
                                subSubTopicJsonObject = subSubTopicsJsonArray.getJSONObject(k);
                                subSubTopicsObject.setSubSubTopicName(subSubTopicJsonObject.getString("name"));
                                if (subSubTopicJsonObject.has("type")) {
                                    subSubTopicsObject.setTypeOfContent(subSubTopicJsonObject.getString("type"));
                                }
                                if (subSubTopicJsonObject.has("calculator_id")) {
                                    subSubTopicsObject.setGetTypeOfCalculator(subSubTopicJsonObject.getString("calculator_id"));
                                }
                                if (subSubTopicJsonObject.has("sub_calculator_id")) {
                                    subSubTopicsObject.setSub_calculator_id(subSubTopicJsonObject.getInt("sub_calculator_id"));
                                }
                                subSubTopicsList.add(subSubTopicsObject);
                            }
                            subTopicsObject.setSubSubTopicsArrayList(subSubTopicsList);
                            if (subSubTopicJsonObject.has("subtopics")) {
                                JSONArray thirdLevelTopicsJsonArray = subSubTopicJsonObject.getJSONArray("subtopics");
                                for (int l = 0; l < thirdLevelTopicsJsonArray.length(); l++) {
                                    ThirdLevelSubTopics thirdLevelTopicsObject = new ThirdLevelSubTopics();
                                    JSONObject thirdLevelTopicJsonObject = thirdLevelTopicsJsonArray.getJSONObject(l);
                                    thirdLevelTopicsObject.setSubTopicName(thirdLevelTopicJsonObject.getString("name"));
                                    if (thirdLevelTopicJsonObject.has("type")) {
                                        thirdLevelTopicsObject.setTypeOfContent(thirdLevelTopicJsonObject.getString("type"));
                                    }
                                    if (thirdLevelTopicJsonObject.has("calculator_id")) {
                                        thirdLevelTopicsObject.setGetTypeOfCalculator(thirdLevelTopicJsonObject.getString("calculator_id"));
                                    }
                                    if (thirdLevelTopicJsonObject.has("sub_calculator_id")) {
                                        thirdLevelTopicsObject.setSub_calculator_id(thirdLevelTopicJsonObject.getInt("sub_calculator_id"));
                                    }
                                    thirdLevelTopicsList.add(thirdLevelTopicsObject);
                                }
                                subSubTopicsObject.setThirdLevelTopicsArrayList(thirdLevelTopicsList);
                            }
                        }
                    }
                    topicsObject.setSubTopicsArrayList(subTopicsList);
                    topicsList.add(topicsObject);
                }
            }
            dataResponse = new UglysResponse();
            dataResponse.setTopicsList(topicsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataResponse;
    }


    public UglysResponse getJsonResponse() {
        return mDataResponse;
    }
}
