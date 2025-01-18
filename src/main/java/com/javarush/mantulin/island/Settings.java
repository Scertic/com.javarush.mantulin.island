package com.javarush.mantulin.island;

import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.predator.*;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.*;
import com.javarush.mantulin.island.entity.creature.plant.Plant;

import java.util.Map;

public class Settings {

    public final static int columnsCount = 1;
    public final static int rowsCount = 1;

    /**
     * Карта соответсвий классов животных и их характеристикам.
     * Порядок в массиве:
     * 0 - Вес одного животного, кг
     * 1- Максимальное количество животных этого вида на одной локации
     * 2 - Скорость перемещения, не более чем локаций за ход
     * 3 - Сколько киллограммов пищи нужно животному до полного насыщения
     */
    public final static Map<Class<? extends Creature>, Double[]> maxNumbersOfCreatures = Map.of(
            Wolf.class, new Double[]{50.0, 30.0, 3.0, 8.0},
            Fox.class, new Double[]{8.0, 30.0, 2.0, 2.0},
            Horse.class, new Double[]{400.0, 20.0, 4.0, 60.0},
            Plant.class, new Double[]{1.0, 200.0, 0.0, 0.0}
    );

    public final static Map<Class<? extends Creature>, Map<Class<? extends Creature>, Integer>> chanceMap = Map.of(
            Wolf.class, Map.of(Wolf.class, 0,
                    Fox.class, 0,
                    Horse.class, 10)
    );

}
