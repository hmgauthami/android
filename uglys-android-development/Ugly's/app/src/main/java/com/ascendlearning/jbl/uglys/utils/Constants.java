package com.ascendlearning.jbl.uglys.utils;

/**
 * Created by sonal.agarwal on 3/16/2016.
 */
public class Constants {
    public static final String PREF = "uglys_prefs";
    public static final String FIRST_LAUNCH = "FALSE";
    public static final int MIN_FONT = 12;
    public static final int MAX_FONT = 20;
    public static final String DEFAULT_FONT = "18";

    public enum BOOKMARK_CODE {
        OHMS, TOFIND1, TOFIND2, AMPACITY, BENDING, CONVERSIONS, PF_CORRECTION, CONDUIT_FILL, FLC_MOTOR, FLC_MOTOR_OTHER_DATA, FLC_TRANSFORMER, LRC, VOLTAGE_DROP, WIRING_DIAGRAMS, SYMBOLS
    }

    public enum BOOKMARK_TYPE {
        BOOK, OTHERS
    }

    public enum SEARCH_RESULT_TYPE {
        EPUB, CALCULATOR, INTERACTIVE
    }

    public static final String CONTAINER_ID = "container_id";
    public static final String TOPIC_NAME = "topic_name";
    public static final String OPEN_PAGE_REQUEST_DATA = "openPageRequestData";
    public static final String FONT_SIZE = "font_size";
    public static final int MAX_LENGTH = 4;
    public static final int EDGE_CASE = 6;
    public static final int MAX_DEC_LENGTH = 2;
    public static final int TIME_OUT = 2000;

}
