package com.ascendlearning.jbl.uglys.views;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;

/**
 * Created by sonal.agarwal on 3/29/2016.
 */
public class CustomExpandableListView extends ExpandableListView {

    public CustomExpandableListView(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(5000, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
