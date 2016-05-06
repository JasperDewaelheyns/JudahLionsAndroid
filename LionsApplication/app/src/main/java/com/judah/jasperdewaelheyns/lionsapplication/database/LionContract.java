package com.judah.jasperdewaelheyns.lionsapplication.database;

import android.provider.BaseColumns;

/**
 * Created by JasperDewaelheyns on 23/10/2015.
 */
public class LionContract {

    public static final String DB_NAME = "lions";
    public static final int DB_VER = 1;

    public static final class Lions implements BaseColumns {
        public static final String TABLE_NAME = "lions";
        public static final String COL_NAME = "name";
        public static final String COL_CHILDNAME = "child";
        public static final String COL_PHONE = "phone";
        public static final String COL_BIRTHDAY = "birthday";
    }
}
