package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;

public class Hunter extends Animal{

    @Override
    public void init(boolean randomAge, Field field, Location location) {
        super.init(randomAge, field, location);
    }

    @Override
    protected double getBreedingProbability() {
        return  AnimalType.HUNTER.getBreedingProbability();
    }

    @Override
    protected int getMaxLiterSize() {
        return 2;
    }

    @Override
    protected int getBreedingAge() {
        return 80;
    }

    @Override
    public Location moveToNewLocation() {
        Location newLocation = findFood();
        if (newLocation == null) {
            newLocation = field.freeAdjacentLocation(getLocation());
        }
        return newLocation;
    }

    @Override
    protected int getMaxAge() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void act(List<AnimalProperties> newAnimals) {
        super.act(newAnimals);
    }

    private Location findFood() {
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    return where;
                }
            }
            if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    return where;
                }
            }

            if (animal instanceof Tiger) {
                Tiger tiger = (Tiger) animal;
                if (tiger.isAlive()) {
                    tiger.setDead();
                    return where;
                }
            }
        }
        return null;
    }
}