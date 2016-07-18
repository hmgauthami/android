package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.FLCMotorHpData;
import com.ascendlearning.jbl.uglys.models.FLCMotorOtherData;
import com.ascendlearning.jbl.uglys.utils.CompositeKey;
import com.ascendlearning.jbl.uglys.utils.Constants;
import com.ascendlearning.jbl.uglys.utils.ESError;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import org.json.JSONException;
import org.readium.sdk.android.components.navigation.NavigationElement;
import org.readium.sdk.android.components.navigation.NavigationPoint;
import org.readium.sdk.android.launcher.model.OpenPageRequest;

import java.util.ArrayList;
import java.util.List;

public class FLCOtherDataCalculatorActivity extends SuperActivity implements UICallback, Spinner.OnItemSelectedListener {
    private UglysController mController;
    private UglysResponse mResponse;
    private ArrayList<FLCMotorOtherData> flcArrayList;
    private ArrayList<FLCMotorHpData> flcHPArrayList;
    private Spinner flc_spinner;
    private Spinner voltage_spinner;
    private TextView result_header;
    private LinearLayout result_view;
    private TextView result;
    private TextView result1;
    private TextView result2;
    private TextView result3;
    private TextView result4;
    private TextView result5;
    private ImageView info_imageview1;
    private ImageView info_imageview2;
    private ImageView info_imageview3;
    private Menu menu;
    private Bookmarks bookmarkObj;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flcother_data_calculator);

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
        flcArrayList = mResponse.getFlcMotorOtherDataArrayList();
        createBookmarkData();
        makeLayout();
    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.FLC_MOTOR_OTHER_DATA.ordinal());
        bookmarkObj.setBookmarkName("Three-phase AC motor data: Starter, Breaker, Heater, Wire, and Conduit size Calculator");
    }

    private void makeLayout() {
        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().toLowerCase().contains("Full-Load Current and Other Data: Three-Phase Ac Motors".toLowerCase())) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(FLCOtherDataCalculatorActivity.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, "Full-Load Current and Other Data: Three-Phase Ac Motors");
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
        flc_spinner = (Spinner) findViewById(R.id.flc_spinner);
        voltage_spinner = (Spinner) findViewById(R.id.voltage_spinner);
        flc_spinner.setOnItemSelectedListener(this);
        voltage_spinner.setOnItemSelectedListener(this);
        result_header = (TextView) findViewById(R.id.result_header);
        result_view = (LinearLayout) findViewById(R.id.result_view);
        result = (TextView) findViewById(R.id.result);
        result1 = (TextView) findViewById(R.id.result1);
        result2 = (TextView) findViewById(R.id.result2);
        result3 = (TextView) findViewById(R.id.result3);
        result4 = (TextView) findViewById(R.id.result4);
        result5 = (TextView) findViewById(R.id.result5);
        info_imageview1 = (ImageView) findViewById(R.id.info_imageview1);
        info_imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculatorInfoDialog dialog = new CalculatorInfoDialog(FLCOtherDataCalculatorActivity.this, android.R.style.Theme_Holo_Dialog, getString(R.string.info1));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
        info_imageview2 = (ImageView) findViewById(R.id.info_imageview2);
        info_imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculatorInfoDialog dialog = new CalculatorInfoDialog(FLCOtherDataCalculatorActivity.this, android.R.style.Theme_Holo_Dialog, getString(R.string.info2));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
        info_imageview3 = (ImageView) findViewById(R.id.info_imageview3);
        info_imageview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculatorInfoDialog dialog = new CalculatorInfoDialog(FLCOtherDataCalculatorActivity.this, android.R.style.Theme_Holo_Dialog, getString(R.string.info3));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });

        final ArrayList<String> flcNameList = new ArrayList<>();
        for (int i = 0; i < flcArrayList.size(); i++) {
            flcNameList.add(flcArrayList.get(i).getMotorpower());
        }
        flcNameList.add(0, getString(R.string.spinner_flc__HP_default));
        flc_spinner.setSelection(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, flcNameList);
        flc_spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner selectedSpinner = (Spinner) parent;
        switch (selectedSpinner.getId()) {
            case R.id.flc_spinner:
                if (position > 0) {
                    result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                    flcHPArrayList = flcArrayList.get(position - 1).getHpDataArrayList();
                    final ArrayList<String> flcVoltageList = new ArrayList<>();
                    for (int i = 0; i < flcHPArrayList.size(); i++) {
                        flcVoltageList.add(flcHPArrayList.get(i).getHp());
                    }
                    flcVoltageList.add(0, getString(R.string.spinner_flc__voltage_default));
                    voltage_spinner.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, flcVoltageList);
                    voltage_spinner.setAdapter(adapter);
                    voltage_spinner.setVisibility(View.VISIBLE);
                } else {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    voltage_spinner.setVisibility(View.GONE);
                }
                break;
            case R.id.voltage_spinner:
                if (position > 0) {
                    result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                    result.setText(flcHPArrayList.get(position - 1).getMotor_ampere());
                    result1.setText(flcHPArrayList.get(position - 1).getBreaker_size() + "");
                    result2.setText(flcHPArrayList.get(position - 1).getStarter_size());
                    result3.setText(flcHPArrayList.get(position - 1).getHeater_amps());
                    result4.setText(flcHPArrayList.get(position - 1).getWire_size());
                    result5.setText(flcHPArrayList.get(position - 1).getConduit_size());
                } else {
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_FLC_MOTOR_OTHER_DATA_CALCULATOR);
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
            application.sd.toggleBookmark(bookmarkObj);
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

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
