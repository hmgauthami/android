package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.utils.Constants;
import com.ascendlearning.jbl.uglys.utils.DecimalDigitsInputFilter;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import org.json.JSONException;
import org.readium.sdk.android.components.navigation.NavigationElement;
import org.readium.sdk.android.components.navigation.NavigationPoint;
import org.readium.sdk.android.launcher.model.OpenPageRequest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OhmsLawCalculator extends SuperActivity {
    private LinearLayout calculation_view;
    private TextView txtAtt1Unit;
    private TextView txtAtt2Unit;
    private TextView txtAtt3Unit;
    private TextView txtAtt4Unit;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private int bookmarkSubType;
    private String bookmarkName;
    private LinearLayout calculator_view;
    private TextView ohms_calculator_header;
    private TextView txtValue;
    private TextView txtAtt1;
    private TextView txtAtt2;
    private TextView value;
    private TextView txtValueUnit;
    private TextView multiplication_square;
    private TextView multiplication_square1;
    private TextView division_square1;
    private TextView division_square2;
    private TextView division1_square1;
    private TextView division1_square2;
    private TextView multiplication_squareroot;
    private TextView division_squareroot;
    private ImageView ohmsLawImage;
    private TextView multiplication_to;
    private TextView multiplication_from;
    private TextView div_to;
    private TextView div_from;
    private TextView value1;
    private TextView value2;
    private EditText to_find_known_value_editbox;
    private TableLayout tableLayout;
    private String val1 = null;
    private String val2 = null;

    private enum FORMULA_TYPE {
        WATT1, WATT2, WATT3, VOLT1, VOLT2, VOLT3, R1, R2, R3, AMPS1, AMPS2, AMPS3
    }

    private FORMULA_TYPE calculationType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ohms_law_calculator);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbarTop);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        bookmarkSubType = getIntent().getIntExtra("subtype", -1);
        if (bookmarkSubType != -1) {
            calculationType = FORMULA_TYPE.values()[bookmarkSubType];
        }
        createBookmarkData();
        makeLayout();

    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.OHMS.ordinal());
        bookmarkObj.setBookmarkCalType(bookmarkSubType);
    }

    private void makeBookmarkLayout() {

        switch (bookmarkSubType) {
            case 0:
                watt1Click();
                break;
            case 1:
                watt2Click();
                break;
            case 2:
                watt3Click();
                break;
            case 3:
                volt1Click();
                break;
            case 4:
                volt2Click();
                break;
            case 5:
                volt3Click();
                break;
            case 6:
                r1Click();
                break;
            case 7:
                r2Click();
                break;
            case 8:
                r3Click();
                break;
            case 9:
                amps1Click();
                break;
            case 10:
                amps2Click();
                break;
            case 11:
                amp3Click();
                break;
        }
    }

    private class CustomTextWatcher implements TextWatcher {
        private EditText e;

        private CustomTextWatcher(EditText mEditText) {
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
                case "W1":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        val1 = e.getText().toString();
                        if (val1.length() > 0) {
                            TextUtil.insertText(txtAtt1Unit, "V", Typeface.BOLD);
                            multiplication_from.setText(val1);
                        } else {
                            TextUtil.insertText(txtAtt1Unit, "E", Typeface.BOLD);
                            multiplication_from.setText("");

                        }

                    } else if (splitArr[1].equalsIgnoreCase("I (Amps)")) {
                        val2 = e.getText().toString();
                        if (val2.length() > 0) {
                            TextUtil.insertText(txtAtt2Unit, "A", Typeface.BOLD);
                            multiplication_to.setText(val2);
                        } else {
                            TextUtil.insertText(txtAtt2Unit, "I", Typeface.BOLD);
                            multiplication_to.setText("");
                        }
                    }
                    if (val1 != null && val2 != null) {
                        if (val1.length() > 0 && val2.length() > 0) {
                            String val = ohmsCalculator(Double.parseDouble(val1), Double.parseDouble(val2), calculationType);
                            value.setText(val);
                        } else {
                            value.setText("");
                        }
                    }
                    break;
                case "W2":
                    if (splitArr[1].equalsIgnoreCase("R (Ohms)")) {
                        val1 = e.getText().toString();
                        if (val1.length() > 0) {
                            TextUtil.insertText(txtAtt1Unit, "Ω", Typeface.BOLD);
                            multiplication_from.setText(val1);
                        } else {
                            TextUtil.insertText(txtAtt1Unit, "R", Typeface.BOLD);
                            multiplication_from.setText("");

                        }

                    } else if (splitArr[1].equalsIgnoreCase("I (Amps)")) {
                        val2 = e.getText().toString();
                        if (val2.length() > 0) {
                            TextUtil.insertText(txtAtt2Unit, "A", Typeface.BOLD);
                            multiplication_to.setText(val2);
                            multiplication_square.setVisibility(View.GONE);
                            multiplication_square1.setVisibility(View.VISIBLE);
                            TextUtil.insertText(multiplication_square1, "2", Typeface.BOLD);

                        } else {
                            TextUtil.insertText(txtAtt2Unit, "I", Typeface.BOLD);
                            multiplication_to.setText("");
                            multiplication_square.setVisibility(View.VISIBLE);
                            TextUtil.insertText(multiplication_square, "2", Typeface.BOLD);
                            multiplication_square1.setVisibility(View.GONE);
                        }
                    }
                    if (val1 != null && val2 != null) {
                        if (val1.length() > 0 && val2.length() > 0) {
                            String val = ohmsCalculator(Double.parseDouble(val1), Double.parseDouble(val2), calculationType);
                            value.setText(val);
                        } else {
                            value.setText("");
                        }
                    }
                    break;
                case "W3":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        val1 = e.getText().toString();
                        if (val1.length() > 0) {
                            TextUtil.insertText(txtAtt3Unit, "V", Typeface.BOLD);
                            div_from.setText(val1);
                            division_square1.setVisibility(View.VISIBLE);
                            TextUtil.insertText(division_square1, "2", Typeface.BOLD);
                            division1_square1.setVisibility(View.GONE);
                        } else {
                            TextUtil.insertText(txtAtt3Unit, "E", Typeface.BOLD);
                            div_from.setText("");
                            division1_square1.setVisibility(View.VISIBLE);
                            TextUtil.insertText(division1_square1, "2", Typeface.BOLD);
                            division_square1.setVisibility(View.GONE);
                        }

                    } else if (splitArr[1].equalsIgnoreCase("R (Ohms)")) {
                        val2 = e.getText().toString();
                        if (val2.length() > 0) {
                            TextUtil.insertText(txtAtt4Unit, "Ω", Typeface.BOLD);
                            div_to.setText(val2);
                        } else {
                            TextUtil.insertText(txtAtt4Unit, "R", Typeface.BOLD);
                            div_to.setText("");
                        }
                    }
                    if (val1 != null && val2 != null) {
                        if (val1.length() > 0 && val2.length() > 0) {
                            String val = ohmsCalculator(Double.parseDouble(val1), Double.parseDouble(val2), calculationType);
                            value.setText(val);
                        } else {
                            value.setText("");
                        }
                    }
                    break;
                case "V1":
                    if (splitArr[1].equalsIgnoreCase("R (Ohms)")) {
                        val1 = e.getText().toString();
                        if (val1.length() > 0) {
                            TextUtil.insertText(txtAtt1Unit, "Ω", Typeface.BOLD);
                            multiplication_from.setText(val1);
                        } else {
                            TextUtil.insertText(txtAtt1Unit, "R", Typeface.BOLD);
                            multiplication_from.setText("");

                        }

                    } else if (splitArr[1].equalsIgnoreCase("I (Amps)")) {
                        val2 = e.getText().toString();
                        if (val2.length() > 0) {
                            TextUtil.insertText(txtAtt2Unit, "A", Typeface.BOLD);
                            multiplication_to.setText(val2);
                        } else {
                            TextUtil.insertText(txtAtt2Unit, "I", Typeface.BOLD);
                            multiplication_to.setText("");
                        }
                    }
                    if (val1 != null && val2 != null) {
                        if (val1.length() > 0 && val2.length() > 0) {
                            String val = ohmsCalculator(Double.parseDouble(val1), Double.parseDouble(val2), calculationType);
                            value.setText(val);
                        } else {
                            value.setText("");
                        }
                    }
                    break;
                case "V2":
                    if (splitArr[1].equalsIgnoreCase("P (Watts)")) {
                        val1 = e.getText().toString();
                        if (val1.length() > 0) {
                            TextUtil.insertText(txtAtt3Unit, "W", Typeface.BOLD);
                            div_from.setText(val1);
                        } else {
                            TextUtil.insertText(txtAtt3Unit, "P", Typeface.BOLD);
                            div_from.setText("");

                        }

                    } else if (splitArr[1].equalsIgnoreCase("I (Amps)")) {
                        val2 = e.getText().toString();
                        if (val2.length() > 0) {
                            TextUtil.insertText(txtAtt4Unit, "A", Typeface.BOLD);
                            div_to.setText(val2);
                        } else {
                            TextUtil.insertText(txtAtt4Unit, "I", Typeface.BOLD);
                            div_to.setText("");
                        }
                    }
                    if (val1 != null && val2 != null) {
                        if (val1.length() > 0 && val2.length() > 0) {
                            String val = ohmsCalculator(Double.parseDouble(val1), Double.parseDouble(val2), calculationType);
                            value.setText(val);
                        } else {
                            value.setText("");
                        }
                    }
                    break;
                case "V3":
                    if (splitArr[1].equalsIgnoreCase("P (Watts)")) {
                        val1 = e.getText().toString();
                        if (val1.length() > 0) {
                            TextUtil.insertText(txtAtt1Unit, "W", Typeface.BOLD);
                            multiplication_from.setText(val1);
                        } else {
                            TextUtil.insertText(txtAtt1Unit, "P", Typeface.BOLD);
                            multiplication_from.setText("");

                        }

                    } else if (splitArr[1].equalsIgnoreCase("R (Ohms)")) {
                        val2 = e.getText().toString();
                        if (val2.length() > 0) {
                            TextUtil.insertText(txtAtt2Unit, "Ω", Typeface.BOLD);
                            multiplication_to.setText(val2);
                        } else {
                            TextUtil.insertText(txtAtt2Unit, "R", Typeface.BOLD);
                            multiplication_to.setText("");
                        }
                    }
                    if (val1 != null && val2 != null) {
                        if (val1.length() > 0 && val2.length() > 0) {
                            String val = ohmsCalculator(Double.parseDouble(val1), Double.parseDouble(val2), calculationType);
                            value.setText(val);
                        } else {
                            value.setText("");
                        }
                    }
                    break;
                case "R1":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        val1 = e.getText().toString();
                        if (val1.length() > 0) {
                            TextUtil.insertText(txtAtt3Unit, "V", Typeface.BOLD);
                            div_from.setText(val1);
                        } else {
                            TextUtil.insertText(txtAtt3Unit, "E", Typeface.BOLD);
                            div_from.setText("");

                        }

                    } else if (splitArr[1].equalsIgnoreCase("I (Amps)")) {
                        val2 = e.getText().toString();
                        if (val2.length() > 0) {
                            TextUtil.insertText(txtAtt4Unit, "A", Typeface.BOLD);
                            div_to.setText(val2);
                        } else {
                            TextUtil.insertText(txtAtt4Unit, "I", Typeface.BOLD);
                            div_to.setText("");
                        }
                    }
                    if (val1 != null && val2 != null) {
                        if (val1.length() > 0 && val2.length() > 0) {
                            String val = ohmsCalculator(Double.parseDouble(val1), Double.parseDouble(val2), calculationType);
                            value.setText(val);
                        } else {
                            value.setText("");
                        }
                    }
                    break;
                case "R2":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        val1 = e.getText().toString();
                        if (val1.length() > 0) {
                            TextUtil.insertText(txtAtt3Unit, "V", Typeface.BOLD);
                            div_from.setText(val1);
                            division_square1.setVisibility(View.VISIBLE);
                            TextUtil.insertText(division_square1, "2", Typeface.BOLD);
                            division1_square1.setVisibility(View.GONE);
                        } else {
                            TextUtil.insertText(txtAtt3Unit, "E", Typeface.BOLD);
                            div_from.setText("");
                            division1_square1.setVisibility(View.VISIBLE);
                            TextUtil.insertText(division1_square1, "2", Typeface.BOLD);
                            division_square1.setVisibility(View.GONE);

                        }

                    } else if (splitArr[1].equalsIgnoreCase("P (Watts)")) {
                        val2 = e.getText().toString();
                        if (val2.length() > 0) {
                            TextUtil.insertText(txtAtt4Unit, "W", Typeface.BOLD);
                            div_to.setText(val2);
                        } else {
                            TextUtil.insertText(txtAtt4Unit, "P", Typeface.BOLD);
                            div_to.setText("");
                        }
                    }
                    if (val1 != null && val2 != null) {
                        if (val1.length() > 0 && val2.length() > 0) {
                            String val = ohmsCalculator(Double.parseDouble(val1), Double.parseDouble(val2), calculationType);
                            value.setText(val);
                        } else {
                            value.setText("");
                        }
                    }
                    break;
                case "R3":
                    if (splitArr[1].equalsIgnoreCase("P (Watts)")) {
                        val1 = e.getText().toString();
                        if (val1.length() > 0) {
                            TextUtil.insertText(txtAtt3Unit, "W", Typeface.BOLD);
                            div_from.setText(val1);
                            division1_square2.setVisibility(View.GONE);
                            division_square2.setVisibility(View.VISIBLE);
                            TextUtil.insertText(division_square2, "2", Typeface.BOLD);
                        } else {
                            TextUtil.insertText(txtAtt3Unit, "P", Typeface.BOLD);
                            div_from.setText("");
                            division_square2.setVisibility(View.GONE);
                            division1_square2.setVisibility(View.VISIBLE);
                            TextUtil.insertText(division1_square2, "2", Typeface.BOLD);

                        }

                    } else if (splitArr[1].equalsIgnoreCase("I (Amps)")) {
                        val2 = e.getText().toString();
                        if (val2.length() > 0) {
                            TextUtil.insertText(txtAtt4Unit, "A", Typeface.BOLD);
                            div_to.setText(val2);
                        } else {
                            TextUtil.insertText(txtAtt4Unit, "I", Typeface.BOLD);
                            div_to.setText("");
                        }
                    }
                    if (val1 != null && val2 != null) {
                        if (val1.length() > 0 && val2.length() > 0) {
                            String val = ohmsCalculator(Double.parseDouble(val1), Double.parseDouble(val2), calculationType);
                            value.setText(val);
                        } else {
                            value.setText("");
                        }
                    }
                    break;
                case "A1":
                    if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        val1 = e.getText().toString();
                        if (val1.length() > 0) {
                            TextUtil.insertText(txtAtt3Unit, "V", Typeface.BOLD);
                            div_from.setText(val1);
                        } else {
                            TextUtil.insertText(txtAtt3Unit, "E", Typeface.BOLD);
                            div_from.setText("");

                        }

                    } else if (splitArr[1].equalsIgnoreCase("R (Ohms)")) {
                        val2 = e.getText().toString();
                        if (val2.length() > 0) {
                            TextUtil.insertText(txtAtt4Unit, "Ω", Typeface.BOLD);
                            div_to.setText(val2);
                        } else {
                            TextUtil.insertText(txtAtt4Unit, "R", Typeface.BOLD);
                            div_to.setText("");
                        }
                    }
                    if (val1 != null && val2 != null) {
                        if (val1.length() > 0 && val2.length() > 0) {
                            String val = ohmsCalculator(Double.parseDouble(val1), Double.parseDouble(val2), calculationType);
                            value.setText(val);
                        } else {
                            value.setText("");
                        }
                    }
                    break;
                case "A2":
                    if (splitArr[1].equalsIgnoreCase("P (Watts)")) {
                        val1 = e.getText().toString();
                        if (val1.length() > 0) {
                            TextUtil.insertText(txtAtt3Unit, "W", Typeface.BOLD);
                            div_from.setText(val1);
                        } else {
                            TextUtil.insertText(txtAtt3Unit, "P", Typeface.BOLD);
                            div_from.setText("");

                        }

                    } else if (splitArr[1].equalsIgnoreCase("E (Volts)")) {
                        val2 = e.getText().toString();
                        if (val2.length() > 0) {
                            TextUtil.insertText(txtAtt4Unit, "V", Typeface.BOLD);
                            div_to.setText(val2);
                        } else {
                            TextUtil.insertText(txtAtt4Unit, "E", Typeface.BOLD);
                            div_to.setText("");
                        }
                    }
                    if (val1 != null && val2 != null) {
                        if (val1.length() > 0 && val2.length() > 0) {
                            String val = ohmsCalculator(Double.parseDouble(val1), Double.parseDouble(val2), calculationType);
                            value.setText(val);
                        } else {
                            value.setText("");
                        }
                    }
                    break;
                case "A3":
                    if (splitArr[1].equalsIgnoreCase("P (Watts)")) {
                        val1 = e.getText().toString();
                        if (val1.length() > 0) {
                            TextUtil.insertText(txtAtt3Unit, "W", Typeface.BOLD);
                            div_from.setText(val1);
                        } else {
                            TextUtil.insertText(txtAtt3Unit, "P", Typeface.BOLD);
                            div_from.setText("");

                        }

                    } else if (splitArr[1].equalsIgnoreCase("R (Ohms)")) {
                        val2 = e.getText().toString();
                        if (val2.length() > 0) {
                            TextUtil.insertText(txtAtt4Unit, "Ω", Typeface.BOLD);
                            div_to.setText(val2);
                        } else {
                            TextUtil.insertText(txtAtt4Unit, "R", Typeface.BOLD);
                            div_to.setText("");
                        }
                    }
                    if (val1 != null && val2 != null) {
                        if (val1.length() > 0 && val2.length() > 0) {
                            String val = ohmsCalculator(Double.parseDouble(val1), Double.parseDouble(val2), calculationType);
                            value.setText(val);
                        } else {
                            value.setText("");
                        }
                    }
                    break;
            }
        }
    }

    private void drawInputValueView(ArrayList<String> inputVal, String type) {
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
                to_find_known_value_editbox.setGravity(Gravity.CENTER_HORIZONTAL);
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

                tableLayout.addView(tbrow);
            }
        }
    }

    private void watt1Click() {
        reinitializeView();
        calculation_view = (LinearLayout) findViewById(R.id.multiplication_layout);

        calculation_view.setVisibility(View.VISIBLE);
        txtAtt1Unit.setVisibility(View.VISIBLE);
        txtAtt2Unit.setVisibility(View.VISIBLE);
        txtAtt3Unit.setVisibility(View.GONE);
        txtAtt4Unit.setVisibility(View.GONE);
        multiplication_from.setVisibility(View.VISIBLE);
        multiplication_to.setVisibility(View.VISIBLE);
        div_from.setVisibility(View.GONE);
        div_to.setVisibility(View.GONE);
        multiplication_square.setVisibility(View.GONE);
        multiplication_square1.setVisibility(View.GONE);
        division_square1.setVisibility(View.GONE);
        division_square2.setVisibility(View.GONE);
        division1_square1.setVisibility(View.GONE);
        division1_square2.setVisibility(View.GONE);
        multiplication_squareroot.setVisibility(View.GONE);
        division_squareroot.setVisibility(View.GONE);

        calculationType = FORMULA_TYPE.WATT1;
        ohmsLawImage.setImageResource(R.drawable.ohms_watts1);
        ohms_calculator_header.setVisibility(View.GONE);
        calculator_view.setVisibility(View.VISIBLE);
        TextUtil.insertText(txtValue, "Watts", Typeface.BOLD);
        TextUtil.insertText(txtAtt1, "Volts", Typeface.BOLD);
        TextUtil.insertText(txtAtt2, "Amps", Typeface.BOLD);
        TextUtil.insertText(txtAtt1Unit, "E", Typeface.BOLD);
        TextUtil.insertText(txtAtt2Unit, "I", Typeface.BOLD);
        TextUtil.insertText(txtValueUnit, "W");
        TextUtil.insertText(value1, "P");
        TextUtil.insertText(value2, "P");
        ArrayList<String> list = new ArrayList<>();
        list.add("E (Volts)");
        list.add("I (Amps)");
        drawInputValueView(list, "W1");
        value.setText("");
        div_from.setText("");
        div_to.setText("");
        multiplication_from.setText("");
        multiplication_to.setText("");
        bookmarkName = "Ohm’s Law Calculator: Find Watts from Volts and Amps";
        bookmarkObj.setBookmarkCalType(calculationType.ordinal());
        bookmarkObj.setBookmarkName(bookmarkName);
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }
    }

    private void watt2Click() {
        reinitializeView();
        calculation_view = (LinearLayout) findViewById(R.id.multiplication_layout);

        calculation_view.setVisibility(View.VISIBLE);
        txtAtt1Unit.setVisibility(View.VISIBLE);
        txtAtt2Unit.setVisibility(View.VISIBLE);
        txtAtt3Unit.setVisibility(View.GONE);
        txtAtt4Unit.setVisibility(View.GONE);
        multiplication_from.setVisibility(View.VISIBLE);
        multiplication_to.setVisibility(View.VISIBLE);
        div_from.setVisibility(View.GONE);
        div_to.setVisibility(View.GONE);
        division_square1.setVisibility(View.GONE);
        multiplication_square.setVisibility(View.VISIBLE);
        multiplication_square1.setVisibility(View.GONE);
        division1_square1.setVisibility(View.GONE);
        division1_square2.setVisibility(View.GONE);
        TextUtil.insertText(multiplication_square, "2", Typeface.BOLD);
        division_square2.setVisibility(View.GONE);
        multiplication_squareroot.setVisibility(View.GONE);
        division_squareroot.setVisibility(View.GONE);

        calculationType = FORMULA_TYPE.WATT2;
        ohmsLawImage.setImageResource(R.drawable.ohms_watts2);
        ohms_calculator_header.setVisibility(View.GONE);
        calculator_view.setVisibility(View.VISIBLE);
        TextUtil.insertText(txtValue, "Watts", Typeface.BOLD);
        TextUtil.insertText(txtAtt1, "Ohms", Typeface.BOLD);
        TextUtil.insertText(txtAtt2, "Amps", Typeface.BOLD);
        TextUtil.insertText(txtAtt1Unit, "R", Typeface.BOLD);
        TextUtil.insertText(txtAtt2Unit, "I", Typeface.BOLD);
        TextUtil.insertText(txtValueUnit, "W");
        TextUtil.insertText(value1, "P");
        TextUtil.insertText(value2, "P");
        ArrayList<String> list = new ArrayList<>();
        list.add("R (Ohms)");
        list.add("I (Amps)");
        drawInputValueView(list, "W2");
        value.setText("");
        div_from.setText("");
        div_to.setText("");
        multiplication_from.setText("");
        multiplication_to.setText("");
        bookmarkName = "Ohm’s Law Calculator: Find Watts from Ohms and Amps";
        bookmarkObj.setBookmarkCalType(calculationType.ordinal());
        bookmarkObj.setBookmarkName(bookmarkName);
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }

    }

    private void watt3Click() {
        reinitializeView();
        calculation_view = (LinearLayout) findViewById(R.id.division_layout);

        calculation_view.setVisibility(View.VISIBLE);
        txtAtt1Unit.setVisibility(View.GONE);
        txtAtt2Unit.setVisibility(View.GONE);
        txtAtt3Unit.setVisibility(View.VISIBLE);
        txtAtt4Unit.setVisibility(View.VISIBLE);
        multiplication_from.setVisibility(View.GONE);
        multiplication_to.setVisibility(View.GONE);
        div_from.setVisibility(View.VISIBLE);
        div_to.setVisibility(View.VISIBLE);
        multiplication_square.setVisibility(View.GONE);
        multiplication_square1.setVisibility(View.GONE);
        division_square2.setVisibility(View.GONE);
        division1_square1.setVisibility(View.VISIBLE);
        TextUtil.insertText(division1_square1, "2", Typeface.BOLD);
        division_square1.setVisibility(View.GONE);
        division1_square2.setVisibility(View.GONE);
        multiplication_squareroot.setVisibility(View.GONE);
        division_squareroot.setVisibility(View.GONE);

        calculationType = FORMULA_TYPE.WATT3;
        ohmsLawImage.setImageResource(R.drawable.ohms_watts3);
        ohms_calculator_header.setVisibility(View.GONE);
        calculator_view.setVisibility(View.VISIBLE);
        TextUtil.insertText(txtValue, "Watts", Typeface.BOLD);
        TextUtil.insertText(txtAtt1, "Volts", Typeface.BOLD);
        TextUtil.insertText(txtAtt2, "Ohms", Typeface.BOLD);
        TextUtil.insertText(txtAtt3Unit, "E", Typeface.BOLD);
        TextUtil.insertText(txtAtt4Unit, "R", Typeface.BOLD);
        TextUtil.insertText(txtValueUnit, "W");
        TextUtil.insertText(value1, "P");
        TextUtil.insertText(value2, "P");
        ArrayList<String> list = new ArrayList<>();
        list.add("E (Volts)");
        list.add("R (Ohms)");
        drawInputValueView(list, "W3");
        value.setText("");
        div_from.setText("");
        div_to.setText("");
        multiplication_from.setText("");
        multiplication_to.setText("");
        bookmarkName = "Ohm’s Law Calculator: Find Watts from Volts and Ohms";
        bookmarkObj.setBookmarkCalType(calculationType.ordinal());
        bookmarkObj.setBookmarkName(bookmarkName);
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }
    }

    private void volt1Click() {
        reinitializeView();
        calculation_view = (LinearLayout) findViewById(R.id.multiplication_layout);

        calculation_view.setVisibility(View.VISIBLE);
        txtAtt1Unit.setVisibility(View.VISIBLE);
        txtAtt2Unit.setVisibility(View.VISIBLE);
        txtAtt3Unit.setVisibility(View.GONE);
        txtAtt4Unit.setVisibility(View.GONE);
        multiplication_from.setVisibility(View.VISIBLE);
        multiplication_to.setVisibility(View.VISIBLE);
        div_from.setVisibility(View.GONE);
        div_to.setVisibility(View.GONE);
        multiplication_square.setVisibility(View.GONE);
        multiplication_square1.setVisibility(View.GONE);
        division_square1.setVisibility(View.GONE);
        division_square2.setVisibility(View.GONE);
        division1_square1.setVisibility(View.GONE);
        division1_square2.setVisibility(View.GONE);
        multiplication_squareroot.setVisibility(View.GONE);
        division_squareroot.setVisibility(View.GONE);

        calculationType = FORMULA_TYPE.VOLT1;
        ohmsLawImage.setImageResource(R.drawable.ohms_volts1);
        ohms_calculator_header.setVisibility(View.GONE);
        calculator_view.setVisibility(View.VISIBLE);
        TextUtil.insertText(txtValue, "Volts", Typeface.BOLD);
        TextUtil.insertText(txtAtt1, "Ohms", Typeface.BOLD);
        TextUtil.insertText(txtAtt2, "Amps", Typeface.BOLD);
        TextUtil.insertText(txtAtt1Unit, "R", Typeface.BOLD);
        TextUtil.insertText(txtAtt2Unit, "I", Typeface.BOLD);
        TextUtil.insertText(txtValueUnit, "V");
        TextUtil.insertText(value1, "E");
        TextUtil.insertText(value2, "E");
        ArrayList<String> list = new ArrayList<>();
        list.add("R (Ohms)");
        list.add("I (Amps)");
        drawInputValueView(list, "V1");
        value.setText("");
        div_from.setText("");
        div_to.setText("");
        multiplication_from.setText("");
        multiplication_to.setText("");
        bookmarkName = "Ohm’s Law Calculator: Find Volts from Watts and Amps";
        bookmarkObj.setBookmarkCalType(calculationType.ordinal());
        bookmarkObj.setBookmarkName(bookmarkName);
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }
    }

    private void volt2Click() {
        reinitializeView();

        calculation_view = (LinearLayout) findViewById(R.id.division_layout);

        calculation_view.setVisibility(View.VISIBLE);
        txtAtt1Unit.setVisibility(View.GONE);
        txtAtt2Unit.setVisibility(View.GONE);
        txtAtt3Unit.setVisibility(View.VISIBLE);
        txtAtt4Unit.setVisibility(View.VISIBLE);
        multiplication_from.setVisibility(View.GONE);
        multiplication_to.setVisibility(View.GONE);
        div_from.setVisibility(View.VISIBLE);
        div_to.setVisibility(View.VISIBLE);
        multiplication_square.setVisibility(View.GONE);
        multiplication_square1.setVisibility(View.GONE);
        division_square1.setVisibility(View.GONE);
        division_square2.setVisibility(View.GONE);
        division1_square1.setVisibility(View.GONE);
        division1_square2.setVisibility(View.GONE);
        multiplication_squareroot.setVisibility(View.GONE);
        division_squareroot.setVisibility(View.GONE);

        calculationType = FORMULA_TYPE.VOLT2;
        ohmsLawImage.setImageResource(R.drawable.ohms_volts2);
        ohms_calculator_header.setVisibility(View.GONE);
        calculator_view.setVisibility(View.VISIBLE);
        TextUtil.insertText(txtValue, "Volts", Typeface.BOLD);
        TextUtil.insertText(txtAtt1, "Watts", Typeface.BOLD);
        TextUtil.insertText(txtAtt2, "Amps", Typeface.BOLD);
        TextUtil.insertText(txtAtt3Unit, "P", Typeface.BOLD);
        TextUtil.insertText(txtAtt4Unit, "I", Typeface.BOLD);
        TextUtil.insertText(txtValueUnit, "V");
        TextUtil.insertText(value1, "E");
        TextUtil.insertText(value2, "E");
        ArrayList<String> list = new ArrayList<>();
        list.add("P (Watts)");
        list.add("I (Amps)");
        drawInputValueView(list, "V2");
        value.setText("");
        div_from.setText("");
        div_to.setText("");
        multiplication_from.setText("");
        multiplication_to.setText("");
        bookmarkName = "Ohm’s Law Calculator: Find Volts from Ohms and Amps";
        bookmarkObj.setBookmarkCalType(calculationType.ordinal());
        bookmarkObj.setBookmarkName(bookmarkName);
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }
    }

    private void volt3Click() {
        reinitializeView();
        calculation_view = (LinearLayout) findViewById(R.id.multiplication_layout);

        calculation_view.setVisibility(View.VISIBLE);
        txtAtt1Unit.setVisibility(View.VISIBLE);
        txtAtt2Unit.setVisibility(View.VISIBLE);
        txtAtt3Unit.setVisibility(View.GONE);
        txtAtt4Unit.setVisibility(View.GONE);
        multiplication_from.setVisibility(View.VISIBLE);
        multiplication_to.setVisibility(View.VISIBLE);
        div_from.setVisibility(View.GONE);
        div_to.setVisibility(View.GONE);
        multiplication_square.setVisibility(View.GONE);
        multiplication_square1.setVisibility(View.GONE);
        division_square1.setVisibility(View.GONE);
        division_square2.setVisibility(View.GONE);
        division1_square1.setVisibility(View.GONE);
        division1_square2.setVisibility(View.GONE);
        multiplication_squareroot.setVisibility(View.VISIBLE);
        TextUtil.insertText(multiplication_squareroot, getString(R.string.sqr_root1), Typeface.BOLD);
        division_squareroot.setVisibility(View.GONE);

        calculationType = FORMULA_TYPE.VOLT3;
        ohmsLawImage.setImageResource(R.drawable.ohms_volts3);
        ohms_calculator_header.setVisibility(View.GONE);
        calculator_view.setVisibility(View.VISIBLE);
        TextUtil.insertText(txtValue, "Volts", Typeface.BOLD);
        TextUtil.insertText(txtAtt1, "Watts", Typeface.BOLD);
        TextUtil.insertText(txtAtt2, "Ohms", Typeface.BOLD);
        TextUtil.insertText(txtAtt1Unit, "P", Typeface.BOLD);
        TextUtil.insertText(txtAtt2Unit, "R", Typeface.BOLD);
        TextUtil.insertText(txtValueUnit, "V");
        TextUtil.insertText(value1, "E");
        TextUtil.insertText(value2, "E");
        ArrayList<String> list = new ArrayList<>();
        list.add("P (Watts)");
        list.add("R (Ohms)");
        drawInputValueView(list, "V3");
        value.setText("");
        div_from.setText("");
        div_to.setText("");
        multiplication_from.setText("");
        multiplication_to.setText("");
        bookmarkName = "Ohm’s Law Calculator: Find Volts from Watts and Ohms";
        bookmarkObj.setBookmarkCalType(calculationType.ordinal());
        bookmarkObj.setBookmarkName(bookmarkName);
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }

    }

    private void r1Click() {
        reinitializeView();
        calculation_view = (LinearLayout) findViewById(R.id.division_layout);

        calculation_view.setVisibility(View.VISIBLE);
        txtAtt1Unit.setVisibility(View.GONE);
        txtAtt2Unit.setVisibility(View.GONE);
        txtAtt3Unit.setVisibility(View.VISIBLE);
        txtAtt4Unit.setVisibility(View.VISIBLE);
        multiplication_from.setVisibility(View.GONE);
        multiplication_to.setVisibility(View.GONE);
        div_from.setVisibility(View.VISIBLE);
        div_to.setVisibility(View.VISIBLE);
        multiplication_square.setVisibility(View.GONE);
        multiplication_square1.setVisibility(View.GONE);
        division_square1.setVisibility(View.GONE);
        division_square2.setVisibility(View.GONE);
        division1_square1.setVisibility(View.GONE);
        division1_square2.setVisibility(View.GONE);
        multiplication_squareroot.setVisibility(View.GONE);
        division_squareroot.setVisibility(View.GONE);

        calculationType = FORMULA_TYPE.R1;
        ohmsLawImage.setImageResource(R.drawable.ohms_ohms1);
        ohms_calculator_header.setVisibility(View.GONE);
        calculator_view.setVisibility(View.VISIBLE);
        TextUtil.insertText(txtValue, "Ohms", Typeface.BOLD);
        TextUtil.insertText(txtAtt1, "Volts", Typeface.BOLD);
        TextUtil.insertText(txtAtt2, "Amps", Typeface.BOLD);
        TextUtil.insertText(txtAtt3Unit, "E", Typeface.BOLD);
        TextUtil.insertText(txtAtt4Unit, "I", Typeface.BOLD);
        TextUtil.insertText(txtValueUnit, "Ω");
        TextUtil.insertText(value1, "R");
        TextUtil.insertText(value2, "R");
        ArrayList<String> list = new ArrayList<>();
        list.add("E (Volts)");
        list.add("I (Amps)");
        drawInputValueView(list, "R1");
        value.setText("");
        div_from.setText("");
        div_to.setText("");
        multiplication_from.setText("");
        multiplication_to.setText("");
        bookmarkName = "Ohm’s Law Calculator: Find Ohms from Volts and Amps";
        bookmarkObj.setBookmarkCalType(calculationType.ordinal());
        bookmarkObj.setBookmarkName(bookmarkName);
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }

    }

    private void r2Click() {
        reinitializeView();
        calculation_view = (LinearLayout) findViewById(R.id.division_layout);

        calculation_view.setVisibility(View.VISIBLE);
        txtAtt1Unit.setVisibility(View.GONE);
        txtAtt2Unit.setVisibility(View.GONE);
        txtAtt3Unit.setVisibility(View.VISIBLE);
        txtAtt4Unit.setVisibility(View.VISIBLE);
        multiplication_from.setVisibility(View.GONE);
        multiplication_to.setVisibility(View.GONE);
        div_from.setVisibility(View.VISIBLE);
        div_to.setVisibility(View.VISIBLE);
        multiplication_square.setVisibility(View.GONE);
        multiplication_square1.setVisibility(View.GONE);
        division1_square1.setVisibility(View.VISIBLE);
        TextUtil.insertText(division1_square1, "2", Typeface.BOLD);
        division_square1.setVisibility(View.GONE);
        division_square1.setVisibility(View.GONE);
        division_square2.setVisibility(View.GONE);
        multiplication_squareroot.setVisibility(View.GONE);
        division_squareroot.setVisibility(View.GONE);

        calculationType = FORMULA_TYPE.R2;
        ohmsLawImage.setImageResource(R.drawable.ohms_ohms2);
        ohms_calculator_header.setVisibility(View.GONE);
        calculator_view.setVisibility(View.VISIBLE);
        TextUtil.insertText(txtValue, "Ohms", Typeface.BOLD);
        TextUtil.insertText(txtAtt1, "Volts", Typeface.BOLD);
        TextUtil.insertText(txtAtt2, "Watts", Typeface.BOLD);
        TextUtil.insertText(txtAtt3Unit, "E", Typeface.BOLD);
        TextUtil.insertText(txtAtt4Unit, "P", Typeface.BOLD);
        TextUtil.insertText(txtValueUnit, "Ω");
        TextUtil.insertText(value1, "R");
        TextUtil.insertText(value2, "R");
        ArrayList<String> list = new ArrayList<>();
        list.add("E (Volts)");
        list.add("P (Watts)");
        drawInputValueView(list, "R2");
        value.setText("");
        div_from.setText("");
        div_to.setText("");
        multiplication_from.setText("");
        multiplication_to.setText("");
        bookmarkName = "Ohm’s Law Calculator: Find Ohms from Volts and Watts";
        bookmarkObj.setBookmarkCalType(calculationType.ordinal());
        bookmarkObj.setBookmarkName(bookmarkName);
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }

    }

    private void r3Click() {
        reinitializeView();
        calculation_view = (LinearLayout) findViewById(R.id.division_layout);

        calculation_view.setVisibility(View.VISIBLE);
        txtAtt1Unit.setVisibility(View.GONE);
        txtAtt2Unit.setVisibility(View.GONE);
        txtAtt3Unit.setVisibility(View.VISIBLE);
        txtAtt4Unit.setVisibility(View.VISIBLE);
        multiplication_from.setVisibility(View.GONE);
        multiplication_to.setVisibility(View.GONE);
        div_from.setVisibility(View.VISIBLE);
        div_to.setVisibility(View.VISIBLE);
        multiplication_square.setVisibility(View.GONE);
        multiplication_square1.setVisibility(View.GONE);
        division_square2.setVisibility(View.GONE);
        division1_square2.setVisibility(View.VISIBLE);
        TextUtil.insertText(division1_square2, "2", Typeface.BOLD);
        division_square1.setVisibility(View.GONE);
        division1_square1.setVisibility(View.GONE);
        multiplication_squareroot.setVisibility(View.GONE);
        division_squareroot.setVisibility(View.GONE);

        calculationType = FORMULA_TYPE.R3;
        ohmsLawImage.setImageResource(R.drawable.ohms_ohms3);
        ohms_calculator_header.setVisibility(View.GONE);
        calculator_view.setVisibility(View.VISIBLE);
        TextUtil.insertText(txtValue, "Ohms", Typeface.BOLD);
        TextUtil.insertText(txtAtt1, "Watts", Typeface.BOLD);
        TextUtil.insertText(txtAtt2, "Amps", Typeface.BOLD);
        TextUtil.insertText(txtAtt3Unit, "P", Typeface.BOLD);
        TextUtil.insertText(txtAtt4Unit, "I", Typeface.BOLD);
        TextUtil.insertText(txtValueUnit, "Ω");
        TextUtil.insertText(value1, "R");
        TextUtil.insertText(value2, "R");
        ArrayList<String> list = new ArrayList<>();
        list.add("P (Watts)");
        list.add("I (Amps)");
        drawInputValueView(list, "R3");
        value.setText("");
        div_from.setText("");
        div_to.setText("");
        multiplication_from.setText("");
        multiplication_to.setText("");
        bookmarkName = "Ohm’s Law Calculator: Find Ohms from Watts and Amps";
        bookmarkObj.setBookmarkCalType(calculationType.ordinal());
        bookmarkObj.setBookmarkName(bookmarkName);
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }

    }

    private void amps1Click() {
        reinitializeView();

        calculation_view = (LinearLayout) findViewById(R.id.division_layout);

        calculation_view.setVisibility(View.VISIBLE);
        txtAtt1Unit.setVisibility(View.GONE);
        txtAtt2Unit.setVisibility(View.GONE);
        txtAtt3Unit.setVisibility(View.VISIBLE);
        txtAtt4Unit.setVisibility(View.VISIBLE);
        multiplication_from.setVisibility(View.GONE);
        multiplication_to.setVisibility(View.GONE);
        div_from.setVisibility(View.VISIBLE);
        div_to.setVisibility(View.VISIBLE);
        multiplication_square.setVisibility(View.GONE);
        multiplication_square1.setVisibility(View.GONE);
        division_square1.setVisibility(View.GONE);
        division_square2.setVisibility(View.GONE);
        division1_square1.setVisibility(View.GONE);
        division1_square2.setVisibility(View.GONE);
        multiplication_squareroot.setVisibility(View.GONE);
        division_squareroot.setVisibility(View.GONE);

        calculationType = FORMULA_TYPE.AMPS1;
        ohmsLawImage.setImageResource(R.drawable.ohms_amps1);
        ohms_calculator_header.setVisibility(View.GONE);
        calculator_view.setVisibility(View.VISIBLE);
        TextUtil.insertText(txtValue, "Amps", Typeface.BOLD);
        TextUtil.insertText(txtAtt1, "Volts", Typeface.BOLD);
        TextUtil.insertText(txtAtt2, "Ohms", Typeface.BOLD);
        TextUtil.insertText(txtAtt3Unit, "E", Typeface.BOLD);
        TextUtil.insertText(txtAtt4Unit, "R", Typeface.BOLD);
        TextUtil.insertText(txtValueUnit, "A");
        TextUtil.insertText(value1, "I");
        TextUtil.insertText(value2, "I");
        ArrayList<String> list = new ArrayList<>();
        list.add("E (Volts)");
        list.add("R (Ohms)");
        drawInputValueView(list, "A1");
        value.setText("");
        div_from.setText("");
        div_to.setText("");
        multiplication_from.setText("");
        multiplication_to.setText("");
        bookmarkName = "Ohm’s Law Calculator: Find Amps from Volts and Ohms";
        bookmarkObj.setBookmarkCalType(calculationType.ordinal());
        bookmarkObj.setBookmarkName(bookmarkName);
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }

    }

    private void amps2Click() {
        reinitializeView();

        calculation_view = (LinearLayout) findViewById(R.id.division_layout);

        calculation_view.setVisibility(View.VISIBLE);
        txtAtt1Unit.setVisibility(View.GONE);
        txtAtt2Unit.setVisibility(View.GONE);
        txtAtt3Unit.setVisibility(View.VISIBLE);
        txtAtt4Unit.setVisibility(View.VISIBLE);
        multiplication_from.setVisibility(View.GONE);
        multiplication_to.setVisibility(View.GONE);
        div_from.setVisibility(View.VISIBLE);
        div_to.setVisibility(View.VISIBLE);
        multiplication_square.setVisibility(View.GONE);
        multiplication_square1.setVisibility(View.GONE);
        division_square1.setVisibility(View.GONE);
        division_square2.setVisibility(View.GONE);
        division1_square1.setVisibility(View.GONE);
        division1_square2.setVisibility(View.GONE);
        multiplication_squareroot.setVisibility(View.GONE);
        division_squareroot.setVisibility(View.GONE);

        calculationType = FORMULA_TYPE.AMPS2;
        ohmsLawImage.setImageResource(R.drawable.ohms_amps2);
        ohms_calculator_header.setVisibility(View.GONE);
        calculator_view.setVisibility(View.VISIBLE);
        TextUtil.insertText(txtValue, "Amps", Typeface.BOLD);
        TextUtil.insertText(txtAtt1, "Watts", Typeface.BOLD);
        TextUtil.insertText(txtAtt2, "Volts", Typeface.BOLD);
        TextUtil.insertText(txtAtt3Unit, "P", Typeface.BOLD);
        TextUtil.insertText(txtAtt4Unit, "E", Typeface.BOLD);
        TextUtil.insertText(txtValueUnit, "A");
        TextUtil.insertText(value1, "I");
        TextUtil.insertText(value2, "I");
        ArrayList<String> list = new ArrayList<>();
        list.add("P (Watts)");
        list.add("E (Volts)");
        drawInputValueView(list, "A2");
        value.setText("");
        div_from.setText("");
        div_to.setText("");
        multiplication_from.setText("");
        multiplication_to.setText("");
        bookmarkName = "Ohm’s Law Calculator: Find Amps from Watts and Volts";
        bookmarkObj.setBookmarkCalType(calculationType.ordinal());
        bookmarkObj.setBookmarkName(bookmarkName);
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }

    }

    private void amp3Click() {
        reinitializeView();

        calculation_view = (LinearLayout) findViewById(R.id.division_layout);

        calculation_view.setVisibility(View.VISIBLE);
        txtAtt1Unit.setVisibility(View.GONE);
        txtAtt2Unit.setVisibility(View.GONE);
        txtAtt3Unit.setVisibility(View.VISIBLE);
        txtAtt4Unit.setVisibility(View.VISIBLE);
        multiplication_from.setVisibility(View.GONE);
        multiplication_to.setVisibility(View.GONE);
        div_from.setVisibility(View.VISIBLE);
        div_to.setVisibility(View.VISIBLE);
        multiplication_square.setVisibility(View.GONE);
        multiplication_square1.setVisibility(View.GONE);
        division_square1.setVisibility(View.GONE);
        division_square2.setVisibility(View.GONE);
        division1_square1.setVisibility(View.GONE);
        division1_square2.setVisibility(View.GONE);
        multiplication_squareroot.setVisibility(View.GONE);
        division_squareroot.setVisibility(View.VISIBLE);
        TextUtil.insertText(division_squareroot, getString(R.string.sqr_root1), Typeface.BOLD);

        calculationType = FORMULA_TYPE.AMPS3;
        ohmsLawImage.setImageResource(R.drawable.ohms_amps3);
        ohms_calculator_header.setVisibility(View.GONE);
        calculator_view.setVisibility(View.VISIBLE);
        TextUtil.insertText(txtValue, "Amps", Typeface.BOLD);
        TextUtil.insertText(txtAtt1, "Watts", Typeface.BOLD);
        TextUtil.insertText(txtAtt2, "Ohms", Typeface.BOLD);
        TextUtil.insertText(txtAtt3Unit, "P", Typeface.BOLD);
        TextUtil.insertText(txtAtt4Unit, "R", Typeface.BOLD);
        TextUtil.insertText(txtValueUnit, "A");
        TextUtil.insertText(value1, "I");
        TextUtil.insertText(value2, "I");
        ArrayList<String> list = new ArrayList<>();
        list.add("P (Watts)");
        list.add("R (Ohms)");
        drawInputValueView(list, "A3");
        value.setText("");
        div_from.setText("");
        div_to.setText("");
        multiplication_from.setText("");
        multiplication_to.setText("");
        bookmarkName = "Ohm’s Law Calculator: Find Amps from Watts and Ohms";
        bookmarkObj.setBookmarkCalType(calculationType.ordinal());
        bookmarkObj.setBookmarkName(bookmarkName);
        if (menu != null) {
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }
    }

    private void makeLayout() {

        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().toLowerCase().trim().contains("Ohm’s Law".toLowerCase().trim())) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(OhmsLawCalculator.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, "Ohm’s Law");
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
        calculator_view = (LinearLayout) findViewById(R.id.calculator_view);
        ohms_calculator_header = (TextView) findViewById(R.id.ohms_calculator_header);
        txtValue = (TextView) findViewById(R.id.textview2);
        txtAtt1 = (TextView) findViewById(R.id.textview4);
        txtAtt2 = (TextView) findViewById(R.id.textview6);
        value = (TextView) findViewById(R.id.value);
        txtValueUnit = (TextView) findViewById(R.id.value_unit);
        multiplication_square = (TextView) findViewById(R.id.multiplication_square);
        multiplication_square1 = (TextView) findViewById(R.id.multiplication_square1);
        division_square1 = (TextView) findViewById(R.id.division_square1);
        division_square2 = (TextView) findViewById(R.id.division_square2);
        division1_square1 = (TextView) findViewById(R.id.division1_square1);
        division1_square2 = (TextView) findViewById(R.id.division1_square2);
        multiplication_squareroot = (TextView) findViewById(R.id.multiplication_squareroot);
        division_squareroot = (TextView) findViewById(R.id.division_squareroot);
        ohmsLawImage = (ImageView) findViewById(R.id.ohmslaw_imageview);
        txtAtt1Unit = (TextView) findViewById(R.id.multiplication_from_unit);
        txtAtt2Unit = (TextView) findViewById(R.id.multiplication_to_unit);
        txtAtt3Unit = (TextView) findViewById(R.id.div_from_unit);
        txtAtt4Unit = (TextView) findViewById(R.id.div_to_unit);
        multiplication_from = (TextView) findViewById(R.id.multiplication_from);
        multiplication_to = (TextView) findViewById(R.id.multiplication_to);
        div_to = (TextView) findViewById(R.id.div_to);
        div_from = (TextView) findViewById(R.id.div_from);
        value1 = (TextView) findViewById(R.id.value1);
        value2 = (TextView) findViewById(R.id.value2);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        makeBookmarkLayout();
        bookmarkName = "Ohm’s Law Calculator";

        Button btn1 = (Button) findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watt1Click();
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watt2Click();
            }
        });
        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watt3Click();
            }
        });
        Button btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volt1Click();
            }
        });
        Button btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volt2Click();
            }
        });
        Button btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volt3Click();
            }
        });
        Button btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1Click();
            }
        });
        Button btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r2Click();
            }
        });
        Button btn9 = (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r3Click();
            }
        });
        Button btn10 = (Button) findViewById(R.id.btn10);
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amps1Click();
            }
        });
        Button btn11 = (Button) findViewById(R.id.btn11);
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amps2Click();
            }
        });
        Button btn12 = (Button) findViewById(R.id.btn12);
        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amp3Click();
            }
        });


    }

    private void reinitializeView() {
        if (calculation_view != null) {
            calculation_view.setVisibility(View.GONE);
            calculation_view = null;
        }
        if (tableLayout != null) {
            int count = tableLayout.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = tableLayout.getChildAt(i);
                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
            }
        }


    }

    private String ohmsCalculator(double val1, double val2, FORMULA_TYPE type) {

        double result = 0;

        switch (type) {

            case WATT1:
                result = val1 * val2;
                break;
            case WATT2:
                result = val1 * val2 * val2;
                break;
            case WATT3:
                result = (val1 * val1) / val2;
                break;
            case VOLT1:
                result = val1 * val2;
                break;
            case VOLT2:
                result = val1 / val2;
                break;
            case VOLT3:
                result = Math.sqrt(val1 * val2);
                break;
            case R1:
                result = val1 / val2;
                break;
            case R2:
                result = (val1 * val1) / val2;
                break;
            case R3:
                result = val1 / (val2 * val2);
                break;
            case AMPS1:
                result = val1 / val2;
                break;
            case AMPS2:
                result = val1 / val2;
                break;
            case AMPS3:
                result = Math.sqrt(val1 / val2);
                break;
        }
        return TextUtil.convertToExponential(result);

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
            if (calculationType != null) {
                bookmarkObj.setBookmarkCalType(calculationType.ordinal());
                bookmarkObj.setBookmarkName(bookmarkName);
                application.sd.toggleBookmark(bookmarkObj);
                if (application.sd.isBookmarked(bookmarkObj)) {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                } else {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

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
        }else if (id == R.id.action_bookmarks) {
            Intent intent = new Intent(this, BookmarksActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
