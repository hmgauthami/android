package com.ascendlearning.jbl.uglys.utils;

import java.text.DecimalFormat;

/**
 * Created by sonal.agarwal on 5/19/2016.
 */
public class Conversion {


    public enum CONVERSION_WEIGHT {
        MG, CG, DCG, G, DKG, HG, KG, TON, CARET, NEWTON, OUNCE, LB, STONE
    }

    public enum CONVERSION_TEMPERATURE {
        CELSIUS, FAHRENHEIT
    }

    public enum CONVERSION_DISTANCE {
        MM, CM, DCM, M, DKM, HM, KM, YARD, PARSEC, MILE, FEET, FATHOM, INCH, LEAGUE
    }

    public enum CONVERSION_AREA {
        FOOT, YARD, M, KM, MILE, ACRE, HT
    }

    public enum CONVERSION_VOLUME {
        MIL, ML, CL, L, DL, HL, KL, GILL, GALLON, PINT, QUART
    }

    public enum CONVERSION_TIME {
        NANO, MICRO, MILLI, CENTI, SEC, MIN, HR, DAY, WEEK, FORT, MONTH, YR, DECADE, CENTUARY, MILL
    }

    public String convertTime(int from, int to, double input) {
        CONVERSION_TIME conversionFrom = CONVERSION_TIME.values()[from];
        CONVERSION_TIME conversionTo = CONVERSION_TIME.values()[to];
        double ret = 0;

        switch (conversionFrom) {
            case NANO:
                switch (conversionTo) {
                    case MICRO:
                        ret = input * 0.001;
                        break;
                    case MILLI:
                        ret = Math.pow(10, -6) * input;
                        break;
                    case CENTI:
                        ret = Math.pow(10, -8) * input;
                        break;
                    case SEC:
                        ret = Math.pow(10, -9) * input;
                        break;
                    case MIN:
                        ret = 1.6 * Math.pow(10, -11) * input;
                        break;
                    case HR:
                        ret = 2.7 * Math.pow(10, -13) * input;
                        break;
                    case DAY:
                        ret = 1.15 * Math.pow(10, -14) * input;
                        break;
                    case WEEK:
                        ret = 1.65 * Math.pow(10, -15) * input;
                        break;
                    case FORT:
                        ret = 8.2 * Math.pow(10, -16) * input;
                        break;
                    case MONTH:
                        ret = 3.8 * Math.pow(10, -16) * input;
                        break;
                    case YR:
                        ret = 3.17 * Math.pow(10, -17) * input;
                        break;
                    case DECADE:
                        ret = 3.17 * Math.pow(10, -18) * input;
                        break;
                    case CENTUARY:
                        ret = 3.17 * Math.pow(10, -19) * input;
                        break;
                    case MILL:
                        ret = 3.17 * Math.pow(10, -20) * input;
                        break;
                }
                break;
            case MICRO:
                switch (conversionTo) {
                    case NANO:
                        ret = Math.pow(10, 3) * input;
                        break;
                    case MILLI:
                        ret = 0.001 * input;
                        break;
                    case CENTI:
                        ret = Math.pow(10, -5) * input;
                        break;
                    case SEC:
                        ret = Math.pow(10, -6) * input;
                        break;
                    case MIN:
                        ret = 1.66 * Math.pow(10, -8) * input;
                        break;
                    case HR:
                        ret = 2.77 * Math.pow(10, -10) * input;
                        break;
                    case DAY:
                        ret = 1.15 * Math.pow(10, -11) * input;
                        break;
                    case WEEK:
                        ret = 1.65 * Math.pow(10, -12) * input;
                        break;
                    case FORT:
                        ret = 8.2 * Math.pow(10, -13) * input;
                        break;
                    case MONTH:
                        ret = 3.8 * Math.pow(10, -13) * input;
                        break;
                    case YR:
                        ret = 3.17 * Math.pow(10, -14) * input;
                        break;
                    case DECADE:
                        ret = 3.17 * Math.pow(10, -15) * input;
                        break;
                    case CENTUARY:
                        ret = 3.17 * Math.pow(10, -16) * input;
                        break;
                    case MILL:
                        ret = 3.17 * Math.pow(10, -17) * input;
                        break;
                }
                break;
            case MILLI:
                switch (conversionTo) {
                    case MICRO:
                        ret = 1000 * input;
                        break;
                    case NANO:
                        ret = Math.pow(10, 6) * input;
                        break;
                    case CENTI:
                        ret = 0.01 * input;
                        break;
                    case SEC:
                        ret = 0.001 * input;
                        break;
                    case MIN:
                        ret = 1.66 * Math.pow(10, -5) * input;
                        break;
                    case HR:
                        ret = 2.77 * Math.pow(10, -7) * input;
                        break;
                    case DAY:
                        ret = 1.15 * Math.pow(10, -8) * input;
                        break;
                    case WEEK:
                        ret = 1.65 * Math.pow(10, -9) * input;
                        break;
                    case FORT:
                        ret = 8.2 * Math.pow(10, -10) * input;
                        break;
                    case MONTH:
                        ret = 3.8 * Math.pow(10, -10) * input;
                        break;
                    case YR:
                        ret = 3.17 * Math.pow(10, -11) * input;
                        break;
                    case DECADE:
                        ret = 3.17 * Math.pow(10, -12) * input;
                        break;
                    case CENTUARY:
                        ret = 3.17 * Math.pow(10, -13) * input;
                        break;
                    case MILL:
                        ret = 3.17 * Math.pow(10, -14) * input;
                        break;
                }
                break;
            case CENTI:
                switch (conversionTo) {
                    case MICRO:
                        ret = 10000 * input;
                        break;
                    case NANO:
                        ret = Math.pow(10, 7) * input;
                        break;
                    case MILLI:
                        ret = 0.1 * input;
                        break;
                    case SEC:
                        ret = 0.01 * input;
                        break;
                    case MIN:
                        ret = 1.66 * Math.pow(10, -4) * input;
                        break;
                    case HR:
                        ret = 2.77 * Math.pow(10, -6) * input;
                        break;
                    case DAY:
                        ret = 1.15 * Math.pow(10, -7) * input;
                        break;
                    case WEEK:
                        ret = 1.65 * Math.pow(10, -8) * input;
                        break;
                    case FORT:
                        ret = 8.2 * Math.pow(10, -9) * input;
                        break;
                    case MONTH:
                        ret = 3.8 * Math.pow(10, -9) * input;
                        break;
                    case YR:
                        ret = 3.17 * Math.pow(10, -10) * input;
                        break;
                    case DECADE:
                        ret = 3.17 * Math.pow(10, -11) * input;
                        break;
                    case CENTUARY:
                        ret = 3.17 * Math.pow(10, -12) * input;
                        break;
                    case MILL:
                        ret = 3.17 * Math.pow(10, -13) * input;
                        break;
                }
                break;
            case SEC:
                switch (conversionTo) {
                    case MICRO:
                        ret = 1000000 * input;
                        break;
                    case NANO:
                        ret = Math.pow(10, 9) * input;
                        break;
                    case CENTI:
                        ret = 10 * input;
                        break;
                    case MILLI:
                        ret = 1000 * input;
                        break;
                    case MIN:
                        ret = 0.01666668 * input;
                        break;
                    case HR:
                        ret = 0.000277778 * input;
                        break;
                    case DAY:
                        ret = 1.15 * Math.pow(10, -5) * input;
                        break;
                    case WEEK:
                        ret = 1.65 * Math.pow(10, -6) * input;
                        break;
                    case FORT:
                        ret = 8.2 * Math.pow(10, -7) * input;
                        break;
                    case MONTH:
                        ret = 3.8 * Math.pow(10, -7) * input;
                        break;
                    case YR:
                        ret = 3.17 * Math.pow(10, -8) * input;
                        break;
                    case DECADE:
                        ret = 3.17 * Math.pow(10, -9) * input;
                        break;
                    case CENTUARY:
                        ret = 3.17 * Math.pow(10, -10) * input;
                        break;
                    case MILL:
                        ret = 3.17 * Math.pow(10, -11) * input;
                        break;
                }
                break;
            case MIN:
                switch (conversionTo) {
                    case MICRO:
                        ret = 6 * Math.pow(10, 7) * input;
                        break;
                    case MILLI:
                        ret = 60000 * input;
                        break;
                    case CENTI:
                        ret = 6000 * input;
                        break;
                    case SEC:
                        ret = 60 * input;
                        break;
                    case NANO:
                        ret = Math.pow(10, 10) * input;
                        break;
                    case HR:
                        ret = 0.0166667 * input;
                        break;
                    case DAY:
                        ret = 0.000694444 * input;
                        break;
                    case WEEK:
                        ret = 5.5 * Math.pow(10, -5) * input;
                        break;
                    case FORT:
                        ret = 4.9 * Math.pow(10, -5) * input;
                        break;
                    case MONTH:
                        ret = 2.2 * Math.pow(10, -5) * input;
                        break;
                    case YR:
                        ret = 1.92 * Math.pow(10, -6) * input;
                        break;
                    case DECADE:
                        ret = 1.92 * Math.pow(10, -7) * input;
                        break;
                    case CENTUARY:
                        ret = 1.92 * Math.pow(10, -8) * input;
                        break;
                    case MILL:
                        ret = 1.92 * Math.pow(10, -9) * input;
                        break;
                }
                break;
            case HR:
                switch (conversionTo) {
                    case MICRO:
                        ret = 3.6 * Math.pow(10, 9) * input;
                        break;
                    case MILLI:
                        ret = 3.6 * Math.pow(10, 6) * input;
                        break;
                    case CENTI:
                        ret = 360000 * input;
                        break;
                    case SEC:
                        ret = 3600 * input;
                        break;
                    case MIN:
                        ret = 60 * input;
                        break;
                    case NANO:
                        ret = 3.6 * Math.pow(10, 12) * input;
                        break;
                    case DAY:
                        ret = 0.0416667 * input;
                        break;
                    case WEEK:
                        ret = 0.00595238 * input;
                        break;
                    case FORT:
                        ret = 0.00297619 * input;
                        break;
                    case MONTH:
                        ret = 0.00136986 * input;
                        break;
                    case YR:
                        ret = 0.000114155 * input;
                        break;
                    case DECADE:
                        ret = 1.14 * Math.pow(10, -5) * input;
                        break;
                    case CENTUARY:
                        ret = 1.14 * Math.pow(10, -6) * input;
                        break;
                    case MILL:
                        ret = 1.14 * Math.pow(10, -7) * input;
                        break;
                }
                break;
            case DAY:
                switch (conversionTo) {
                    case MICRO:
                        ret = 8.64 * Math.pow(10, 10) * input;
                        break;
                    case MILLI:
                        ret = 8.64 * Math.pow(10, 7) * input;
                        break;
                    case CENTI:
                        ret = 8.64 * Math.pow(10, 6) * input;
                        break;
                    case SEC:
                        ret = 86400 * input;
                        break;
                    case MIN:
                        ret = 1440 * input;
                        break;
                    case HR:
                        ret = 24 * input;
                        break;
                    case NANO:
                        ret = 8.6 * Math.pow(10, 13) * input;
                        break;
                    case WEEK:
                        ret = 0.142857 * input;
                        break;
                    case FORT:
                        ret = 0.0714286 * input;
                        break;
                    case MONTH:
                        ret = 0.0328767 * input;
                        break;
                    case YR:
                        ret = 0.00273973 * input;
                        break;
                    case DECADE:
                        ret = 0.000273973 * input;
                        break;
                    case CENTUARY:
                        ret = 2.7 * Math.pow(10, -5) * input;
                        break;
                    case MILL:
                        ret = 2.7 * Math.pow(10, -6) * input;
                        break;
                }
                break;
            case WEEK:
                switch (conversionTo) {
                    case MICRO:
                        ret = 6.04 * Math.pow(10, 11) * input;
                        break;
                    case MILLI:
                        ret = 6.04 * Math.pow(10, 8) * input;
                        break;
                    case CENTI:
                        ret = 6.04 * Math.pow(10, 7) * input;
                        break;
                    case SEC:
                        ret = 604800 * input;
                        break;
                    case MIN:
                        ret = 10080 * input;
                        break;
                    case HR:
                        ret = 168 * input;
                        break;
                    case DAY:
                        ret = 7 * input;
                        break;
                    case NANO:
                        ret = 6.04 * Math.pow(10, 14) * input;
                        break;
                    case FORT:
                        ret = 0.5 * input;
                        break;
                    case MONTH:
                        ret = 0.230137 * input;
                        break;
                    case YR:
                        ret = 0.0191781 * input;
                        break;
                    case DECADE:
                        ret = 0.00191781 * input;
                        break;
                    case CENTUARY:
                        ret = 0.000191781 * input;
                        break;
                    case MILL:
                        ret = 1.91 * Math.pow(10, -5) * input;
                        break;
                }
                break;
            case FORT:
                switch (conversionTo) {
                    case MICRO:
                        ret = 1.21 * Math.pow(10, 12) * input;
                        break;
                    case MILLI:
                        ret = 1.21 * Math.pow(10, 9) * input;
                        break;
                    case CENTI:
                        ret = 1.21 * Math.pow(10, 8) * input;
                        break;
                    case SEC:
                        ret = 1.21 * Math.pow(10, 6) * input;
                        break;
                    case MIN:
                        ret = 20160 * input;
                        break;
                    case HR:
                        ret = 336 * input;
                        break;
                    case DAY:
                        ret = 14 * input;
                        break;
                    case WEEK:
                        ret = 2 * input;
                        break;
                    case NANO:
                        ret = 1.21 * Math.pow(10, 15) * input;
                        break;
                    case MONTH:
                        ret = 0.460273 * input;
                        break;
                    case YR:
                        ret = 0.0383562 * input;
                        break;
                    case DECADE:
                        ret = 0.00383562 * input;
                        break;
                    case CENTUARY:
                        ret = 0.000383562 * input;
                        break;
                    case MILL:
                        ret = 3.83 * Math.pow(10, -5) * input;
                        break;
                }
                break;
            case MONTH:
                switch (conversionTo) {
                    case MICRO:
                        ret = 2.62 * Math.pow(10, 12) * input;
                        break;
                    case MILLI:
                        ret = 2.62 * Math.pow(10, 9) * input;
                        break;
                    case CENTI:
                        ret = 2.62 * Math.pow(10, 7) * input;
                        break;
                    case SEC:
                        ret = 2.62 * Math.pow(10, 6) * input;
                        break;
                    case MIN:
                        ret = 43800 * input;
                        break;
                    case HR:
                        ret = 730.001 * input;
                        break;
                    case DAY:
                        ret = 30.4167 * input;
                        break;
                    case WEEK:
                        ret = 4.34524 * input;
                        break;
                    case FORT:
                        ret = 2.17262 * input;
                        break;
                    case NANO:
                        ret = 2.62 * Math.pow(10, 15) * input;
                        break;
                    case YR:
                        ret = 0.0833334 * input;
                        break;
                    case DECADE:
                        ret = 0.00833334 * input;
                        break;
                    case CENTUARY:
                        ret = 0.000833334 * input;
                        break;
                    case MILL:
                        ret = 8.33 * Math.pow(10, -5) * input;
                        break;
                }
                break;
            case YR:
                switch (conversionTo) {
                    case MICRO:
                        ret = 3.154 * Math.pow(10, 13) * input;
                        break;
                    case MILLI:
                        ret = 3.154 * Math.pow(10, 10) * input;
                        break;
                    case CENTI:
                        ret = 3.154 * Math.pow(10, 8) * input;
                        break;
                    case SEC:
                        ret = 3.154 * Math.pow(10, 7) * input;
                        break;
                    case MIN:
                        ret = 525600 * input;
                        break;
                    case HR:
                        ret = 8760 * input;
                        break;
                    case DAY:
                        ret = 365 * input;
                        break;
                    case WEEK:
                        ret = 52.1429 * input;
                        break;
                    case FORT:
                        ret = 26.0714 * input;
                        break;
                    case MONTH:
                        ret = 12 * input;
                        break;
                    case NANO:
                        ret = 3.154 * Math.pow(10, 16) * input;
                        break;
                    case DECADE:
                        ret = 0.1 * input;
                        break;
                    case CENTUARY:
                        ret = 0.01 * input;
                        break;
                    case MILL:
                        ret = 0.001 * input;
                        break;
                }
                break;
            case DECADE:
                switch (conversionTo) {
                    case MICRO:
                        ret = 3.154 * Math.pow(10, 14) * input;
                        break;
                    case MILLI:
                        ret = 3.154 * Math.pow(10, 11) * input;
                        break;
                    case CENTI:
                        ret = 3.154 * Math.pow(10, 9) * input;
                        break;
                    case SEC:
                        ret = 3.154 * Math.pow(10, 8) * input;
                        break;
                    case MIN:
                        ret = 5.25 * Math.pow(10, 6) * input;
                        break;
                    case HR:
                        ret = 87600 * input;
                        break;
                    case DAY:
                        ret = 3650 * input;
                        break;
                    case WEEK:
                        ret = 521.429 * input;
                        break;
                    case FORT:
                        ret = 260.714 * input;
                        break;
                    case MONTH:
                        ret = 120 * input;
                        break;
                    case YR:
                        ret = 10 * input;
                        break;
                    case NANO:
                        ret = 3.154 * Math.pow(10, 17) * input;
                        break;
                    case CENTUARY:
                        ret = 0.1 * input;
                        break;
                    case MILL:
                        ret = 0.01 * input;
                        break;
                }
                break;
            case CENTUARY:
                switch (conversionTo) {
                    case MICRO:
                        ret = 3.154 * Math.pow(10, 15) * input;
                        break;
                    case MILLI:
                        ret = 3.154 * Math.pow(10, 12) * input;
                        break;
                    case CENTI:
                        ret = 3.154 * Math.pow(10, 10) * input;
                        break;
                    case SEC:
                        ret = 3.154 * Math.pow(10, 9) * input;
                        break;
                    case MIN:
                        ret = 5.25 * Math.pow(10, 7) * input;
                        break;
                    case HR:
                        ret = 876000 * input;
                        break;
                    case DAY:
                        ret = 36500 * input;
                        break;
                    case WEEK:
                        ret = 5214.29 * input;
                        break;
                    case FORT:
                        ret = 2607.14 * input;
                        break;
                    case MONTH:
                        ret = 1200 * input;
                        break;
                    case YR:
                        ret = 100 * input;
                        break;
                    case DECADE:
                        ret = 10 * input;
                        break;
                    case NANO:
                        ret = 3.154 * Math.pow(10, 18) * input;
                        break;
                    case MILL:
                        ret = 0.1 * input;
                        break;
                }
                break;
            case MILL:
                switch (conversionTo) {
                    case MICRO:
                        ret = 3.154 * Math.pow(10, 16) * input;
                        break;
                    case MILLI:
                        ret = 3.154 * Math.pow(10, 13) * input;
                        break;
                    case CENTI:
                        ret = 3.154 * Math.pow(10, 11) * input;
                        break;
                    case SEC:
                        ret = 3.154 * Math.pow(10, 10) * input;
                        break;
                    case MIN:
                        ret = 5.25 * Math.pow(10, 8) * input;
                        break;
                    case HR:
                        ret = 8760000 * input;
                        break;
                    case DAY:
                        ret = 365000 * input;
                        break;
                    case WEEK:
                        ret = 52142.9 * input;
                        break;
                    case FORT:
                        ret = 26071.4 * input;
                        break;
                    case MONTH:
                        ret = 12000 * input;
                        break;
                    case YR:
                        ret = 1000 * input;
                        break;
                    case DECADE:
                        ret = 100 * input;
                        break;
                    case NANO:
                        ret = 3.154 * Math.pow(10, 19) * input;
                        break;
                    case CENTUARY:
                        ret = 10 * input;
                        break;
                }
                break;


        }
        if (from == to) {
            return String.format("%s", input);
        }
        return TextUtil.convertToExponential(ret);
      }

