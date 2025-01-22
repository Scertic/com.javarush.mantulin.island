package com.javarush.mantulin.island;

import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.predator.*;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.*;
import com.javarush.mantulin.island.entity.creature.plant.Plant;

import java.util.HashMap;
import java.util.Map;

public class Settings {
    private static Settings settings;
    private final int columnsCount = 1;
    private final int rowsCount = 1;

    /**
     * Карта соответсвий классов животных и их характеристикам:
     * weight - Вес одного животного, кг
     * maxCountOnLocation - Максимальное количество животных этого вида на одной локации
     * maxSteps - Скорость перемещения, не более чем локаций за ход
     * foodWeightForFullSatiety - Сколько киллограммов пищи нужно животному до полного насыщения
     */
    private final Map<Class<? extends Creature>, Map<String, Number>> creatureSettings = new HashMap<>();
    private final Map<Class<? extends  Creature>, String> icoMap = new HashMap<>();

    //Количество симуляций
    private final int simCount = 1;

    {
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
        icoMap.put(Creature.class, "?");
    }

    {
        creatureSettings.put(Wolf.class, Map.of("weight", 50, "maxCountOnLocation", 30, "maxSteps", 3, "foodWeightForFullSatiety", 8.5));
        creatureSettings.put(Boa.class, Map.of("weight", 15, "maxCountOnLocation", 30, "maxSteps", 1, "foodWeightForFullSatiety", 3));
        creatureSettings.put(Fox.class, Map.of("weight", 8, "maxCountOnLocation", 30, "maxSteps", 2, "foodWeightForFullSatiety", 2));
        creatureSettings.put(Bear.class, Map.of("weight", 500, "maxCountOnLocation", 5, "maxSteps", 2, "foodWeightForFullSatiety", 80));
        creatureSettings.put(Eagle.class, Map.of("weight", 6, "maxCountOnLocation", 20, "maxSteps", 3, "foodWeightForFullSatiety", 1));
        creatureSettings.put(Horse.class, Map.of("weight", 400, "maxCountOnLocation", 20, "maxSteps", 4, "foodWeightForFullSatiety", 60));
        creatureSettings.put(Deer.class, Map.of("weight", 300, "maxCountOnLocation", 20, "maxSteps", 4, "foodWeightForFullSatiety", 50));
        creatureSettings.put(Rabbit.class, Map.of("weight", 2, "maxCountOnLocation", 150, "maxSteps", 2, "foodWeightForFullSatiety", 0.45));
        creatureSettings.put(Mouse.class, Map.of("weight", 0.05, "maxCountOnLocation", 500, "maxSteps", 1, "foodWeightForFullSatiety", 0.1));
        creatureSettings.put(Goat.class, Map.of("weight", 60, "maxCountOnLocation", 140, "maxSteps", 3, "foodWeightForFullSatiety", 10));
        creatureSettings.put(Sheep.class, Map.of("weight", 70, "maxCountOnLocation", 140, "maxSteps", 3, "foodWeightForFullSatiety", 15));
        creatureSettings.put(Boar.class, Map.of("weight", 400, "maxCountOnLocation", 50, "maxSteps", 2, "foodWeightForFullSatiety", 50));
        creatureSettings.put(Buffalo.class, Map.of("weight", 700, "maxCountOnLocation", 10, "maxSteps", 3, "foodWeightForFullSatiety", 100));
        creatureSettings.put(Duck.class, Map.of("weight", 1, "maxCountOnLocation", 200, "maxSteps", 4, "foodWeightForFullSatiety", 0.15));
        creatureSettings.put(Caterpillar.class, Map.of("weight", 0.01, "maxCountOnLocation", 1000, "maxSteps", 0, "foodWeightForFullSatiety", 0));
        creatureSettings.put(Plant.class, Map.of("weight", 1, "maxCountOnLocation", 200, "maxSteps", 0, "foodWeightForFullSatiety", 0));
    }

    private final Map<Class<? extends Creature>, Map<Class<? extends Creature>, Integer>> chanceMap = new HashMap<>();

    {
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
    private Settings() {
    }

    public static Settings getInstance() {
        if (settings != null) {
            return settings;
        }
        return new Settings();
    }

    public int getColumnsCount() {
        return columnsCount;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public Map<Class<? extends Creature>, Map<String, Number>> getCreatureSettings() {
        return new HashMap<>(creatureSettings);
    }

    public Map<Class<? extends Creature>, String> getIcoMap() {
        return new HashMap<>(icoMap);
    }

    public Map<Class<? extends Creature>, Map<Class<? extends Creature>, Integer>> getChanceMap() {
        return new HashMap<>(chanceMap);
    }

    public int getSimCount() {
        return simCount;
    }
}
