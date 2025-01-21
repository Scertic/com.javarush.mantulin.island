package com.javarush.mantulin.island.util;

import com.javarush.mantulin.island.Settings;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.Herbivore;
import com.javarush.mantulin.island.entity.creature.animal.predator.Predator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CreatureFactory {

    private List<Class<? extends Creature>> listHerbivore;
    private List<Class<? extends Creature>> listPredator;

    public CreatureFactory() {
        this.listHerbivore = Settings.maxNumbersOfCreatures.keySet().stream().filter(Herbivore.class::isAssignableFrom).toList();;
        this.listPredator = Settings.maxNumbersOfCreatures.keySet().stream().filter(Predator.class::isAssignableFrom).toList();;
    }

    /**
     * Метод возвращет травоядное животное.
     * Метод требует конструктор по умолчанию.
     * @return - возвращает случайный экземпляр травоядного животного.
     */
    public Creature getHerbivore() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomIX = random.nextInt(listHerbivore.size());
        try {
            return listHerbivore.get(randomIX).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод возвращет хищное животное.
     * Метод требует конструктор по умолчанию.
     * @return - возвращает случайный экземпляр хищного животного.
     */
    public Creature getPredator() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomIX = random.nextInt(listPredator.size());
        try {
            return listPredator.get(randomIX).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для получения экземпляра существа определенного класса.
     * Метод требует конструктор по умолчанию.
     * @param creatureClass - класс существа
     * @return - существо
     */
    public Creature getCreature(Class<? extends Creature> creatureClass) {
        try {
            return creatureClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
