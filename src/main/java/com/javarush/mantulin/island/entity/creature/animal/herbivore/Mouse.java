package com.javarush.mantulin.island.entity.creature.animal.herbivore;

import com.javarush.mantulin.island.entity.Location;

public class Mouse extends Herbivore{
    public Mouse(Location location) {
        super(location);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC01";
    }
}