    public String convertVolume(int from, int to, double input) {
        CONVERSION_VOLUME conversionFrom = CONVERSION_VOLUME.values()[from];
        CONVERSION_VOLUME conversionTo = CONVERSION_VOLUME.values()[to];
        double ret = 0;

        switch (conversionFrom) {
            case MIL:
                switch (conversionTo) {
                    case ML:
                        ret = input * 0.001;
                        break;
                    case CL:
                        ret = Math.pow(10, -4) * input;
                        break;
                    case L:
                        ret = Math.pow(10, -6) * input;
                        break;
                    case DL:
                        ret = Math.pow(10, -7) * input;
                        break;
                    case HL:
                        ret = Math.pow(10, -8) * input;
                        break;
                    case KL:
                        ret = Math.pow(10, -9) * input;
                        break;
                    case GILL:
                        ret = 8.45 * Math.pow(10, -6) * input;
                        break;
                    case GALLON:
                        ret = 2.64 * Math.pow(10, -7) * input;
                        break;
                    case PINT:
                        ret = 2.11 * Math.pow(10, -6) * input;
                        break;
                    case QUART:
                        ret = 1.05 * Math.pow(10, -6) * input;
                        break;
                }
                break;
            case ML:
                switch (conversionTo) {
                    case MIL:
                        ret = input * 1000;
                        break;
                    case CL:
                        ret = input * 0.1;
                        break;
                    case L:
                        ret = input / 1000;
                        break;
                    case DL:
                        ret = input / 10000;
                        break;
                    case HL:
                        ret = input / 100000;
                        break;
                    case KL:
                        ret = input / 1000000;
                        break;
                    case GILL:
                        ret = input * 0.00845351;
                        break;
                    case GALLON:
                        ret = input * 0.000264172;
                        break;
                    case PINT:
                        ret = input * 0.00211338;
                        break;
                    case QUART:
                        ret = input * 0.00105669;
                        break;
                }
                break;
            case CL:
                switch (conversionTo) {
                    case MIL:
                        ret = input * 10000;
                        break;
                    case ML:
                        ret = input * 10;
                        break;
                    case L:
                        ret = input * 0.01;
                        break;
                    case DL:
                        ret = input * 0.001;
                        break;
                    case HL:
                        ret = input * 0.0001;
                        break;
                    case KL:
                        ret = input * 0.00001;
                        break;
                    case GILL:
                        ret = input * 0.0845351;
                        break;
                    case GALLON:
                        ret = input * 0.00264172;
                        break;
                    case PINT:
                        ret = input * 0.0211338;
                        break;
                    case QUART:
                        ret = input * 0.0105669;
                        break;
                }
                break;
            case L:
                switch (conversionTo) {
                    case MIL:
                        ret = input * 1000000;
                        break;
                    case CL:
                        ret = input * 100;
                        break;
                    case ML:
                        ret = input * 1000;
                        break;
                    case DL:
                        ret = input * 0.1;
                        break;
                    case HL:
                        ret = input * 0.01;
                        break;
                    case KL:
                        ret = input * 0.001;
                        break;
                    case GILL:
                        ret = input * 8.45351;
                        break;
                    case GALLON:
                        ret = input * 0.264172;
                        break;
                    case PINT:
                        ret = input * 2.11338;
                        break;
                    case QUART:
                        ret = input * 1.05669;
                        break;
                }
                break;
            case DL:
                switch (conversionTo) {
                    case MIL:
                        ret = input * 10000000;
                        break;
                    case CL:
                        ret = input * 1000;
                        break;
                    case L:
                        ret = input * 10;
                        break;
                    case ML:
                        ret = input * 1000;
                        break;
                    case HL:
                        ret = input / 10;
                        break;
                    case KL:
                        ret = input / 100;
                        break;
                    case GILL:
                        ret = input * 84.5351;
                        break;
                    case GALLON:
                        ret = input * 2.64172;
                        break;
                    case PINT:
                        ret = input * 21.1338;
                        break;
                    case QUART:
                        ret = input * 10.5669;
                        break;
                }
                break;
            case HL:
                switch (conversionTo) {
                    case MIL:
                        ret = input * 100000000;
                        break;
                    case CL:
                        ret = input * 10000;
                        break;
                    case L:
                        ret = input * 100;
                        break;
                    case ML:
                        ret = input * 10000;
                        break;
                    case DL:
                        ret = input * 10;
                        break;
                    case KL:
                        ret = input / 10;
                        break;
                    case GILL:
                        ret = input * 845.351;
                        break;
                    case GALLON:
                        ret = input * 26.4172;
                        break;
                    case PINT:
                        ret = input * 211.338;
                        break;
                    case QUART:
                        ret = input * 105.669;
                        break;
                }
                break;
            case KL:
                switch (conversionTo) {
                    case MIL:
                        ret = input * 1000000000;
                        break;
                    case CL:
                        ret = input * 100000;
                        break;
                    case L:
                        ret = input * 1000;
                        break;
                    case ML:
                        ret = input * 100000;
                        break;
                    case DL:
                        ret = input * 100;
                        break;
                    case HL:
                        ret = input * 10;
                        break;
                    case GILL:
                        ret = input * 8453.51;
                        break;
                    case GALLON:
                        ret = input * 264.172;
                        break;
                    case PINT:
                        ret = input * 2113.38;
                        break;
                    case QUART:
                        ret = input * 1056.69;
                        break;
                }
                break;
            case GILL:
                switch (conversionTo) {
                    case MIL:
                        ret = input * 118294;
                        break;
                    case CL:
                        ret = input * 1.18294;
                        break;
                    case L:
                        ret = input * 0.118294;
                        break;
                    case DL:
                        ret = input * 0.0118294;
                        break;
                    case HL:
                        ret = input * 0.00118294;
                        break;
                    case KL:
                        ret = input * 0.000118294;
                        break;
                    case ML:
                        ret = input * 118.294;
                        break;
                    case GALLON:
                        ret = input * 0.03125;
                        break;
                    case PINT:
                        ret = input * 0.25;
                        break;
                    case QUART:
                        ret = input * 0.125;
                        break;
                }
                break;
            case GALLON:
                switch (conversionTo) {
                    case MIL:
                        ret = 3.78 * Math.pow(10, 6) * input;
                        break;
                    case CL:
                        ret = input * 37.8541;
                        break;
                    case L:
                        ret = input * 3.78541;
                        break;
                    case DL:
                        ret = input * 0.378541;
                        break;
                    case HL:
                        ret = input * 0.0378541;
                        break;
                    case KL:
                        ret = input * 0.00378541;
                        break;
                    case GILL:
                        ret = input * 32;
                        break;
                    case ML:
                        ret = input * 3785.41;
                        break;
                    case PINT:
                        ret = input * 8;
                        break;
                    case QUART:
                        ret = input * 0.125;
                        break;
                }
                break;
            case PINT:
                switch (conversionTo) {
                    case MIL:
                        ret = input * 473176;
                        break;
                    case CL:
                        ret = input * 4.73176;
                        break;
                    case L:
                        ret = input * 0.473176;
                        break;
                    case DL:
                        ret = input * 0.0473176;
                        break;
                    case HL:
                        ret = input * 0.00473176;
                        break;
                    case KL:
                        ret = input * 0.000473176;
                        break;
                    case GILL:
                        ret = input * 4;
                        break;
                    case GALLON:
                        ret = input * 0.125;
                        break;
                    case ML:
                        ret = input * 473.176;
                        break;
                    case QUART:
                        ret = input * 0.5;
                        break;
                }
                break;
            case QUART:
                switch (conversionTo) {
                    case MIL:
                        ret = input * 946353;
                        break;
                    case CL:
                        ret = input * 9.46353;
                        break;
                    case L:
                        ret = input * 0.946353;
                        break;
                    case DL:
                        ret = input * 0.0946353;
                        break;
                    case HL:
                        ret = input * 0.00946353;
                        break;
                    case KL:
                        ret = input * 0.00946353;
                        break;
                    case GILL:
                        ret = input * 8;
                        break;
                    case GALLON:
                        ret = input * 0.25;
                        break;
                    case PINT:
                        ret = input * 2;
                        break;
                    case ML:
                        ret = input * 946.353;
                        break;
                }
                break;


        }
        if (from == to) {
            return String.format("%s", input);
        }
        return TextUtil.convertToExponential(ret);
    }

