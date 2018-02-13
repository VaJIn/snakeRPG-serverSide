package fr.vajin.snakerpg.engine;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import fr.vajin.snakerpg.GameConstants;
import fr.vajin.snakerpg.engine.utilities.Direction;
import fr.vajin.snakerpg.engine.utilities.Position;

import java.util.*;

/**
 * A simple snake that move, grow and die.
 */
public class SimpleSnake extends Snake {

    private int size;

    //An atom A is referenced at the key K such as K.equals(A.getPosition())
    private Multimap<Position, SnakeAtom> atoms;

    //HEad of the snake
    private SnakeAtom head;
    //Tail of the snake
    private SnakeAtom tail;

    private Deque<Direction> nextDirections;
    private Direction lastDirection;

    private int lastMoveTick;

    private int leftToGrow;

    private List<Position> newPositions;

    private class SimpleSnakeAtom extends SnakeAtom {

        private SimpleSnakeAtom(Position position, int id) {
            super(position, id);
        }

        private SimpleSnakeAtom(Position position, SnakeAtom towardsTail) {
            super(position, towardsTail.getId() + 1);
            this.atomTowardsTail = towardsTail;
            towardsTail.atomTowardsHead = this;
//            System.out.println("New atom : pos " + position + " id : " + this.getId() +  "TowardTail : " + getAtomTowardsTail().getPosition());
        }
    }

