package nz.ac.auckland.se281;

public class Life extends Policy {

  public Life(int sumInsured, int age) {
    super(sumInsured);

    this.basePremium = (int) ((1 + (double) age / 100) * (double) sumInsured / 100);
  }
}
