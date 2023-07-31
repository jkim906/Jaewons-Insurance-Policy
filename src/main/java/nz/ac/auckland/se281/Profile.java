package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Profile {
  private ArrayList<Policy> listOfPolicy = new ArrayList<Policy>();

  private String name;
  private String age;
  private boolean isLoaded;
  private boolean lifePolicy;

  public Profile(String name, String age) {
    this.name = name;
    this.age = age;
  }

  public void addPolicy(Policy policy) {
    listOfPolicy.add(policy);
  }

  public void setIsLoaded(boolean isLoaded) {
    this.isLoaded = isLoaded;
  }

  public void setLifePolicy(boolean lifePolicy) {
    this.lifePolicy = lifePolicy;
  }

  public String getName() {
    return name;
  }

  public String getAge() {
    return age;
  }

  public boolean getIsLoaded() {
    return this.isLoaded;
  }

  public boolean getLifePolicy() {
    return lifePolicy;
  }

  public ArrayList<Policy> getPolicy() {
    return listOfPolicy;
  }
}
