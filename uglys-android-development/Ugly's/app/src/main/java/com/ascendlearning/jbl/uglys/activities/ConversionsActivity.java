package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.ConversionParameters;
import com.ascendlearning.jbl.uglys.models.Conversions;
import com.ascendlearning.jbl.uglys.utils.CompositeKey;
import com.ascendlearning.jbl.uglys.utils.Constants;
import com.ascendlearning.jbl.uglys.utils.Conversion;
import com.ascendlearning.jbl.uglys.utils.DecimalDigitsInputFilter;
import com.ascendlearning.jbl.uglys.utils.ESError;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import org.json.JSONException;
import org.readium.sdk.android.components.navigation.NavigationElement;
import org.readium.sdk.android.components.navigation.NavigationPoint;
import org.readium.sdk.android.launcher.model.OpenPageRequest;

import java.util.ArrayList;
import java.util.List;

public class ConversionsActivity extends SuperActivity implements UICallback, Spinner.OnItemSelectedListener {

    private ArrayList<Conversions> conversionList;
    private UglysController mController;
    private UglysResponse mResponse;
    private CONVERSIONS selectedConversion;
    private Conversion conversionObject;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private int bookmarkSubType;
    private int conversionToSubType;
    private int conversionFromSubType;
    private Spinner spinner;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private LinearLayout conversion_subview;
    private EditText convertTo;
    private EditText convertFrom;
    private ArrayList<String> conversionNameList;
    private ArrayList<String> conversionFromList;
    private ArrayList<String> conversionToList;


    private enum CONVERSIONS {
        TEMPERATURE, WEIGHT, AREA, LENGTH, TIME, VOLUME
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversions);

