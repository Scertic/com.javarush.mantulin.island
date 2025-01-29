package com.javarush.mantulin.island.entity.creature;

import com.javarush.mantulin.island.configuration.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Класс для определения верхушки иерархии.
 */
public abstract class Creature {

    private static int uniqueId = 0;
    private final String name;
    private final String ico;

    public List<Class<? extends Creature>> getMyFavoriteCreatureToEat() {
        return myFavoriteCreatureToEat;
    }

    protected List<Class<? extends Creature>> myFavoriteCreatureToEat;


    public Creature() {
        uniqueId++;
        this.name = this.getClass().getSimpleName()+uniqueId;
        this.ico = Settings.getInstance().getIcoMap().get(this.getClass());
        Map<Class<? extends Creature>, Integer> classIntegerMap = Settings.getInstance().getChanceMap().get(this.getClass());
        if (classIntegerMap == null || classIntegerMap.keySet().isEmpty()) {
            myFavoriteCreatureToEat = new ArrayList<>();
        } else {
            myFavoriteCreatureToEat = new ArrayList<>(classIntegerMap.keySet());
        }
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

    @Override
    public String toString() {
        return ico;
    }

    public String getName() {
        return name;
    }
}
