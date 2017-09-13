package sinkmyship.games;

/**
 *
 */
public class Shoot {

    public final String gameId;

    public final String playerId;

    public final int x;

    public final int y;

    public Shoot(String gameId, String playerId, int x, int y) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shoot shoot = (Shoot) o;

        if (x != shoot.x) return false;
        if (y != shoot.y) return false;
        if (!gameId.equals(shoot.gameId)) return false;
        return playerId.equals(shoot.playerId);
    }

    @Override
    public int hashCode() {
        int result = gameId.hashCode();
        result = 31 * result + playerId.hashCode();
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

}