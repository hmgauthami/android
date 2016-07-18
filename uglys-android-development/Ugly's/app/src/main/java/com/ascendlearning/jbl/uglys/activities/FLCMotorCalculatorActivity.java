package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.FLCMotor;
import com.ascendlearning.jbl.uglys.models.FLCMotorHP;
import com.ascendlearning.jbl.uglys.models.FLCMotorTypes;
import com.ascendlearning.jbl.uglys.models.FLCMotorVoltageRating;
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

public class FLCMotorCalculatorActivity extends SuperActivity implements UICallback, Spinner.OnItemSelectedListener {
    private UglysController mController;
    private UglysResponse mResponse;
    private ArrayList<FLCMotor> flcArrayList;
    private ArrayList<FLCMotorTypes> flcTypesArrayList;
    private ArrayList<FLCMotorHP> flcHPArraList;
    private ArrayList<FLCMotorVoltageRating> flcvoltageArraList;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private Spinner flc_spinner;
    private Spinner flc_spinner1;
    private Spinner flc_spinner2;
    private Spinner flc_spinner3;
    private TextView result_header;
    private LinearLayout result_view;
    private TextView result;
    private RelativeLayout relatedContent_view;
    private int bookmarkCalType;
    private ImageView info_imageview;
    private String infoText;

