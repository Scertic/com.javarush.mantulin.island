package com.javarush.mantulin.island;

import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.predator.*;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.*;
import com.javarush.mantulin.island.entity.creature.plant.Plant;

import java.util.HashMap;
import java.util.Map;

public class Settings {

    public final static int columnsCount = 2;
    public final static int rowsCount = 1;

    /**
     * Карта соответсвий классов животных и их характеристикам.
     * Порядок в массиве:
     * 0 - Вес одного животного, кг
     * 1- Максимальное количество животных этого вида на одной локации
     * 2 - Скорость перемещения, не более чем локаций за ход
     * 3 - Сколько киллограммов пищи нужно животному до полного насыщения
     */
    public final static Map<Class<? extends Creature>, Double[]> maxNumbersOfCreatures = new HashMap<>();
    public final static Map<Class<? extends  Creature>, String> icoMap = new HashMap<>();
    public final static int simCount = 10;

    static {
        icoMap.put(Wolf.class, "\uD83D\uDC3A");
        icoMap.put(Buffalo.class, "\uD83D\uDC03");
        icoMap.put(Bear.class, "\uD83D\uDC3B");
        icoMap.put(Horse.class, "\uD83D\uDC0E");
        icoMap.put(Deer.class, "\uD83E\uDD8C");
        icoMap.put(Boar.class, "\uD83D\uDC17");
        icoMap.put(Sheep.class, "\uD83D\uDC11");
        icoMap.put(Goat.class, "\uD83D\uDC10");
        icoMap.put(Boa.class, "\uD83D\uDC0D");
        icoMap.put(Fox.class, "\uD83E\uDD8A");
        icoMap.put(Eagle.class, "\uD83E\uDD85");
        icoMap.put(Rabbit.class, "\uD83D\uDC07");
        icoMap.put(Duck.class, "\uD83E\uDD86");
        icoMap.put(Mouse.class, "\uD83D\uDC01");
        icoMap.put(Caterpillar.class, "\uD83D\uDC1B");
        icoMap.put(Plant.class, "\uD83C\uDF3F");
    }

    static {
        maxNumbersOfCreatures.put(Wolf.class, new Double[]{50.0, 30.0, 3.0, 8.0});
        maxNumbersOfCreatures.put(Boa.class, new Double[]{15.0, 30.0, 1.0, 3.0});
        maxNumbersOfCreatures.put(Fox.class, new Double[]{8.0, 30.0, 2.0, 2.0});
        maxNumbersOfCreatures.put(Bear.class, new Double[]{500.0, 5.0, 2.0, 80.0});
        maxNumbersOfCreatures.put(Eagle.class, new Double[]{6.0, 20.0, 3.0, 1.0});
        maxNumbersOfCreatures.put(Horse.class, new Double[]{400.0, 20.0, 4.0, 60.0});
        maxNumbersOfCreatures.put(Deer.class, new Double[]{300.0, 20.0, 4.0, 50.0});
        maxNumbersOfCreatures.put(Rabbit.class, new Double[]{2.0, 150.0, 2.0, 0.45});
        maxNumbersOfCreatures.put(Mouse.class, new Double[]{0.05, 500.0, 1.0, 0.01});
        maxNumbersOfCreatures.put(Goat.class, new Double[]{60.0, 140.0, 3.0, 10.0});
        maxNumbersOfCreatures.put(Sheep.class, new Double[]{70.0, 140.0, 3.0, 15.0});
        maxNumbersOfCreatures.put(Boar.class, new Double[]{400.0, 50.0, 2.0, 50.0});
        maxNumbersOfCreatures.put(Buffalo.class, new Double[]{700.0, 10.0, 3.0, 100.0});
        maxNumbersOfCreatures.put(Duck.class, new Double[]{1.0, 200.0, 4.0, 0.15});
        maxNumbersOfCreatures.put(Caterpillar.class, new Double[]{0.01, 1000.0, 0.0, 0.0});
        maxNumbersOfCreatures.put(Plant.class, new Double[]{1.0, 200.0, 0.0, 0.0});
    }

    public final static Map<Class<? extends Creature>, Map<Class<? extends Creature>, Integer>> chanceMap = new HashMap<>();

    static {
        chanceMap.put(Wolf.class, Map.of(
//                    Fox.class, 0,
                Horse.class, 10,
                Deer.class, 15,
                Rabbit.class, 60,
                Mouse.class, 80,
                Goat.class, 60,
                Sheep.class, 70,
                Boar.class, 15,
                Buffalo.class, 10,
                Duck.class, 40
        ));
        chanceMap.put(Boa.class, Map.of(
                Fox.class, 15,
                Rabbit.class, 20,
                Mouse.class, 40,
                Duck.class, 10
        ));
        chanceMap.put(Fox.class, Map.of(
                Rabbit.class, 70,
                Mouse.class, 90,
                Duck.class, 60,
                Caterpillar.class, 40
        ));
        chanceMap.put(Bear.class, Map.of(
                Boa.class, 80,
                Horse.class, 40,
                Deer.class, 80,
                Rabbit.class, 80,
                Mouse.class, 90,
                Goat.class, 70,
                Sheep.class, 70,
                Boar.class, 50,
                Buffalo.class, 20,
                Duck.class, 10
        ));
        chanceMap.put(Eagle.class, Map.of(
                Fox.class, 10,
                Rabbit.class, 90,
                Mouse.class, 90,
                Duck.class, 80
        ));
        chanceMap.put(Horse.class, Map.of(
                Plant.class, 100
        ));
        chanceMap.put(Deer.class, Map.of(
                Plant.class, 100
        ));
        chanceMap.put(Rabbit.class, Map.of(
                Plant.class, 100
        ));
        chanceMap.put(Mouse.class, Map.of(
                Caterpillar.class, 90,
                Plant.class, 100
        ));
        chanceMap.put(Goat.class, Map.of(
                Plant.class, 100
        ));
        chanceMap.put(Sheep.class, Map.of(
                Plant.class, 100
        ));
        chanceMap.put(Boar.class, Map.of(
                Mouse.class, 50,
                Caterpillar.class, 90,
                Plant.class, 100
        ));
        chanceMap.put(Buffalo.class, Map.of(
                Plant.class, 100
        ));
        chanceMap.put(Duck.class, Map.of(
                Caterpillar.class, 90,
                Plant.class, 100
        ));
        chanceMap.put(Caterpillar.class, Map.of(
                Plant.class, 100
        ));
    }

}
