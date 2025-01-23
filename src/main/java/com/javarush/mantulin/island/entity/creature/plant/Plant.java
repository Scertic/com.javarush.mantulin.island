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

    public void risingWeight() {
        weight = Settings.getInstance().getCreatureSettings().get(this.getClass()).get("maxCountOnLocation").doubleValue();
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "\uD83C\uDF3F";
    }
}
