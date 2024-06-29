package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Graph {
  private Map<String, Country> countryNodes;

  private Map<Country, LinkedHashSet<Country>> adjCountries;

  public Graph() {
    this.countryNodes = new HashMap<>();
    this.adjCountries = new HashMap<>();
  }

  /**
   * Adds the node to the two hashmaps. Each node is a country. The Countries map consists of a
   * hashmap of a country instance and its string counterpart. The adjCountries map consists each
   * country and a list of it's adjacent countries.
   *
   * @param country the country that is added as a node.
   */
  public void addNode(Country country) {
    countryNodes.putIfAbsent(country.getName(), country);
    adjCountries.putIfAbsent(country, new LinkedHashSet<>());
  }

  /**
   * Adds the adjacent to the corresponding country, creating an edge
   *
   * @param node1 The node country
   * @param node2 The country adjacent to the node country
   */
  public void addEdge(String node1, String node2) {
    Country country1 = countryNodes.get(node1);
    Country country2 = countryNodes.get(node2);
    adjCountries.get(country1).add(country2);
  }

  public Map<String, Country> getCountryNodes() {
    return countryNodes;
  }

  /**
   * Searches through the graph to find the destination using BreadthFirstTraversal returning a list
   * of all the countries that must to be crossed to get to the destination.
   *
   * @param startCountry The country from which we start.
   * @param endCountry the destination.
   * @return Returns a list of all countries that are crossed to get to the destination, returns
   *     null if no list is found.
   */
  public List<Country> findDestinationPath(Country startCountry, Country endCountry) {
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    // Hashmap stores previous country of each node visited
    Map<Country, Country> previousCountry = new HashMap<>();
    queue.add(startCountry);
    visited.add(startCountry);
    previousCountry.put(startCountry, null);
    while (!queue.isEmpty()) {
      Country currentCountry = queue.poll();

      // if target country is found
      if (currentCountry.equals(endCountry)) {
        List<Country> path = new LinkedList<>();
        for (Country at = endCountry; at != null; at = previousCountry.get(at)) {
          path.add(at);
        }
        Collections.reverse(path);
        return path;
      }

      // checks each adjacent node and adds it to the queue and then checks each adjacent node of
      // the adjacent nodes in the queue
      for (Country adjCountry : adjCountries.get(currentCountry)) {
        if (!visited.contains(adjCountry)) {
          visited.add(adjCountry);
          queue.add(adjCountry);
          previousCountry.put(adjCountry, currentCountry);
        }
      }
    }

    // returns null if it is not found
    return null;
  }
}