    public String convertArea(int from, int to, double input) {
        CONVERSION_AREA conversionFrom = CONVERSION_AREA.values()[from];
        CONVERSION_AREA conversionTo = CONVERSION_AREA.values()[to];
        double ret = 0;

        switch (conversionFrom) {

            case FOOT:
                switch (conversionTo) {
                    case YARD:
                        ret = input * 0.11;
                        break;
                    case M:
                        ret = input * 0.092903;
                        break;
                    case KM:
                        ret = 9.3 * Math.pow(10, -8) * input;
                        break;
                    case MILE:
                        ret = 3.58 * Math.pow(10, -8) * input;
                        break;
                    case ACRE:
                        ret = 2.29 * Math.pow(10, -5) * input;
                        break;
                    case HT:
                        ret = 9.2 * Math.pow(10, -6) * input;
                        break;
                }
                break;
            case YARD:
                switch (conversionTo) {
                    case FOOT:
                        ret = input * 9;
                        break;
                    case M:
                        ret = input / (1.09361 * 1.09361);
                        break;
                    case KM:
                        ret = input / (1093.6133 * 1093.6133);
                        break;
                    case MILE:
                        ret = input / (1760 * 1760);
                        break;
                    case ACRE:
                        ret = input * 0.000206612;
                        break;
                    case HT:
                        ret = 8.36 * Math.pow(10, -5) * input;
                        break;
                }
                break;
            case M:
                switch (conversionTo) {
                    case FOOT:
                        ret = input * 10.7639;
                        break;
                    case YARD:
                        ret = 1.09361 * 1.09361 * input;
                        break;
                    case KM:
                        ret = input / (1000 * 1000);
                        break;
                    case MILE:
                        ret = input / (1609.34 * 1609.34);
                        break;
                    case ACRE:
                        ret = input * 0.000247105;
                        break;
                    case HT:
                        ret = input * 0.0001;
                        break;
                }
                break;
            case KM:
                switch (conversionTo) {
                    case FOOT:
                        ret = 1.07 * Math.pow(10, 7) * input;
                        break;
                    case M:
                        ret = input * 1000 * 1000;
                        break;
                    case YARD:
                        ret = 1093.6133 * 1093.6133 * input;
                        break;
                    case MILE:
                        ret = 0.62137 * 0.62137 * input;
                        break;
                    case ACRE:
                        ret = input * 247.105;
                        break;
                    case HT:
                        ret = input * 100;
                        break;
                }
                break;
            case MILE:
                switch (conversionTo) {
                    case FOOT:
                        ret = 2.78 * Math.pow(10, 7) * input;
                        break;
                    case M:
                        ret = 1609.34 * 1609.34 * input;
                        break;
                    case KM:
                        ret = 1.60934 * 1.60934 * input;
                        break;
                    case YARD:
                        ret = input * 1760 * 1760;
                        break;
                    case ACRE:
                        ret = input * 640;
                        break;
                    case HT:
                        ret = input * 259;
                        break;
                }
                break;
            case ACRE:
                switch (conversionTo) {
                    case FOOT:
                        ret = input * 43560;
                        break;
                    case M:
                        ret = input * 4046.86;
                        break;
                    case KM:
                        ret = input * 0.00404686;
                        break;
                    case MILE:
                        ret = input * 0.0015625;
                        break;
                    case YARD:
                        ret = input * 4840;
                        break;
                    case HT:
                        ret = input * 0.404686;
                        break;
                }
                break;
            case HT:
                switch (conversionTo) {
                    case FOOT:
                        ret = input * 107639;
                        break;
                    case M:
                        ret = input * 10000;
                        break;
                    case KM:
                        ret = input * 0.01;
                        break;
                    case MILE:
                        ret = input * 0.0039;
                        break;
                    case ACRE:
                        ret = input * 2.47105;
                        break;
                    case YARD:
                        ret = input * 11959.9;
                        break;
                }
                break;
        }
        if (from == to) {
            return String.format("%s", input);
        }
        return TextUtil.convertToExponential(ret);
    }

