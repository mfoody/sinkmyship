package sinkmyship.players;

import java.util.Objects;

/**
 *
 */
public final class Player {

    public final String id;

    public final String firstName;

    public final String lastName;

    public final String emailAddress;

    Player(String id, String firstName, String lastName, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof Player) {
            Player that = (Player) other;
            return Objects.equals(this.id, that.id) &&
                    Objects.equals(this.firstName, that.firstName) &&
                    Objects.equals(this.lastName, that.lastName) &&
                    Objects.equals(this.emailAddress, that.emailAddress);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, emailAddress);
    }

}