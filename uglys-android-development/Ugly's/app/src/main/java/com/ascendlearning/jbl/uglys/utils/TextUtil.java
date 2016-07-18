package com.ascendlearning.jbl.uglys.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ascendlearning.jbl.uglys.R;

import java.text.DecimalFormat;

public final class TextUtil {
    public static final String AdelleSans_BOLD = "fonts/AdelleSans_SemiBold_1.otf";
    public static final String AdelleSans_REGULAR = "fonts/TypeTogether - AdelleSans-Regular.otf";
    public static final String AdelleSans_ITALIC = "fonts/AdelleSans_LightItalic_0.otf";
    public static final String Helvetica_ITALIC = "fonts/FontAwesome_0.otf";
    public static final String Helvetica_REGULAR = "fonts/HelveticaLTStd-Roman_0.otf";
    public static final String TimesRoman_REGULAR = "fonts/tomnr.ttf";
    public static final String TimesRoman_BOLD = "fonts/Times New Roman Bold.ttf";
    private static Typeface adellesansBold = null;
    private static Typeface adellesansRegular = null;
    private static Typeface adellesansItalic = null;
    private static Typeface helveticaItalic = null;
    private static Typeface helveticaRegular = null;
    private static Typeface timesRegular = null;
    private static Typeface timesBold = null;

    private static boolean systemSupportsFonts = false;

    public static void loadFonts(AssetManager assetManager) {
        try {
            adellesansBold = Typeface.createFromAsset(assetManager, AdelleSans_BOLD);
            adellesansRegular = Typeface.createFromAsset(assetManager, AdelleSans_REGULAR);
            adellesansItalic = Typeface.createFromAsset(assetManager, AdelleSans_ITALIC);
            helveticaItalic = Typeface.createFromAsset(assetManager, Helvetica_ITALIC);
            helveticaRegular = Typeface.createFromAsset(assetManager, Helvetica_REGULAR);
            timesRegular = Typeface.createFromAsset(assetManager, TimesRoman_REGULAR);
            timesBold = Typeface.createFromAsset(assetManager, TimesRoman_BOLD);
            systemSupportsFonts = true;
        } catch (RuntimeException e) {
            systemSupportsFonts = false;
        }
    }

    public static void insertText(TextView textView, String text) {
        assert (textView != null);
        if (systemSupportsFonts) {
            textView.setTypeface(adellesansRegular);
        }
        textView.setText(text);
    }

    public static void insertText(RadioButton radioButton, String text) {
        assert (radioButton != null);
        if (systemSupportsFonts) {
            radioButton.setTypeface(adellesansRegular);
        }
        radioButton.setText(text);
    }

    public static void insertText(TextView textView, String text, int styleFlag) {
        if (textView == null) {
            return;
        }
        if (systemSupportsFonts) {
            if (styleFlag == Typeface.BOLD) {
                textView.setTypeface(adellesansBold);
            } else if (styleFlag == Typeface.ITALIC) {
                textView.setTypeface(adellesansItalic, Typeface.ITALIC);
            } else if (styleFlag == Typeface.BOLD_ITALIC) {
                textView.setTypeface(adellesansBold, Typeface.ITALIC);
            }
        }
        textView.setText(text);
    }

    public static void insertText1(TextView textView, String text, int styleFlag) {
        if (textView == null) {
            return;
        }
        if (systemSupportsFonts) {
            if (styleFlag == Typeface.BOLD) {
                textView.setTypeface(timesBold);
            } else {
                textView.setTypeface(timesRegular);

            }
        }
        textView.setText(text);
    }

    public static void insertText(TextView textView, String text, String fontName) {
        if (textView == null) {
            return;
        }
        if (systemSupportsFonts) {
            if (fontName.equalsIgnoreCase("H")) {
                textView.setTypeface(helveticaRegular);
            } else if (fontName.equalsIgnoreCase("T")) {
                textView.setTypeface(timesRegular);

            } else {
                textView.setTypeface(adellesansRegular);
            }
        }
        textView.setText(text);
    }

    public static int getRealFontSize(int fontSizeIndex) {
        int rs;
        switch (fontSizeIndex) {
            case 16:
                rs = 100;
                break;
            case 18:
                rs = 150;
                break;
            case 20:
                rs = 200;
                break;
            case 14:
                rs = 80;
                break;
            case 12:
                rs = 40;
                break;
            default:
                rs = 150;
        }

        return rs;
    }

    public static String convertToExponential(double value) {
        String formatted = "";
        String[] splitString = Double.toString(value).split("\\.");
        if (splitString[0].length() > Constants.MAX_LENGTH || splitString[1].length() > Constants.EDGE_CASE) {
            DecimalFormat df = new DecimalFormat("0.##E0");
            formatted = df.format(value);
        } else {
            formatted = String.format("%s", value);
        }

        return formatted;

    }


}
