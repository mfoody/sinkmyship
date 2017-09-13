package sinkmyship.games.domain;

import org.junit.Test;
import sinkmyship.common.Serializer;

import static org.junit.Assert.*;

/**
 *
 */
public class PlacedShipTest {

    @Test
    public void serialize() {
        Ship ship = ImmutableShip.of("battleship", 4, 1);
        Board.PlacedShip placedShip = ImmutablePlacedShip.of(ship, new Position(4, 3), Direction.HORIZONTAL);
        assertEquals(placedShip, Serializer.deserialize(Serializer.serialize(placedShip), Board.PlacedShip.class).get());
    }
}