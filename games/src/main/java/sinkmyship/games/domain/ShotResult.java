package sinkmyship.games.domain;

import org.immutables.value.Value;

/**
 *
 */
@Value.Immutable
public abstract class ShotResult {

    @Value.Parameter
    public abstract Game game();

    @Value.Parameter
    public abstract ShotHitMiss hitMiss();

}