import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.example.NoMoreTilesException;
import org.example.TilesBag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TilesBagTest {

  public static final int TOTAL_AMOUNT_OF_TILES = 26;
  public static final Map<Character, Integer> tilesAmount = new HashMap<>();
  @BeforeAll
  public static void setup(){
    for (char c = 'A'; c <= 'Z'; c++) {
      tilesAmount.put(c, 1);
    }
  }
  @Test
  public void testTilesBagCreation() {
    TilesBag tilesBag = new TilesBag();
    assertEquals(TOTAL_AMOUNT_OF_TILES, tilesBag.getTiles().size());
  }

  @Test
  public void testTilesBagWithSpecificAmountCreation() {
    TilesBag tilesBag = new TilesBag(tilesAmount);
    assertEquals(TOTAL_AMOUNT_OF_TILES, tilesBag.getTiles().size());
  }

  @Test
  public void testWhenATileIsPickedFromBagItIsRemovedFromTheBag() throws NoMoreTilesException {
    TilesBag tilesBag = new TilesBag();
    int tiles_to_pick = 1;
    tilesBag.getTilesFromBag(tiles_to_pick);
    assertEquals(TOTAL_AMOUNT_OF_TILES - tiles_to_pick, tilesBag.getTiles().size());
  }

  @Test
  public void testThrowExceptionWhenTryToPickTilesFromEmptyTilesBag() {
    TilesBag tilesBag = new TilesBag();
    int tiles_to_pick = 27;
    assertThrows(NoMoreTilesException.class, () -> tilesBag.getTilesFromBag(tiles_to_pick));
  }
}
