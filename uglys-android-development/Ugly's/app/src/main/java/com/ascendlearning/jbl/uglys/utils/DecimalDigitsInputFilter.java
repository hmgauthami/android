package com.ascendlearning.jbl.uglys.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sonal.agarwal on 6/3/2016.
 */
public class DecimalDigitsInputFilter implements InputFilter {

    int maxDigitsBeforeDecimalPoint;
    int maxDigitsAfterDecimalPoint;

    public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
        maxDigitsBeforeDecimalPoint = digitsBeforeZero;
        maxDigitsAfterDecimalPoint = digitsAfterZero;

    }

    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        StringBuilder builder = new StringBuilder(dest);
        builder.replace(dstart, dend, source
                .subSequence(start, end).toString());
        if (!builder.toString().matches(
                "(([0-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"

        )) {
            if (source.length() == 0) {
                return dest.subSequence(dstart, dend);
            }
            return "";
        }

        if (builder.toString().length() > 0 && builder.toString().charAt(start) == '.') {
            return "";
        }
        return null;

    }

}