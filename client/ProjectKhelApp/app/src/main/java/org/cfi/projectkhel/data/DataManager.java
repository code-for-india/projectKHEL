package org.cfi.projectkhel.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the master Data and keeps a local cached copy.
 */
public class DataManager {

  private List<Entry> locations;
  private List<Entry> coordinators;
  private List<Entry> modules;
  private List<LocationEntry> beneficiaries;
  private DataStorage storage;

  private static DataManager instance = new DataManager();

  private DataManager() {
  }

  public void initialize(DataStorage pStorage) {
    storage = pStorage;
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
      if (entry.locationId.equals(locationId)) {
        locBeneficiaries.add(entry);
      }
    }
    return locBeneficiaries;
  }
}