    private enum FLC {
        DC, SP, TP
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flcmotor_calculator);
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
        flcArrayList = mResponse.getFlcMotorArrayList();
        createBookmarkData();
        makeLayout();
        bookmarkCalType = getIntent().getIntExtra("flcType", -1);
        if (bookmarkCalType != -1) {
            flc_spinner.setTag(R.id.spinner_pos, bookmarkCalType + 1);
            flc_spinner.setSelection(bookmarkCalType + 1);
            flcHPArraList = null;
            flcvoltageArraList = null;
            if (bookmarkCalType == FLC.TP.ordinal()) {
                flcTypesArrayList = flcArrayList.get(bookmarkCalType).getFlcMotorTypesArrayList();
                final ArrayList<String> flcTypesList = new ArrayList<>();
                for (int i = 0; i < flcTypesArrayList.size(); i++) {
                    flcTypesList.add(flcTypesArrayList.get(i).getFlcTypeName());
                }
                flcTypesList.add(0, getString(R.string.spinner_flc_type_default));
                flc_spinner1.setSelection(0);
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, flcTypesList);
                flc_spinner1.setAdapter(adapter);
            } else {
                flcHPArraList = flcArrayList.get(bookmarkCalType).getHpArrayList();
                final ArrayList<String> flcHPList = new ArrayList<>();
                for (int i = 0; i < flcHPArraList.size(); i++) {
                    flcHPList.add(flcHPArraList.get(i).getHpName());
                }
                flcHPList.add(0, getString(R.string.spinner_flc__HP_default));
                flc_spinner1.setSelection(0);
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, flcHPList);
                flc_spinner1.setAdapter(adapter);
            }
            bookmarkObj.setBookmarkName("FLC Motor Calculator - " + flcArrayList.get(bookmarkCalType).getFlcName());
            bookmarkObj.setBookmarkCalType(bookmarkCalType);
            flc_spinner1.setVisibility(View.VISIBLE);
            flc_spinner2.setVisibility(View.GONE);
            flc_spinner3.setVisibility(View.GONE);
            result_header.setVisibility(View.GONE);
            result_view.setVisibility(View.GONE);
            relatedContent_view.setVisibility(View.VISIBLE);
        }

    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.FLC_MOTOR.ordinal());
    }

    private void makeLayout() {
        flc_spinner = (Spinner) findViewById(R.id.flc_spinner);
        flc_spinner1 = (Spinner) findViewById(R.id.flc_spinner1);
        flc_spinner2 = (Spinner) findViewById(R.id.flc_spinner2);
        flc_spinner3 = (Spinner) findViewById(R.id.flc_spinner3);
        flc_spinner.setOnItemSelectedListener(this);
        flc_spinner1.setOnItemSelectedListener(this);
        flc_spinner2.setOnItemSelectedListener(this);
        flc_spinner3.setOnItemSelectedListener(this);
        result_header = (TextView) findViewById(R.id.result_header);
        result_view = (LinearLayout) findViewById(R.id.result_view);
        result = (TextView) findViewById(R.id.result);
        relatedContent_view = (RelativeLayout) findViewById(R.id.relatedContent_view);
        info_imageview = (ImageView) findViewById(R.id.info_imageview);

        final ArrayList<String> flcNameList = new ArrayList<>();
        for (int i = 0; i < flcArrayList.size(); i++) {
            flcNameList.add(flcArrayList.get(i).getFlcName());
        }
        flcNameList.add(0, getString(R.string.spinner_flc_default));
        flc_spinner.setSelection(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.conversion_spinner_dropdown, flcNameList) {
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
        flc_spinner.setAdapter(adapter);

        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().equalsIgnoreCase(flcNameList.get(flc_spinner.getSelectedItemPosition()))) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(FLCMotorCalculatorActivity.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, flcNameList.get(flc_spinner.getSelectedItemPosition()));
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
            case R.id.flc_spinner:
                Object tag = flc_spinner.getTag(R.id.spinner_pos);
                if (tag != null) {
                    tag = tag;
                }
                if (position > 0 && tag != position) {
                    flc_spinner.setTag(R.id.spinner_pos, position);
                    flcHPArraList = null;
                    flcvoltageArraList = null;
                    if (position == FLC.TP.ordinal() + 1) {
                        flcTypesArrayList = flcArrayList.get(position - 1).getFlcMotorTypesArrayList();
                        final ArrayList<String> flcTypesList = new ArrayList<>();
                        for (int i = 0; i < flcTypesArrayList.size(); i++) {
                            flcTypesList.add(flcTypesArrayList.get(i).getFlcTypeName());
                        }
                        flcTypesList.add(0, getString(R.string.spinner_flc_type_default));
                        flc_spinner1.setSelection(0);
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.conversion_spinner_dropdown, flcTypesList) {
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
                        flc_spinner1.setAdapter(adapter);

                    } else {
                        flcHPArraList = flcArrayList.get(position - 1).getHpArrayList();
                        final ArrayList<String> flcHPList = new ArrayList<>();
                        for (int i = 0; i < flcHPArraList.size(); i++) {
                            flcHPList.add(flcHPArraList.get(i).getHpName());
                        }
                        flcHPList.add(0, getString(R.string.spinner_flc__HP_default));
                        flc_spinner1.setSelection(0);
                        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, flcHPList);
                        flc_spinner1.setAdapter(adapter);
                    }
                    FLC pos = FLC.values()[position - 1];
                    switch (pos) {
                        case DC:
                            infoText = getString(R.string.flc_info1);
                            break;
                        case SP:
                            infoText = getString(R.string.flc_info2);
                            break;
                        case TP:
                            infoText = getString(R.string.flc_info3);
                            break;
                    }
                    info_imageview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CalculatorInfoDialog dialog = new CalculatorInfoDialog(FLCMotorCalculatorActivity.this, android.R.style.Theme_Holo_Dialog, infoText);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.setCanceledOnTouchOutside(true);
                            dialog.show();
                        }
                    });
                    bookmarkObj.setBookmarkCalType(position - 1);
                    bookmarkObj.setBookmarkName("FLC Motor Calculator - " + flcArrayList.get(position - 1).getFlcName());
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                    flc_spinner1.setVisibility(View.VISIBLE);
                    flc_spinner2.setVisibility(View.GONE);
                    flc_spinner3.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    relatedContent_view.setVisibility(View.VISIBLE);
                } else if (position == 0) {
                    flc_spinner.setTag(R.id.spinner_pos, position);
                    flc_spinner1.setVisibility(View.GONE);
                    flc_spinner2.setVisibility(View.GONE);
                    flc_spinner3.setVisibility(View.GONE);
                    flcHPArraList = null;
                    flcvoltageArraList = null;
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    relatedContent_view.setVisibility(View.GONE);
                    bookmarkObj.setBookmarkCalType(-1);
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                }
                break;

            case R.id.flc_spinner1:
                if (position > 0) {
                    if (flcTypesArrayList != null) {
                        flcHPArraList = flcTypesArrayList.get(position - 1).getHpArrayList();
                        final ArrayList<String> flcTypesList = new ArrayList<>();
                        for (int i = 0; i < flcHPArraList.size(); i++) {
                            flcTypesList.add(flcHPArraList.get(i).getHpName());
                        }
                        flcTypesList.add(0, getString(R.string.spinner_flc_type_default));
                        flc_spinner2.setSelection(0);
                        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, flcTypesList);
                        flc_spinner2.setAdapter(adapter);
                    } else {
                        flcvoltageArraList = flcHPArraList.get(position - 1).getVoltageRatingArrayList();
                        final ArrayList<String> flcVoltageList = new ArrayList<>();
                        for (int i = 0; i < flcvoltageArraList.size(); i++) {
                            flcVoltageList.add(flcvoltageArraList.get(i).getName());
                        }
                        flcVoltageList.add(0, getString(R.string.spinner_flc__voltage_default));
                        flc_spinner2.setSelection(0);
                        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, flcVoltageList);
                        flc_spinner2.setAdapter(adapter);
                    }
                    flc_spinner2.setVisibility(View.VISIBLE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                } else {
                    flcvoltageArraList = null;
                    flc_spinner3.setVisibility(View.GONE);
                    flc_spinner2.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
            case R.id.flc_spinner2:
                if (position > 0) {
                    if (flcTypesArrayList != null) {
                        flcvoltageArraList = flcHPArraList.get(position - 1).getVoltageRatingArrayList();
                        final ArrayList<String> flcVoltageList = new ArrayList<>();
                        for (int i = 0; i < flcvoltageArraList.size(); i++) {
                            flcVoltageList.add(flcvoltageArraList.get(i).getName());
                        }
                        flcVoltageList.add(0, getString(R.string.spinner_flc__voltage_default));
                        flc_spinner3.setSelection(0);
                        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, flcVoltageList);
                        flc_spinner3.setAdapter(adapter);
                        flc_spinner3.setVisibility(View.VISIBLE);
                        result_header.setVisibility(View.GONE);
                        result_view.setVisibility(View.GONE);
                    } else {
                        result_header.setVisibility(View.VISIBLE);
                        result_view.setVisibility(View.VISIBLE);
                        result.setText(flcvoltageArraList.get(position - 1).getValue() + "");
                    }
                } else {
                    flc_spinner3.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
            case R.id.flc_spinner3:
                if (position > 0) {
                    result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                    result.setText(flcvoltageArraList.get(position - 1).getValue() + "");
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
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_FLC_MOTOR_CALCULATOR);
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
            if (bookmarkObj.getBookmarkCalType() != -1) {
                application.sd.toggleBookmark(bookmarkObj);
                if (application.sd.isBookmarked(bookmarkObj)) {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                } else {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                }
            } else {
                showToast(getString(R.string.flc_motor_validation), 1000);
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
