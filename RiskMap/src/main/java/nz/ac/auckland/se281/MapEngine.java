package nz.ac.auckland.se281;

import java.util.LinkedList;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {

  private Graph riskMap;

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this method invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures
    this.riskMap = new Graph();
    // Parsing Countries, creating nodes
    for (String line : countries) {
      String[] countryData = line.split(",");
      riskMap.addNode(
          new Country(countryData[0], countryData[1], Integer.parseInt(countryData[2])));
    }

    // Parsing adjancies, creating edges
    for (String line : adjacencies) {
      String[] adjData = line.split(",");
      for (int i = 1; i < adjData.length; i++) {
        riskMap.addEdge(adjData[0], adjData[i]);
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // Asks user to insert country and takes input
    MessageCli.INSERT_COUNTRY.printMessage();
    String userInput = Utils.scanner.nextLine();
    Country userCountry = null;

    // While loop that checks whether the country inputted is valid or not, and throws an exception
    // if not
    // userCountry is null if input is invalid
    while (userCountry == null) {
      try {

        userCountry =
            riskMap.getCountryNodes().get(Utils.capitalizeFirstLetterOfEachWord(userInput));

        if (userCountry == null) {
          throw new InvalidCountryNameException(userInput);

        } else {
          MessageCli.COUNTRY_INFO.printMessage(
              userCountry.getName(),
              userCountry.getContinent(),
              Integer.toString(userCountry.getCrossBorderTax()));
        }

      } catch (InvalidCountryNameException e) {
        // handles exception by asking again until input is valid
        MessageCli.INVALID_COUNTRY.printMessage(Utils.capitalizeFirstLetterOfEachWord(userInput));
        userInput = Utils.scanner.nextLine();
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {

    // Start country
    MessageCli.INSERT_SOURCE.printMessage();
    String startInput = Utils.scanner.nextLine();
    Country startCountry = null;
    // Handling invalid inputs
    while (startCountry == null) {
      try {
        startCountry =
            riskMap.getCountryNodes().get(Utils.capitalizeFirstLetterOfEachWord(startInput));

        if (startCountry == null) {
          throw new InvalidCountryNameException(startInput);
        }
      } catch (InvalidCountryNameException e) {
        MessageCli.INVALID_COUNTRY.printMessage(Utils.capitalizeFirstLetterOfEachWord(startInput));
        startInput = Utils.scanner.nextLine();
      }
    }

    // End Country
    MessageCli.INSERT_DESTINATION.printMessage();
    String endInput = Utils.scanner.nextLine();
    Country endCountry = null;

    // Handling invalid inputs
    while (endCountry == null) {
      try {
        endCountry = riskMap.getCountryNodes().get(Utils.capitalizeFirstLetterOfEachWord(endInput));

        if (endCountry == null) {
          throw new InvalidCountryNameException(endInput);
        }
      } catch (InvalidCountryNameException e) {
        MessageCli.INVALID_COUNTRY.printMessage(Utils.capitalizeFirstLetterOfEachWord(endInput));
        endInput = Utils.scanner.nextLine();
      }
    }

    // Searching for fastest route
    if (startCountry.equals(endCountry)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    // Getting Country name of each country in the path
    List<Country> path = riskMap.findDestinationPath(startCountry, endCountry);
    String countryNames;
    String countryContinent;
    List<String> pathContinents = new LinkedList<>();
    int tax = 0;
    StringBuilder sbName = new StringBuilder();
    StringBuilder sbCont = new StringBuilder();
    sbName.append("[");
    sbCont.append("[");

    for (int i = 0; i < path.size(); i++) {
      if (i < path.size() - 1) {
        sbName.append(path.get(i).getName() + ", ");
      } else if (i == path.size() - 1) {
        sbName.append(path.get(i).getName() + "]");
      }

      if (pathContinents.contains(path.get(i).getContinent())) {
        continue;
      } else {
        pathContinents.add(path.get(i).getContinent());
      }
    }

    for (int i = 0; i < pathContinents.size(); i++) {
      if (i < pathContinents.size() - 1) {
        sbCont.append(pathContinents.get(i) + ", ");
      } else if (i == pathContinents.size() - 1) {
        sbCont.append(pathContinents.get(i) + "]");
      }
    }

    for (int i = 0; i < path.size(); i++) {
      tax += path.get(i).getCrossBorderTax();
    }
    tax = tax - path.get(0).getCrossBorderTax();
    countryNames = sbName.toString();
    countryContinent = sbCont.toString();

    MessageCli.ROUTE_INFO.printMessage(countryNames);
    MessageCli.CONTINENT_INFO.printMessage(countryContinent);
    MessageCli.TAX_INFO.printMessage(Integer.toString(tax));
  }
}
