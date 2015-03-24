package org.cfi.projectkhel.data;

/**
 * Entry class. Note this class is immutable.
 */
public class Entry {
  private Integer id;
  private String name;

  Entry(Integer pId, String pName) {
    id = pId;
    name = pName;
  }

  public static Entry newEntry(Integer pId, String pName) {
    return new Entry(pId, pName);
  }

  public Integer getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Entry{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }

  public String getName() {
    return name;
  }
}
