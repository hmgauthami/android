package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.ConduitConductorSize;
import com.ascendlearning.jbl.uglys.models.ConduitFill;
import com.ascendlearning.jbl.uglys.models.ConduitInsulation;
import com.ascendlearning.jbl.uglys.models.ConduitTradeSize;
import com.ascendlearning.jbl.uglys.utils.CompositeKey;
import com.ascendlearning.jbl.uglys.utils.Constants;
import com.ascendlearning.jbl.uglys.utils.DecimalDigitsInputFilter;
import com.ascendlearning.jbl.uglys.utils.ESError;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import org.json.JSONException;
import org.readium.sdk.android.components.navigation.NavigationElement;
import org.readium.sdk.android.components.navigation.NavigationPoint;
import org.readium.sdk.android.launcher.model.OpenPageRequest;

import java.util.ArrayList;
import java.util.List;

public class ConduitFillCalculator extends SuperActivity implements UICallback, Spinner.OnItemSelectedListener {
    private UglysController mController;
    private ArrayList<ConduitFill> conduitFillArrayList;
    private ArrayList<ConduitInsulation> wireInsulationArrayList;
    private ArrayList<ConduitConductorSize> conductorSizeArrayList;
    private ArrayList<ConduitTradeSize> tradeSizeArrayList;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private Spinner conduit_type_spinner;
    TextView wire_count_header;
    EditText wire_count_edit_text;
    private TextView result;
    private TextView result1;
    private TextView result2;
    private TableLayout table_layout1;
    private TableLayout table_layout2;
    private TableLayout table_layout3;
    private TableLayout table_layout4;
    private TableLayout table_layout5;
    private Spinner insulation_spinner1;
    private Spinner insulation_spinner2;
    private Spinner insulation_spinner3;
    private Spinner insulation_spinner4;
    private Spinner insulation_spinner5;
    private Spinner conductor_size_spinner1;
    private Spinner conductor_size_spinner2;
    private Spinner conductor_size_spinner3;
    private Spinner conductor_size_spinner4;
    private Spinner conductor_size_spinner5;
    private EditText conductor_count_edit_text1;
    private EditText wire_area_edit_text1;
    private EditText total_area_edit_text1;
    private EditText conductor_count_edit_text2;
    private EditText wire_area_edit_text2;
    private EditText total_area_edit_text2;
    private EditText conductor_count_edit_text3;
    private EditText wire_area_edit_text3;
    private EditText total_area_edit_text3;
    private EditText conductor_count_edit_text4;
    private EditText wire_area_edit_text4;
    private EditText total_area_edit_text4;
    private EditText conductor_count_edit_text5;
    private EditText wire_area_edit_text5;
    private EditText total_area_edit_text5;
    double conductorSizeValue1;
    double conductorSizeValue2;
    double conductorSizeValue3;
    double conductorSizeValue4;
    double conductorSizeValue5;
    private RadioGroup radiofill;
    private TextView result_header;
    private LinearLayout result_view;
    private TableRow conductor_size_row1;
    private TableRow conductor_size_row2;
    private TableRow conductor_size_row3;
    private TableRow conductor_size_row4;
    private TableRow conductor_size_row5;
    private TableRow conductor_count_row1;
    private TableRow conductor_count_row2;
    private TableRow conductor_count_row3;
    private TableRow conductor_count_row4;
    private TableRow conductor_count_row5;
    private TableRow wire_area_row1;
    private TableRow wire_area_row2;
    private TableRow wire_area_row3;
    private TableRow wire_area_row4;
    private TableRow wire_area_row5;
    private TableRow total_area_row1;
    private TableRow total_area_row2;
    private TableRow total_area_row3;
    private TableRow total_area_row4;
    private TableRow total_area_row5;
    private TextView radiofill_header;
    private String infoText;
    private RelativeLayout relatedContent_view;
    private String topic = "";
    private final int COIL_COUNT = 5;
    int wireCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conduit_fill_calculator);
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbarTop);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        fetchJsonData();
        UglysResponse mResponse = mController.getJsonResponse();
        conduitFillArrayList = mResponse.getConduitfillArrayList();
        fetchJsonData1();
        mResponse = mController.getJsonResponse();
        wireInsulationArrayList = mResponse.getConduitfill1ArrayList();

        int conduitType = getIntent().getIntExtra("conduitType", -1);

        createBookmarkData();
        makeLayout();
        if (conduitType > 0) {
            conduit_type_spinner.setSelection(conduitType);
            conduit_type_spinner.setTag(R.id.spinner_pos, conduitType);
            bookmarkObj.setBookmarkCalType(conduitType);
            setBookmarkName(conduitType);
            final ArrayList<String> wireTypeNameList = new ArrayList<>();
            for (int i = 0; i < wireInsulationArrayList.size(); i++) {
                wireTypeNameList.add(wireInsulationArrayList.get(i).getInsulationName());
            }
            result_header.setVisibility(View.GONE);
            result_view.setVisibility(View.GONE);
            wire_count_edit_text.setVisibility(View.VISIBLE);
            wire_count_header.setVisibility(View.VISIBLE);
            radiofill.setVisibility(View.VISIBLE);
            radiofill_header.setVisibility(View.VISIBLE);
            tradeSizeArrayList = conduitFillArrayList.get(conduitType - 1).getTradeSizeArrayList();
            relatedContent_view.setVisibility(View.VISIBLE);
        }

    }

    private void setBookmarkName(int conduitType) {
        switch (conduitType) {
            case 1:
                bookmarkObj.setBookmarkName("Conduit Fill Calculator - Electric Metallic Tubing (EMT)");
                break;
            case 2:
                bookmarkObj.setBookmarkName("Conduit Fill Calculator - Electric NonMetallic Tubing (ENT)");
                break;
            case 3:
                bookmarkObj.setBookmarkName("Conduit Fill Calculator - Flexible Metal Conduit (FMC)");
                break;
            case 4:
                bookmarkObj.setBookmarkName("Conduit Fill Calculator - Intermediate Metal Conduit (IMC)");
                break;
            case 5:
                bookmarkObj.setBookmarkName("Conduit Fill Calculator - LiquidTight Flexible NonMetallic Conduit (TYPE LFNC-B)");
                break;
            case 6:
                bookmarkObj.setBookmarkName("Conduit Fill Calculator - LiquidTight Flexible NonMetallic Conduit (TYPE LFNC-A)");
                break;
            case 7:
                bookmarkObj.setBookmarkName("Conduit Fill Calculator - LiquidTight Flexible Metal Conduit (LFMC)");
                break;
            case 8:
                bookmarkObj.setBookmarkName("Conduit Fill Calculator - Rigid Metal Conduit (RMC)");
                break;
            case 9:
                bookmarkObj.setBookmarkName("Conduit Fill Calculator - Rigid PVC Conduit (PVC), SCHEDULE 80");
                break;
            case 10:
                bookmarkObj.setBookmarkName("Conduit Fill Calculator - Rigid PVC Conduit (PVC), SCHEDULE 40 & HDPE Conduit (HDPE)");
                break;
            case 11:
                bookmarkObj.setBookmarkName("Conduit Fill Calculator - TYPE A, Rigid PVC Conduit (PVC)");
                break;
            case 12:
                bookmarkObj.setBookmarkName("Conduit Fill Calculator - TYPE EB, PVC Conduit (PVC)");
                break;
        }
    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.CONDUIT_FILL.ordinal());
    }

    private void makeLayout() {
        relatedContent_view = (RelativeLayout) findViewById(R.id.relatedContent_view);
        result_header = (TextView) findViewById(R.id.result_header);
        result_view = (LinearLayout) findViewById(R.id.result_view);
        result = (TextView) findViewById(R.id.result);
        result1 = (TextView) findViewById(R.id.result1);
        result2 = (TextView) findViewById(R.id.result2);
        conduit_type_spinner = (Spinner) findViewById(R.id.conduit_fill_type_spinner);
        wire_count_header = (TextView) findViewById(R.id.wire_count_header);
        radiofill_header = (TextView) findViewById(R.id.radiofill_header);
        radiofill = (RadioGroup) findViewById(R.id.radiofill);
        wire_count_edit_text = (EditText) findViewById(R.id.wire_count_edit_text);
        wire_count_edit_text.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
        table_layout1 = (TableLayout) findViewById(R.id.tablelayout1);
        table_layout2 = (TableLayout) findViewById(R.id.tablelayout2);
        table_layout3 = (TableLayout) findViewById(R.id.tablelayout3);
        table_layout4 = (TableLayout) findViewById(R.id.tablelayout4);
        table_layout5 = (TableLayout) findViewById(R.id.tablelayout5);
        insulation_spinner1 = (Spinner) findViewById(R.id.insulation_spinner1);
        insulation_spinner2 = (Spinner) findViewById(R.id.insulation_spinner2);
        insulation_spinner3 = (Spinner) findViewById(R.id.insulation_spinner3);
        insulation_spinner4 = (Spinner) findViewById(R.id.insulation_spinner4);
        insulation_spinner5 = (Spinner) findViewById(R.id.insulation_spinner5);
        conductor_size_spinner1 = (Spinner) findViewById(R.id.conductor_size_spinner1);
        conductor_size_spinner2 = (Spinner) findViewById(R.id.conductor_size_spinner2);
        conductor_size_spinner3 = (Spinner) findViewById(R.id.conductor_size_spinner3);
        conductor_size_spinner4 = (Spinner) findViewById(R.id.conductor_size_spinner4);
        conductor_size_spinner5 = (Spinner) findViewById(R.id.conductor_size_spinner5);
        conductor_count_edit_text1 = (EditText) findViewById(R.id.conductor_count_edit_text1);
        conductor_count_edit_text2 = (EditText) findViewById(R.id.conductor_count_edit_text2);
        conductor_count_edit_text3 = (EditText) findViewById(R.id.conductor_count_edit_text3);
        conductor_count_edit_text4 = (EditText) findViewById(R.id.conductor_count_edit_text4);
        conductor_count_edit_text5 = (EditText) findViewById(R.id.conductor_count_edit_text5);
        conductor_count_edit_text1.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
        conductor_count_edit_text2.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
        conductor_count_edit_text3.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
        conductor_count_edit_text4.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
        conductor_count_edit_text5.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
        total_area_edit_text1 = (EditText) findViewById(R.id.total_area_edit_text1);
        total_area_edit_text2 = (EditText) findViewById(R.id.total_area_edit_text2);
        total_area_edit_text3 = (EditText) findViewById(R.id.total_area_edit_text3);
        total_area_edit_text4 = (EditText) findViewById(R.id.total_area_edit_text4);
        total_area_edit_text5 = (EditText) findViewById(R.id.total_area_edit_text5);
        wire_area_edit_text1 = (EditText) findViewById(R.id.wire_area_edit_text1);
        wire_area_edit_text2 = (EditText) findViewById(R.id.wire_area_edit_text2);
        wire_area_edit_text3 = (EditText) findViewById(R.id.wire_area_edit_text3);
        wire_area_edit_text4 = (EditText) findViewById(R.id.wire_area_edit_text4);
        wire_area_edit_text5 = (EditText) findViewById(R.id.wire_area_edit_text5);
        conductor_size_row1 = (TableRow) findViewById(R.id.conductor_size_row1);
        conductor_size_row2 = (TableRow) findViewById(R.id.conductor_size_row2);
        conductor_size_row3 = (TableRow) findViewById(R.id.conductor_size_row3);
        conductor_size_row4 = (TableRow) findViewById(R.id.conductor_size_row4);
        conductor_size_row5 = (TableRow) findViewById(R.id.conductor_size_row5);
        conductor_count_row1 = (TableRow) findViewById(R.id.conductor_count_row1);
        conductor_count_row2 = (TableRow) findViewById(R.id.conductor_count_row2);
        conductor_count_row3 = (TableRow) findViewById(R.id.conductor_count_row3);
        conductor_count_row4 = (TableRow) findViewById(R.id.conductor_count_row4);
        conductor_count_row5 = (TableRow) findViewById(R.id.conductor_count_row5);
        wire_area_row1 = (TableRow) findViewById(R.id.wire_area_row1);
        wire_area_row2 = (TableRow) findViewById(R.id.wire_area_row2);
        wire_area_row3 = (TableRow) findViewById(R.id.wire_area_row3);
        wire_area_row4 = (TableRow) findViewById(R.id.wire_area_row4);
        wire_area_row5 = (TableRow) findViewById(R.id.wire_area_row5);
        total_area_row1 = (TableRow) findViewById(R.id.total_area_row1);
        total_area_row2 = (TableRow) findViewById(R.id.total_area_row2);
        total_area_row3 = (TableRow) findViewById(R.id.total_area_row3);
        total_area_row4 = (TableRow) findViewById(R.id.total_area_row4);
        total_area_row5 = (TableRow) findViewById(R.id.total_area_row5);

        final ArrayList<String> conduitInsulationNameList = new ArrayList<>();
        for (int i = 0; i < wireInsulationArrayList.size(); i++) {
            conduitInsulationNameList.add(wireInsulationArrayList.get(i).getInsulationName());
        }
        conduitInsulationNameList.add(0, getString(R.string.spinner_wire_type_default));
        insulation_spinner1.setSelection(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, conduitInsulationNameList);
        insulation_spinner1.setAdapter(adapter);
        insulation_spinner2.setSelection(0);
        insulation_spinner2.setAdapter(adapter);
        insulation_spinner3.setSelection(0);
        insulation_spinner3.setAdapter(adapter);
        insulation_spinner4.setSelection(0);
        insulation_spinner4.setAdapter(adapter);
        insulation_spinner5.setSelection(0);
        insulation_spinner5.setAdapter(adapter);
        wire_count_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (wire_count_edit_text.getText().length() > 0) {
                    if (Integer.parseInt(wire_count_edit_text.getText().toString()) <= COIL_COUNT) {
                        wireCount = Integer.parseInt(wire_count_edit_text.getText().toString());
                        reinitializeView();
                        switch (wireCount) {
                            case 1:
                                table_layout1.setVisibility(View.VISIBLE);
                                table_layout2.setVisibility(View.GONE);
                                table_layout3.setVisibility(View.GONE);
                                table_layout4.setVisibility(View.GONE);
                                table_layout5.setVisibility(View.GONE);
                                break;
                            case 2:
                                table_layout1.setVisibility(View.VISIBLE);
                                table_layout2.setVisibility(View.VISIBLE);
                                table_layout3.setVisibility(View.GONE);
                                table_layout4.setVisibility(View.GONE);
                                table_layout5.setVisibility(View.GONE);
                                break;
                            case 3:
                                table_layout1.setVisibility(View.VISIBLE);
                                table_layout2.setVisibility(View.VISIBLE);
                                table_layout3.setVisibility(View.VISIBLE);
                                table_layout4.setVisibility(View.GONE);
                                table_layout5.setVisibility(View.GONE);
                                break;
                            case 4:
                                table_layout1.setVisibility(View.VISIBLE);
                                table_layout2.setVisibility(View.VISIBLE);
                                table_layout3.setVisibility(View.VISIBLE);
                                table_layout4.setVisibility(View.VISIBLE);
                                table_layout5.setVisibility(View.GONE);
                                break;
                            case 5:
                                table_layout1.setVisibility(View.VISIBLE);
                                table_layout2.setVisibility(View.VISIBLE);
                                table_layout3.setVisibility(View.VISIBLE);
                                table_layout4.setVisibility(View.VISIBLE);
                                table_layout5.setVisibility(View.VISIBLE);
                                break;
                        }
                    } else {
                        showToast(getString(R.string.wire_count_validation), 1000);
                        table_layout1.setVisibility(View.GONE);
                        table_layout2.setVisibility(View.GONE);
                        table_layout3.setVisibility(View.GONE);
                        table_layout4.setVisibility(View.GONE);
                        table_layout5.setVisibility(View.GONE);
                        result_header.setVisibility(View.GONE);
                        result_view.setVisibility(View.GONE);
                        wire_count_edit_text.setText("");
                    }

                } else {
                    table_layout1.setVisibility(View.GONE);
                    table_layout2.setVisibility(View.GONE);
                    table_layout3.setVisibility(View.GONE);
                    table_layout4.setVisibility(View.GONE);
                    table_layout5.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        conductor_count_edit_text1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (conductor_count_edit_text1.getText().length() > 0) {
                    total_area_row1.setVisibility(View.VISIBLE);
                    total_area_edit_text1.setText(TextUtil.convertToExponential( conductorSizeValue1 * Integer.parseInt(conductor_count_edit_text1.getText().toString())));
                    result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                    int radioButtonID = radiofill.getCheckedRadioButtonId();
                    View radioButton = radiofill.findViewById(radioButtonID);
                    int idx = radiofill.indexOfChild(radioButton);
                    ArrayList<String> tradeSizeList = new ArrayList<String>();
                    wire_area_edit_text1.setText(conductorSizeValue1 + "");
                    result1.setText(calculateGrandTotal(total_area_edit_text1, total_area_edit_text2, total_area_edit_text3, total_area_edit_text4, total_area_edit_text5));
                    result.setText(calculateConductorTotal(conductor_count_edit_text1,
                            conductor_count_edit_text2,
                            conductor_count_edit_text3,
                            conductor_count_edit_text4,
                            conductor_count_edit_text5
                    ));
                    if (idx == 0) {
                        if (Integer.parseInt(result.getText().toString()) == 1) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getOneWire() + "");
                            }

                        } else if (Integer.parseInt(result.getText().toString()) == 2) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getTwoWire() + "");
                            }

                        } else if (Integer.parseInt(result.getText().toString()) > 2) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getOverTwoWire() + "");
                            }
                        }
                    } else {
                        for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                            tradeSizeList.add(tradeSizeArrayList.get(i).getNipple() + "");
                        }
                    }
                    String minRacewayVal = null;
                    for (int i = 0; i < tradeSizeList.size(); i++) {
                        if (Double.parseDouble(result1.getText().toString()) <= Double.parseDouble(tradeSizeList.get(i))) {
                            minRacewayVal = tradeSizeArrayList.get(i).getName();
                            break;
                        }
                    }

                    if (minRacewayVal != null) {
                        result2.setText(minRacewayVal);
                    } else {
                        resetValues();
                    }

                } else {
                    wire_area_edit_text1.setText("");
                    total_area_edit_text1.setText("");
                    if(conductor_count_edit_text1.getText().toString().isEmpty() && conductor_count_edit_text2.getText().toString().isEmpty()
                            && conductor_count_edit_text3.getText().toString().isEmpty() && conductor_count_edit_text4.getText().toString().isEmpty()
                            && conductor_count_edit_text5.getText().toString().isEmpty()) {
                        result_header.setVisibility(View.GONE);
                        result_view.setVisibility(View.GONE);
                    }
                }

            }
        });
        conductor_count_edit_text2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (conductor_count_edit_text2.getText().length() > 0) {
                    total_area_row2.setVisibility(View.VISIBLE);
                    total_area_edit_text2.setText(TextUtil.convertToExponential( conductorSizeValue2 * Integer.parseInt(conductor_count_edit_text2.getText().toString())));
                    result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                    int radioButtonID = radiofill.getCheckedRadioButtonId();
                    View radioButton = radiofill.findViewById(radioButtonID);
                    int idx = radiofill.indexOfChild(radioButton);
                    ArrayList<String> tradeSizeList = new ArrayList<String>();
                    wire_area_edit_text2.setText(conductorSizeValue2 + "");
                    result1.setText(calculateGrandTotal(total_area_edit_text1, total_area_edit_text2, total_area_edit_text3, total_area_edit_text4, total_area_edit_text5));
                    result.setText(calculateConductorTotal(conductor_count_edit_text1,
                            conductor_count_edit_text2,
                            conductor_count_edit_text3,
                            conductor_count_edit_text4,
                            conductor_count_edit_text5
                    ));
                    if (idx == 0) {
                        if (Integer.parseInt(result.getText().toString()) == 1) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getOneWire() + "");
                            }

                        } else if (Integer.parseInt(result.getText().toString()) == 2) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getTwoWire() + "");
                            }

                        } else if (Integer.parseInt(result.getText().toString()) > 2) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getOverTwoWire() + "");
                            }
                        }
                    } else {
                        for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                            tradeSizeList.add(tradeSizeArrayList.get(i).getNipple() + "");
                        }
                    }
                    String minRacewayVal = null;
                    for (int i = 0; i < tradeSizeList.size(); i++) {
                        if (Double.parseDouble(result1.getText().toString()) <= Double.parseDouble(tradeSizeList.get(i))) {
                            minRacewayVal = tradeSizeArrayList.get(i).getName();
                            break;
                        }
                    }

                    if (minRacewayVal != null) {
                        result2.setText(minRacewayVal);
                    } else {
                        resetValues();
                    }
                } else {
                    wire_area_edit_text2.setText("");
                    total_area_edit_text2.setText("");
                    if(conductor_count_edit_text1.getText().toString().isEmpty() && conductor_count_edit_text2.getText().toString().isEmpty()
                            && conductor_count_edit_text3.getText().toString().isEmpty() && conductor_count_edit_text4.getText().toString().isEmpty()
                            && conductor_count_edit_text5.getText().toString().isEmpty()) {
                        result_header.setVisibility(View.GONE);
                        result_view.setVisibility(View.GONE);
                    }
                }

            }
        });
        conductor_count_edit_text3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (conductor_count_edit_text3.getText().length() > 0) {
                    total_area_row3.setVisibility(View.VISIBLE);
                    total_area_edit_text3.setText(TextUtil.convertToExponential( conductorSizeValue3 * Integer.parseInt(conductor_count_edit_text3.getText().toString())));
                    result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                    int radioButtonID = radiofill.getCheckedRadioButtonId();
                    View radioButton = radiofill.findViewById(radioButtonID);
                    int idx = radiofill.indexOfChild(radioButton);
                    wire_area_edit_text3.setText(conductorSizeValue3 + "");
                    ArrayList<String> tradeSizeList = new ArrayList<String>();
                    result1.setText(calculateGrandTotal(total_area_edit_text1, total_area_edit_text2, total_area_edit_text3, total_area_edit_text4, total_area_edit_text5));
                    result.setText(calculateConductorTotal(conductor_count_edit_text1,
                            conductor_count_edit_text2,
                            conductor_count_edit_text3,
                            conductor_count_edit_text4,
                            conductor_count_edit_text5
                    ));
                    if (idx == 0) {
                        if (Integer.parseInt(result.getText().toString()) == 1) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getOneWire() + "");
                            }

                        } else if (Integer.parseInt(result.getText().toString()) == 2) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getTwoWire() + "");
                            }

                        } else if (Integer.parseInt(result.getText().toString()) > 2) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getOverTwoWire() + "");
                            }
                        }
                    } else {
                        for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                            tradeSizeList.add(tradeSizeArrayList.get(i).getNipple() + "");
                        }
                    }
                    String minRacewayVal = null;
                    for (int i = 0; i < tradeSizeList.size(); i++) {
                        if (Double.parseDouble(result1.getText().toString()) <= Double.parseDouble(tradeSizeList.get(i))) {
                            minRacewayVal = tradeSizeArrayList.get(i).getName();
                            break;
                        }
                    }

                    if (minRacewayVal != null) {
                        result2.setText(minRacewayVal);
                    } else {
                        resetValues();
                    }
                } else {
                    wire_area_edit_text3.setText("");
                    total_area_edit_text3.setText("");
                    if(conductor_count_edit_text1.getText().toString().isEmpty() && conductor_count_edit_text2.getText().toString().isEmpty()
                            && conductor_count_edit_text3.getText().toString().isEmpty() && conductor_count_edit_text4.getText().toString().isEmpty()
                            && conductor_count_edit_text5.getText().toString().isEmpty()) {
                        result_header.setVisibility(View.GONE);
                        result_view.setVisibility(View.GONE);
                    }
                }

            }
        });
        conductor_count_edit_text4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (conductor_count_edit_text4.getText().length() > 0) {
                    total_area_row4.setVisibility(View.VISIBLE);
                    total_area_edit_text4.setText(TextUtil.convertToExponential( conductorSizeValue4 * Integer.parseInt(conductor_count_edit_text4.getText().toString())));
                    result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                    int radioButtonID = radiofill.getCheckedRadioButtonId();
                    View radioButton = radiofill.findViewById(radioButtonID);
                    int idx = radiofill.indexOfChild(radioButton);
                    ArrayList<String> tradeSizeList = new ArrayList<String>();
                    wire_area_edit_text4.setText(conductorSizeValue4 + "");
                    result1.setText(calculateGrandTotal(total_area_edit_text1, total_area_edit_text2, total_area_edit_text3, total_area_edit_text4, total_area_edit_text5));
                    result.setText(calculateConductorTotal(conductor_count_edit_text1,
                            conductor_count_edit_text2,
                            conductor_count_edit_text3,
                            conductor_count_edit_text4,
                            conductor_count_edit_text5
                    ));
                    if (idx == 0) {
                        if (Integer.parseInt(result.getText().toString()) == 1) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getOneWire() + "");
                            }

                        } else if (Integer.parseInt(result.getText().toString()) == 2) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getTwoWire() + "");
                            }

                        } else if (Integer.parseInt(result.getText().toString()) > 2) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getOverTwoWire() + "");
                            }
                        }
                    } else {
                        for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                            tradeSizeList.add(tradeSizeArrayList.get(i).getNipple() + "");
                        }
                    }
                    String minRacewayVal = null;
                    for (int i = 0; i < tradeSizeList.size(); i++) {
                        if (Double.parseDouble(result1.getText().toString()) <= Double.parseDouble(tradeSizeList.get(i))) {
                            minRacewayVal = tradeSizeArrayList.get(i).getName();
                            break;
                        }
                    }

                    if (minRacewayVal != null) {
                        result2.setText(minRacewayVal);
                    } else {
                        resetValues();
                    }
                } else {
                    wire_area_edit_text4.setText("");
                    total_area_edit_text4.setText("");
                    if(conductor_count_edit_text1.getText().toString().isEmpty() && conductor_count_edit_text2.getText().toString().isEmpty()
                            && conductor_count_edit_text3.getText().toString().isEmpty() && conductor_count_edit_text4.getText().toString().isEmpty()
                            && conductor_count_edit_text5.getText().toString().isEmpty()) {
                        result_header.setVisibility(View.GONE);
                        result_view.setVisibility(View.GONE);
                    }
                }

            }
        });
        conductor_count_edit_text5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (conductor_count_edit_text5.getText().length() > 0) {
                    total_area_row5.setVisibility(View.VISIBLE);
                    total_area_edit_text5.setText(TextUtil.convertToExponential(conductorSizeValue5 * Integer.parseInt(conductor_count_edit_text5.getText().toString())));
                    result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                    int radioButtonID = radiofill.getCheckedRadioButtonId();
                    View radioButton = radiofill.findViewById(radioButtonID);
                    int idx = radiofill.indexOfChild(radioButton);
                    ArrayList<String> tradeSizeList = new ArrayList<String>();
                    wire_area_edit_text5.setText(conductorSizeValue5 + "");
                    result1.setText(calculateGrandTotal(total_area_edit_text1, total_area_edit_text2, total_area_edit_text3, total_area_edit_text4, total_area_edit_text5));
                    result.setText(calculateConductorTotal(conductor_count_edit_text1,
                            conductor_count_edit_text2,
                            conductor_count_edit_text3,
                            conductor_count_edit_text4,
                            conductor_count_edit_text5
                    ));
                    if (idx == 0) {
                        if (Integer.parseInt(result.getText().toString()) == 1) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getOneWire() + "");
                            }

                        } else if (Integer.parseInt(result.getText().toString()) == 2) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getTwoWire() + "");
                            }

                        } else if (Integer.parseInt(result.getText().toString()) > 2) {
                            for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                                tradeSizeList.add(tradeSizeArrayList.get(i).getOverTwoWire() + "");
                            }
                        }
                    } else {
                        for (int i = 0; i < tradeSizeArrayList.size(); i++) {
                            tradeSizeList.add(tradeSizeArrayList.get(i).getNipple() + "");
                        }
                    }
                    String minRacewayVal = null;
                    for (int i = 0; i < tradeSizeList.size(); i++) {
                        if (Double.parseDouble(result1.getText().toString()) <= Double.parseDouble(tradeSizeList.get(i))) {
                            minRacewayVal = tradeSizeArrayList.get(i).getName();
                            break;
                        }
                    }

                    if (minRacewayVal != null) {
                        result2.setText(minRacewayVal);
                    } else {
                        resetValues();
                    }
                } else {
                    wire_area_edit_text5.setText("");
                    total_area_edit_text5.setText("");
                    if(conductor_count_edit_text1.getText().toString().isEmpty() && conductor_count_edit_text2.getText().toString().isEmpty()
                            && conductor_count_edit_text3.getText().toString().isEmpty() && conductor_count_edit_text4.getText().toString().isEmpty()
                            && conductor_count_edit_text5.getText().toString().isEmpty()) {
                        result_header.setVisibility(View.GONE);
                        result_view.setVisibility(View.GONE);
                    }
                }

            }
        });
        conduit_type_spinner.setOnItemSelectedListener(this);
        insulation_spinner1.setOnItemSelectedListener(this);
        insulation_spinner2.setOnItemSelectedListener(this);
        insulation_spinner3.setOnItemSelectedListener(this);
        insulation_spinner4.setOnItemSelectedListener(this);
        insulation_spinner5.setOnItemSelectedListener(this);
        conductor_size_spinner1.setOnItemSelectedListener(this);
        conductor_size_spinner2.setOnItemSelectedListener(this);
        conductor_size_spinner3.setOnItemSelectedListener(this);
        conductor_size_spinner4.setOnItemSelectedListener(this);
        conductor_size_spinner5.setOnItemSelectedListener(this);
        final ArrayList<String> conduitTypeNameList = new ArrayList<>();
        for (int i = 0; i < conduitFillArrayList.size(); i++) {
            conduitTypeNameList.add(conduitFillArrayList.get(i).getConduitName());
        }

        conduitTypeNameList.add(0, getString(R.string.spinner_conduit_type_default));
        conduit_type_spinner.setSelection(0);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.conversion_spinner_dropdown, conduitTypeNameList) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                CheckedTextView tv = (CheckedTextView) view.findViewById(R.id.text1);
                final CheckedTextView finalItem = tv;
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        finalItem.setSingleLine(false);
                    }
                });
                return view;
            }

        };
        conduit_type_spinner.setAdapter(adapter1);

        if (bookmarkObj.getBookmarkCalType() > 0) {
            relatedContent_view.setVisibility(View.VISIBLE);
        } else {
            relatedContent_view.setVisibility(View.GONE);
        }
        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (bookmarkObj.getBookmarkCalType()) {
                    case 1:
                        topic = "Electrical Metallic Tubing";
                        break;
                    case 2:
                        topic = "Nonmetallic Tubing";
                        break;
                    case 10:
                        topic = "Rigid Pvc Conduit, Schedule 40";
                        break;
                    case 9:
                        topic = "Rigid Pvc Conduit, Schedule 80";
                        break;
                    case 7:
                        topic = "Liquidtight Flexible Metal Conduit";
                        break;
                    case 8:
                        topic = "Rigid Metal Conduit";
                        break;
                    case 3:
                        topic = "Flexible Metal Conduit";
                        break;
                    default:
                        topic = "Maximum Number Of Conductors in Trade Sizes Of Conduit or Tubing";
                        break;
                }
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().toLowerCase().contains(topic.toLowerCase())) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(ConduitFillCalculator.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, topic);
                            OpenPageRequest openPageRequest = OpenPageRequest.fromContentUrl(point.getContent(), application.navigationTable.getSourceHref());
                            try {
                                intent.putExtra(Constants.OPEN_PAGE_REQUEST_DATA, openPageRequest.toJSON().toString());
                                startActivity(intent);
                            } catch (JSONException e) {
                            }
                        }
                        break;
                    }
                }
            }
        });

        ImageView info_imageview = (ImageView) findViewById(R.id.info_imageview);
        infoText = getString(R.string.conduit_fill_info);
        info_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (infoText != null) {
                    CalculatorInfoDialog dialog = new CalculatorInfoDialog(ConduitFillCalculator.this, android.R.style.Theme_Holo_Dialog, infoText);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();
                }

            }
        });
    }

    private void resetValues() {
        conductor_count_edit_text1.setText("");
        conductor_count_edit_text2.setText("");
        conductor_count_edit_text3.setText("");
        conductor_count_edit_text4.setText("");
        conductor_count_edit_text5.setText("");
        wire_area_edit_text1.setText("");
        wire_area_edit_text2.setText("");
        wire_area_edit_text3.setText("");
        wire_area_edit_text4.setText("");
        wire_area_edit_text5.setText("");
        total_area_edit_text1.setText("");
        total_area_edit_text2.setText("");
        total_area_edit_text3.setText("");
        total_area_edit_text4.setText("");
        total_area_edit_text5.setText("");
        result2.setText("");
        result1.setText("");
        result.setText("");
        showToast(getString(R.string.conductor_size_validation), 2000);

    }

    private void reinitializeView() {
        table_layout1 = null;
        table_layout2 = null;
        table_layout3 = null;
        table_layout4 = null;
        table_layout5 = null;
        table_layout1 = (TableLayout) findViewById(R.id.tablelayout1);
        table_layout2 = (TableLayout) findViewById(R.id.tablelayout2);
        table_layout3 = (TableLayout) findViewById(R.id.tablelayout3);
        table_layout4 = (TableLayout) findViewById(R.id.tablelayout4);
        table_layout5 = (TableLayout) findViewById(R.id.tablelayout5);
        insulation_spinner1.setSelection(0);
        insulation_spinner2.setSelection(0);
        insulation_spinner3.setSelection(0);
        insulation_spinner4.setSelection(0);
        insulation_spinner5.setSelection(0);
        conductor_size_spinner1.setSelection(0);
        conductor_size_spinner2.setSelection(0);
        conductor_size_spinner3.setSelection(0);
        conductor_size_spinner4.setSelection(0);
        conductor_size_spinner5.setSelection(0);
        conductor_count_edit_text1.setText("");
        conductor_count_edit_text2.setText("");
        conductor_count_edit_text3.setText("");
        conductor_count_edit_text4.setText("");
        conductor_count_edit_text5.setText("");
        wire_area_edit_text1.setText("");
        wire_area_edit_text2.setText("");
        wire_area_edit_text3.setText("");
        wire_area_edit_text4.setText("");
        wire_area_edit_text5.setText("");
        total_area_edit_text1.setText("");
        total_area_edit_text2.setText("");
        total_area_edit_text3.setText("");
        total_area_edit_text4.setText("");
        total_area_edit_text5.setText("");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner selectedSpinner = (Spinner) parent;
        switch (selectedSpinner.getId()) {
            case R.id.conduit_fill_type_spinner:
                Object tag = conduit_type_spinner.getTag(R.id.spinner_pos);
                if (tag != null) {
                    tag = tag;
                }
                if (position > 0 && tag != position) {
                    conduit_type_spinner.setTag(R.id.spinner_pos, position);
                    bookmarkObj.setBookmarkCalType(position);
                    setBookmarkName(position);
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                    if (bookmarkObj.getBookmarkCalType() > 0) {
                        relatedContent_view.setVisibility(View.VISIBLE);
                    } else {
                        relatedContent_view.setVisibility(View.GONE);
                    }
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    wire_count_edit_text.setVisibility(View.VISIBLE);
                    wire_count_header.setVisibility(View.VISIBLE);
                    radiofill.setVisibility(View.VISIBLE);
                    radiofill_header.setVisibility(View.VISIBLE);
                    radiofill.check(R.id.radioAuto);
                    table_layout1.setVisibility(View.GONE);
                    table_layout2.setVisibility(View.GONE);
                    table_layout3.setVisibility(View.GONE);
                    table_layout4.setVisibility(View.GONE);
                    table_layout5.setVisibility(View.GONE);
                    reinitializeView();
                    wire_count_edit_text.setText("");
                    tradeSizeArrayList = conduitFillArrayList.get(position - 1).getTradeSizeArrayList();
                } else if (position == 0) {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    wire_count_edit_text.setVisibility(View.GONE);
                    wire_count_edit_text.setText("");
                    wire_count_header.setVisibility(View.GONE);
                    radiofill.setVisibility(View.GONE);
                    radiofill_header.setVisibility(View.GONE);
                    relatedContent_view.setVisibility(View.GONE);
                    table_layout1.setVisibility(View.GONE);
                    table_layout2.setVisibility(View.GONE);
                    table_layout3.setVisibility(View.GONE);
                    table_layout4.setVisibility(View.GONE);
                    table_layout5.setVisibility(View.GONE);
                    reinitializeView();
                    bookmarkObj.setBookmarkCalType(-1);
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                }
                break;
            case R.id.insulation_spinner1:
                if (position > 0) {
                    conductorSizeArrayList = wireInsulationArrayList.get(position - 1).getConductorSizeList();
                    ArrayList<String> conductorSize = new ArrayList<>();
                    for (int i = 0; i < conductorSizeArrayList.size(); i++) {
                        conductorSize.add(conductorSizeArrayList.get(i).getConductorSize());
                    }
                    conductorSize.add(0, getString(R.string.spinner_conductor_size_default));
                    conductor_size_spinner1.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, conductorSize);
                    conductor_size_spinner1.setAdapter(adapter);
                    conductor_size_row1.setVisibility(View.VISIBLE);
                    conductor_count_edit_text1.setText(conductor_count_edit_text1.getText());
                    total_area_row1.setVisibility(View.GONE);
                    total_area_edit_text1.setText("");
                } else {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    conductor_count_row1.setVisibility(View.GONE);
                    conductor_count_edit_text1.setText("");
                    conductor_size_row1.setVisibility(View.GONE);
                    conductor_size_spinner1.setSelection(0);
                    total_area_row1.setVisibility(View.GONE);
                    wire_area_edit_text1.setText("");
                    wire_area_row1.setVisibility(View.GONE);
                    total_area_edit_text1.setText("");
                }
                break;
            case R.id.insulation_spinner2:
                if (position > 0) {
                    conductorSizeArrayList = wireInsulationArrayList.get(position - 1).getConductorSizeList();
                    ArrayList<String> conductorSize = new ArrayList<>();
                    for (int i = 0; i < conductorSizeArrayList.size(); i++) {
                        conductorSize.add(conductorSizeArrayList.get(i).getConductorSize());
                    }
                    conductorSize.add(0, getString(R.string.spinner_conductor_size_default));
                    conductor_size_spinner2.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, conductorSize);
                    conductor_size_spinner2.setAdapter(adapter);
                    conductor_size_row2.setVisibility(View.VISIBLE);
                    conductor_count_edit_text2.setText(conductor_count_edit_text2.getText());
                    total_area_row2.setVisibility(View.GONE);
                    total_area_edit_text2.setText("");
                } else {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    conductor_count_row2.setVisibility(View.GONE);
                    conductor_count_edit_text2.setText("");
                    conductor_size_row2.setVisibility(View.GONE);
                    conductor_size_spinner2.setSelection(0);
                    total_area_row2.setVisibility(View.GONE);
                    wire_area_edit_text2.setText("");
                    wire_area_row2.setVisibility(View.GONE);
                    total_area_edit_text2.setText("");
                }
                break;
            case R.id.insulation_spinner3:
                if (position > 0) {
                    conductorSizeArrayList = wireInsulationArrayList.get(position - 1).getConductorSizeList();
                    ArrayList<String> conductorSize = new ArrayList<>();
                    for (int i = 0; i < conductorSizeArrayList.size(); i++) {
                        conductorSize.add(conductorSizeArrayList.get(i).getConductorSize());
                    }
                    conductorSize.add(0, getString(R.string.spinner_conductor_size_default));
                    conductor_size_spinner3.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, conductorSize);
                    conductor_size_spinner3.setAdapter(adapter);
                    conductor_size_row3.setVisibility(View.VISIBLE);
                    conductor_count_edit_text3.setText(conductor_count_edit_text3.getText());
                    total_area_row3.setVisibility(View.GONE);
                    total_area_edit_text3.setText("");
                } else {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    conductor_count_row3.setVisibility(View.GONE);
                    conductor_count_edit_text3.setText("");
                    conductor_size_row3.setVisibility(View.GONE);
                    conductor_size_spinner3.setSelection(0);
                    total_area_row3.setVisibility(View.GONE);
                    wire_area_edit_text3.setText("");
                    wire_area_row3.setVisibility(View.GONE);
                    total_area_edit_text3.setText("");
                }
                break;
            case R.id.insulation_spinner4:
                if (position > 0) {
                    conductorSizeArrayList = wireInsulationArrayList.get(position - 1).getConductorSizeList();
                    ArrayList<String> conductorSize = new ArrayList<>();
                    for (int i = 0; i < conductorSizeArrayList.size(); i++) {
                        conductorSize.add(conductorSizeArrayList.get(i).getConductorSize());
                    }
                    conductorSize.add(0, getString(R.string.spinner_conductor_size_default));
                    conductor_size_spinner4.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, conductorSize);
                    conductor_size_spinner4.setAdapter(adapter);
                    conductor_size_row4.setVisibility(View.VISIBLE);
                    conductor_count_edit_text4.setText(conductor_count_edit_text4.getText());
                    total_area_row4.setVisibility(View.GONE);
                    total_area_edit_text4.setText("");
                } else {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    conductor_count_row4.setVisibility(View.GONE);
                    conductor_count_edit_text4.setText("");
                    conductor_size_row4.setVisibility(View.GONE);
                    conductor_size_spinner4.setSelection(0);
                    total_area_row4.setVisibility(View.GONE);
                    wire_area_edit_text4.setText("");
                    wire_area_row4.setVisibility(View.GONE);
                    total_area_edit_text4.setText("");
                }
                break;
            case R.id.insulation_spinner5:
                if (position > 0) {
                    conductorSizeArrayList = wireInsulationArrayList.get(position - 1).getConductorSizeList();
                    ArrayList<String> conductorSize = new ArrayList<>();
                    for (int i = 0; i < conductorSizeArrayList.size(); i++) {
                        conductorSize.add(conductorSizeArrayList.get(i).getConductorSize());
                    }
                    conductorSize.add(0, getString(R.string.spinner_conductor_size_default));
                    conductor_size_spinner5.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, conductorSize);
                    conductor_size_spinner5.setAdapter(adapter);
                    conductor_size_row5.setVisibility(View.VISIBLE);
                    conductor_count_edit_text5.setText(conductor_count_edit_text5.getText());
                    total_area_row5.setVisibility(View.GONE);
                    total_area_edit_text5.setText("");
                } else {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    conductor_count_row5.setVisibility(View.GONE);
                    conductor_count_edit_text5.setText("");
                    conductor_size_row5.setVisibility(View.GONE);
                    conductor_size_spinner5.setSelection(0);
                    total_area_row5.setVisibility(View.GONE);
                    wire_area_edit_text5.setText("");
                    wire_area_row5.setVisibility(View.GONE);
                    total_area_edit_text5.setText("");
                }
                break;
            case R.id.conductor_size_spinner1:
                if (position > 0) {
                    conductorSizeValue1 = conductorSizeArrayList.get(position - 1).getValue();
                    conductor_count_row1.setVisibility(View.VISIBLE);
                    wire_area_row1.setVisibility(View.VISIBLE);
                    conductor_count_edit_text1.setText(conductor_count_edit_text1.getText());
                    total_area_row1.setVisibility(View.VISIBLE);
                } else {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    conductor_count_row1.setVisibility(View.GONE);
                    wire_area_row1.setVisibility(View.GONE);
                    conductor_count_edit_text1.setText("");
                    total_area_row1.setVisibility(View.GONE);
                    wire_area_edit_text1.setText("");
                    total_area_edit_text1.setText("");
                }
                break;
            case R.id.conductor_size_spinner2:
                if (position > 0) {
                    conductorSizeValue2 = conductorSizeArrayList.get(position - 1).getValue();
                    conductor_count_row2.setVisibility(View.VISIBLE);
                    wire_area_row2.setVisibility(View.VISIBLE);
                    conductor_count_edit_text2.setText(conductor_count_edit_text2.getText());
                    total_area_row2.setVisibility(View.VISIBLE);
                } else {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    conductor_count_row2.setVisibility(View.GONE);
                    wire_area_row2.setVisibility(View.GONE);
                    conductor_count_edit_text2.setText("");
                    total_area_row2.setVisibility(View.GONE);
                    wire_area_edit_text2.setText("");
                    total_area_edit_text2.setText("");
                }
                break;
            case R.id.conductor_size_spinner3:
                if (position > 0) {
                    conductorSizeValue3 = conductorSizeArrayList.get(position - 1).getValue();
                    conductor_count_row3.setVisibility(View.VISIBLE);
                    wire_area_row3.setVisibility(View.VISIBLE);
                    conductor_count_edit_text3.setText(conductor_count_edit_text3.getText());
                    total_area_row3.setVisibility(View.VISIBLE);
                } else {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    conductor_count_row3.setVisibility(View.GONE);
                    wire_area_row3.setVisibility(View.GONE);
                    conductor_count_edit_text3.setText("");
                    total_area_row3.setVisibility(View.GONE);
                    wire_area_edit_text3.setText("");
                    total_area_edit_text3.setText("");
                }
                break;
            case R.id.conductor_size_spinner4:
                if (position > 0) {
                    conductorSizeValue4 = conductorSizeArrayList.get(position - 1).getValue();
                    conductor_count_row4.setVisibility(View.VISIBLE);
                    wire_area_row4.setVisibility(View.VISIBLE);
                    conductor_count_edit_text4.setText(conductor_count_edit_text4.getText());
                    total_area_row4.setVisibility(View.VISIBLE);
                } else {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    conductor_count_row4.setVisibility(View.GONE);
                    wire_area_row4.setVisibility(View.GONE);
                    conductor_count_edit_text4.setText("");
                    total_area_row4.setVisibility(View.GONE);
                    wire_area_edit_text4.setText("");
                    total_area_edit_text4.setText("");
                }
                break;
            case R.id.conductor_size_spinner5:
                if (position > 0) {
                    conductorSizeValue5 = conductorSizeArrayList.get(position - 1).getValue();
                    conductor_count_row5.setVisibility(View.VISIBLE);
                    wire_area_row5.setVisibility(View.VISIBLE);
                    conductor_count_edit_text5.setText(conductor_count_edit_text5.getText());
                    total_area_row5.setVisibility(View.VISIBLE);
                } else {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    conductor_count_row5.setVisibility(View.GONE);
                    wire_area_row5.setVisibility(View.GONE);
                    conductor_count_edit_text5.setText("");
                    total_area_row5.setVisibility(View.GONE);
                    wire_area_edit_text5.setText("");
                    total_area_edit_text5.setText("");
                }
                break;
        }
    }

    private String calculateConductorTotal(EditText editText1, EditText editText2, EditText editText3, EditText editText4, EditText editText5) {
        int val1 = 0;
        int val2 = 0;
        int val3 = 0;
        int val4 = 0;
        int val5 = 0;
        if (!editText1.getText().toString().isEmpty()) {
            val1 = Integer.parseInt(editText1.getText().toString());
        }
        if (!editText2.getText().toString().isEmpty()) {
            val2 = Integer.parseInt(editText2.getText().toString());
        }
        if (!editText3.getText().toString().isEmpty()) {
            val3 = Integer.parseInt(editText3.getText().toString());
        }
        if (!editText4.getText().toString().isEmpty()) {
            val4 = Integer.parseInt(editText4.getText().toString());
        }
        if (!editText5.getText().toString().isEmpty()) {
            val5 = Integer.parseInt(editText5.getText().toString());
        }
        int result = val1 + val2 + val3 + val4 + val5;
        return TextUtil.convertToExponential(result);
    }

    private String calculateGrandTotal(EditText editText1, EditText editText2, EditText editText3, EditText editText4, EditText editText5) {
        double val1 = 0.0;
        double val2 = 0.0;
        double val3 = 0.0;
        double val4 = 0.0;
        double val5 = 0.0;
        if (!editText1.getText().toString().isEmpty()) {
            val1 = Double.parseDouble(editText1.getText().toString());
        }
        if (!editText2.getText().toString().isEmpty()) {
            val2 = Double.parseDouble(editText2.getText().toString());
        }
        if (!editText3.getText().toString().isEmpty()) {
            val3 = Double.parseDouble(editText3.getText().toString());
        }
        if (!editText4.getText().toString().isEmpty()) {
            val4 = Double.parseDouble(editText4.getText().toString());
        }
        if (!editText5.getText().toString().isEmpty()) {
            val5 = Double.parseDouble(editText5.getText().toString());
        }
        double result = val1 + val2 + val3 + val4 + val5;
        return TextUtil.convertToExponential(result);
    }

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_CONDUIT_FILL);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);
    }

    private void fetchJsonData1() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_CONDUIT_FILL1);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculators, menu);
        this.menu = menu;
        if (application.sd.isBookmarked(bookmarkObj)) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
        } else {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_bookmark) {
            if (bookmarkObj.getBookmarkCalType() > 0) {
                application.sd.toggleBookmark(bookmarkObj);
                if (application.sd.isBookmarked(bookmarkObj)) {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                } else {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                }
            } else {
                showToast(getString(R.string.conduit_fill_validation), 1000);
            }
            return true;
        } else if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_bookmarks) {
            Intent intent = new Intent(this, BookmarksActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void operationInitialized() {

    }

    @Override
    public void operationStarted() {

    }

    @Override
    public void operationInProgress() {

    }

    @Override
    public void operationCompleted(Object object) {

    }

    @Override
    public void operationUpdated(Object object) {

    }

    @Override
    public void operationFailed(ESError error, Object object) {

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
