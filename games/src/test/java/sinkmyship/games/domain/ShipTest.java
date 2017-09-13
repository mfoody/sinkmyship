package sinkmyship.games.domain;

import org.junit.Test;
import sinkmyship.common.Serializer;

import static org.junit.Assert.*;

/**
 *
 */
public class ShipTest {

    @Test
    public void serialize() {
        Ship ship = ImmutableShip.of("Dingy", 1,0);
        assertEquals(ship, Serializer.deserialize(Serializer.serialize(ship), Ship.class).get());
    }

    @Test
    public void isSunkWhen0Hits() {
        assertFalse(ImmutableShip.of("battleship", 2, 0).isSunk());
    }

    @Test
    public void isSunkWhenNonZeroHits() {
        assertFalse(ImmutableShip.of("battleship", 2, 1).isSunk());
    }

    @Test
    public void isSunkWhenLengthHits() {
        assertTrue(ImmutableShip.of("battleship", 4, 4).isSunk());
    }

    @Test
    public void testHit() {
        Ship ship = ImmutableShip.of("battleship", 4, 0);
        Ship hitShip = ship.hit();
        assertEquals(hitShip.hits(), 1);
        assertEquals(ship.hits(), 0);
    }
}