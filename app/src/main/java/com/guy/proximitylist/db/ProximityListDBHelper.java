package com.guy.proximitylist.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Utility class for common database operations:
 *   - select all lists
 *   - select all items in a given list
 *   - etc.
 *
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
        db.execSQL(ProximityListContract.ProximityListItem.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Do nothing
    }

    /**
     * Returns all _ID and ENTRY_NAME column values (i.e. all list names)
     *
     * @return Cursor
     */
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

    /**
     * Returns all _ID and ITEM_NAME column values for a given list (i.e. all item names in a given list).
     *
     * @param listId
     * @return Cursor
     */
    public Cursor getAllListItems(String listId) {
        return db.query(
                ProximityListContract.ProximityListItem.TABLE_NAME,
                new String[] { ProximityListContract.ProximityListItem._ID, ProximityListContract.ProximityListItem.ITEM_NAME },
                ProximityListContract.ProximityListItem.LIST_ID + " = " + listId,
                null,
                null,
                null,
                null
        );
    }

    /**
     * Returns number of items in a given list.
     *
     * @param listId
     * @return int representing number of items in a list
     */
    public int getRowCount(String listId) {
        Cursor c = db.rawQuery("select count(*) from " + ProximityListContract.ProximityListItem.TABLE_NAME +
                               " where " + ProximityListContract.ProximityListItem.LIST_ID + " = " + listId, null);
        if (c != null) {
            c.moveToFirst();
            int count = c.getInt(0);
            c.close();
            return count;
        }

        return 0;
    }

    /**
     * Permanently deletes all items in a list.
     *
     * @param listId
     */
    public void clearList(String listId) {
        db.delete(
                ProximityListContract.ProximityListItem.TABLE_NAME,
                ProximityListContract.ProximityListItem.LIST_ID + " = ?",
                new String[] { listId }
        );
    }
}
