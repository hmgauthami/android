package com.ascendlearning.jbl.uglys.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.models.MainMenu;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 3/18/2016.
 */
public class MainScreenAdapter extends ArrayAdapter<MainMenu> {
    private Context context;
    private final ArrayList<MainMenu> mainScreenValues;

    public MainScreenAdapter(Context context, int textViewResourceId, ArrayList<MainMenu> mainScreenValues) {
        super(context, textViewResourceId, mainScreenValues);
        this.context = context;
        this.mainScreenValues = mainScreenValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View vi = convertView;
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            // get layout from activity_main_gridview.xml
            vi = inflater.inflate(R.layout.activity_main_gridview, null);

            holder.mainMenuTitle = (TextView) vi.findViewById(R.id.grid_item_label);
            holder.mainMenuImage = (ImageView) vi.findViewById(R.id.grid_item_image);
            vi.setTag(holder);

        } else {
            holder = (ViewHolder) vi.getTag();
        }
        TextUtil.insertText(holder.mainMenuTitle, mainScreenValues.get(position).getTitle());
        holder.mainMenuImage.setImageResource(mainScreenValues.get(position).getImageResourceId());
        return vi;
    }

    @Override
    public int getCount() {
        return mainScreenValues.size();
    }

    static class ViewHolder {

        ImageView mainMenuImage;
        TextView mainMenuTitle;

    }

}
