package be.ugent.devops.services.logic;

import be.ugent.devops.services.logic.api.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.json.Json;
import io.vertx.core.json.jackson.DatabindCodec;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class SerdesTest {

    @Inject
    MoveFactory moveFactory;

    @BeforeAll
    public static void setup() {
        DatabindCodec.mapper().registerModule(new Jdk8Module());
    }

    @Test
    public void testBaseMove() {
        var baseMove = moveFactory.baseBuildUnit(UnitType.CLERIC);
        // Convert to JSON string
        var jsonString = Json.encode(baseMove);
        // Convert to GameObject instance
        var decodedBaseMove = Json.decodeValue(jsonString, BaseMove.class);

        // Both instances should be equal
        assertEquals(baseMove, decodedBaseMove);
    }

    @Test
    public void testGameContext() {
        var context = new GameContext(0, "test", 400, 300,
                Map.of(UnitType.FIGHTER, 100), Map.of(UnitType.FIGHTER, 500), Map.of(UnitMoveType.IDLE, 0));
        // Convert to JSON string
        var jsonString = Json.encode(context);
        // Convert to GameObject instance
        var decodedContext = Json.decodeValue(jsonString, GameContext.class);

        // Both instances should be equal
        assertEquals(context, decodedContext);
    }

    @Test
    public void testLocation() {
        var location = new Location(0, 0, false, false, false, false, 0,
                new Unit(0, 0, UnitType.PIONEER, 10, 100, false, false));

        // Convert to JSON string
        var jsonString = Json.encode(location);
        // Convert to Location instance
        var decodedLocation = Json.decodeValue(jsonString, Location.class);
        // Both instances should be equal
        assertEquals(location, decodedLocation);
    }
}
