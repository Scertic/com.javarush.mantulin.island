package com.javarush.mantulin.island.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.mantulin.island.entity.creature.Creature;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
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

    private final Map<Class<? extends Creature>, Map<Class<? extends Creature>, Integer>> chanceMap = new HashMap<>();

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

    private void loadSettings() {
        ObjectMapper objectMapper = new ObjectMapper();
        String resourcePath = "settings.json";
        try (InputStream inputStream = Settings.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Файл не найден: " + resourcePath);
            }
            Settings deserializedSettings = objectMapper.readValue(inputStream, Settings.class);
            this.creatureSettings.putAll(deserializedSettings.getCreatureSettings());
            this.icoMap.putAll(deserializedSettings.getIcoMap());
            this.chanceMap.putAll(deserializedSettings.getChanceMap());
        } catch (IOException e) {
            //TODO Сделать загрузку настроек по умолчанию
        }
    }
}
