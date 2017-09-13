package sinkmyship.players.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import io.vavr.control.Try;
import sinkmyship.players.Player;

/**
 *
 */
class DynamoPlayerRepository extends PlayerRepository {

    private final Table table;

    DynamoPlayerRepository() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion("us-east-1").build();
        DynamoDB db = new DynamoDB(client);
        table = db.getTable("players");
    }

    @Override
    public Try<Player> create(Player player) {
        return Try.of(() -> {
            table.putItem(new Item()
                    .withString("id", player.id)
                    .withString("firstName", player.firstName)
                    .withString("lastName", player.lastName)
                    .withString("emailAddress", player.emailAddress));
            return player;
        });
    }

}