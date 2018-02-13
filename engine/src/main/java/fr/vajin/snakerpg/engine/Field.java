package fr.vajin.snakerpg.engine;


import fr.vajin.snakerpg.engine.utilities.Position;

public interface Field {
    FieldUnit getFieldUnits(Position position);

    int getWidth();

    int getHeight();

}