    public SimpleSnake(List<Position> startingPositions, Direction startingDirection) {
        super(100, 100, 0, 0, 2);

        this.atoms = MultimapBuilder.hashKeys().arrayListValues().build();

        this.nextDirections = new ArrayDeque<>();
        this.lastDirection = startingDirection;

        this.leftToGrow = 0;

        this.lastMoveTick = -1;

        Iterator<Position> it = startingPositions.iterator();

        this.tail = new SimpleSnakeAtom(it.next(), 0);
        SnakeAtom last = tail;
        atoms.put(last.getPosition(), last);
        while (it.hasNext()) {
            last = new SimpleSnakeAtom(it.next(), last);
            atoms.put(last.getPosition(), last);
        }
        this.head = last;
        this.size = atoms.size();

    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int grow(int howMuch) {
        return (leftToGrow = leftToGrow + howMuch);
    }

    @Override
    public int shrink(int howMuch) {
        return 0;
    }

    @Override
    public void sendAction(int action) {
        switch (action) {
            case TURN_LEFT:
                if (nextDirections.isEmpty()) {
                    nextDirections.addLast(lastDirection.rotate(false));
                } else {
                    this.nextDirections.addLast(nextDirections.peekLast().rotate(false));
                }
                break;
            case TURN_RIGHT:
                if (nextDirections.isEmpty()) {
                    nextDirections.addLast(lastDirection.rotate(true));
                } else {
                    this.nextDirections.addLast(nextDirections.peekLast().rotate(true));
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown action " + action);
        }
    }

    @Override
    public Iterator<SnakeAtom> activatedAtomIterator() {
        return null;
    }

    @Override
    public boolean computeTick(int tick) {
        if (this.getLifePoint() <= 0) {
            //Snake is dead
            return false;
        }
        newPositions = new ArrayList<>();

        /*
            Speed in tile/s
            Tickrate = 32 tick/s;
            tickrate / speed -> tick/tile
         */
        if (tick - lastMoveTick > GameConstants.TICKRATE / this.getSpeed()) {
            //We move

            lastMoveTick = tick;

            Direction currentDirection;

            if (nextDirections.isEmpty()) {
                currentDirection = lastDirection;
            } else {
                currentDirection = this.nextDirections.removeFirst();
                lastDirection = currentDirection;
            }


            Position newPosition = new Position(this.head.getPosition());
            newPosition.moveInDirection(currentDirection, 1);


            boolean destroyed = false;

            //check if we don't hit ourself
            if (this.coverPosition(newPosition)) {
                destroyed = true;
            }

            this.newPositions.add(newPosition);


            SnakeAtom newHead = new SimpleSnakeAtom(newPosition, head);

            notifySpriteChange(head.getId(), head.getPosition(), head.getGraphicKey());

            head = newHead;

            atoms.put(head.getPosition(), head);

            notifyChangeAtPosition(newPosition, NEW_COVERED_POSITION);
            notifySpriteChange(head.getId(), head.getPosition(), head.getGraphicKey());

            //Remove tail if needed (no growth)

            //Check if new head position is valid on the board

            if (newPosition.getX() < 0 || newPosition.getX() >= this.getEngine().getField().getWidth() || newPosition.getY() < 0 || newPosition.getY() >= this.getEngine().getField().getHeight()) {
                this.destroy();
            } else {
                FieldUnit headFieldUnit = this.getEngine().getField().getFieldUnits(newPosition);
                if (!headFieldUnit.isWalkable()) {
                    System.out.println("\033[31mField " + headFieldUnit + " ! You die\033[0m");
                    destroyed = true;
                }
            }


            if (leftToGrow > 0) {
                leftToGrow--;
                size++;
            } else {
                //Remove tail
                tail.setActivated(false);
                notifySpriteChange(tail.getId(), tail.getPosition(), tail.getGraphicKey());
                notifyChangeAtPosition(tail.getPosition(), ONE_LESS_COVER_ON_POSITION);
                tail = tail.getAtomTowardsHead();
                notifySpriteChange(tail.getId(), tail.getPosition(), tail.getGraphicKey());
            }

            if (destroyed) {
                this.destroy();
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Position> getNewPositions() {
        return newPositions;
    }

    @Override
    public boolean coverPosition(Position pos) {
        //For all atoms at position pos
        for (SnakeAtom sk : atoms.get(pos)) { //At worse the multimap return an empty collection, so no risk of NullPointerException
            if (sk.isActivated()) {
                //if an atom at this position is
                return true;
            }
        }
        return false;
    }

    @Override
    public void handleCollisionWith(Entity otherObject, Position collisionPosition, boolean isInitater) {
        //EVOLUTION : encapsulate this method into a strategie pattern (power : snake dont die on contact with itself, as an example
        if (isInitater && otherObject.isKiller()) {
            this.destroy();
        } else {
            //Do nothing
        }
    }

    @Override
    public boolean isKiller() {
        return true;
    }

    @Override
    public void inflictDamage(int damage) {
        //DAMAGE FORMULA : resistance decrease damage linearly, such as 0 resistance = no damage reduction, 100 resistance = full reduction
        this.setLifePoint(damage - (damage * this.getResistance()) / 100);
    }

    @Override
    public void destroy() {
        //Dis is death
        this.setLifePoint(0);
        this.notifyOfDestruction();
    }

    @Override
    public String getGraphicRessourceKeyForPosition(Position pos) {
        return null;
    }

    @Override
    public Iterator<EntityTileInfo> getEntityTilesInfosIterator() {
        return new EntityTileInfoInterator(head);
    }

    private class EntityTileInfoInterator implements Iterator<EntityTileInfo> {

        SnakeAtom current;

        private EntityTileInfoInterator(SnakeAtom head) {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null && current.isActivated();
        }

        @Override
        public EntityTileInfo next() {
            if (current == null) {
                throw new NoSuchElementException("There is no next element !");
            }
            EntityTileInfo info = new EntityTileInfo() {
                final String ressourceKey = current.getGraphicKey();
                final Position pos = current.getPosition();
                final int id = current.getId();

                @Override
                public String getRessourceKey() {
                    return ressourceKey;
                }

                @Override
                public Position getPosition() {
                    return pos;
                }

                @Override
                public int getId() {
                    return id;
                }
            };

            current = current.getAtomTowardsTail();

            return info;
        }
    }
}
