package sinkmyship.players;

import java.util.Objects;

import static sinkmyship.common.Requirements.nonEmpty;
import static sinkmyship.common.Requirements.require;

/**
 *
 */
final class RegisterPlayer {

    public final String id;

    public final String firstName;

    public final String lastName;

    public final String emailAddress;

    public RegisterPlayer(String id, String firstName, String lastName, String emailAddress) {
        require(nonEmpty(id), "id is required");
        require(nonEmpty(firstName), "firstName is required");
        require(nonEmpty(lastName), "lastName is required");
        require(nonEmpty(emailAddress), "emailAddress is required");

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof RegisterPlayer) {
            RegisterPlayer that = (RegisterPlayer) obj;
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