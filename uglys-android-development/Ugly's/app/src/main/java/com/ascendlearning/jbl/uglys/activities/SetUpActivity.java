package com.ascendlearning.jbl.uglys.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.utils.Constants;
import com.ascendlearning.jbl.uglys.utils.PrefrenceUtil;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

public class SetUpActivity extends SuperActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        initToolbars();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        makeLayout();
    }

    private void makeLayout() {
        LinearLayout arrowView1 = (LinearLayout) findViewById(R.id.next1);
        LinearLayout arrowView2 = (LinearLayout) findViewById(R.id.next2);
        LinearLayout arrowView3 = (LinearLayout) findViewById(R.id.next3);
        LinearLayout arrowView4 = (LinearLayout) findViewById(R.id.next4);

        final LinearLayout layout1 = (LinearLayout) findViewById(R.id.setup_view1);
        final LinearLayout layout2 = (LinearLayout) findViewById(R.id.setup_view2);
        final LinearLayout layout3 = (LinearLayout) findViewById(R.id.setup_view3);
        final LinearLayout layout4 = (LinearLayout) findViewById(R.id.setup_view4);

        TextView next_text_view1 = (TextView) findViewById(R.id.next_text_view1);
        TextUtil.insertText(next_text_view1, getString(R.string.next), Typeface.BOLD);
        TextView next_text_view2 = (TextView) findViewById(R.id.next_text_view2);
        TextUtil.insertText(next_text_view2, getString(R.string.next), Typeface.BOLD);
        TextView next_text_view3 = (TextView) findViewById(R.id.next_text_view3);
        TextUtil.insertText(next_text_view3, getString(R.string.next), Typeface.BOLD);
        TextView next_text_view4 = (TextView) findViewById(R.id.next_text_view4);
        TextUtil.insertText(next_text_view4, getString(R.string.exit), Typeface.BOLD);

        arrowView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout1.setVisibility(View.INVISIBLE);
                layout2.setVisibility(View.VISIBLE);
            }
        });
        arrowView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout2.setVisibility(View.INVISIBLE);
                layout3.setVisibility(View.VISIBLE);
            }
        });
        arrowView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout3.setVisibility(View.INVISIBLE);
                layout4.setVisibility(View.VISIBLE);
            }
        });
        arrowView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PrefrenceUtil.contains(Constants.FIRST_LAUNCH)) {
                    PrefrenceUtil.setString(Constants.FIRST_LAUNCH, "TRUE");
                }
                Intent i = new Intent(SetUpActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculators, menu);
        return true;
    }
    private void initToolbars() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbarTop);
    }


}
