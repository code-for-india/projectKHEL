package org.cfi.projectkhel.data.storage;

import android.util.Log;

import org.cfi.projectkhel.AttendanceConstants;
import org.cfi.projectkhel.data.DataStorage;
import org.cfi.projectkhel.data.Entry;
import org.cfi.projectkhel.data.LocationEntry;
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

  private FileStorageHandler storageHandler;

  public LocalStorage(FileStorageHandler pStorageHandler) {
    storageHandler = pStorageHandler;
  }

  @Override
  public List<Entry> getLocations() {
    final String data = storageHandler.readFileData(FileStorageHandler.FILE_LOCATIONS);
    return getEntries(data, "locations");
  }

  @Override
  public List<Entry> getCoordinators() {
    final String data = storageHandler.readFileData(FileStorageHandler.FILE_COORDINATORS);
    return getEntries(data, "coordinators");
  }

  @Override
  public List<LocationEntry> getBeneficiaries() {
    final String data = storageHandler.readFileData(FileStorageHandler.FILE_BENEFICIARIES);
    return getLocationEntries(data, "beneficiaries");
  }

  @Override
  public List<Entry> getModules() {
    final String data = storageHandler.readFileData(FileStorageHandler.FILE_MODULES);
    return getEntries(data, "modules");
  }

  /**
   * For any generic type of Entry
   * @param fileData actual data in the storage for this entry
   * @param rootNode name of the entry (locations, modules, etc)
   * @return List format
   */
  private List<Entry> getEntries(String fileData, String rootNode) {
    Log.d(AttendanceConstants.TAG, "Getting entries for " + rootNode);
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
    final List<LocationEntry> entries = new ArrayList<>();
    try {
      final JSONObject json = new JSONObject(new JSONTokener(fileData));
      final JSONArray arr = json.getJSONArray(rootNode);
      for (int i = 0; i < arr.length(); i++) {
        final JSONObject jsonEntry = 	arr.getJSONObject(i);
        entries.add(LocationEntry.newEntry(jsonEntry.getInt("id"),
              jsonEntry.getString("name"), jsonEntry.getInt("location_id")));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return entries;
  }
}
