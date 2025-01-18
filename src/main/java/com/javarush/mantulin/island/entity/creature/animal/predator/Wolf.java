package com.javarush.mantulin.island.entity.creature.animal.predator;

import com.javarush.mantulin.island.entity.Location;

public class Wolf extends Predator{
    public Wolf(Location location) {
        super(location);
    }


    @Override
    public String toString() {
        return "\uD83D\uDC3A";
    }
}
