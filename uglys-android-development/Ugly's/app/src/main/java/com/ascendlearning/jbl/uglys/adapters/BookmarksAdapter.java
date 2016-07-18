package com.ascendlearning.jbl.uglys.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import org.readium.sdk.android.launcher.model.Bookmark;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonal.agarwal on 3/31/2016.
 */
public class BookmarksAdapter extends ArrayAdapter<Bookmarks> {
    List<Bookmarks> bookmarkList;
    int resourceId;
    Context context;

    public BookmarksAdapter(Context context, int resource, List<Bookmarks> bookmarkList) {
        super(context, resource, bookmarkList);
        this.context = context;
        this.resourceId = resource;
        this.bookmarkList = bookmarkList;
    }

    @Override
    public int getCount() {
        return bookmarkList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View vi = convertView;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            vi = inflater.inflate(R.layout.bookmarks_row, null);

            holder.bookmarkName = (TextView) vi.findViewById(R.id.bookmark_name);

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        TextUtil.insertText(holder.bookmarkName, bookmarkList.get(position).getBookmarkName());
        return vi;
    }

    static class ViewHolder {
        TextView bookmarkName;
    }

}
