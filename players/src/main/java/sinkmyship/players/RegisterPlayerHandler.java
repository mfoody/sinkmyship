package sinkmyship.players;

import io.vavr.control.Try;
import sinkmyship.common.ApiGatewayHandler;
import sinkmyship.players.repositories.PlayerRepository;

public class RegisterPlayerHandler extends ApiGatewayHandler<RegisterPlayer, Player> {

    private PlayerRepository repository;

    public RegisterPlayerHandler() {
        this(PlayerRepository.getInstance());
    }

    public RegisterPlayerHandler(PlayerRepository repository) {
        super(RegisterPlayer.class);
        this.repository = repository;
    }

    @Override
    public Try<Player> handle(RegisterPlayer command) {
        Player player = new Player(command.id, command.firstName, command.lastName, command.emailAddress);
        return repository.create(player);
    }

}