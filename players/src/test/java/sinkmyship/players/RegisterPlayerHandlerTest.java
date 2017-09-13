package sinkmyship.players;

import io.vavr.control.Try;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sinkmyship.players.repositories.PlayerRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 */
public class RegisterPlayerHandlerTest {

    @Test
    public void testHandle() throws Exception {
        RegisterPlayer command = new RegisterPlayer("123", "Mike", "Foody", "wmfoody@gmail.com");
        Player expected = new Player(command.id,command.firstName, command.lastName, command.emailAddress);

        PlayerRepository repository = mock(PlayerRepository.class);
        when(repository.create(expected)).thenReturn(Try.success(expected));
        RegisterPlayerHandler handler = new RegisterPlayerHandler(repository);

        Try<Player> player = handler.handle(command);

        assertThat(player.get(), is(equalTo(expected)));
        verify(repository).create(expected);
    }

}