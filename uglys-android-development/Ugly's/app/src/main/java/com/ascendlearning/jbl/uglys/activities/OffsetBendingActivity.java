package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SwitchCompat;
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
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.OffsetBending;
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
import java.util.HashMap;
import java.util.List;

public class OffsetBendingActivity extends SuperActivity implements UICallback {

    private int bendingType;
    private UglysController mController;
    private UglysResponse mResponse;
    private ArrayList<OffsetBending> offsetBendingArrayList;
    private Menu menu;
    private Bookmarks bookmarkObj;
    private String bendingName;
    private HashMap<Integer, Double> offsetBendingmultiplierHashmap;
    private SwitchCompat chk;
    private WebView webview_bending;
    private LinearLayout webview_layout;
    private double multiplier;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offset_bending_activity);

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
        offsetBendingArrayList = mResponse.getOffsetBendingArrayList();

        offsetBendingmultiplierHashmap = new HashMap<>();
        for (int i = 0; i < offsetBendingArrayList.size(); i++) {
            offsetBendingmultiplierHashmap.put(offsetBendingArrayList.get(i).getAngle(), offsetBendingArrayList.get(i).getMultiplier());
        }

        bendingType = getIntent().getIntExtra("bendingType", -1);
        bendingName = getIntent().getStringExtra("bendingName");
        createBookmarkData();

        makeLayout();
        //loadHTML();

    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkName(bendingName);
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.BENDING.ordinal());
        bookmarkObj.setBookmarkCalType(bendingType);
    }

    private void loadHTML() {
        // final WebView webview_bending = (WebView) findViewById(R.id.webview_bending);
        webview_bending = new WebView(this);
        webview_layout.addView(webview_bending);
        webview_bending.loadUrl("file:///android_asset/html/offset.html");
    }

    private void makeLayout() {
        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().toLowerCase().contains(bendingName.toLowerCase())) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(OffsetBendingActivity.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, bendingName);
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
        final ScrollView scrollview = (ScrollView) findViewById(R.id.bending_scrollview);
        TextView bending_details_text_view = (TextView) findViewById(R.id.bending_details_text_view);
        TextView table_header = (TextView) findViewById(R.id.table_header);
        TextView multiplier_header = (TextView) findViewById(R.id.multiplier_header);
        TextView angle_header = (TextView) findViewById(R.id.angle_header);
        TextUtil.insertText(table_header, getString(R.string.table_header), Typeface.BOLD);
        TextUtil.insertText(multiplier_header, getString(R.string.multiplier_header), Typeface.BOLD);
        TextUtil.insertText(angle_header, getString(R.string.angle), Typeface.BOLD);
        TextUtil.insertText(bending_details_text_view, getIntent().getStringExtra("bendingName"));
        final TextView result1_text = (TextView) findViewById(R.id.result);
        final TextView result2_text = (TextView) findViewById(R.id.result1);
        final TextView result_header = (TextView) findViewById(R.id.result_header);
        final LinearLayout result_view = (LinearLayout) findViewById(R.id.result_view);
        webview_layout = (LinearLayout) findViewById(R.id.web_view_layout);

        chk = (SwitchCompat) findViewById(R.id.chk);

        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    loadHTML();
                    //scrollview.setVisibility(View.VISIBLE);
                    scrollview.post(new Runnable() {
                        public void run() {
                            scrollview.smoothScrollBy(0, 0);
                            scrollview.fullScroll(ScrollView.FOCUS_UP);
                        }
                    });
                } else {
                    webview_layout.removeView(webview_bending);
                    scrollview.post(new Runnable() {
                        public void run() {
                            scrollview.smoothScrollBy(0, 0);
                            scrollview.fullScroll(ScrollView.FOCUS_UP);
                        }
                    });
                    //scrollview.setVisibility(View.GONE);
                }
            }
        });

        TextView bend_distance_header = (TextView) findViewById(R.id.bend_distance_header);
        TextUtil.insertText(bend_distance_header, getString(R.string.offset_bending_calculator_bend_distance), Typeface.BOLD);

        TextView angle_text = (TextView) findViewById(R.id.angle_text);
        TextUtil.insertText(angle_text, getString(R.string.angle), Typeface.BOLD);

        TextView offset_text = (TextView) findViewById(R.id.offset_text);
        TextUtil.insertText(offset_text, getString(R.string.offset), Typeface.BOLD);

        final TextView result_text = (TextView) findViewById(R.id.result_text);
        TextUtil.insertText(result_text, getString(R.string.result), Typeface.BOLD);

        final EditText angle_edit = (EditText) findViewById(R.id.angle_edit);
        final EditText offset_edit = (EditText) findViewById(R.id.offset_edit);
        final EditText result_edit = (EditText) findViewById(R.id.result_edit);
        angle_edit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});
        offset_edit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(Constants.MAX_LENGTH, Constants.MAX_DEC_LENGTH)});

        angle_edit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (offset_edit != null && angle_edit != null) {
                    if (getCurrentFocus() == angle_edit) {
                        if (angle_edit.length() > 0) {
                            if (Double.parseDouble(angle_edit.getText().toString()) <= 90) {
                                if (Double.parseDouble(angle_edit.getText().toString()) < 1) {
                                    multiplier = ((60 / Double.parseDouble(angle_edit.getText().toString())) + (Double.parseDouble(angle_edit.getText().toString()) / 200)) - 0.15;
                                } else if(Double.parseDouble(angle_edit.getText().toString()) > 0){
                                    multiplier = offsetBendingmultiplierHashmap.get((int) Double.parseDouble(angle_edit.getText().toString()));
                                }
                                if (offset_edit.length() > 0) {
                                    double offset = Double.parseDouble(offset_edit.getText().toString());
                                    double result = multiplier * offset;
                                    result_edit.setText(TextUtil.convertToExponential(result));
                                    result1_text.setText(TextUtil.convertToExponential(result));
                                    result2_text.setText(calculateShrinkage(Double.parseDouble(angle_edit.getText().toString())));
                                    result_header.setVisibility(View.VISIBLE);
                                    result_view.setVisibility(View.VISIBLE);
                                }
                            } else {
                                angle_edit.setText("");
                                result_edit.setText("");
                                showToast(getString(R.string.angle_validation), 1000);
                                result_header.setVisibility(View.GONE);
                                result_view.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

        });
        offset_edit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (offset_edit != null && angle_edit != null) {
                    if (getCurrentFocus() == offset_edit) {
                        if (angle_edit.length() > 0 && offset_edit.length() > 0) {
                            double offset = Double.parseDouble(offset_edit.getText().toString());
                            double result = multiplier * offset;
                            result_edit.setText(TextUtil.convertToExponential(result));
                            result1_text.setText(TextUtil.convertToExponential(result));
                            result2_text.setText(calculateShrinkage(Double.parseDouble(angle_edit.getText().toString())));
                            result_header.setVisibility(View.VISIBLE);
                            result_view.setVisibility(View.VISIBLE);
                        } else {
                            result_header.setVisibility(View.GONE);
                            result_view.setVisibility(View.GONE);
                            result_edit.setText("");
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

        });

        offset_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("", "Enter pressed");
                    offset_edit.setCursorVisible(false);
                }
                return false;
            }
        });
    }

    private String calculateShrinkage(double angle) {

        double result = 0;
        if (angle < 60) {
            result = angle / 120;
        } else if (angle >= 60 && angle <= 90) {
            result = angle / 100;
        }
        return TextUtil.convertToExponential(result);

    }

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_OFFSET_BENDING);
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
}