        initToolbars();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        fetchJsonData();
        mResponse = mController.getJsonResponse();
        conversionList = mResponse.getConversionsList();
        conversionObject = new Conversion();
        bookmarkSubType = getIntent().getIntExtra("subtype", -1);
        conversionToSubType = getIntent().getIntExtra("toSubtype", -1);
        conversionFromSubType = getIntent().getIntExtra("fromSubtype", -1);
        createBookmarkData();
        makeLayout();
        if (bookmarkSubType != -1) {
            spinner.setSelection(bookmarkSubType + 1);
            spinner.setTag(R.id.spinner_pos, bookmarkSubType + 1);
            conversion_subview.setVisibility(View.VISIBLE);
            ArrayList<ConversionParameters> params = conversionList.get(bookmarkSubType).getParamList();

            conversionToList.clear();
            conversionFromList.clear();
            convertFrom.setText("");
            convertTo.setText("");

            conversionToList.add(0, getString(R.string.spinner_default));
            conversionFromList.add(0, getString(R.string.spinner_default));
            spinnerFrom.setSelection(0);
            spinnerTo.setSelection(0);

            for (int j = 0; j < params.size(); j++) {
                conversionFromList.add(params.get(j).getParameterName());
                conversionToList.add(params.get(j).getParameterName());
            }

            final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(ConversionsActivity.this, R.layout.conversion_spinner_dropdown, conversionToList);
            spinnerTo.setAdapter(adapter1);

            final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(ConversionsActivity.this, R.layout.conversion_spinner_dropdown, conversionFromList);
            spinnerFrom.setAdapter(adapter2);

            selectedConversion = CONVERSIONS.values()[bookmarkSubType];
            spinnerFrom.setSelection(conversionFromSubType + 1);
            spinnerTo.setSelection(conversionToSubType + 1);
        }

    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkCalType(bookmarkSubType);
        bookmarkObj.setBookmarkConversionToSubtype(conversionToSubType);
        bookmarkObj.setBookmarkConversionFromSubtype(conversionFromSubType);
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.CONVERSIONS.ordinal());
    }

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_CONVERSIONS);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);
    }

    private void makeLayout() {
        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().toLowerCase().contains("U.S. Weights and Measures".toLowerCase())) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(ConversionsActivity.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, getIntent().getStringExtra("topic_name"));
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

        spinner = (Spinner) findViewById(R.id.conversion_spinner);
        spinnerFrom = (Spinner) findViewById(R.id.conversion_spinner_from);
        spinnerTo = (Spinner) findViewById(R.id.conversion_spinner_to);
        conversion_subview = (LinearLayout) findViewById(R.id.conversion_subview);
        convertTo = (EditText) findViewById(R.id.to);
        convertFrom = (EditText) findViewById(R.id.from);
        convertFrom.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});

        conversionNameList = new ArrayList<>();
        conversionFromList = new ArrayList<>();
        conversionToList = new ArrayList<>();
        for (int i = 0; i < conversionList.size(); i++) {
            conversionNameList.add(conversionList.get(i).getConversionName());
        }
        conversionNameList.add(0, getString(R.string.spinner_default));
        spinner.setSelection(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, conversionNameList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinnerFrom.setOnItemSelectedListener(this);
        spinnerTo.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner selectedSpinner = (Spinner) parent;
        switch (selectedSpinner.getId()) {
            case R.id.conversion_spinner:
                Object tag = spinner.getTag(R.id.spinner_pos);
                if (tag != null) {
                    tag = tag;
                }
                if (position > 0 && tag != position) {
                    spinner.setTag(R.id.spinner_pos, position);
                    conversion_subview.setVisibility(View.VISIBLE);
                    ArrayList<ConversionParameters> params = conversionList.get(position - 1).getParamList();

                    conversionToList.clear();
                    conversionFromList.clear();
                    convertFrom.setText("");
                    convertTo.setText("");

                    conversionToList.add(0, getString(R.string.spinner_default));
                    conversionFromList.add(0, getString(R.string.spinner_default));
                    spinnerFrom.setSelection(0);
                    spinnerTo.setSelection(0);

                    for (int j = 0; j < params.size(); j++) {
                        conversionFromList.add(params.get(j).getParameterName());
                        conversionToList.add(params.get(j).getParameterName());
                    }

                    final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(ConversionsActivity.this, R.layout.conversion_spinner_dropdown, conversionToList);
                    spinnerTo.setAdapter(adapter1);

                    final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(ConversionsActivity.this, R.layout.conversion_spinner_dropdown, conversionFromList);
                    spinnerFrom.setAdapter(adapter2);

                    selectedConversion = CONVERSIONS.values()[position - 1];
                    bookmarkObj.setBookmarkCalType(position - 1);
                    convertFrom.addTextChangedListener(new TextWatcher() {
                        public void afterTextChanged(Editable s) {
                            if (spinnerFrom.getSelectedItemPosition() > 0 && spinnerTo.getSelectedItemPosition() > 0 && convertFrom.getText().toString().length() > 0) {
                                switch (selectedConversion) {
                                    case TEMPERATURE:
                                        convertTo.setText(conversionObject.convertTemp(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                        break;
                                    case WEIGHT:
                                        convertTo.setText(conversionObject.convertWeight(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                        break;
                                    case LENGTH:
                                        convertTo.setText(conversionObject.convertLength(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                        break;
                                    case AREA:
                                        convertTo.setText(conversionObject.convertArea(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                        break;
                                    case VOLUME:
                                        convertTo.setText(conversionObject.convertVolume(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                        break;
                                    case TIME:
                                        convertTo.setText(conversionObject.convertTime(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                        break;
                                }
                            } else if (convertFrom.getText().toString().length() == 0) {
                                convertTo.setText("");
                            }
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            // TODO Auto-generated method stub
                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            // TODO Auto-generated method stub
                        }

                    });
                    convertFrom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                Log.i("", "Enter pressed");
                                convertFrom.setCursorVisible(false);
                            }
                            return false;
                        }
                    });
                }
                if (position == 0 && tag != position) {
                    conversion_subview.setVisibility(View.GONE);
                }
                break;
            case R.id.conversion_spinner_from:
                if (position > 0) {
                    bookmarkObj.setBookmarkConversionFromSubtype(position - 1);
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                    if (spinnerFrom.getSelectedItemPosition() > 0 && spinnerTo.getSelectedItemPosition() > 0) {
                        bookmarkObj.setBookmarkName("Conversion: " + conversionList.get(selectedConversion.ordinal()).getConversionName() + " -From: " + conversionFromList.get(spinnerFrom.getSelectedItemPosition())
                                + " To: " + conversionToList.get(spinnerTo.getSelectedItemPosition()));

                        if (convertFrom.getText().length() > 0) {
                            switch (selectedConversion) {
                                case TEMPERATURE:
                                    convertTo.setText(conversionObject.convertTemp(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                    break;
                                case WEIGHT:
                                    convertTo.setText(conversionObject.convertWeight(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                    break;
                                case LENGTH:
                                    convertTo.setText(conversionObject.convertLength(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                    break;
                                case AREA:
                                    convertTo.setText(conversionObject.convertArea(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                    break;
                                case VOLUME:
                                    convertTo.setText(conversionObject.convertVolume(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                    break;
                                case TIME:
                                    convertTo.setText(conversionObject.convertTime(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                    break;
                            }
                        }
                    }
                }
                break;
            case R.id.conversion_spinner_to:
                if (position > 0) {
                    bookmarkObj.setBookmarkConversionToSubtype(position - 1);
                    if (application.sd.isBookmarked(bookmarkObj)) {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                    } else {
                        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                    }
                    if (spinnerFrom.getSelectedItemPosition() > 0 && spinnerTo.getSelectedItemPosition() > 0) {
                        bookmarkObj.setBookmarkName("Conversion: " + conversionList.get(selectedConversion.ordinal()).getConversionName() + " -From: " + conversionFromList.get(spinnerFrom.getSelectedItemPosition())
                                + " To: " + conversionToList.get(spinnerTo.getSelectedItemPosition()));
                        if (convertFrom.getText().length() > 0) {
                            switch (selectedConversion) {
                                case TEMPERATURE:
                                    convertTo.setText(conversionObject.convertTemp(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                    break;
                                case WEIGHT:
                                    convertTo.setText(conversionObject.convertWeight(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                    break;
                                case LENGTH:
                                    convertTo.setText(conversionObject.convertLength(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                    break;
                                case AREA:
                                    convertTo.setText(conversionObject.convertArea(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                    break;
                                case VOLUME:
                                    convertTo.setText(conversionObject.convertVolume(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                    break;
                                case TIME:
                                    convertTo.setText(conversionObject.convertTime(spinnerFrom.getSelectedItemPosition() - 1, spinnerTo.getSelectedItemPosition() - 1, Double.parseDouble(convertFrom.getText().toString())));
                                    break;
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initToolbars() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbarTop);
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
            if (bookmarkObj.getBookmarkName() != null) {
                application.sd.toggleBookmark(bookmarkObj);
                if (application.sd.isBookmarked(bookmarkObj)) {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                } else {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                }
            } else {
                showToast("Please select a Conversion to bookmark", 1000);
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
