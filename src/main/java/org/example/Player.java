package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {

  private final String name;
  private final List<Character> tiles;

  public Player(String name) {
    this.name = name;
    tiles = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public List<Character> getTiles() {
    return tiles;
  }

  public void addTiles(List<Character> tiles) {
    this.tiles.addAll(tiles);
  }
}
