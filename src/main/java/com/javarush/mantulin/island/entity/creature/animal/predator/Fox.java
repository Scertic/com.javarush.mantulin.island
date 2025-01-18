package com.javarush.mantulin.island.entity.creature.animal.predator;

import com.javarush.mantulin.island.entity.Location;

public class Fox extends Predator{
    public Fox(Location location) {
        super(location);
    }

    @Override
    public String toString() {
        return "\uD83E\uDD8A";
    }
}