    public String convertTemp(int from, int to, double input) {
        CONVERSION_TEMPERATURE conversionFrom = CONVERSION_TEMPERATURE.values()[from];
        CONVERSION_TEMPERATURE conversionTo = CONVERSION_TEMPERATURE.values()[to];
        double ret = 0;

        switch (conversionFrom) {

            case FAHRENHEIT:
                switch (conversionTo) {
                    case CELSIUS:
                        ret = (input - 32) * 5 / 9;
                        break;
                }
                break;
            case CELSIUS:
                switch (conversionTo) {
                    case FAHRENHEIT:
                        ret = (input * 9 / 5) + 32;
                        break;
                }
                break;
        }
        if (from == to) {
            return String.format("%s", input);
        }
        return TextUtil.convertToExponential(ret);
    }

    public String convertWeight(int from, int to, double input) {
        CONVERSION_WEIGHT conversionFrom = CONVERSION_WEIGHT.values()[from];
        CONVERSION_WEIGHT conversionTo = CONVERSION_WEIGHT.values()[to];
        double ret = 0;
        switch (conversionFrom) {

            case KG:
                switch (conversionTo) {
                    case CG:
                        ret = input * 10000;
                        break;
                    case DCG:
                        ret = input * 100000;
                        break;
                    case DKG:
                        ret = input * 100;
                        break;
                    case HG:
                        ret = input * 10;
                        break;
                    case TON:
                        ret = input * 0.00110231;
                        break;
                    case CARET:
                        ret = input * 5000;
                        break;
                    case NEWTON:
                        ret = input * 9.806;
                        break;
                    case STONE:
                        ret = input * 0.157473;
                        break;
                    case MG:
                        ret = input * 1000000;
                        break;
                    case G:
                        ret = 1000 * input;
                        break;
                    case OUNCE:
                        ret = input * 35.27396;
                        break;
                    case LB:
                        ret = 2.2046 * input;
                        break;
                }
                break;
            case MG:
                switch (conversionTo) {
                    case DCG:
                        ret = input / 100;
                        break;
                    case DKG:
                        ret = input / 10000;
                        break;
                    case HG:
                        ret = input / 100000;
                        break;
                    case TON:
                        ret = input * 0.00000000110231;
                        break;
                    case CARET:
                        ret = input * 0.005000;
                        break;
                    case NEWTON:
                        ret = input * 0.000009806;
                        break;
                    case STONE:
                        ret = input * 0.000000157473;
                        break;
                    case KG:
                        ret = input / 1000000;
                        break;
                    case G:
                        ret = input / 1000;
                        break;
                    case CG:
                        ret = input / 10;
                        break;
                    case OUNCE:
                        ret = input / 28349;
                        break;
                    case LB:
                        ret = input / 453592.37;
                        break;
                }
                break;
            case G:
                switch (conversionTo) {
                    case CG:
                        ret = input * 100;
                        break;
                    case DCG:
                        ret = input * 10;
                        break;
                    case DKG:
                        ret = input / 10;
                        break;
                    case HG:
                        ret = input / 100;
                        break;
                    case TON:
                        ret = input * 0.00000110231;
                        break;
                    case CARET:
                        ret = input * 5;
                        break;
                    case NEWTON:
                        ret = input * 0.009806;
                        break;
                    case STONE:
                        ret = input * 0.000157473;
                        break;
                    case KG:
                        ret = input / 1000;
                        break;
                    case MG:
                        ret = input * 1000;
                        break;
                    case OUNCE:
                        ret = input * 0.03527;
                        break;
                    case LB:
                        ret = 0.0022 * input;
                        break;
                }
                break;
            case OUNCE:
                switch (conversionTo) {
                    case CG:
                        ret = input * 2834.95;
                        break;
                    case DCG:
                        ret = input * 283.495;
                        break;
                    case DKG:
                        ret = input * 2.83495;
                        break;
                    case HG:
                        ret = input * 0.283495;
                        break;
                    case TON:
                        ret = input * 0.00003125;
                        break;
                    case CARET:
                        ret = input * 141.747615625;
                        break;
                    case NEWTON:
                        ret = input * 0.278013851766;
                        break;
                    case STONE:
                        ret = input * 0.005;
                        break;
                    case KG:
                        ret = input * 0.02835;
                        break;
                    case MG:
                        ret = input * 28349.52313;
                        break;
                    case G:
                        ret = input * 28.34952;
                        break;
                    case LB:
                        ret = input / 16;
                        break;
                }
                break;
            case LB:
                switch (conversionTo) {
                    case CG:
                        ret = 45359.23 * input;
                        break;
                    case DCG:
                        ret = 4535.94 * input;
                        break;
                    case DKG:
                        ret = 45.4 * input;
                        break;
                    case HG:
                        ret = 4.54 * input;
                        break;
                    case TON:
                        ret = 0.0005 * input;
                        break;
                    case CARET:
                        ret = 2267.96185 * input;
                        break;
                    case NEWTON:
                        ret = 4.45 * input;
                        break;
                    case STONE:
                        ret = 0.08 * input;
                        break;
                    case KG:
                        ret = 0.454 * input;
                        break;
                    case MG:
                        ret = input * 453592.37;
                        break;
                    case G:
                        ret = 453.6 * input;
                        break;
                    case OUNCE:
                        ret = 16 * input;
                        break;
                }
                break;
            case CG:
                switch (conversionTo) {
                    case DCG:
                        ret = input / 10;
                        break;
                    case DKG:
                        ret = input / 1000;
                        break;
                    case HG:
                        ret = input / 10000;
                        break;
                    case TON:
                        ret = input * 0.000000011;
                        break;
                    case CARET:
                        ret = input * 0.05;
                        break;
                    case NEWTON:
                        ret = input * 0.00009806;
                        break;
                    case STONE:
                        ret = input * 0.000001764;
                        break;
                    case KG:
                        ret = input / 100000;
                        break;
                    case MG:
                        ret = input * 10;
                        break;
                    case G:
                        ret = input / 100;
                        break;
                    case OUNCE:
                        ret = input * 0.0003527396;
                        break;
                    case LB:
                        ret = 0.000022 * input;
                        break;
                }
                break;
            case DCG:
                switch (conversionTo) {
                    case CG:
                        ret = input / 10;
                        break;
                    case DKG:
                        ret = input / 100;
                        break;
                    case HG:
                        ret = input / 1000;
                        break;
                    case TON:
                        ret = input * 0.00000011;
                        break;
                    case CARET:
                        ret = input * 0.5;
                        break;
                    case NEWTON:
                        ret = input * 0.0009806;
                        break;
                    case STONE:
                        ret = input * 0.00001764;
                        break;
                    case KG:
                        ret = input / 10000;
                        break;
                    case MG:
                        ret = input * 100;
                        break;
                    case G:
                        ret = input / 10;
                        break;
                    case OUNCE:
                        ret = input * 0.003527396;
                        break;
                    case LB:
                        ret = 0.00022 * input;
                        break;
                }
                break;
            case DKG:
                switch (conversionTo) {
                    case CG:
                        ret = input * 1000;
                        break;
                    case DCG:
                        ret = input * 100;
                        break;
                    case G:
                        ret = input * 10;
                        break;
                    case HG:
                        ret = input / 10;
                        break;
                    case TON:
                        ret = input * 0.0000110231;
                        break;
                    case CARET:
                        ret = input * 50;
                        break;
                    case NEWTON:
                        ret = input * 0.09806;
                        break;
                    case STONE:
                        ret = input * 0.00157473;
                        break;
                    case KG:
                        ret = input / 100;
                        break;
                    case MG:
                        ret = input * 10000;
                        break;
                    case OUNCE:
                        ret = input * 0.3527;
                        break;
                    case LB:
                        ret = 0.022 * input;
                        break;
                }
                break;
            case HG:
                switch (conversionTo) {
                    case CG:
                        ret = input * 10000;
                        break;
                    case DCG:
                        ret = input * 1000;
                        break;
                    case G:
                        ret = input * 100;
                        break;
                    case DKG:
                        ret = input * 10;
                        break;
                    case TON:
                        ret = input * 0.000110231;
                        break;
                    case CARET:
                        ret = input * 500;
                        break;
                    case NEWTON:
                        ret = input * 0.9806;
                        break;
                    case STONE:
                        ret = input * 0.0157473;
                        break;
                    case KG:
                        ret = input / 10;
                        break;
                    case MG:
                        ret = input * 100000;
                        break;
                    case OUNCE:
                        ret = input * 3.527;
                        break;
                    case LB:
                        ret = 0.22 * input;
                        break;
                }
                break;
            case TON:
                switch (conversionTo) {
                    case CG:
                        ret = (double) 90718474 * input;
                        break;
                    case DCG:
                        ret = 9071847.4 * input;
                        break;
                    case HG:
                        ret = 9071.85 * input;
                        break;
                    case DKG:
                        ret = 90718.48 * input;
                        break;
                    case CARET:
                        ret = 4535923.7 * input;
                        break;
                    case NEWTON:
                        ret = 8896.44 * input;
                        break;
                    case STONE:
                        ret = (double) 160 * input;
                        break;
                    case KG:
                        ret = 907.18 * input;
                        break;
                    case MG:
                        ret = (double) 907184740 * input;
                        break;
                    case G:
                        ret = 907184.74 * input;
                        break;
                    case OUNCE:
                        ret = (double) 32000 * input;
                        break;
                    case LB:
                        ret = (double) 2000 * input;
                        break;
                }
                break;
            case CARET:
                switch (conversionTo) {
                    case CG:
                        ret = (double) 20 * input;
                        break;
                    case DCG:
                        ret = (double) 2 * input;
                        break;
                    case HG:
                        ret = 0.002 * input;
                        break;
                    case TON:
                        ret = 0.00000022 * input;
                        break;
                    case DKG:
                        ret = 0.02 * input;
                        break;
                    case NEWTON:
                        ret = 0.0012 * input;
                        break;
                    case STONE:
                        ret = 0.000035274 * input;
                        break;
                    case KG:
                        ret = 0.0002 * input;
                        break;
                    case MG:
                        ret = (double) 200 * input;
                        break;
                    case G:
                        ret = 0.2 * input;
                        break;
                    case OUNCE:
                        ret = 0.007054792 * input;
                        break;
                    case LB:
                        ret = 0.000440925 * input;
                        break;
                }
                break;
            case NEWTON:
                switch (conversionTo) {
                    case CG:
                        ret = 10197.16 * input;
                        break;
                    case DCG:
                        ret = 1019.716 * input;
                        break;
                    case HG:
                        ret = 1.019716 * input;
                        break;
                    case TON:
                        ret = 0.00011 * input;
                        break;
                    case CARET:
                        ret = 496.69 * input;
                        break;
                    case DKG:
                        ret = 10.19716 * input;
                        break;
                    case STONE:
                        ret = 0.016 * input;
                        break;
                    case KG:
                        ret = 0.101 * input;
                        break;
                    case MG:
                        ret = 101971.6 * input;
                        break;
                    case G:
                        ret = 101.9716 * input;
                        break;
                    case OUNCE:
                        ret = 3.59 * input;
                        break;
                    case LB:
                        ret = 0.22 * input;
                        break;
                }
                break;
            case STONE:
                switch (conversionTo) {
                    case CG:
                        ret = (double) 635029 * input;
                        break;
                    case DCG:
                        ret = 63502.9 * input;
                        break;
                    case HG:
                        ret = 63.50 * input;
                        break;
                    case TON:
                        ret = 0.007 * input;
                        break;
                    case CARET:
                        ret = 31751.5 * input;
                        break;
                    case NEWTON:
                        ret = 62.275 * input;
                        break;
                    case DKG:
                        ret = 635.029 * input;
                        break;
                    case KG:
                        ret = 6.35 * input;
                        break;
                    case MG:
                        ret = 6.35 * Math.pow(10, 6) * input;
                        break;
                    case G:
                        ret = 6350.29 * input;
                        break;
                    case OUNCE:
                        ret = (double) 224 * input;
                        break;
                    case LB:
                        ret = (double) 14 * input;
                        break;
                }
                break;

        }
        if (from == to) {
            return String.format("%s", input);
        }
        return TextUtil.convertToExponential(ret);
    }

