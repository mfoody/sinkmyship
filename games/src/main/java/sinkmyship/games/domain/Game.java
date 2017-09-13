package sinkmyship.games.domain;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.immutables.value.Value;

import java.time.Instant;

import static sinkmyship.common.Requirements.require;

/**
 *
 */
@Value.Immutable(builder = false)
public abstract class Game {

    public static Game of(String gameId, String player1, String player2, int width, int height) {
        ShipDefinition cutter = ImmutableShipDefinition.of("Cutter", 2);
        ShipDefinition destroyer = ImmutableShipDefinition.of("Destroyer", 3);
        ShipDefinition submarine = ImmutableShipDefinition.of("Submarine", 3);
        ShipDefinition battleship = ImmutableShipDefinition.of("Battleship", 4);
        ShipDefinition carrier = ImmutableShipDefinition.of("Carrier", 5);

        Map<String, ShipDefinition> ships = HashMap.of(
                cutter.shipId(), cutter,
                destroyer.shipId(), destroyer,
                submarine.shipId(), submarine,
                battleship.shipId(), battleship,
                carrier.shipId(), carrier);

        Board player1Board = Board.of(width, height, player1);
        Board player2Board = Board.of(width, height, player2);
        Map<String, Board> boards = HashMap.of(
                player1, player1Board,
                player2, player2Board);

        return ImmutableGame.of(gameId, player1, player2, GameStatus.NEW, DomainTime.now(), ships, boards);
    }

    @Value.Parameter
    public abstract String gameId();

    @Value.Parameter
    public abstract String player1();

    @Value.Parameter
    public abstract String player2();

    @Value.Parameter
    public abstract GameStatus status();

    @Value.Parameter
    public abstract Instant startedAt();

    @Value.Parameter
    public abstract Map<String, ShipDefinition> ships();

    @Value.Parameter
    public abstract Map<String, Board> boards();

    public boolean isReadyToStart() {
        return boards().values().forAll(board -> board.isReadyToStart(ships().values()));
    }

    public Game cancelGame() {
        return ImmutableGame.copyOf(this).withStatus(GameStatus.CANCELLED);
    }

    public Game defineShip(String shipId, int length) {
        ImmutableShipDefinition shipDefinition = ImmutableShipDefinition.of(shipId, length);
        return ImmutableGame.copyOf(this).withShips(ships().put(shipId, shipDefinition));
    }

    public Game placeShip(String playerId, String shipId, Position position, Direction direction) {
        require(boards().containsKey(playerId), "Player %s is not defined", playerId);
        require(ships().containsKey(shipId), "Ship %s is not defined", shipId);

        return ships()
                .get(shipId)
                .flatMap(shipDefinition -> {
                    return boards().get(playerId).map(board -> {
                        Board updatedBoard = board.placeShip(shipDefinition, position, direction);
                        return ImmutableGame.copyOf(this).withBoards(boards().put(playerId, updatedBoard));
                    });
                })
                .get();
    }

    public Tuple2<Game, ShotHitMiss> shoot(String playerId, int x, int y) {
        require(boards().containsKey(playerId), "Player {} is not defined");
        return boards()
                .get(playerId)
                .map(board -> board.shoot(x, y))
                .map(boardHitMiss -> boardHitMiss.apply((updatedBoard, hitMiss) -> {
                    Map<String, Board> updatedBoards = boards().put(playerId, updatedBoard);
                    return Tuple.of((Game) ImmutableGame.copyOf(this).withBoards(updatedBoards), hitMiss);
                }))
                .get();
    }

    public Game startGame() {
        if (!status().equals(GameStatus.NEW)) {
            throw new IllegalStateException("Game must be in NEW status");
        }
        return ImmutableGame.copyOf(this).withStatus(GameStatus.IN_PROGRESS);
    }

}