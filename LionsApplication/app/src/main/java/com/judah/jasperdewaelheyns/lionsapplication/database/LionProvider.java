package com.judah.jasperdewaelheyns.lionsapplication.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by JasperDewaelheyns on 23/10/2015.
 */
public class LionProvider extends ContentProvider {

    public static final String AUTHORITY = "com.judah.jasperdewaelheyns.lionsapplication";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static final int LIONS = 1;
    private static final int LIONS_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "lions", LIONS);
        uriMatcher.addURI(AUTHORITY, "lions/#", LIONS_ID);
    }

    private LionDBHelper lionDBHelper;


    @Override
    public boolean onCreate() {
        lionDBHelper = new LionDBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        SQLiteDatabase database = lionDBHelper.getWritableDatabase();

        Cursor cursor;

        switch (uriMatcher.match(uri)) {

            case LIONS:
                queryBuilder.setTables(LionContract.Lions.TABLE_NAME);
                cursor = queryBuilder.query(database, projection , selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case LIONS_ID:
                queryBuilder.setTables(LionContract.Lions.TABLE_NAME);
                queryBuilder.appendWhere(LionContract.Lions._ID + " = " + uri.getLastPathSegment());
                cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }

    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = 0;
        SQLiteDatabase db = lionDBHelper.getWritableDatabase();
        String path;

        switch (uriMatcher.match(uri)) {
            case LIONS:
                id = db.insert(LionContract.Lions.TABLE_NAME, null, values);
                path = "lions/" + id;
                getContext().getContentResolver().notifyChange(uri, null);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return Uri.parse(path);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
