package com.ascendlearning.jbl.uglys.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;
import com.ascendlearning.jbl.uglys.UglysApplication;
import com.ascendlearning.jbl.uglys.models.FontStyle;
import com.ascendlearning.jbl.uglys.utils.Constants;
import com.ascendlearning.jbl.uglys.utils.PrefrenceUtil;
import com.ascendlearning.jbl.uglys.utils.TextUtil;

import java.util.ArrayList;

/**
 * Created by sonal.agarwal on 5/2/2016.
 */
public class CustomFontAlertDialog extends Dialog {
    UglysApplication app;

    public CustomFontAlertDialog(Context context, int theme, UglysApplication app) {
        super(context, theme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.app = app;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Context context = getContext();

        // create custom dialog main layout and inflate view in that.
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.custom_font_dialog, null);

        final Button fontsize_minus_btn = (Button) layout.findViewById(R.id.fontsize_minus_btn);
        final Button fontsize_plus_btn = (Button) layout.findViewById(R.id.fontsize_plus_btn);
        final TextView tv_font_size_header = (TextView) layout.findViewById(R.id.tv_font_size_header);

        TextUtil.insertText(tv_font_size_header,context.getString(R.string.font_size));

        final int[] text = app.getFontSize();
        tv_font_size_header.setTextSize(text[0]);

        if (text[0] == Constants.MIN_FONT) {
            fontsize_plus_btn.setEnabled(true);
            fontsize_plus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
            fontsize_minus_btn.setEnabled(false);
            fontsize_minus_btn.setBackgroundResource(R.drawable.bg_fontsize_selected_btn);
        } else if (text[0] == Constants.MAX_FONT) {
            fontsize_minus_btn.setEnabled(true);
            fontsize_minus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
            fontsize_plus_btn.setEnabled(false);
            fontsize_plus_btn.setBackgroundResource(R.drawable.bg_fontsize_selected_btn);
        }else{
            fontsize_minus_btn.setEnabled(true);
            fontsize_minus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
            fontsize_plus_btn.setEnabled(true);
            fontsize_plus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
        }
        fontsize_minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text[0] >= Constants.MIN_FONT) {
                    fontsize_plus_btn.setEnabled(true);
                    fontsize_plus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
                    fontsize_minus_btn.setEnabled(true);
                    fontsize_minus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
                    text[0] = text[0] - 2;
                    if (text[0] == Constants.MIN_FONT) {
                        fontsize_minus_btn.setEnabled(false);
                        fontsize_minus_btn.setBackgroundResource(R.drawable.bg_fontsize_selected_btn);
                    }
                    app.setFontSize(text);
                    PrefrenceUtil.setString(Constants.FONT_SIZE, Integer.toString(text[0]));
                    tv_font_size_header.setTextSize(text[0]);
                } else {
                    fontsize_minus_btn.setBackgroundResource(R.drawable.bg_fontsize_selected_btn);
                    fontsize_minus_btn.setEnabled(false);
                }

            }
        });
        fontsize_plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text[0] <= Constants.MAX_FONT) {
                    fontsize_plus_btn.setEnabled(true);
                    fontsize_plus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
                    fontsize_minus_btn.setEnabled(true);
                    fontsize_minus_btn.setBackgroundResource(R.drawable.font_size_btn_pressed);
                    text[0] = text[0] + 2;
                    if (text[0] == Constants.MAX_FONT) {
                        fontsize_plus_btn.setEnabled(false);
                        fontsize_plus_btn.setBackgroundResource(R.drawable.bg_fontsize_selected_btn);
                    }
                    app.setFontSize(text);
                    PrefrenceUtil.setString(Constants.FONT_SIZE, Integer.toString(text[0]));
                    tv_font_size_header.setTextSize(text[0]);
                } else {
                    fontsize_plus_btn.setBackgroundResource(R.drawable.bg_fontsize_selected_btn);
                    fontsize_plus_btn.setEnabled(false);
                }

            }
        });
        setContentView(layout);

    }

}
