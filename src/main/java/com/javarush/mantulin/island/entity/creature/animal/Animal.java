package com.javarush.mantulin.island.entity.creature.animal;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.Herbivore;
import com.javarush.mantulin.island.entity.creature.animal.predator.Predator;
import com.javarush.mantulin.island.entity.creature.plant.Plant;
import com.javarush.mantulin.island.util.Direction;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Абстрактный класс для описания поведения и состояния животного по умолчанию.
 */
public abstract class Animal extends Creature {

    double weight;
    public int satiety = 100;
    private final double forFullSatiety;
    public boolean isAlive = true;

    public Animal() {
        super();
        this.weight = Settings.getInstance().getCreatureSettings().get(this.getClass()).get("weight").doubleValue();
        this.forFullSatiety = Settings.getInstance().getCreatureSettings().get(this.getClass()).get("foodWeightForFullSatiety").doubleValue();
    }

    public Creature eat(Creature creature) {
        decreaseSatiety();
        if (satiety < 0) {
            this.die();
            return null;
        }
        if (satiety == 100) {
            return null;
        }
        if (creature == null) {
            //Поменять характеристики
            decreaseSatiety();
            return null;
        }
        if (this instanceof Herbivore) {
            if (creature.getClass() == Plant.class) {
                increaseSatiety(creature);
                return creature;
            }
        } else if (this instanceof Predator) {
            if (creature instanceof Animal) {
                if (!((Animal) creature).isAlive) {
                    return null;
                }
                //Найти вероятность того, что текущее существо может съесть входящее
                Integer chance = getChanceToEat(creature);
                Random random = new Random();
                if (random.nextInt(100) + 1 <= chance) {
                    increaseSatiety(creature);
                    return creature;
                }
            }
        }
        decreaseSatiety();
        return null;
    }

    protected void increaseSatiety(Creature creature) {
        double creatureWeight = Settings.getInstance().getCreatureSettings().get(creature.getClass()).get("weight").doubleValue();
        if (Double.compare(forFullSatiety, creatureWeight) < 0) {
            satiety = 100;
        } else {
           satiety += creatureWeight;
        }
    }

    /**
     * Метод возвращает количество перемещений за раз.
     * @param direction - направление перемещения
     * @return - количество перемещений за раз
     */
    public int move(Direction direction) {
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
        int step = Settings.getInstance().getCreatureSettings().get(this.getClass()).get("maxSteps").intValue();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(step+1);
    }

    /**
     * Метод выбора случаного направления.
     * @return - возвращает направление.
     */
    public Direction chooseDirection() {
        int count = Direction.values().length;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return Direction.values()[random.nextInt(count)];
    }

    /**
     * Метод размножения существ.
     * @return - возвращает новый экземпляр текущего существа. Возвращет null, если размножение не удалось.
     */
    public Creature reproduce() {
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
        try {
            if (satiety < 100) {
                return null;
            }
            decreaseSatiety();
            return this.getClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            return null;
        }
    }

    void die() {
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
        isAlive = false;
    }

    protected void decreaseSatiety() {
        this.satiety = this.satiety - (int) (weight / Settings.getInstance().getCreatureSettings().get(this.getClass()).get("foodWeightForFullSatiety").doubleValue());
        if (satiety < 0) {
            this.die();
//            System.out.println(this.getClass().getSimpleName() + " умер от голода");
        }
    }

    protected Integer getChanceToEat(Creature creature) {
        return Settings.getInstance().getChanceMap().get(this.getClass()).get(creature.getClass());
    }

}
