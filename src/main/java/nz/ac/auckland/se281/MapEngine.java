package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {
  private List<Country> countryList = new ArrayList<Country>();
  private boolean validCountryName;
  private Graph graph = new Graph();
  private Country startCountry;
  private Country endCountry;
  private List<Country> countryPath = new ArrayList<>();
  private Map<String, Country> countryMap = new HashMap<>();
  private Country fixedCountry;
  private StringBuilder route = new StringBuilder();
  private List<String> continentList = new ArrayList<>();
  private StringBuilder continentRoute = new StringBuilder();
  private int totalTax;

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    // Go through the file to create an instance for each country
    for (String string : countries) {
      String[] countryParts = string.split(",");
      Country country = new Country(countryParts[0], countryParts[1], countryParts[2]);
      countryList.add(country);
      graph.addNode(country);
      countryMap.putIfAbsent(country.getName(), country);
    }

    // Go through the files and add all the countries and their adjacent countries to the graph
    for (String string : adjacencies) {
      String[] adjParts = string.split(",");
      fixedCountry = countryMap.get(adjParts[0]);

      for (int i = 1; i < adjParts.length; i++) {
        graph.addEdge(fixedCountry, countryMap.get(adjParts[i]));
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    String input = Utils.scanner.nextLine();

    // Throw an exception when the country given by the user is invalid
    validCountryName = false;
    while (validCountryName == false) {
      try {
        countryNameValid(input);
        validCountryName = true;
      } catch (InvalidCountryName e) {
        MessageCli.INVALID_COUNTRY.printMessage(Utils.capitalizeFirstLetterOfEachWord(input));
        input = Utils.scanner.nextLine();
      }
    }

    // Find the country that matches the name with input and print info
    for (Country country : countryList) {
      if (Utils.capitalizeFirstLetterOfEachWord(input).equals(country.getName())) {
        MessageCli.COUNTRY_INFO.printMessage(
            country.getName(), country.getContinent(), country.getTaxFee());
      }
    }
  }

  /**
   * This method is used to check whether a string is a valid country name.
   *
   * @param name String for the name of the country.
   * @throws InvalidCountryName Exception type for when invalid name is given.
   */
  public void countryNameValid(String name) throws InvalidCountryName {
    for (Country country : countryList) {
      if (Utils.capitalizeFirstLetterOfEachWord(name).equals(country.getName())) {
        return;
      }
    }
    throw new InvalidCountryName(name);
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    MessageCli.INSERT_SOURCE.printMessage();
    String start = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());

    // Check if start country name is valid
    validCountryName = false;
    while (validCountryName == false) {
      try {
        countryNameValid(start);
        validCountryName = true;
      } catch (InvalidCountryName e) {
        MessageCli.INVALID_COUNTRY.printMessage(start);
        start = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());
      }
    }

    MessageCli.INSERT_DESTINATION.printMessage();
    String destination = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());

    // Check if destination country is valid
    validCountryName = false;
    while (validCountryName == false) {
      try {
        countryNameValid(destination);
        validCountryName = true;
      } catch (InvalidCountryName e) {
        MessageCli.INVALID_COUNTRY.printMessage(destination);
        destination = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());
      }
    }

    if (start.equals(destination)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    // Assign the corresponding countries to the given inputs by user
    for (Country country : countryList) {
      if (start.equals(country.getName())) {
        startCountry = country;
      }
    }
    for (Country country : countryList) {
      if (destination.equals(country.getName())) {
        endCountry = country;
      }
    }

    countryPath = graph.findShortestPath(startCountry, endCountry);

    totalTax = 0;

    // Update the infomation about the countries on the route and their taxfees
    for (Country country : countryPath) {
      if (!country.equals(countryPath.get(countryPath.size() - 1))) {
        route.append(country.getName() + ", ");
      } else {
        route.append(country.getName());
      }

      if (!continentList.contains(country.getContinent())) {
        continentList.add(country.getContinent());
      }

      if (!country.equals(countryPath.get(0))) {
        totalTax += Integer.parseInt(country.getTaxFee());
      }
    }

    MessageCli.ROUTE_INFO.printMessage("[" + route + "]");

    // Get all of the continents visited in the route
    for (String continent : continentList) {
      if (!continent.equals(continentList.get(continentList.size() - 1))) {
        continentRoute.append(continent + ", ");
      } else {
        continentRoute.append(continent);
      }
    }

    MessageCli.CONTINENT_INFO.printMessage("[" + continentRoute + "]");

    MessageCli.TAX_INFO.printMessage(Integer.toString(totalTax));
  }
}
