package sinkmyship.games;

import java.util.Objects;

import static sinkmyship.common.Requirements.nonEmpty;
import static sinkmyship.common.Requirements.require;

/**
 *
 */
public class CreateGame {

    public final String player1;

    public final String player2;

    public final String gameId;

    public final int width;

    public final int height;

    public CreateGame(String player1, String player2, String gameId, int width, int height) {
        require(nonEmpty(player1), "player1 is required");
        require(nonEmpty(player2), "player2 is required");
        require(nonEmpty(gameId), "gameId is required");
        require(width > 0, "width must be > 0");
        require(height > 0, "height must be > 0");

        this.player1 = player1;
        this.player2 = player2;
        this.gameId = gameId;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof CreateGame) {
            CreateGame that = (CreateGame) obj;
            return Objects.equals(this.player1, that.player1) &&
                    Objects.equals(this.player2, that.player2) &&
                    Objects.equals(this.gameId, that.gameId) &&
                    this.width == that.width && this.height == that.height;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(player1, player2, gameId, width, height);
    }

}