package sinkmyship.games.domain;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class GameTest {

    private Instant now = Instant.now();

    private final String gameId = "G1";

    private final String player1 = "p1";

    private final String player2 = "p2";

    private final int width = 4;

    private final int height = 5;

    private final Game game = Game.of(gameId, player1, player2, width, height);

    @Before
    public void setup() {
        DomainTime.setNow(now);
    }

    @After
    public void teardown() {
        DomainTime.setNow(null);
    }

    @Test
    public void testOfSetsGameId() {
        assertEquals(gameId, game.gameId());
    }

    @Test
    public void testOfSetsPlayer1() {
        assertEquals(player1, game.player1());
    }

    @Test
    public void testOfSetsPlayer2() {
        assertEquals(player2, game.player2());
    }

    @Test
    public void testOfSetsWidth() {
        assertThat(game.boards().values().map(Board::width), everyItem(is(width)));
    }

    @Test
    public void testOfSetsHeight() {
        assertThat(game.boards().values().map(Board::height), everyItem(is(height)));
    }

    @Test
    public void testOfCreatesCutter() {
        assertThat(game.ships().values(), hasItem(ImmutableShipDefinition.of("Cutter", 2)));
    }

    @Test
    public void testOfCreatesSubmarine() {
        assertThat(game.ships().values(), hasItem(ImmutableShipDefinition.of("Submarine", 3)));
    }

    @Test
    public void testOfCreatesDestroyer() {
        assertThat(game.ships().values(), hasItem(ImmutableShipDefinition.of("Destroyer", 3)));
    }

    @Test
    public void testOfCreatesBattleship() {
        assertThat(game.ships().values(), hasItem(ImmutableShipDefinition.of("Battleship", 4)));
    }

    @Test
    public void testOfCreatesCarrier() {
        assertThat(game.ships().values(), hasItem(ImmutableShipDefinition.of("Carrier", 5)));
    }

    @Test
    public void testOfCreatesPlayer1Board() {
        ImmutableBoard expected = ImmutableBoard.of(width, height, player1, List.empty(), HashMap.empty());
        assertEquals(expected, game.boards().get(player1).get());
    }

    @Test
    public void testOfCreatesPlayer2Board() {
        ImmutableBoard expected = ImmutableBoard.of(width, height, player2, List.empty(), HashMap.empty());
        assertEquals(expected, game.boards().get(player2).get());
    }

    @Test
    public void defineShipWhenEmpty() {
        Game updatedGame = game.defineShip("battleship", 4);
        assertThat(updatedGame.ships().values(), hasItem(ImmutableShipDefinition.of("battleship", 4)));
    }

    @Test
    public void defineShipWhenAlreadyDefined() {
        String shipId = "battleship";
        Game updatedGame = game.defineShip(shipId, 3).defineShip(shipId, 5);
        assertThat(updatedGame.ships().values(), not(hasItem(ImmutableShipDefinition.of(shipId, 3))));
        assertThat(updatedGame.ships().values(), hasItem(ImmutableShipDefinition.of(shipId, 5)));
    }

    @Test
    public void placeShip() {
        Position position = new Position(2, 1);
        Game updatedGame = game
                .defineShip("battleship", 4)
                .placeShip(player1, "battleship", position, Direction.HORIZONTAL);
        ImmutableShip ship = ImmutableShip.of("battleship", 4, 0);
        Board expectedBoard = ImmutableBoard.of(width, height, player1, List.empty(),
                HashMap.of(ship.shipId(), ImmutablePlacedShip.of(ship, position, Direction.HORIZONTAL)));
        assertEquals(expectedBoard, updatedGame.boards().get(player1).get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeShipWhenShipIsUndefined() {
        game.placeShip(player1, "Sailboat", new Position(1, 2), Direction.HORIZONTAL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeShipWhenPlayerIsUndefined() {
        game.placeShip("zz", "Battleship", new Position(1, 2), Direction.HORIZONTAL);
    }

    @Test
    public void startGameWhenReady() {
        Game updatedGame = game.startGame();
        assertEquals(updatedGame.status(), GameStatus.IN_PROGRESS);
    }

    @Test(expected = IllegalStateException.class)
    public void startGameWhenNotNew() {
        game.startGame().startGame();
    }

    @Test(expected = IllegalStateException.class)
    public void startGameWhenBoardsAreNotReady() {
        Game game = ImmutableGame.of("1", "pw1", "pw2", GameStatus.IN_PROGRESS, Instant.now(),
                HashMap.empty(), HashMap.empty());
        game.startGame();
    }
}