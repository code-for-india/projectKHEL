package org.cfi.projectkhel.model;

/**
 * Tracks the beneficiary entries.
 */
public class LocationEntry extends Entry {

  Integer locationId;

  LocationEntry(Integer pId, String pName, Integer pLocationId) {
    super(pId, pName);
    locationId = pLocationId;
  }

  public static LocationEntry newEntry(Integer pId, String pName, Integer pLocationId) {
    return new LocationEntry(pId, pName, pLocationId);
  }

  @Override
  public String toString() {
    return "LocationEntry{" +
        "locationId=" + locationId + super.toString() +
        '}';
  }

  public Integer getLocationId() {
    return locationId;
  }
}
