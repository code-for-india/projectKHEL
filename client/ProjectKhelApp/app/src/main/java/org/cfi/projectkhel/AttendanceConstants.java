package org.cfi.projectkhel;

public interface AttendanceConstants {
  String TAG = "projectkhel";

  int ROW_DATE = 0;
  int ROW_LOCATION = 1;
  int ROW_COORDINATORS = 2;
  int ROW_BENEFICIARIES = 3;
  int ROW_MODULES = 4;
  int ROW_RATING = 5;
  int ROW_MODEOFTRANSPORT = 6;
  int ROW_DEFRIEFING = 7;
  int ROW_COMMENTS = 8;

  // Keys into Shared Preferences
  String KEY_MASTER_SYNC     = "app.master.sync";
  String KEY_ATTENDANCE_SYNC = "app.att.sync";

  String KEY_SELECTED_IDX = "sel.attendance.idx";
}
