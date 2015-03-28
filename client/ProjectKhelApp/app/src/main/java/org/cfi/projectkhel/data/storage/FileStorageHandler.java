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
 * Provides read and write functions to manage the data.
 */
public class FileStorageHandler {

  public static String FILE_MASTER_SYNC = "mastersync.khel";
  public static String FILE_LOCATIONS = "locations.khel";
  public static String FILE_MODULES = "modules.khel";
  public static String FILE_BENEFICIARIES = "beneficiaries.khel";
  public static String FILE_COORDINATORS = "coordinators.khel";
  public static String FILE_ATTENDANCE = "attendances.khel";
  private Context context;

  public FileStorageHandler(Context pContext) {
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
      //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader
      final BufferedReader inputReader = new BufferedReader(new InputStreamReader(
          context.openFileInput(fileName)));
      String inputString;
      //Reading data line by line and storing it into the stringbuffer
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
      //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader
      final BufferedReader inputReader = new BufferedReader(new InputStreamReader(
          context.openFileInput(fileName)));
      String inputString;
      //Reading data line by line and storing it into the stringbuffer
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
}
