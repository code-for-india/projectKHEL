package org.cfi.projectkhel.rest;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.cfi.projectkhel.AttendanceConstants;
import org.cfi.projectkhel.data.Attendance;
import org.cfi.projectkhel.data.Entry;
import org.cfi.projectkhel.data.storage.FileStorageHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Handles syncing of data with Server or storing it locally for later sync.
 * No refresh of internal data structures is done here.
 */
public class MasterDataFetcher {

  private static final int MIN_LENGTH = 10;
  private static final int LOC_INDEX = 0;
  private static final int COOR_INDEX = 1;
  private static final int MOD_INDEX = 2;
  private static final int BENF_INDEX = 3;

  private FileStorageHandler storageHandler;
  private String masterSyncData;

  public MasterDataFetcher(FileStorageHandler pStorageHandler) {
    storageHandler = pStorageHandler;
  }

  /**
   * Pulls all the master data from the server.
   */
  public void pullMasterData() {
    // TODO - Criteria to determine if data is already up to date.
    // Fetch the old records first.
    masterSyncData = storageHandler.readFileData(FileStorageHandler.FILE_MASTER_SYNC);

    Log.d(AttendanceConstants.TAG, "Pulling Master data: " + masterSyncData);

    // Fetch the new ones only (dont update).
    fetchAndStore("mastersync", null, FileStorageHandler.FILE_MASTER_SYNC, false);
  }

  /**
   * Pushes attendance to Server and if no connection, appends it to internal storage for later sync.
   * @param data
   */
  public void pushAttendanceData(Attendance data) {
    postAttendance(data.toJSON());
  }

  public void pushOfflineAttendanceData() {
    Log.d(AttendanceConstants.TAG, "Pushing offline Attendances");
    List<String> attendances = storageHandler.readFileDataLines(FileStorageHandler.FILE_ATTENDANCE);
    if (attendances.size() == 0) {
      Log.d(AttendanceConstants.TAG, "No offline attendances to sync");
    } else {
      // Clear the file since we don't want these to be submitted again.
      storageHandler.emptyFile(FileStorageHandler.FILE_ATTENDANCE);
      for (String s : attendances) {
        postAttendance(s);
      }
    }
  }

  /**
   * Write attendance to internal storage
   * @param jsonData pass json form (attendance.toJSON())
   */
  public void writeAttendanceData(String jsonData) {
    Log.d(AttendanceConstants.TAG, "Saving attendance for offline sync " + jsonData);
    storageHandler.writeFileData(FileStorageHandler.FILE_ATTENDANCE, jsonData + "\n", true);
  }

  private void fetchAndStore(final String relativeURL, final RequestParams params,
                             final String fileName, final boolean store) {
    // Initiate REST request.
    RestClient.get(relativeURL, params, new TextHttpResponseHandler() {
      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        // TODO
        Log.e(AttendanceConstants.TAG, "Error fetching data from server ");
        throwable.printStackTrace();
      }

      @Override
      public void onSuccess(int statusCode, Header[] headers, String responseString) {
        Log.d(AttendanceConstants.TAG, "Got REST Response: " + responseString);
        if (store) {
          storageHandler.writeFileData(fileName, responseString, false);
          Log.d(AttendanceConstants.TAG, "Wrote to file: " + fileName);
        }

        if (FileStorageHandler.FILE_MASTER_SYNC.equals(fileName)) {
          fetchMasters(responseString);
        }
      }
    });
  }

  private void fetchMasters(String newMasterSyncData) {
    boolean result[] = compareSyncTimes(masterSyncData, newMasterSyncData);

    if (result[LOC_INDEX]) {
      Log.d(AttendanceConstants.TAG, "Fetching locations");
      fetchAndStore("locations", null, FileStorageHandler.FILE_LOCATIONS, true);
    } else {
      Log.d(AttendanceConstants.TAG, "Locations already up to date");
    }
    if (result[MOD_INDEX]) {
      Log.d(AttendanceConstants.TAG, "Fetching modules");
      fetchAndStore("modules", null, FileStorageHandler.FILE_MODULES, true);
    } else {
      Log.d(AttendanceConstants.TAG, "modules already up to date");
    }
    if (result[COOR_INDEX]) {
      Log.d(AttendanceConstants.TAG, "Fetching coordinators");
      fetchAndStore("coordinators", null, FileStorageHandler.FILE_COORDINATORS, true);
    } else {
      Log.d(AttendanceConstants.TAG, "coordinators already up to date");
    }
    if (result[BENF_INDEX]) {
      Log.d(AttendanceConstants.TAG, "Fetching beneficiaries");
      fetchAndStore("beneficiaries", null, FileStorageHandler.FILE_BENEFICIARIES, true);
    } else {
      Log.d(AttendanceConstants.TAG, "beneficiaries already up to date");
    }

    storageHandler.writeFileData(FileStorageHandler.FILE_MASTER_SYNC, newMasterSyncData, false);
  }

  private void postAttendance(final String attendanceData) {
    try {
      Log.d(AttendanceConstants.TAG, "Posting Attendance data to server" + attendanceData);
      RestClient.post("attendance", new StringEntity(attendanceData), new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
          // If the response is JSONObject instead of expected JSONArray
          Log.d(AttendanceConstants.TAG, "Post attendance to Server. Got " + response);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
          Log.d(AttendanceConstants.TAG, "Error posting attendance to Server. Got " + throwable.toString());
          // Append for later sync
          writeAttendanceData(attendanceData);
        }
      });
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  private boolean[] compareSyncTimes(String oldSyncTimes, String newSyncTimes) {
    Log.d(AttendanceConstants.TAG, "Comparing sync times");
    boolean result[] = new boolean[4];
    // In exception cases, the data gets reloaded (esp first time)
    Arrays.fill(result, Boolean.TRUE);
    try {
      final JSONArray oldJsonArr = new JSONObject(new JSONTokener(oldSyncTimes)).getJSONArray("mastersync");
      final JSONArray newJsonArr = new JSONObject(new JSONTokener(newSyncTimes)).getJSONArray("mastersync");

      for (int i = 0; i < oldJsonArr.length(); i++) {
        final JSONObject oldJsonEntry = oldJsonArr.getJSONObject(i);
        final JSONObject newJsonEntry = newJsonArr.getJSONObject(i);
        // Assumptions is that entries are ordered the same way in both old and new
        int index = getIndexForName(oldJsonEntry.getString("name"));
        result[index] = isDataNewer(oldJsonEntry, newJsonEntry, "synctime");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  private boolean isDataNewer(JSONObject oldJson, JSONObject newJson, String key) throws JSONException {
    Log.d(AttendanceConstants.TAG, "New = " + newJson.getInt(key) + " Old = " + oldJson.getInt(key));
    return newJson.getInt(key) > oldJson.getInt(key);
  }

  private int getIndexForName(String name) {
    switch (name) {
      case "locations":
        return LOC_INDEX;
      case "coordinators":
        return COOR_INDEX;
      case "modules":
        return MOD_INDEX;
      case "beneficiaries":
        return BENF_INDEX;
    }
    // AIOOB Exception it if not valid
    return -1;
  }
}
