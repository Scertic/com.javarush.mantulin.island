package com.javarush.mantulin.island.service;

import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.entity.Location;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.Animal;
import com.javarush.mantulin.island.util.Direction;

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
                //проверяем на смерть
                if (!animal.isAlive) {
                    location.getLock().lock();
                    try {
                        location.removeCreature(animal);
                    } finally {
                        location.getLock().unlock();
                    }
                    continue;
                }
                //еда
                Creature creatureToEat = location.findCreatureToEat(animal);
                if (animal.eat(creatureToEat) != null) {
                    location.getLock().lock();
                    try {
                        location.removeCreature(creatureToEat);
                    } finally {
                        location.getLock().unlock();
                    }
                }
                //размножение
                if (location.getCreaturesOnLocation().stream()
                        .filter(x -> x.getClass() == animal.getClass())
                        .count() > 1) {
                    Creature reproduce = animal.reproduce();
                    if (reproduce != null) {
                        location.getLock().lock();
                        try {
                            if (!location.addCreature(reproduce)) {
                                reproduce = null;
                            }
                        } finally {
                            location.getLock().unlock();
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
                    if (location1 == null) {
                        continue;
                    }
                    location1.getLock().lock();
                    try {
                        location1.addCreature(creature);
                    } finally {
                        location1.getLock().unlock();
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
