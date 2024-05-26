package nz.ac.auckland.se281;

/** This class is for the country type. */
public class Country {

  private String name;
  private String continent;
  private String taxFee;

  /**
   * This is the constructor for this country type.
   *
   * @param name String for the name of the country.
   * @param continent String for the continent of the country's location.
   * @param taxFee String to show the taxfee for this country.
   */
  public Country(String name, String continent, String taxFee) {
    this.name = name;
    this.continent = continent;
    this.taxFee = taxFee;
  }

  /**
   * This method returns the name of the country.
   *
   * @return return the string for the name.
   */
  public String getName() {
    return name;
  }

  /**
   * This method returns the continent.
   *
   * @return returns the string for the continent.
   */
  public String getContinent() {
    return continent;
  }

  /**
   * This method returns the taxfee.
   *
   * @return returns string for taxfee.
   */
  public String getTaxFee() {
    return taxFee;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Country other = (Country) obj;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }
}
