package com.ascendlearning.jbl.uglys.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.models.Bookmarks;
import com.ascendlearning.jbl.uglys.models.Calculators;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 4/1/2016.
 */
public class CalculatorAdapter extends ArrayAdapter<Calculators> {

    ArrayList<Calculators> calculatorList;
    int resourceId;
    Context context;

    public CalculatorAdapter(Context context, int resource, ArrayList<Calculators> calculatorList) {
        super(context, resource, calculatorList);
        this.context = context;
        this.resourceId = resource;
        this.calculatorList = calculatorList;
    }

    @Override
    public int getCount() {
        return calculatorList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View vi = convertView;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            vi = inflater.inflate(resourceId, null);

            holder.calculatorName = (TextView) vi.findViewById(R.id.calculator_name);

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        TextUtil.insertText(holder.calculatorName, calculatorList.get(position).getCalculatorName());
        return vi;
    }

    static class ViewHolder {
        TextView calculatorName;
    }

}
