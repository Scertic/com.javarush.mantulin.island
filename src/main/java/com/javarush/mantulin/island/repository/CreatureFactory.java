package com.javarush.mantulin.island.repository;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.Herbivore;
import com.javarush.mantulin.island.entity.creature.animal.predator.Predator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CreatureFactory {

    /**
     * Метод для получения экземпляра существа определенного класса.
     * Метод требует конструктор по умолчанию.
     * @param creatureClass - класс существа
     * @return - существо или null при неудаче.
     */
    public Creature getCreature(Class<? extends Creature> creatureClass) {
        try {
            return creatureClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

}
