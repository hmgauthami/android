package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.ToFind;
import com.ascendlearning.jbl.uglys.models.ToFindKnownValues;
import com.ascendlearning.jbl.uglys.models.ToFindTypes;
import com.ascendlearning.jbl.uglys.utils.CompositeKey;
import com.ascendlearning.jbl.uglys.utils.Constants;
import com.ascendlearning.jbl.uglys.utils.DecimalDigitsInputFilter;
import com.ascendlearning.jbl.uglys.utils.ESError;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import org.json.JSONException;
import org.readium.sdk.android.components.navigation.NavigationElement;
import org.readium.sdk.android.components.navigation.NavigationPoint;
import org.readium.sdk.android.launcher.model.OpenPageRequest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ToFind1Calculator extends SuperActivity implements UICallback, Spinner.OnItemSelectedListener {
    private UglysController mController;
    private UglysResponse mResponse;
    private ArrayList<ToFind> toFindArrayList;
    private TableLayout to_find_known_value_view;
    private TableLayout to_find_known_value_view1;
    private TableLayout to_find_known_value_view2;
    private EditText to_find_known_value_editbox;
    private String Val1 = null;
    private String Val2 = null;
    private String Val3 = null;
    private String Val4 = null;
    private LinearLayout result_view;
    private LinearLayout result_view_hp_sp;
    private LinearLayout result_view_hp_tp;
    private LinearLayout result_view_kw_kva_sp;
    private LinearLayout result_view_kw_kva_tp;
    private LinearLayout multiplication_second_amps_view;
    private LinearLayout div_second_amps_view;
    private LinearLayout div_third_amps_view;
    private LinearLayout div_fourth_amps_view;
    private TextView result_unit_kW_kva;
    private TextView mul_from_unit_kW_kva;
    private TextView mul_to_unit_kW_kva;
    private TextView result_kW_kva;
    private TextView mul_from_kW_kva;
    private TextView mul_to_kW_kva;
    private TextView mul_to_kW_kva1;
    private TextView mul_to_unit_kW_kva1;
    private TextView div_from_unit_hp;
    private TextView div_from_unit_hp1;
    private TextView result_hp;
    private TextView div_from_hp;
    private TextView div_from_hp1;
    private TextView div_from_hp2;
    private TextView div_from_unit_hp2;
    private TextView div_from_unit_hp3;
    private TextView div_from_hp3;
    private TextView mul_from_unit_watts;
    private TextView mul_to_unit_watts;
    private TextView result_watts;
    private TextView mul_from_watts;
    private TextView mul_to_watts;
    private TextView mul_from_unit_amps;
    private TextView div_to_unit_amps;
    private TextView result_amps;
    private TextView mul_from_amps;
    private TextView div_to_amps;
    private TextView div_to_amps1;
    private TextView div_to_amps2;
    private TextView div_to_unit_amps1;
    private TextView div_to_unit_amps2;
    private TextView multiplication_value_amps;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private int bookmarkSubType;
    private String bookmarkName;
    private int toFindKnownVal;
    private int toFindCurrentType;
    private int calculatorType = -1;
    private Spinner spinner;
    private Spinner val_spinner;
    private Spinner type_spinner;
    private ArrayList<ToFindTypes> toFindTypesList;
    private TextView to_find_result_header;
    private ArrayList<String> inputValuesSecondLevel;
    private ArrayList<String> inputValuesFirstLevel;
    private ArrayList<ToFindKnownValues> knownValues;
    private TO_FIND_AMP selectedAmpsKnownValue;
    private RelativeLayout relatedContent_view;

    private enum TO_FIND {
        AMPS, HP, W, kW, KVA, C
    }

    private enum TO_FIND_AMP {
        HP, kW, PF, KVA
    }

    private enum TO_FIND_HP_KW_AMPS {
        DC, SP, TP
    }

    private enum TO_FIND_KVA_AMPS {
        SP, TP
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_find1_calculator);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbarTop);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        fetchJsonData();
        mResponse = mController.getJsonResponse();
        toFindArrayList = mResponse.getToFindList();

        bookmarkSubType = getIntent().getIntExtra("subtype", -1);
        toFindKnownVal = getIntent().getIntExtra("knownVal", -1);
        toFindCurrentType = getIntent().getIntExtra("currentType", -1);

        createBookmarkData();
        makeLayout();

    }

    private void makeBookmarkLayout() {
        switch (bookmarkSubType) {
            case 0:
                ampsClick();
                if (menu != null) {
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                }
                break;
            case 1:
                hpClick();
                if (menu != null) {
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                }
                break;
            case 2:
                wattsClick();
                if (menu != null) {
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                }
                break;
            case 3:
                kWClick();
                if (menu != null) {
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                }
                break;
            case 4:
                kVAClick();
                if (menu != null) {
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                }
                break;

        }
    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.TOFIND1.ordinal());
        bookmarkObj.setBookmarkCalType(bookmarkSubType);

    }

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_TO_FIND1);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);
    }

    private void ampsClick() {
        spinner.setSelection(bookmarkSubType + 1);
        spinner.setTag(R.id.spinner_pos, bookmarkSubType + 1);
        knownValues = toFindArrayList.get(bookmarkSubType).getKnownValues();
        //if we reset the first spinner when third spinner is visible
        if (knownValues.get(0).getFindTypesArrayList() == null) {
            type_spinner.setVisibility(View.GONE);
        }
        val_spinner.setVisibility(View.VISIBLE);
        to_find_result_header.setVisibility(View.GONE);
        to_find_known_value_view.setVisibility(View.GONE);
        to_find_known_value_view1.setVisibility(View.GONE);
        to_find_known_value_view2.setVisibility(View.GONE);
        reinitializeView();
        final ArrayList<String> toFindknownValList = new ArrayList<>();
        for (int i = 0; i < knownValues.size(); i++) {
            toFindknownValList.add(knownValues.get(i).getKnownValueName());
        }
        toFindknownValList.add(0, getString(R.string.spinner_known_value_default));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(ToFind1Calculator.this, R.layout.conversion_spinner_dropdown, toFindknownValList);
        val_spinner.setAdapter(adapter);
        val_spinner.setSelection(toFindKnownVal + 1);
        val_spinner.setTag(R.id.val_spinner_pos, toFindKnownVal + 1);

        inputValuesSecondLevel = knownValues.get(toFindKnownVal).getInputValues();
        toFindTypesList = knownValues.get(toFindKnownVal).getFindTypesArrayList();
        final ArrayList<String> typeNameList = new ArrayList<>();
        for (int i = 0; i < toFindTypesList.size(); i++) {
            typeNameList.add(toFindTypesList.get(i).getToFindTypeName());
        }
        type_spinner.setVisibility(View.VISIBLE);
        typeNameList.add(0, getString(R.string.spinner_current_type_default));
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(ToFind1Calculator.this, R.layout.conversion_spinner_dropdown, typeNameList);
        type_spinner.setAdapter(adapter1);
        type_spinner.setSelection(toFindCurrentType + 1);
        calculatorType = bookmarkSubType;
        bookmarkObj.setBookmarkCalType(calculatorType);
        bookmarkObj.setBookmarkToFindCurrentType(toFindCurrentType);
        bookmarkObj.setBookmarkToFindKnown(toFindKnownVal);
        reinitializeView();
        result_view = (LinearLayout) findViewById(R.id.result_view_amps);
        to_find_known_value_view.setVisibility(View.VISIBLE);
        selectedAmpsKnownValue = TO_FIND_AMP.values()[toFindKnownVal];
        drawAmperesView();
        drawInputValueView(inputValuesSecondLevel, to_find_known_value_view, "AMPS");
        ArrayList<String> inputValuesThirdLevel = toFindTypesList.get(toFindCurrentType).getInputValues();

        if (to_find_known_value_view1 != null) {
            int count = to_find_known_value_view1.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = to_find_known_value_view1.getChildAt(i);
                if (child instanceof TableRow)
                    ((ViewGroup) child).removeAllViews();
            }
        }

        to_find_known_value_view1.setVisibility(View.VISIBLE);

        if (selectedAmpsKnownValue == TO_FIND_AMP.KVA || selectedAmpsKnownValue == TO_FIND_AMP.PF) {
            showAmpsTypes1(TO_FIND_KVA_AMPS.values()[toFindCurrentType], selectedAmpsKnownValue, inputValuesThirdLevel);
        } else {
            showAmpsTypes(TO_FIND_HP_KW_AMPS.values()[toFindCurrentType], selectedAmpsKnownValue, inputValuesThirdLevel);

        }
        to_find_result_header.setVisibility(View.VISIBLE);
        result_view.setVisibility(View.VISIBLE);


    }

    private void hpClick() {
        spinner.setSelection(bookmarkSubType + 1, false);
        spinner.setTag(R.id.spinner_pos, bookmarkSubType + 1);
        knownValues = toFindArrayList.get(bookmarkSubType).getKnownValues();
        if (knownValues.get(0).getFindTypesArrayList() == null) {
            type_spinner.setVisibility(View.GONE);
        }
        val_spinner.setVisibility(View.VISIBLE);
        to_find_result_header.setVisibility(View.GONE);
        to_find_known_value_view.setVisibility(View.GONE);
        to_find_known_value_view1.setVisibility(View.GONE);
        to_find_known_value_view2.setVisibility(View.GONE);
        reinitializeView();
        final ArrayList<String> toFindknownValList = new ArrayList<>();
        for (int i = 0; i < knownValues.size(); i++) {
            toFindknownValList.add(knownValues.get(i).getKnownValueName());
        }
        toFindknownValList.add(0, getString(R.string.spinner_known_value_default));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(ToFind1Calculator.this, R.layout.conversion_spinner_dropdown, toFindknownValList);
        val_spinner.setAdapter(adapter);
        val_spinner.setSelection(toFindCurrentType + 1, false);
        val_spinner.setTag(R.id.val_spinner_pos, toFindCurrentType + 1);

        inputValuesSecondLevel = knownValues.get(toFindCurrentType).getInputValues();
        toFindTypesList = knownValues.get(toFindCurrentType).getFindTypesArrayList();
        calculatorType = bookmarkSubType;
        bookmarkObj.setBookmarkCalType(calculatorType);
        bookmarkObj.setBookmarkToFindCurrentType(toFindCurrentType);
        reinitializeView();
        result_view = (LinearLayout) findViewById(R.id.result_view_hp);
        showHPKnownValues(toFindCurrentType, inputValuesSecondLevel);
        to_find_result_header.setVisibility(View.VISIBLE);
        result_view.setVisibility(View.VISIBLE);
    }

    private void kWClick() {
        spinner.setSelection(bookmarkSubType + 1);
        spinner.setTag(R.id.spinner_pos, bookmarkSubType + 1);
        knownValues = toFindArrayList.get(bookmarkSubType).getKnownValues();
        if (knownValues.get(0).getFindTypesArrayList() == null) {
            type_spinner.setVisibility(View.GONE);
        }
        val_spinner.setVisibility(View.VISIBLE);
        to_find_result_header.setVisibility(View.GONE);
        to_find_known_value_view.setVisibility(View.GONE);
        to_find_known_value_view1.setVisibility(View.GONE);
        to_find_known_value_view2.setVisibility(View.GONE);
        reinitializeView();
        final ArrayList<String> toFindknownValList = new ArrayList<>();
        for (int i = 0; i < knownValues.size(); i++) {
            toFindknownValList.add(knownValues.get(i).getKnownValueName());
        }
        toFindknownValList.add(0, getString(R.string.spinner_known_value_default));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(ToFind1Calculator.this, R.layout.conversion_spinner_dropdown, toFindknownValList);
        val_spinner.setAdapter(adapter);
        val_spinner.setSelection(toFindCurrentType + 1);
        val_spinner.setTag(R.id.val_spinner_pos, toFindCurrentType + 1);

        inputValuesSecondLevel = knownValues.get(toFindCurrentType).getInputValues();
        toFindTypesList = knownValues.get(toFindCurrentType).getFindTypesArrayList();
        calculatorType = bookmarkSubType;
        bookmarkObj.setBookmarkCalType(calculatorType);
        bookmarkObj.setBookmarkToFindCurrentType(toFindCurrentType);
        reinitializeView();
        result_view = (LinearLayout) findViewById(R.id.result_view_kW_kva);
        showkwKnownValues(toFindCurrentType, inputValuesSecondLevel);
        to_find_result_header.setVisibility(View.VISIBLE);
        result_view.setVisibility(View.VISIBLE);
    }

    private void kVAClick() {
        spinner.setSelection(bookmarkSubType + 1);
        spinner.setTag(R.id.spinner_pos, bookmarkSubType + 1);
        knownValues = toFindArrayList.get(bookmarkSubType).getKnownValues();
        //if we reset the first spinner when third spinner is visible
        if (knownValues.get(0).getFindTypesArrayList() == null) {
            type_spinner.setVisibility(View.GONE);
        }
        val_spinner.setVisibility(View.VISIBLE);
        to_find_result_header.setVisibility(View.GONE);
        to_find_known_value_view.setVisibility(View.GONE);
        to_find_known_value_view1.setVisibility(View.GONE);
        to_find_known_value_view2.setVisibility(View.GONE);
        reinitializeView();
        final ArrayList<String> toFindknownValList = new ArrayList<>();
        for (int i = 0; i < knownValues.size(); i++) {
            toFindknownValList.add(knownValues.get(i).getKnownValueName());
        }
        toFindknownValList.add(0, getString(R.string.spinner_known_value_default));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(ToFind1Calculator.this, R.layout.conversion_spinner_dropdown, toFindknownValList);
        val_spinner.setAdapter(adapter);
        val_spinner.setSelection(toFindCurrentType + 1);
        val_spinner.setTag(R.id.val_spinner_pos, toFindCurrentType + 1);

        inputValuesSecondLevel = knownValues.get(toFindCurrentType).getInputValues();
        toFindTypesList = knownValues.get(toFindCurrentType).getFindTypesArrayList();
        calculatorType = bookmarkSubType;
        bookmarkObj.setBookmarkCalType(calculatorType);
        bookmarkObj.setBookmarkToFindCurrentType(toFindCurrentType);
        reinitializeView();
        result_view = (LinearLayout) findViewById(R.id.result_view_kW_kva);
        showKVAKnownValues(toFindCurrentType, inputValuesSecondLevel);
        to_find_result_header.setVisibility(View.VISIBLE);
        result_view.setVisibility(View.VISIBLE);
    }

    private void wattsClick() {
        spinner.setSelection(bookmarkSubType + 1);
        inputValuesFirstLevel = toFindArrayList.get(bookmarkSubType).getInputValues();
        type_spinner.setVisibility(View.GONE);
        val_spinner.setVisibility(View.GONE);
        reinitializeView();
        to_find_known_value_view.setVisibility(View.GONE);
        to_find_known_value_view1.setVisibility(View.GONE);
        to_find_known_value_view2.setVisibility(View.VISIBLE);
        result_view_hp_sp.setVisibility(View.GONE);
        result_view_hp_tp.setVisibility(View.GONE);
        calculatorType = TO_FIND.values()[bookmarkSubType].ordinal();
        bookmarkObj.setBookmarkCalType(calculatorType);
        result_view = (LinearLayout) findViewById(R.id.result_view_watts);
        mul_from_unit_watts = (TextView) findViewById(R.id.mul_from_unit_watts);
        mul_to_unit_watts = (TextView) findViewById(R.id.mul_to_unit_watts);
        result_watts = (TextView) findViewById(R.id.result_watts);
        mul_from_watts = (TextView) findViewById(R.id.mul_from_watts);
        mul_to_watts = (TextView) findViewById(R.id.mul_to_watts);
        mul_from_watts.setText("");
        mul_to_watts.setText("");
        result_watts.setText("");
        drawInputValueView(inputValuesFirstLevel, to_find_known_value_view2, "W");
        TextUtil.insertText(mul_from_unit_watts, "E");
        TextUtil.insertText(mul_to_unit_watts, "I");
        bookmarkName = "To Find Calculator: Watts";
        bookmarkObj.setBookmarkName("To Find Calculator: Watts");
        to_find_result_header.setVisibility(View.VISIBLE);
        result_view.setVisibility(View.VISIBLE);
    }

    private void makeLayout() {
        relatedContent_view = (RelativeLayout) findViewById(R.id.relatedContent_view);
        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().toLowerCase().contains("To Find".toLowerCase())) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(ToFind1Calculator.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, getIntent().getStringExtra("topic_name"));
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
        final ArrayList<String> toFindNameList = new ArrayList<>();
        final ArrayList<String> toFindTypeList = new ArrayList<>();
        for (int i = 0; i < toFindArrayList.size(); i++) {
            toFindNameList.add(toFindArrayList.get(i).getFindName());
            toFindTypeList.add(toFindArrayList.get(i).getType());
        }

        spinner = (Spinner) findViewById(R.id.to_find_spinner);
        val_spinner = (Spinner) findViewById(R.id.to_find_val_spinner);
        type_spinner = (Spinner) findViewById(R.id.to_find_type_spinner);
        to_find_known_value_view = (TableLayout) findViewById(R.id.to_find_known_value_view);
        to_find_known_value_view1 = (TableLayout) findViewById(R.id.to_find_known_value_view1);
        to_find_known_value_view2 = (TableLayout) findViewById(R.id.to_find_known_value_view2);
        to_find_result_header = (TextView) findViewById(R.id.to_find_result_header);
        result_view_hp_sp = (LinearLayout) findViewById(R.id.div_from_unit_hp_sp);
        result_view_hp_tp = (LinearLayout) findViewById(R.id.div_from_unit_hp_tp);
        result_view_kw_kva_sp = (LinearLayout) findViewById(R.id.div_from_unit_kW_kva_sp);
        result_view_kw_kva_tp = (LinearLayout) findViewById(R.id.div_from_unit_kW_kva_tp);
        multiplication_second_amps_view = (LinearLayout) findViewById(R.id.multiplication_second_amps_view);
        div_second_amps_view = (LinearLayout) findViewById(R.id.div_second_amps_view);
        div_third_amps_view = (LinearLayout) findViewById(R.id.div_third_amps_view);
        div_fourth_amps_view = (LinearLayout) findViewById(R.id.div_fourth_amps_view);

        toFindNameList.add(0, getString(R.string.spinner_to_find_default));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, toFindNameList);
        spinner.setAdapter(adapter);
        spinner.setSelection(0, false);
        makeBookmarkLayout();

        spinner.setOnItemSelectedListener(this);
        val_spinner.setOnItemSelectedListener(this);
        type_spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner selectedSpinner = (Spinner) parent;
        switch (selectedSpinner.getId()) {
            case R.id.to_find_spinner:
                Object tag = spinner.getTag(R.id.spinner_pos);
                if (tag != null) {
                    tag = tag;
                }
                if (position > 0 && tag != position) {
                    spinner.setTag(R.id.spinner_pos, position);
                    knownValues = toFindArrayList.get(position - 1).getKnownValues();
                    if (knownValues != null) {
                        //if we reset the first spinner when third spinner is visible
                        if (knownValues.get(0).getFindTypesArrayList() == null) {
                            type_spinner.setVisibility(View.GONE);
                        }
                        val_spinner.setVisibility(View.VISIBLE);
                        to_find_result_header.setVisibility(View.GONE);
                        to_find_known_value_view.setVisibility(View.GONE);
                        to_find_known_value_view1.setVisibility(View.GONE);
                        to_find_known_value_view2.setVisibility(View.GONE);
                        reinitializeView();
                        final ArrayList<String> toFindknownValList = new ArrayList<>();
                        for (int i = 0; i < knownValues.size(); i++) {
                            toFindknownValList.add(knownValues.get(i).getKnownValueName());
                        }
                        toFindknownValList.add(0, getString(R.string.spinner_known_value_default));
                        final ArrayAdapter<String> adapter = new ArrayAdapter<>(ToFind1Calculator.this, R.layout.conversion_spinner_dropdown, toFindknownValList);
                        val_spinner.setAdapter(adapter);
                        val_spinner.setSelection(0, false);
                        bookmarkObj.setBookmarkCalType(-1);
                        bookmarkObj.setBookmarkName(null);
                        bookmarkObj.setBookmarkToFindKnown(-1);
                        bookmarkObj.setBookmarkToFindCurrentType(-1);
                        if (menu != null) {
                            if (application.sd.isBookmarked(bookmarkObj)) {
                                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                            } else {
                                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                            }
                        }

                    } else {
                        inputValuesFirstLevel = toFindArrayList.get(position - 1).getInputValues();
                        type_spinner.setVisibility(View.GONE);
                        val_spinner.setVisibility(View.GONE);
                        reinitializeView();
                        to_find_known_value_view.setVisibility(View.GONE);
                        to_find_known_value_view1.setVisibility(View.GONE);
                        to_find_known_value_view2.setVisibility(View.VISIBLE);
                        result_view_hp_sp.setVisibility(View.GONE);
                        result_view_hp_tp.setVisibility(View.GONE);

                        calculatorType = TO_FIND.values()[spinner.getSelectedItemPosition() - 1].ordinal();
                        bookmarkObj.setBookmarkCalType(calculatorType);
                        if (calculatorType == TO_FIND.W.ordinal()) {
                            result_view = (LinearLayout) findViewById(R.id.result_view_watts);
                            mul_from_unit_watts = (TextView) findViewById(R.id.mul_from_unit_watts);
                            mul_to_unit_watts = (TextView) findViewById(R.id.mul_to_unit_watts);
                            result_watts = (TextView) findViewById(R.id.result_watts);
                            mul_from_watts = (TextView) findViewById(R.id.mul_from_watts);
                            mul_to_watts = (TextView) findViewById(R.id.mul_to_watts);
                            mul_from_watts.setText("");
                            mul_to_watts.setText("");
                            result_watts.setText("");
                            drawInputValueView(inputValuesFirstLevel, to_find_known_value_view2, "W");
                            TextUtil.insertText(mul_from_unit_watts, "E");
                            TextUtil.insertText(mul_to_unit_watts, "I");
                            bookmarkName = "To Find Calculator: Watts";
                            bookmarkObj.setBookmarkName("To Find Calculator: Watts");
                            if (menu != null) {
                                if (application.sd.isBookmarked(bookmarkObj)) {
                                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                                } else {
                                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                                }
                            }
                        }
                        to_find_result_header.setVisibility(View.VISIBLE);
                        result_view.setVisibility(View.VISIBLE);
                    }
                } else if (position == 0) {
                    spinner.setTag(R.id.spinner_pos, position);
                    type_spinner.setVisibility(View.GONE);
                    val_spinner.setVisibility(View.GONE);
                    to_find_result_header.setVisibility(View.GONE);
                    to_find_known_value_view.setVisibility(View.GONE);
                    to_find_known_value_view1.setVisibility(View.GONE);
                    to_find_known_value_view2.setVisibility(View.GONE);
                    to_find_result_header.setVisibility(View.GONE);
                    if (result_view != null)
                        result_view.setVisibility(View.GONE);
                }
                break;
            case R.id.to_find_val_spinner:
                tag = val_spinner.getTag(R.id.val_spinner_pos);
                if (tag != null) {
                    tag = tag;
                }
                if (position > 0 && tag != position) {
                    val_spinner.setTag(R.id.val_spinner_pos, position);
                    inputValuesSecondLevel = knownValues.get(position - 1).getInputValues();
                    toFindTypesList = knownValues.get(position - 1).getFindTypesArrayList();
                    if (toFindTypesList != null) {
                        final ArrayList<String> typeNameList = new ArrayList<>();
                        for (int i = 0; i < toFindTypesList.size(); i++) {
                            typeNameList.add(toFindTypesList.get(i).getToFindTypeName());
                        }
                        type_spinner.setVisibility(View.VISIBLE);
                        typeNameList.add(0, getString(R.string.spinner_current_type_default));
                        type_spinner.setSelection(0, false);
                        final ArrayAdapter<String> adapter = new ArrayAdapter<>(ToFind1Calculator.this, R.layout.conversion_spinner_dropdown, typeNameList);
                        type_spinner.setAdapter(adapter);
                        calculatorType = spinner.getSelectedItemPosition() - 1;
                        bookmarkObj.setBookmarkCalType(calculatorType);
                        if (calculatorType == TO_FIND.AMPS.ordinal()) {
                            reinitializeView();
                            result_view = (LinearLayout) findViewById(R.id.result_view_amps);
                            to_find_known_value_view.setVisibility(View.VISIBLE);
                            selectedAmpsKnownValue = TO_FIND_AMP.values()[position - 1];
                            drawAmperesView();
                            drawInputValueView(inputValuesSecondLevel, to_find_known_value_view, "AMPS");
                            bookmarkObj.setBookmarkCalType(-1);
                            bookmarkObj.setBookmarkName(null);
                            bookmarkObj.setBookmarkToFindKnown(-1);
                            bookmarkObj.setBookmarkToFindCurrentType(-1);
                            if (menu != null) {
                                if (application.sd.isBookmarked(bookmarkObj)) {
                                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                                } else {
                                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                                }
                            }

                        }
                    } else {
                        calculatorType = spinner.getSelectedItemPosition() - 1;
                        bookmarkObj.setBookmarkCalType(calculatorType);
                        if (spinner.getSelectedItemPosition() - 1 == TO_FIND.HP.ordinal()) {
                            reinitializeView();
                            result_view = (LinearLayout) findViewById(R.id.result_view_hp);
                            showHPKnownValues(position - 1, inputValuesSecondLevel);
                        } else if (spinner.getSelectedItemPosition() - 1 == TO_FIND.kW.ordinal()) {
                            reinitializeView();
                            result_view = (LinearLayout) findViewById(R.id.result_view_kW_kva);
                            showkwKnownValues(position - 1, inputValuesSecondLevel);
                        } else if (spinner.getSelectedItemPosition() - 1 == TO_FIND.KVA.ordinal()) {
                            reinitializeView();
                            result_view = (LinearLayout) findViewById(R.id.result_view_kW_kva);
                            showKVAKnownValues(position - 1, inputValuesSecondLevel);
                        }
                        to_find_result_header.setVisibility(View.VISIBLE);
                        result_view.setVisibility(View.VISIBLE);
                    }

                } else if (position == 0) {
                    val_spinner.setTag(R.id.val_spinner_pos, position);
                    to_find_result_header.setVisibility(View.GONE);
                    to_find_known_value_view.setVisibility(View.GONE);
                    to_find_known_value_view1.setVisibility(View.GONE);
                    to_find_result_header.setVisibility(View.GONE);
                    if (result_view != null)
                        result_view.setVisibility(View.GONE);
                    type_spinner.setVisibility(View.GONE);
                }
                break;
            case R.id.to_find_type_spinner:
                if (position > 0) {
                    ArrayList<String> inputValuesThirdLevel = toFindTypesList.get(position - 1).getInputValues();

                    if (to_find_known_value_view1 != null) {
                        int count = to_find_known_value_view1.getChildCount();
                        for (int i = 0; i < count; i++) {
                            View child = to_find_known_value_view1.getChildAt(i);
                            if (child instanceof TableRow)
                                ((ViewGroup) child).removeAllViews();
                        }
                    }

                    to_find_known_value_view1.setVisibility(View.VISIBLE);

                    if (selectedAmpsKnownValue == TO_FIND_AMP.KVA || selectedAmpsKnownValue == TO_FIND_AMP.PF) {
                        showAmpsTypes1(TO_FIND_KVA_AMPS.values()[position - 1], selectedAmpsKnownValue, inputValuesThirdLevel);
                    } else {
                        showAmpsTypes(TO_FIND_HP_KW_AMPS.values()[position - 1], selectedAmpsKnownValue, inputValuesThirdLevel);

                    }
                    to_find_result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                } else {
                    to_find_result_header.setVisibility(View.GONE);
                    to_find_known_value_view1.setVisibility(View.GONE);
                    to_find_known_value_view2.setVisibility(View.GONE);
                    to_find_result_header.setVisibility(View.GONE);
                    if (result_view != null)
                        result_view.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void reinitializeView() {
        if (result_view != null) {
            result_view.setVisibility(View.GONE);
            result_view = null;
        }
        if (to_find_known_value_view != null) {
            int count = to_find_known_value_view.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = to_find_known_value_view.getChildAt(i);
                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
            }
        }
        if (to_find_known_value_view1 != null) {
            int count = to_find_known_value_view1.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = to_find_known_value_view1.getChildAt(i);
                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
            }
        }
        if (to_find_known_value_view2 != null) {
            int count = to_find_known_value_view2.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = to_find_known_value_view2.getChildAt(i);
                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
            }
        }
    }

    private String ToFindWattsCalculator(double val1, double val2, TO_FIND type) {
        double result = 0;
        switch (type) {
            case W:
                result = val1 * val2;
                break;
            case C:
                result = val1 / val2;
                break;
        }

        return TextUtil.convertToExponential(result);

    }

    private String ToFindHPCalculator(double val1, double val2, double val3, double val4, TO_FIND_HP_KW_AMPS type) {
        double result = 0;
        switch (type) {
            case DC:
                result = (val1 * val2 * val3) / 746;
                break;
            case SP:
                result = (val1 * val2 * val3 * val4) / 746;
                break;
            case TP:
                result = (val1 * val2 * val3 * val4 * 1.73) / 746;
                break;
        }

        return TextUtil.convertToExponential(result);

    }

    private String ToFindkWCalculator(double val1, double val2, double val3, TO_FIND_HP_KW_AMPS type) {
        double result = 0;
        switch (type) {
            case DC:
                result = (val1 * val2) / 1000;
                break;
            case SP:
                result = (val1 * val2 * val3) / 1000;
                break;
            case TP:
                result = (val1 * val2 * val3 * 1.73) / 1000;
                break;
        }

        return TextUtil.convertToExponential(result);

    }

    private String ToFindKVACalculator(double val1, double val2, TO_FIND_KVA_AMPS type) {
        double result = 0;
        switch (type) {
            case SP:
                result = (val1 * val2) / 1000;
                break;
            case TP:
                result = (val1 * val2 * 1.73) / 1000;
                break;
        }

        return TextUtil.convertToExponential(result);

    }

    private String ToFindAMPSCalculator1(double val1, double val2, double val3, TO_FIND_KVA_AMPS type, TO_FIND_AMP selectedAmpsKnownValue) {
        double result = 0;
        switch (type) {
            case SP:
                switch (selectedAmpsKnownValue) {
                    case PF:
                        result = (val1) / (val2 * val3);
                        break;
                    case KVA:
                        result = (val1 * 1000) / val2;
                        break;
                }
                break;
            case TP:
                switch (selectedAmpsKnownValue) {
                    case PF:
                        result = (val1) / (val2 * val3 * 1.73);
                        break;
                    case KVA:
                        result = (val1 * 1000) / (val2 * 1.73);
                        break;
                }
                break;
        }

        return TextUtil.convertToExponential(result);

    }

    private String ToFindAMPSCalculator(double val1, double val2, double val3, double val4, TO_FIND_HP_KW_AMPS type, TO_FIND_AMP selectedAmpsKnownValue) {
        double result = 0;
        switch (type) {
            case DC:
                switch (selectedAmpsKnownValue) {
                    case kW:
                        result = (val1 * 1000) / val2;
                        break;
                    case HP:
                        result = (val1 * 746) / (val2 * val3);
                        break;
                }

                break;
            case SP:
                switch (selectedAmpsKnownValue) {
                    case kW:
                        result = (val1 * 1000) / (val2 * val3);
                        break;
                    case HP:
                        result = (val1 * 746) / (val2 * val3 * val4);
                        break;
                }
                break;
            case TP:
                switch (selectedAmpsKnownValue) {
                    case kW:
                        result = (val1 * 1000) / (val2 * val3 * 1.73);
                        break;
                    case HP:
                        result = (val1 * 746) / (val2 * val3 * val4 * 1.73);
                        break;
                }
                break;
        }
        return TextUtil.convertToExponential(result);

    }

    private void showKVAKnownValues(int val, ArrayList<String> inputVal) {

        final TO_FIND_KVA_AMPS position = TO_FIND_KVA_AMPS.values()[val];
        to_find_known_value_view.setVisibility(View.VISIBLE);
        to_find_known_value_view1.setVisibility(View.GONE);
        to_find_known_value_view2.setVisibility(View.GONE);
        result_unit_kW_kva = (TextView) findViewById(R.id.final_result_unit_kW_kva);
        mul_from_unit_kW_kva = (TextView) findViewById(R.id.mul_from_unit_kW_kva);
        mul_to_unit_kW_kva = (TextView) findViewById(R.id.mul_to_unit_kW_kva);
        result_kW_kva = (TextView) findViewById(R.id.final_result_rhs_kW_kva);
        mul_from_kW_kva = (TextView) findViewById(R.id.mul_from_kW_kva);
        mul_to_kW_kva = (TextView) findViewById(R.id.mul_to_kW_kva);
        TextView result_kW_kva1 = (TextView) findViewById(R.id.result_kW_kva);
        result_kW_kva1.setText("KVA");
        TextView final_result_lhs__kW_kva = (TextView) findViewById(R.id.final_result_lhs__kW_kva);
        final_result_lhs__kW_kva.setText("KVA");
        mul_from_kW_kva.setText("");
        mul_to_kW_kva.setText("");
        result_kW_kva.setText("");
        bookmarkObj.setBookmarkToFindCurrentType(position.ordinal());

        switch (position) {
            case SP:
                drawInputValueView(inputVal, to_find_known_value_view, "KVA_SP");
                result_view_kw_kva_sp.setVisibility(View.GONE);
                result_view_kw_kva_tp.setVisibility(View.GONE);
                TextUtil.insertText(result_unit_kW_kva, "KVA");
                TextUtil.insertText(mul_from_unit_kW_kva, "E");
                TextUtil.insertText(mul_to_unit_kW_kva, "I");
                bookmarkName = "To Find Calculator: _KVA - Single Phase";
                bookmarkObj.setBookmarkName(bookmarkName);

                break;
            case TP:
                drawInputValueView(inputVal, to_find_known_value_view, "KVA_TP");
                result_view_kw_kva_sp.setVisibility(View.GONE);
                result_view_kw_kva_tp.setVisibility(View.VISIBLE);
                TextUtil.insertText(result_unit_kW_kva, "KVA");
                TextUtil.insertText(mul_from_unit_kW_kva, "E");
                TextUtil.insertText(mul_to_unit_kW_kva, "I");
                bookmarkName = "To Find Calculator: _KVA - Three Phase";
                bookmarkObj.setBookmarkName(bookmarkName);

                break;
        }
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }
    }

    private void showkwKnownValues(int val, ArrayList<String> inputVal) {

        final TO_FIND_HP_KW_AMPS position = TO_FIND_HP_KW_AMPS.values()[val];
        to_find_known_value_view.setVisibility(View.VISIBLE);
        to_find_known_value_view1.setVisibility(View.GONE);
        to_find_known_value_view2.setVisibility(View.GONE);
        result_unit_kW_kva = (TextView) findViewById(R.id.final_result_unit_kW_kva);
        mul_from_unit_kW_kva = (TextView) findViewById(R.id.mul_from_unit_kW_kva);
        mul_to_unit_kW_kva = (TextView) findViewById(R.id.mul_to_unit_kW_kva);
        result_kW_kva = (TextView) findViewById(R.id.final_result_rhs_kW_kva);
        TextView result_kW_kva1 = (TextView) findViewById(R.id.result_kW_kva);
        result_kW_kva1.setText("KW");
        TextView final_result_lhs__kW_kva = (TextView) findViewById(R.id.final_result_lhs__kW_kva);
        final_result_lhs__kW_kva.setText("KW");
        mul_from_kW_kva = (TextView) findViewById(R.id.mul_from_kW_kva);
        mul_to_kW_kva = (TextView) findViewById(R.id.mul_to_kW_kva);
        mul_to_kW_kva1 = (TextView) findViewById(R.id.mul_to_kW_kva1);
        mul_to_unit_kW_kva1 = (TextView) findViewById(R.id.mul_to_unit_kW_kva1);

        mul_from_kW_kva.setText("");
        mul_to_kW_kva.setText("");
        mul_to_kW_kva1.setText("");
        result_kW_kva.setText("");
        bookmarkObj.setBookmarkToFindCurrentType(position.ordinal());

        switch (position) {
            case DC:
                result_view_kw_kva_sp.setVisibility(View.GONE);
                result_view_kw_kva_tp.setVisibility(View.GONE);
                drawInputValueView(inputVal, to_find_known_value_view, "DC_kW");
                TextUtil.insertText(result_unit_kW_kva, "KW");
                TextUtil.insertText(mul_from_unit_kW_kva, "E");
                TextUtil.insertText(mul_to_unit_kW_kva, "I");
                bookmarkName = "To Find Calculator: _KW - Direct Current";
                bookmarkObj.setBookmarkName(bookmarkName);

                break;
            case SP:
                result_view_kw_kva_sp.setVisibility(View.VISIBLE);
                result_view_kw_kva_tp.setVisibility(View.GONE);
                TextUtil.insertText(mul_to_unit_kW_kva1, "PF");
                drawInputValueView(inputVal, to_find_known_value_view, "SP_kW");
                TextUtil.insertText(result_unit_kW_kva, "KW");
                TextUtil.insertText(mul_from_unit_kW_kva, "E");
                TextUtil.insertText(mul_to_unit_kW_kva, "I");
                bookmarkName = "To Find Calculator: _KW - Single Phase";
                bookmarkObj.setBookmarkName(bookmarkName);

                break;
            case TP:
                TextUtil.insertText(mul_to_unit_kW_kva1, "PF");
                result_view_kw_kva_sp.setVisibility(View.VISIBLE);
                result_view_kw_kva_tp.setVisibility(View.VISIBLE);
                drawInputValueView(inputVal, to_find_known_value_view, "TP_kW");
                TextUtil.insertText(result_unit_kW_kva, "KW");
                TextUtil.insertText(mul_from_unit_kW_kva, "E");
                TextUtil.insertText(mul_to_unit_kW_kva, "I");
                bookmarkName = "To Find Calculator: _KW - Three Phase";
                bookmarkObj.setBookmarkName(bookmarkName);

                break;
        }
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }
    }

    private void showHPKnownValues(int val, ArrayList<String> inputVal) {

        final TO_FIND_HP_KW_AMPS position = TO_FIND_HP_KW_AMPS.values()[val];
        to_find_known_value_view.setVisibility(View.VISIBLE);
        to_find_known_value_view1.setVisibility(View.GONE);
        to_find_known_value_view2.setVisibility(View.GONE);
        div_from_unit_hp = (TextView) findViewById(R.id.div_from_unit_hp);
        div_from_unit_hp1 = (TextView) findViewById(R.id.div_from_unit_hp1);
        result_hp = (TextView) findViewById(R.id.final_result_rhs_hp);
        div_from_hp = (TextView) findViewById(R.id.div_from_hp);
        div_from_hp1 = (TextView) findViewById(R.id.div_from_hp1);
        div_from_hp2 = (TextView) findViewById(R.id.div_from_hp2);
        div_from_hp3 = (TextView) findViewById(R.id.div_from_hp3);
        div_from_unit_hp2 = (TextView) findViewById(R.id.div_from_unit_hp2);
        div_from_unit_hp3 = (TextView) findViewById(R.id.div_from_unit_hp3);
        div_from_hp.setText("");
        div_from_hp1.setText("");
        div_from_hp2.setText("");
        div_from_hp3.setText("");
        result_hp.setText("");

        bookmarkObj.setBookmarkToFindCurrentType(position.ordinal());
        switch (position) {
            case DC:
                drawInputValueView(inputVal, to_find_known_value_view, "HP_DC");
                result_view_hp_sp.setVisibility(View.GONE);
                result_view_hp_tp.setVisibility(View.GONE);
                TextUtil.insertText(div_from_unit_hp2, "%EFF");
                TextUtil.insertText(div_from_unit_hp, "E");
                TextUtil.insertText(div_from_unit_hp1, "I");
                bookmarkName = "To Find Calculator: _HP - Direct Current";
                bookmarkObj.setBookmarkName(bookmarkName);

                break;
            case SP:
                drawInputValueView(inputVal, to_find_known_value_view, "HP_SP");
                result_view_hp_sp.setVisibility(View.VISIBLE);
                result_view_hp_tp.setVisibility(View.GONE);
                TextUtil.insertText(div_from_unit_hp2, "%EFF");
                TextUtil.insertText(div_from_unit_hp3, "PF");
                TextUtil.insertText(div_from_unit_hp, "E");
                TextUtil.insertText(div_from_unit_hp1, "I");
                bookmarkName = "To Find Calculator: _HP - Single Phase";
                bookmarkObj.setBookmarkName(bookmarkName);

                break;
            case TP:
                drawInputValueView(inputVal, to_find_known_value_view, "HP_TP");
                result_view_hp_sp.setVisibility(View.VISIBLE);
                result_view_hp_tp.setVisibility(View.VISIBLE);
                TextUtil.insertText(div_from_unit_hp2, "%EFF");
                TextUtil.insertText(div_from_unit_hp3, "PF");
                TextUtil.insertText(div_from_unit_hp, "E");
                TextUtil.insertText(div_from_unit_hp1, "I");
                bookmarkName = "To Find Calculator: _HP - Three Phase";
                bookmarkObj.setBookmarkName(bookmarkName);
                break;
        }
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }
    }

    private void drawInputValueView(ArrayList<String> inputVal, TableLayout view, String type) {
        if (inputVal != null) {
            for (int i = 0; i < inputVal.size(); i++) {
                TableRow tbrow = new TableRow(this);
                TableRow.LayoutParams linearParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                tbrow.setBackgroundResource(R.drawable.cell_shape);
                tbrow.setLayoutParams(linearParams);
                tbrow.setOrientation(LinearLayout.HORIZONTAL);
                to_find_known_value_editbox = new EditText(this);
                to_find_known_value_editbox.setTag(type + "-" + inputVal.get(i));
                to_find_known_value_editbox.setBackgroundResource(R.drawable.bg_offsetbending_editbox);
                to_find_known_value_editbox.setGravity(Gravity.CENTER);
                to_find_known_value_editbox.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                to_find_known_value_editbox.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                to_find_known_value_editbox.addTextChangedListener(new CustomTextWatcher(to_find_known_value_editbox));
                to_find_known_value_editbox.setSingleLine(true);
                to_find_known_value_editbox.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
                if (i == inputVal.size() - 1) {
                    to_find_known_value_editbox.setImeOptions(EditorInfo.IME_ACTION_DONE);
                } else {
                    to_find_known_value_editbox.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                }

                TableRow.LayoutParams editTextParams = new TableRow.LayoutParams(200, TableRow.LayoutParams.WRAP_CONTENT);
                editTextParams.setMargins(20, 0, 10, -15);
                to_find_known_value_editbox.setLayoutParams(editTextParams);
                to_find_known_value_editbox.setOnEditorActionListener(new OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            to_find_known_value_editbox.setCursorVisible(false);
                        }
                        return false;
                    }
                });
                tbrow.addView(to_find_known_value_editbox);

                TextView to_find_known_value_txt = new TextView(this);
                TextUtil.insertText(to_find_known_value_txt, inputVal.get(i));
                to_find_known_value_txt.setTextColor(Color.BLACK);
                to_find_known_value_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                to_find_known_value_txt.setPadding(0, 15, 0, 15);
                tbrow.addView(to_find_known_value_txt);
                editTextParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                editTextParams.setMargins(10, 0, 10, 10);
                to_find_known_value_txt.setLayoutParams(editTextParams);

                view.addView(tbrow);
            }
        }
    }


    private void showAmpsTypes(final TO_FIND_HP_KW_AMPS position, final TO_FIND_AMP selectedAmpsKnownValue, ArrayList<String> inputVal) {
        switch (position) {
            case DC:
                switch (selectedAmpsKnownValue) {
                    case kW:
                        drawInputValueView(inputVal, to_find_known_value_view1, "AMPS_kW_DC");
                        multiplication_second_amps_view.setVisibility(View.VISIBLE);
                        TextUtil.insertText(multiplication_value_amps, "1000");
                        div_second_amps_view.setVisibility(View.GONE);
                        div_third_amps_view.setVisibility(View.GONE);
                        div_fourth_amps_view.setVisibility(View.GONE);
                        TextUtil.insertText(mul_from_unit_amps, "KW");
                        TextUtil.insertText(div_to_unit_amps, "E");
                        bookmarkName = "To Find Calculator: Amperes _- _KW - Direct Current";

                        break;
                    case HP:
                        drawInputValueView(inputVal, to_find_known_value_view1, "AMPS_HP_DC");
                        multiplication_second_amps_view.setVisibility(View.VISIBLE);
                        div_second_amps_view.setVisibility(View.VISIBLE);
                        div_third_amps_view.setVisibility(View.GONE);
                        div_fourth_amps_view.setVisibility(View.GONE);
                        TextUtil.insertText(multiplication_value_amps, "746");
                        TextUtil.insertText(mul_from_unit_amps, "HP");
                        TextUtil.insertText(div_to_unit_amps, "E");
                        TextUtil.insertText(div_to_unit_amps1, "%EFF");
                        bookmarkName = "To Find Calculator: Amperes _- _HP - Direct Current";
                        break;
                }
                break;
            case SP:
                switch (selectedAmpsKnownValue) {
                    case kW:
                        drawInputValueView(inputVal, to_find_known_value_view1, "AMPS_kW_SP");
                        multiplication_second_amps_view.setVisibility(View.VISIBLE);
                        div_second_amps_view.setVisibility(View.VISIBLE);
                        div_third_amps_view.setVisibility(View.GONE);
                        div_fourth_amps_view.setVisibility(View.GONE);
                        TextUtil.insertText(multiplication_value_amps, "1000");
                        TextUtil.insertText(mul_from_unit_amps, "KW");
                        TextUtil.insertText(div_to_unit_amps, "E");
                        TextUtil.insertText(div_to_unit_amps1, "PF");
                        bookmarkName = "To Find Calculator: Amperes _- _KW - Single Phase";

                        break;
                    case HP:
                        drawInputValueView(inputVal, to_find_known_value_view1, "AMPS_HP_SP");
                        multiplication_second_amps_view.setVisibility(View.VISIBLE);
                        div_second_amps_view.setVisibility(View.VISIBLE);
                        div_third_amps_view.setVisibility(View.VISIBLE);
                        div_fourth_amps_view.setVisibility(View.GONE);
                        TextUtil.insertText(multiplication_value_amps, "746");
                        TextUtil.insertText(mul_from_unit_amps, "HP");
                        TextUtil.insertText(div_to_unit_amps, "E");
                        TextUtil.insertText(div_to_unit_amps1, "%EFF");
                        TextUtil.insertText(div_to_unit_amps2, "PF");
                        bookmarkName = "To Find Calculator: Amperes _- _HP - Single Phase";

                        break;
                }

                break;
            case TP:
                switch (selectedAmpsKnownValue) {
                    case kW:
                        drawInputValueView(inputVal, to_find_known_value_view1, "AMPS_kW_TP");
                        multiplication_second_amps_view.setVisibility(View.VISIBLE);
                        div_second_amps_view.setVisibility(View.VISIBLE);
                        div_third_amps_view.setVisibility(View.GONE);
                        div_fourth_amps_view.setVisibility(View.VISIBLE);
                        TextUtil.insertText(multiplication_value_amps, "1000");
                        TextUtil.insertText(mul_from_unit_amps, "KW");
                        TextUtil.insertText(div_to_unit_amps, "E");
                        TextUtil.insertText(div_to_unit_amps1, "PF");
                        bookmarkName = "To Find Calculator: Amperes _- _KW - Three Phase";

                        break;
                    case HP:
                        drawInputValueView(inputVal, to_find_known_value_view1, "AMPS_HP_TP");
                        multiplication_second_amps_view.setVisibility(View.VISIBLE);
                        div_second_amps_view.setVisibility(View.VISIBLE);
                        div_third_amps_view.setVisibility(View.VISIBLE);
                        div_fourth_amps_view.setVisibility(View.VISIBLE);
                        TextUtil.insertText(multiplication_value_amps, "746");
                        TextUtil.insertText(mul_from_unit_amps, "HP");
                        TextUtil.insertText(div_to_unit_amps, "E");
                        TextUtil.insertText(div_to_unit_amps1, "%EFF");
                        TextUtil.insertText(div_to_unit_amps2, "PF");
                        bookmarkName = "To Find Calculator: Amperes _- _HP - Three Phase";

                        break;
                }

                break;

        }
        bookmarkObj.setBookmarkName(bookmarkName);
        bookmarkObj.setBookmarkToFindKnown(selectedAmpsKnownValue.ordinal());
        bookmarkObj.setBookmarkToFindCurrentType(position.ordinal());
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }

    }

    private void showAmpsTypes1(final TO_FIND_KVA_AMPS position, final TO_FIND_AMP selectedAmpsKnownValue, ArrayList<String> inputVal) {

        switch (position) {
            case SP:
                switch (selectedAmpsKnownValue) {
                    case PF:
                        to_find_known_value_editbox.setText(Val3);
                        multiplication_second_amps_view.setVisibility(View.GONE);
                        div_second_amps_view.setVisibility(View.VISIBLE);
                        div_third_amps_view.setVisibility(View.GONE);
                        div_fourth_amps_view.setVisibility(View.GONE);
                        TextUtil.insertText(mul_from_unit_amps, "P");
                        TextUtil.insertText(div_to_unit_amps, "E");
                        TextUtil.insertText(div_to_unit_amps1, "PF");

                        bookmarkName = "To Find Calculator: Amperes _- _PF - Single Phase";

                        break;
                    case KVA:
                        drawInputValueView(inputVal, to_find_known_value_view1, "AMPS_KVA_SP");
                        multiplication_second_amps_view.setVisibility(View.VISIBLE);
                        div_second_amps_view.setVisibility(View.GONE);
                        div_third_amps_view.setVisibility(View.GONE);
                        div_fourth_amps_view.setVisibility(View.GONE);
                        TextUtil.insertText(mul_from_unit_amps, "KVA");
                        TextUtil.insertText(div_to_unit_amps, "E");
                        TextUtil.insertText(multiplication_value_amps, "1000");
                        bookmarkName = "To Find Calculator: Amperes _- _KVA - Single Phase";

                        break;
                }
                break;
            case TP:
                switch (selectedAmpsKnownValue) {
                    case PF:
                        to_find_known_value_editbox.setText(Val3);
                        multiplication_second_amps_view.setVisibility(View.GONE);
                        div_second_amps_view.setVisibility(View.VISIBLE);
                        div_third_amps_view.setVisibility(View.GONE);
                        div_fourth_amps_view.setVisibility(View.VISIBLE);
                        TextUtil.insertText(mul_from_unit_amps, "P");
                        TextUtil.insertText(div_to_unit_amps, "E");
                        TextUtil.insertText(div_to_unit_amps1, "PF");
                        bookmarkName = "To Find Calculator: Amperes _- _PF - Three Phase";

                        break;
                    case KVA:
                        drawInputValueView(inputVal, to_find_known_value_view1, "AMPS_KVA_TP");
                        multiplication_second_amps_view.setVisibility(View.VISIBLE);
                        div_second_amps_view.setVisibility(View.GONE);
                        div_third_amps_view.setVisibility(View.GONE);
                        div_fourth_amps_view.setVisibility(View.VISIBLE);
                        TextUtil.insertText(mul_from_unit_amps, "KVA");
                        TextUtil.insertText(div_to_unit_amps, "E");
                        TextUtil.insertText(multiplication_value_amps, "1000");
                        bookmarkName = "To Find Calculator: Amperes _- _KVA - Three Phase";

                        break;
                }

                break;
        }
        bookmarkObj.setBookmarkName(bookmarkName);
        bookmarkObj.setBookmarkToFindKnown(selectedAmpsKnownValue.ordinal());
        bookmarkObj.setBookmarkToFindCurrentType(position.ordinal());
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }
    }

    private void drawAmperesView() {
        reinitializeAmpsView();
        result_amps = (TextView) findViewById(R.id.result_amps);
        result_amps.setVisibility(View.VISIBLE);
        mul_from_unit_amps = (TextView) findViewById(R.id.mul_from_unit_amps);
        mul_from_unit_amps.setVisibility(View.VISIBLE);
        div_to_unit_amps = (TextView) findViewById(R.id.div_to_unit_amps);
        div_to_unit_amps.setVisibility(View.VISIBLE);
        div_to_unit_amps1 = (TextView) findViewById(R.id.div_to_unit_amps1);
        div_to_unit_amps1.setVisibility(View.VISIBLE);
        div_to_unit_amps2 = (TextView) findViewById(R.id.div_to_unit_amps2);
        div_to_unit_amps2.setVisibility(View.VISIBLE);
        mul_from_amps = (TextView) findViewById(R.id.mul_from_amps);
        mul_from_amps.setVisibility(View.VISIBLE);
        div_to_amps = (TextView) findViewById(R.id.div_to_amps);
        div_to_amps.setVisibility(View.VISIBLE);
        div_to_amps1 = (TextView) findViewById(R.id.div_to_amps1);
        div_to_amps1.setVisibility(View.VISIBLE);
        div_to_amps2 = (TextView) findViewById(R.id.div_to_amps2);
        div_to_amps2.setVisibility(View.VISIBLE);
        multiplication_value_amps = (TextView) findViewById(R.id.multiplication_value_amps);
        multiplication_value_amps.setVisibility(View.VISIBLE);

        mul_from_amps.setText("");
        div_to_amps.setText("");
        div_to_amps1.setText("");
        div_to_amps2.setText("");
        result_amps.setText("");

    }

    private void reinitializeAmpsView() {
        if (result_amps != null) {
            result_amps.setVisibility(View.GONE);
            result_amps = null;
            mul_from_unit_amps.setVisibility(View.GONE);
            mul_from_unit_amps = null;
            div_to_unit_amps.setVisibility(View.GONE);
            div_to_unit_amps = null;
            div_to_unit_amps1.setVisibility(View.GONE);
            div_to_unit_amps1 = null;
            div_to_unit_amps2.setVisibility(View.GONE);
            div_to_unit_amps2 = null;
            mul_from_amps.setVisibility(View.GONE);
            mul_from_amps = null;
            div_to_amps.setVisibility(View.GONE);
            div_to_amps = null;
            div_to_amps1.setVisibility(View.GONE);
            div_to_amps1 = null;
            div_to_amps2.setVisibility(View.GONE);
            div_to_amps2 = null;
            multiplication_value_amps.setVisibility(View.GONE);
            multiplication_value_amps = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculators, menu);
        this.menu = menu;
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
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
            if (bookmarkName != null) {
                bookmarkObj.setBookmarkName(bookmarkName);
                bookmarkObj.setBookmarkCalType(calculatorType);
                application.sd.toggleBookmark(bookmarkObj);
                if (menu != null) {
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                }
            } else {
                showToast(getString(R.string.bookmark_validation), 1000);
            }

            return true;
        } else if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_search) {
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

    private class CustomTextWatcher implements TextWatcher {
        private EditText e;

        public CustomTextWatcher(EditText mEditText) {
            e = mEditText;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            String tag = e.getTag().toString();
            String[] splitArr = tag.split("-");
            switch (splitArr[0]) {
                case "W":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(mul_from_watts, Val1);
                        if (Val1.length() > 0) {
                            TextUtil.insertText(mul_from_unit_watts, "V");
                        } else {
                            TextUtil.insertText(mul_from_unit_watts, "E");
                        }


                    } else if (splitArr[1].equalsIgnoreCase("I (Amperes)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(mul_to_watts, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(mul_to_unit_watts, "A");
                        } else {
                            TextUtil.insertText(mul_to_unit_watts, "I");
                        }
                    }
                    if (Val1 != null && Val2 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0) {
                            String result = ToFindWattsCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), TO_FIND.W);
                            TextUtil.insertText(result_watts, result);
                        } else {
                            TextUtil.insertText(result_watts, "");
                        }
                    }
                    break;

                case "AMPS":
                    if (splitArr[1].equalsIgnoreCase("HP (Horsepower)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(mul_from_amps, Val1);
                        TextUtil.insertText(mul_from_unit_amps, "HP");

                    } else if (splitArr[1].equalsIgnoreCase("KW (KiloWatts)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(mul_from_amps, Val1);
                        TextUtil.insertText(mul_from_unit_amps, "KW");

                    } else if (splitArr[1].equalsIgnoreCase("P (Watts)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(mul_from_amps, Val1);
                        if (Val1.length() > 0) {
                            TextUtil.insertText(mul_from_unit_amps, "W");
                        } else {
                            TextUtil.insertText(mul_from_unit_amps, "P");
                        }

                    } else if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(div_to_amps, Val2);
                        if (Val1.length() > 0) {
                            TextUtil.insertText(div_to_unit_amps, "V");
                        } else {
                            TextUtil.insertText(div_to_unit_amps, "E");
                        }

                    } else if (splitArr[1].equalsIgnoreCase("PF (Power Factor)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(div_to_amps1, Val3);

                    } else if (splitArr[1].equalsIgnoreCase("KVA (KiloVolt")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(mul_from_amps, Val1);

                    }
                    if (Val1 != null && Val2 != null && Val3 != null) {
                        if (type_spinner.getSelectedItemPosition() == 1 && selectedAmpsKnownValue == TO_FIND_AMP.PF) {
                            TextUtil.insertText(mul_from_amps, Val1);
                            TextUtil.insertText(mul_from_unit_amps, "W");
                            TextUtil.insertText(div_to_amps, Val2);
                            if (Val2.length() > 0) {
                                TextUtil.insertText(div_to_unit_amps, "V");
                            } else {
                                TextUtil.insertText(div_to_unit_amps, "E");
                            }
                            TextUtil.insertText(div_to_amps1, Val3);

                            if (Val1 != null && Val2 != null && Val3 != null) {
                                if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0) {
                                    String result = ToFindAMPSCalculator1(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3) / 100, TO_FIND_KVA_AMPS.SP, TO_FIND_AMP.PF);
                                    TextUtil.insertText(result_amps, result);
                                } else {
                                    TextUtil.insertText(result_amps, "");
                                }
                            }
                        } else if (type_spinner.getSelectedItemPosition() == 2 && selectedAmpsKnownValue == TO_FIND_AMP.PF) {
                            TextUtil.insertText(mul_from_amps, Val1);
                            TextUtil.insertText(mul_from_unit_amps, "W");
                            TextUtil.insertText(div_to_amps, Val2);
                            if (Val2.length() > 0) {
                                TextUtil.insertText(div_to_unit_amps, "V");
                            } else {
                                TextUtil.insertText(div_to_unit_amps, "E");
                            }
                            TextUtil.insertText(div_to_amps1, Val3);
                            if (Val1 != null && Val2 != null && Val3 != null) {
                                if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0) {
                                    String result = ToFindAMPSCalculator1(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3) / 100, TO_FIND_KVA_AMPS.TP, TO_FIND_AMP.PF);
                                    TextUtil.insertText(result_amps, result);
                                } else {
                                    TextUtil.insertText(result_amps, "");
                                }
                            }
                        }
                    }
                    break;

                case "AMPS_kW_DC":
                    TextUtil.insertText(multiplication_value_amps, "1000");
                    TextUtil.insertText(mul_from_amps, Val1);
                    TextUtil.insertText(mul_from_unit_amps, "KW");
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(div_to_amps, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(div_to_unit_amps, "V");
                        } else {
                            TextUtil.insertText(div_to_unit_amps, "E");
                        }
                    }
                    if (Val1 != null && Val2 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0) {
                            String result = ToFindAMPSCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), 1, 1, TO_FIND_HP_KW_AMPS.DC, TO_FIND_AMP.kW);
                            TextUtil.insertText(result_amps, result);
                        } else {
                            TextUtil.insertText(result_amps, "");
                        }
                    }
                    break;
                case "AMPS_kW_SP":
                    TextUtil.insertText(multiplication_value_amps, "1000");
                    TextUtil.insertText(mul_from_amps, Val1);
                    TextUtil.insertText(mul_from_unit_amps, "KW");
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(div_to_amps, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(div_to_unit_amps, "V");
                        } else {
                            TextUtil.insertText(div_to_unit_amps, "E");
                        }
                    } else if (splitArr[1].equalsIgnoreCase("PF (Power Factor)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(div_to_amps1, Val3);
                    }
                    if (Val1 != null && Val2 != null && Val3 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0) {
                            String result = ToFindAMPSCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3) / 100, 1, TO_FIND_HP_KW_AMPS.SP, TO_FIND_AMP.kW);
                            TextUtil.insertText(result_amps, result);
                        } else {
                            TextUtil.insertText(result_amps, "");
                        }
                    }
                    break;
                case "AMPS_kW_TP":
                    TextUtil.insertText(multiplication_value_amps, "1000");
                    TextUtil.insertText(mul_from_amps, Val1);
                    TextUtil.insertText(mul_from_unit_amps, "KW");
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(div_to_amps, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(div_to_unit_amps, "V");
                        } else {
                            TextUtil.insertText(div_to_unit_amps, "E");
                        }
                    } else if (splitArr[1].equalsIgnoreCase("PF (Power Factor)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(div_to_amps1, Val3);
                    }
                    if (Val1 != null && Val2 != null && Val3 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0) {
                            String result = ToFindAMPSCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3) / 100, 1, TO_FIND_HP_KW_AMPS.TP, TO_FIND_AMP.kW);
                            TextUtil.insertText(result_amps, result);
                        } else {
                            TextUtil.insertText(result_amps, "");
                        }
                    }
                    break;
                case "AMPS_HP_DC":
                    TextUtil.insertText(multiplication_value_amps, "746");
                    TextUtil.insertText(mul_from_amps, Val1);
                    TextUtil.insertText(mul_from_unit_amps, "HP");
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(div_to_amps, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(div_to_unit_amps, "V");
                        } else {
                            TextUtil.insertText(div_to_unit_amps, "E");
                        }

                    } else if (splitArr[1].equalsIgnoreCase("%EFF (Efficiency)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(div_to_amps1, Val3);

                    }
                    if (Val1 != null && Val2 != null && Val3 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0) {
                            String result = ToFindAMPSCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3) / 100, 1, TO_FIND_HP_KW_AMPS.DC, TO_FIND_AMP.HP);
                            TextUtil.insertText(result_amps, result);
                        } else {
                            TextUtil.insertText(result_amps, "");
                        }
                    }
                    break;
                case "AMPS_HP_SP":
                    TextUtil.insertText(multiplication_value_amps, "746");
                    TextUtil.insertText(mul_from_amps, Val1);
                    TextUtil.insertText(mul_from_unit_amps, "HP");
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(div_to_amps, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(div_to_unit_amps, "V");
                        } else {
                            TextUtil.insertText(div_to_unit_amps, "E");
                        }
                    } else if (splitArr[1].equalsIgnoreCase("%EFF (Efficiency)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(div_to_amps1, Val3);


                    } else if (splitArr[1].equalsIgnoreCase("PF (Power Factor)")) {
                        Val4 = e.getText().toString();
                        TextUtil.insertText(div_to_amps2, Val4);
                    }
                    if (Val1 != null && Val2 != null && Val3 != null && Val4 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0 && Val4.length() > 0) {
                            String result = ToFindAMPSCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3) / 100, Double.parseDouble(Val4) / 100, TO_FIND_HP_KW_AMPS.SP, TO_FIND_AMP.HP);
                            TextUtil.insertText(result_amps, result);
                        } else {
                            TextUtil.insertText(result_amps, "");
                        }
                    }
                    break;
                case "AMPS_HP_TP":
                    TextUtil.insertText(multiplication_value_amps, "746");
                    TextUtil.insertText(mul_from_amps, Val1);
                    TextUtil.insertText(mul_from_unit_amps, "HP");
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(div_to_amps, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(div_to_unit_amps, "V");
                        } else {
                            TextUtil.insertText(div_to_unit_amps, "E");
                        }
                    } else if (splitArr[1].equalsIgnoreCase("%EFF (Efficiency)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(div_to_amps1, Val3);
                    } else if (splitArr[1].equalsIgnoreCase("PF (Power Factor)")) {
                        Val4 = e.getText().toString();
                        TextUtil.insertText(div_to_amps2, Val4);
                    }
                    if (Val1 != null && Val2 != null && Val3 != null && Val4 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0 && Val4.length() > 0) {
                            String result = ToFindAMPSCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3) / 100, Double.parseDouble(Val4) / 100, TO_FIND_HP_KW_AMPS.TP, TO_FIND_AMP.HP);
                            TextUtil.insertText(result_amps, result);
                        } else {
                            TextUtil.insertText(result_amps, "");
                        }
                    }
                    break;
                case "AMPS_KVA_SP":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(div_to_amps, Val2);
                        TextUtil.insertText(mul_from_amps, Val1);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(div_to_unit_amps, "V");
                        } else {
                            TextUtil.insertText(div_to_unit_amps, "E");
                        }
                    }
                    if (Val1 != null && Val2 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0) {
                            String result = ToFindAMPSCalculator1(Double.parseDouble(Val1), Double.parseDouble(Val2), 1, TO_FIND_KVA_AMPS.SP, TO_FIND_AMP.KVA);
                            TextUtil.insertText(result_amps, result);
                        } else {
                            TextUtil.insertText(result_amps, "");
                        }
                    }
                    break;
                case "AMPS_KVA_TP":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(div_to_amps, Val2);
                        TextUtil.insertText(mul_from_amps, Val1);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(div_to_unit_amps, "V");
                        } else {
                            TextUtil.insertText(div_to_unit_amps, "E");
                        }

                    }
                    if (Val1 != null && Val2 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0) {
                            String result = ToFindAMPSCalculator1(Double.parseDouble(Val1), Double.parseDouble(Val2), 1, TO_FIND_KVA_AMPS.TP, TO_FIND_AMP.KVA);
                            TextUtil.insertText(result_amps, result);
                        } else {
                            TextUtil.insertText(result_amps, "");
                        }
                    }
                    break;
                case "HP_DC":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val1 = e.getText().toString();
                        if (Val1.length() > 0) {
                            TextUtil.insertText(div_from_unit_hp, "V");
                        } else {
                            TextUtil.insertText(div_from_unit_hp, "E");
                        }
                        TextUtil.insertText(div_from_hp, Val1);

                    } else if (splitArr[1].equalsIgnoreCase("I (Amperes)")) {
                        Val2 = e.getText().toString();
                        if (Val2.length() > 0) {
                            TextUtil.insertText(div_from_unit_hp1, "A");
                        } else {
                            TextUtil.insertText(div_from_unit_hp1, "I");
                        }
                        TextUtil.insertText(div_from_hp1, Val2);

                    } else if (splitArr[1].equalsIgnoreCase("%EFF (Efficiency)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(div_from_hp2, Val3);

                    }
                    if (Val1 != null && Val2 != null && Val3 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0) {
                            String result = ToFindHPCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3) / 100, 1, TO_FIND_HP_KW_AMPS.DC);
                            TextUtil.insertText(result_hp, result);
                        } else {
                            TextUtil.insertText(result_hp, "");
                        }
                    }
                    break;
                case "HP_SP":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(div_from_unit_hp, "V");
                        if (Val1.length() > 0) {
                            TextUtil.insertText(div_from_unit_hp, "V");
                        } else {
                            TextUtil.insertText(div_from_unit_hp, "E");
                        }
                        TextUtil.insertText(div_from_hp, Val1);
                    } else if (splitArr[1].equalsIgnoreCase("I (Amperes)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(div_from_unit_hp1, "A");
                        if (Val2.length() > 0) {
                            TextUtil.insertText(div_from_unit_hp1, "A");
                        } else {
                            TextUtil.insertText(div_from_unit_hp1, "I");
                        }
                        TextUtil.insertText(div_from_hp1, Val2);
                    } else if (splitArr[1].equalsIgnoreCase("%EFF (Efficiency)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(div_from_hp2, Val3);
                    } else if (splitArr[1].equalsIgnoreCase("PF (Power Factor)")) {
                        Val4 = e.getText().toString();
                        TextUtil.insertText(div_from_hp3, Val4);
                    }
                    if (Val1 != null && Val2 != null && Val3 != null && Val4 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0 && Val4.length() > 0) {
                            String result = ToFindHPCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3) / 100, Double.parseDouble(Val4) / 100, TO_FIND_HP_KW_AMPS.SP);
                            TextUtil.insertText(result_hp, result);
                        } else {
                            TextUtil.insertText(result_hp, "");
                        }
                    }
                    break;
                case "HP_TP":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val1 = e.getText().toString();
                        if (Val1.length() > 0) {
                            TextUtil.insertText(div_from_unit_hp, "V");
                        } else {
                            TextUtil.insertText(div_from_unit_hp, "E");
                        }
                        TextUtil.insertText(div_from_hp, Val1);

                    } else if (splitArr[1].equalsIgnoreCase("I (Amperes)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(div_from_unit_hp1, "A");
                        if (Val2.length() > 0) {
                            TextUtil.insertText(div_from_unit_hp1, "A");
                        } else {
                            TextUtil.insertText(div_from_unit_hp1, "I");
                        }
                        TextUtil.insertText(div_from_hp1, Val2);
                    } else if (splitArr[1].equalsIgnoreCase("%EFF (Efficiency)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(div_from_hp2, Val3);

                    } else if (splitArr[1].equalsIgnoreCase("PF (Power Factor)")) {
                        Val4 = e.getText().toString();
                        TextUtil.insertText(div_from_hp3, Val4);
                    }
                    if (Val1 != null && Val2 != null && Val3 != null && Val4 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0 && Val4.length() > 0) {
                            String result = ToFindHPCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3) / 100, Double.parseDouble(Val4) / 100, TO_FIND_HP_KW_AMPS.TP);
                            TextUtil.insertText(result_hp, result);
                        } else {
                            TextUtil.insertText(result_hp, "");
                        }
                    }
                    break;
                case "DC_kW":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(mul_from_kW_kva, Val1);
                        if (Val1.length() > 0) {
                            TextUtil.insertText(mul_from_unit_kW_kva, "V");
                        } else {
                            TextUtil.insertText(mul_from_unit_kW_kva, "E");
                        }

                    } else if (splitArr[1].equalsIgnoreCase("I (Amperes)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(mul_to_kW_kva, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(mul_to_unit_kW_kva, "A");
                        } else {
                            TextUtil.insertText(mul_to_unit_kW_kva, "I");
                        }
                    }
                    if (Val1 != null && Val2 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0) {
                            String result = ToFindkWCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), 1, TO_FIND_HP_KW_AMPS.DC);
                            TextUtil.insertText(result_kW_kva, result);
                            TextUtil.insertText(result_unit_kW_kva, "KW");
                        } else {
                            TextUtil.insertText(result_kW_kva, "");
                        }
                    }
                    break;
                case "SP_kW":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(mul_from_kW_kva, Val1);
                        if (Val1.length() > 0) {
                            TextUtil.insertText(mul_from_unit_kW_kva, "V");
                        } else {
                            TextUtil.insertText(mul_from_unit_kW_kva, "E");
                        }
                    } else if (splitArr[1].equalsIgnoreCase("I (Amperes)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(mul_to_kW_kva, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(mul_to_unit_kW_kva, "A");
                        } else {
                            TextUtil.insertText(mul_to_unit_kW_kva, "I");
                        }
                    } else if (splitArr[1].equalsIgnoreCase("PF (Power Factor)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(mul_to_kW_kva1, Val3);

                    }
                    if (Val1 != null && Val2 != null && Val3 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0) {
                            String result = ToFindkWCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3) / 100, TO_FIND_HP_KW_AMPS.SP);
                            TextUtil.insertText(result_kW_kva, result);
                            TextUtil.insertText(result_unit_kW_kva, "KW");
                        } else {
                            TextUtil.insertText(result_kW_kva, "");
                        }
                    }
                    break;
                case "TP_kW":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(mul_from_kW_kva, Val1);
                        if (Val1.length() > 0) {
                            TextUtil.insertText(mul_from_unit_kW_kva, "V");
                        } else {
                            TextUtil.insertText(mul_from_unit_kW_kva, "E");
                        }
                    } else if (splitArr[1].equalsIgnoreCase("I (Amperes)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(mul_to_kW_kva, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(mul_to_unit_kW_kva, "A");
                        } else {
                            TextUtil.insertText(mul_to_unit_kW_kva, "I");
                        }
                    } else if (splitArr[1].equalsIgnoreCase("PF (Power Factor)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(mul_to_kW_kva1, Val3);
                    }
                    if (Val1 != null && Val2 != null && Val3 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0) {
                            String result = ToFindkWCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3) / 100, TO_FIND_HP_KW_AMPS.TP);
                            TextUtil.insertText(result_kW_kva, result);
                            TextUtil.insertText(result_unit_kW_kva, "KW");
                        } else {
                            TextUtil.insertText(result_kW_kva, "");
                        }
                    }
                    break;
                case "KVA_SP":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(mul_from_kW_kva, Val1);
                        if (Val1.length() > 0) {
                            TextUtil.insertText(mul_from_unit_kW_kva, "V");
                        } else {
                            TextUtil.insertText(mul_from_unit_kW_kva, "E");
                        }
                    } else if (splitArr[1].equalsIgnoreCase("I (Amperes)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(mul_to_kW_kva, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(mul_to_unit_kW_kva, "A");
                        } else {
                            TextUtil.insertText(mul_to_unit_kW_kva, "I");
                        }
                    }
                    if (Val1 != null && Val2 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0) {
                            String result = ToFindKVACalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), TO_FIND_KVA_AMPS.SP);
                            TextUtil.insertText(result_kW_kva, result);
                            TextUtil.insertText(result_unit_kW_kva, "KVA");
                        } else {
                            TextUtil.insertText(result_kW_kva, "");
                        }
                    }
                    break;
                case "KVA_TP":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(mul_from_kW_kva, Val1);
                        if (Val1.length() > 0) {
                            TextUtil.insertText(mul_from_unit_kW_kva, "V");
                        } else {
                            TextUtil.insertText(mul_from_unit_kW_kva, "E");
                        }
                    } else if (splitArr[1].equalsIgnoreCase("I (Amperes)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(mul_to_kW_kva, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(mul_to_unit_kW_kva, "A");
                        } else {
                            TextUtil.insertText(mul_to_unit_kW_kva, "I");
                        }
                    }
                    if (Val1 != null && Val2 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0) {
                            String result = ToFindKVACalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), TO_FIND_KVA_AMPS.TP);
                            TextUtil.insertText(result_kW_kva, result);
                            TextUtil.insertText(result_unit_kW_kva, "KVA");
                        } else {
                            TextUtil.insertText(result_kW_kva, "");
                        }
                    }
                    break;
            }
        }
    }

}
