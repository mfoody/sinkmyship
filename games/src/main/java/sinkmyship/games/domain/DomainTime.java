package sinkmyship.games.domain;

import java.time.Instant;

/**
 *
 */
public class DomainTime {

    private static Instant now;

    public static void setNow(Instant value) {
        now = value;
    }

    public static Instant now() {
        if (now != null) {
            return now;
        }
        return Instant.now();
    }
}
