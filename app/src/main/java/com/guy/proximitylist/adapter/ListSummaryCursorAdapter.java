package com.guy.proximitylist.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import com.guy.proximitylist.R;

/**
 * Created by Guy on 3/23/2015.
 */
public class ListSummaryCursorAdapter extends SimpleCursorAdapter {

    private Cursor cursor;

    public ListSummaryCursorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to, int flags) {
        super(context, layout, cursor, from, to, flags);
        this.cursor = cursor;
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
            ListSummaryViewHolder vh = new ListSummaryViewHolder();
            vh.itemTxt = (EditText) convertView.findViewById(R.id.list_summary_item_txt);

            vh.itemTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        System.out.println(position + ": Lost focus.");
                    }
                }
            });
        }

        return super.getView(position, convertView, parent);
    }

    static class ListSummaryViewHolder {
        EditText itemTxt;
    }
}
