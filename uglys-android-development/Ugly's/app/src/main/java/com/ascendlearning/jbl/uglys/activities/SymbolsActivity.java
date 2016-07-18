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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.Symbols;
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

public class SymbolsActivity extends SuperActivity implements UICallback, Spinner.OnItemSelectedListener {
    private ArrayList<Symbols> symbolsList;
    private UglysController mController;
    private UglysResponse mResponse;
    private Bookmarks bookmarkObj;
    private Menu menu;
    private Spinner spinner;
    private LinearLayout result_view;
    private ImageView result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symbols);

        initToolbars();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        fetchJsonData();
        mResponse = mController.getJsonResponse();
        symbolsList = mResponse.getSymbolList();
        int symbolType = getIntent().getIntExtra("symbolType", -1);
        createBookmarkData();
        makeLayout();

        if (symbolType != -1) {
            spinner.setSelection(symbolType);
            spinner.setTag(R.id.spinner_pos, symbolType);
            bookmarkObj.setBookmarkCalType(symbolType);
            int resourceId = getResources().getIdentifier(symbolsList.get(symbolType - 1).getSymbolImage(), "drawable", getPackageName());
            result.setImageResource(resourceId);
            result_view.setVisibility(View.VISIBLE);
        }
    }

    private void createBookmarkData() {
        bookmarkObj = new Bookmarks();
        bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.OTHERS.ordinal());
        bookmarkObj.setBookmarkCode(Constants.BOOKMARK_CODE.SYMBOLS.ordinal());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object tag = spinner.getTag(R.id.spinner_pos);
        if (tag != null) {
            tag = tag;
        }
        if (position > 0 && tag != position) {
            spinner.setTag(R.id.spinner_pos, position);
            bookmarkObj.setBookmarkCalType(position);
            bookmarkObj.setBookmarkName("Symbols - " + symbolsList.get(position - 1).getSymbolName());
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
            int resourceId = getResources().getIdentifier(symbolsList.get(position - 1).getSymbolImage(), "drawable", getPackageName());
            result.setImageResource(resourceId);
            result_view.setVisibility(View.VISIBLE);

        } else if (position == 0) {
            result_view.setVisibility(View.GONE);
            bookmarkObj.setBookmarkCalType(-1);
            if (application.sd.isBookmarked(bookmarkObj)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
            } else {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

            }
        }
    }

    private void makeLayout() {
        spinner = (Spinner) findViewById(R.id.symbols_spinner);
        result_view = (LinearLayout) findViewById(R.id.result_view);
        result = (ImageView) findViewById(R.id.result);
        final ArrayList<String> symbolNameList = new ArrayList<>();
        for (int i = 0; i < symbolsList.size(); i++) {
            symbolNameList.add(symbolsList.get(i).getSymbolName());
        }
        symbolNameList.add(0, getString(R.string.spinner_default));
        spinner.setSelection(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.conversion_spinner_dropdown, symbolNameList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        TextView bookLocation = (TextView) findViewById(R.id.book_location);
        TextUtil.insertText(bookLocation, getString(R.string.book_location));
        bookLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                for (int i = 0; i < navigationElements.size(); i++) {
                    if (navigationElements.get(i).getTitle().toLowerCase().contains("Electrical Symbols".toLowerCase())) {
                        NavigationElement navigation = navigationElements.get(i);
                        if (navigation instanceof NavigationPoint) {
                            NavigationPoint point = (NavigationPoint) navigation;
                            Intent intent = new Intent(SymbolsActivity.this, BookViewActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                            intent.putExtra(Constants.TOPIC_NAME, "Electrical Symbols");
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

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_SYMBOLS);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bookmark) {
            if (bookmarkObj.getBookmarkCalType() > 0) {
                application.sd.toggleBookmark(bookmarkObj);
                if (application.sd.isBookmarked(bookmarkObj)) {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark_onstate));
                } else {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.wrapper_addbookmark));

                }
            } else {
                showToast(getString(R.string.symbol_validation), 1000);
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
