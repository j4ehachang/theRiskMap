package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  List<Country> countryList = new ArrayList<Country>();
  boolean validCountryName = false;

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    for (String string : countries) {
      String[] countryParts = string.split(",");
      Country country = new Country(countryParts[0], countryParts[1], countryParts[2]);
      countryList.add(country);
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    String input = Utils.scanner.nextLine();

    while (validCountryName == false) {
      try {
        countryNameValid(input);
        validCountryName = true;
      } catch (InvalidCountryName e) {
        MessageCli.INVALID_COUNTRY.printMessage(Utils.capitalizeFirstLetterOfEachWord(input));
        input = Utils.scanner.nextLine();
      }
    }

    for (Country country : countryList) {
      if (Utils.capitalizeFirstLetterOfEachWord(input).equals(country.getName())) {
        MessageCli.COUNTRY_INFO.printMessage(
            country.getName(), country.getContinent(), country.getTaxFee());
      }
    }
  }

  public void countryNameValid(String name) throws InvalidCountryName {
    for (Country country : countryList) {
      if (Utils.capitalizeFirstLetterOfEachWord(name).equals(country.getName())) {
        return;
      }
    }
    throw new InvalidCountryName(name);
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
