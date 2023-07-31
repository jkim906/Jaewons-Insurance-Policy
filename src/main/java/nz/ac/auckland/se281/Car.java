package nz.ac.auckland.se281;

public class Car extends Policy {
  private String make;
  private String licensePlate;
  private boolean mechanicalBreaks;

  public Car(int sumInsured, String make, String licensePlate, boolean mechanicalBreaks, int age) {
    super(sumInsured);
    this.make = make;
    this.licensePlate = licensePlate;
    this.mechanicalBreaks = mechanicalBreaks;

    if (age < 25) {
      this.basePremium = (15 * sumInsured) / 100;
    } else {
      this.basePremium = (10 * sumInsured) / 100;
    }
    if (mechanicalBreaks) {
      basePremium = basePremium + 80;
    }
  }

  public String getMake() {
    return make;
  }
}
