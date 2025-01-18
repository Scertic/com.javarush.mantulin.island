package com.javarush.mantulin.island.entity.creature;

import com.javarush.mantulin.island.entity.Location;

import java.util.Objects;

/**
 * Класс для определения верхушки иерархии.
 */
public abstract class Creature {
    String name;
    protected Location location;

    public Creature(Location location) {
        this.location = location;
        this.name = this.getClass().getSimpleName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creature creature = (Creature) o;
        return Objects.equals(name, creature.name) && Objects.equals(location, creature.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location);
    }
}
