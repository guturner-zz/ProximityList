package com.guy.proximitylist.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Guy on 3/14/2015.
 */
public class ProximityListDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "proximitylist.db";
    public static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public ProximityListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProximityListContract.ProximityListEntry.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Do nothing
    }

    public Cursor getAllListEntries() {
        return db.query(
                ProximityListContract.ProximityListEntry.TABLE_NAME,
                new String[] { ProximityListContract.ProximityListEntry._ID, ProximityListContract.ProximityListEntry.ENTRY_NAME },
                null,
                null,
                null,
                null,
                null
        );
    }
}
