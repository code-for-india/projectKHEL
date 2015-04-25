package org.cfi.projectkhel.data.storage;

import org.cfi.projectkhel.data.DataStorage;
import org.cfi.projectkhel.model.Entry;
import org.cfi.projectkhel.model.LocationEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates entries for UI using hardcoded data values.
 */
public class DummyStorage implements DataStorage {

  private List<Entry> locations;
  private List<Entry> coordinators;
  private List<Entry> modules;
  private List<LocationEntry> beneficiaries;

  public DummyStorage() {
    coordinators = new ArrayList<>();
    locations = new ArrayList<>();
    beneficiaries = new ArrayList<>();
    modules = new ArrayList<>();
    populateDummyData();
  }

  @Override
  public List<Entry> getLocations() {
    return locations;
  }

  @Override
  public List<Entry> getCoordinators() {
    return coordinators;
  }

  @Override
  public List<LocationEntry> getBeneficiaries() {
    return beneficiaries;
  }

  @Override
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

    beneficiaries.add(LocationEntry.newEntry(1, "Sam1", 1));
    beneficiaries.add(LocationEntry.newEntry(5, "Jay5", 1));
    beneficiaries.add(LocationEntry.newEntry(3, "Veeru3", 4));
    beneficiaries.add(LocationEntry.newEntry(6, "Sum6", 4));
    beneficiaries.add(LocationEntry.newEntry(8, "Greg8", 2));
    beneficiaries.add(LocationEntry.newEntry(10, "Alice10", 3));
    beneficiaries.add(LocationEntry.newEntry(4, "Jane4", 8));
    beneficiaries.add(LocationEntry.newEntry(20, "Bob20", 8));
  }
}
