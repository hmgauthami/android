package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Ampacity;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.LRC;
import com.ascendlearning.jbl.uglys.models.LRCHP;
import com.ascendlearning.jbl.uglys.models.LRCVoltage;
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

public class LRCCalculator extends SuperActivity implements Spinner.OnItemSelectedListener, UICallback {

    private Spinner current_type_spinner;
    private Spinner hp_spinner;
    private Spinner voltage_spinner;
    private LinearLayout result_view;
    private TextView result_header;
    private TextView result;
    private UglysController mController;
    private UglysResponse mResponse;
    private ArrayList<LRC> lrcArrayList;
    private ArrayList<LRCHP> hpArrayList;
    private ArrayList<LRCVoltage> voltageArrayList;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private ArrayList<String> motor_types;
    private ImageView info_imageview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrccalculator);
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
        lrcArrayList = mResponse.getLrcArrayList();

        int motorType = getIntent().getIntExtra("motorType", -1);

        createBookmarkData();
        makeLayout();

        if (motorType != -1) {
            current_type_spinner.setTag(R.id.spinner_pos, motorType + 1);
            current_type_spinner.setSelection(motorType + 1);
            hp_spinner.setVisibility(View.VISIBLE);
            result_view.setVisibility(View.VISIBLE);
            result_header.setVisibility(View.VISIBLE);

            hpArrayList = lrcArrayList.get(motorType).getHpArrayList();
            ArrayList<String> hp = new ArrayList<>();
            for (int i = 0; i < hpArrayList.size(); i++) {
                hp.add(hpArrayList.get(i).getHp());
            }
            hp.add(0, getString(R.string.lrc_calculator_hp_default));
            hp_spinner.setSelection(0);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, hp);
            hp_spinner.setAdapter(adapter);
            bookmarkObj.setBookmarkCalType(motorType);

        }
    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.LRC.ordinal());
    }

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_LRC_CALCULATOR);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);
    }

    public void makeLayout() {

        current_type_spinner = (Spinner) findViewById(R.id.current_type_spinner);
        hp_spinner = (Spinner) findViewById(R.id.hp_spinner);
        voltage_spinner = (Spinner) findViewById(R.id.voltage_spinner);
        result_view = (LinearLayout) findViewById(R.id.result_view);
        result_header = (TextView) findViewById(R.id.result_header);
        result = (TextView) findViewById(R.id.result);
        info_imageview = (ImageView) findViewById(R.id.info_imageview);

        info_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_type_spinner.getSelectedItemPosition() == 2) {
                    CalculatorInfoDialog dialog = new CalculatorInfoDialog(LRCCalculator.this, android.R.style.Theme_Holo_Dialog, getString(R.string.lrc_info));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();
                }
            }
        });

        current_type_spinner.setOnItemSelectedListener(this);
        hp_spinner.setOnItemSelectedListener(this);
        voltage_spinner.setOnItemSelectedListener(this);

        motor_types = new ArrayList<>();
        for (int i = 0; i < lrcArrayList.size(); i++) {
            motor_types.add(lrcArrayList.get(i).getName());
        }
        motor_types.add(0, getString(R.string.lrc_calculator_type_default));
        current_type_spinner.setSelection(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, motor_types);
        current_type_spinner.setAdapter(adapter);

        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().toLowerCase().contains("Locked-Rotor Code Letters".toLowerCase())) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(LRCCalculator.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, getString(R.string.title_activity_lrc_calculator));
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner selectedSpinner = (Spinner) parent;
        switch (selectedSpinner.getId()) {
            case R.id.current_type_spinner:
                Object tag = current_type_spinner.getTag(R.id.spinner_pos);
                if (tag != null) {
                    tag = tag;
                }
                if (position > 0 && tag != position) {
                    current_type_spinner.setTag(R.id.spinner_pos, position);
                    hp_spinner.setVisibility(View.VISIBLE);

                    hpArrayList = lrcArrayList.get(position - 1).getHpArrayList();
                    ArrayList<String> hp = new ArrayList<>();
                    for (int i = 0; i < hpArrayList.size(); i++) {
                        hp.add(hpArrayList.get(i).getHp());
                    }
                    hp.add(0, getString(R.string.lrc_calculator_hp_default));
                    hp_spinner.setSelection(0);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, hp);
                    hp_spinner.setAdapter(adapter);


                    bookmarkObj.setBookmarkCalType(position - 1);
                    bookmarkObj.setBookmarkName(getString(R.string.title_activity_lrc_calculator) + " - " + motor_types.get(position));
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }

                    switch (position){
                        case 1:
                            info_imageview.setVisibility(View.GONE);
                            break;
                        case 2:
                            info_imageview.setVisibility(View.VISIBLE);
                            break;
                    }
                } else if (position == 0) {
                    hp_spinner.setVisibility(View.GONE);
                    voltage_spinner.setVisibility(View.GONE);
                    bookmarkObj.setBookmarkCalType(-1);
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                    current_type_spinner.setTag(R.id.spinner_pos, null);
                }
                result_view.setVisibility(View.GONE);
                result_header.setVisibility(View.GONE);
                break;
            case R.id.hp_spinner:
                if (position > 0) {
                    voltage_spinner.setVisibility(View.VISIBLE);
                    voltageArrayList = hpArrayList.get(position - 1).getVoltageArrayList();
                    ArrayList<String> voltage = new ArrayList<>();
                    for (int i = 0; i < voltageArrayList.size(); i++) {
                        voltage.add(voltageArrayList.get(i).getVoltage());
                    }
                    voltage.add(0, getString(R.string.lrc_calculator_voltage_default));
                    voltage_spinner.setSelection(0);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, voltage);
                    voltage_spinner.setAdapter(adapter);
                } else {
                    voltage_spinner.setVisibility(View.GONE);
                }
                result_view.setVisibility(View.GONE);
                result_header.setVisibility(View.GONE);
                break;
            case R.id.voltage_spinner:
                if (position > 0) {
                    result.setText(voltageArrayList.get(position - 1).getCurrent());
                    result_view.setVisibility(View.VISIBLE);
                    result_header.setVisibility(View.VISIBLE);
                } else {
                    result.setText("");
                    result_view.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
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
                showToast(getString(R.string.lrc_validation), 1000);
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
