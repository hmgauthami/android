package com.ascendlearning.jbl.uglys.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.UglysApplication;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

/**
 * Created by sonal.agarwal on 5/19/2016.
 */
public class CalculatorInfoDialog extends Dialog {

    private String infoText;

    public CalculatorInfoDialog(Context context, int theme, String infoText) {
        super(context, theme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.infoText = infoText;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Context context = getContext();

        // create custom dialog main layout and inflate view in that.
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.calculator_info_dialog, null);

        TextView cal_info_text = (TextView) layout.findViewById(R.id.cal_info_text);
        TextUtil.insertText(cal_info_text, infoText);

        ImageView cancel_imageview = (ImageView) layout.findViewById(R.id.cancel_imageview);
        cancel_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setContentView(layout);
    }
}
