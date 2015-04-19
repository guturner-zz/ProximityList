package com.guy.proximitylist.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.guy.proximitylist.R;
import com.guy.proximitylist.adapter.ListSummaryCursorAdapter;
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

    private ListSummaryCursorAdapter simpleCursorAdapter;

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
        String[] fromCols = { ProximityListContract.ProximityListItem._ID, ProximityListContract.ProximityListItem.ITEM_NAME };
        int[] toViews     = { 0, R.id.list_summary_item_txt };

        simpleCursorAdapter = new ListSummaryCursorAdapter(
            getBaseContext(),
            R.layout.list_summary_item_layout,
            null,
            fromCols,
            toViews,
            0
        );
        lv = (ListView) findViewById(R.id.list_summary_lv);
        lv.setAdapter(simpleCursorAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditText itemTxt = (EditText) view.findViewById(R.id.list_summary_item_txt);
            }
        });

        getLoaderManager().initLoader(0, null, this);

        newItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });
    }

    public void addNewItem() {
        ProximityListDBHelper dbHelper = new ProximityListDBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        AlertDialog alert = new AlertDialog.Builder(ListSummaryActivity.this).create();
        alert.setTitle("New Item");

        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.new_obj_layout, null);
        alert.setView(view);

        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Save",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText newItemNameTxt = (EditText) view.findViewById(R.id.new_obj_name_txt);
                        String newItemName      = newItemNameTxt.getText().toString();

                        if (!newItemName.equals("")) {
                            ProximityListDBHelper dbHelper = new ProximityListDBHelper(getApplicationContext());
                            SQLiteDatabase db = dbHelper.getWritableDatabase();

                            ContentValues values = new ContentValues();
                            values.put(ProximityListContract.ProximityListItem.ITEM_NAME, newItemName);
                            values.put(ProximityListContract.ProximityListItem.LIST_ID, listId);

                            db.insert(ProximityListContract.ProximityListItem.TABLE_NAME,
                                    "null",
                                    values);

                            // Reload ListView:
                            Cursor c = dbHelper.getAllListItems(listId);
                            simpleCursorAdapter.swapCursor(c);

                            db.close();
                        }
                    }
                });

        alert.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alert.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog alert = new AlertDialog.Builder(ListSummaryActivity.this).create();
        alert.setTitle("Quit?");

        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Quit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListSummaryActivity.this.finish();
                        overridePendingTransition(0, 0);
                    }
                });

        alert.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alert.show();
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

    @Override
    public void finish() {
        super.finish();
    }
}
