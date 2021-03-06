package com.ascendlearning.jbl.uglys.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.UglysApplication;
import com.ascendlearning.jbl.uglys.activities.SimpleAmpacityCalculator;
import com.ascendlearning.jbl.uglys.activities.BendingDetailsActivity;
import com.ascendlearning.jbl.uglys.activities.BookViewActivity;
import com.ascendlearning.jbl.uglys.activities.ConduitFillCalculator;
import com.ascendlearning.jbl.uglys.activities.ConversionsActivity;
import com.ascendlearning.jbl.uglys.activities.FLCMotorCalculatorActivity;
import com.ascendlearning.jbl.uglys.activities.FLCOtherDataCalculatorActivity;
import com.ascendlearning.jbl.uglys.activities.FLCTransformerCalculatorActivity;
import com.ascendlearning.jbl.uglys.activities.LRCCalculator;
import com.ascendlearning.jbl.uglys.activities.OffsetBendingActivity;
import com.ascendlearning.jbl.uglys.activities.OhmsLawCalculator;
import com.ascendlearning.jbl.uglys.activities.PFCorrectionCalculator;
import com.ascendlearning.jbl.uglys.activities.SymbolsActivity;
import com.ascendlearning.jbl.uglys.activities.ToFind1Calculator;
import com.ascendlearning.jbl.uglys.activities.ToFind2Calculator;
import com.ascendlearning.jbl.uglys.activities.VoltageDropCalculatorActivity;
import com.ascendlearning.jbl.uglys.activities.WiringDiagramsActivity;
import com.ascendlearning.jbl.uglys.models.SubTopics;
import com.ascendlearning.jbl.uglys.models.SubsubTopics;
import com.ascendlearning.jbl.uglys.utils.Constants;
import com.ascendlearning.jbl.uglys.utils.TextUtil;
import com.ascendlearning.jbl.uglys.views.CustomExpandableListView;

import org.json.JSONException;
import org.readium.sdk.android.components.navigation.NavigationElement;
import org.readium.sdk.android.components.navigation.NavigationPoint;
import org.readium.sdk.android.launcher.model.OpenPageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonal.agarwal on 3/28/2016.
 */
public class SecondLevelTopicsExpandableAdapter extends BaseExpandableListAdapter {
    private SubTopics parentItems;
    private Context context;
    private LayoutInflater inflater;
    private SubsubTopics child;
    private UglysApplication app;

