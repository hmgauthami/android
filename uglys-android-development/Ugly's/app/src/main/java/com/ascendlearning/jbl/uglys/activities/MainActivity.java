package com.ascendlearning.jbl.uglys.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.adapters.MainScreenAdapter;
import com.ascendlearning.jbl.uglys.adapters.SearchResultsAdapter;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Bending;
import com.ascendlearning.jbl.uglys.models.Calculators;
import com.ascendlearning.jbl.uglys.models.MainMenu;
import com.ascendlearning.jbl.uglys.models.PFCorrection;
import com.ascendlearning.jbl.uglys.models.SearchResults;
import com.ascendlearning.jbl.uglys.utils.CompositeKey;
import com.ascendlearning.jbl.uglys.utils.Constants;
import com.ascendlearning.jbl.uglys.utils.ESError;

import org.json.JSONException;
import org.readium.sdk.android.components.navigation.NavigationElement;
import org.readium.sdk.android.components.navigation.NavigationPoint;
import org.readium.sdk.android.launcher.model.OpenPageRequest;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends SuperActivity{
    private GridView gridView;
    private List<NavigationElement> navigationElements;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbars();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(false);

        populateMainMenu();


    }

    @Override
    public void onResume() {
        super.onResume();
     }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void populateMainMenu() {
        gridView = (GridView) findViewById(R.id.gridView1);

        String[] main_menu_title = new String[]{
                getString(R.string.main_menu_topics), getString(R.string.main_menu_calculators), getString(R.string.main_menu_bookmarks),
                getString(R.string.main_menu_symbols), getString(R.string.main_menu_conversions), getString(R.string.main_menu_bending),
                getString(R.string.main_menu_safety), getString(R.string.main_menu_nec)};
        Integer[] main_menu_image_id = new Integer[]{
                R.drawable.topics_btn_pressed, R.drawable.calculator_btn_pressed, R.drawable.bookmarks_btn_pressed,
                R.drawable.symbols_btn_pressed, R.drawable.conversions_btn_pressed, R.drawable.bending_btn_pressed,
                R.drawable.safety_btn_pressed, R.drawable.nec_btn_pressed};
        ArrayList<MainMenu> mainMenuList = new ArrayList<>();
        for (int i = 0; i < main_menu_title.length; i++) {
            MainMenu mainMenuObj = new MainMenu();
            mainMenuObj.setImageResourceId(main_menu_image_id[i]);
            mainMenuObj.setTitle(main_menu_title[i]);
            mainMenuList.add(mainMenuObj);
        }

        gridView.setAdapter(new MainScreenAdapter(this, R.layout.activity_main_gridview, mainMenuList));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                switch (position) {
                                                    case 0:
                                                        Intent intent = new Intent(MainActivity.this, TopicsScreen.class);
                                                        startActivity(intent);
                                                        break;
                                                    case 1:
                                                        intent = new Intent(MainActivity.this, CalculatorListActivity.class);
                                                        startActivity(intent);
                                                        break;
                                                    case 2:
                                                        intent = new Intent(MainActivity.this, BookmarksActivity.class);
                                                        startActivity(intent);
                                                        break;
                                                    case 3:
                                                        intent = new Intent(MainActivity.this, SymbolsActivity.class);
                                                        startActivity(intent);
                                                        break;
                                                    case 4:
                                                        intent = new Intent(MainActivity.this, ConversionsActivity.class);
                                                        startActivity(intent);
                                                        break;
                                                    case 5:
                                                        intent = new Intent(MainActivity.this, BendingActivity.class);
                                                        startActivity(intent);
                                                        break;
                                                    case 6:
                                                        if (navigationElements == null) {
                                                            navigationElements = application.flatNavigationTable(application.getNavigationTable(), new ArrayList<NavigationElement>());
                                                        }
                                                        for (int i = 0; i < navigationElements.size(); i++) {
                                                            if (navigationElements.get(i).getTitle().equalsIgnoreCase("Electrical Safety")) {
                                                                NavigationElement navigation = navigationElements.get(i);
                                                                if (navigation instanceof NavigationPoint) {
                                                                    NavigationPoint point = (NavigationPoint) navigation;
                                                                    intent = new Intent(MainActivity.this, BookViewActivity.class);
                                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                    intent.putExtra(Constants.CONTAINER_ID, application.container.getNativePtr());
                                                                    intent.putExtra(Constants.TOPIC_NAME, "Electrical Safety");
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

                                                }
                                            }
                                        }

        );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
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
    private void initToolbars() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbarTop);

    }


}
