package sinkmyship.games;

import java.util.Objects;

import static sinkmyship.common.Requirements.nonEmpty;
import static sinkmyship.common.Requirements.require;

/**
 *
 */
final class CancelGame {

    public String gameId;

    public CancelGame(String gameId) {
        require(nonEmpty(gameId), "gameId is required");
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof CancelGame) {
            CancelGame that = (CancelGame) obj;
            return Objects.equals(this.gameId, that.gameId);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId);
    }

}