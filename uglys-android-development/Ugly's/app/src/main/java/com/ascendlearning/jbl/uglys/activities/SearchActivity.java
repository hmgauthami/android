package com.ascendlearning.jbl.uglys.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.adapters.SearchResultsAdapter;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bending;
import com.ascendlearning.jbl.uglys.models.Calculators;
import com.ascendlearning.jbl.uglys.models.SearchResults;
import com.ascendlearning.jbl.uglys.models.Topics;
import com.ascendlearning.jbl.uglys.utils.CompositeKey;
import com.ascendlearning.jbl.uglys.utils.Constants;
import com.ascendlearning.jbl.uglys.utils.ESError;

import org.json.JSONException;
import org.readium.sdk.android.components.navigation.NavigationElement;
import org.readium.sdk.android.components.navigation.NavigationPoint;
import org.readium.sdk.android.launcher.model.OpenPageRequest;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends SuperActivity implements UICallback {

    private SearchResultsAdapter adapter;
    private EditText searchView;
    private ListView search_results_listView;
    private List<NavigationElement> navigationElements;
    private ArrayList<SearchResults> searchResults;
    private ArrayList<Calculators> calculatorList;
    private ArrayList<Topics> topicsList;
    private ArrayList<Bending> bendingList;
    private SearchResults selectedResult;
    private UglysController mController;
    private UglysResponse mResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbarTop);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        fetchCalculatorJsonData();
        mResponse = mController.getJsonResponse();
        calculatorList = mResponse.getCalculatorList();
        fetchBendingJsonData();
        mResponse = mController.getJsonResponse();
        bendingList = mResponse.getBendingList();
        fetchTopicsJsonData();
        mResponse = mController.getJsonResponse();
        topicsList = mResponse.getTopicsList();
        makeLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void fetchCalculatorJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_CALCULATORS);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);

    }

    private void fetchTopicsJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_TOPICS);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);

    }

    private void fetchBendingJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_BENDING);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);
    }

    private void makeLayout() {
        final ArrayList<SearchResults> finalSearchResults = new ArrayList<>();

        searchView = (EditText) findViewById(R.id.search);
        searchView.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(searchView, 0);
            }
        }, 200);
        searchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (searchView.getRight() - searchView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (searchView.getText().toString().length() > 0) {
                            searchView.setText("");
                            Drawable img = getResources().getDrawable(R.drawable.search_magnifyingglass);
                            img.setBounds(0, 0, 60, 60);
                            searchView.setCompoundDrawables(null, null, img, null);
                        } else {
                            InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            keyboard.showSoftInput(searchView, 0);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        search_results_listView = (ListView) findViewById(R.id.search_results_listView);
        if (navigationElements == null) {
            navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
        }
        searchResults = new ArrayList<>();
        for (int i = 0; i < calculatorList.size(); i++) {
            SearchResults resultObj = new SearchResults();
            resultObj.setTopicName(calculatorList.get(i).getCalculatorName());
            resultObj.setResultType(Constants.SEARCH_RESULT_TYPE.CALCULATOR.ordinal());
            resultObj.setCalculatorType(calculatorList.get(i).getCalculatorType());
            searchResults.add(resultObj);
        }
        for (int i = 0; i < bendingList.size(); i++) {
            SearchResults resultObj = new SearchResults();
            resultObj.setTopicName(bendingList.get(i).getBendingName());
            resultObj.setResultType(Constants.SEARCH_RESULT_TYPE.INTERACTIVE.ordinal());
            resultObj.setCalculatorType(bendingList.get(i).getType());
            searchResults.add(resultObj);
        }
        for (int i = 0; i < topicsList.size(); i++) {
            if (topicsList.get(i).getSubTopicsArrayList() != null) {
                for (int j = 0; j < topicsList.get(i).getSubTopicsArrayList().size(); j++) {
                    if (topicsList.get(i).getSubTopicsArrayList().get(j).getTypeOfContent() != null) {
                        if (topicsList.get(i).getSubTopicsArrayList().get(j).getTypeOfContent().equalsIgnoreCase(getString(R.string.interactive))) {
                            String calculator_id = topicsList.get(i).getSubTopicsArrayList().get(j).getGetTypeOfCalculator();
                            if (calculator_id != null) {
                                if (calculator_id.equalsIgnoreCase("12") || calculator_id.equalsIgnoreCase("13")) {
                                    SearchResults resultObj = new SearchResults();
                                    resultObj.setTopicName(topicsList.get(i).getSubTopicsArrayList().get(j).getSubTopicName());
                                    resultObj.setResultType(Constants.SEARCH_RESULT_TYPE.INTERACTIVE.ordinal());
                                    resultObj.setCalculatorType(Integer.parseInt(topicsList.get(i).getSubTopicsArrayList().get(j).getGetTypeOfCalculator()));
                                    searchResults.add(resultObj);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 5; i < navigationElements.size(); i++) {
            SearchResults resultObj = new SearchResults();
            resultObj.setTopicName(navigationElements.get(i).getTitle());
            resultObj.setResultType(Constants.SEARCH_RESULT_TYPE.EPUB.ordinal());
            searchResults.add(resultObj);
        }
        adapter = new SearchResultsAdapter(SearchActivity.this, R.layout.search_results_row, finalSearchResults, searchView.getText().toString());
        search_results_listView.setAdapter(adapter);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                finalSearchResults.clear();
                adapter = new SearchResultsAdapter(SearchActivity.this, R.layout.search_results_row, finalSearchResults, searchView.getText().toString());
                search_results_listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (searchView.getText().toString().length() > 0) {
                    Drawable img = getResources().getDrawable(R.drawable.modal_closex);
                    img.setBounds(0, 0, 60, 60);
                    searchView.setCompoundDrawables(null, null, img, null);
                    for (int i = 0; i < searchResults.size(); i++) {
                        if (searchResults.get(i).getTopicName().toLowerCase().contains(searchView.getText().toString().toLowerCase())) {
                            finalSearchResults.add(searchResults.get(i));
                        }
                    }
                    adapter = new SearchResultsAdapter(SearchActivity.this, R.layout.search_results_row, finalSearchResults, searchView.getText().toString());
                    search_results_listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });


        search_results_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedResult = finalSearchResults.get(position);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

                handler.sendEmptyMessage(0);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
        }else if (id == R.id.action_bookmarks) {
            Intent intent = new Intent(this, BookmarksActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String selected = selectedResult.getTopicName();
            int type = selectedResult.getResultType();
            Constants.SEARCH_RESULT_TYPE choice = Constants.SEARCH_RESULT_TYPE.values()[type];
            switch (choice) {
                case EPUB:
                    if (navigationElements == null) {
                        navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                    }
                    for (int i = 0; i < navigationElements.size(); i++) {
                        if (navigationElements.get(i).getTitle().equalsIgnoreCase(selected)) {
                            NavigationElement navigation = navigationElements.get(i);
                            if (navigation instanceof NavigationPoint) {
                                NavigationPoint point = (NavigationPoint) navigation;
                                Intent intent = new Intent(SearchActivity.this, BookViewActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                                intent.putExtra(Constants.TOPIC_NAME, selected);
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
                    break;
                case CALCULATOR:
                    int calculatorType = selectedResult.getCalculatorType();
                    Intent intent = null;
                    switch (calculatorType) {
                        case 1:
                            intent = new Intent(SearchActivity.this, OhmsLawCalculator.class);
                            break;
                        case 2:
                            intent = new Intent(SearchActivity.this, ToFind1Calculator.class);
                            break;
                        case 3:
                            intent = new Intent(SearchActivity.this, PFCorrectionCalculator.class);
                            break;
                        case 4:
                            intent = new Intent(SearchActivity.this, FLCMotorCalculatorActivity.class);
                            break;
                        case 5:
                            intent = new Intent(SearchActivity.this, FLCOtherDataCalculatorActivity.class);
                            break;
                        case 6:
                            intent = new Intent(SearchActivity.this, LRCCalculator.class);
                            break;
                        case 7:
                            intent = new Intent(SearchActivity.this, VoltageDropCalculatorActivity.class);
                            break;
                        case 8:
                            intent = new Intent(SearchActivity.this, FLCTransformerCalculatorActivity.class);
                            break;
                        case 9:
                            intent = new Intent(SearchActivity.this, SimpleAmpacityCalculator.class);
                            break;
                        case 10:
                            intent = new Intent(SearchActivity.this, ConduitFillCalculator.class);
                            break;
                        case 11:
                            intent = new Intent(SearchActivity.this, ConversionsActivity.class);
                            break;
                        case 12:
                            intent = new Intent(SearchActivity.this, OffsetBendingActivity.class);
                            intent.putExtra("bendingName", "Offset Bending Calculator");
                            intent.putExtra("bendingType", 5);
                            break;
                        case 13:
                            intent = new Intent(SearchActivity.this, ToFind2Calculator.class);
                            break;
                    }
                    if (intent != null) {
                        intent.putExtra("topic_name", selectedResult.getTopicName());
                        startActivity(intent);
                    }
                    break;
                case INTERACTIVE:
                    switch (selectedResult.getCalculatorType()) {
                        case 5:
                            intent = new Intent(SearchActivity.this, OffsetBendingActivity.class);
                            intent.putExtra("bendingName", "Offset Bending Calculator");
                            intent.putExtra("bendingType", selectedResult.getCalculatorType());
                            startActivity(intent);
                            break;
                        case 6:
                        case 9:
                            intent = new Intent(SearchActivity.this, BendingDetailsActivity.class);
                            intent.putExtra("bendingName", selectedResult.getTopicName());
                            intent.putExtra("bendingType", selectedResult.getCalculatorType());
                            startActivity(intent);
                            break;
                        case 12:
                            intent = new Intent(SearchActivity.this, WiringDiagramsActivity.class);
                            startActivity(intent);
                            break;
                        case 13:
                            intent = new Intent(SearchActivity.this, SymbolsActivity.class);
                            startActivity(intent);
                            break;
                    }
                    break;
            }
        }
    };

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
