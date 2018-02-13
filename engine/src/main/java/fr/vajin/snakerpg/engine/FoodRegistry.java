package fr.vajin.snakerpg.engine;

public interface FoodRegistry {

    Food getRandomFood();

    Food getFood(String key);
}
