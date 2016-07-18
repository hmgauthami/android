package com.ascendlearning.jbl.uglys.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.models.Bending;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 3/31/2016.
 */
public class BendingAdapter extends ArrayAdapter<Bending> {
    ArrayList<Bending> bendingList;
    int resourceId;
    Context context;

    public BendingAdapter(Context context, int resource, ArrayList<Bending> bendingList) {
        super(context, resource, bendingList);
        this.context = context;
        this.resourceId = resource;
        this.bendingList = bendingList;
    }

    @Override
    public int getCount() {
        return bendingList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View vi = convertView;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            vi = inflater.inflate(resourceId, null);

            holder.image = (ImageView) vi.findViewById(R.id.image1);
            holder.bendingText = (TextView) vi.findViewById(R.id.bending_text_view);

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        TextUtil.insertText(holder.bendingText, bendingList.get(position).getBendingName());
        if (bendingList.get(position).getImageName().length() > 0) {
            int resourceId = context.getResources().getIdentifier(bendingList.get(position).getImageName(), "drawable", context.getPackageName());
            holder.image.setImageResource(resourceId);
        }
        return vi;
    }

    static class ViewHolder {
        ImageView image;
        TextView bendingText;
    }

}
