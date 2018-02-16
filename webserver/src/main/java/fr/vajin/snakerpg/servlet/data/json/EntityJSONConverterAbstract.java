package fr.vajin.snakerpg.servlet.data.json;

import fr.vajin.snakerpg.database.entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public abstract class EntityJSONConverterAbstract implements EntityJSONConverter {

    static final String JSON_USER_ID = "userId";
    static final String JSON_USER_ALIAS = "alias";
    static final String JSON_USER_ACCOUNT_NAME = "accountName";
    static final String JSON_USER_EMAIL = "email";
    static final String JSON_USER_SNAKELIST = "snakes";

    public abstract AccessLevel getAccessLevel();

    @Override
    public JSONObject convertUser(UserEntity userEntity) {
        JSONObject object = new JSONObject();

        object.put(JSON_USER_ID, userEntity.getId());
        object.put(JSON_USER_ALIAS, userEntity.getAlias());
        if (this.getAccessLevel() == AccessLevel.USER || this.getAccessLevel() == AccessLevel.ADMIN) {
            object.put(JSON_USER_ACCOUNT_NAME, userEntity.getAccountName());
            object.put(JSON_USER_EMAIL, userEntity.getEmail());
        }

        if (!userEntity.getSnakes().isEmpty()) {
            JSONArray snakeList = new JSONArray();
            for (SnakeEntity snakeEntity : userEntity.getSnakes()) {
                snakeList.add(this.convertSnake(snakeEntity));
            }
            object.put(JSON_USER_SNAKELIST, snakeList);
        }

        return object;
    }

    @Override
    public JSONObject convertSnake(SnakeEntity snakeEntity) {


        return null;
    }

    @Override
    public JSONObject convertGame(GameEntity gameEntity) {
        return null;
    }

    @Override
    public JSONObject convertGameMode(GameModeEntity gameModeEntity) {
        return null;
    }

    @Override
    public JSONObject convertSnakeClass(SnakeClassEntity snakeClassEntity) {
        return null;
    }

    enum AccessLevel {
        ADMIN,
        USER,
        OTHER;
    }
}
