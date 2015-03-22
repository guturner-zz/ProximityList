package com.guy.proximitylist.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.guy.proximitylist.activity.ListSummaryActivity;
import com.guy.proximitylist.db.ProximityListDBHelper;

/**
 * Created by Guy on 3/19/2015.
 */
public class ListItem extends ContentProvider {
    public static final String PROVIDER_NAME = "com.guy.proximitylist.content.listitem";
    public static final Uri    CONTENT_URI   = Uri.parse("content://" + PROVIDER_NAME + "/listitems");

    /* Query Switches */
    private static final int LISTITEMS = 1;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "listitems", LISTITEMS);
    }

    ProximityListDBHelper db;

    @Override
    public boolean onCreate() {
        db = new ProximityListDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) == LISTITEMS) {
            return db.getAllListItems(ListSummaryActivity.listId);
        } else {
            return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
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