    public String convertLength(int from, int to, double input) {
        CONVERSION_DISTANCE conversionFrom = CONVERSION_DISTANCE.values()[from];
        CONVERSION_DISTANCE conversionTo = CONVERSION_DISTANCE.values()[to];
        double ret = 0;
        switch (conversionFrom) {

            case KM:
                switch (conversionTo) {
                    case M:
                        ret = input * 1000;
                        break;
                    case MM:
                        ret = 1000000 * input;
                        break;
                    case INCH:
                        ret = input * 39370.1;
                        break;
                    case FEET:
                        ret = input * 3280.84;
                        break;
                    case MILE:
                        ret = 0.62137 * input;
                        break;
                    case CM:
                        ret = 100000 * input;
                        break;
                    case DCM:
                        ret = 10000 * input;
                        break;
                    case DKM:
                        ret = 100 * input;
                        break;
                    case HM:
                        ret = 10 * input;
                        break;
                    case YARD:
                        ret = 1093.61 * input;
                        break;
                    case PARSEC:
                        ret = 3.2 * Math.pow(10, -14) * input;
                        break;
                    case FATHOM:
                        ret = 546.80 * input;
                        break;
                    case LEAGUE:
                        ret = 0.21 * input;
                        break;
                }
                break;
            case M:
                switch (conversionTo) {
                    case KM:
                        ret = 0.001 * input;
                        break;
                    case MM:
                        ret = 1000 * input;
                        break;
                    case INCH:
                        ret = 100 * input / 2.54;
                        break;
                    case FEET:
                        ret = input * 3.28084;
                        break;
                    case MILE:
                        ret = input / 1609.34;
                        break;
                    case CM:
                        ret = 100 * input;
                        break;
                    case DCM:
                        ret = 0.1 * input;
                        break;
                    case DKM:
                        ret = 10 * input;
                        break;
                    case HM:
                        ret = 100 * input;
                        break;
                    case YARD:
                        ret = 1.093 * input;
                        break;
                    case PARSEC:
                        ret = 3.2 * Math.pow(10, -17) * input;
                        break;
                    case FATHOM:
                        ret = 0.54680 * input;
                        break;
                    case LEAGUE:
                        ret = 0.00021 * input;
                        break;
                }
                break;
            case MM:
                switch (conversionTo) {
                    case KM:
                        ret = input / 1000000;
                        break;
                    case M:
                        ret = input / 1000;
                        break;
                    case INCH:
                        ret = input / 25.4;
                        break;
                    case FEET:
                        ret = 0.00328 * input;
                        break;
                    case MILE:
                        ret = input / 1609340;
                        break;
                    case CM:
                        ret = input / 100;
                        break;
                    case DCM:
                        ret = input / 10;
                        break;
                    case DKM:
                        ret = input / 10000;
                        break;
                    case HM:
                        ret = input / 100000;
                        break;
                    case YARD:
                        ret = 0.001093613 * input;
                        break;
                    case PARSEC:
                        ret = 3.2 * Math.pow(10, -20) * input;
                        break;
                    case FATHOM:
                        ret = 0.00055 * input;
                        break;
                    case LEAGUE:
                        ret = 0.000000207 * input;
                        break;
                }
                break;
            case INCH:
                switch (conversionTo) {
                    case M:
                        ret = 0.0254 * input;
                        break;
                    case MM:
                        ret = input * 25.4;
                        break;
                    case KM:
                        ret = input * 0.0000254;
                        break;
                    case FEET:
                        ret = input / 12;
                        break;
                    case MILE:
                        ret = input / 63360;
                        break;
                    case CM:
                        ret = 2.54 * input;
                        break;
                    case DCM:
                        ret = 0.254 * input;
                        break;
                    case DKM:
                        ret = 0.00254 * input;
                        break;
                    case HM:
                        ret = 0.000254 * input;
                        break;
                    case YARD:
                        ret = 0.027 * input;
                        break;
                    case PARSEC:
                        ret = 8.2 * Math.pow(10, -19) * input;
                        break;
                    case FATHOM:
                        ret = 0.013888889 * input;
                        break;
                    case LEAGUE:
                        ret = 0.000005261 * input;
                        break;
                }
                break;
            case FEET:
                switch (conversionTo) {
                    case M:
                        ret = input * 0.3048;
                        break;
                    case MM:
                        ret = input * 304.8;
                        break;
                    case INCH:
                        ret = 12 * input;
                        break;
                    case KM:
                        ret = input * 0.0003048;
                        break;
                    case MILE:
                        ret = input / 5280;
                        break;
                    case CM:
                        ret = input * 30.48;
                        break;
                    case DCM:
                        ret = 3.048 * input;
                        break;
                    case DKM:
                        ret = 0.03048 * input;
                        break;
                    case HM:
                        ret = 0.003048 * input;
                        break;
                    case YARD:
                        ret = 0.33 * input;
                        break;
                    case PARSEC:
                        ret = 9.9 * Math.pow(10, -20) * input;
                        break;
                    case FATHOM:
                        ret = 0.16 * input;
                        break;
                    case LEAGUE:
                        ret = 0.00006 * input;
                        break;

                }
                break;
            case MILE:
                switch (conversionTo) {
                    case M:
                        ret = 1609.34 * input;
                        break;
                    case MM:
                        ret = input * 1609340;
                        break;
                    case INCH:
                        ret = 63360 * input;
                        break;
                    case FEET:
                        ret = 5280 * input;
                        break;
                    case KM:
                        ret = 1.60934 * input;
                        break;
                    case CM:
                        ret = 160934 * input;
                        break;
                    case DCM:
                        ret = 16093.44 * input;
                        break;
                    case DKM:
                        ret = 160.93 * input;
                        break;
                    case HM:
                        ret = 16.09 * input;
                        break;
                    case YARD:
                        ret = 1760 * input;
                        break;
                    case PARSEC:
                        ret = 5.2 * Math.pow(10, -14) * input;
                        break;
                    case FATHOM:
                        ret = 880 * input;
                        break;
                    case LEAGUE:
                        ret = 0.33 * input;
                        break;

                }
                break;
            case CM:
                switch (conversionTo) {
                    case MILE:
                        ret = input / 160934;
                        break;
                    case MM:
                        ret = 10 * input;
                        break;
                    case INCH:
                        ret = input / 2.54;
                        break;
                    case FEET:
                        ret = input * 0.03281;
                        break;
                    case KM:
                        ret = input / 100000;
                        break;
                    case M:
                        ret = input / 100;
                        break;
                    case DCM:
                        ret = 10 * input;
                        break;
                    case DKM:
                        ret = 1000 * input;
                        break;
                    case HM:
                        ret = 10000 * input;
                        break;
                    case YARD:
                        ret = 0.010 * input;
                        break;
                    case PARSEC:
                        ret = 3.2 * Math.pow(10, -17) * input;
                        break;
                    case FATHOM:
                        ret = 0.0055 * input;
                        break;
                    case LEAGUE:
                        ret = 0.000002 * input;
                        break;

                }
                break;
            case DCM:
                switch (conversionTo) {
                    case MILE:
                        ret = input * 0.000062;
                        break;
                    case MM:
                        ret = 100 * input;
                        break;
                    case INCH:
                        ret = input / 3.93;
                        break;
                    case FEET:
                        ret = input * 0.33;
                        break;
                    case KM:
                        ret = input * 100000;
                        break;
                    case M:
                        ret = input / 100;
                        break;
                    case CM:
                        ret = input / 10;
                        break;
                    case DKM:
                        ret = input / 1000;
                        break;
                    case HM:
                        ret = input / 10000;
                        break;
                    case YARD:
                        ret = 0.1 * input;
                        break;
                    case PARSEC:
                        ret = 3.2 * Math.pow(10, -18) * input;
                        break;
                    case FATHOM:
                        ret = 0.05 * input;
                        break;
                    case LEAGUE:
                        ret = 0.00002 * input;
                        break;

                }
                break;
            case DKM:
                switch (conversionTo) {
                    case MILE:
                        ret = input * 0.006;
                        break;
                    case MM:
                        ret = 10000 * input;
                        break;
                    case INCH:
                        ret = input / 393.7;
                        break;
                    case FEET:
                        ret = input * 32.8;
                        break;
                    case KM:
                        ret = input / 100;
                        break;
                    case M:
                        ret = input * 10;
                        break;
                    case DCM:
                        ret = input * 1000;
                        break;
                    case CM:
                        ret = input * 100;
                        break;
                    case HM:
                        ret = 0.1 * input;
                        break;
                    case YARD:
                        ret = 10.9 * input;
                        break;
                    case PARSEC:
                        ret = 3.2 * Math.pow(10, -16) * input;
                        break;
                    case FATHOM:
                        ret = 5.4 * input;
                        break;
                    case LEAGUE:
                        ret = 0.002 * input;
                        break;

                }
                break;
            case HM:
                switch (conversionTo) {
                    case MILE:
                        ret = input * 0.062;
                        break;
                    case MM:
                        ret = 100000 * input;
                        break;
                    case INCH:
                        ret = input * 3937;
                        break;
                    case FEET:
                        ret = input * 328.08;
                        break;
                    case KM:
                        ret = input / 10;
                        break;
                    case M:
                        ret = input * 100;
                        break;
                    case DCM:
                        ret = 1000 * input;
                        break;
                    case DKM:
                        ret = 10 * input;
                        break;
                    case CM:
                        ret = 10000 * input;
                        break;
                    case YARD:
                        ret = 109.3 * input;
                        break;
                    case PARSEC:
                        ret = 3.2 * Math.pow(10, -15) * input;
                        break;
                    case FATHOM:
                        ret = 54.68 * input;
                        break;
                    case LEAGUE:
                        ret = 0.02 * input;
                        break;

                }
                break;
            case YARD:
                switch (conversionTo) {
                    case MILE:
                        ret = input * 0.00056;
                        break;
                    case MM:
                        ret = 914.4 * input;
                        break;
                    case INCH:
                        ret = input * 36;
                        break;
                    case FEET:
                        ret = input * 0.03281;
                        break;
                    case KM:
                        ret = input * 0.0009144;
                        break;
                    case M:
                        ret = input * 0.9144;
                        break;
                    case DCM:
                        ret = 9.144 * input;
                        break;
                    case DKM:
                        ret = 0.09144 * input;
                        break;
                    case HM:
                        ret = 0.009144 * input;
                        break;
                    case CM:
                        ret = 91.44 * input;
                        break;
                    case PARSEC:
                        ret = 2.9 * Math.pow(10, -16) * input;
                        break;
                    case FATHOM:
                        ret = 0.5 * input;
                        break;
                    case LEAGUE:
                        ret = 0.00018 * input;
                        break;

                }
                break;
            case PARSEC:
                switch (conversionTo) {
                    case MILE:
                        ret = input * 1.91 * Math.pow(10, 13);
                        break;
                    case MM:
                        ret = 3.085 * Math.pow(10, 19) * input;
                        break;
                    case INCH:
                        ret = input * 1.21 * Math.pow(10, 18);
                        break;
                    case FEET:
                        ret = input * 1.01 * Math.pow(10, 17);
                        break;
                    case KM:
                        ret = input * 3.085 * Math.pow(10, 13);
                        break;
                    case M:
                        ret = input * 3.085 * Math.pow(10, 16);
                        break;
                    case DCM:
                        ret = 3.085 * Math.pow(10, 17) * input;
                        break;
                    case DKM:
                        ret = 3.085 * Math.pow(10, 15) * input;
                        break;
                    case HM:
                        ret = 3.085 * Math.pow(10, 14) * input;
                        break;
                    case YARD:
                        ret = 3.37 * Math.pow(10, 16) * input;
                        break;
                    case CM:
                        ret = 3.085 * Math.pow(10, 18) * input;
                        break;
                    case FATHOM:
                        ret = 1.68 * Math.pow(10, 16) * input;
                        break;
                    case LEAGUE:
                        ret = 6.39 * Math.pow(10, 12) * input;
                        break;

                }
                break;
            case FATHOM:
                switch (conversionTo) {
                    case MILE:
                        ret = input * 0.0011;
                        break;
                    case MM:
                        ret = 1828.8 * input;
                        break;
                    case INCH:
                        ret = input * 72;
                        break;
                    case FEET:
                        ret = input * 6;
                        break;
                    case KM:
                        ret = input * 0.0018288;
                        break;
                    case M:
                        ret = input * 1.82;
                        break;
                    case DCM:
                        ret = 18.288 * input;
                        break;
                    case DKM:
                        ret = 0.18288 * input;
                        break;
                    case HM:
                        ret = input * 0.018288;
                        break;
                    case YARD:
                        ret = 2 * input;
                        break;
                    case PARSEC:
                        ret = 0.0000000000000000059 * input;
                        break;
                    case CM:
                        ret = 182.88 * input;
                        break;
                    case LEAGUE:
                        ret = 0.00037 * input;
                        break;

                }
                break;
            case LEAGUE:
                switch (conversionTo) {
                    case MILE:
                        ret = input * 3;
                        break;
                    case MM:
                        ret = 4828032 * input;
                        break;
                    case INCH:
                        ret = input * 190080;
                        break;
                    case FEET:
                        ret = input * 15840;
                        break;
                    case KM:
                        ret = input * 4.828032;
                        break;
                    case M:
                        ret = input * 4828.032;
                        break;
                    case DCM:
                        ret = 48280.32 * input;
                        break;
                    case DKM:
                        ret = 482.8032 * input;
                        break;
                    case HM:
                        ret = 48.28032 * input;
                        break;
                    case YARD:
                        ret = 5280 * input;
                        break;
                    case PARSEC:
                        ret = input * 1.5 * Math.pow(10, -13);
                        break;
                    case FATHOM:
                        ret = 2640 * input;
                        break;
                    case CM:
                        ret = 482803.2 * input;
                        break;

                }
                break;
        }
        if (from == to) {
            return String.format("%s", input);
        }

        return TextUtil.convertToExponential(ret);
    }
}
