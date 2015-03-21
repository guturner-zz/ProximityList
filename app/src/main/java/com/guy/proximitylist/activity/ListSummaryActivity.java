package com.guy.proximitylist.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.guy.proximitylist.R;

/**
 * Created by Guy on 3/21/2015.
 */
public class ListSummaryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_summary);

        // Receive intent:
        Intent intent   = getIntent();
        String listName = intent.getStringExtra(WelcomeActivity.EXTRA_LIST_NAME);

        TextView listNameLbl = (TextView) findViewById(R.id.list_name_lbl);
        listNameLbl.setText(listName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
