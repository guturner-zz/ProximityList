package com.guy.proximitylist.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import com.guy.proximitylist.R;
import com.guy.proximitylist.db.ProximityListContract;
import com.guy.proximitylist.db.ProximityListDBHelper;

import java.util.HashMap;

/**
 * Created by Guy on 3/23/2015.
 */
public class ListSummaryCursorAdapter extends SimpleCursorAdapter {

    private Context context;

    ListSummaryViewHolder vh;

    public ListSummaryCursorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to, int flags) {
        super(context, layout, cursor, from, to, flags);
        this.context = context;
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
            vh.itemTxt = (EditText) convertView.findViewById(R.id.list_summary_item_txt);
        }

        return super.getView(position, convertView, parent);
    }

    static class ListSummaryViewHolder {
        EditText itemTxt;
    }
}
