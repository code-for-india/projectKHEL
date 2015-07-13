package org.cfi.projectkhel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.cfi.projectkhel.data.DataManager;

/**
 * Main Activity of the Application.
 */
public class MainActivity extends ActionBarActivity implements AppContext {

  static final int MAIN_ACTIVITY = 0x1001;

  private KhelApplication app;
  private MasterDataFetcher masterDataFetcher;
  private TextView mLastMasterSyncTime;
  private TextView mLastAttendanceSyncTime;
  private TextView mOfflineRecords;
  private Handler mHandler;

  private SharedPreferences sharedPref;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    sharedPref = getPreferences(Context.MODE_PRIVATE);
    mHandler = new Handler();

    // Get the application instance
    app = (KhelApplication) getApplication();
    masterDataFetcher = new MasterDataFetcher(this, sharedPref);
    app.setDataFetcher(masterDataFetcher);

    mLastMasterSyncTime = (TextView) findViewById(R.id.lastMasterSyncTextView);
    mLastAttendanceSyncTime = (TextView) findViewById(R.id.lastAttendanceSyncTextView);
    mOfflineRecords = (TextView) findViewById(R.id.offlineRecsTextView);

    // Not sure if this is the best place to load all data
    DataManager.getInstance().loadAll();
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (isConnected()) {
      masterDataFetcher.pushOfflineAttendanceData();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    updateUISyncTimes();
  }

  public void onAttendanceClick(View v) {
    if (DataManager.getInstance().isDataPopulated()) {
      Intent intent = new Intent(this, AttendanceActivity.class);
      startActivityForResult(intent, MAIN_ACTIVITY);
    } else {
      // No data populated.
      Toast.makeText(this, getString(R.string.nomastersync), Toast.LENGTH_LONG).show();
      onSyncClick(v);
    }
  }

  public void onSyncClick(View v) {
     if (isConnected()) {
       syncData();
    } else {
      Log.i(AttendanceConstants.TAG, getString(R.string.nonetwork));
      Toast.makeText(this, getString(R.string.nonetwork), Toast.LENGTH_SHORT).show();
    }
  }

  private boolean isConnected() {
    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    return networkInfo != null && networkInfo.isConnected();
  }

  private void syncData() {
//    final ProgressDialog progressDialog = ProgressDialog.show(this, "", "Sync in progress ...", true);
    Toast.makeText(this, getString(R.string.sync_now), Toast.LENGTH_LONG).show();
    masterDataFetcher.pullMasterData(true);
    masterDataFetcher.pushOfflineAttendanceData();
    DataManager.getInstance().loadAll();
//    progressDialog.dismiss();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
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
  public void updateUISyncTimes() {
    mHandler.postDelayed(new Runnable() {
       @Override
       public void run() {
         mLastMasterSyncTime.setText(getString(R.string.last_master_sync) + " "
             + sharedPref.getString(AttendanceConstants.KEY_MASTER_SYNC, ""));
         mLastAttendanceSyncTime.setText(getString(R.string.last_att_sync) + " "
             + sharedPref.getString(AttendanceConstants.KEY_ATTENDANCE_SYNC, ""));
         mOfflineRecords.setText(getString(R.string.offline_recs) + " "
             + masterDataFetcher.getOfflineAttendances());
       }
     }, 100);
  }

  @Override
  public Context getContext() {
    return this;
  }
}
