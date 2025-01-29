package com.javarush.mantulin.island.service;

import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.entity.Location;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.Animal;
import com.javarush.mantulin.island.util.Direction;

import java.util.concurrent.TimeUnit;

/**
 * Класс для запуска дня на острове.
 */
public class LocationAnimalService implements Runnable {

    private final Island island;
    private final Location location;
    private final int locationI;
    private final int locationJ;

    public LocationAnimalService(Island island, Location location) {
        this.island = island;
        this.location = location;
        this.locationI = island.getLocationI(location);
        this.locationJ = island.getLocationJ(location);
    }

    private void simulateLifeCycle() {
        for (Creature creature : location.getCreaturesOnLocation()) {
            if (creature instanceof Animal animal) {
                if (!animal.isAlive) {
                        location.removeCreature(animal);
                    continue;
                }
                //еда
                Creature creatureToEat = location.findCreatureToEat(animal);
                if (animal.eat(creatureToEat) != null) {
                        location.removeCreature(creatureToEat);
                }
                //размножение
                if (location.getCreaturesOnLocation().stream()
                        .filter(x -> x.getClass() == animal.getClass())
                        .count() > 1) {
                    Creature reproduce = animal.reproduce();
                    if (reproduce != null) {
                            if (!location.addCreature(reproduce)) {
                                reproduce = null;
                            }
                    }
                }
                //перемещение
                Direction direction = ((Animal) creature).chooseDirection();
                int step = ((Animal) creature).move(direction);
                if (step > 0) {
                    Location location1 = null;
                    if (locationI == -1 || locationJ == -1) {
                        continue;
                    }
                    if (direction == Direction.RIGHT) {
                        location1 = island.getLocation(locationI, locationJ + step);
                    }
                    if (direction == Direction.LEFT) {
                        location1 = island.getLocation(locationI, locationJ - step);
                    }
                    if (direction == Direction.UP) {
                        location1 = island.getLocation(locationI - step, locationJ);
                    }
                    if (direction == Direction.DOWN) {
                        location1 = island.getLocation(locationI + step, locationJ);
                    }
                    if (location1 == null || location1.equals(location)) {
                        continue;
                    }

                    Location firstLocation = location.getName().compareTo(location1.getName()) < 0 ? location : location1;
                    Location secondLocation = firstLocation == location ? location1 : location;

                    firstLocation.getLock().lock();
                    try {
                        secondLocation.getLock().lock();
                        try {
                            if (location1.addCreature(creature)) {
                                location.removeCreature(creature);
                            }
                        } finally {
                            secondLocation.getLock().unlock();
                        }
                    } finally {
                        firstLocation.getLock().unlock();
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            simulateLifeCycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
