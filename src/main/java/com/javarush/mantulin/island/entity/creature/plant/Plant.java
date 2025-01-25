package com.javarush.mantulin.island.entity.creature.plant;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.Location;
import com.javarush.mantulin.island.entity.creature.Creature;

public class Plant extends Creature {
    private double weight;
    public Plant() {
        super();
        weight = Settings.getInstance().getCreatureSettings().get(this.getClass()).get("maxCountOnLocation").doubleValue();
    }

    public void looseWeight(double eaten) {
        weight -= eaten;
    }

    public double getWeight() {
        return weight;
    }
}
