package nz.ac.auckland.se281;

/** This class is for the exception that need to be thrown when country name is invalid. */
public class InvalidCountryName extends Exception {

  private String countryName;

  /**
   * Constructor for the exception class.
   *
   * @param countryName The country name that is invalid.
   */
  public InvalidCountryName(String countryName) {
    super(countryName);
    this.countryName = countryName;
  }

  /**
   * Returns the country name.
   *
   * @return the country name.
   */
  public String getCountryName() {
    return countryName;
  }
}
