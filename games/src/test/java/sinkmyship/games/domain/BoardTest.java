package sinkmyship.games.domain;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import org.junit.Test;
import sinkmyship.common.Serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class BoardTest {

    @Test
    public void testSerialization() {
        Game game = Game.of("1", "p1", "p2", 10, 12);
        Board board = game.boards().values().head();
        assertEquals(board, Serializer.deserialize(Serializer.serialize(board), Board.class).get());
    }

    @Test
    public void testOf() {
        int width = 6;
        int height = 8;
        String playerId = "p1";
        Board board = Board.of(width, height, playerId);
        Board expected = ImmutableBoard.of(width, height, playerId, List.empty(), HashMap.empty());
        assertEquals(expected, board);
    }

    @Test
    public void placeShipWhenFirst() {
        Board board = Board.of(6, 6, "p1");
        ShipDefinition ship = ImmutableShipDefinition.of("battleship", 3);
        Position position = new Position(2, 3);
        Board updatedBoard = board.placeShip(ship, position, Direction.HORIZONTAL);
        assertTrue(updatedBoard.ships().containsKey(ship.shipId()));
    }

    @Test
    public void placeShipMoreThanOnce() {
        Board board = Board.of(6, 6, "p1");
        ShipDefinition ship1 = ImmutableShipDefinition.of("battleship", 3);
        Position position1 = new Position(2, 3);

        ShipDefinition ship2 = ImmutableShipDefinition.of("destroyer", 4);
        Position position2 = new Position(4, 0);

        Board updatedBoard = board
                .placeShip(ship1, position1, Direction.HORIZONTAL)
                .placeShip(ship2, position2, Direction.VERTICAL);

        assertTrue(updatedBoard.ships().size() == 2);
    }

    @Test
    public void placeShipWhenAlreadyPlaced() {
        Board board = Board.of(6, 6, "p1");
        ShipDefinition ship = ImmutableShipDefinition.of("destroyer", 3);
        Position position1 = new Position(2, 3);
        Position position2 = new Position(4, 0);

        Board updatedBoard = board
                .placeShip(ship, position1, Direction.HORIZONTAL)
                .placeShip(ship, position2, Direction.VERTICAL);

        assertEquals(updatedBoard.ships().size(), 1);
        Option<Board.PlacedShip> placed = updatedBoard.ships().get(ship.shipId());
        assertTrue(placed.isDefined());
        assertEquals(ImmutablePlacedShip.of(Ship.of(ship), position2, Direction.VERTICAL), placed.get());
    }

    @Test
    public void isReadyToStartWhenReady() {
        ShipDefinition battleship = ImmutableShipDefinition.of("battleship", 4);
        Board board = Board.of(4, 4, "p1").placeShip(battleship, new Position(0, 0), Direction.HORIZONTAL);

    }
}