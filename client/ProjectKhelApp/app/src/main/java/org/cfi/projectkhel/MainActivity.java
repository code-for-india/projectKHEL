package org.cfi.projectkhel;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

  static final int MAIN_ACTIVITY = 0x1001;
  private Button mAttendanceButton;
  private Button mSyncButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mAttendanceButton = (Button) findViewById(R.id.attendanceButton);
    mSyncButton = (Button) findViewById(R.id.syncButton);
  }

  public void onAttendanceClick(View v) {
    Intent intent = new Intent(this, AttendanceActivity.class);
    startActivityForResult(intent, MAIN_ACTIVITY);
  }

  public void onSyncClick(View v) {
    // TODO sync data
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
