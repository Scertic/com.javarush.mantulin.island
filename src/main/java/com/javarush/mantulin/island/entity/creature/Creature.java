package com.javarush.mantulin.island.entity.creature;

import com.javarush.mantulin.island.entity.Location;

/**
 * Класс для определения верхушки иерархии.
 */
public abstract class Creature {
    Location location;

    public Creature(Location location) {
        this.location = location;
    }
}
