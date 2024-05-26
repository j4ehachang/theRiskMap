package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/** This class is for the graph representing the map of the countries. */
public class Graph {
  private Map<Country, List<Country>> adjNodes;

  /** Constructor for the graph class. */
  public Graph() {
    this.adjNodes = new HashMap<>();
  }

  /**
   * This method adds a node to the graph.
   *
   * @param node a country.
   */
  public void addNode(Country node) {
    adjNodes.putIfAbsent(node, new ArrayList<>());
  }

  /**
   * This method adds an edge to the graph.
   *
   * @param node1 country one.
   * @param node2 country two.
   */
  public void addEdge(Country node1, Country node2) {
    addNode(node1);
    addNode(node2);
    adjNodes.get(node1).add(node2);
  }

  /**
   * This method returns the shortest path from a country to another.
   *
   * @param start start country.
   * @param destination destination country.
   * @return an arraylist representing the shortest route.
   */
  public List<Country> findShortestPath(Country start, Country destination) {
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    Map<Country, Country> parentMap = new HashMap<>();
    List<Country> cyclePath = new ArrayList<>();

    if (!adjNodes.containsKey(start)) {
      return null; // Start node not present in the graph
    }

    queue.add(start);
    visited.add(start);
    parentMap.put(start, null);

    while (!queue.isEmpty()) {
      Country current = queue.poll();

      // Queue adjacent countries if they have not been visited yet
      for (Country neighbor : adjNodes.get(current)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.add(neighbor);
          parentMap.put(neighbor, current);

          // When we reach the desination find the shortest way back to the start
        } else if (current.equals(destination)) {

          Country node = current;
          while (node != null) {
            cyclePath.add(node);
            node = parentMap.get(node);
          }
          Collections.reverse(cyclePath);
          return cyclePath;
        }
      }
    }
    return null;
  }
}
