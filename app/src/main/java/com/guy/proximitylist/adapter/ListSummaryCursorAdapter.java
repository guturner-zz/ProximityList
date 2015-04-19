package com.guy.proximitylist.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter;

import com.guy.proximitylist.R;
import com.guy.proximitylist.db.ProximityListContract;
import com.guy.proximitylist.db.ProximityListDBHelper;

import java.util.HashMap;

/**
 * Created by Guy on 3/23/2015.
 */
public class ListSummaryCursorAdapter extends SimpleCursorAdapter {

    private String listId;
    private Context context;
    private Cursor cursor;

    ListSummaryViewHolder vh;

    public ListSummaryCursorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to, int flags, String listId) {
        super(context, layout, cursor, from, to, flags);
        this.context = context;
        this.cursor  = cursor;
        this.listId = listId;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            vh = new ListSummaryViewHolder();
            vh.itemTxt  = (TextView) convertView.findViewById(R.id.list_summary_item_txt);
            vh.checkBtn = (Button) convertView.findViewById(R.id.list_summary_check_btn);

            vh.checkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProximityListDBHelper dbHelper = new ProximityListDBHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    Cursor c1 = db.rawQuery("select * from " + ProximityListContract.ProximityListItem.TABLE_NAME, null);
                    c1.moveToPosition(position);
                    int deleteRow = c1.getInt(0);
                    c1.close();

                    db.delete(ProximityListContract.ProximityListItem.TABLE_NAME,
                            ProximityListContract.ProximityListItem._ID + " = " + deleteRow,
                            null);

                    // Reload ListView:
                    Cursor c2 = dbHelper.getAllListItems(listId);
                    swapCursor(c2);

                    db.close();
                }
            });
        }

        return super.getView(position, convertView, parent);
    }

    static class ListSummaryViewHolder {
        TextView itemTxt;
        Button checkBtn;
    }
}
