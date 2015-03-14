package org.cfi.projectkhel.data;

import java.util.List;

public interface DataStorage {

  public List<Entry> getLocations();

  public List<Entry> getCoordinators();

  public List<LocationEntry> getBeneficiaries();

  public List<Entry> getModules();
}
