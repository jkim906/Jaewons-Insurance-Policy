package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {
  Database database = new Database();

  public InsuranceSystem() {
    // Only this constructor can be used (if you need to initialise fields).
  }

  public void printDatabase() {
    database.countDataBase();
  }

  public void createNewProfile(String userName, String age) {
    database.createProfile(userName, age);
  }

  public void loadProfile(String userName) {
    database.loadingProfile(userName);
  }

  public void unloadProfile() {
    database.unloadingprofile();
  }

  public void deleteProfile(String userName) {
    database.deletingProfile(userName);
  }

  public void createPolicy(PolicyType type, String[] options) {
    database.creatingPolicy(type, options);
  }
}
