import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.example.Player;
import org.example.Scrabble;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ScrabbleTest {

  public static final int TOTAL_AMOUNT_OF_TILES = 98;
  private static Set<String> validWords;
  private final int AMOUNT_OF_TILES_START_GAME_PER_PLAYER = 7;

  @BeforeAll
  public static void setup() {
    validWords = Set.of("HELLO", "HELL", "APARTMENT");
  }

  @Test
  public void testScrubleInitializedWithCorrectAmountOfTilesInTheBag() {
    Scrabble scrabble = new Scrabble(validWords);
    assertEquals(TOTAL_AMOUNT_OF_TILES, scrabble.getBagOfTiles().getTiles().size());
  }

  @Test
  public void testAddPlayerToTheGame() {
    Scrabble scrabble = new Scrabble(validWords);
    Player playerOne = new Player("player1");
    scrabble.addPlayerToTheGame(playerOne);
    assertEquals(1, scrabble.getPlayers().size());
  }

  @Test
  public void testWhenGameIsStartedEachPlayerReceivesCorrectAmountOfTiles() {
    Scrabble scrabble = new Scrabble(validWords);
    scrabble.addPlayerToTheGame(new Player("player1"));
    scrabble.addPlayerToTheGame(new Player("player2"));
    scrabble.startGame(AMOUNT_OF_TILES_START_GAME_PER_PLAYER);
    scrabble
        .getPlayers()
        .forEach(
            player ->
                assertEquals(AMOUNT_OF_TILES_START_GAME_PER_PLAYER, player.getTiles().size()));
  }

  @Test
  public void testTilesAreRemovedFromBagWhenPicked() {
    Scrabble scrabble = new Scrabble(validWords);
    scrabble.addPlayerToTheGame(new Player("player1"));
    scrabble.startGame(AMOUNT_OF_TILES_START_GAME_PER_PLAYER);
    assertEquals(
        TOTAL_AMOUNT_OF_TILES - AMOUNT_OF_TILES_START_GAME_PER_PLAYER,
        scrabble.getBagOfTiles().getTiles().size());
  }

  @Test
  public void testCalculateScoreOfAWordGivesTheRightAmountOfPoints() {
    assertEquals(8, Scrabble.calculateScore("HELLO"));
  }

  @Test
  public void testEmptyListOfTilesWhenBagIsEmpty() {
    Scrabble scrabble = new Scrabble(validWords);
    assertEquals(0, scrabble.getTilesFromBag(TOTAL_AMOUNT_OF_TILES + 1).size());
  }

  @Test
  public void testFindWordWithMostPoints() {
    Scrabble scrabble = new Scrabble(validWords);
    assertEquals(
        "HELL", scrabble.findHighestScoringWordFromTiles(List.of('C', 'H', 'E', 'L', 'L')));
  }

  @Test
  public void testNoValidWordsWithCurrentTiles() {
    Scrabble scrabble = new Scrabble(validWords);
    assertEquals("", scrabble.findHighestScoringWordFromTiles(List.of('C', 'H', 'E', 'L', 'K')));
  }

  @Test
  public void testWordIsValid() {
    Scrabble scrabble = new Scrabble(validWords);
    assertTrue(scrabble.isValidWord("HELLO"));
  }

  @Test
  public void testWordIsNotValid() {
    Scrabble scrabble = new Scrabble(validWords);
    assertFalse(scrabble.isValidWord("HEL"));
  }

  @Test
  public void testFindAllPossibleCombinations() {
    Scrabble scrabble = new Scrabble(validWords);
    List<String> combinations = scrabble.generateCombinations(Arrays.asList('A', 'B', 'C'));
    // n!/(n-r)!
    assertEquals(15, combinations.size());
  }

  @Test
  public void testFindNoWordsWordGivenInvalidTiles() {
    Scrabble scrabble = new Scrabble(validWords);
    String word = scrabble.findHighestScoringWordFromTiles(Arrays.asList('A', 'B', 'C'));
    assertEquals("", word);
  }

  @Test
  public void testFindHighestScoringWordGivenSomeTiles() {
    Scrabble scrabble = new Scrabble(validWords);
    String word =
        scrabble.findHighestScoringWordFromTiles(
            Arrays.asList('H', 'E', 'L', 'L'));
    assertEquals("HELL", word);

    String tiles = "HELLAPARTEMENT";
    System.out.println(scrabble.findHighestScoringWord(tiles));
  }
}
