package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.adapters.CalculatorAdapter;
import com.ascendlearning.jbl.uglys.controllers.UICallback;
import com.ascendlearning.jbl.uglys.controllers.UglysController;
import com.ascendlearning.jbl.uglys.controllers.UglysResponse;
import com.ascendlearning.jbl.uglys.models.Calculators;
import com.ascendlearning.jbl.uglys.utils.CompositeKey;
import com.ascendlearning.jbl.uglys.utils.ESError;

import java.util.ArrayList;

public class CalculatorListActivity extends SuperActivity implements UICallback {
    private ArrayList<Calculators> calculatorList;
    private UglysController mController;
    private UglysResponse mResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_list);

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
        calculatorList = mResponse.getCalculatorList();
        makeLayout();
    }

    private void makeLayout() {
        ListView calculatorListView = (ListView) findViewById(R.id.calculators_listView);
        calculatorListView.setAdapter(new CalculatorAdapter(this, R.layout.calculators_row, calculatorList));
        calculatorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int type = calculatorList.get(position).getCalculatorType();
                Intent intent = null;
                switch (type) {
                    case 1:
                        intent = new Intent(CalculatorListActivity.this, OhmsLawCalculator.class);
                        break;
                    case 2:
                        intent = new Intent(CalculatorListActivity.this, ToFind1Calculator.class);
                        break;
                    case 3:
                        intent = new Intent(CalculatorListActivity.this, PFCorrectionCalculator.class);
                        break;
                    case 4:
                        intent = new Intent(CalculatorListActivity.this, FLCMotorCalculatorActivity.class);
                        break;
                    case 5:
                        intent = new Intent(CalculatorListActivity.this, FLCOtherDataCalculatorActivity.class);
                        break;
                    case 6:
                        intent = new Intent(CalculatorListActivity.this, LRCCalculator.class);
                        break;
                    case 7:
                        intent = new Intent(CalculatorListActivity.this, VoltageDropCalculatorActivity.class);
                        break;
                    case 8:
                        intent = new Intent(CalculatorListActivity.this, FLCTransformerCalculatorActivity.class);
                        break;
                    case 9:
                        intent = new Intent(CalculatorListActivity.this, SimpleAmpacityCalculator.class);
                        break;
                    case 10:
                        intent = new Intent(CalculatorListActivity.this, ConduitFillCalculator.class);
                        break;
                    case 11:
                        intent = new Intent(CalculatorListActivity.this, ConversionsActivity.class);
                        break;
                    case 12:
                        intent = new Intent(CalculatorListActivity.this, OffsetBendingActivity.class);
                        intent.putExtra("bendingName", "Offset Bending Calculator");
                        intent.putExtra("bendingType", 5);
                        break;
                    case 13:
                        intent = new Intent(CalculatorListActivity.this, ToFind2Calculator.class);
                        break;
                }
                if (intent != null) {
                    intent.putExtra("topic_name", calculatorList.get(position).getCalculatorName());
                    startActivity(intent);
                }
            }
        });
    }

    private void fetchJsonData() {
        mController = new UglysController();
        mController.setUICallBack(this);
        CompositeKey getCourseKey = new CompositeKey(UglysController.FILTER_CALCULATORS);
        mController.setCompositeKey(getCourseKey);
        mController.fetchData(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_others, menu);
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
        }  else if (id == R.id.action_home) {
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
}
