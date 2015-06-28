package org.cfi.projectkhel.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Data holder for attendance. Useful to post JSON to server.
 */
public class Attendance {

  private String date;
  private int location;
  private List<Integer> coordinators;
  private List<Integer> modules;
  private List<Integer> beneficiaries;
  private String comments;
//  private int rating;
  private String userId;
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

//  public int getRating() {
//    return rating;
//  }
//
//  public void setRating(int rating) {
//    this.rating = rating;
//  }

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
//      jsonObj.put("rating", rating);
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

    System.out.println(at1);
    //System.out.println(at1.toJSON());
  }


}
