package org.cfi.projectkhel.data;

import org.cfi.projectkhel.data.storage.FileDataStorage;
import org.cfi.projectkhel.data.storage.TestDataStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the master Data
 */
public class DataManager {

  private List<Entry> locations;
  private List<Entry> coordinators;
  private List<Entry> modules;
  private List<LocationEntry> beneficiaries;

  private static DataManager instance = new DataManager();

  private DataStorage storage;

  private DataManager() {
    storage = new TestDataStorage();
//    storage = new FileDataStorage();
  }

  public static DataManager getInstance() {
    return instance;
  }

  public List<Entry> getLocations() {
    if (locations == null) {
      locations = storage.getLocations();
    }
    return locations;
  }

  public List<Entry> getCoordinators() {
    if (coordinators == null) {
      coordinators = storage.getCoordinators();
    }
    return coordinators;
  }

  public List<Entry> getModules() {
    if (modules == null) {
      modules = storage.getModules();
    }
    return modules;
  }

  public List<LocationEntry> getBeneficiaries() {
    if (beneficiaries == null) {
      beneficiaries = storage.getBeneficiaries();
    }
    return beneficiaries;
  }

  public List<Entry> getBeneficiariesForLocation(Integer locationId) {
    List<Entry> locBeneficiaries = new ArrayList<>();
    for (LocationEntry entry : getBeneficiaries()) {
      if (entry.locationId == locationId) {
        locBeneficiaries.add(entry);
      }
    }
    return locBeneficiaries;
  }
}