    public SecondLevelTopicsExpandableAdapter(Context context, SubTopics data, UglysApplication app) {
        this.parentItems = data;
        this.context = context;
        this.app = app;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        child = getChild(groupPosition, childPosition);

        final CustomExpandableListView thirdLevelexplv = new CustomExpandableListView(context);
        thirdLevelexplv.setGroupIndicator(null);
        thirdLevelexplv.setDividerHeight(2);
        thirdLevelexplv.setClickable(true);
        ThirdLevelTopicsExpandableAdapter adapter = new ThirdLevelTopicsExpandableAdapter(context, child, app);
        thirdLevelexplv.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++)
            thirdLevelexplv.expandGroup(i);
        thirdLevelexplv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId) {
                thirdLevelexplv.expandGroup(itemPosition);
                return true;
            }
        });
        return thirdLevelexplv;
    }

    static class ViewHolder {
        TextView subTopicName;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View vi = convertView;
        if (vi == null) {
            holder = new ViewHolder();
            vi = inflater.inflate(R.layout.topics_row, null);
            holder.subTopicName = (TextView) vi.findViewById(R.id.sub_topic_textView);

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        TextUtil.insertText(holder.subTopicName, getGroup(groupPosition).getSubTopicName());
        holder.subTopicName.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       if (getGroup(groupPosition).getTypeOfContent() == null) {
                                                           return;
                                                       }
                                                       if (getGroup(groupPosition).getTypeOfContent().equalsIgnoreCase(context.getString(R.string.epub))) {
                                                           final List<NavigationElement> navigationElements = app.flatNavigationTable(app.getNavigationTable(), new ArrayList<NavigationElement>());
                                                           for (int i = 0; i < navigationElements.size(); i++) {
                                                               if (navigationElements.get(i).getTitle().equalsIgnoreCase(getGroup(groupPosition).getSubTopicName())) {
                                                                   NavigationElement navigation = navigationElements.get(i);
                                                                   if (navigation instanceof NavigationPoint) {
                                                                       NavigationPoint point = (NavigationPoint) navigation;
                                                                       Intent intent = new Intent(context, BookViewActivity.class);
                                                                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                       intent.putExtra(Constants.CONTAINER_ID, app.container.getNativePtr());
                                                                       intent.putExtra(Constants.TOPIC_NAME, getGroup(groupPosition).getSubTopicName());
                                                                       OpenPageRequest openPageRequest = OpenPageRequest.fromContentUrl(point.getContent(), app.navigationTable.getSourceHref());
                                                                       try {
                                                                           intent.putExtra(Constants.OPEN_PAGE_REQUEST_DATA, openPageRequest.toJSON().toString());
                                                                           context.startActivity(intent);
                                                                           break;
                                                                       } catch (JSONException e) {
                                                                       }
                                                                   }
                                                               }
                                                           }
                                                       } else if (getGroup(groupPosition).getTypeOfContent().equalsIgnoreCase(context.getString(R.string.calculator))) {
                                                           String calculator_id = getGroup(groupPosition).getGetTypeOfCalculator();
                                                           Intent intent = null;
                                                           switch (calculator_id) {
                                                               case "1":
                                                                   intent = new Intent(context, OhmsLawCalculator.class);
                                                                   break;
                                                               case "2":
                                                                   intent = new Intent(context, ToFind1Calculator.class);
                                                                   switch (getGroup(groupPosition).getSub_calculator_id()) {
                                                                       case 1:
                                                                           intent.putExtra("subtype", 0);
                                                                           intent.putExtra("knownVal", 0);
                                                                           intent.putExtra("currentType", 0);
                                                                           break;
                                                                       case 2:
                                                                           intent.putExtra("subtype", 0);
                                                                           intent.putExtra("knownVal", 0);
                                                                           intent.putExtra("currentType", 1);
                                                                           break;
                                                                       case 3:
                                                                           intent.putExtra("subtype", 0);
                                                                           intent.putExtra("knownVal", 0);
                                                                           intent.putExtra("currentType", 2);
                                                                           break;
                                                                       case 4:
                                                                           intent.putExtra("subtype", 1);
                                                                           intent.putExtra("currentType", 0);
                                                                           break;
                                                                       case 5:
                                                                           intent.putExtra("subtype", 1);
                                                                           intent.putExtra("currentType", 1);
                                                                           break;
                                                                       case 6:
                                                                           intent.putExtra("subtype", 1);
                                                                           intent.putExtra("currentType", 2);
                                                                           break;
                                                                       case 7:
                                                                           intent.putExtra("subtype", 2);
                                                                           break;
                                                                       case 8:
                                                                           intent.putExtra("subtype", 3);
                                                                           intent.putExtra("currentType", 0);
                                                                           break;
                                                                       case 9:
                                                                           intent.putExtra("subtype", 3);
                                                                           intent.putExtra("currentType", 1);
                                                                           break;
                                                                       case 10:
                                                                           intent.putExtra("subtype", 3);
                                                                           intent.putExtra("currentType", 2);
                                                                           break;
                                                                       case 11:
                                                                           intent.putExtra("subtype", 4);
                                                                           intent.putExtra("currentType", 0);
                                                                           break;
                                                                       case 12:
                                                                           intent.putExtra("subtype", 4);
                                                                           intent.putExtra("currentType", 1);
                                                                           break;
                                                                       case 13:
                                                                           intent = new Intent(context, ToFind2Calculator.class);
                                                                           intent.putExtra("subtype", 0);
                                                                           break;
                                                                       case 14:
                                                                           intent = new Intent(context, ToFind2Calculator.class);
                                                                           intent.putExtra("subtype", 1);
                                                                           intent.putExtra("knownVal", 0);
                                                                           break;
                                                                       case 15:
                                                                           intent = new Intent(context, ToFind2Calculator.class);
                                                                           intent.putExtra("subtype", 2);
                                                                           intent.putExtra("knownVal", 0);
                                                                           break;
                                                                   }
                                                                   break;
                                                               case "9":
                                                                   intent = new Intent(context, SimpleAmpacityCalculator.class);
                                                                   switch (getGroup(groupPosition).getSub_calculator_id()) {
                                                                       case 1:
                                                                           intent.putExtra("AmpacityTemp", 0);
                                                                           intent.putExtra("AmpacityLoc", 0);
                                                                           break;
                                                                       case 2:
                                                                           intent.putExtra("AmpacityTemp", 0);
                                                                           intent.putExtra("AmpacityLoc", 1);
                                                                           break;
                                                                       case 3:
                                                                           intent.putExtra("AmpacityTemp", 1);
                                                                           intent.putExtra("AmpacityLoc", 0);
                                                                           break;
                                                                       case 4:
                                                                           intent.putExtra("AmpacityTemp", 1);
                                                                           intent.putExtra("AmpacityLoc", 1);
                                                                           break;
                                                                   }

                                                                   break;
                                                               case "3":
                                                                   intent = new Intent(context, PFCorrectionCalculator.class);
                                                                   break;
                                                               case "4":
                                                                   intent = new Intent(context, FLCMotorCalculatorActivity.class);
                                                                   intent.putExtra("flcType", getGroup(groupPosition).getSub_calculator_id());
                                                                   break;
                                                               case "5":
                                                                   intent = new Intent(context, FLCOtherDataCalculatorActivity.class);
                                                                   break;
                                                               case "6":
                                                                   intent = new Intent(context, LRCCalculator.class);
                                                                   break;
                                                               case "7":
                                                                   intent = new Intent(context, VoltageDropCalculatorActivity.class);
                                                                   break;
                                                               case "8":
                                                                   intent = new Intent(context, FLCTransformerCalculatorActivity.class);
                                                                   break;
                                                               case "10":
                                                                   intent = new Intent(context, ConduitFillCalculator.class);
                                                                   intent.putExtra("conduitType", getGroup(groupPosition).getSub_calculator_id());
                                                                   break;
                                                               case "11":
                                                                   intent = new Intent(context, ConversionsActivity.class);
                                                                   break;
                                                               case "12":
                                                                   intent = new Intent(context, OffsetBendingActivity.class);
                                                                   intent.putExtra("bendingName", "Offset Bending Calculator");
                                                                   intent.putExtra("bendingType", 5);
                                                                   break;
                                                           }
                                                           if (intent != null) {
                                                               intent.putExtra("topic_name", getGroup(groupPosition).getSubTopicName());
                                                               context.startActivity(intent);
                                                           }
                                                       } else if (getGroup(groupPosition).getTypeOfContent().equalsIgnoreCase(context.getString(R.string.interactive))) {
                                                           String calculator_id = getGroup(groupPosition).getGetTypeOfCalculator();
                                                           if (calculator_id != null) {
                                                               Intent intent = null;
                                                               if (calculator_id.equalsIgnoreCase("5")) {
                                                                   intent = new Intent(context, OffsetBendingActivity.class);
                                                                   intent.putExtra("bendingType", Integer.parseInt(calculator_id));
                                                                   intent.putExtra("bendingName", getGroup(groupPosition).getSubTopicName());
                                                                   context.startActivity(intent);
                                                               } else if (calculator_id.equalsIgnoreCase("12")) {
                                                                   intent = new Intent(context, WiringDiagramsActivity.class);
                                                                   context.startActivity(intent);
                                                               } else if (calculator_id.equalsIgnoreCase("13")) {
                                                                   intent = new Intent(context, SymbolsActivity.class);
                                                                   context.startActivity(intent);
                                                               } else {
                                                                   intent = new Intent(context, BendingDetailsActivity.class);
                                                                   intent.putExtra("bendingType", Integer.parseInt(calculator_id));
                                                                   intent.putExtra("bendingName", getGroup(groupPosition).getSubTopicName());
                                                                   context.startActivity(intent);
                                                               }

                                                           }
                                                       }
                                                   }
                                               }

        );
        return vi;
    }


    @Override
    public SubsubTopics getChild(int groupPosition, int childPosition) {
        return parentItems.getSubSubTopicsArrayList().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (parentItems.getSubSubTopicsArrayList() == null) {
            return 0;
        }
        ArrayList<SubsubTopics> chList = parentItems.getSubSubTopicsArrayList();
        return chList.size();
    }

    @Override
    public SubTopics getGroup(int groupPosition) {
        return parentItems;
    }

    @Override
    public int getGroupCount() {
        return 1;
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
