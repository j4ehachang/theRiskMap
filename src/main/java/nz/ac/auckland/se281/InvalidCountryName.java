package nz.ac.auckland.se281;

public class InvalidCountryName extends Exception {

  private String countryName;

  public InvalidCountryName(String countryName) {
    super(countryName);
    this.countryName = countryName;
  }

  public String getCountryName() {
    return countryName;
  }
}
