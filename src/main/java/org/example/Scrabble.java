package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Scrabble {
  private static final TrieNode dictionary = new TrieNode(); // Load this from a file
  private static final Map<Character, Integer> scores = new HashMap<>();
  private static final Map<Character, Integer> letterDistribution = new HashMap<>();
  static {
    // Initialize letter score
    scores.put('E', 1); scores.put('A', 1); scores.put('I', 1); scores.put('O', 1);
    scores.put('N', 1); scores.put('R', 1); scores.put('T', 1); scores.put('L', 1);
    scores.put('S', 1); scores.put('U', 1); scores.put('D', 2); scores.put('G', 2);
    scores.put('B', 3); scores.put('C', 3); scores.put('M', 3); scores.put('P', 3);
    scores.put('F', 4); scores.put('H', 4); scores.put('V', 4); scores.put('W', 4);
    scores.put('Y', 4); scores.put('K', 5); scores.put('J', 8); scores.put('X', 8);
    scores.put('Q', 10); scores.put('Z', 10);
    // Initialize letter distribution
    letterDistribution.put('E', 12); letterDistribution.put('A', 9); letterDistribution.put('I', 9);
    letterDistribution.put('O', 8); letterDistribution.put('N', 6); letterDistribution.put('R', 6);
    letterDistribution.put('T', 6); letterDistribution.put('L', 4); letterDistribution.put('S', 4);
    letterDistribution.put('U', 4); letterDistribution.put('D', 4); letterDistribution.put('G', 3);
    letterDistribution.put('B', 2); letterDistribution.put('C', 2); letterDistribution.put('M', 2);
    letterDistribution.put('P', 2); letterDistribution.put('F', 2); letterDistribution.put('H', 2);
    letterDistribution.put('V', 2); letterDistribution.put('W', 2); letterDistribution.put('Y', 2);
    letterDistribution.put('K', 1); letterDistribution.put('J', 1); letterDistribution.put('X', 1);
    letterDistribution.put('Q', 1); letterDistribution.put('Z', 1);}

  private final List<Player> players;
  private final TilesBag bag;
  private final Set<String> validWords;

  public Scrabble(Set<String> validWords) {
    this.validWords = validWords;
    this.players = new ArrayList<>();
    this.bag = new TilesBag(letterDistribution);
    validWords.forEach(dictionary::insert);

  }

  public static void main(String[] args) {}

  public static int calculateScore(String word) {
    int score = 0;
    for (char c : word.toUpperCase().toCharArray()) {
      score += scores.get(c);
    }
    return score;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void addPlayerToTheGame(Player playerOne) {
    this.players.add(playerOne);
  }

  public void startGame(int amountOfTilesStartGame) {
    for (Player player : this.players) {
      player.addTiles(getTilesFromBag(amountOfTilesStartGame));
    }
  }

  public List<Character> getTilesFromBag(int amountOfTilesStartGame) {
    try {
      return bag.getTilesFromBag(amountOfTilesStartGame);
    } catch (NoMoreTilesException e) {
      return List.of();
    }
  }

  public TilesBag getBagOfTiles() {
    return this.bag;
  }

  public String findHighestScoringWordFromTiles(List<Character> tiles) {
    String highestScoringWord = "";
    int highestScore = 0;
    // Generate all possible combinations of the tiles
    List<String> combinations = generateCombinations(tiles);
    for (String combination : combinations) {
      if (isValidWord(combination)) {
        int score = calculateScore(combination);
        if (score > highestScore) {
          highestScore = score;
          highestScoringWord = combination;
        }
      }
    }
    return highestScoringWord;
  }

  public List<String> generateCombinations(List<Character> tiles) {
    List<String> combinations = new ArrayList<>();
    generateCombinationsHelper(tiles, "", combinations);
    return combinations;
  }

  private void generateCombinationsHelper(List<Character> tiles, String prefix, List<String> combinations) {
    if (!prefix.isEmpty()) {
      combinations.add(prefix);
    }
    for (int i = 0; i < tiles.size(); i++) {
      List<Character> remainingTiles = new ArrayList<>(tiles);
      char c = remainingTiles.remove(i);
      generateCombinationsHelper(remainingTiles, prefix + c, combinations);
    }
  }

  public boolean isValidWord(String word) {
    return this.validWords.contains(word.toUpperCase());
  }
  public String findHighestScoringWord(String tiles) {
    return findHighestScoringWord(dictionary, tiles, "", 0, "", 0);
  }

  private static String findHighestScoringWord(TrieNode node, String tiles, String word, int score, String bestWord, int bestScore) {
    if (node.isEndOfWord() && score > bestScore) {
      bestWord = word;
      bestScore = score;
    }

    for (char c : tiles.toCharArray()) {
      TrieNode child = node.getNode(c);
      if (child != null) {
        String remainingTiles = tiles.replaceFirst(Character.toString(c), "");
        bestWord = findHighestScoringWord(child, remainingTiles, word + c, score + scores.get(c), bestWord, bestScore);
      }
    }

    return bestWord;
  }

}
