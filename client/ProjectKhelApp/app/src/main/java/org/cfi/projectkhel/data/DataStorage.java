package org.cfi.projectkhel.data;

import org.cfi.projectkhel.model.Entry;
import org.cfi.projectkhel.model.LocationEntry;

import java.util.List;

public interface DataStorage {

  String LOCATIONS = "locations";
  String COORDINATORS = "coordinators";
  String BENEFICIARIES = "beneficiaries";
  String MODULES = "modules";

  public List<Entry> getLocations();

  public List<Entry> getCoordinators();

  public List<LocationEntry> getBeneficiaries();

  public List<Entry> getModules();
}
