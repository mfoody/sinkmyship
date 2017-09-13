package sinkmyship.games.domain;

import java.util.Objects;

import static sinkmyship.common.Requirements.require;

/**
 *
 */
public class Position {

    public final int x;

    public final int y;

    public Position(int x, int y) {
        require(x >= 0, "x must be >= 0");
        require(y >= 0, "y must be >= 0");

        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Position) {
            Position that = (Position) obj;
            return Objects.equals(this.x, that.x) && Objects.equals(this.y, that.y);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

}