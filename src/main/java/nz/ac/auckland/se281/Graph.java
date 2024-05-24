package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Graph {
  private Map<Country, List<Country>> adjNodes;

  public Graph() {
    this.adjNodes = new HashMap<>();
  }

  public void addNode(Country node) {
    adjNodes.putIfAbsent(node, new ArrayList<>());
  }

  public void addEdge(Country node1, Country node2) {
    addNode(node1);
    addNode(node2);
    adjNodes.get(node1).add(node2);
  }

  public void removeNode(Country node) {
    adjNodes.remove(node);
    for (Country key : adjNodes.keySet()) {
      adjNodes.get(key).remove(node);
    }
  }

  public void removeEdge(Country node1, Country node2) {
    adjNodes.getOrDefault(node1, new ArrayList<>()).remove(node2);
    adjNodes.getOrDefault(node2, new ArrayList<>()).remove(node1);
  }

  public List<Country> findShortestPath(Country root) {
    if (!adjNodes.containsKey(root)) {
      return null; // Start node not present in the graph
    }

    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    Map<Country, Country> parentMap = new HashMap<>();

    queue.add(root);
    visited.add(root);
    while (!queue.isEmpty()) {
      Country node = queue.poll();
      for (Country n : adjNodes.get(node)) {
        if (!visited.contains(n)) {
          visited.add(n);
          queue.add(n);
        }
      }
    }
    return visited;
  }
}
