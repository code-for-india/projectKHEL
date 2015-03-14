package org.cfi.projectkhel.rest;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.cfi.projectkhel.AttendanceConstants;
import org.cfi.projectkhel.data.Attendance;
import org.cfi.projectkhel.data.storage.FileStorageHandler;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Handles syncing of data with Server or storing it locally for later sync.
 * No refresh of internal data structures is done here.
 */
public class MasterDataFetcher {

  private static final int MIN_LENGTH = 10;
  private FileStorageHandler storageHandler;

  public MasterDataFetcher(FileStorageHandler pStorageHandler) {
    storageHandler = pStorageHandler;
  }

  /**
   * Pulls all the master data from the server.
   */
  public void pullMasterData() {
    // TODO - Criteria to determine if data is already up to date.

    fetchAndStore("locations", null, FileStorageHandler.FILE_LOCATIONS);
    fetchAndStore("modules", null, FileStorageHandler.FILE_MODULES);
    fetchAndStore("coordinators", null, FileStorageHandler.FILE_COORDINATORS);
    fetchAndStore("beneficiaries", null, FileStorageHandler.FILE_BENEFICIARIES);
  }

  /**
   * Pushes attendance to Server and if no connection, appends it to internal storage for later sync.
   * @param data
   */
  public void pushAttendanceData(Attendance data) {
    postAttendance(data.toJSON());
  }

  public void pushOfflineAttendanceData() {
    List<String> attendances = storageHandler.readFileDataLines(FileStorageHandler.FILE_ATTENDANCE);
    if (attendances.size() == 0) {
      Log.d(AttendanceConstants.TAG, "No offline attendances to sync");
    } else {
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

  private void fetchAndStore(final String relativeURL, final RequestParams params, final String fileName) {
    RestClient.get(relativeURL, params, new TextHttpResponseHandler() {
      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        // TODO
      }

      @Override
      public void onSuccess(int statusCode, Header[] headers, String responseString) {
        storageHandler.writeFileData(fileName, responseString, false);
      }
    });
  }

  private void postAttendance(final String attendanceData) {
    try {
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
}
