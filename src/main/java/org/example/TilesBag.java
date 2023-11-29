package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TilesBag {

  List<Character> tiles;

  public TilesBag() {
    this.tiles = new ArrayList<>();
    for (char tile = 'a'; tile <= 'z'; tile++) {
      tiles.add(tile);
    }
    Collections.shuffle(tiles);
  }

  public TilesBag(Map<Character, Integer> letterDistribution) {
    this.tiles = new ArrayList<>();
    letterDistribution.forEach(this::addAmountOfSpecificTiles);
  }

  private void addAmountOfSpecificTiles(Character character, Integer amount) {
    for (int c = 0; c < amount; c++) {
      tiles.add(character);
    }
  }

  public List<Character> getTiles() {
    return tiles;
  }

  public List<Character> getTilesFromBag(int tilesToPick) throws NoMoreTilesException {
    List<Character> pickedTiles = new ArrayList<>();
    for (int c = 0; c < tilesToPick; c++) {
      try {
        pickedTiles.add(tiles.remove(0));
      } catch (IndexOutOfBoundsException e) {
        throw new NoMoreTilesException("No more tiles available.");
      }
    }
    return pickedTiles;
  }
}
