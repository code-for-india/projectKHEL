package org.cfi.projectkhel.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.util.Log;

import org.cfi.projectkhel.model.Attendance;

import static org.cfi.projectkhel.AttendanceConstants.TAG;

/**
 * Manages Android Internal storage for all the master data including attendances.
 * Provides read and write functions to manage the data.
 */
public class FileStorageUtils {

  public static String FILE_MASTER_SYNC = "mastersync.khel";
  public static String FILE_LOCATIONS = "locations.khel";
  public static String FILE_MODULES = "modules.khel";
  public static String FILE_BENEFICIARIES = "beneficiaries.khel";
  public static String FILE_COORDINATORS = "coordinators.khel";
  public static String FILE_ATTENDANCE = "attendances.khel";
  private static String SAVED_ATTENDANCES = "saved_attendances.khel";

  private Context context;

  public FileStorageUtils(Context pContext) {
    context = pContext;
  }

  /**
   * Read data from a file.
   * @param fileName
   * @return file contents.
   */
  public String readFileData(String fileName) {
    final StringBuilder stringBuffer = new StringBuilder();
    try {
      final BufferedReader inputReader = new BufferedReader(new InputStreamReader(
          context.openFileInput(fileName)));
      String inputString;
      while ((inputString = inputReader.readLine()) != null) {
        stringBuffer.append(inputString + "\n");
      }
      inputReader.close();
    } catch (FileNotFoundException fnfe) {
      // If file is not found, create an empty one.
      emptyFile(fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringBuffer.toString();
  }

  /**
   * Read data from a file. Useful to fetch all offline attendances.
   * @param fileName
   * @return file contents separated line by line
   */
  public List<String> readFileDataLines(String fileName) {
    final List<String> list = new ArrayList<>();
    try {
      final BufferedReader inputReader = new BufferedReader(new InputStreamReader(
          context.openFileInput(fileName)));
      String inputString;
      while ((inputString = inputReader.readLine()) != null) {
        list.add(inputString);
      }
      inputReader.close();
    }  catch (FileNotFoundException fnfe) {
      // If file is not found, create an empty one.
      emptyFile(fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
  }

  /**
   * Writes string contents to the file.
   * @param fileName
   * @param data
   * @param append
   * @return true if data was written without errors.
   */
  public boolean writeFileData(String fileName, String data, boolean append) {
    boolean status = true;
    final int mode = append ? Context.MODE_APPEND : Context.MODE_PRIVATE;

    try {
      FileOutputStream fos = context.openFileOutput(fileName, mode);
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

  /**
   * Clears the file.
   * @param fileName
   * @return
   */
  public boolean emptyFile(String fileName) {
    boolean status = true;
    try {
      FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
//      fos.write(0);
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

  // Serializes the attendances and saves it to a file
  public void saveAttendances(Set<Attendance> attendances) {
    try {
      final FileOutputStream fos = context.openFileOutput(SAVED_ATTENDANCES, Context.MODE_PRIVATE);
      final ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(attendances);
      oos.close();
      fos.close();
      Log.d(TAG, "Saved # attendance: " + attendances.size());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Creates an object by reading it from a file
  public Set<Attendance> readAttendances() {
    Set<Attendance> attendances = null;
    try {
      FileInputStream fileInputStream = context.openFileInput(SAVED_ATTENDANCES);
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
      attendances = (Set<Attendance>) objectInputStream.readObject();
      objectInputStream.close();
      fileInputStream.close();
      Log.d(TAG, "Read # attendance: " + attendances.size());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return attendances;
  }
}
