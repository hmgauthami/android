package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
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

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.ToFind;
import com.ascendlearning.jbl.uglys.models.ToFindKnownValues;
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

public class ToFind2Calculator extends SuperActivity implements UICallback, Spinner.OnItemSelectedListener {
    private final int COIL_COUNT = 5;
    private UglysController mController;
    private UglysResponse mResponse;
    private ArrayList<ToFind> toFindArrayList;
    private Spinner spinner;
    private Spinner val_spinner;
    private TableLayout to_find_known_value_view;
    private TextView to_find_result_header;
    private LinearLayout result_view;
    private EditText to_find_known_value_editbox;
    private TextView add_parallel_inductance1;
    private TextView add_parallel_inductance2;
    private TextView add_parallel_inductance3;
    private TextView add_parallel_inductance4;
    private TextView add_parallel_inductance5;
    private TextView parallel_inductance_symbol1;
    private TextView parallel_inductance_symbol2;
    private TextView parallel_inductance_symbol3;
    private TextView parallel_inductance_symbol4;
    private LinearLayout parallel_inductance_view1;
    private LinearLayout parallel_inductance_view2;
    private LinearLayout parallel_inductance_view3;
    private LinearLayout parallel_inductance_view4;
    private LinearLayout parallel_inductance_view5;
    private TextView result_parallel_inductance;
    private TextView add_series_inductance1_symbol;
    private TextView add_series_inductance2_symbol;
    private TextView add_series_inductance3_symbol;
    private TextView add_series_inductance4_symbol;
    private TextView final_result_parallel_inductance_value;
    private TextView final_result_series_inductance_value;
    private TextView reactanceUnit1;
    private TextView reactanceUnit2;
    private TextView inductanceUnit1;
    private TextView inductanceUnit2;
    private TextView inductanceUnit3;
    private TextView inductanceUnit4;
    private TextView inductanceUnit5;
    private EditText coil_count;
    private TextView coil_count_header;
    private ArrayList<ToFindKnownValues> knownValues;
    private TO_FIND pos;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private int bookmarkSubType;
    private String bookmarkName;
    private int toFindKnownVal;
    private ArrayList<String> inputValuesSecondLevel;
    private TextView div_from_unit_cap;
    private TextView div_to_unit_cap;
    TextView result_cap;
    private TextView div_from_cap;
    private TextView div_to_cap;
    private int calculatorType = -1;
    private String Val1 = null;
    private String Val2 = null;
    private String Val3 = null;
    private String Val4 = null;
    private String Val5 = null;
    private TextView final_result_parallel_inductance_unit;
    private RelativeLayout relatedContent_view;

    private enum TO_FIND {
        C, INDUC, REACT
    }

    private enum TO_FIND_CALCULATOR {
        C, INDUC_S, INDUC_P, REACT_I, REACT_C
    }


    private enum TO_FIND_INDUCTANCE {
        SERIES, PARALLEL
    }

    private enum TO_FIND_REACTANCE {
        INDUC, CAP
    }

    private enum TO_FIND_CAPACITANCE {
        CV, SERIES, PARALLEL
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_find2_calculator);

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

