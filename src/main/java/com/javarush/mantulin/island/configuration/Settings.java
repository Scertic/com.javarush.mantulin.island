package com.javarush.mantulin.island.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.*;
import com.javarush.mantulin.island.entity.creature.animal.predator.*;
import com.javarush.mantulin.island.entity.creature.plant.Plant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Settings {

    private Map<String, Class<? extends Creature>> classes = new HashMap<>();
    {
        classes.put("Wolf", Wolf.class);
        classes.put("Boa", Boa.class);
        classes.put("Fox", Fox.class);
        classes.put("Bear", Bear.class);
        classes.put("Eagle", Eagle.class);
        classes.put("Horse", Horse.class);
        classes.put("Deer", Deer.class);
        classes.put("Rabbit", Rabbit.class);
        classes.put("Mouse", Mouse.class);
        classes.put("Goat", Goat.class);
        classes.put("Sheep", Sheep.class);
        classes.put("Boar", Boar.class);
        classes.put("Buffalo", Buffalo.class);
        classes.put("Duck", Duck.class);
        classes.put("Caterpillar", Caterpillar.class);
        classes.put("Plant", Plant.class);
        classes.put("Creature", Creature.class);
    }
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
    @JsonIgnore
    private final Map<Class<? extends Creature>, Map<String, Number>> creatureSettings = new HashMap<>();
    @JsonIgnore
    private final Map<Class<? extends  Creature>, String> icoMap = new HashMap<>();
    private final int simCount = 1; //Количество симуляций
    @JsonIgnore
    private final Map<Class<? extends Creature>, Map<Class<? extends Creature>, Integer>> chanceMap = new HashMap<>();


    private Map<String, Map<String, Number>> creatureSettingsD = new HashMap<>();
    private Map<String, String> icoMapD = new HashMap<>();
    private Map<String, Map<String, Integer>> chanceMapD = new HashMap<>();

    private Settings() {
    }

    public static Settings getInstance() {
        if (settings == null) {
            settings = new Settings();
            settings.loadSettings();
        }
        return settings;
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

    @JsonProperty("creatureSettings")
    private Map<String, Map<String, Number>> getCreatureSettingsD() {
        return new HashMap<>(creatureSettingsD);
    }

    @JsonProperty("icoMap")
    private Map<String, String> getIcoMapD() {
        return new HashMap<>(icoMapD);
    }

    @JsonProperty("chanceMap")
    private Map<String, Map<String, Integer>> getChanceMapD() {
        return new HashMap<>(chanceMapD);
    }



    private void loadSettings() {
        ObjectMapper objectMapper = new ObjectMapper();
        String resourcePath = "settings.json";
        try (InputStream inputStream = Settings.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Файл не найден: " + resourcePath);
            }
            Settings deserializedSettings = objectMapper.readValue(inputStream, Settings.class);
            this.creatureSettingsD.putAll(deserializedSettings.getCreatureSettingsD());
            for (Map.Entry<String, Map<String, Number>> entry : getCreatureSettingsD().entrySet()) {
                this.creatureSettings.put(classes.get(entry.getKey()), entry.getValue());
            }

            this.icoMapD.putAll(deserializedSettings.getIcoMapD());
            for (Map.Entry<String, String> entry : getIcoMapD().entrySet()) {
                this.icoMap.put(classes.get(entry.getKey()), entry.getValue());
            }

            this.chanceMapD.putAll(deserializedSettings.getChanceMapD());
            for (Map.Entry<String, Map<String, Integer>> entry : getChanceMapD().entrySet()) {
                Map<Class<? extends Creature>, Integer> res = new HashMap<>();
                for ( Map.Entry<String, Integer> string: entry.getValue().entrySet()) {
                    res.put(classes.get(string.getKey()), string.getValue());
                }
                chanceMap.put(classes.get(entry.getKey()), res);
            }

            creatureSettingsD.clear();
            creatureSettingsD = null;
            icoMapD.clear();
            icoMapD = null;
            chanceMapD.clear();
            chanceMapD = null;
        } catch (IOException e) {
            //TODO Сделать загрузку настроек по умолчанию
        }
    }

    public static void main(String[] args) {
        System.out.println(Settings.getInstance().getCreatureSettings());
        System.out.println(Settings.getInstance().getIcoMap());
        System.out.println(Settings.getInstance().getChanceMap());
    }
}
