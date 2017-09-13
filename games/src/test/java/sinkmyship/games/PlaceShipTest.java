package sinkmyship.games;

import org.junit.Test;
import sinkmyship.games.domain.Direction;

/**
 *
 */
public class PlaceShipTest {

    final private String gameId = "1";

    final private String player1 = "pw1";

    final private String shipId = "pw2";

    final private int x = 10;

    final private int y = 10;

    final private Direction direction = Direction.HORIZONTAL;

    @Test(expected = IllegalArgumentException.class)
    public void constructorWhenGameIdIsNull() {
        new PlaceShip(null, player1, shipId, x, y, direction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWhenGameIdIsEmpty() {
        new PlaceShip("", player1, shipId, x, y, direction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWhenPlayerIdIsNull() {
        new PlaceShip(gameId, null, shipId, x, y, direction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWhenPlayerIdIsEmpty() {
        new PlaceShip(gameId, "", shipId, x, y, direction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWhenShipIdIsNull() {
        new PlaceShip(gameId, player1, null, x, y, direction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWhenShipIdIsEmpty() {
        new PlaceShip(gameId, player1, "", x, y, direction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWhenXIsLessThan0() {
        new PlaceShip(gameId, player1, shipId, -1, y, direction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWhenYIsLessThan0() {
        new PlaceShip(gameId, player1, shipId, x, -1, direction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWhenDirectionIsNull() {
        new PlaceShip(gameId, player1, shipId, x, y, null);
    }
}