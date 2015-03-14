package org.cfi.projectkhel.data.storage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import org.cfi.projectkhel.data.Attendance;

/**
 * Manages Android Internal storage for all the master data including attendances.
 */
public class FileStorageHandler {

  public static String FILE_LOCATIONS = "locations.khel";
  public static String FILE_MODULES = "modules.khel";
  public static String FILE_BENEFICIARIES = "beneficiaries.khel";
  public static String FILE_COORDINATORS = "coordinators.khel";
  public static String FILE_ATTENDANCE = "attendances.khel";
  private Context context;

  public FileStorageHandler(Context pContext) {
    context = pContext;
  }

  public String readFileData(String fileName) {
    final StringBuilder stringBuffer = new StringBuilder();
    try {
      //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader
      final BufferedReader inputReader = new BufferedReader(new InputStreamReader(
          context.openFileInput(fileName)));
      String inputString;
      //Reading data line by line and storing it into the stringbuffer
      while ((inputString = inputReader.readLine()) != null) {
        stringBuffer.append(inputString + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringBuffer.toString();
  }

  public List<String> readFileDataLines(String fileName) {
    final List<String> list = new ArrayList<>();
    try {
      //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader
      final BufferedReader inputReader = new BufferedReader(new InputStreamReader(
          context.openFileInput(fileName)));
      String inputString;
      //Reading data line by line and storing it into the stringbuffer
      while ((inputString = inputReader.readLine()) != null) {
        list.add(inputString);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
  }

  public boolean writeFileData(String fileName, String data, boolean append) {
    boolean status = true;
    FileOutputStream fos;
    try {
      final int mode;
      if (append) {
        mode = Context.MODE_APPEND;
      } else {
        mode = Context.MODE_PRIVATE;
      }
      fos = context.openFileOutput(fileName, mode);
      fos.write(data.getBytes());
      fos.close();
    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
      status = false;
    } catch (IOException ex) {
      ex.printStackTrace();
      status = false;
    }
    return status;
  }

}
