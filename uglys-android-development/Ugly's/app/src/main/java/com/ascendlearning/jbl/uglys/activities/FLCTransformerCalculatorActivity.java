package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.FLCTransformer;
import com.ascendlearning.jbl.uglys.models.FLCTransformerAmperes;
import com.ascendlearning.jbl.uglys.models.FLCTransformerKVA;
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

public class FLCTransformerCalculatorActivity extends SuperActivity implements UICallback, Spinner.OnItemSelectedListener {
    private UglysController mController;
    private UglysResponse mResponse;
    private ArrayList<FLCTransformer> flcArrayList;
    private ArrayList<FLCTransformerKVA> flcArrayKVAList;
    private ArrayList<FLCTransformerAmperes> flcArrayvoltageList;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private Spinner flc_spinner;
    private Spinner kva_spinner;
    private Spinner voltage_spinner;
    private TextView result_header;
    private LinearLayout result_view;
    private TextView result;
    private RelativeLayout relatedContent_view;
    private int bookmarkCalType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flctransformer_calculator);

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
        flcArrayList = mResponse.getFlcTransformerArrayList();
        createBookmarkData();
        makeLayout();
        bookmarkCalType = getIntent().getIntExtra("flcType", -1);
        if (bookmarkCalType != -1) {
            flc_spinner.setTag(R.id.spinner_pos, bookmarkCalType + 1);
            flc_spinner.setSelection(bookmarkCalType + 1);
            flcArrayKVAList = flcArrayList.get(bookmarkCalType).getKvaList();
            final ArrayList<String> flcKVAList = new ArrayList<>();
            for (int i = 0; i < flcArrayKVAList.size(); i++) {
                flcKVAList.add(flcArrayKVAList.get(i).getKvaValue() + "");
            }
            flcKVAList.add(0, getString(R.string.spinner_kva_default));
            kva_spinner.setSelection(0);
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, flcKVAList);
            kva_spinner.setAdapter(adapter);
            kva_spinner.setVisibility(View.VISIBLE);
            voltage_spinner.setVisibility(View.GONE);
            result_header.setVisibility(View.GONE);
            result_view.setVisibility(View.GONE);
            relatedContent_view.setVisibility(View.VISIBLE);
            bookmarkObj.setBookmarkCalType(bookmarkCalType);
            bookmarkObj.setBookmarkName("FLC-Transformer Calculator - " + flcArrayList.get(bookmarkCalType).getFlcName());

        }

    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.FLC_TRANSFORMER.ordinal());
    }

    private void makeLayout() {
        flc_spinner = (Spinner) findViewById(R.id.flc_spinner);
        kva_spinner = (Spinner) findViewById(R.id.kva_spinner);
        voltage_spinner = (Spinner) findViewById(R.id.voltage_spinner);
        flc_spinner.setOnItemSelectedListener(this);
        kva_spinner.setOnItemSelectedListener(this);
        voltage_spinner.setOnItemSelectedListener(this);
        result_header = (TextView) findViewById(R.id.result_header);
        result_view = (LinearLayout) findViewById(R.id.result_view);
        result = (TextView) findViewById(R.id.result);
        relatedContent_view = (RelativeLayout) findViewById(R.id.relatedContent_view);

        final ArrayList<String> flcNameList = new ArrayList<>();
        for (int i = 0; i < flcArrayList.size(); i++) {
            flcNameList.add(flcArrayList.get(i).getFlcName());
        }
        flcNameList.add(0, getString(R.string.spinner_flc_default));
        flc_spinner.setSelection(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, flcNameList);
        flc_spinner.setAdapter(adapter);

        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().equalsIgnoreCase("Full-Load Currents")) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(FLCTransformerCalculatorActivity.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, "Full-Load Currents");
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
                    flcArrayKVAList = flcArrayList.get(position - 1).getKvaList();
                    final ArrayList<String> flcKVAList = new ArrayList<>();
                    for (int i = 0; i < flcArrayKVAList.size(); i++) {
                        flcKVAList.add(flcArrayKVAList.get(i).getKvaValue() + "");
                    }
                    flcKVAList.add(0, getString(R.string.spinner_kva_default));
                    kva_spinner.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, flcKVAList);
                    kva_spinner.setAdapter(adapter);
                    kva_spinner.setVisibility(View.VISIBLE);
                    voltage_spinner.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    relatedContent_view.setVisibility(View.VISIBLE);
                    bookmarkObj.setBookmarkCalType(position - 1);
                    bookmarkObj.setBookmarkName("FLC-Transformer Calculator - " + flcArrayList.get(position - 1).getFlcName());
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                } else if (position == 0) {
                    flc_spinner.setTag(R.id.spinner_pos, position);
                    kva_spinner.setVisibility(View.GONE);
                    voltage_spinner.setVisibility(View.GONE);
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
            case R.id.kva_spinner:
                if (position > 0) {
                    flcArrayvoltageList = flcArrayKVAList.get(position - 1).getAmperesList();
                    final ArrayList<String> flcVoltageList = new ArrayList<>();
                    for (int i = 0; i < flcArrayvoltageList.size(); i++) {
                        flcVoltageList.add(flcArrayvoltageList.get(i).getVoltage() + "");
                    }
                    flcVoltageList.add(0, getString(R.string.spinner_flc__voltage_default));
                    voltage_spinner.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, flcVoltageList);
                    voltage_spinner.setAdapter(adapter);
                    voltage_spinner.setVisibility(View.VISIBLE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                } else {
                    voltage_spinner.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
            case R.id.voltage_spinner:
                if (position > 0) {
                    result.setText(flcArrayvoltageList.get(position - 1).getAmpsValue() + "");
                    result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
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
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_FLC_TRANSFORMER_CALCULATOR);
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
