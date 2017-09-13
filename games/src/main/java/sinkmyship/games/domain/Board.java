package sinkmyship.games.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Traversable;
import org.immutables.value.Value;


/**
 *
 */
@Value.Immutable(builder = false)
@JsonDeserialize(as = ImmutableBoard.class)
public abstract class Board {

    public static Board of(int width, int height, String playerId) {
        return ImmutableBoard.of(width, height, playerId, List.empty(), HashMap.empty());
    }

    @Value.Parameter
    public abstract int width();

    @Value.Parameter
    public abstract int height();

    @Value.Parameter
    public abstract String playerId();

    @Value.Parameter
    public abstract List<Shot> shots();

    @Value.Parameter
    public abstract Map<String, PlacedShip> ships();

    public boolean isReadyToStart(Traversable<ShipDefinition> ships) {
        return ships.forAll(ship -> ships().containsKey(ship.shipId()));
    }

    public Board placeShip(ShipDefinition shipDefinition, Position position, Direction direction) {
        Ship ship = Ship.of(shipDefinition.shipId(), shipDefinition.length());
        PlacedShip placedShip = ImmutablePlacedShip.of(ship, position, direction);
        return ImmutableBoard.copyOf(this).withShips(ships().put(ship.shipId(), placedShip));
    }

    public Tuple2<Board, ShotHitMiss> shoot(int x, int y) {
        ImmutableShot shot = ImmutableShot.of(new Position(x, y));
        boolean hit = ships().values().exists(ship -> ship.occupiesSpace(x, y));
        ShotHitMiss hitMiss = ShotHitMiss.of(hit);
        Board updatedBoard = ImmutableBoard.copyOf(this).withShots(shots().prepend(shot));
        return Tuple.of(updatedBoard, hitMiss);
    }

    @Value.Immutable
    @JsonDeserialize(as = ImmutablePlacedShip.class)
    static abstract class PlacedShip {

        @Value.Parameter
        public abstract Ship ship();

        @Value.Parameter
        public abstract Position position();

        @Value.Parameter
        public abstract Direction direction();

        boolean occupiesSpace(int x, int y) {
            return true;
        }
    }

}