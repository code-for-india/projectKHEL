package org.cfi.projectkhel.data;

import android.util.Log;

import org.cfi.projectkhel.AttendanceConstants;
import org.cfi.projectkhel.model.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to set selected entries.
 */
public final class DataUtils {

  public static CharSequence [] getEntryNames(List<? extends Entry> entries) {
    final CharSequence[] data = new CharSequence[entries.size()];
    int i = 0;
    for (Entry e : entries) {
      data[i++] = e.getName();
    }
    return data;
  }

  public static List<Integer> getAllEntryIds(List<? extends Entry> entries) {
    final List<Integer> data = new ArrayList<>(entries.size() * 2);
    for (Entry e : entries) {
      data.add(e.getId());
    }
    return data;
  }

  public static Integer getIdFromSelectedItem(Integer selItem, List<? extends Entry> entryList) {
    return entryList.get(selItem).getId();
  }

  public static List<Integer> getIdsFromSelectedItems(List<Integer> selectedItems, List<? extends Entry> entryList) {
//    Log.d(AttendanceConstants.TAG, "In getIdsFromSelectedItems...");
    final List<Integer> data = new ArrayList<>(selectedItems.size() * 2);
    for (Integer id : selectedItems) {
      data.add(entryList.get(id).getId());
    }
//    Log.d(AttendanceConstants.TAG, "Selected: " + selectedItems);
//    Log.d(AttendanceConstants.TAG, "Now AttendanceIDs: " + data);
    return data;
  }

  public static Integer getSelectedItemFromId(Integer selId, List<? extends Entry> entryList) {
    int i = 0;
    for (Entry entry : entryList) {
      if (entry.getId().equals(selId)) {
        break;
      }
      i++;
    }
    return i;
  }

  public static boolean[] getSelectedItemsFromIds(List<Integer> selectedIds, List<? extends Entry> entryList) {
//    Log.d(AttendanceConstants.TAG, "In getSelectedItemsFromIds...");
    final boolean[] data = new boolean[entryList.size()];
    int i = 0;
    for (Entry entry : entryList) {
      int myId = entry.getId();
      // Check if in selected list
      //Log.d(AttendanceConstants.TAG, "Checking for " + myId);
      data[i++] = selectedIds.contains(myId);
    }
//    Log.d(AttendanceConstants.TAG, "AttendanceIds: " + selectedIds);
//    Log.d(AttendanceConstants.TAG, "Now Selected: " + Arrays.toString(data));
    return data;
  }

  public static List<Integer> getSelectedItemsFromBoolList(boolean[] list) {
    final List<Integer> data = new ArrayList<>(list.length + 10);
    int i = 0;
    for (boolean b : list) {
      if (b) {
        data.add(i);
      }
      i++;
    }
    //Log.d(AttendanceConstants.TAG, "getSelectedItemsFromBoolList: " + data);
    return data;
  }

}
