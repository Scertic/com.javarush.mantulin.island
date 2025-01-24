package com.javarush.mantulin.island.service;

import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.entity.Location;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.Animal;

public class LocationAnimalService implements Runnable{

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
                    location.removeCreature(animal);
                    location.getLock().unlock();
                    continue;
                }
                //еда
                Creature creatureToEat = location.findCreatureToEat(animal);
                if(animal.eat(creatureToEat) != null) {
                    location.getLock().lock();
                    location.removeCreature(creatureToEat);
                    location.getLock().unlock();
                }
                //размножение
                if (location.getCreaturesOnLocation().stream()
                        .filter(x -> x.getClass() == animal.getClass())
                        .count() > 1) {
                    Creature reproduce = animal.reproduce();
                    if (reproduce != null) {
                        location.getLock().lock();
                        location.addCreature(reproduce);
                        location.getLock().unlock();
                    }

                }
            }
            //Спавним растение
//           addCreature(new Plant());
        }
    }

    @Override
    public void run() {
        simulateLifeCycle();
    }
}
