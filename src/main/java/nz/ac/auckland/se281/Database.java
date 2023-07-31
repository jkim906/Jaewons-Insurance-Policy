package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Main.PolicyType;

public class Database {

  private ArrayList<Profile> listOfProfiles = new ArrayList<Profile>();

  /* method to count how many username in database to present the list of usernames
  and age in the correct form */
  public void countDataBase() {
    if (listOfProfiles.isEmpty()) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(
          "0", "s", "."); // if there is no profile in the database
    } else if (listOfProfiles.size() == 1) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(
          "1", "", ":"); // if there is only 1 profile in the database
      profileList();
    } else if (listOfProfiles.size() >= 2) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(
          Integer.toString(listOfProfiles.size()),
          "s",
          ":"); // if there is more than 1 profile in the database
      profileList();
    }
  }

  // method to print the database including their name, age, number of policies and total premium.
  // Also shows which profile is loaded
  private void profileList() {
    int i = 1;
    for (Profile person : listOfProfiles) {
      // printing the correct message for if the profile is loaded and has more than 1 policy or no
      // policy
      if ((person.getIsLoaded() == true)
          && ((person.getPolicy().size() == 0) || (person.getPolicy().size() > 1))) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            "*** ",
            Integer.toString(i),
            person.getName(),
            person.getAge(),
            Integer.toString(person.getPolicy().size()),
            "ies",
            Integer.toString(totalBasePremium(person)));
        printPolicy(person);
        // printing the correct message for if the profile is loaded and has 1 policy
      } else if ((person.getIsLoaded() == true) && ((person.getPolicy().size() == 1))) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            "*** ",
            Integer.toString(i),
            person.getName(),
            person.getAge(),
            Integer.toString(person.getPolicy().size()),
            "y",
            Integer.toString(totalBasePremium(person)));
        printPolicy(person);
        // printing the correct message for if the profile is not loaded and has more than 1 policy
        // or no policy
      } else if ((person.getIsLoaded() == false)
          && ((person.getPolicy().size() == 0) || (person.getPolicy().size() > 1))) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            "",
            Integer.toString(i),
            person.getName(),
            person.getAge(),
            Integer.toString(person.getPolicy().size()),
            "ies",
            Integer.toString(totalBasePremium(person)));
        printPolicy(person);
        // printing the correct message for if the profile is not loaded and has 1 policy.
      } else if ((person.getIsLoaded() == false) && ((person.getPolicy().size() == 1))) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            "",
            Integer.toString(i),
            person.getName(),
            person.getAge(),
            Integer.toString(person.getPolicy().size()),
            "y",
            Integer.toString(totalBasePremium(person)));
        printPolicy(person);
      }
      i++;
    }
  }

  // method to create profile into arraylist if username and age is valid
  public void createProfile(String name, String age) {
    if (checkIfLoaded()) {
      if ((checkUserLength(name, age)) && (checkAge(name, age) && ifUnique(name, age)) == true) {
        Profile profileInstance = new Profile(titleCase(name), age);
        listOfProfiles.add(profileInstance); // add profile to ProfileList
        MessageCli.PROFILE_CREATED.printMessage(titleCase(name), age);
      } else {
        return;
      }
    } else {
      // if profile is loaded already, print error message.
      MessageCli.CANNOT_CREATE_WHILE_LOADED.printMessage(titleCase(getLoadedName()));
    }
  }

  // next three sub methods is used to check if the username is valid
  // checking if the username is atleast three characters long
  private boolean checkUserLength(String name, String age) {
    if (name.length() > 2) {
      return true;
    } else {
      MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(titleCase(name));
      return false;
    }
  }

  // checking if the age is a positive integer using an if statement
  private boolean checkAge(String name, String age) {
    if (Integer.parseInt(age) >= 0) {
      return true;
    } else {
      MessageCli.INVALID_AGE.printMessage(age, titleCase(name));
      return false;
    }
  }

  // checking if username is unique by going through all the profiles in the profileList
  private boolean ifUnique(String name, String age) {
    for (Profile instance : listOfProfiles) {
      if (instance.getName().equals(titleCase(name))) {
        // print error message if username is not unique
        MessageCli.INVALID_USERNAME_NOT_UNIQUE.printMessage(titleCase(name));
        return false;
      } else {
        return true;
      }
    }
    return true;
  }

  // sub-method to return a string in title case.
  private String titleCase(String name) {
    String firstLetter = name.substring(0, 1);
    String restLetter = name.substring(1);
    String capitalise1 = firstLetter.toUpperCase();
    String capitalise2 = restLetter.toLowerCase();

    return capitalise1 + capitalise2;
  }

  // method to load profile if the profile is not already loaded, if no profile is loaded then error
  // message is printed
  public void loadingProfile(String name) {
    for (Profile instance : listOfProfiles) {
      if (instance.getName().equals(titleCase(name))) {
        for (Profile instance2 : listOfProfiles) {
          if (instance2.getIsLoaded() == true) {
            instance2.setIsLoaded(false);
          }
        }
        // if profile is found to load, print message and set the profile to loaded
        MessageCli.PROFILE_LOADED.printMessage(titleCase(name));
        instance.setIsLoaded(true);
        return;
      }
    }
    // if no profile is found to load, print error message
    MessageCli.NO_PROFILE_FOUND_TO_LOAD.printMessage(titleCase(name));
  }

  private boolean checkIfTrue(String name) {

    // check if an instance of profile that has input name is loaded
    for (Profile instance : listOfProfiles) {
      if (instance.getName().equals(titleCase(name)) && instance.getIsLoaded()) {
        return true;
      }
    }
    return false;
  }

  // method to unload profile and if there is no profile loaded error message is printed
  public void unloadingprofile() {
    for (Profile instance : listOfProfiles) {
      if (instance.getIsLoaded() == true) {
        MessageCli.PROFILE_UNLOADED.printMessage(instance.getName());
        instance.setIsLoaded(false);
        return;
      }
    }
    MessageCli.NO_PROFILE_LOADED.printMessage();
  }

  // method to delete profile if the profile is not loaded or matches name in database otherwise
  // error message is printed
  public void deletingProfile(String name) {
    // username matches in system
    if (checkIfProfileExist(name)) {

      // if profile is loaded
      if (checkIfTrue(name)) {
        MessageCli.CANNOT_DELETE_PROFILE_WHILE_LOADED.printMessage(titleCase(name));

        // if profile is not loaded
      } else {
        MessageCli.PROFILE_DELETED.printMessage(titleCase(name));
        listOfProfiles.remove(listOfProfiles.get(getProfileIndex(name)));
      }

      // username does not match in system
    } else {
      MessageCli.NO_PROFILE_FOUND_TO_DELETE.printMessage(titleCase(name));
    }
  }

  // method to check if profile is loaded returning a boolean
  public boolean checkIfLoaded() {
    for (Profile instance : listOfProfiles) {
      if (instance.getIsLoaded() == true) {
        return false;
      }
    }
    return true;
  }

  // getter method to return the name of the loaded profile
  public String getLoadedName() {
    for (Profile instance : listOfProfiles) {
      if (instance.getIsLoaded()) {
        return instance.getName();
      }
    }
    return null;
  }

  // submethod to retrieve the index of the profile in the arraylist
  private int getProfileIndex(String name) {
    for (Profile instance : listOfProfiles) {
      if (instance.getName().equals(titleCase(name))) {
        return listOfProfiles.indexOf(instance);
      }
    }
    return -1;
  }

  // submethod to check if profile exist in the system by checking if the name inputted matches the
  // name in the profileList
  private boolean checkIfProfileExist(String name) {
    for (Profile instance : listOfProfiles) {
      if (instance.getName().equals(titleCase(name))) {
        return true;
      }
    }
    return false;
  }

  // submethod to check if age is valid for life insurance(Age cannot be over 100)
  private boolean policyAgeCheck(String age) {
    for (Profile instance : listOfProfiles) {
      if (instance.getIsLoaded() == true) {
        // if age is less than or equal to 100 return true
        if (Integer.parseInt(age) <= 100) {
          return true;
          // if age is over 100 return false
        } else {
          MessageCli.OVER_AGE_LIMIT_LIFE_POLICY.printMessage(instance.getName());
          return false;
        }
      }
    }
    return false;
  }

  // submethod to check if profile has a life insurance policy already.(Can only have 1 life policy)
  private boolean checkIfOnePolicy() {
    for (Profile instance : listOfProfiles) {
      if (instance.getIsLoaded() == true) {
        if (instance.getLifePolicy()) {
          MessageCli.ALREADY_HAS_LIFE_POLICY.printMessage(instance.getName());
          return false;
        }
      }
    }
    return true;
  }

  // submethod to retrieve the loaded profile instance to use in creating policy
  private Profile getLoadedProfile() {
    for (Profile instance : listOfProfiles) {
      if (instance.getIsLoaded() == true) {
        return instance;
      }
    }
    return null;
  }

  // method to create a new policy and add it to the loaded profile, if no profile is loaded or
  // found an error message is printed
  public void creatingPolicy(PolicyType type, String[] options) {
    if (getLoadedProfile() != null) {
      // store the right user inputs into Home Policy in the loaded profile
      if (type == PolicyType.HOME) {
        boolean rental = titleCase(options[2]).startsWith("Y");
        Policy policyInstance = new Home(Integer.parseInt(options[0]), options[1], rental);
        getLoadedProfile().addPolicy(policyInstance);
        MessageCli.NEW_POLICY_CREATED.printMessage("home", getLoadedProfile().getName());
        // store the right user inputs into Car Policy in the loaded profile
      } else if (type == PolicyType.CAR) {
        boolean mechanicalBreaks = titleCase(options[3]).startsWith("Y");
        Policy policyInstance =
            new Car(
                Integer.parseInt(options[0]),
                options[1],
                options[2],
                mechanicalBreaks,
                Integer.parseInt(getLoadedProfile().getAge()));
        getLoadedProfile().addPolicy(policyInstance);
        MessageCli.NEW_POLICY_CREATED.printMessage("car", getLoadedProfile().getName());
        // store the right user inputs into LIFE Policy in the loaded profile
      } else if (type == PolicyType.LIFE) {
        // check if age is valid and if profile already has a life policy before creating a new life
        // policy
        if ((policyAgeCheck(getLoadedProfile().getAge())) && (checkIfOnePolicy())) {
          Policy policyInstance =
              new Life(Integer.parseInt(options[0]), Integer.parseInt(getLoadedProfile().getAge()));
          getLoadedProfile().addPolicy(policyInstance);
          MessageCli.NEW_POLICY_CREATED.printMessage("life", getLoadedProfile().getName());
          getLoadedProfile().setLifePolicy(true);
        }
        return;
      }
    } else {
      // if no profile is loaded, print error message
      MessageCli.NO_PROFILE_FOUND_TO_CREATE_POLICY.printMessage();
    }
  }

  // method to print the policies of the loaded profiles for each policy type(home, car, life)
  public void printPolicy(Profile instance) {
    for (Policy policy : instance.getPolicy()) {
      // printing home policy details
      if (policy instanceof Home) {
        Home home = (Home) policy;
        MessageCli.PRINT_DB_HOME_POLICY.printMessage(
            home.getAddress(),
            Integer.toString(policy.getSumInsured()),
            Integer.toString(policy.getBasePremium()),
            Integer.toString((discountPrice(instance, home.getBasePremium()))));
        // printing car policy details
      } else if (policy instanceof Car) {
        Car car = (Car) policy;
        MessageCli.PRINT_DB_CAR_POLICY.printMessage(
            car.getMake(),
            Integer.toString(policy.getSumInsured()),
            Integer.toString(policy.getBasePremium()),
            Integer.toString((discountPrice(instance, car.getBasePremium()))));
        // printing life policy details
      } else if (policy instanceof Life) {
        Life life = (Life) policy;
        MessageCli.PRINT_DB_LIFE_POLICY.printMessage(
            Integer.toString(policy.getSumInsured()),
            Integer.toString(policy.getBasePremium()),
            Integer.toString((discountPrice(instance, life.getBasePremium()))));
      }
    }
  }

  // submethod to calculate the discounted total base premium of the loaded profile
  public int discountPrice(Profile profile, int basePremium) {
    if (profile.getPolicy().size() == 2) {
      basePremium = (int) (0.9 * basePremium);
    } else if (profile.getPolicy().size() >= 3) {
      basePremium = (int) (0.8 * basePremium);
    }
    return basePremium;
  }

  // method to calculate the total base premium of the loaded profile by adding the base premium of
  // each policy together and applying the discounts
  private int totalBasePremium(Profile instance) {
    int total = 0;
    for (Policy policy : instance.getPolicy()) {
      total += policy.getBasePremium();
    }

    total = discountPrice(instance, total);

    return total;
  }
}
