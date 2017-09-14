package sinkmyship.games.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.fasterxml.jackson.core.type.TypeReference;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import io.vavr.control.Try;
import sinkmyship.common.Serializer;
import sinkmyship.games.GameDetailedView;
import sinkmyship.games.GameSummaryView;
import sinkmyship.games.ImmutableGameDetailedView;
import sinkmyship.games.ImmutableGameSummaryView;
import sinkmyship.games.domain.*;

import java.time.Instant;

/**
 *
 */
class DynamoGameRepository extends GameRepository {

    private final AmazonDynamoDB client;

    private final Table table;

    private final TypeReference<Map<String, ShipDefinition>> shipsTypeReference =
            new TypeReference<Map<String, ShipDefinition>>() {
            };

    private final TypeReference<Map<String, Board>> boardsTypeReference =
            new TypeReference<Map<String, Board>>() {
            };


    DynamoGameRepository() {
        client = AmazonDynamoDBClientBuilder.standard().withRegion("us-east-1").build();
        DynamoDB db = new DynamoDB(client);
        table = db.getTable("games");
    }

    @Override
    public Try<Game> create(Game game) {
        return Try.of(() -> {
            String shipJson = Serializer.serialize(game.ships());
            String boardJson = Serializer.serialize(game.boards());
            table.putItem(new Item()
                    .withString("id", game.gameId())
                    .withString("player1", game.player1())
                    .withString("player2", game.player2())
                    .withString("status", game.status().toString())
                    .withLong("startedAt", game.startedAt().toEpochMilli())
                    .withJSON("ships", shipJson)
                    .withJSON("boards", boardJson));
            return game;
        });
    }

    @Override
    public Try<Game> update(Game game) {
        return Try.of(() -> {
            table.updateItem(new PrimaryKey("id", game.gameId()),
                    new AttributeUpdate("status").put(game.startedAt().toEpochMilli()));
            return game;
        });
    }

    @Override
    public Try<Game> load(String gameId) {
        return Try.of(() -> table.getItem("id", gameId)).flatMap(item -> {
            return Serializer.deserialize(item.getJSON("ships"), shipsTypeReference).flatMap(ships -> {
                return Serializer.deserialize(item.getJSON("boards"), boardsTypeReference).map(boards -> {
                    String player1 = item.getString("player1");
                    String player2 = item.getString("player2");
                    GameStatus status = GameStatus.valueOf(item.getString("status"));
                    Instant startedAt = Instant.ofEpochMilli(item.getLong("startedAt"));
                    return ImmutableGame.of(gameId, player1, player2, status, startedAt, HashMap.empty(), HashMap.empty());
                });
            });
        });

    }

    @Override
    public Try<List<GameSummaryView>> findAllGames() {
        return Try.of(() -> {
            ScanRequest request = new ScanRequest(table.getTableName()).withAttributesToGet("id", "status");
            ScanResult result = client.scan(request);
            return result.getItems().stream()
                    .map(map -> {
                        String id = map.get("id").getS();
                        GameStatus status = GameStatus.valueOf(map.get("status").getS());
                        return ImmutableGameSummaryView.of(id, status);
                    })
                    .collect(List.collector());
        });
    }

    @Override
    public Try<Option<GameDetailedView>> findOneGame(String gameId) {
        return Try.of(() -> {
            return Option.of(table.getItem("id", gameId)).map((item) -> {
                GameStatus status = GameStatus.valueOf(item.getString("status"));
                return ImmutableGameDetailedView.of(gameId, status);
            });
        });
    }

}