package nz.ac.auckland.se281;

public class Country {

  private String name;
  private String continent;
  private String taxFee;

  public Country(String name, String continent, String taxFee) {
    this.name = name;
    this.continent = continent;
    this.taxFee = taxFee;
  }

  public String getName() {
    return name;
  }

  public String getContinent() {
    return continent;
  }

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
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Country other = (Country) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Country [name=" + name +  "]";
  }

}
