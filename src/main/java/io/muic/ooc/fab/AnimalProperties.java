package io.muic.ooc.fab;

import io.muic.ooc.fab.Field;
import io.muic.ooc.fab.Location;

import java.util.List;

public interface AnimalProperties{
    void init(boolean randomAge, Field field, Location location);

    void act(List<AnimalProperties> newAnimals);

    boolean isAlive();

    void setDead();

    Location getLocation();

    void setLocation(Location newLocation);

    void incrementAge();

    void giveBirth(List<AnimalProperties> newAnimals);

    Animal breedOne(Field field, Location location);

    int breed();

    boolean canBreed();

}
