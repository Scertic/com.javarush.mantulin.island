package com.javarush.mantulin.island.util;

import com.javarush.mantulin.island.Settings;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.Animal;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.Herbivore;
import com.javarush.mantulin.island.entity.creature.animal.predator.Predator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalFactory {

    /**
     * Метод возвращет травоядное животное.
     * Метод требует конструктор по умолчанию.
     * @return - возвращает случайный экземпляр травоядного животного.
     */
    public Animal createHerbivore() {
        List<Class<? extends Creature>> list = Settings.maxNumbersOfCreatures.keySet().stream().filter(Herbivore.class::isAssignableFrom).toList();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomIX = random.nextInt(list.size());
        try {
            return (Animal) list.get(randomIX).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод возвращет хищное животное.
     * Метод требует конструктор по умолчанию.
     * @return - возвращает случайный экземпляр хищного животного.
     */
    public Animal createPredator() {
        List<Class<? extends Creature>> list = Settings.maxNumbersOfCreatures.keySet().stream().filter(Predator.class::isAssignableFrom).toList();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomIX = random.nextInt(list.size());
        try {
            return (Animal) list.get(randomIX).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
