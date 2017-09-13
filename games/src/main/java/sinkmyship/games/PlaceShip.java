package sinkmyship.games;

import sinkmyship.games.domain.Direction;

import static sinkmyship.common.Requirements.nonEmpty;
import static sinkmyship.common.Requirements.require;

/**
 *
 */
final class PlaceShip {

    public final String gameId;

    public final String playerId;

    public final String shipId;

    public final int x;

    public final int y;

    public final Direction direction;

    PlaceShip(String gameId, String playerId, String shipId, int x, int y, Direction direction) {
        require(nonEmpty(gameId), "gameId is required");
        require(nonEmpty(playerId), "playerId is required");
        require(nonEmpty(shipId), "shipId is required");
        require(x >= 0, "x must be >= 0");
        require(y >= 0, "y must be >= 0");
        require(direction != null, "direction is required");

        this.gameId = gameId;
        this.playerId = playerId;
        this.shipId = shipId;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceShip placeShip = (PlaceShip) o;

        if (x != placeShip.x) return false;
        if (y != placeShip.y) return false;
        if (!gameId.equals(placeShip.gameId)) return false;
        if (!playerId.equals(placeShip.playerId)) return false;
        if (!shipId.equals(placeShip.shipId)) return false;
        return direction == placeShip.direction;
    }

    @Override
    public int hashCode() {
        int result = gameId.hashCode();
        result = 31 * result + playerId.hashCode();
        result = 31 * result + shipId.hashCode();
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + direction.hashCode();
        return result;
    }

}