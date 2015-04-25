package org.cfi.projectkhel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.cfi.projectkhel.data.DataManager;

/**
 * Main Activity of the Application.
 */
public class MainActivity extends ActionBarActivity {

  static final int MAIN_ACTIVITY = 0x1001;
  private MasterDataFetcher masterDataFetcher;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Get the application instance
    final KhelApplication app = (KhelApplication) getApplication();

    masterDataFetcher = new MasterDataFetcher(this);
    app.setDataFetcher(masterDataFetcher);

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
