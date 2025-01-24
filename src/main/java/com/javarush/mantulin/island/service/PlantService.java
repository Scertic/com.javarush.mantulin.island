package com.javarush.mantulin.island.service;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.entity.Location;
import com.javarush.mantulin.island.entity.creature.plant.Plant;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlantService implements Runnable {
    private Island island;
    List<Location> locations;

    public PlantService(Island island) {
        this.island = island;
        locations = island.getLocations();
    }

    @Override
    public void run() {
        System.out.println("Запуск");
        for (Location location : locations) {
            location.getLock().lock();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            for (int i = 0; i < random.nextInt(Settings.getInstance().getCreatureSettings().get(Plant.class).get("maxCountOnLocation").intValue()); i++) {
                location.addCreature(new Plant());
            }
            location.getLock().unlock();
        }
    }
}
