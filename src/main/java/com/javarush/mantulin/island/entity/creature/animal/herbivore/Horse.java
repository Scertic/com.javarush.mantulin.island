package com.javarush.mantulin.island.entity.creature.animal.herbivore;

import com.javarush.mantulin.island.entity.Location;

public class Horse extends Herbivore{
    public Horse(Location location) {
        super(location);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC0E";
    }
}
