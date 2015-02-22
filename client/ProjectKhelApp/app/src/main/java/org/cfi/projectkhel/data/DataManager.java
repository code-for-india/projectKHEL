package org.cfi.projectkhel.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the master Data
 */
public class DataManager {

  private List<Entry> locations;
  private List<Entry> coordinators;
  private List<Entry> modules;
  private List<Entry> beneficiaries;

  private static DataManager instance = new DataManager();

  private DataManager() {
    coordinators = new ArrayList<>();
    locations = new ArrayList<>();
    beneficiaries = new ArrayList<>();
    modules = new ArrayList<>();
    populateDummyData();
  }

  public static DataManager getInstance() {
    return instance;
  }

  public List<Entry> getLocations() {
    return locations;
  }

  public List<Entry> getCoordinators() {
    return coordinators;
  }

  public List<Entry> getBeneficiaries() {
    return beneficiaries;
  }

  public List<Entry> getModules() {
    return modules;
  }

  private void populateDummyData() {
    coordinators.add(Entry.newEntry(1, "Alice1"));
    coordinators.add(Entry.newEntry(2, "Alice2"));
    coordinators.add(Entry.newEntry(3, "Alice3"));
    coordinators.add(Entry.newEntry(6, "Alice6"));
    coordinators.add(Entry.newEntry(8, "Alice8"));
    coordinators.add(Entry.newEntry(10, "Alice10"));
    coordinators.add(Entry.newEntry(4, "Alice4"));
    coordinators.add(Entry.newEntry(20, "Alice20"));

    locations.add(Entry.newEntry(1, "School A"));
    locations.add(Entry.newEntry(2, "School B"));
    locations.add(Entry.newEntry(3, "School C"));
    locations.add(Entry.newEntry(4, "School D"));
    locations.add(Entry.newEntry(8, "School E"));

    modules.add(Entry.newEntry(5, "Module P"));
    modules.add(Entry.newEntry(4, "Module Q"));
    modules.add(Entry.newEntry(10, "Module R"));

    beneficiaries.add(Entry.newEntry(1, "Sam1"));
    beneficiaries.add(Entry.newEntry(5, "Jay5"));
    beneficiaries.add(Entry.newEntry(3, "Veeru3"));
    beneficiaries.add(Entry.newEntry(6, "Sum6"));
    beneficiaries.add(Entry.newEntry(8, "Greg8"));
    beneficiaries.add(Entry.newEntry(10, "Alice10"));
    beneficiaries.add(Entry.newEntry(4, "Jane4"));
    beneficiaries.add(Entry.newEntry(20, "Bob20"));
  }
}
