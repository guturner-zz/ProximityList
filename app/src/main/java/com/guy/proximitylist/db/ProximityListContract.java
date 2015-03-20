package com.guy.proximitylist.db;

import android.content.ContentProvider;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Guy on 3/14/2015.
 */
public class ProximityListContract {
    public ProximityListContract() { }

    public static abstract class ProximityListEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String ENTRY_NAME = "entryname";

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                ENTRY_NAME + " TEXT" +
                " )";
    }
}
