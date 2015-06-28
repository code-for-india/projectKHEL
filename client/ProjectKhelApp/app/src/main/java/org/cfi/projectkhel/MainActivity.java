package org.cfi.projectkhel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.cfi.projectkhel.data.DataManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Main Activity of the Application.
 */
public class MainActivity extends ActionBarActivity {

  static final int MAIN_ACTIVITY = 0x1001;
  private MasterDataFetcher masterDataFetcher;
  private TextView lastSyncTime;
  private SharedPreferences sharedPref;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Get the application instance
    final KhelApplication app = (KhelApplication) getApplication();

    masterDataFetcher = new MasterDataFetcher(this);
    app.setDataFetcher(masterDataFetcher);

    sharedPref = getPreferences(Context.MODE_PRIVATE);
    lastSyncTime = (TextView) findViewById(R.id.lastSyncTextView);
    lastSyncTime.setText(sharedPref.getString(getString(R.string.lastsynckey), ""));
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
    masterDataFetcher.pullMasterData(true);
    masterDataFetcher.pushOfflineAttendanceData();
    DataManager.getInstance().loadAll();
//    progressDialog.dismiss();
    SharedPreferences.Editor editor = sharedPref.edit();
    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
    String date = df.format(Calendar.getInstance().getTime());

    editor.putString(getString(R.string.lastsynckey), date);
    editor.commit();
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
}
