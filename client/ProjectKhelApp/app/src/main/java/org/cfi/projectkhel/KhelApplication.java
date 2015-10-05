package org.cfi.projectkhel;

import android.app.Application;
import android.content.Context;

import org.cfi.projectkhel.data.DataManager;
import org.cfi.projectkhel.data.DataStorage;
import org.cfi.projectkhel.data.DataUtils;
import org.cfi.projectkhel.data.storage.LocalStorage;
import org.cfi.projectkhel.model.Attendance;
import org.cfi.projectkhel.model.Entry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Maintains the application wide instances.
 */
public class KhelApplication extends Application {

  private MasterDataFetcher dataFetcher;

  // List of saved attendances.
  private Set<Attendance> savedAttendances;

  public void init(Context context, MasterDataFetcher dataFetcher) {
    this.dataFetcher = dataFetcher;
    // Initialize attendances.
    savedAttendances = dataFetcher.getStorageHandler().readAttendances();
    if (savedAttendances == null) {
      savedAttendances = new HashSet<>();
      dataFetcher.getStorageHandler().saveAttendances(savedAttendances);
    }

    // Initialize storage
//    DataStorage storage = new TestDataStorage();
    final DataStorage storage = new LocalStorage(dataFetcher.getStorageHandler());
    DataManager.getInstance().initialize(storage);
  }

  private MasterDataFetcher getDataFetcher() {
    return dataFetcher;
  }

  public void submitAttendance(Attendance attendance) {
    dataFetcher.pushAttendanceData(attendance);
    // Remove from saved list if it was in it.
    if (savedAttendances.remove(attendance)) {
      dataFetcher.getStorageHandler().saveAttendances(savedAttendances);
    }
  }

  public void saveAttendance(Attendance attendance) {
    // Set will prevent duplicate adds here.
    savedAttendances.add(attendance);
    // Save updated info from attendance if there is any.
    dataFetcher.getStorageHandler().saveAttendances(savedAttendances);
  }

  public Set<Attendance> getSavedAttendances() {
    return savedAttendances;
  }

  // Kept here to avoid multiple lookups
  public CharSequence [] getAttendanceNames() {
    final CharSequence[] data = new CharSequence[savedAttendances.size()];
    final List<Entry> locations =  DataManager.getInstance().getLocations();
    int i = 0;
    for (Attendance at : savedAttendances) {
      data[i++] = getSavedAttendanceName(at, DataUtils.getSelectedNameFromId(at.getLocation(), locations));
    }
    return data;
  }

  public Attendance getSelectedAttendance(int index) {
    return (Attendance) savedAttendances.toArray()[index];
  }

  private String getSavedAttendanceName(final Attendance at, final String location) {
    StringBuilder sb = new StringBuilder();
    sb.append(at.getDate());
    sb.append(" - ");
    sb.append(location);
    return sb.toString();
  }
}
