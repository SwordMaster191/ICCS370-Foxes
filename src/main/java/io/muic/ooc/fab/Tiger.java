package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;
public class Tiger extends Animal{

    private int foodLevel;

    @Override
    public void init(boolean randomAge, Field field, Location location){
        super.init(randomAge, field, location);
        foodLevel = RANDOM.nextInt(AnimalType.FOX.getFoodValue());
    }

    public void incrementHunger(){
        foodLevel--;
        if (foodLevel <= 0) setDead();
    }

    public Location findFood(){
        List<Location> adj = field.adjacentLocations(getLocation());
        Iterator<Location> iter = adj.iterator();
        while(iter.hasNext()){
            Location next = iter.next();
            Object animal = field.getObjectAt(next);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;

                if(rabbit.isAlive()){
                    rabbit.setDead();
                    foodLevel = AnimalType.RABBIT.getFoodValue();
                    return next;
                }
            }
        }
        return null;
    }

    @Override
    public Location moveToNewLocation() {
        Location newLocation = findFood();
        if (newLocation == null) newLocation = field.freeAdjacentLocation(getLocation());
        return newLocation;
    }


    @Override
    protected int getMaxAge(){
        return 175;
    }

    @Override
    protected double getBreedingProbability(){
        return AnimalType.TIGER.getBreedingProbability();
    }

    @Override
    protected int getMaxLiterSize(){
        return 2;
    }

    @Override
    protected int getBreedingAge() {
        return 20;
    }

    @Override
    public void act(List<AnimalProperties> newAnimals){
        super.act(newAnimals);
        incrementHunger();
    }
}
