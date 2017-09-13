package sinkmyship.games.domain;

/**
 *
 */
public enum ShotHitMiss {

    HIT,

    MISS;

    public static ShotHitMiss of(boolean hit) {
        if (hit) {
            return HIT;
        } else {
            return MISS;
        }
    }

}