package org.cfi.projectkhel.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Data holder for attendance. Useful to post JSON to server.
 */
public final class Attendance {

  private String userId;
  private String date;
  private int location;
  private List<Integer> coordinators;
  private List<Integer> modules;
  private List<Integer> beneficiaries;
  private String comments;
  // New mode of transport
  private String modeOfTransport;
  // New debriefing
  private String debriefWhatWorked;
  private String debriefToImprove;
  private String debriefDidntWork;
  // New ratings
  private int ratingSessionObjectives;
  private int ratingOrgObjectives;
  private int ratingFunForKids;
  // used for field validation.
  enum MyType { INT, STR, LST};

  private Attendance() {
    coordinators = new ArrayList<>();
    modules = new ArrayList<>();
    beneficiaries = new ArrayList<>();
  }

  public Attendance(String pUserId) {
    this();
    userId = pUserId;
  }

  public Attendance clone() {
    return new Attendance(this.userId);
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setDate(int year, int monthOfYear, int dayOfMonth) {
    setDate(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
  }

  public int getLocation() {
    return location;
  }

  public void setLocation(int location) {
    this.location = location;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public void addCoordinator(int coordinatorId) {
    coordinators.add(coordinatorId);
  }

  public void addCoordinators(List<Integer> coordinatorList) {
    coordinators.clear();
    coordinators.addAll(coordinatorList);
  }

  public List<Integer> getCoordinators() {
    return coordinators;
  }

  public void addModule(int moduleId) {
    modules.add(moduleId);
  }

  public void addModules(List<Integer> moduleList) {
    modules.clear();
    modules.addAll(moduleList);
  }

  public List<Integer> getModules() {
    return modules;
  }

  public void addBeneficiary(int beneficiaryId) {
    beneficiaries.add(beneficiaryId);
  }

  public void addBeneficiaries(List<Integer> beneficiaryList) {
    beneficiaries.clear();
    beneficiaries.addAll(beneficiaryList);
  }

  public List<Integer> getBeneficiaries() {
    return beneficiaries;
  }

  public int getRatingFunForKids() {
    return ratingFunForKids;
  }

  public void setRatingFunForKids(int ratingFunForKids) {
    this.ratingFunForKids = ratingFunForKids;
  }

  public String getModeOfTransport() {
    return modeOfTransport;
  }

  public void setModeOfTransport(String modeOfTransport) {
    this.modeOfTransport = modeOfTransport;
  }

  public String getDebriefWhatWorked() {
    return debriefWhatWorked;
  }

  public void setDebriefWhatWorked(String debriefWhatWorked) {
    this.debriefWhatWorked = debriefWhatWorked;
  }

  public String getDebriefToImprove() {
    return debriefToImprove;
  }

  public void setDebriefToImprove(String debriefToImprove) {
    this.debriefToImprove = debriefToImprove;
  }

  public String getDebriefDidntWork() {
    return debriefDidntWork;
  }

  public void setDebriefDidntWork(String debriefDidntWork) {
    this.debriefDidntWork = debriefDidntWork;
  }

  public int getRatingSessionObjectives() {
    return ratingSessionObjectives;
  }

  public void setRatingSessionObjectives(int ratingSessionObjectives) {
    this.ratingSessionObjectives = ratingSessionObjectives;
  }

  public int getRatingOrgObjectives() {
    return ratingOrgObjectives;
  }

  public void setRatingOrgObjectives(int ratingOrgObjectives) {
    this.ratingOrgObjectives = ratingOrgObjectives;
  }

  /**
   * Validate all the fields and return name of missing field.
   * @return missing field name
   */
  public String validate() {
    String error = null;
    try {
      checkValue(date, MyType.STR, "Date");
      checkValue(location, MyType.INT, "Location");
      checkValue(coordinators, MyType.LST, "Coordinators");
      checkValue(beneficiaries, MyType.LST, "Beneficiaries");
      checkValue(modules, MyType.LST, "Modules");

      checkValue(ratingSessionObjectives, MyType.INT, "Rating:Session Objectives");
      checkValue(ratingOrgObjectives, MyType.INT, "Rating:Org Objectives");
      checkValue(ratingFunForKids, MyType.INT, "Rating:Fun For Kids");

      checkValue(modeOfTransport, MyType.STR, "Mode Of Transport");
      checkValue(debriefWhatWorked, MyType.STR, "Debriefing:What Worked");
      checkValue(debriefToImprove, MyType.STR, "Debriefing:To Improve");
      checkValue(debriefDidntWork, MyType.STR, "Debriefing:Didnt Work");
      checkValue(comments, MyType.STR, "Comments");
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    return error;
  }

  private void checkValue(Object value, MyType type, String name) {
    switch (type) {
      case INT:
        if (((Integer)value).intValue() == 0) {
          throw new IllegalArgumentException(name);
        }
        break;
      case STR:
        if (value == null || ((String)value).isEmpty()) {
          throw new IllegalArgumentException(name);
        }
        break;
      case LST:
        if (value == null || ((List<Integer>)value).isEmpty()) {
          throw new IllegalArgumentException(name);
        }
        break;
    }
  }

  /**
   * Convert in the following form
    {
        "date": "2014-02-19",
        "location": 10,
        "coordinators": "2,5,7,9",
        "modules": "88,10,100",
        "beneficiaries": "Alice, Bob, Smith, Jane",
        "comments": "Wonderful demo again",
        "rating": 8,
        "userid": "james"
    }
  */
  public String toJSON() {
    final JSONObject jsonObj = new JSONObject();
    try {
      jsonObj.put("date", date);
      jsonObj.put("location", location);
      jsonObj.put("coordinators", listToCSV(coordinators));
      jsonObj.put("modules", listToCSV(modules));
      jsonObj.put("beneficiaries", listToCSV(beneficiaries));
      jsonObj.put("comments", comments);
      jsonObj.put("userid", userId);
      jsonObj.put("modeoftransport", modeOfTransport);
      jsonObj.put("debriefwhatworked", debriefWhatWorked);
      jsonObj.put("debrieftoimprove", debriefToImprove);
      jsonObj.put("debriefdidntwork", debriefDidntWork);
      jsonObj.put("ratingsessionobjectives", ratingSessionObjectives);
      jsonObj.put("ratingorgobjectives", ratingOrgObjectives);
      jsonObj.put("ratingfunforkids", ratingFunForKids);

    } catch (JSONException e) {
      // TODO - Handle this exception
      e.printStackTrace();
    }
    return jsonObj.toString();
  }

  @Override
  public String toString() {
    return "Attendance{" +
        "date='" + date + '\'' +
        ", location=" + location +
        ", coordinators=" + coordinators +
        ", modules=" + modules +
        ", beneficiaries=" + beneficiaries +
        ", comments='" + comments + '\'' +
        ", userId='" + userId + '\'' +
        ", modeOfTransport='" + modeOfTransport + '\'' +
        ", debriefWhatWorked='" + debriefWhatWorked + '\'' +
        ", debriefToImprove='" + debriefToImprove + '\'' +
        ", debriefDidntWork='" + debriefDidntWork + '\'' +
        ", ratingSessionObjectives=" + ratingSessionObjectives +
        ", ratingOrgObjectives=" + ratingOrgObjectives +
        ", ratingFunForKids=" + ratingFunForKids +
        '}';
  }

  private static String listToCSV(List<Integer> list) {
    final String str = list.toString();
    return str.replaceAll("\\[|\\]", "");
  }

  public static void main(String[] args) {
    List<Integer> list = new ArrayList<>();
    list.add(100);list.add(101);list.add(200);list.add(300);list.add(77);
    System.out.println("List ..= " + Attendance.listToCSV(list));

    Attendance at1 = new Attendance("james");
    at1.setDate(2015, 2, 20);
    at1.setLocation(999);
    at1.addCoordinator(9);at1.addCoordinator(55);at1.addCoordinator(32);
    at1.addModule(8);at1.addModule(228);at1.addModule(972);
    at1.addBeneficiary(33);at1.addBeneficiary(44);at1.addBeneficiary(55);at1.addBeneficiary(66);
    at1.setComments("Great Event");
    at1.setRatingFunForKids(3);at1.setRatingSessionObjectives(4);at1.setRatingOrgObjectives(3);

    System.out.println(at1);

    System.out.println("Validate::" + at1.validate());
    //System.out.println(at1.toJSON());
  }

}
