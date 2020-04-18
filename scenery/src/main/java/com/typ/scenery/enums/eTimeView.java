package com.typ.scenery.enums;

public enum eTimeView {
    DAY,
    SUNRISE,
    MID_DAY,
    CENTER_OF_DAY,
    BEFORE_NIGHT,
    NIGHT;

    public static eTimeView getEnumByOrdinal(int ordinal) {
        eTimeView result = null;
        switch (ordinal) {
            case 0:
                result = DAY;
                break;
            case 1:
                result = SUNRISE;
                break;
            case 2:
                result = MID_DAY;
                break;
            case 3:
                result = CENTER_OF_DAY;
                break;
            case 4:
                result = BEFORE_NIGHT;
                break;
            case 5:
                result = NIGHT;
                break;
        }
        return result;
    }
}
