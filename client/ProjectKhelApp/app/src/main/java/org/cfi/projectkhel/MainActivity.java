package org.cfi.projectkhel;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.cfi.projectkhel.data.Attendance;
import org.cfi.projectkhel.data.DataManager;
import org.cfi.projectkhel.data.storage.FileStorageHandler;
import org.cfi.projectkhel.rest.MasterDataFetcher;

/**
 * Main Activity of the Application.
 */
public class MainActivity extends ActionBarActivity {

  static final int MAIN_ACTIVITY = 0x1001;
  private Button mAttendanceButton;
  private Button mSyncButton;
  private MasterDataFetcher masterDataFetcher;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mAttendanceButton = (Button) findViewById(R.id.attendanceButton);
    mSyncButton = (Button) findViewById(R.id.syncButton);

    // Get the application instance
    final KhelApplication app = (KhelApplication) getApplication();
    final FileStorageHandler fileStorageHandler = new FileStorageHandler(this);
    masterDataFetcher = new MasterDataFetcher(fileStorageHandler);
    app.setStorageHandler(fileStorageHandler);
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
      masterDataFetcher.pullMasterData();
      masterDataFetcher.pushOfflineAttendanceData();
      DataManager.getInstance().loadAll();
    } else {
      Log.i(AttendanceConstants.TAG, getString(R.string.nonetwork));
      Toast.makeText(this, getString(R.string.nonetwork), Toast.LENGTH_SHORT).show();
    }
  }

  public boolean isConnected() {
    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      return true;
    } else {
      return false;
    }
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