        createBookmarkData();
        makeLayout();
    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.TOFIND2.ordinal());
        bookmarkObj.setBookmarkCalType(bookmarkSubType);
    }

    private void makeLayout() {
        relatedContent_view = (RelativeLayout) findViewById(R.id.relatedContent_view);

        final ArrayList<String> toFindNameList = new ArrayList<>();
        final ArrayList<String> toFindTypeList = new ArrayList<>();
        for (int i = 0; i < toFindArrayList.size(); i++) {
            toFindNameList.add(toFindArrayList.get(i).getFindName());
            toFindTypeList.add(toFindArrayList.get(i).getType());
        }
        spinner = (Spinner) findViewById(R.id.to_find_spinner);
        val_spinner = (Spinner) findViewById(R.id.to_find_val_spinner);
        to_find_known_value_view = (TableLayout) findViewById(R.id.to_find_known_value_view);
        to_find_result_header = (TextView) findViewById(R.id.to_find_result_header);

        toFindNameList.add(0, getString(R.string.spinner_to_find_default));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, toFindNameList);
        spinner.setAdapter(adapter);
        spinner.setSelection(0, false);

        spinner.setOnItemSelectedListener(this);
        val_spinner.setOnItemSelectedListener(this);


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
                            Intent intent = new Intent(ToFind2Calculator.this, BookViewActivity.class);
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

        makeBookmarkLayout();
    }

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_TO_FIND2);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);
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

    }

    private void makeBookmarkLayout() {
        switch (bookmarkSubType) {
            case 0:
                capacitanceClick();
                if (menu != null) {
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                }
                break;
            case 1:
                inductanceClick();
                if (menu != null) {
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                }
                break;
            case 2:
                reactanceClick();
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
                to_find_known_value_editbox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

    private void inductanceClick() {
        spinner.setSelection(bookmarkSubType + 1);
        spinner.setTag(R.id.spinner_pos, bookmarkSubType + 1);
        knownValues = toFindArrayList.get(bookmarkSubType).getKnownValues();
        pos = TO_FIND.values()[bookmarkSubType];        //if we reset the first spinner when third spinner is visible
        val_spinner.setVisibility(View.VISIBLE);
        to_find_result_header.setVisibility(View.GONE);
        to_find_known_value_view.setVisibility(View.GONE);
        reinitializeView();
        final ArrayList<String> toFindknownValList = new ArrayList<>();
        for (int i = 0; i < knownValues.size(); i++) {
            toFindknownValList.add(knownValues.get(i).getKnownValueName());
        }
        toFindknownValList.add(0, getString(R.string.spinner_default));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(ToFind2Calculator.this, R.layout.conversion_spinner_dropdown, toFindknownValList);
        val_spinner.setAdapter(adapter);
        val_spinner.setSelection(toFindKnownVal + 1, false);
        val_spinner.setTag(R.id.val_spinner_pos, toFindKnownVal + 1);

        inputValuesSecondLevel = knownValues.get(toFindKnownVal).getInputValues();
        calculatorType = bookmarkSubType;
        bookmarkObj.setBookmarkCalType(calculatorType);
        bookmarkObj.setBookmarkToFindKnown(toFindKnownVal);
        reinitializeView();
        showIndutanceKnownValues(toFindKnownVal, inputValuesSecondLevel);
        to_find_result_header.setVisibility(View.VISIBLE);
        result_view.setVisibility(View.VISIBLE);
    }

    private void reactanceClick() {
        spinner.setSelection(bookmarkSubType + 1);
        spinner.setTag(R.id.spinner_pos, bookmarkSubType + 1);
        knownValues = toFindArrayList.get(bookmarkSubType).getKnownValues();
        pos = TO_FIND.values()[bookmarkSubType];        //if we reset the first spinner when third spinner is visible
        val_spinner.setVisibility(View.VISIBLE);
        to_find_result_header.setVisibility(View.GONE);
        to_find_known_value_view.setVisibility(View.GONE);
        reinitializeView();
        final ArrayList<String> toFindknownValList = new ArrayList<>();
        for (int i = 0; i < knownValues.size(); i++) {
            toFindknownValList.add(knownValues.get(i).getKnownValueName());
        }
        toFindknownValList.add(0, getString(R.string.spinner_default));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(ToFind2Calculator.this, R.layout.conversion_spinner_dropdown, toFindknownValList);
        val_spinner.setAdapter(adapter);
        val_spinner.setSelection(toFindKnownVal + 1, false);
        val_spinner.setTag(R.id.val_spinner_pos, toFindKnownVal + 1);

        inputValuesSecondLevel = knownValues.get(toFindKnownVal).getInputValues();
        calculatorType = bookmarkSubType;
        bookmarkObj.setBookmarkCalType(calculatorType);
        bookmarkObj.setBookmarkToFindKnown(toFindKnownVal);
        reinitializeView();
        showReactanceKnownValues(toFindKnownVal, inputValuesSecondLevel);
        to_find_result_header.setVisibility(View.VISIBLE);
        result_view.setVisibility(View.VISIBLE);
    }

    private void capacitanceClick() {
        spinner.setSelection(bookmarkSubType + 1);
        spinner.setTag(R.id.spinner_pos, bookmarkSubType + 1);
        knownValues = toFindArrayList.get(bookmarkSubType).getKnownValues();
        pos = TO_FIND.values()[bookmarkSubType];        //if we reset the first spinner when third spinner is visible
        val_spinner.setVisibility(View.VISIBLE);
        to_find_result_header.setVisibility(View.GONE);
        to_find_known_value_view.setVisibility(View.GONE);
        reinitializeView();
        final ArrayList<String> toFindknownValList = new ArrayList<>();
        for (int i = 0; i < knownValues.size(); i++) {
            toFindknownValList.add(knownValues.get(i).getKnownValueName());
        }
        toFindknownValList.add(0, getString(R.string.spinner_default));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(ToFind2Calculator.this, R.layout.conversion_spinner_dropdown, toFindknownValList);
        val_spinner.setAdapter(adapter);
        val_spinner.setSelection(toFindKnownVal + 1, false);
        val_spinner.setTag(R.id.val_spinner_pos, toFindKnownVal + 1);

        inputValuesSecondLevel = knownValues.get(toFindKnownVal).getInputValues();
        calculatorType = bookmarkSubType;
        bookmarkObj.setBookmarkCalType(calculatorType);
        bookmarkObj.setBookmarkToFindKnown(toFindKnownVal);
        reinitializeView();
        showCapacitanceKnownValues(toFindKnownVal, inputValuesSecondLevel);
        to_find_result_header.setVisibility(View.VISIBLE);
        result_view.setVisibility(View.VISIBLE);
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
                    if (coil_count != null) {
                        coil_count.setVisibility(View.GONE);
                        coil_count_header.setVisibility(View.GONE);
                    }
                    spinner.setTag(R.id.spinner_pos, position);
                    val_spinner.setTag(R.id.val_spinner_pos, null);
                    knownValues = toFindArrayList.get(position - 1).getKnownValues();
                    pos = TO_FIND.values()[spinner.getSelectedItemPosition() - 1];
                    val_spinner.setVisibility(View.VISIBLE);
                    to_find_result_header.setVisibility(View.GONE);
                    reinitializeView();

                    final ArrayList<String> toFindknownValList = new ArrayList<>();
                    for (int i = 0; i < knownValues.size(); i++) {
                        toFindknownValList.add(knownValues.get(i).getKnownValueName());
                    }
                    toFindknownValList.add(0, getString(R.string.spinner_default));
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(ToFind2Calculator.this, R.layout.conversion_spinner_dropdown, toFindknownValList);
                    val_spinner.setAdapter(adapter);
                    val_spinner.setSelection(0, false);
                    bookmarkName = null;
                    bookmarkObj.setBookmarkCalType(-1);
                    bookmarkObj.setBookmarkName(null);
                    bookmarkObj.setBookmarkToFindKnown(-1);
                    if (menu != null) {
                        if (application.sd.isBookmarked(bookmarkObj)) {
                            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                        } else {
                            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                        }
                    }

                } else if (position == 0) {
                    spinner.setTag(R.id.spinner_pos, position);
                    to_find_result_header.setVisibility(View.GONE);
                    if (result_view != null)
                        result_view.setVisibility(View.GONE);
                    val_spinner.setVisibility(View.GONE);
                    if (coil_count != null) {
                        coil_count.setVisibility(View.GONE);
                        coil_count_header.setVisibility(View.GONE);
                    }
                    to_find_known_value_view.setVisibility(View.GONE);
                    bookmarkObj.setBookmarkCalType(-1);
                    bookmarkObj.setBookmarkName(null);
                    bookmarkObj.setBookmarkToFindKnown(-1);
                    bookmarkName = null;
                    if (menu != null) {
                        if (application.sd.isBookmarked(bookmarkObj)) {
                            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                        } else {
                            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                        }
                    }
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
                    calculatorType = spinner.getSelectedItemPosition() - 1;
                    bookmarkObj.setBookmarkCalType(calculatorType);
                    if (spinner.getSelectedItemPosition() - 1 == TO_FIND.INDUC.ordinal()) {
                        reinitializeView();
                        showIndutanceKnownValues(position - 1, inputValuesSecondLevel);
                    } else if (spinner.getSelectedItemPosition() - 1 == TO_FIND.REACT.ordinal()) {
                        reinitializeView();
                        showReactanceKnownValues(position - 1, inputValuesSecondLevel);
                        to_find_result_header.setVisibility(View.VISIBLE);
                        result_view.setVisibility(View.VISIBLE);
                    } else if (spinner.getSelectedItemPosition() - 1 == TO_FIND.C.ordinal()) {
                        reinitializeView();
                        showCapacitanceKnownValues(position - 1, inputValuesSecondLevel);
                    }
                } else if (position == 0) {
                    to_find_result_header.setVisibility(View.GONE);
                    if (result_view != null)
                        result_view.setVisibility(View.GONE);
                    if (coil_count != null) {
                        coil_count.setVisibility(View.GONE);
                        coil_count_header.setVisibility(View.GONE);
                    }
                    if (to_find_known_value_view != null) {
                        int count = to_find_known_value_view.getChildCount();
                        for (int i = 0; i < count; i++) {
                            View child = to_find_known_value_view.getChildAt(i);
                            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                        }
                    }
                }
                break;
        }
    }

    private void showCapacitanceKnownValues(int val, final ArrayList<String> inputVal) {
        final TO_FIND_CAPACITANCE position = TO_FIND_CAPACITANCE.values()[val];
        to_find_known_value_view.setVisibility(View.VISIBLE);
        bookmarkObj.setBookmarkToFindKnown(position.ordinal());
        final ArrayList<String> coilCountInputVals = new ArrayList<>();
        switch (position) {
            case CV:
                result_view = (LinearLayout) findViewById(R.id.result_view_cap);
                to_find_result_header.setVisibility(View.VISIBLE);
                result_view.setVisibility(View.VISIBLE);
                div_from_unit_cap = (TextView) findViewById(R.id.div_from_unit_cap);
                div_to_unit_cap = (TextView) findViewById(R.id.div_to_unit_cap);
                result_cap = (TextView) findViewById(R.id.result_cap);
                div_from_cap = (TextView) findViewById(R.id.div_from_cap);
                div_to_cap = (TextView) findViewById(R.id.div_to_cap);
                coil_count = (EditText) findViewById(R.id.coil_count);
                coil_count_header = (TextView) findViewById(R.id.coil_count_header);
                coil_count.setVisibility(View.GONE);
                coil_count_header.setVisibility(View.GONE);
                result_cap.setText("");
                div_from_cap.setText("");
                div_to_cap.setText("");
                TextUtil.insertText(div_from_unit_cap, "Q");
                TextUtil.insertText(div_to_unit_cap, "E");
                bookmarkName = "To Find Calculator: Capacitance - Coulombs and Volts are Known";
                bookmarkObj.setBookmarkName(bookmarkName);
                drawInputValueView(inputVal, to_find_known_value_view, "C_CV");
                break;
            case PARALLEL:
                TextView final_result_series_inductance_unit = (TextView) findViewById(R.id.final_result_series_inductance_unit);
                final_result_series_inductance_unit.setText("microfarads");
                TextView result_series_inductance_value = (TextView) findViewById(R.id.result_series_inductance_value);
                result_series_inductance_value.setText(Html.fromHtml("C<sub><small><small>T</small></small></sub>"));
                TextView result_series_inductance = (TextView) findViewById(R.id.result_series_inductance);
                result_series_inductance.setText(Html.fromHtml("C<sub><small><small>T</small></small></sub>"));
                final_result_series_inductance_value = (TextView) findViewById(R.id.final_result_series_inductance_value);
                result_view = (LinearLayout) findViewById(R.id.result_view_series_inductance);
                add_parallel_inductance1 = (TextView) findViewById(R.id.add_series_inductance1);
                add_parallel_inductance2 = (TextView) findViewById(R.id.add_series_inductance2);
                add_parallel_inductance3 = (TextView) findViewById(R.id.add_series_inductance3);
                add_parallel_inductance4 = (TextView) findViewById(R.id.add_series_inductance4);
                add_parallel_inductance5 = (TextView) findViewById(R.id.add_series_inductance5);
                add_series_inductance1_symbol = (TextView) findViewById(R.id.add_series_inductance1_symbol);
                add_series_inductance2_symbol = (TextView) findViewById(R.id.add_series_inductance2_symbol);
                add_series_inductance3_symbol = (TextView) findViewById(R.id.add_series_inductance3_symbol);
                add_series_inductance4_symbol = (TextView) findViewById(R.id.add_series_inductance4_symbol);
                inductanceUnit1 = (TextView) findViewById(R.id.add_series_inductance1_unit);
                inductanceUnit2 = (TextView) findViewById(R.id.add_series_inductance2_unit);
                inductanceUnit3 = (TextView) findViewById(R.id.add_series_inductance3_unit);
                inductanceUnit4 = (TextView) findViewById(R.id.add_series_inductance4_unit);
                inductanceUnit5 = (TextView) findViewById(R.id.add_series_inductance5_unit);
                coil_count = (EditText) findViewById(R.id.coil_count);
                coil_count.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
                coil_count_header = (TextView) findViewById(R.id.coil_count_header);
                coil_count.setVisibility(View.VISIBLE);
                coil_count_header.setVisibility(View.VISIBLE);
                coil_count.setText("");
                add_parallel_inductance1.setVisibility(View.GONE);
                add_parallel_inductance2.setVisibility(View.GONE);
                add_parallel_inductance3.setVisibility(View.GONE);
                add_parallel_inductance4.setVisibility(View.GONE);
                add_parallel_inductance5.setVisibility(View.GONE);
                inductanceUnit1.setVisibility(View.GONE);
                inductanceUnit2.setVisibility(View.GONE);
                inductanceUnit3.setVisibility(View.GONE);
                inductanceUnit4.setVisibility(View.GONE);
                inductanceUnit5.setVisibility(View.GONE);
                add_series_inductance1_symbol.setVisibility(View.GONE);
                add_series_inductance2_symbol.setVisibility(View.GONE);
                add_series_inductance3_symbol.setVisibility(View.GONE);
                add_series_inductance4_symbol.setVisibility(View.GONE);
                bookmarkName = "To Find Calculator: Capacitance - Parallel";
                bookmarkObj.setBookmarkName(bookmarkName);
                break;
            case SERIES:
                final_result_parallel_inductance_unit = (TextView) findViewById(R.id.final_result_parallel_inductance_unit);
                final_result_parallel_inductance_unit.setText("microfarads");
                TextView result_parallel_inductance_value = (TextView) findViewById(R.id.result_parallel_inductance_value);
                result_parallel_inductance_value.setText(Html.fromHtml("C<sub><small><small>T</small></small></sub>"));
                result_view = (LinearLayout) findViewById(R.id.result_view_parallel_inductance);
                result_parallel_inductance = (TextView) findViewById(R.id.result_parallel_inductance);
                add_parallel_inductance1 = (TextView) findViewById(R.id.add_parallel_inductance1);
                add_parallel_inductance2 = (TextView) findViewById(R.id.add_parallel_inductance2);
                add_parallel_inductance3 = (TextView) findViewById(R.id.add_parallel_inductance3);
                add_parallel_inductance4 = (TextView) findViewById(R.id.add_parallel_inductance4);
                add_parallel_inductance5 = (TextView) findViewById(R.id.add_parallel_inductance5);
                final_result_parallel_inductance_value = (TextView) findViewById(R.id.final_result_parallel_inductance_value);
                inductanceUnit1 = (TextView) findViewById(R.id.add_parallel_inductance1_unit);
                inductanceUnit2 = (TextView) findViewById(R.id.add_parallel_inductance2_unit);
                inductanceUnit3 = (TextView) findViewById(R.id.add_parallel_inductance3_unit);
                inductanceUnit4 = (TextView) findViewById(R.id.add_parallel_inductance4_unit);
                inductanceUnit5 = (TextView) findViewById(R.id.add_parallel_inductance5_unit);
                parallel_inductance_symbol1 = (TextView) findViewById(R.id.parallel_inductance_symbol1);
                parallel_inductance_symbol2 = (TextView) findViewById(R.id.parallel_inductance_symbol2);
                parallel_inductance_symbol3 = (TextView) findViewById(R.id.parallel_inductance_symbol3);
                parallel_inductance_symbol4 = (TextView) findViewById(R.id.parallel_inductance_symbol4);
                parallel_inductance_view1 = (LinearLayout) findViewById(R.id.parallel_inductance_view1);
                parallel_inductance_view2 = (LinearLayout) findViewById(R.id.parallel_inductance_view2);
                parallel_inductance_view3 = (LinearLayout) findViewById(R.id.parallel_inductance_view3);
                parallel_inductance_view4 = (LinearLayout) findViewById(R.id.parallel_inductance_view4);
                parallel_inductance_view5 = (LinearLayout) findViewById(R.id.parallel_inductance_view5);
                coil_count = (EditText) findViewById(R.id.coil_count);
                coil_count.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
                coil_count_header = (TextView) findViewById(R.id.coil_count_header);
                coil_count.setVisibility(View.VISIBLE);
                coil_count_header.setVisibility(View.VISIBLE);
                coil_count.setText("");
                parallel_inductance_view1.setVisibility(View.GONE);
                parallel_inductance_view2.setVisibility(View.GONE);
                parallel_inductance_view3.setVisibility(View.GONE);
                parallel_inductance_view4.setVisibility(View.GONE);
                parallel_inductance_view5.setVisibility(View.GONE);
                parallel_inductance_symbol1.setVisibility(View.GONE);
                parallel_inductance_symbol2.setVisibility(View.GONE);
                parallel_inductance_symbol3.setVisibility(View.GONE);
                parallel_inductance_symbol4.setVisibility(View.GONE);
                result_parallel_inductance.setText(Html.fromHtml("C<sub><small><small>T</small></small></sub>"));
                bookmarkName = "To Find Calculator: Capacitance - Series";
                bookmarkObj.setBookmarkName(bookmarkName);
                break;

        }
        coil_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (position == TO_FIND_CAPACITANCE.PARALLEL) {
                    if (coil_count.getText().length() > 0) {
                        if (Integer.parseInt(coil_count.getText().toString()) <= COIL_COUNT) {
                            if (to_find_known_value_view != null) {
                                int childCount = to_find_known_value_view.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    View child = to_find_known_value_view.getChildAt(i);
                                    if (child instanceof TableRow) {
                                        ((ViewGroup) child).removeAllViews();
                                    }
                                }
                            }
                            int num_of_coils = Integer.parseInt(coil_count.getText().toString());
                            coilCountInputVals.clear();
                            for (int i = 0; i < num_of_coils; i++) {
                                coilCountInputVals.add(inputVal.get(i));
                            }
                            switch (num_of_coils) {
                                case 1:
                                    add_parallel_inductance1.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("C<sub><small><small>1</small></small></sub>"));
                                    add_parallel_inductance1.setVisibility(View.VISIBLE);
                                    add_parallel_inductance2.setVisibility(View.GONE);
                                    add_parallel_inductance3.setVisibility(View.GONE);
                                    add_parallel_inductance4.setVisibility(View.GONE);
                                    add_parallel_inductance5.setVisibility(View.GONE);
                                    inductanceUnit1.setVisibility(View.VISIBLE);
                                    inductanceUnit2.setVisibility(View.GONE);
                                    inductanceUnit3.setVisibility(View.GONE);
                                    inductanceUnit4.setVisibility(View.GONE);
                                    inductanceUnit5.setVisibility(View.GONE);
                                    add_series_inductance1_symbol.setVisibility(View.GONE);
                                    add_series_inductance2_symbol.setVisibility(View.GONE);
                                    add_series_inductance3_symbol.setVisibility(View.GONE);
                                    add_series_inductance4_symbol.setVisibility(View.GONE);

                                    break;
                                case 2:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("C<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("C<sub><small><small>2</small></small></sub>"));
                                    add_series_inductance1_symbol.setText("+");
                                    add_parallel_inductance1.setVisibility(View.VISIBLE);
                                    add_parallel_inductance2.setVisibility(View.VISIBLE);
                                    add_parallel_inductance3.setVisibility(View.GONE);
                                    add_parallel_inductance4.setVisibility(View.GONE);
                                    add_parallel_inductance5.setVisibility(View.GONE);
                                    inductanceUnit1.setVisibility(View.VISIBLE);
                                    inductanceUnit2.setVisibility(View.VISIBLE);
                                    inductanceUnit3.setVisibility(View.GONE);
                                    inductanceUnit4.setVisibility(View.GONE);
                                    inductanceUnit5.setVisibility(View.GONE);
                                    add_series_inductance1_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance2_symbol.setVisibility(View.GONE);
                                    add_series_inductance3_symbol.setVisibility(View.GONE);
                                    add_series_inductance4_symbol.setVisibility(View.GONE);
                                    break;
                                case 3:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    add_parallel_inductance3.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("C<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("C<sub><small><small>2</small></small></sub>"));
                                    inductanceUnit3.setText(Html.fromHtml("C<sub><small><small>3</small></small></sub>"));
                                    add_series_inductance1_symbol.setText("+");
                                    add_series_inductance2_symbol.setText("+");
                                    add_parallel_inductance1.setVisibility(View.VISIBLE);
                                    add_parallel_inductance2.setVisibility(View.VISIBLE);
                                    add_parallel_inductance3.setVisibility(View.VISIBLE);
                                    add_parallel_inductance4.setVisibility(View.GONE);
                                    add_parallel_inductance5.setVisibility(View.GONE);
                                    inductanceUnit1.setVisibility(View.VISIBLE);
                                    inductanceUnit2.setVisibility(View.VISIBLE);
                                    inductanceUnit3.setVisibility(View.VISIBLE);
                                    inductanceUnit4.setVisibility(View.GONE);
                                    inductanceUnit5.setVisibility(View.GONE);
                                    add_series_inductance1_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance2_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance3_symbol.setVisibility(View.GONE);
                                    add_series_inductance4_symbol.setVisibility(View.GONE);
                                    break;
                                case 4:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    add_parallel_inductance3.setText("");
                                    add_parallel_inductance4.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("C<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("C<sub><small><small>2</small></small></sub>"));
                                    inductanceUnit3.setText(Html.fromHtml("C<sub><small><small>3</small></small></sub>"));
                                    inductanceUnit4.setText(Html.fromHtml("C<sub><small><small>4</small></small></sub>"));
                                    add_series_inductance1_symbol.setText("+");
                                    add_series_inductance2_symbol.setText("+");
                                    add_series_inductance3_symbol.setText("+");
                                    add_parallel_inductance1.setVisibility(View.VISIBLE);
                                    add_parallel_inductance2.setVisibility(View.VISIBLE);
                                    add_parallel_inductance3.setVisibility(View.VISIBLE);
                                    add_parallel_inductance4.setVisibility(View.VISIBLE);
                                    add_parallel_inductance5.setVisibility(View.GONE);
                                    inductanceUnit1.setVisibility(View.VISIBLE);
                                    inductanceUnit2.setVisibility(View.VISIBLE);
                                    inductanceUnit3.setVisibility(View.VISIBLE);
                                    inductanceUnit4.setVisibility(View.VISIBLE);
                                    inductanceUnit5.setVisibility(View.GONE);
                                    add_series_inductance1_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance2_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance3_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance4_symbol.setVisibility(View.GONE);
                                    break;
                                case 5:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    add_parallel_inductance3.setText("");
                                    add_parallel_inductance4.setText("");
                                    add_parallel_inductance5.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("C<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("C<sub><small><small>2</small></small></sub>"));
                                    inductanceUnit3.setText(Html.fromHtml("C<sub><small><small>3</small></small></sub>"));
                                    inductanceUnit4.setText(Html.fromHtml("C<sub><small><small>4</small></small></sub>"));
                                    inductanceUnit5.setText(Html.fromHtml("C<sub><small><small>5</small></small></sub>"));
                                    add_series_inductance1_symbol.setText("+");
                                    add_series_inductance2_symbol.setText("+");
                                    add_series_inductance3_symbol.setText("+");
                                    add_series_inductance4_symbol.setText("+");
                                    add_parallel_inductance1.setVisibility(View.VISIBLE);
                                    add_parallel_inductance2.setVisibility(View.VISIBLE);
                                    add_parallel_inductance3.setVisibility(View.VISIBLE);
                                    add_parallel_inductance4.setVisibility(View.VISIBLE);
                                    add_parallel_inductance5.setVisibility(View.VISIBLE);
                                    inductanceUnit1.setVisibility(View.VISIBLE);
                                    inductanceUnit2.setVisibility(View.VISIBLE);
                                    inductanceUnit3.setVisibility(View.VISIBLE);
                                    inductanceUnit4.setVisibility(View.VISIBLE);
                                    inductanceUnit5.setVisibility(View.VISIBLE);
                                    add_series_inductance1_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance2_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance3_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance4_symbol.setVisibility(View.VISIBLE);
                                    break;
                            }
                            drawInputValueView(coilCountInputVals, to_find_known_value_view, "C_S");
                            bookmarkName = "To Find Calculator: _CAPACITANCE - _SERIES";
                            bookmarkObj.setBookmarkName(bookmarkName);
                            to_find_result_header.setVisibility(View.VISIBLE);
                            result_view.setVisibility(View.VISIBLE);
                        } else {
                            showToast(getString(R.string.coil_count_validation), 1000);
                            if (to_find_known_value_view != null) {
                                int childCount = to_find_known_value_view.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    View child = to_find_known_value_view.getChildAt(i);
                                    if (child instanceof TableRow) {
                                        ((ViewGroup) child).removeAllViews();
                                    }
                                }
                            }
                            coil_count.setText("");
                            coilCountInputVals.clear();
                            to_find_result_header.setVisibility(View.GONE);
                            result_view.setVisibility(View.GONE);
                        }
                    } else {
                        if (to_find_known_value_view != null) {
                            int childCount = to_find_known_value_view.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View child = to_find_known_value_view.getChildAt(i);
                                if (child instanceof TableRow) {
                                    ((ViewGroup) child).removeAllViews();
                                }
                            }
                        }
                        coilCountInputVals.clear();
                        to_find_result_header.setVisibility(View.GONE);
                        result_view.setVisibility(View.GONE);
                    }
                } else if (position == TO_FIND_CAPACITANCE.SERIES) {

                    if (coil_count.getText().length() > 0) {
                        if (Integer.parseInt(coil_count.getText().toString()) <= COIL_COUNT) {
                            if (to_find_known_value_view != null) {
                                int childCount = to_find_known_value_view.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    View child = to_find_known_value_view.getChildAt(i);
                                    if (child instanceof TableRow) {
                                        ((ViewGroup) child).removeAllViews();
                                    }
                                }
                            }
                            int num_of_coils = Integer.parseInt(coil_count.getText().toString());
                            coilCountInputVals.clear();
                            for (int i = 0; i < num_of_coils; i++) {
                                coilCountInputVals.add(inputVal.get(i));
                            }
                            switch (num_of_coils) {
                                case 1:
                                    add_parallel_inductance1.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("C<sub><small><small>1</small></small></sub>"));
                                    parallel_inductance_view1.setVisibility(View.VISIBLE);
                                    parallel_inductance_view2.setVisibility(View.GONE);
                                    parallel_inductance_view3.setVisibility(View.GONE);
                                    parallel_inductance_view4.setVisibility(View.GONE);
                                    parallel_inductance_view5.setVisibility(View.GONE);
                                    parallel_inductance_symbol1.setVisibility(View.GONE);
                                    parallel_inductance_symbol2.setVisibility(View.GONE);
                                    parallel_inductance_symbol3.setVisibility(View.GONE);
                                    parallel_inductance_symbol4.setVisibility(View.GONE);

                                    break;
                                case 2:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("C<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("C<sub><small><small>2</small></small></sub>"));
                                    parallel_inductance_symbol1.setText("+");
                                    parallel_inductance_view1.setVisibility(View.VISIBLE);
                                    parallel_inductance_view2.setVisibility(View.VISIBLE);
                                    parallel_inductance_view3.setVisibility(View.GONE);
                                    parallel_inductance_view4.setVisibility(View.GONE);
                                    parallel_inductance_view5.setVisibility(View.GONE);
                                    parallel_inductance_symbol1.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol2.setVisibility(View.GONE);
                                    parallel_inductance_symbol3.setVisibility(View.GONE);
                                    parallel_inductance_symbol4.setVisibility(View.GONE);
                                    break;
                                case 3:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    add_parallel_inductance3.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("C<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("C<sub><small><small>2</small></small></sub>"));
                                    inductanceUnit3.setText(Html.fromHtml("C<sub><small><small>3</small></small></sub>"));
                                    parallel_inductance_symbol1.setText("+");
                                    parallel_inductance_symbol2.setText("+");
                                    parallel_inductance_view1.setVisibility(View.VISIBLE);
                                    parallel_inductance_view2.setVisibility(View.VISIBLE);
                                    parallel_inductance_view3.setVisibility(View.VISIBLE);
                                    parallel_inductance_view4.setVisibility(View.GONE);
                                    parallel_inductance_view5.setVisibility(View.GONE);
                                    parallel_inductance_symbol1.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol2.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol3.setVisibility(View.GONE);
                                    parallel_inductance_symbol4.setVisibility(View.GONE);
                                    break;
                                case 4:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    add_parallel_inductance3.setText("");
                                    add_parallel_inductance4.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("C<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("C<sub><small><small>2</small></small></sub>"));
                                    inductanceUnit3.setText(Html.fromHtml("C<sub><small><small>3</small></small></sub>"));
                                    inductanceUnit4.setText(Html.fromHtml("C<sub><small><small>4</small></small></sub>"));
                                    parallel_inductance_symbol1.setText("+");
                                    parallel_inductance_symbol2.setText("+");
                                    parallel_inductance_symbol3.setText("+");
                                    parallel_inductance_view1.setVisibility(View.VISIBLE);
                                    parallel_inductance_view2.setVisibility(View.VISIBLE);
                                    parallel_inductance_view3.setVisibility(View.VISIBLE);
                                    parallel_inductance_view4.setVisibility(View.VISIBLE);
                                    parallel_inductance_view5.setVisibility(View.GONE);
                                    parallel_inductance_symbol1.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol2.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol3.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol4.setVisibility(View.GONE);
                                    break;
                                case 5:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    add_parallel_inductance3.setText("");
                                    add_parallel_inductance4.setText("");
                                    add_parallel_inductance5.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("C<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("C<sub><small><small>2</small></small></sub>"));
                                    inductanceUnit3.setText(Html.fromHtml("C<sub><small><small>3</small></small></sub>"));
                                    inductanceUnit4.setText(Html.fromHtml("C<sub><small><small>4</small></small></sub>"));
                                    inductanceUnit5.setText(Html.fromHtml("C<sub><small><small>5</small></small></sub>"));
                                    parallel_inductance_symbol1.setText("+");
                                    parallel_inductance_symbol2.setText("+");
                                    parallel_inductance_symbol3.setText("+");
                                    parallel_inductance_symbol4.setText("+");
                                    parallel_inductance_view1.setVisibility(View.VISIBLE);
                                    parallel_inductance_view2.setVisibility(View.VISIBLE);
                                    parallel_inductance_view3.setVisibility(View.VISIBLE);
                                    parallel_inductance_view4.setVisibility(View.VISIBLE);
                                    parallel_inductance_view5.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol1.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol2.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol3.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol4.setVisibility(View.VISIBLE);
                                    break;
                            }
                            result_parallel_inductance.setText(Html.fromHtml("C<sub><small><small>T</small></small></sub>"));
                            drawInputValueView(coilCountInputVals, to_find_known_value_view, "C_P");
                            bookmarkName = "To Find Calculator: _CAPACITANCE - _PARALLEL";
                            bookmarkObj.setBookmarkName(bookmarkName);
                            to_find_result_header.setVisibility(View.VISIBLE);
                            result_view.setVisibility(View.VISIBLE);
                        }else{
                            showToast(getString(R.string.coil_count_validation), 1000);
                            if (to_find_known_value_view != null) {
                                int childCount = to_find_known_value_view.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    View child = to_find_known_value_view.getChildAt(i);
                                    if (child instanceof TableRow) {
                                        ((ViewGroup) child).removeAllViews();
                                    }
                                }
                            }
                            coil_count.setText("");
                            coilCountInputVals.clear();
                            to_find_result_header.setVisibility(View.GONE);
                            result_view.setVisibility(View.GONE);
                        }
                    } else {
                        if (to_find_known_value_view != null) {
                            int childCount = to_find_known_value_view.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View child = to_find_known_value_view.getChildAt(i);
                                if (child instanceof TableRow) {
                                    ((ViewGroup) child).removeAllViews();
                                }
                            }
                        }
                        coilCountInputVals.clear();
                        to_find_result_header.setVisibility(View.GONE);
                        result_view.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }
    }

    private void showReactanceKnownValues(int val, ArrayList<String> inputVal) {

        final TO_FIND_REACTANCE position = TO_FIND_REACTANCE.values()[val];
        to_find_known_value_view.setVisibility(View.VISIBLE);

        bookmarkObj.setBookmarkToFindKnown(position.ordinal());
        if (coil_count != null) {
            coil_count.setVisibility(View.GONE);
            coil_count_header.setVisibility(View.GONE);
        }
        switch (position) {
            case INDUC:
                result_view = (LinearLayout) findViewById(R.id.result_view_series_inductance);
                result_parallel_inductance = (TextView) findViewById(R.id.final_result_series_inductance_value);
                add_series_inductance1_symbol = (TextView) findViewById(R.id.add_series_inductance1_symbol);
                add_series_inductance2_symbol = (TextView) findViewById(R.id.add_series_inductance2_symbol);
                add_series_inductance3_symbol = (TextView) findViewById(R.id.add_series_inductance3_symbol);
                add_series_inductance4_symbol = (TextView) findViewById(R.id.add_series_inductance4_symbol);
                add_parallel_inductance1 = (TextView) findViewById(R.id.add_series_inductance1);
                add_parallel_inductance2 = (TextView) findViewById(R.id.add_series_inductance2);
                add_parallel_inductance3 = (TextView) findViewById(R.id.add_series_inductance3);
                add_parallel_inductance4 = (TextView) findViewById(R.id.add_series_inductance4);
                add_parallel_inductance5 = (TextView) findViewById(R.id.add_series_inductance5);
                coil_count = (EditText) findViewById(R.id.coil_count);
                coil_count_header = (TextView) findViewById(R.id.coil_count_header);
                coil_count.setVisibility(View.GONE);
                coil_count_header.setVisibility(View.GONE);
                reactanceUnit1 = (TextView) findViewById(R.id.add_series_inductance3_unit);
                reactanceUnit2 = (TextView) findViewById(R.id.add_series_inductance4_unit);
                TextView reactanceUnit3 = (TextView) findViewById(R.id.add_series_inductance1_unit);
                TextView reactanceUnit4 = (TextView) findViewById(R.id.add_series_inductance2_unit);
                TextView reactanceUnit5 = (TextView) findViewById(R.id.add_series_inductance5_unit);
                reactanceUnit3.setText("");
                reactanceUnit4.setText("");
                reactanceUnit5.setText("");
                add_series_inductance1_symbol.setVisibility(View.VISIBLE);
                add_series_inductance2_symbol.setVisibility(View.VISIBLE);
                add_series_inductance3_symbol.setVisibility(View.VISIBLE);
                add_series_inductance4_symbol.setVisibility(View.GONE);
                add_series_inductance1_symbol.setText("X");
                add_series_inductance2_symbol.setText("X");
                add_series_inductance3_symbol.setText("X");
                if (inductanceUnit1 != null) {
                    inductanceUnit1.setText("");
                    inductanceUnit2.setText("");
                }
                add_parallel_inductance1.setVisibility(View.VISIBLE);
                add_parallel_inductance2.setVisibility(View.VISIBLE);
                add_parallel_inductance3.setVisibility(View.VISIBLE);
                add_parallel_inductance4.setVisibility(View.VISIBLE);
                add_parallel_inductance5.setVisibility(View.GONE);
                add_parallel_inductance1.setText("2");
                add_parallel_inductance2.setText("3.1416");
                add_parallel_inductance3.setText("");
                add_parallel_inductance4.setText("");
                drawInputValueView(inputVal, to_find_known_value_view, "REAC_I");
                reactanceUnit1.setText("F");
                reactanceUnit2.setText("L");
                reactanceUnit1.setVisibility(View.VISIBLE);
                reactanceUnit2.setVisibility(View.VISIBLE);
                TextView final_result_series_inductance_unit = (TextView) findViewById(R.id.final_result_series_inductance_unit);
                final_result_series_inductance_unit.setText("Henry");
                TextView result_series_inductance_value = (TextView) findViewById(R.id.result_series_inductance_value);
                result_series_inductance_value.setText(Html.fromHtml("X<sub><small><small>L</small></small></sub>"));
                TextView result_series_inductance = (TextView) findViewById(R.id.result_series_inductance);
                result_series_inductance.setText(Html.fromHtml("X<sub><small><small>L</small></small></sub>"));
                result_parallel_inductance.setText("");
                bookmarkName = "To Find Calculator: _REACTANCE - _INDUCTANCE";
                bookmarkObj.setBookmarkName(bookmarkName);

                break;
            case CAP:
                result_view = (LinearLayout) findViewById(R.id.result_view_cap_reactance);
                TextView result_cap_reactance = (TextView) findViewById(R.id.result_cap_reactance);
                result_cap_reactance.setText(Html.fromHtml("X<sub><small><small>C</small></small></sub>"));
                TextView result_cap_reactance_lhs_value = (TextView) findViewById(R.id.result_cap_reactance_lhs_value);
                result_cap_reactance_lhs_value.setText(Html.fromHtml("X<sub><small><small>C</small></small></sub>"));
                TextView result_cap_reactance_unit = (TextView) findViewById(R.id.result_cap_reactance_unit);
                result_cap_reactance_unit.setText("Henry");
                result_parallel_inductance = (TextView) findViewById(R.id.final_result_cap_reactance_rhs_value);
                add_parallel_inductance1 = (TextView) findViewById(R.id.add_cap_reactance1);
                add_parallel_inductance2 = (TextView) findViewById(R.id.add_cap_reactance2);
                add_parallel_inductance3 = (TextView) findViewById(R.id.add_cap_reactance3);
                add_parallel_inductance4 = (TextView) findViewById(R.id.add_cap_reactance4);
                reactanceUnit1 = (TextView) findViewById(R.id.add_cap_reactance3_unit);
                reactanceUnit2 = (TextView) findViewById(R.id.add_cap_reactance4_unit);
                add_parallel_inductance1.setText("2");
                add_parallel_inductance2.setText("3.1416");
                add_parallel_inductance3.setText("");
                add_parallel_inductance4.setText("");
                drawInputValueView(inputVal, to_find_known_value_view, "REAC_C");
                reactanceUnit1.setText("F");
                reactanceUnit2.setText("C");
                result_parallel_inductance.setText("");
                bookmarkName = "To Find Calculator: _REACTANCE - _CAPACITIVE";
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

    private void showIndutanceKnownValues(final int val, final ArrayList<String> inputVal) {

        TextView result_parallel_inductance_value = (TextView) findViewById(R.id.result_parallel_inductance_value);
        result_parallel_inductance_value.setText(Html.fromHtml("L<sub><small><small>T</small></small></sub>"));

        final TO_FIND_INDUCTANCE position = TO_FIND_INDUCTANCE.values()[val];
        to_find_known_value_view.setVisibility(View.VISIBLE);

        bookmarkObj.setBookmarkToFindKnown(position.ordinal());

        final ArrayList<String> coilCountInputVals = new ArrayList<>();
        switch (position) {
            case SERIES:
                TextView final_result_series_inductance_unit = (TextView) findViewById(R.id.final_result_series_inductance_unit);
                final_result_series_inductance_unit.setText("Henry");
                TextView result_series_inductance_value = (TextView) findViewById(R.id.result_series_inductance_value);
                result_series_inductance_value.setText(Html.fromHtml("L<sub><small><small>T</small></small></sub>"));
                TextView result_series_inductance = (TextView) findViewById(R.id.result_series_inductance);
                result_series_inductance.setText(Html.fromHtml("L<sub><small><small>T</small></small></sub>"));
                final_result_series_inductance_value = (TextView) findViewById(R.id.final_result_series_inductance_value);
                result_view = (LinearLayout) findViewById(R.id.result_view_series_inductance);
                add_parallel_inductance1 = (TextView) findViewById(R.id.add_series_inductance1);
                add_parallel_inductance2 = (TextView) findViewById(R.id.add_series_inductance2);
                add_parallel_inductance3 = (TextView) findViewById(R.id.add_series_inductance3);
                add_parallel_inductance4 = (TextView) findViewById(R.id.add_series_inductance4);
                add_parallel_inductance5 = (TextView) findViewById(R.id.add_series_inductance5);
                add_series_inductance1_symbol = (TextView) findViewById(R.id.add_series_inductance1_symbol);
                add_series_inductance2_symbol = (TextView) findViewById(R.id.add_series_inductance2_symbol);
                add_series_inductance3_symbol = (TextView) findViewById(R.id.add_series_inductance3_symbol);
                add_series_inductance4_symbol = (TextView) findViewById(R.id.add_series_inductance4_symbol);
                inductanceUnit1 = (TextView) findViewById(R.id.add_series_inductance1_unit);
                inductanceUnit2 = (TextView) findViewById(R.id.add_series_inductance2_unit);
                inductanceUnit3 = (TextView) findViewById(R.id.add_series_inductance3_unit);
                inductanceUnit4 = (TextView) findViewById(R.id.add_series_inductance4_unit);
                inductanceUnit5 = (TextView) findViewById(R.id.add_series_inductance5_unit);
                coil_count = (EditText) findViewById(R.id.coil_count);
                coil_count.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
                coil_count_header = (TextView) findViewById(R.id.coil_count_header);
                coil_count.setVisibility(View.VISIBLE);
                coil_count_header.setVisibility(View.VISIBLE);
                coil_count.setText("");
                add_parallel_inductance1.setVisibility(View.GONE);
                add_parallel_inductance2.setVisibility(View.GONE);
                add_parallel_inductance3.setVisibility(View.GONE);
                add_parallel_inductance4.setVisibility(View.GONE);
                add_parallel_inductance5.setVisibility(View.GONE);
                inductanceUnit1.setVisibility(View.GONE);
                inductanceUnit2.setVisibility(View.GONE);
                inductanceUnit3.setVisibility(View.GONE);
                inductanceUnit4.setVisibility(View.GONE);
                inductanceUnit5.setVisibility(View.GONE);
                add_series_inductance1_symbol.setVisibility(View.GONE);
                add_series_inductance2_symbol.setVisibility(View.GONE);
                add_series_inductance3_symbol.setVisibility(View.GONE);
                add_series_inductance4_symbol.setVisibility(View.GONE);
                bookmarkName = "To Find Calculator: _Inductance - _Series";
                bookmarkObj.setBookmarkName(bookmarkName);
                break;
            case PARALLEL:
                final_result_parallel_inductance_unit = (TextView) findViewById(R.id.final_result_parallel_inductance_unit);
                final_result_parallel_inductance_unit.setText("Henry");
                result_view = (LinearLayout) findViewById(R.id.result_view_parallel_inductance);
                result_parallel_inductance = (TextView) findViewById(R.id.result_parallel_inductance);
                add_parallel_inductance1 = (TextView) findViewById(R.id.add_parallel_inductance1);
                add_parallel_inductance2 = (TextView) findViewById(R.id.add_parallel_inductance2);
                add_parallel_inductance3 = (TextView) findViewById(R.id.add_parallel_inductance3);
                add_parallel_inductance4 = (TextView) findViewById(R.id.add_parallel_inductance4);
                add_parallel_inductance5 = (TextView) findViewById(R.id.add_parallel_inductance5);
                final_result_parallel_inductance_value = (TextView) findViewById(R.id.final_result_parallel_inductance_value);
                inductanceUnit1 = (TextView) findViewById(R.id.add_parallel_inductance1_unit);
                inductanceUnit2 = (TextView) findViewById(R.id.add_parallel_inductance2_unit);
                inductanceUnit3 = (TextView) findViewById(R.id.add_parallel_inductance3_unit);
                inductanceUnit4 = (TextView) findViewById(R.id.add_parallel_inductance4_unit);
                inductanceUnit5 = (TextView) findViewById(R.id.add_parallel_inductance5_unit);
                parallel_inductance_symbol1 = (TextView) findViewById(R.id.parallel_inductance_symbol1);
                parallel_inductance_symbol2 = (TextView) findViewById(R.id.parallel_inductance_symbol2);
                parallel_inductance_symbol3 = (TextView) findViewById(R.id.parallel_inductance_symbol3);
                parallel_inductance_symbol4 = (TextView) findViewById(R.id.parallel_inductance_symbol4);
                parallel_inductance_view1 = (LinearLayout) findViewById(R.id.parallel_inductance_view1);
                parallel_inductance_view2 = (LinearLayout) findViewById(R.id.parallel_inductance_view2);
                parallel_inductance_view3 = (LinearLayout) findViewById(R.id.parallel_inductance_view3);
                parallel_inductance_view4 = (LinearLayout) findViewById(R.id.parallel_inductance_view4);
                parallel_inductance_view5 = (LinearLayout) findViewById(R.id.parallel_inductance_view5);
                coil_count = (EditText) findViewById(R.id.coil_count);
                coil_count.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
                coil_count_header = (TextView) findViewById(R.id.coil_count_header);
                coil_count.setVisibility(View.VISIBLE);
                coil_count_header.setVisibility(View.VISIBLE);
                coil_count.setText("");
                parallel_inductance_view1.setVisibility(View.GONE);
                parallel_inductance_view2.setVisibility(View.GONE);
                parallel_inductance_view3.setVisibility(View.GONE);
                parallel_inductance_view4.setVisibility(View.GONE);
                parallel_inductance_view5.setVisibility(View.GONE);
                parallel_inductance_symbol1.setVisibility(View.GONE);
                parallel_inductance_symbol2.setVisibility(View.GONE);
                parallel_inductance_symbol3.setVisibility(View.GONE);
                parallel_inductance_symbol4.setVisibility(View.GONE);
                result_parallel_inductance.setText(Html.fromHtml("L<sub><small><small>T</small></small></sub>"));
                bookmarkName = "To Find Calculator: _Inductance - _Parallel";
                bookmarkObj.setBookmarkName(bookmarkName);
                break;

        }
        coil_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (position == TO_FIND_INDUCTANCE.SERIES) {
                    if (coil_count.getText().length() > 0) {
                        if (Integer.parseInt(coil_count.getText().toString()) <= COIL_COUNT) {
                            if (to_find_known_value_view != null) {
                                int childCount = to_find_known_value_view.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    View child = to_find_known_value_view.getChildAt(i);
                                    if (child instanceof TableRow) {
                                        ((ViewGroup) child).removeAllViews();
                                    }
                                }
                            }
                            int num_of_coils = Integer.parseInt(coil_count.getText().toString());
                            coilCountInputVals.clear();
                            for (int i = 0; i < num_of_coils; i++) {
                                coilCountInputVals.add(inputVal.get(i));
                            }
                            switch (num_of_coils) {
                                case 1:
                                    add_parallel_inductance1.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("L<sub><small><small>1</small></small></sub>"));
                                    add_parallel_inductance1.setVisibility(View.VISIBLE);
                                    add_parallel_inductance2.setVisibility(View.GONE);
                                    add_parallel_inductance3.setVisibility(View.GONE);
                                    add_parallel_inductance4.setVisibility(View.GONE);
                                    add_parallel_inductance5.setVisibility(View.GONE);
                                    inductanceUnit1.setVisibility(View.VISIBLE);
                                    inductanceUnit2.setVisibility(View.GONE);
                                    inductanceUnit3.setVisibility(View.GONE);
                                    inductanceUnit4.setVisibility(View.GONE);
                                    inductanceUnit5.setVisibility(View.GONE);
                                    add_series_inductance1_symbol.setVisibility(View.GONE);
                                    add_series_inductance2_symbol.setVisibility(View.GONE);
                                    add_series_inductance3_symbol.setVisibility(View.GONE);
                                    add_series_inductance4_symbol.setVisibility(View.GONE);

                                    break;
                                case 2:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("L<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("L<sub><small><small>2</small></small></sub>"));
                                    add_series_inductance1_symbol.setText("+");
                                    add_parallel_inductance1.setVisibility(View.VISIBLE);
                                    add_parallel_inductance2.setVisibility(View.VISIBLE);
                                    add_parallel_inductance3.setVisibility(View.GONE);
                                    add_parallel_inductance4.setVisibility(View.GONE);
                                    add_parallel_inductance5.setVisibility(View.GONE);
                                    inductanceUnit1.setVisibility(View.VISIBLE);
                                    inductanceUnit2.setVisibility(View.VISIBLE);
                                    inductanceUnit3.setVisibility(View.GONE);
                                    inductanceUnit4.setVisibility(View.GONE);
                                    inductanceUnit5.setVisibility(View.GONE);
                                    add_series_inductance1_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance2_symbol.setVisibility(View.GONE);
                                    add_series_inductance3_symbol.setVisibility(View.GONE);
                                    add_series_inductance4_symbol.setVisibility(View.GONE);
                                    break;
                                case 3:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    add_parallel_inductance3.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("L<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("L<sub><small><small>2</small></small></sub>"));
                                    inductanceUnit3.setText(Html.fromHtml("L<sub><small><small>3</small></small></sub>"));
                                    add_series_inductance1_symbol.setText("+");
                                    add_series_inductance2_symbol.setText("+");
                                    add_parallel_inductance1.setVisibility(View.VISIBLE);
                                    add_parallel_inductance2.setVisibility(View.VISIBLE);
                                    add_parallel_inductance3.setVisibility(View.VISIBLE);
                                    add_parallel_inductance4.setVisibility(View.GONE);
                                    add_parallel_inductance5.setVisibility(View.GONE);
                                    inductanceUnit1.setVisibility(View.VISIBLE);
                                    inductanceUnit2.setVisibility(View.VISIBLE);
                                    inductanceUnit3.setVisibility(View.VISIBLE);
                                    inductanceUnit4.setVisibility(View.GONE);
                                    inductanceUnit5.setVisibility(View.GONE);
                                    add_series_inductance1_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance2_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance3_symbol.setVisibility(View.GONE);
                                    add_series_inductance4_symbol.setVisibility(View.GONE);
                                    break;
                                case 4:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    add_parallel_inductance3.setText("");
                                    add_parallel_inductance4.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("L<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("L<sub><small><small>2</small></small></sub>"));
                                    inductanceUnit3.setText(Html.fromHtml("L<sub><small><small>3</small></small></sub>"));
                                    inductanceUnit4.setText(Html.fromHtml("L<sub><small><small>4</small></small></sub>"));
                                    add_series_inductance1_symbol.setText("+");
                                    add_series_inductance2_symbol.setText("+");
                                    add_series_inductance3_symbol.setText("+");
                                    add_parallel_inductance1.setVisibility(View.VISIBLE);
                                    add_parallel_inductance2.setVisibility(View.VISIBLE);
                                    add_parallel_inductance3.setVisibility(View.VISIBLE);
                                    add_parallel_inductance4.setVisibility(View.VISIBLE);
                                    add_parallel_inductance5.setVisibility(View.GONE);
                                    inductanceUnit1.setVisibility(View.VISIBLE);
                                    inductanceUnit2.setVisibility(View.VISIBLE);
                                    inductanceUnit3.setVisibility(View.VISIBLE);
                                    inductanceUnit4.setVisibility(View.VISIBLE);
                                    inductanceUnit5.setVisibility(View.GONE);
                                    add_series_inductance1_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance2_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance3_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance4_symbol.setVisibility(View.GONE);
                                    break;
                                case 5:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    add_parallel_inductance3.setText("");
                                    add_parallel_inductance4.setText("");
                                    add_parallel_inductance5.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("L<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("L<sub><small><small>2</small></small></sub>"));
                                    inductanceUnit3.setText(Html.fromHtml("L<sub><small><small>3</small></small></sub>"));
                                    inductanceUnit4.setText(Html.fromHtml("L<sub><small><small>4</small></small></sub>"));
                                    inductanceUnit5.setText(Html.fromHtml("L<sub><small><small>5</small></small></sub>"));
                                    add_series_inductance1_symbol.setText("+");
                                    add_series_inductance2_symbol.setText("+");
                                    add_series_inductance3_symbol.setText("+");
                                    add_series_inductance4_symbol.setText("+");
                                    add_parallel_inductance1.setVisibility(View.VISIBLE);
                                    add_parallel_inductance2.setVisibility(View.VISIBLE);
                                    add_parallel_inductance3.setVisibility(View.VISIBLE);
                                    add_parallel_inductance4.setVisibility(View.VISIBLE);
                                    add_parallel_inductance5.setVisibility(View.VISIBLE);
                                    inductanceUnit1.setVisibility(View.VISIBLE);
                                    inductanceUnit2.setVisibility(View.VISIBLE);
                                    inductanceUnit3.setVisibility(View.VISIBLE);
                                    inductanceUnit4.setVisibility(View.VISIBLE);
                                    inductanceUnit5.setVisibility(View.VISIBLE);
                                    add_series_inductance1_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance2_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance3_symbol.setVisibility(View.VISIBLE);
                                    add_series_inductance4_symbol.setVisibility(View.VISIBLE);
                                    break;
                            }
                            drawInputValueView(coilCountInputVals, to_find_known_value_view, "IND_S");
                            bookmarkName = "To Find Calculator: INDUCTANCE - SERIES";
                            bookmarkObj.setBookmarkName(bookmarkName);
                            to_find_result_header.setVisibility(View.VISIBLE);
                            result_view.setVisibility(View.VISIBLE);
                        } else {
                            showToast(getString(R.string.coil_count_validation), 1000);
                            if (to_find_known_value_view != null) {
                                int childCount = to_find_known_value_view.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    View child = to_find_known_value_view.getChildAt(i);
                                    if (child instanceof TableRow) {
                                        ((ViewGroup) child).removeAllViews();
                                    }
                                }
                            }
                            coil_count.setText("");
                            coilCountInputVals.clear();
                            to_find_result_header.setVisibility(View.GONE);
                            result_view.setVisibility(View.GONE);
                        }
                    } else {
                        if (to_find_known_value_view != null) {
                            int childCount = to_find_known_value_view.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View child = to_find_known_value_view.getChildAt(i);
                                if (child instanceof TableRow) {
                                    ((ViewGroup) child).removeAllViews();
                                }
                            }
                        }
                        coilCountInputVals.clear();
                        to_find_result_header.setVisibility(View.GONE);
                        result_view.setVisibility(View.GONE);
                    }
                } else if (position == TO_FIND_INDUCTANCE.PARALLEL) {
                    if (coil_count.getText().length() > 0) {
                        if (Integer.parseInt(coil_count.getText().toString()) <= COIL_COUNT) {
                            if (to_find_known_value_view != null) {
                                int childCount = to_find_known_value_view.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    View child = to_find_known_value_view.getChildAt(i);
                                    if (child instanceof TableRow) {
                                        ((ViewGroup) child).removeAllViews();
                                    }
                                }
                            }
                            int num_of_coils = Integer.parseInt(coil_count.getText().toString());
                            coilCountInputVals.clear();
                            for (int i = 0; i < num_of_coils; i++) {
                                coilCountInputVals.add(inputVal.get(i));
                            }
                            switch (num_of_coils) {
                                case 1:
                                    add_parallel_inductance1.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("L<sub><small><small>1</small></small></sub>"));
                                    parallel_inductance_view1.setVisibility(View.VISIBLE);
                                    parallel_inductance_view2.setVisibility(View.GONE);
                                    parallel_inductance_view3.setVisibility(View.GONE);
                                    parallel_inductance_view4.setVisibility(View.GONE);
                                    parallel_inductance_view5.setVisibility(View.GONE);
                                    parallel_inductance_symbol1.setVisibility(View.GONE);
                                    parallel_inductance_symbol2.setVisibility(View.GONE);
                                    parallel_inductance_symbol3.setVisibility(View.GONE);
                                    parallel_inductance_symbol4.setVisibility(View.GONE);

                                    break;
                                case 2:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("L<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("L<sub><small><small>2</small></small></sub>"));
                                    parallel_inductance_symbol1.setText("+");
                                    parallel_inductance_view1.setVisibility(View.VISIBLE);
                                    parallel_inductance_view2.setVisibility(View.VISIBLE);
                                    parallel_inductance_view3.setVisibility(View.GONE);
                                    parallel_inductance_view4.setVisibility(View.GONE);
                                    parallel_inductance_view5.setVisibility(View.GONE);
                                    parallel_inductance_symbol1.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol2.setVisibility(View.GONE);
                                    parallel_inductance_symbol3.setVisibility(View.GONE);
                                    parallel_inductance_symbol4.setVisibility(View.GONE);
                                    break;
                                case 3:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    add_parallel_inductance3.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("L<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("L<sub><small><small>2</small></small></sub>"));
                                    inductanceUnit3.setText(Html.fromHtml("L<sub><small><small>3</small></small></sub>"));
                                    parallel_inductance_symbol1.setText("+");
                                    parallel_inductance_symbol2.setText("+");
                                    parallel_inductance_view1.setVisibility(View.VISIBLE);
                                    parallel_inductance_view2.setVisibility(View.VISIBLE);
                                    parallel_inductance_view3.setVisibility(View.VISIBLE);
                                    parallel_inductance_view4.setVisibility(View.GONE);
                                    parallel_inductance_view5.setVisibility(View.GONE);
                                    parallel_inductance_symbol1.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol2.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol3.setVisibility(View.GONE);
                                    parallel_inductance_symbol4.setVisibility(View.GONE);
                                    break;
                                case 4:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    add_parallel_inductance3.setText("");
                                    add_parallel_inductance4.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("L<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("L<sub><small><small>2</small></small></sub>"));
                                    inductanceUnit3.setText(Html.fromHtml("L<sub><small><small>3</small></small></sub>"));
                                    inductanceUnit4.setText(Html.fromHtml("L<sub><small><small>4</small></small></sub>"));
                                    parallel_inductance_symbol1.setText("+");
                                    parallel_inductance_symbol2.setText("+");
                                    parallel_inductance_symbol3.setText("+");
                                    parallel_inductance_view1.setVisibility(View.VISIBLE);
                                    parallel_inductance_view2.setVisibility(View.VISIBLE);
                                    parallel_inductance_view3.setVisibility(View.VISIBLE);
                                    parallel_inductance_view4.setVisibility(View.VISIBLE);
                                    parallel_inductance_view5.setVisibility(View.GONE);
                                    parallel_inductance_symbol1.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol2.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol3.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol4.setVisibility(View.GONE);
                                    break;
                                case 5:
                                    add_parallel_inductance1.setText("");
                                    add_parallel_inductance2.setText("");
                                    add_parallel_inductance3.setText("");
                                    add_parallel_inductance4.setText("");
                                    add_parallel_inductance5.setText("");
                                    inductanceUnit1.setText(Html.fromHtml("L<sub><small><small>1</small></small></sub>"));
                                    inductanceUnit2.setText(Html.fromHtml("L<sub><small><small>2</small></small></sub>"));
                                    inductanceUnit3.setText(Html.fromHtml("L<sub><small><small>3</small></small></sub>"));
                                    inductanceUnit4.setText(Html.fromHtml("L<sub><small><small>4</small></small></sub>"));
                                    inductanceUnit5.setText(Html.fromHtml("L<sub><small><small>5</small></small></sub>"));
                                    parallel_inductance_symbol1.setText("+");
                                    parallel_inductance_symbol2.setText("+");
                                    parallel_inductance_symbol3.setText("+");
                                    parallel_inductance_symbol4.setText("+");
                                    parallel_inductance_view1.setVisibility(View.VISIBLE);
                                    parallel_inductance_view2.setVisibility(View.VISIBLE);
                                    parallel_inductance_view3.setVisibility(View.VISIBLE);
                                    parallel_inductance_view4.setVisibility(View.VISIBLE);
                                    parallel_inductance_view5.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol1.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol2.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol3.setVisibility(View.VISIBLE);
                                    parallel_inductance_symbol4.setVisibility(View.VISIBLE);
                                    break;
                            }
                            result_parallel_inductance.setText(Html.fromHtml("L<sub><small><small>T</small></small></sub>"));
                            drawInputValueView(coilCountInputVals, to_find_known_value_view, "IND_P");
                            bookmarkName = "To Find Calculator: _INDUCTANCE - PARALLEL";
                            bookmarkObj.setBookmarkName(bookmarkName);
                            to_find_result_header.setVisibility(View.VISIBLE);
                            result_view.setVisibility(View.VISIBLE);
                        } else {
                            showToast(getString(R.string.coil_count_validation), 1000);
                            if (to_find_known_value_view != null) {
                                int childCount = to_find_known_value_view.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    View child = to_find_known_value_view.getChildAt(i);
                                    if (child instanceof TableRow) {
                                        ((ViewGroup) child).removeAllViews();
                                    }
                                }
                            }
                            coilCountInputVals.clear();
                            coil_count.setText("");
                            to_find_result_header.setVisibility(View.GONE);
                            result_view.setVisibility(View.GONE);
                        }
                    } else {
                        if (to_find_known_value_view != null) {
                            int childCount = to_find_known_value_view.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View child = to_find_known_value_view.getChildAt(i);
                                if (child instanceof TableRow) {
                                    ((ViewGroup) child).removeAllViews();
                                }
                            }
                        }
                        coilCountInputVals.clear();
                        to_find_result_header.setVisibility(View.GONE);
                        result_view.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
            if (bookmarkObj.getBookmarkToFindKnown() != -1) {
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

    private String toFindCalculator(double val1, double val2, double val3, double val4, double val5, TO_FIND_CALCULATOR calType, int numOfCoils) {
        double result = 0;
        switch (calType) {
            case C:
                result = val1 / val2;
                break;
            case REACT_I:
                result = 2 * 3.1416 * val1 * val2;
                break;
            case REACT_C:
                result = 1 / (2 * 3.1416 * val1 * val2);
                break;
            case INDUC_S:
                result = val1 + val2 + val3 + val4 + val5;
                break;
            case INDUC_P:
                switch (numOfCoils) {
                    case 1:
                        result = 1 / ((1 / val1));
                        break;
                    case 2:
                        result = 1 / ((1 / val1) + (1 / val2));
                        break;
                    case 3:
                        result = 1 / ((1 / val1) + (1 / val2) + (1 / val3));
                        break;
                    case 4:
                        result = 1 / ((1 / val1) + (1 / val2) + (1 / val3) + (1 / val4));
                        break;
                    case 5:
                        result = 1 / ((1 / val1) + (1 / val2) + (1 / val3) + (1 / val4) + (1 / val5));
                        break;
                }
                break;

        }

        return TextUtil.convertToExponential(result);

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
                case "C_CV":
                    if (splitArr[1].equalsIgnoreCase("Q (Coulombs)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(div_from_cap, Val1);
                        if (Val1.length() > 0) {
                            TextUtil.insertText(div_from_unit_cap, "C");
                        } else {
                            TextUtil.insertText(div_from_unit_cap, "Q");
                        }

                    } else if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(div_to_cap, Val2);
                        if (Val2.length() > 0) {
                            TextUtil.insertText(div_to_unit_cap, "V");
                        } else {
                            TextUtil.insertText(div_to_unit_cap, "E");
                        }

                    }
                    if (Val1 != null && Val2 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0) {
                            String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), 1, 1, 1, TO_FIND_CALCULATOR.C, 0);
                            TextUtil.insertText(result_cap, result);
                        } else {
                            TextUtil.insertText(result_cap, "");
                        }
                    }
                    break;
                case "C_S":
                    if (splitArr[1].equalsIgnoreCase("C1 (Capacitance1)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance1, Val1);

                    } else if (splitArr[1].equalsIgnoreCase("C2 (Capacitance2)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance2, Val2);

                    } else if (splitArr[1].equalsIgnoreCase("C3 (Capacitance3)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance3, Val3);

                    } else if (splitArr[1].equalsIgnoreCase("C4 (Capacitance4)")) {
                        Val4 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance4, Val4);

                    } else if (splitArr[1].equalsIgnoreCase("C5 (Capacitance5)")) {
                        Val5 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance5, Val5);

                    }
                    if (coil_count != null) {
                        int num_of_coils = Integer.parseInt(coil_count.getText().toString());
                        switch (num_of_coils) {
                            case 1:
                                if (Val1 != null) {
                                    if (Val1.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), 0, 0, 0, 0, TO_FIND_CALCULATOR.INDUC_S, 0);
                                        TextUtil.insertText(final_result_series_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_series_inductance_value, "");
                                    }
                                }
                                break;

                            case 2:
                                if (Val1 != null && Val2 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), 0, 0, 0, TO_FIND_CALCULATOR.INDUC_S, 0);
                                        TextUtil.insertText(final_result_series_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_series_inductance_value, "");

                                    }
                                }
                                break;
                            case 3:
                                if (Val1 != null && Val2 != null && Val3 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3), 0, 0, TO_FIND_CALCULATOR.INDUC_S, 0);
                                        TextUtil.insertText(final_result_series_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_series_inductance_value, "");

                                    }
                                }
                                break;
                            case 4:
                                if (Val1 != null && Val2 != null && Val3 != null && Val4 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0 && Val4.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3), Double.parseDouble(Val4), 0, TO_FIND_CALCULATOR.INDUC_S, 0);
                                        TextUtil.insertText(final_result_series_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_series_inductance_value, "");
                                    }
                                }
                                break;
                            case 5:
                                if (Val1 != null && Val2 != null && Val3 != null && Val4 != null && Val5 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0 && Val4.length() > 0 && Val5.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3), Double.parseDouble(Val4), Double.parseDouble(Val5), TO_FIND_CALCULATOR.INDUC_S, 0);
                                        TextUtil.insertText(final_result_series_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_series_inductance_value, "");
                                    }
                                }
                                break;
                        }
                    }
                    break;
                case "C_P":
                    if (splitArr[1].equalsIgnoreCase("C1 (Capacitance1)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance1, Val1);

                    } else if (splitArr[1].equalsIgnoreCase("C2 (Capacitance2)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance2, Val2);

                    } else if (splitArr[1].equalsIgnoreCase("C3 (Capacitance3)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance3, Val3);

                    } else if (splitArr[1].equalsIgnoreCase("C4 (Capacitance4)")) {
                        Val4 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance4, Val4);

                    } else if (splitArr[1].equalsIgnoreCase("C5 (Capacitance5)")) {
                        Val5 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance5, Val5);

                    }
                    if (coil_count != null) {
                        int num_of_coils = Integer.parseInt(coil_count.getText().toString());
                        switch (num_of_coils) {
                            case 1:
                                if (Val1 != null) {
                                    if (Val1.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), 0, 0, 0, 0, TO_FIND_CALCULATOR.INDUC_P, num_of_coils);
                                        TextUtil.insertText(final_result_parallel_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_parallel_inductance_value, "");

                                    }
                                }
                                break;

                            case 2:
                                if (Val1 != null && Val2 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), 0, 0, 0, TO_FIND_CALCULATOR.INDUC_P, num_of_coils);
                                        TextUtil.insertText(final_result_parallel_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_parallel_inductance_value, "");

                                    }
                                }
                                break;
                            case 3:
                                if (Val1 != null && Val2 != null && Val3 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3), 0, 0, TO_FIND_CALCULATOR.INDUC_P, num_of_coils);
                                        TextUtil.insertText(final_result_parallel_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_parallel_inductance_value, "");

                                    }
                                }
                                break;
                            case 4:
                                if (Val1 != null && Val2 != null && Val3 != null && Val4 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0 && Val4.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3), Double.parseDouble(Val4), 0, TO_FIND_CALCULATOR.INDUC_P, num_of_coils);
                                        TextUtil.insertText(final_result_parallel_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_parallel_inductance_value, "");

                                    }
                                }
                                break;
                            case 5:
                                if (Val1 != null && Val2 != null && Val3 != null && Val4 != null && Val5 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0 && Val4.length() > 0 && Val5.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3), Double.parseDouble(Val4), Double.parseDouble(Val5), TO_FIND_CALCULATOR.INDUC_P, num_of_coils);
                                        TextUtil.insertText(final_result_parallel_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_parallel_inductance_value, "");

                                    }
                                }
                                break;
                        }
                    }
                    break;
                case "REAC_I":
                    if (splitArr[1].equalsIgnoreCase("F (Frequency)")) {
                        Val1 = e.getText().toString();
                        if (Val1.length() > 0) {
                            TextUtil.insertText(add_parallel_inductance3, Val1);
                            reactanceUnit1.setText("");
                            reactanceUnit2.setText("Henry");
                        } else {
                            reactanceUnit1.setText("F");
                            reactanceUnit2.setText("L");
                        }

                    } else if (splitArr[1].equalsIgnoreCase("L (Inductance)")) {
                        Val2 = e.getText().toString();
                        if (Val2.length() > 0) {
                            TextUtil.insertText(add_parallel_inductance4, Val2);
                            reactanceUnit1.setText("");
                            reactanceUnit2.setText("Henry");
                        } else {
                            reactanceUnit1.setText("F");
                            reactanceUnit2.setText("L");
                        }
                    }
                    if (Val1 != null && Val2 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0) {
                            String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2) / 1000000, 1, 1, 1, TO_FIND_CALCULATOR.REACT_I, 0);
                            TextUtil.insertText(result_parallel_inductance, result);
                        } else {
                            TextUtil.insertText(result_parallel_inductance, "");
                        }
                    }
                    break;
                case "REAC_C":
                    if (splitArr[1].equalsIgnoreCase("F (Frequency)")) {
                        Val1 = e.getText().toString();
                        if (Val1.length() > 0) {
                            TextUtil.insertText(add_parallel_inductance3, Val1);
                            reactanceUnit1.setText("");
                            reactanceUnit2.setText("Farads");
                        } else {
                            reactanceUnit1.setText("F");
                            reactanceUnit2.setText("C");
                        }

                    } else if (splitArr[1].equalsIgnoreCase("C (Capacitance)")) {
                        Val2 = e.getText().toString();
                        if (Val2.length() > 0) {
                            TextUtil.insertText(add_parallel_inductance4, Val2);
                            reactanceUnit1.setText("");
                            reactanceUnit2.setText("Farads");
                        } else {
                            reactanceUnit1.setText("F");
                            reactanceUnit2.setText("C");
                        }

                    }
                    if (Val1 != null && Val2 != null) {
                        if (Val1.length() > 0 && Val2.length() > 0) {
                            String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), 1, 1, 1, TO_FIND_CALCULATOR.REACT_C, 0);
                            TextUtil.insertText(result_parallel_inductance, result);
                        } else {
                            TextUtil.insertText(result_parallel_inductance, "");
                        }
                    }
                    break;
                case "IND_S":
                    if (splitArr[1].equalsIgnoreCase("L1 (Inductance1)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance1, Val1);

                    } else if (splitArr[1].equalsIgnoreCase("L2 (Inductance2)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance2, Val2);

                    } else if (splitArr[1].equalsIgnoreCase("L3 (Inductance3)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance3, Val3);

                    } else if (splitArr[1].equalsIgnoreCase("L4 (Inductance4)")) {
                        Val4 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance4, Val4);

                    } else if (splitArr[1].equalsIgnoreCase("L5 (Inductance5)")) {
                        Val5 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance5, Val5);

                    }
                    if (coil_count != null) {
                        int num_of_coils = Integer.parseInt(coil_count.getText().toString());
                        switch (num_of_coils) {
                            case 1:
                                if (Val1 != null) {
                                    if (Val1.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), 0, 0, 0, 0, TO_FIND_CALCULATOR.INDUC_S, 0);
                                        TextUtil.insertText(final_result_series_inductance_value, result);

                                    } else {
                                        TextUtil.insertText(final_result_series_inductance_value, "");
                                    }
                                }
                                break;

                            case 2:
                                if (Val1 != null && Val2 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), 0, 0, 0, TO_FIND_CALCULATOR.INDUC_S, 0);
                                        TextUtil.insertText(final_result_series_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_series_inductance_value, "");

                                    }
                                }
                                break;
                            case 3:
                                if (Val1 != null && Val2 != null && Val3 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3), 0, 0, TO_FIND_CALCULATOR.INDUC_S, 0);
                                        TextUtil.insertText(final_result_series_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_series_inductance_value, "");

                                    }
                                }
                                break;
                            case 4:
                                if (Val1 != null && Val2 != null && Val3 != null && Val4 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0 && Val4.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3), Double.parseDouble(Val4), 0, TO_FIND_CALCULATOR.INDUC_S, 0);
                                        TextUtil.insertText(final_result_series_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_series_inductance_value, "");

                                    }
                                }
                                break;
                            case 5:
                                if (Val1 != null && Val2 != null && Val3 != null && Val4 != null && Val5 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0 && Val4.length() > 0 && Val5.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3), Double.parseDouble(Val4), Double.parseDouble(Val5), TO_FIND_CALCULATOR.INDUC_S, 0);
                                        TextUtil.insertText(final_result_series_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_series_inductance_value, "");
                                    }
                                }
                                break;
                        }
                    }
                    break;
                case "IND_P":
                    if (splitArr[1].equalsIgnoreCase("L1 (Inductance1)")) {
                        Val1 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance1, Val1);

                    } else if (splitArr[1].equalsIgnoreCase("L2 (Inductance2)")) {
                        Val2 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance2, Val2);

                    } else if (splitArr[1].equalsIgnoreCase("L3 (Inductance3)")) {
                        Val3 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance3, Val3);

                    } else if (splitArr[1].equalsIgnoreCase("L4 (Inductance4)")) {
                        Val4 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance4, Val4);

                    } else if (splitArr[1].equalsIgnoreCase("L5 (Inductance5)")) {
                        Val5 = e.getText().toString();
                        TextUtil.insertText(add_parallel_inductance5, Val5);

                    }
                    if (coil_count != null) {
                        int num_of_coils = Integer.parseInt(coil_count.getText().toString());
                        switch (num_of_coils) {
                            case 1:
                                if (Val1 != null) {
                                    if (Val1.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), 0, 0, 0, 0, TO_FIND_CALCULATOR.INDUC_P, num_of_coils);
                                        TextUtil.insertText(final_result_parallel_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_parallel_inductance_value, "");

                                    }
                                }
                                break;

                            case 2:
                                if (Val1 != null && Val2 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), 0, 0, 0, TO_FIND_CALCULATOR.INDUC_P, num_of_coils);
                                        TextUtil.insertText(final_result_parallel_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_parallel_inductance_value, "");

                                    }
                                }
                                break;
                            case 3:
                                if (Val1 != null && Val2 != null && Val3 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3), 0, 0, TO_FIND_CALCULATOR.INDUC_P, num_of_coils);
                                        TextUtil.insertText(final_result_parallel_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_parallel_inductance_value, "");

                                    }
                                }
                                break;
                            case 4:
                                if (Val1 != null && Val2 != null && Val3 != null && Val4 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0 && Val4.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3), Double.parseDouble(Val4), 0, TO_FIND_CALCULATOR.INDUC_P, num_of_coils);
                                        TextUtil.insertText(final_result_parallel_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_parallel_inductance_value, "");

                                    }
                                }
                                break;
                            case 5:
                                if (Val1 != null && Val2 != null && Val3 != null && Val4 != null && Val5 != null) {
                                    if (Val1.length() > 0 && Val2.length() > 0 && Val3.length() > 0 && Val4.length() > 0 && Val5.length() > 0) {
                                        String result = toFindCalculator(Double.parseDouble(Val1), Double.parseDouble(Val2), Double.parseDouble(Val3), Double.parseDouble(Val4), Double.parseDouble(Val5), TO_FIND_CALCULATOR.INDUC_P, num_of_coils);
                                        TextUtil.insertText(final_result_parallel_inductance_value, result);
                                    } else {
                                        TextUtil.insertText(final_result_parallel_inductance_value, "");

                                    }
                                }
                                break;
                        }
                    }
                    break;

            }
        }
    }

}
