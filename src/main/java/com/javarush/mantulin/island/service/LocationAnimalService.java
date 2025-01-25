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

    private Island island;
    private Location location;

    public LocationAnimalService(Island island, Location location) {
        this.island = island;
        this.location = location;
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
                            location.addCreature(reproduce);
                        } finally {
                            location.getLock().unlock();
                        }
                    }
                }
                //перемещение
                Direction direction = ((Animal) creature).chooseDirection();
                int step = ((Animal) creature).move(direction);
                if (step > 0) {
                    int locationI = island.getLocationI(location);
                    int locationJ = island.getLocationJ(location);
                    if (locationI == -1 || locationJ == -1) {
                        continue;
                    }
                    if (direction == Direction.RIGHT) {
                        Location location1 = null;
                        location1 = island.getLocation(locationI, locationJ + step);
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
                    if (direction == Direction.LEFT) {
                        Location location1 = null;
                        location1 = island.getLocation(locationI, locationJ - step);
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
                    if (direction == Direction.UP) {
                        Location location1 = null;
                        location1 = island.getLocation(locationI - step, locationJ);
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
                    if (direction == Direction.DOWN) {
                        Location location1 = null;
                        location1 = island.getLocation(locationI + step, locationJ);
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
