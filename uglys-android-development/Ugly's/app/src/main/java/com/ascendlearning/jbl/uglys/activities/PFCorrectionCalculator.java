package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.CorrectedPF;
import com.ascendlearning.jbl.uglys.models.PFCorrection;
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

public class PFCorrectionCalculator extends SuperActivity implements UICallback, Spinner.OnItemSelectedListener {
    private UglysController mController;
    private UglysResponse mResponse;
    private ArrayList<PFCorrection> pfCorrectionArrayList;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private Spinner epf_spinner;
    private Spinner cpf_spinner;
    private TextView result;
    private TextView pf_correction_result_header;
    private LinearLayout result_view;
    private ArrayList<String> epfArrayList;
    private ArrayList<CorrectedPF> cpfArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pfcorrection_calculator);

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
        pfCorrectionArrayList = mResponse.getPfCorrectionArrayList();

        createBookmarkData();
        makeLayout();
    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkName("PF Correction Calculator");
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.PF_CORRECTION.ordinal());
    }

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_PF_CORRECTION);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);
    }

    private void makeLayout() {
        epfArrayList = new ArrayList<>();
        for (int i = 0; i < pfCorrectionArrayList.size(); i++) {
            epfArrayList.add(Integer.toString(pfCorrectionArrayList.get(i).getEpf()));
        }
        pf_correction_result_header = (TextView) findViewById(R.id.pf_correction_result_header);
        result_view = (LinearLayout) findViewById(R.id.result_view);
        result = (TextView) findViewById(R.id.result);
        epf_spinner = (Spinner) findViewById(R.id.epf_spinner);
        cpf_spinner = (Spinner) findViewById(R.id.cpf_spinner);
        epf_spinner.setOnItemSelectedListener(this);
        cpf_spinner.setOnItemSelectedListener(this);

        epfArrayList.add(0, getString(R.string.spinner_epf_default));
        epf_spinner.setSelection(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, epfArrayList);
        epf_spinner.setAdapter(adapter);

        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().toLowerCase().contains("Power Factor Correction".toLowerCase())) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(PFCorrectionCalculator.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, "Power Factor Correction");
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner selectedSpinner = (Spinner) parent;
        switch (selectedSpinner.getId()) {
            case R.id.epf_spinner:
                 if (position > 0) {
                    cpfArrayList = pfCorrectionArrayList.get(position - 1).getCorrectedPfArrayList();
                    final ArrayList<String> CPFList = new ArrayList<>();
                    for (int i = 0; i < cpfArrayList.size(); i++) {
                        CPFList.add(cpfArrayList.get(i).getName());
                    }
                    cpf_spinner.setVisibility(View.VISIBLE);
                    CPFList.add(0, getString(R.string.spinner_cpf_default));
                    cpf_spinner.setSelection(0);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(PFCorrectionCalculator.this, R.layout.conversion_spinner_dropdown, CPFList);
                    cpf_spinner.setAdapter(adapter);
                    pf_correction_result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }else{
                     cpf_spinner.setVisibility(View.GONE);
                     pf_correction_result_header.setVisibility(View.GONE);
                     result_view.setVisibility(View.GONE);
                 }
                break;
            case R.id.cpf_spinner:
                if (position > 0) {
                    pf_correction_result_header.setVisibility(View.VISIBLE);
                    result_view.setVisibility(View.VISIBLE);
                    TextUtil.insertText(result, Double.toString(cpfArrayList.get(position - 1).getValue()), Typeface.BOLD);
                }else{
                    pf_correction_result_header.setVisibility(View.GONE);
                    result_view.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
