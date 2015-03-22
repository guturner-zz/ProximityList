package com.guy.proximitylist.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.guy.proximitylist.R;
import com.guy.proximitylist.content.ListItem;
import com.guy.proximitylist.db.ProximityListContract;
import com.guy.proximitylist.db.ProximityListDBHelper;

/**
 * Created by Guy on 3/21/2015.
 */
public class ListSummaryActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static String listId;

    private Button newItemBtn;
    private ListView lv;

    private SimpleCursorAdapter simpleCursorAdapter;

    static final String[] projection = new String[] {
        ProximityListContract.ProximityListItem._ID,
        ProximityListContract.ProximityListItem.ITEM_NAME
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_summary);

        newItemBtn = (Button) findViewById(R.id.new_item_btn);

        // Receive intent:
        Intent intent   = getIntent();
        String listName = intent.getStringExtra(WelcomeActivity.EXTRA_LIST_NAME);
        listId          = intent.getStringExtra(WelcomeActivity.EXTRA_LIST_ID);

        TextView listNameLbl = (TextView) findViewById(R.id.list_name_lbl);
        listNameLbl.setText(listName);

        // Maps DB columns to listview elements:
        String[] fromCols = { ProximityListContract.ProximityListEntry._ID, ProximityListContract.ProximityListItem.ITEM_NAME };
        int[] toViews     = { 0, R.id.list_summary_item_txt };

        simpleCursorAdapter = new SimpleCursorAdapter(
            getBaseContext(),
            R.layout.list_summary_item_layout,
            null,
            fromCols,
            toViews,
            0
        );
        lv = (ListView) findViewById(R.id.list_summary_lv);
        lv.setAdapter(simpleCursorAdapter);

        getLoaderManager().initLoader(0, null, this);

        newItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem("abc");
            }
        });
    }

    public void addNewItem(String itemName) {
        ProximityListDBHelper dbHelper = new ProximityListDBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProximityListContract.ProximityListItem.ITEM_NAME, itemName);
        values.put(ProximityListContract.ProximityListItem.LIST_ID, listId);

        db.insert(ProximityListContract.ProximityListItem.TABLE_NAME,
                "null",
                values);

        // Reload ListView:
        Cursor c = dbHelper.getAllListItems(listId);
        simpleCursorAdapter.swapCursor(c);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
            this,
            ListItem.CONTENT_URI,
            projection,
            null,
            null,
            null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        simpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        simpleCursorAdapter.swapCursor(null);
    }
}
