package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.graphics.Typeface;
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
import com.ascendlearning.jbl.uglys.models.Ampacity;
import com.ascendlearning.jbl.uglys.models.AmpacityConductorType;
import com.ascendlearning.jbl.uglys.models.AmpacityWireInsulation;
import com.ascendlearning.jbl.uglys.models.AmpacityWireLocation;
import com.ascendlearning.jbl.uglys.models.AmpacityWireSize;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
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

public class SimpleAmpacityCalculator extends SuperActivity implements UICallback, Spinner.OnItemSelectedListener {
    private UglysController mController;
    private UglysResponse mResponse;
    private ArrayList<Ampacity> ampacityArrayList;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private String infoText;
    private Spinner ambient_Temp_spinner;
    private Spinner ampacity_wire_loc_spinner;
    private Spinner ampacity_conductor_type_spinner;
    private Spinner ampacity_wire_insulation_spinner;
    private Spinner ampacity_wire_size_spinner;
    private TextView result;
    private TextView ampacity_result_header;
    private LinearLayout result_view;
    private ArrayList<AmpacityWireSize> wireSizeArrayList;
    private ArrayList<AmpacityWireInsulation> wireInsulationArrayList;
    private ArrayList<AmpacityConductorType> conductorTypeArrayList;
    private ArrayList<AmpacityWireLocation> wireLocArrayList;
    private int ampacityTemp;
    private int ampacityLoc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ampacity_calculator);

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
        ampacityArrayList = mResponse.getAmpacityList();
        ampacityTemp = getIntent().getIntExtra("AmpacityTemp", -1);
        ampacityLoc = getIntent().getIntExtra("AmpacityLoc", -1);
        createBookmarkData();
        makeLayout();
        if (ampacityTemp != -1) {
            ambient_Temp_spinner.setSelection(ampacityTemp + 1);
            ambient_Temp_spinner.setTag(R.id.spinner_pos, ampacityTemp + 1);
            wireLocArrayList = ampacityArrayList.get(ampacityTemp).getAmpacityWireLocationArrayList();
            final ArrayList<String> wireLocNameList = new ArrayList<>();
            for (int i = 0; i < wireLocArrayList.size(); i++) {
                wireLocNameList.add(wireLocArrayList.get(i).getAmpLocation());
            }
            ampacity_wire_loc_spinner.setVisibility(View.VISIBLE);
            ampacity_conductor_type_spinner.setVisibility(View.GONE);
            ampacity_wire_insulation_spinner.setVisibility(View.GONE);
            ampacity_wire_size_spinner.setVisibility(View.GONE);
            wireLocNameList.add(0, getString(R.string.spinner_default));

            ArrayAdapter<String> adapter = new ArrayAdapter<>(SimpleAmpacityCalculator.this, R.layout.conversion_spinner_dropdown, wireLocNameList);
            ampacity_wire_loc_spinner.setAdapter(adapter);
            ampacity_wire_loc_spinner.setSelection(ampacityLoc + 1);
            ambient_Temp_spinner.setTag(R.id.val_spinner_pos, ampacityLoc + 1);

            conductorTypeArrayList = wireLocArrayList.get(ampacityLoc).getConductorTypeArrayList();
            final ArrayList<String> conductorTypeNameList = new ArrayList<>();
            for (int i = 0; i < conductorTypeArrayList.size(); i++) {
                conductorTypeNameList.add(conductorTypeArrayList.get(i).getConductorTypeName());
            }
            ampacity_conductor_type_spinner.setVisibility(View.VISIBLE);
            ampacity_wire_insulation_spinner.setVisibility(View.GONE);
            ampacity_wire_size_spinner.setVisibility(View.GONE);
            ampacity_result_header.setVisibility(View.GONE);
            result_view.setVisibility(View.GONE);
            conductorTypeNameList.add(0, getString(R.string.spinner_ampacity_conductor_type_default));
            ampacity_conductor_type_spinner.setSelection(0);
            adapter = new ArrayAdapter<>(SimpleAmpacityCalculator.this, R.layout.conversion_spinner_dropdown, conductorTypeNameList);
            ampacity_conductor_type_spinner.setAdapter(adapter);
        }

    }


    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkName("Simple Ampacity Calculator");
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.AMPACITY.ordinal());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner selectedSpinner = (Spinner) parent;
        switch (selectedSpinner.getId()) {
            case R.id.ambient_Temp_spinner:
                Object tag = ambient_Temp_spinner.getTag(R.id.spinner_pos);
                if (tag != null) {
                    tag = tag;
                }
                if (position > 0 && tag != position) {
                    ambient_Temp_spinner.setTag(R.id.spinner_pos, position);
                    wireLocArrayList = ampacityArrayList.get(position - 1).getAmpacityWireLocationArrayList();
                    final ArrayList<String> wireLocNameList = new ArrayList<>();
                    for (int i = 0; i < wireLocArrayList.size(); i++) {
                        wireLocNameList.add(wireLocArrayList.get(i).getAmpLocation());
                    }
                    ampacity_wire_loc_spinner.setVisibility(View.VISIBLE);
                    ampacity_conductor_type_spinner.setVisibility(View.GONE);
                    ampacity_wire_insulation_spinner.setVisibility(View.GONE);
                    ampacity_wire_size_spinner.setVisibility(View.GONE);
                    ampacity_result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);

                    wireLocNameList.add(0, getString(R.string.spinner_ampacity_wire_location_default));
                    ampacity_wire_loc_spinner.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(SimpleAmpacityCalculator.this, R.layout.conversion_spinner_dropdown, wireLocNameList);
                    ampacity_wire_loc_spinner.setAdapter(adapter);
                } else if (position == 0) {
                    ampacity_wire_loc_spinner.setVisibility(View.GONE);
                    ampacity_conductor_type_spinner.setVisibility(View.GONE);
                    ampacity_wire_insulation_spinner.setVisibility(View.GONE);
                    ampacity_wire_size_spinner.setVisibility(View.GONE);
                    ampacity_result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    ambient_Temp_spinner.setTag(R.id.spinner_pos, null);
                }
                break;
            case R.id.ampacity_wire_loc_spinner:
                tag = ampacity_wire_loc_spinner.getTag(R.id.val_spinner_pos);
                if (tag != null) {
                    tag = tag;
                }
                if (position > 0 && tag != position) {
                    ampacity_wire_loc_spinner.setTag(R.id.val_spinner_pos, position);
                    conductorTypeArrayList = wireLocArrayList.get(position - 1).getConductorTypeArrayList();
                    final ArrayList<String> conductorTypeNameList = new ArrayList<>();
                    for (int i = 0; i < conductorTypeArrayList.size(); i++) {
                        conductorTypeNameList.add(conductorTypeArrayList.get(i).getConductorTypeName());
                    }
                    ampacity_conductor_type_spinner.setVisibility(View.VISIBLE);
                    ampacity_wire_insulation_spinner.setVisibility(View.GONE);
                    ampacity_wire_size_spinner.setVisibility(View.GONE);
                    ampacity_result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    conductorTypeNameList.add(0, getString(R.string.spinner_ampacity_conductor_type_default));
                    ampacity_conductor_type_spinner.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(SimpleAmpacityCalculator.this, R.layout.conversion_spinner_dropdown, conductorTypeNameList);
                    ampacity_conductor_type_spinner.setAdapter(adapter);
                } else if (position == 0) {
                    ampacity_conductor_type_spinner.setVisibility(View.GONE);
                    ampacity_wire_insulation_spinner.setVisibility(View.GONE);
                    ampacity_wire_size_spinner.setVisibility(View.GONE);
                    ampacity_result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    ambient_Temp_spinner.setTag(R.id.val_spinner_pos, null);
                }
                break;
            case R.id.ampacity_conductor_type_spinner:
                if (position > 0) {
                    wireInsulationArrayList = conductorTypeArrayList.get(position - 1).getAmpacityWireInsulationArrayList();
                    final ArrayList<String> wireInsulationNameList = new ArrayList<>();
                    for (int i = 0; i < wireInsulationArrayList.size(); i++) {
                        wireInsulationNameList.add(wireInsulationArrayList.get(i).getWireInsulationName());
                    }
                    ampacity_wire_insulation_spinner.setVisibility(View.VISIBLE);
                    ampacity_wire_size_spinner.setVisibility(View.GONE);
                    ampacity_result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    wireInsulationNameList.add(0, getString(R.string.spinner_ampacity_wire_insulation_default));
                    ampacity_wire_insulation_spinner.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(SimpleAmpacityCalculator.this, R.layout.conversion_spinner_dropdown, wireInsulationNameList);
                    ampacity_wire_insulation_spinner.setAdapter(adapter);

                } else {
                    ampacity_wire_insulation_spinner.setVisibility(View.GONE);
                    ampacity_wire_size_spinner.setVisibility(View.GONE);
                    ampacity_result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
            case R.id.ampacity_wire_insulation_spinner:
                if (position > 0) {
                    wireSizeArrayList = wireInsulationArrayList.get(position - 1).getAmpacityWireSizeArrayList();
                    final ArrayList<String> wireSizeList = new ArrayList<>();
                    for (int i = 0; i < wireSizeArrayList.size(); i++) {
                        wireSizeList.add(wireSizeArrayList.get(i).getWireSize());
                    }
                    ampacity_wire_size_spinner.setVisibility(View.VISIBLE);
                    ampacity_result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                    wireSizeList.add(0, getString(R.string.spinner_ampacity_wire_size_default));
                    ampacity_wire_size_spinner.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(SimpleAmpacityCalculator.this, R.layout.conversion_spinner_dropdown, wireSizeList);
                    ampacity_wire_size_spinner.setAdapter(adapter);
                } else {
                    ampacity_wire_size_spinner.setVisibility(View.GONE);
                    ampacity_result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
            case R.id.ampacity_wire_size_spinner:
                if (position > 0) {
                    ampacity_result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                    TextUtil.insertText(result, wireSizeArrayList.get(position - 1).getAmpacity(), Typeface.BOLD);

                } else {
                    ampacity_result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void makeLayout() {
        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().toLowerCase().contains("RACEWAY, CABLE".toLowerCase())) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(SimpleAmpacityCalculator.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, "Simple Ampacity Calculator");
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


        final ArrayList<String> ambientTempList = new ArrayList<>();
        for (int i = 0; i < ampacityArrayList.size(); i++) {
            ambientTempList.add(ampacityArrayList.get(i).getAmbientTemp());
        }
        ampacity_result_header = (TextView) findViewById(R.id.ampacity_result_header);
        result_view = (LinearLayout) findViewById(R.id.result_view);
        result = (TextView) findViewById(R.id.result);
        ambient_Temp_spinner = (Spinner) findViewById(R.id.ambient_Temp_spinner);
        ampacity_wire_loc_spinner = (Spinner) findViewById(R.id.ampacity_wire_loc_spinner);
        ampacity_conductor_type_spinner = (Spinner) findViewById(R.id.ampacity_conductor_type_spinner);
        ampacity_wire_insulation_spinner = (Spinner) findViewById(R.id.ampacity_wire_insulation_spinner);
        ampacity_wire_size_spinner = (Spinner) findViewById(R.id.ampacity_wire_size_spinner);
        ambient_Temp_spinner.setOnItemSelectedListener(this);
        ampacity_wire_loc_spinner.setOnItemSelectedListener(this);
        ampacity_conductor_type_spinner.setOnItemSelectedListener(this);
        ampacity_wire_insulation_spinner.setOnItemSelectedListener(this);
        ampacity_wire_size_spinner.setOnItemSelectedListener(this);

        ambientTempList.add(0, getString(R.string.spinner_ampacity_ambient_temp_default));
        ambient_Temp_spinner.setSelection(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, ambientTempList);
        ambient_Temp_spinner.setAdapter(adapter);

        ImageView info_imageview = (ImageView) findViewById(R.id.info_imageview);
        info_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (ambient_Temp_spinner.getSelectedItemPosition()) {
                    case 1:
                        switch (ampacity_wire_loc_spinner.getSelectedItemPosition()) {
                            case 1:
                                infoText = getString(R.string.amp_cal_info_text1);
                                break;
                            case 2:
                                infoText = getString(R.string.amp_cal_info_text2);
                                break;
                        }
                        break;
                    case 2:
                        switch (ampacity_wire_loc_spinner.getSelectedItemPosition()) {
                            case 1:
                                infoText = getString(R.string.amp_cal_info_text3);
                                break;
                            case 2:
                                infoText = getString(R.string.amp_cal_info_text4);
                                break;
                        }
                        break;

                }
                if (infoText != null) {
                    CalculatorInfoDialog dialog = new CalculatorInfoDialog(SimpleAmpacityCalculator.this, android.R.style.Theme_Holo_Dialog, infoText);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();
                }
            }
        });
    }

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_AMPACITY);
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

}
