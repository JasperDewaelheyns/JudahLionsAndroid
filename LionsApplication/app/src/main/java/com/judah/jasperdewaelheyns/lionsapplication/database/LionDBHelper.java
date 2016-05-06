package com.judah.jasperdewaelheyns.lionsapplication.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JasperDewaelheyns on 23/10/2015.
 */
public class LionDBHelper extends SQLiteOpenHelper {

    public LionDBHelper(Context ctx) {
        super(ctx, LionContract.DB_NAME, null, LionContract.DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "CREATE TABLE " + LionContract.Lions.TABLE_NAME + " ("
                + LionContract.Lions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LionContract.Lions.COL_NAME + " TEXT, "
                + LionContract.Lions.COL_CHILDNAME + " TEXT, "
                + LionContract.Lions.COL_BIRTHDAY + " TEXT, "
                + LionContract.Lions.COL_PHONE + " TEXT)";

        try {
            db.execSQL(sqlCreate);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            String sqlDestroy = "DROP TABLE " + LionContract.Lions.TABLE_NAME;
            try {
                db.execSQL(sqlDestroy);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            onCreate(db);
        }
    }
}
