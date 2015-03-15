package com.guy.proximitylist.db;

import android.provider.BaseColumns;

/**
 * Created by Guy on 3/14/2015.
 */
public class ProximityListContract {
    public ProximityListContract() { }

    public static abstract class ProximityListEntry implements BaseColumns {
        public static final String TABLE_NAME             = "entry";
        public static final String COLUMN_NAME_ENTRY_ID   = "entryid";
        public static final String COLUMN_NAME_ENTRY_NAME = "entryname";

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME_ENTRY_ID   + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_ENTRY_NAME + " TEXT" +
                ")";
    }
}
