package com.ascendlearning.jbl.uglys.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.UglysApplication;
import com.ascendlearning.jbl.uglys.models.SubTopics;
import com.ascendlearning.jbl.uglys.models.Topics;
import com.ascendlearning.jbl.uglys.utils.TextUtil;
import com.ascendlearning.jbl.uglys.views.CustomExpandableListView;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 3/28/2016.
 */
public class TopicsExpandableAdapter extends BaseExpandableListAdapter {
    private ArrayList<Topics> parentItems;
    private Context context;
    private LayoutInflater inflater;
    private SubTopics child;
    private UglysApplication app;

    public TopicsExpandableAdapter(Context context, ArrayList<Topics> data, UglysApplication app) {
        this.parentItems = data;
        this.context = context;
        this.app = app;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        child = parentItems.get(groupPosition).getSubTopicsArrayList().get(childPosition);

        final CustomExpandableListView secondLevelexplv = new CustomExpandableListView(context);
        secondLevelexplv.setGroupIndicator(null);
        secondLevelexplv.setDividerHeight(2);
        secondLevelexplv.setClickable(true);
        SecondLevelTopicsExpandableAdapter adapter = new SecondLevelTopicsExpandableAdapter(context, child, app);
        secondLevelexplv.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++)
            secondLevelexplv.expandGroup(i);
        secondLevelexplv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId) {
                secondLevelexplv.expandGroup(itemPosition);
                return true;
            }
        });
        return secondLevelexplv;
    }

    static class ViewHolder {
        TextView subTopicName;
        ImageView image;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View vi = convertView;
        if (vi == null) {
            holder = new ViewHolder();
            vi = inflater.inflate(R.layout.topics_group, null);
            holder.subTopicName = (TextView) vi.findViewById(R.id.group_name);
            holder.image = (ImageView) vi.findViewById(R.id.accordian);

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        TextUtil.insertText(holder.subTopicName, getGroup(groupPosition).getTopicName());
        holder.image.setImageResource(R.drawable.accordion_plus);
        if (isExpanded) {
            holder.image.setImageResource(R.drawable.accordion_minus);

        }
        return vi;
    }

    @Override
    public SubTopics getChild(int groupPosition, int childPosition) {
        return parentItems.get(groupPosition).getSubTopicsArrayList().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<SubTopics> chList = parentItems.get(groupPosition).getSubTopicsArrayList();
        return chList.size();
    }

    @Override
    public Topics getGroup(int groupPosition) {
        return parentItems.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
