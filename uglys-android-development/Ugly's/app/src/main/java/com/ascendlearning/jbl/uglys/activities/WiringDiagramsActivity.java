package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.ascendlearning.jbl.uglys.models.WiringDiagramGrounding;
import com.ascendlearning.jbl.uglys.models.WiringDiagramPhases;
import com.ascendlearning.jbl.uglys.models.WiringDiagramVoltage;
import com.ascendlearning.jbl.uglys.models.WiringDiagramWires;
import com.ascendlearning.jbl.uglys.models.WiringDiagrams;
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

public class WiringDiagramsActivity extends SuperActivity implements UICallback, Spinner.OnItemSelectedListener {
    private UglysController mController;
    private UglysResponse mResponse;
    private ArrayList<WiringDiagrams> wiringDiagramsArrayList;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private Spinner poles_spinner;
    private Spinner wires_spinner;
    private Spinner grounding_type_spinner;
    private Spinner voltage_spinner;
    private Spinner phase_spinner;
    private ImageView result;
    private TextView result_header;
    private LinearLayout result_view;
    private ArrayList<WiringDiagramWires> wiresArrayList;
    private ArrayList<WiringDiagramGrounding> groundingTypeArrayList;
    private ArrayList<WiringDiagramVoltage> voltageArrayList;
    private ArrayList<WiringDiagramPhases> phasesArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiring_diagrams);

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
        wiringDiagramsArrayList = mResponse.getWiringArrayList();
        createBookmarkData();
        makeLayout();
    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkName(getString(R.string.title_wiring_diagram_interactive));
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.WIRING_DIAGRAMS.ordinal());
    }

    private void makeLayout() {
        result_header = (TextView) findViewById(R.id.result_header);
        result_view = (LinearLayout) findViewById(R.id.result_view);
        result = (ImageView) findViewById(R.id.result);
        poles_spinner = (Spinner) findViewById(R.id.poles_spinner);
        wires_spinner = (Spinner) findViewById(R.id.wires_spinner);
        grounding_type_spinner = (Spinner) findViewById(R.id.grounding_type_spinner);
        voltage_spinner = (Spinner) findViewById(R.id.voltage_spinner);
        phase_spinner = (Spinner) findViewById(R.id.phase_spinner);
        poles_spinner.setOnItemSelectedListener(this);
        wires_spinner.setOnItemSelectedListener(this);
        grounding_type_spinner.setOnItemSelectedListener(this);
        voltage_spinner.setOnItemSelectedListener(this);
        phase_spinner.setOnItemSelectedListener(this);

        ArrayList<String> poles = new ArrayList<>();
        for (int i = 0; i < wiringDiagramsArrayList.size(); i++) {
            poles.add(wiringDiagramsArrayList.get(i).getPoles());
        }
        poles.add(0, getString(R.string.spinner_poles_default));
        poles_spinner.setSelection(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, poles);
        poles_spinner.setAdapter(adapter);
        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().toLowerCase().contains("Wiring Diagrams for Nema Configurations".toLowerCase())) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(WiringDiagramsActivity.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, getString(R.string.title_wiring_diagram_interactive));
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
            case R.id.poles_spinner:
                if (position > 0) {
                    wiresArrayList = wiringDiagramsArrayList.get(position - 1).getWiresArrayList();
                    final ArrayList<String> wireNameList = new ArrayList<>();
                    for (int i = 0; i < wiresArrayList.size(); i++) {
                        wireNameList.add(wiresArrayList.get(i).getWiresName());
                    }
                    wires_spinner.setVisibility(View.VISIBLE);
                    grounding_type_spinner.setVisibility(View.GONE);
                    voltage_spinner.setVisibility(View.GONE);
                    phase_spinner.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);

                    wireNameList.add(0, getString(R.string.spinner_wires_default));
                    wires_spinner.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(WiringDiagramsActivity.this, R.layout.conversion_spinner_dropdown, wireNameList);
                    wires_spinner.setAdapter(adapter);
                } else {
                    wires_spinner.setVisibility(View.GONE);
                    grounding_type_spinner.setVisibility(View.GONE);
                    voltage_spinner.setVisibility(View.GONE);
                    phase_spinner.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
            case R.id.wires_spinner:
                if (position > 0) {
                    groundingTypeArrayList = wiresArrayList.get(position - 1).getGroundingTypeArrayList();
                    final ArrayList<String> groundingTypeList = new ArrayList<>();
                    for (int i = 0; i < groundingTypeArrayList.size(); i++) {
                        groundingTypeList.add(groundingTypeArrayList.get(i).getGroundingType());
                    }
                    wires_spinner.setVisibility(View.VISIBLE);
                    grounding_type_spinner.setVisibility(View.VISIBLE);
                    voltage_spinner.setVisibility(View.GONE);
                    phase_spinner.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);

                    groundingTypeList.add(0, getString(R.string.spinner_grounding_default));
                    grounding_type_spinner.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(WiringDiagramsActivity.this, R.layout.conversion_spinner_dropdown, groundingTypeList);
                    grounding_type_spinner.setAdapter(adapter);
                } else {
                    wires_spinner.setVisibility(View.VISIBLE);
                    grounding_type_spinner.setVisibility(View.GONE);
                    voltage_spinner.setVisibility(View.GONE);
                    phase_spinner.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
            case R.id.grounding_type_spinner:
                if (position > 0) {
                    voltageArrayList = groundingTypeArrayList.get(position - 1).getVoltageArrayList();
                    final ArrayList<String> voltageList = new ArrayList<>();
                    for (int i = 0; i < voltageArrayList.size(); i++) {
                        voltageList.add(voltageArrayList.get(i).getVoltage());
                    }
                    wires_spinner.setVisibility(View.VISIBLE);
                    grounding_type_spinner.setVisibility(View.VISIBLE);
                    voltage_spinner.setVisibility(View.VISIBLE);
                    phase_spinner.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);

                    voltageList.add(0, getString(R.string.spinner_voltage_default));
                    voltage_spinner.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(WiringDiagramsActivity.this, R.layout.conversion_spinner_dropdown, voltageList);
                    voltage_spinner.setAdapter(adapter);
                } else {
                    wires_spinner.setVisibility(View.VISIBLE);
                    grounding_type_spinner.setVisibility(View.VISIBLE);
                    voltage_spinner.setVisibility(View.GONE);
                    phase_spinner.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
            case R.id.voltage_spinner:
                if (position > 0) {
                    phasesArrayList = voltageArrayList.get(position - 1).getPhaseArrayList();
                    final ArrayList<String> phaseList = new ArrayList<>();
                    for (int i = 0; i < phasesArrayList.size(); i++) {
                        phaseList.add(phasesArrayList.get(i).getPhase());
                    }
                    wires_spinner.setVisibility(View.VISIBLE);
                    grounding_type_spinner.setVisibility(View.VISIBLE);
                    voltage_spinner.setVisibility(View.VISIBLE);
                    phase_spinner.setVisibility(View.VISIBLE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);

                    phaseList.add(0, getString(R.string.spinner_phase_default));
                    phase_spinner.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(WiringDiagramsActivity.this, R.layout.conversion_spinner_dropdown, phaseList);
                    phase_spinner.setAdapter(adapter);
                } else {
                    wires_spinner.setVisibility(View.VISIBLE);
                    grounding_type_spinner.setVisibility(View.VISIBLE);
                    voltage_spinner.setVisibility(View.VISIBLE);
                    phase_spinner.setVisibility(View.GONE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
            case R.id.phase_spinner:
                if (position > 0) {
                    String image = phasesArrayList.get(position - 1).getDiagramImageUrl();
                    wires_spinner.setVisibility(View.VISIBLE);
                    grounding_type_spinner.setVisibility(View.VISIBLE);
                    voltage_spinner.setVisibility(View.VISIBLE);
                    phase_spinner.setVisibility(View.VISIBLE);
                    result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                    int resourceId = getResources().getIdentifier(image, "drawable", getPackageName());
                    result.setImageResource(resourceId);
                } else {
                    wires_spinner.setVisibility(View.VISIBLE);
                    grounding_type_spinner.setVisibility(View.VISIBLE);
                    voltage_spinner.setVisibility(View.VISIBLE);
                    phase_spinner.setVisibility(View.VISIBLE);
                    result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_WIRING_DIAGRAMS_INTERACTIVES);
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
