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

    public static abstract class ProximityListItem implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String ITEM_NAME  = "itemname";
        public static final String LIST_ID    = "listid";
        public static final String LIST_POS   = "listpos";

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                ITEM_NAME + " TEXT," +
                LIST_ID + " INTEGER, " +
                LIST_POS + " INTEGER, " +
                " FOREIGN KEY (" + LIST_ID + ") REFERENCES " + ProximityListEntry.TABLE_NAME + " (" + ProximityListEntry._ID + ")" +
                " )";
    }
}
