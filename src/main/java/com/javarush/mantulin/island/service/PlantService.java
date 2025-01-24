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
        //TODO Растения растут или как?
        System.out.println("Запуск");
        for (Location location : locations) {
            location.getLock().lock();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            for (int i = 0; i < 200/*Settings.getInstance().getCreatureSettings().get(Plant.class).get("maxCountOnLocation").intValue()*/; i++) {
                if (!location.addCreature(new Plant())) {
                    System.out.println(location.getCreaturesOnLocation().stream().filter(x -> x.getClass() == Plant.class).count());
                }
            }
            location.getLock().unlock();
        }
    }
}
