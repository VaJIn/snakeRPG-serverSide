package fr.vajin.snakerpg.servlet.data.json;

import fr.vajin.snakerpg.database.entities.*;
import org.json.simple.JSONObject;

public interface EntityJSONConverter {

    JSONObject convertUser(UserEntity userEntity);

    JSONObject convertSnake(SnakeEntity snakeEntity);

    JSONObject convertGame(GameEntity gameEntity);

    JSONObject convertGameMode(GameModeEntity gameModeEntity);

    JSONObject convertSnakeClass(SnakeClassEntity snakeClassEntity);

}
