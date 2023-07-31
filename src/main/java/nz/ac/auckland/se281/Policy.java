package nz.ac.auckland.se281;

public abstract class Policy {

  protected int sumInsured;
  protected int basePremium;

  public Policy(int sumInsured) {
    this.sumInsured = sumInsured;
  }

  public int getSumInsured() {
    return sumInsured;
  }

  public int getBasePremium() {
    return basePremium;
  }
}
