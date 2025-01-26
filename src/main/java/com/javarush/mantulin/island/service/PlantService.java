package com.javarush.mantulin.island.service;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.entity.Location;
import com.javarush.mantulin.island.entity.creature.plant.Plant;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс роста растений на локации.
 */
public class PlantService implements Runnable {
    private Island island;
    List<Location> locations;

    public PlantService(Island island) {
        this.island = island;
        locations = island.getLocations();
    }

    @Override
    public void run() {
        for (Location location : locations) {
            location.getLock().lock();
            try {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                int rand = random.nextInt(Settings.getInstance().getCreatureSettings().get(Plant.class).get("maxCountOnLocation").intValue());
                for (int i = 0; i < rand; i++) {
                    if (!location.addCreature(new Plant())) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                location.getLock().unlock();
            }

        }
    }
}
