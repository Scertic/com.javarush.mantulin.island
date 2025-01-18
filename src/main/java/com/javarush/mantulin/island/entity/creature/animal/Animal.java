package com.javarush.mantulin.island.entity.creature.animal;

import com.javarush.mantulin.island.Settings;
import com.javarush.mantulin.island.entity.Location;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.Herbivore;
import com.javarush.mantulin.island.entity.creature.animal.predator.Predator;
import com.javarush.mantulin.island.entity.creature.plant.Plant;
import com.javarush.mantulin.island.util.Direction;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Абстрактный класс для описания поведения и состояния животного по умолчанию.
 */
public abstract class Animal extends Creature {

    // ОБЩИЕ ХАРАКТЕРИСТИКИ
    // СЫТОСТЬ satiety = ? вес
    // ВЕС ЖИВОТНОГО
    // СКОРОСТЬ ПЕРЕМЕЩЕНИЯ

    double weight;
    int satiety;

    public Animal(Location location) {
        super(location);
    }


    public void eat(Creature creature) {
        if (creature == null) {
            //Поменять характеристики
            return;
        }
        if (this instanceof Herbivore) {
            if (creature.getClass() == Plant.class) {
                //Убавить у травы "здоровье" на необъодимое количество для насышения животного
                //либо до 0 если "здоровья" травы не хватает

                //увеличить сытость текущего животного в соответсвии с количеством съеденного
            } //иначе убавить сытость и вес ? текущего создания
        } else if (this instanceof Predator) {
            if (creature instanceof Animal) {
                //Найти вероятность того, что текущее существо может съесть входящее
                Integer chance = getChanceToEat(creature);
                Random random = new Random();
                if (random.nextInt(100)+1 <= chance) {
                    System.out.println(this.getClass().getSimpleName() + " eats " + creature.getClass().getSimpleName());
                    ((Animal) creature).die();
                }
            }
        }
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
        // КТО ИМЕННО ЭТОТ Creature БУДЕТ ВЛИЯТЬ НА ФОРМАТ ПОЕДАНИЯ
        // КОГДА СТАНЕТ ПОНЯТНО КТО КОНКРЕТНО ЭТО Creature
        // МЫ МОЖЕМ ОПРЕДЕЛИТЬ ВЕРОЯТНОСТЬ ЕГО ПОЕДАНИЯ И РЕАЛИЗОВАТЬ ЭТУ ЛОГИКУ

    }

    /**
     * Метод поиска создания для еды.
     * @return - создание или null, если совпадения не найдены
     */
    public Creature findCreatureToEat() {
        Set<Creature> creaturesNearby = location.getCreatures();
        Map<Class<? extends Creature>, Integer> classIntegerMap = Settings.chanceMap.get(this.getClass());
        if (classIntegerMap == null) {
            return null;
        }
        Set<Class<? extends Creature>> myFavoriteCreature = new HashSet<>(classIntegerMap.keySet());
        if (myFavoriteCreature.isEmpty()) {
            return null;
        }
        for (Creature creature1 : creaturesNearby) {
            if(myFavoriteCreature.contains(creature1.getClass())) {
                return creature1;
            }
        }
        return null;
    }

    void move(Direction direction) {
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
    }

    Creature reproduce() {
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
        return null;
    }

    void die() {
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
        System.out.println(this.getClass().getSimpleName() + " dies");
        location.removeCreature(this);
    }

    void decreaseWeight(){
    }

    protected Integer getChanceToEat(Creature creature) {
        Integer chance = Settings.chanceMap.get(this.getClass()).get(creature.getClass());
        return chance;
    }

}
