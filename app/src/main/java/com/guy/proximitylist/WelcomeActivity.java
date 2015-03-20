package com.guy.proximitylist;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.guy.proximitylist.content.ListEntry;
import com.guy.proximitylist.db.ProximityListContract;
import com.guy.proximitylist.db.ProximityListDBHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class WelcomeActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String DB_NAME = "proximitylist";

    private static final long MIN_DIST_DELTA_FOR_UPDATE = 1;
    private static final long MIN_TIME_TWEEN_UPDATES = 1000;

    private static final long PROX_RADIUS = 1000;
    private static final long PROX_EXPIRE = -1;

    private static final String POINT_LATITUDE_KEY  = "POINT_LATITUDE_KEY";
    private static final String POINT_LONGITUDE_KEY = "POINT_LONGITUDE_KEY";

    private static final String PROX_ALERT_INTENT = "com.guy.ProximityAlert";

    private static final NumberFormat nf = new DecimalFormat("##.########");

    private LocationManager locationManager;

    private EditText latitudeEditTxt;
    private EditText longitudeEditTxt;
    private Button findCoordBtn;
    private Button saveCoordBtn;
    private ListView lv;

    private Button newListBtn;

    private SimpleCursorAdapter simpleCursorAdapter;

    static final String[] projection = new String[] {
        ProximityListContract.ProximityListEntry._ID,
        ProximityListContract.ProximityListEntry.ENTRY_NAME
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        /*
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                               MIN_TIME_TWEEN_UPDATES,
                                               MIN_DIST_DELTA_FOR_UPDATE,
                                               new MyLocationListener());
        */

        //latitudeEditTxt  = (EditText)findViewById(R.id.latitude_txt);
        //longitudeEditTxt = (EditText)findViewById(R.id.longitude_txt);

        //findCoordBtn     = (Button)findViewById(R.id.find_coord_btn);
        //saveCoordBtn     = (Button)findViewById(R.id.save_coord_btn);

        //locOneBtn = (Button)findViewById(R.id.loc_1_btn);
        //locTwoBtn = (Button)findViewById(R.id.loc_2_btn);

        // Maps DB columns to listview elements:
        String[] fromCols = { ProximityListContract.ProximityListEntry._ID, ProximityListContract.ProximityListEntry.ENTRY_NAME };
        int[] toViews     = { 0, R.id.list_name };

        // Default adapter to be changed in onLoadFinished:
        simpleCursorAdapter = new SimpleCursorAdapter(
                getBaseContext(),
                R.layout.list_layout,
                null, // DB Cursor. To be updated later
                fromCols,
                toViews,
                0
        );
        lv = (ListView) findViewById(R.id.lists_lv);
        lv.setAdapter(simpleCursorAdapter);

        // Start a new loader:
        getLoaderManager().initLoader(0, null, this);

        newListBtn = (Button)findViewById(R.id.new_list_btn);

        newListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewList("ABC");
            }
        });

        /*
        findCoordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateCoordFromLastLoc();
            }
        });
        */

        /*
        saveCoordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCoordForAlert();
            }
        });
        */

    }

    private void createNewList(String listName) {
        ProximityListDBHelper dbHelper = new ProximityListDBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProximityListContract.ProximityListEntry.ENTRY_NAME, listName);

        db.insert(ProximityListContract.ProximityListEntry.TABLE_NAME,
                  "null",
                  values);

        // Reload ListView:
        Cursor c = dbHelper.getAllListEntries();
        simpleCursorAdapter.swapCursor(c);

        System.out.println(DatabaseUtils.queryNumEntries(db, ProximityListContract.ProximityListEntry.TABLE_NAME));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
            this,
            ListEntry.CONTENT_URI,
            projection,
            null,
            null,
            null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the cursor into the cursor adapter:
        simpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Called when the cursor provided in onLoadFinished is no longer needed:
        simpleCursorAdapter.swapCursor(null);
    }

    /*
    private void saveCoordForAlert() {
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (loc == null) {
            Toast.makeText(this, "No last known location.", Toast.LENGTH_LONG).show();
            return;
        }

        saveCoordInPreferences((float)loc.getLatitude(), (float)loc.getLongitude());
        addProximityAlert(loc.getLatitude(), loc.getLongitude());
        Toast.makeText(this, "Got loc.", Toast.LENGTH_LONG).show();
    }
    */

    /*
    private void saveCoordInPreferences(float latitude, float longitude) {
        SharedPreferences pref = this.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);

        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putFloat(POINT_LATITUDE_KEY, latitude);
        prefEditor.putFloat(POINT_LONGITUDE_KEY, longitude);
        prefEditor.commit();
    }
    */

    /*
    private void addProximityAlert(double latitude, double longitude) {
        Intent intent = new Intent(PROX_ALERT_INTENT);
        PendingIntent proxIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        locationManager.addProximityAlert(latitude, longitude, PROX_RADIUS, PROX_EXPIRE, proxIntent);

        IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
        registerReceiver(new ProximityIntentReceiver(), filter);
    }
    */

    /*
    private void populateCoordFromLastLoc() {
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (loc != null) {
            latitudeEditTxt.setText(nf.format(loc.getLatitude()));
            longitudeEditTxt.setText(nf.format(loc.getLongitude()));
        }
    }
    */

    /*
    private Location retrieveLocFromPreferences() {
        SharedPreferences pref = this.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);

        Location loc = new Location("POINT_LOC");
        loc.setLatitude(pref.getFloat(POINT_LATITUDE_KEY, 0));
        loc.setLongitude(pref.getFloat(POINT_LONGITUDE_KEY, 0));

        return loc;
    }
    */

    /*
    public class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Location pointLoc = retrieveLocFromPreferences();

            float distance = location.distanceTo(pointLoc);
            Toast.makeText(WelcomeActivity.this, "Distance from Point: " + distance, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
    */
}
