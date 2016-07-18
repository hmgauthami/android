package com.ascendlearning.jbl.uglys.adapters;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.models.SearchResults;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 5/18/2016.
 */
public class SearchResultsAdapter extends ArrayAdapter<SearchResults> {
    private ArrayList<SearchResults> searchResults, suggestions, tempItems;
    private int resourceId;
    private Context context;
    private String searchVal;

    public SearchResultsAdapter(Context context, int resource, ArrayList<SearchResults> searchResults, String searchVal) {
        super(context, resource, searchResults);
        this.context = context;
        this.resourceId = resource;
        this.searchResults = searchResults;
        this.searchVal = searchVal;
        //tempItems = new ArrayList<>(searchResults);
        //suggestions = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return searchResults.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View vi = convertView;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            vi = inflater.inflate(resourceId, null);

            holder.resultName = (TextView) vi.findViewById(R.id.search_text);

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        int indexStart = searchResults.get(position).getTopicName().toLowerCase().indexOf(searchVal.toLowerCase());
        int indexEnd = indexStart + searchVal.length();
        if (indexStart != -1) {
            Spannable wordtoSpan = new SpannableString(searchResults.get(position).getTopicName());
            wordtoSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.yellow)), indexStart, indexEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.resultName.setText(wordtoSpan);
        } else {
            TextUtil.insertText(holder.resultName, searchResults.get(position).getTopicName());
        }

        return vi;
    }

    static class ViewHolder {
        TextView resultName;
    }

}
