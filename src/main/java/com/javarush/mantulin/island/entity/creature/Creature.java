package com.javarush.mantulin.island.entity.creature;

import com.javarush.mantulin.island.entity.Location;

import java.util.Objects;

/**
 * Класс для определения верхушки иерархии.
 */
public abstract class Creature {

    static int uniqueId = 0;
    String name;

    public Creature() {
        uniqueId++;
        this.name = this.getClass().getSimpleName()+uniqueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creature creature = (Creature) o;
        return Objects.equals(name, creature.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
