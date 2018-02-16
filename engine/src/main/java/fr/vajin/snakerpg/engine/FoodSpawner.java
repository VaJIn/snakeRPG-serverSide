package fr.vajin.snakerpg.engine;


import fr.vajin.snakerpg.engine.utilities.Position;

import java.util.*;

public class FoodSpawner extends DynamicEntity

{

    private final FoodRegistry registry;
    private Map<Position, Food> spawnedFood;

    private final int maxSimultaneousFood;
    private final int minSimultaneousFood;

    final static int MAX_TRIES = 10;

    private Random randomGenerator;
    private List<Position> newPositions;

    public FoodSpawner(int maxFood, int minFood, FoodRegistry registry) {
        this.maxSimultaneousFood = maxFood;
        this.minSimultaneousFood = minFood;

        this.spawnedFood = new HashMap<>();
        this.randomGenerator = new Random();
        this.registry = registry;
    }

    @Override
    public boolean computeTick(int tick) {
        boolean trySpawn = spawnedFood.size() < minSimultaneousFood || spawnedFood.size() < maxSimultaneousFood;
        this.newPositions = new ArrayList<>(1);
        if (trySpawn) {
            Position p = tryToGenerateFood();
            if (p != null) {
                this.newPositions.add(p);
                return true;
            }
        }
        return false;
    }

    private Position tryToGenerateFood() {
        Position pos = this.getPositionForFood();
        if (pos == null) {
            return null;
        } else {
            Food newFood = registry.getRandomFood();
            this.spawnedFood.put(pos, newFood);
            this.notifyChangeAtPosition(pos, Entity.NEW_COVERED_POSITION);
            this.notifySpriteChange(hashCode(), pos, newFood.getRessourceKey());
            return pos;
        }
    }

    private Position getPositionForFood() {
        int tries;
        for (int i = 0; i < MAX_TRIES; ++i) {
            int x = randomGenerator.nextInt(this.getEngine().getField().getWidth());
            int y = randomGenerator.nextInt(this.getEngine().getField().getHeight());

            Position pos = new Position(x, y);
            if (!this.getEngine().doesAnEntityCoverPosition(pos) && this.getEngine().getField().getFieldUnits(pos).isBonusSpawnArea()) {
                return pos;
            }
        }
        return null;
    }

    @Override
    public boolean isKiller() {
        return false;
    }

    @Override
    public List<Position> getNewPositions() {
        return newPositions;
    }

    @Override
    public boolean coverPosition(Position pos) {
        return spawnedFood.containsKey(pos);
    }

    @Override
    public void handleCollisionWith(Entity otherObject, Position collisionPosition, boolean isInitater) {
        if (!isInitater) {
            Food struckFood = spawnedFood.get(collisionPosition);
            if (otherObject instanceof Snake) {
                Snake snake = (Snake) otherObject;

                snake.grow(struckFood.getGrowthFactor());

                this.removeFood(collisionPosition);
            } else {
                //Remove the food at this position
                this.removeFood(collisionPosition);
            }
        }
    }

    private void removeFood(Position pos) {
        Food removed = spawnedFood.remove(pos);
        notifySpriteChange(pos.hashCode(), pos, "");
        notifyChangeAtPosition(pos, Entity.ONE_LESS_COVER_ON_POSITION);
    }

    @Override
    public void inflictDamage(int damage) {
        //Should never happen
    }

    @Override
    public void destroy() {
        //Not to be destroyed
    }

    @Override
    public String getGraphicRessourceKeyForPosition(Position pos) {
        Food f = spawnedFood.get(pos);
        if (f == null) {
            return "";
        } else {
            return f.getRessourceKey();
        }
    }

    @Override
    public Iterator<EntityTileInfo> getEntityTilesInfosIterator() {
        return new Iterator<EntityTileInfo>() {

            Iterator<Map.Entry<Position, Food>> it = spawnedFood.entrySet().iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public EntityTileInfo next() {
                Map.Entry<Position, Food> entry = it.next();
                return new EntityTileInfo() {
                    Food f = entry.getValue();
                    Position p = entry.getKey();

                    @Override
                    public String getRessourceKey() {
                        return f.getRessourceKey();
                    }

                    @Override
                    public Position getPosition() {
                        return p;
                    }

                    @Override
                    public int getId() {
                        return p.hashCode();
                    }
                };
            }
        };
    }
}
