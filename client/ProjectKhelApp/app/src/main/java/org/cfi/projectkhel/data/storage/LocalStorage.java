package org.cfi.projectkhel.data.storage;

import android.util.Log;

import org.cfi.projectkhel.AttendanceConstants;
import org.cfi.projectkhel.data.DataStorage;
import org.cfi.projectkhel.data.FileStorageUtils;
import org.cfi.projectkhel.model.Entry;
import org.cfi.projectkhel.model.LocationEntry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Fetches master data from the local store into structures required for UI display.
 *
 */
public class LocalStorage implements DataStorage {

  private FileStorageUtils storageHandler;

  public LocalStorage(FileStorageUtils pStorageHandler) {
    storageHandler = pStorageHandler;
  }

  @Override
  public List<Entry> getLocations() {
    final String data = storageHandler.readFileData(FileStorageUtils.FILE_LOCATIONS);
    return getEntries(data, LOCATIONS);
  }

  @Override
  public List<Entry> getCoordinators() {
    final String data = storageHandler.readFileData(FileStorageUtils.FILE_COORDINATORS);
    return getEntries(data, COORDINATORS);
  }

  @Override
  public List<LocationEntry> getBeneficiaries() {
    final String data = storageHandler.readFileData(FileStorageUtils.FILE_BENEFICIARIES);
    return getLocationEntries(data, BENEFICIARIES);
  }

  @Override
  public List<Entry> getModules() {
    final String data = storageHandler.readFileData(FileStorageUtils.FILE_MODULES);
    return getEntries(data, MODULES);
  }

  /**
   * For any generic type of Entry
   * @param fileData actual data in the storage for this entry
   * @param rootNode name of the entry (locations, modules, etc)
   * @return List format
   */
  private List<Entry> getEntries(String fileData, String rootNode) {
    Log.d(AttendanceConstants.TAG, "Getting entries for " + rootNode);
//    Log.d(AttendanceConstants.TAG, "Data:: " + fileData);
    final List<Entry> entries = new ArrayList<>();
    try {
      final JSONObject json = new JSONObject(new JSONTokener(fileData));
      final JSONArray arr = json.getJSONArray(rootNode);
      for (int i = 0; i < arr.length(); i++) {
        final JSONObject jsonEntry = 	arr.getJSONObject(i);
        entries.add(Entry.newEntry(jsonEntry.getInt("id"), jsonEntry.getString("name")));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    Log.d(AttendanceConstants.TAG, "Entries: " + entries);
    return entries;
  }

  /**
   * For specific to Location entries required for Beneficiaries.
   * @param fileData actual data in the storage for this entry
   * @param rootNode name of the entry (locations, modules, etc)
   * @return List format
   */
  private List<LocationEntry> getLocationEntries(String fileData, String rootNode) {
//    Log.d(AttendanceConstants.TAG, "Data:: " + fileData);
    final List<LocationEntry> entries = new ArrayList<>();
    try {
      final JSONObject json = new JSONObject(new JSONTokener(fileData));
      final JSONArray arr = json.getJSONArray(rootNode);
      for (int i = 0; i < arr.length(); i++) {
        final JSONObject jsonEntry = 	arr.getJSONObject(i);
        entries.add(LocationEntry.newEntry(jsonEntry.getInt("id"),
                    getBeneficiaryNameTag(jsonEntry),
                    jsonEntry.getInt("location_id")));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return entries;
  }

  private String getBeneficiaryNameTag(final JSONObject jsonEntry) {
    final StringBuilder sb = new StringBuilder();
    sb.append(jsonEntry.optString("name"));
    sb.append(" ");
    sb.append(jsonEntry.optString("class", ""));
    sb.append(" ");
    sb.append(jsonEntry.optString("age", ""));
    sb.append(" ");
    sb.append(jsonEntry.optString("sex", ""));
    return sb.toString();
  }
}
