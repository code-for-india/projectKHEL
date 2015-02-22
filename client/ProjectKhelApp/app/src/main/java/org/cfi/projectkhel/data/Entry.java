package org.cfi.projectkhel.data;

/**
 * Entry class
 */
public class Entry {
  private Integer id;
  private String name;

  private Entry(Integer pId, String pName) {
    id = pId;
    name = pName;
  }

  public static Entry newEntry(Integer pId, String pName) {
    return new Entry(pId, pName);
  }

  public Integer getId() {
    return id;
  }

//  public void setId(Integer id) {
//    this.id = id;
//  }

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
//
//  public void setName(String name) {
//    this.name = name;
//  }
}
