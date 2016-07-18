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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.VoltageDrop;
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

public class VoltageDropCalculatorActivity extends SuperActivity implements Spinner.OnItemSelectedListener, UICallback {

    private Spinner circuit_type_spinner;
    private Spinner conductor_type_spinner;
    private Spinner wire_size_spinner;
    private TableLayout table_layout;
    private LinearLayout result_view;
    private TextView result_header;
    private EditText length_edit_text;
    private EditText current_edit_text;
    private EditText voltage_edit_text;
    private TextView mul_from_amps1;
    private TextView mul_from_amps1_value;
    private TextView mul_from_amps2;
    private TextView mul_from_unit_amps2;
    private TextView mul_from_amps3;
    private TextView mul_from_unit_amps3;
    private TextView div_to_amps;
    private TextView div_to_unit_amps;
    private TextView mul_from_amps1_symbol;
    private TextView result;
    private TextView result1;
    private TextView result2;
    private UglysController mController;
    private UglysResponse mResponse;
    private ArrayList<VoltageDrop> areaArrayList;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private ArrayList<String> circuit_types;
    private ArrayList<String> conductor_types;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voltage_drop_activity);
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
        areaArrayList = mResponse.getVoltageDropArrayList();

        int voltageType = getIntent().getIntExtra("voltageType", -1);

        createBookmarkData();
        makeLayout();

        if (voltageType != -1) {
            circuit_type_spinner.setTag(R.id.spinner_pos, voltageType + 1);
            circuit_type_spinner.setSelection(voltageType + 1);
            conductor_type_spinner.setVisibility(View.VISIBLE);
            wire_size_spinner.setVisibility(View.VISIBLE);
            table_layout.setVisibility(View.VISIBLE);
            result_view.setVisibility(View.VISIBLE);
            result_header.setVisibility(View.VISIBLE);

            ArrayList<String> wireSize = new ArrayList<>();
            for (int i = 0; i < areaArrayList.size(); i++) {
                wireSize.add(areaArrayList.get(i).getSize());
            }
            wireSize.add(0, getString(R.string.voltage_drop_wire_size_default));
            wire_size_spinner.setSelection(0);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, wireSize);
            wire_size_spinner.setAdapter(adapter);

            conductor_types = new ArrayList<>();
            conductor_types.add(0, getString(R.string.voltage_drop_wire_conductor_type_default));
            conductor_types.add(1, getString(R.string.voltage_drop_wire_conductor_type1));
            conductor_types.add(2, getString(R.string.voltage_drop_wire_conductor_type2));
            conductor_type_spinner.setSelection(0);
            adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, conductor_types);
            conductor_type_spinner.setAdapter(adapter);

            switch (voltageType + 1) {
                case 1:
                    mul_from_amps1_value.setText("2");
                    break;
                case 2:
                    mul_from_amps1_value.setText("1.73");
                    break;
            }
            bookmarkObj.setBookmarkCalType(voltageType);

        }
    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.VOLTAGE_DROP.ordinal());
    }

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_VOLTAGE_DROP_CALCULATOR);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);
    }

    public void makeLayout() {

        circuit_type_spinner = (Spinner) findViewById(R.id.circuit_type_spinner);
        conductor_type_spinner = (Spinner) findViewById(R.id.conductor_type_spinner);
        wire_size_spinner = (Spinner) findViewById(R.id.wire_size_spinner);
        table_layout = (TableLayout) findViewById(R.id.table_layout);
        result_view = (LinearLayout) findViewById(R.id.result_view);
        result_header = (TextView) findViewById(R.id.result_header);
        result = (TextView) findViewById(R.id.result);
        result1 = (TextView) findViewById(R.id.result1);
        result2 = (TextView) findViewById(R.id.result2);
        current_edit_text = (EditText) findViewById(R.id.current_edit_text);
        current_edit_text.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
        length_edit_text = (EditText) findViewById(R.id.length_edit_text);
        length_edit_text.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
        voltage_edit_text = (EditText) findViewById(R.id.voltage_edit_text);
        voltage_edit_text.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
        mul_from_amps1_symbol = (TextView) findViewById(R.id.mul_from_amps1_symbol);
        mul_from_amps1 = (TextView) findViewById(R.id.mul_from_amps1);
        mul_from_amps2 = (TextView) findViewById(R.id.mul_from_amps2);
        mul_from_amps3 = (TextView) findViewById(R.id.mul_from_amps3);
        mul_from_amps1.setText("K");
        mul_from_amps1_value = (TextView) findViewById(R.id.mul_from_amps1_value);
        mul_from_unit_amps2 = (TextView) findViewById(R.id.mul_from_unit_amps2);
        mul_from_unit_amps3 = (TextView) findViewById(R.id.mul_from_unit_amps3);
        mul_from_unit_amps2.setText("L");
        mul_from_unit_amps3.setText("I");
        div_to_amps = (TextView) findViewById(R.id.div_to_amps);
        div_to_unit_amps = (TextView) findViewById(R.id.div_to_unit_amps);
        div_to_unit_amps.setText("Cm");

        circuit_type_spinner.setOnItemSelectedListener(this);
        conductor_type_spinner.setOnItemSelectedListener(this);
        wire_size_spinner.setOnItemSelectedListener(this);

        current_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (current_edit_text.getText().length() > 0) {
                    mul_from_amps3.setText(current_edit_text.getText().toString());
                    mul_from_unit_amps3.setText("A");
                } else {
                    mul_from_amps3.setText("");
                    mul_from_unit_amps3.setText("I");
                }
                if (mul_from_amps1.getText().length() > 0 && !mul_from_amps1.getText().toString().equalsIgnoreCase("k") && mul_from_amps2.getText().length() > 0 && mul_from_amps3.getText().length() > 0 && div_to_amps.getText().length() > 0) {
                    result.setText(calculateVoltageDrop(Double.parseDouble(mul_from_amps1.getText().toString()), Double.parseDouble(mul_from_amps2.getText().toString()), Double.parseDouble(mul_from_amps3.getText().toString()), Double.parseDouble(div_to_amps.getText().toString()), circuit_type_spinner.getSelectedItemPosition()));
                } else {
                    result.setText("");
                }
                if (voltage_edit_text.getText().length() > 0 && result.getText().length() > 0) {
                    result1.setText(calculateVoltagePercentDrop(Double.parseDouble(voltage_edit_text.getText().toString()), Double.parseDouble(result.getText().toString())));
                    result2.setText(calculateVoltageAtLoad(Double.parseDouble(voltage_edit_text.getText().toString()), Double.parseDouble(result.getText().toString())));
                } else {
                    result1.setText("");
                    result2.setText("");
                }
            }
        });
        voltage_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (voltage_edit_text.getText().length() > 0 && result.getText().length() > 0) {
                    result1.setText(calculateVoltagePercentDrop(Double.parseDouble(voltage_edit_text.getText().toString()), Double.parseDouble(result.getText().toString())));
                    result2.setText(calculateVoltageAtLoad(Double.parseDouble(voltage_edit_text.getText().toString()), Double.parseDouble(result.getText().toString())));
                } else {
                    result1.setText("");
                    result2.setText("");
                }

            }
        });

        length_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (length_edit_text.getText().length() > 0) {
                    mul_from_amps2.setText(length_edit_text.getText().toString());
                } else {
                    mul_from_amps2.setText("");
                }
                if (mul_from_amps1.getText().length() > 0 && !mul_from_amps1.getText().toString().equalsIgnoreCase("k") && mul_from_amps2.getText().length() > 0 && mul_from_amps3.getText().length() > 0 && div_to_amps.getText().length() > 0) {
                    result.setText(calculateVoltageDrop(Double.parseDouble(mul_from_amps1.getText().toString()), Double.parseDouble(mul_from_amps2.getText().toString()), Double.parseDouble(mul_from_amps3.getText().toString()), Double.parseDouble(div_to_amps.getText().toString()), circuit_type_spinner.getSelectedItemPosition()));
                } else {
                    result.setText("");
                }
                if (voltage_edit_text.getText().length() > 0 && result.getText().length() > 0) {
                    result1.setText(calculateVoltagePercentDrop(Double.parseDouble(voltage_edit_text.getText().toString()), Double.parseDouble(result.getText().toString())));
                    result2.setText(calculateVoltageAtLoad(Double.parseDouble(voltage_edit_text.getText().toString()), Double.parseDouble(result.getText().toString())));
                } else {
                    result1.setText("");
                    result2.setText("");
                }
            }
        });
        circuit_types = new ArrayList<>();
        circuit_types.add(0, getString(R.string.voltage_drop_calculator_type_default));
        circuit_types.add(1, getString(R.string.voltage_drop_calculator_type1));
        circuit_types.add(2, getString(R.string.voltage_drop_calculator_type2));
        circuit_type_spinner.setSelection(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, circuit_types);
        circuit_type_spinner.setAdapter(adapter);

        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().toLowerCase().contains("Voltage Drop Calculations: Inductance Negligible".toLowerCase())) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(VoltageDropCalculatorActivity.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, getString(R.string.title_activity_voltage_drop_calculator));
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
    }

    private String calculateVoltagePercentDrop(double voltage, double voltageDrop) {
        double result = 0.0;
        result = (voltageDrop / voltage) * 100;
        return TextUtil.convertToExponential(result);
    }

    private String calculateVoltageAtLoad(double voltage, double voltageDrop) {
        double result = 0.0;
        result = voltage - voltageDrop;
        return TextUtil.convertToExponential(result);
    }

    private String calculateVoltageDrop(double k, double l, double i, double cm, int circuitType) {

        double result = 0.0;
        switch (circuitType) {
            case 1:
                result = (2 * k * l * i) / (cm);
                break;
            case 2:
                result = (1.73 * k * l * i) / (cm);
                break;
        }

        return TextUtil.convertToExponential(result);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner selectedSpinner = (Spinner) parent;
        switch (selectedSpinner.getId()) {
            case R.id.circuit_type_spinner:
                Object tag = circuit_type_spinner.getTag(R.id.spinner_pos);
                if (tag != null) {
                    tag = tag;
                }
                if (position > 0 && tag != position) {
                    circuit_type_spinner.setTag(R.id.spinner_pos, position);
                    conductor_type_spinner.setVisibility(View.VISIBLE);
                    wire_size_spinner.setVisibility(View.VISIBLE);
                    table_layout.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                    result_header.setVisibility(View.VISIBLE);
                    mul_from_amps1.setText("K");
                    mul_from_amps1_symbol.setVisibility(View.GONE);
                    mul_from_amps2.setText("");
                    mul_from_amps3.setText("");
                    div_to_amps.setText("");
                    result.setText("");
                    current_edit_text.setText("");
                    voltage_edit_text.setText("");
                    length_edit_text.setText("");

                    ArrayList<String> wireSize = new ArrayList<>();
                    for (int i = 0; i < areaArrayList.size(); i++) {
                        wireSize.add(areaArrayList.get(i).getSize());
                    }
                    wireSize.add(0, getString(R.string.voltage_drop_wire_size_default));
                    wire_size_spinner.setSelection(0);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, wireSize);
                    wire_size_spinner.setAdapter(adapter);

                    conductor_types = new ArrayList<>();
                    conductor_types.add(0, getString(R.string.voltage_drop_wire_conductor_type_default));
                    conductor_types.add(1, getString(R.string.voltage_drop_wire_conductor_type1));
                    conductor_types.add(2, getString(R.string.voltage_drop_wire_conductor_type2));
                    conductor_type_spinner.setSelection(0);
                    adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, conductor_types);
                    conductor_type_spinner.setAdapter(adapter);

                    switch (position) {
                        case 1:
                            mul_from_amps1_value.setText("2");
                            break;
                        case 2:
                            mul_from_amps1_value.setText("1.73");
                            break;
                    }
                    bookmarkObj.setBookmarkCalType(position - 1);
                    bookmarkObj.setBookmarkName(getString(R.string.title_activity_voltage_drop_calculator) + " - " + circuit_types.get(position));
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                } else if (position == 0) {
                    conductor_type_spinner.setVisibility(View.GONE);
                    wire_size_spinner.setVisibility(View.GONE);
                    table_layout.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    bookmarkObj.setBookmarkCalType(-1);
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                    circuit_type_spinner.setTag(R.id.spinner_pos, null);
                    mul_from_amps1.setText("K");
                    mul_from_amps1_symbol.setVisibility(View.GONE);
                    mul_from_amps2.setText("");
                    mul_from_amps3.setText("");
                    div_to_amps.setText("");
                    result.setText("");
                    current_edit_text.setText("");
                    voltage_edit_text.setText("");
                    length_edit_text.setText("");
                }
                break;
            case R.id.conductor_type_spinner:
                if (position > 0) {
                    switch (position) {
                        case 1:
                            mul_from_amps1.setText("12.9");
                            break;
                        case 2:
                            mul_from_amps1.setText("21.2");
                            break;
                    }
                    mul_from_amps1_symbol.setVisibility(View.VISIBLE);

                    if (mul_from_amps1.getText().length() > 0 && !mul_from_amps1.getText().toString().equalsIgnoreCase("k") && mul_from_amps2.getText().length() > 0 && mul_from_amps3.getText().length() > 0 && div_to_amps.getText().length() > 0) {
                        result.setText(calculateVoltageDrop(Double.parseDouble(mul_from_amps1.getText().toString()), Double.parseDouble(mul_from_amps2.getText().toString()), Double.parseDouble(mul_from_amps3.getText().toString()), Double.parseDouble(div_to_amps.getText().toString()), circuit_type_spinner.getSelectedItemPosition()));
                    } else {
                        result.setText("");
                    }
                    if (voltage_edit_text.getText().length() > 0 && result.getText().length() > 0) {
                        result1.setText(calculateVoltagePercentDrop(Double.parseDouble(voltage_edit_text.getText().toString()), Double.parseDouble(result.getText().toString())));
                        result2.setText(calculateVoltageAtLoad(Double.parseDouble(voltage_edit_text.getText().toString()), Double.parseDouble(result.getText().toString())));
                    } else {
                        result1.setText("");
                        result2.setText("");
                    }
                } else {
                    mul_from_amps1.setText("K");
                    mul_from_amps1_symbol.setVisibility(View.GONE);
                }

                break;
            case R.id.wire_size_spinner:
                if (position > 0) {
                    div_to_amps.setText(Long.toString(areaArrayList.get(position - 1).getArea()));
                    if (mul_from_amps1.getText().length() > 0 && !mul_from_amps1.getText().toString().equalsIgnoreCase("k") && mul_from_amps2.getText().length() > 0 && mul_from_amps3.getText().length() > 0 && div_to_amps.getText().length() > 0) {
                        result.setText(calculateVoltageDrop(Double.parseDouble(mul_from_amps1.getText().toString()), Double.parseDouble(mul_from_amps2.getText().toString()), Double.parseDouble(mul_from_amps3.getText().toString()), Double.parseDouble(div_to_amps.getText().toString()), circuit_type_spinner.getSelectedItemPosition()));
                    } else {
                        result.setText("");
                    }
                    if (voltage_edit_text.getText().length() > 0 && result.getText().length() > 0) {
                        result1.setText(calculateVoltagePercentDrop(Double.parseDouble(voltage_edit_text.getText().toString()), Double.parseDouble(result.getText().toString())));
                        result2.setText(calculateVoltageAtLoad(Double.parseDouble(voltage_edit_text.getText().toString()), Double.parseDouble(result.getText().toString())));
                    } else {
                        result1.setText("");
                        result2.setText("");
                    }
                } else {
                    div_to_amps.setText("");
                }

                break;
        }
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
            if (bookmarkObj.getBookmarkCalType() != -1) {
                application.sd.toggleBookmark(bookmarkObj);
                if (application.sd.isBookmarked(bookmarkObj)) {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                } else {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                }
            } else {
                showToast(getString(R.string.voltage_drop_validation), 1000);
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
    public void onNothingSelected(AdapterView<?> parent) {

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
}
