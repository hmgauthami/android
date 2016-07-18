package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.adapters.BookmarksAdapter;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.utils.Constants;

import org.json.JSONException;
import org.readium.sdk.android.components.navigation.NavigationElement;
import org.readium.sdk.android.components.navigation.NavigationPoint;
import org.readium.sdk.android.launcher.model.Bookmark;
import org.readium.sdk.android.launcher.model.BookmarkDatabase;
import org.readium.sdk.android.launcher.model.OpenPageRequest;

import java.util.ArrayList;
import java.util.List;

public class BookmarksActivity extends SuperActivity {
    private List<Bookmarks> bookmarkList;
    private List<Bookmark> bookmarks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbarTop);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        makeLayout();
    }

    private void makeLayout() {
        populateData();
        ListView bookmarksListView = (ListView) findViewById(R.id.bookmarks_listView);
        bookmarksListView.setAdapter(new BookmarksAdapter(this, R.layout.bookmarks_row, bookmarkList));
        bookmarksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bookmarks selectedBookmark = bookmarkList.get(position);
                if (selectedBookmark.getBookmarkType() == Constants.BOOKMARK_TYPE.OTHERS.ordinal()) {
                    final Constants.BOOKMARK_CODE bookmarkCode = Constants.BOOKMARK_CODE.values()[selectedBookmark.getBookmarkCode()];
                    switch (bookmarkCode) {
                        case OHMS:
                            Intent intent = new Intent(BookmarksActivity.this, OhmsLawCalculator.class);
                            intent.putExtra("subtype", selectedBookmark.getBookmarkCalType());
                            startActivity(intent);
                            break;
                        case TOFIND1:
                            intent = new Intent(BookmarksActivity.this, ToFind1Calculator.class);
                            intent.putExtra("subtype", selectedBookmark.getBookmarkCalType());
                            intent.putExtra("knownVal", selectedBookmark.getBookmarkToFindKnown());
                            intent.putExtra("currentType", selectedBookmark.getBookmarkToFindCurrentType());
                            startActivity(intent);
                            break;
                        case TOFIND2:
                            intent = new Intent(BookmarksActivity.this, ToFind2Calculator.class);
                            intent.putExtra("subtype", selectedBookmark.getBookmarkCalType());
                            intent.putExtra("knownVal", selectedBookmark.getBookmarkToFindKnown());
                            startActivity(intent);
                            break;
                        case PF_CORRECTION:
                            intent = new Intent(BookmarksActivity.this, PFCorrectionCalculator.class);
                            startActivity(intent);
                            break;
                        case AMPACITY:
                            intent = new Intent(BookmarksActivity.this, SimpleAmpacityCalculator.class);
                            startActivity(intent);
                            break;
                        case BENDING:
                            if (selectedBookmark.getBookmarkCalType() == 5) {
                                intent = new Intent(BookmarksActivity.this, OffsetBendingActivity.class);
                            } else {
                                intent = new Intent(BookmarksActivity.this, BendingDetailsActivity.class);
                            }
                            intent.putExtra("bendingName", selectedBookmark.getBookmarkName());
                            intent.putExtra("bendingType", selectedBookmark.getBookmarkCalType());
                            startActivity(intent);
                            break;
                        case CONVERSIONS:
                            intent = new Intent(BookmarksActivity.this, ConversionsActivity.class);
                            intent.putExtra("subtype", selectedBookmark.getBookmarkCalType());
                            intent.putExtra("fromSubtype", selectedBookmark.getBookmarkConversionFromSubtype());
                            intent.putExtra("toSubtype", selectedBookmark.getBookmarkConversionToSubtype());
                            startActivity(intent);
                            break;
                        case FLC_MOTOR:
                            intent = new Intent(BookmarksActivity.this, FLCMotorCalculatorActivity.class);
                            intent.putExtra("flcType", selectedBookmark.getBookmarkCalType());
                            startActivity(intent);
                            break;
                        case FLC_MOTOR_OTHER_DATA:
                            intent = new Intent(BookmarksActivity.this, FLCOtherDataCalculatorActivity.class);
                            startActivity(intent);
                            break;
                        case FLC_TRANSFORMER:
                            intent = new Intent(BookmarksActivity.this, FLCTransformerCalculatorActivity.class);
                            intent.putExtra("flcType", selectedBookmark.getBookmarkCalType());
                            startActivity(intent);
                            break;
                        case LRC:
                            intent = new Intent(BookmarksActivity.this, LRCCalculator.class);
                            intent.putExtra("motorType", selectedBookmark.getBookmarkCalType());
                            startActivity(intent);
                            break;
                        case VOLTAGE_DROP:
                            intent = new Intent(BookmarksActivity.this, VoltageDropCalculatorActivity.class);
                            intent.putExtra("voltageType", selectedBookmark.getBookmarkCalType());
                            startActivity(intent);
                            break;
                        case CONDUIT_FILL:
                            intent = new Intent(BookmarksActivity.this, ConduitFillCalculator.class);
                            intent.putExtra("conduitType", selectedBookmark.getBookmarkCalType());
                            startActivity(intent);
                            break;
                        case WIRING_DIAGRAMS:
                            intent = new Intent(BookmarksActivity.this, WiringDiagramsActivity.class);
                            startActivity(intent);
                            break;
                        case SYMBOLS:
                            intent = new Intent(BookmarksActivity.this, SymbolsActivity.class);
                            intent.putExtra("symbolType", selectedBookmark.getBookmarkCalType());
                            startActivity(intent);
                            break;
                    }

                } else {
                    final List<NavigationElement> navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                    for (int i = 0; i < navigationElements.size(); i++) {
                        selectedBookmark = bookmarkList.get(position);
                        for (int j = 0; j < bookmarks.size(); j++) {
                            if (bookmarks.get(j).getTitle().equalsIgnoreCase(selectedBookmark.getBookmarkName())) {
                                if (navigationElements.get(i).getTitle().equalsIgnoreCase(bookmarks.get(j).getTitle())) {
                                    NavigationElement navigation = navigationElements.get(i);
                                    if (navigation instanceof NavigationPoint) {
                                        NavigationPoint point = (NavigationPoint) navigation;
                                        Intent intent = new Intent(BookmarksActivity.this, BookViewActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                                        intent.putExtra(Constants.TOPIC_NAME, bookmarks.get(j).getTitle());
                                        OpenPageRequest openPageRequest = OpenPageRequest.fromContentUrl(point.getContent(), application.navigationTable.getSourceHref());
                                        try {
                                            intent.putExtra(Constants.OPEN_PAGE_REQUEST_DATA, openPageRequest.toJSON().toString());
                                            startActivity(intent);
                                        } catch (JSONException e) {
                                        }
                                    }
                                    break;
                                }
                                break;
                            }
                        }

                    }
                }
            }
        });
    }


    private void populateData() {
        bookmarkList = new ArrayList<>();
        bookmarkList = application.sd.fetchBookmarks();
        bookmarks = BookmarkDatabase.getInstance().getBookmarks(application.container.getName());
        for (int i = 0; i < bookmarks.size(); i++) {
            Bookmark bookmark = bookmarks.get(i);
            Bookmarks bookmarkObj = new Bookmarks();
            bookmarkObj.setBookmarkName(bookmark.getTitle());
            bookmarkObj.setBookmarkType(Constants.BOOKMARK_TYPE.BOOK.ordinal());
            bookmarkList.add(bookmarkObj);
        }

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
        } else if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bookmarks, menu);
        return true;
    }

}
