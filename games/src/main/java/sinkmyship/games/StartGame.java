package sinkmyship.games;

import static sinkmyship.common.Requirements.nonEmpty;
import static sinkmyship.common.Requirements.require;

/**
 *
 */
public class StartGame {

    public final String gameId;

    public StartGame(String gameId) {
        require(nonEmpty(gameId), "gameId is required");
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StartGame startGame = (StartGame) o;

        return gameId.equals(startGame.gameId);
    }

    @Override
    public int hashCode() {
        return gameId.hashCode();
    }
}