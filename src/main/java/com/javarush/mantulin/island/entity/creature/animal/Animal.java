package com.javarush.mantulin.island.entity.creature.animal;

import com.javarush.mantulin.island.Settings;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.Herbivore;
import com.javarush.mantulin.island.entity.creature.animal.predator.Predator;
import com.javarush.mantulin.island.entity.creature.plant.Plant;
import com.javarush.mantulin.island.util.Direction;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Абстрактный класс для описания поведения и состояния животного по умолчанию.
 */
public abstract class Animal extends Creature {

    // ОБЩИЕ ХАРАКТЕРИСТИКИ
    // СЫТОСТЬ satiety = ? вес
    // ВЕС ЖИВОТНОГО
    // СКОРОСТЬ ПЕРЕМЕЩЕНИЯ

    double weight;
    public int satiety = 100;
    private final double forFullSatiety;
    public boolean isAlive = true;

    public Animal() {
        super();
        this.weight = Settings.maxNumbersOfCreatures.get(this.getClass())[0];
        this.forFullSatiety = Settings.maxNumbersOfCreatures.get(this.getClass())[3];
    }


    public Creature eat(Creature creature) {
        decreaseSatiety();
        if (this.satiety == 100) {
            return null;
        }
        if (creature == null) {
            //Поменять характеристики
            decreaseSatiety();
            return null;
        }
        if (this instanceof Herbivore) {
            if (creature.getClass() == Plant.class) {
                //Убавить у травы "здоровье" на необъодимое количество для насышения животного
                //либо до 0 если "здоровья" травы не хватает
                //System.out.println(this.getClass().getSimpleName() + " eats " + creature.getClass().getSimpleName());
                Double creatureWeight = Settings.maxNumbersOfCreatures.get(creature.getClass())[0];
                if (Double.compare(forFullSatiety, creatureWeight) < 0) {
                    satiety = 100;
                } else {
                    satiety += creatureWeight;
                }
                return creature;
                //увеличить сытость текущего животного в соответсвии с количеством съеденного
            } //иначе убавить сытость и вес ? текущего создания
        } else if (this instanceof Predator) {
            if (creature instanceof Animal) {
                //Найти вероятность того, что текущее существо может съесть входящее
                Integer chance = getChanceToEat(creature);
                Random random = new Random();
                if (random.nextInt(100) + 1 <= chance) {
                    //System.out.println(this.getClass().getSimpleName() + " eats " + creature.getClass().getSimpleName());
                    Double creatureWeight = Settings.maxNumbersOfCreatures.get(creature.getClass())[0];
                    if (Double.compare(forFullSatiety, creatureWeight) < 0) {
                        satiety = 100;
                    } else {
                       satiety += creatureWeight;
                    }
                    return creature;
                }
            }
        }
        decreaseSatiety();
        return null;
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
        // КТО ИМЕННО ЭТОТ Creature БУДЕТ ВЛИЯТЬ НА ФОРМАТ ПОЕДАНИЯ
        // КОГДА СТАНЕТ ПОНЯТНО КТО КОНКРЕТНО ЭТО Creature
        // МЫ МОЖЕМ ОПРЕДЕЛИТЬ ВЕРОЯТНОСТЬ ЕГО ПОЕДАНИЯ И РЕАЛИЗОВАТЬ ЭТУ ЛОГИКУ

    }


    void move(Direction direction) {
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
    }

    public Creature reproduce() {
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
        try {
            decreaseSatiety();
            if (satiety < 70) {
                return null;
            }
            return this.getClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            return null;
        }
    }

    void die() {
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
        System.out.println(this.getClass().getSimpleName() + " dies");
        isAlive = false;
    }

    void decreaseSatiety() {
        this.satiety = this.satiety - (int) (weight / Settings.maxNumbersOfCreatures.get(this.getClass())[3]);
        if (satiety < 0) {
            this.isAlive = false;
            //System.out.println(this.getClass().getSimpleName() + " умер от голода");
        }
    }

    protected Integer getChanceToEat(Creature creature) {
        return Settings.chanceMap.get(this.getClass()).get(creature.getClass());
    }

}
