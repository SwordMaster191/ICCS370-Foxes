package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public abstract class Animal implements AnimalProperties{
    // Whether the animal is alive or not.
    private boolean alive = true;
    // The fox's position.
    protected Location location;
    // The field occupied.
    protected Field field;
    // Individual characteristics (instance fields).
    // The fox's age.
    protected int age = 0;

    public abstract Location moveToNewLocation();
    protected abstract int getMaxLiterSize();
    protected abstract int getBreedingAge();
    protected abstract int getMaxAge();
    protected abstract double getBreedingProbability();

    protected static final Random RANDOM = new Random();

    @Override
    public void init(boolean randomAge, Field field, Location location){
        this.field = field;
        setLocation((location));
        if(randomAge) age = RANDOM.nextInt(getMaxAge());
    }

    @Override
    public void act(List<AnimalProperties> newAnimals){
        incrementAge();
        if (isAlive()) {
            giveBirth(newAnimals);
            Location newLocation = moveToNewLocation();

            if(newLocation != null) setLocation(newLocation);
            else setDead();
        }
    }

    /**
     * Check whether the animal is alive or not.
     *
     * @return true if the animal is still alive.
     */
    @Override
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Return the fox's location.
     *
     * @return The fox's location.
     */
    public Location getLocation() {
        return location;
    }


    /**
     * Increase the age. This could result in the rabbit's death.
     */
   @Override
    public void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }


    /**
     * Indicate that the fox is no longer alive. It is removed from the field.
     */
    @Override
    public void setDead() {
        setAlive(false);
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Place the rabbit at the new location in the given field.
     *
     * @param newLocation The rabbit's new location.
     */
    @Override
    public void setLocation(Location newLocation) {
        if (location != null) field.clear(location);
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Generate a number representing the number of births, if it can breed.
     *
     * @return The number of births (may be zero).
     */
    @Override
    public int breed() {
        int births = 0;
        if (canBreed() && RANDOM.nextDouble() <= getBreedingProbability()) {
            births = RANDOM.nextInt(getMaxLiterSize()) + 1;
        }
        return births;
    }


    /**
     * A rabbit can breed if it has reached the breeding age.
     *
     * @return true if the rabbit can breed, false otherwise.
     */
    @Override
    public boolean canBreed() {
        return age >= getBreedingAge();
    }


    /**
     * Check whether or not this rabbit is to give birth at this step. New
     * births will be made into free adjacent locations.
     *
     * @param newRabbits A list to return newly born rabbits.
     */
    @Override
    public void giveBirth(List newRabbits) {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = field.getFreeAdjacentLocations(location);
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = breedOne(field, loc);
            newRabbits.add(young);
        }
    }

    @Override
    public Animal breedOne(Field field, Location location){
        return AnimalFactory.createAnimal(getClass(), field, location);
    }


}
