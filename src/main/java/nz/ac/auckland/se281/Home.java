package nz.ac.auckland.se281;

public class Home extends Policy {
  private String address;
  private boolean rental;

  public Home(int sumInsured, String address, boolean rental) {
    super(sumInsured);
    this.address = address;
    this.rental = rental;

    if (rental) {
      this.basePremium = (2 * sumInsured) / 100;
    } else {
      this.basePremium = (1 * sumInsured) / 100;
    }
  }

  public String getAddress() {
    return address;
  }

  public boolean getRental() {
    return rental;
  }
}
