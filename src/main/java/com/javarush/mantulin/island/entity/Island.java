package com.javarush.mantulin.island.entity;

import com.javarush.mantulin.island.configuration.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс острова.
 */
public class Island {

    private final int columnsCount;
    private final int rowsCount;
    public Location[][] locations;

    public Island(int rowsCount, int columnsCount) {
        System.out.println("Создаем остров " + rowsCount + "x" + columnsCount);
        this.columnsCount = columnsCount;
        this.rowsCount = rowsCount;
        locations = new Location[rowsCount][columnsCount];
        initIslandLocations();
        System.out.println("Количество дней симуляции: " + Settings.getInstance().getSimCount());
    }

    /**
     *Метод создания локаций в массиве локаций острова.
     */
    private void initIslandLocations() {
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                this.locations[i][j] = new Location();
            }
        }
    }

    /**
     * Метод получения всех локаций.
     * @return - возвращает лист локаций.
     */
    public List<Location> getLocations() {
        ArrayList<Location> locationList = new ArrayList<>(locations.length);
        for (int i = 0; i < this.locations.length; i++) {
            locationList.addAll(Arrays.asList(this.locations[i]));
        }
        return locationList;
    }

    /**
     * Метод получения локации по "координатам" двумерного массива.
     * @param i - номер строки
     * @param j - номер колонки
     * @return - Возвращает локацию, существующую по "координатам" двумерного массива, в противном случае возвращает null.
     */
    public Location getLocation(int i, int j) {
        try {
            return locations[i][j];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Метод получения "координаты" j массива локаций
     * @param location - запрашиваемая локация
     * @return - возвращет j локации, в противном случае возвращает "-1"
     */
    public int getLocationJ(Location location) {
        for (int i = 0; i < Settings.getInstance().getRowsCount(); i++) {
            for (int j = 0; j < Settings.getInstance().getColumnsCount(); j++) {
                if (locations[i][j].equals(location)) {
                    return j;
                }
            }
        }
        return -1;
    }

    /**
     * Метод получения "координаты" i массива локаций
     * @param location - запрашиваемая локация
     * @return - возвращет i локации, в противном случае возвращает "-1"
     */
    public int getLocationI(Location location) {
        for (int i = 0; i < Settings.getInstance().getRowsCount(); i++) {
            for (int j = 0; j < Settings.getInstance().getColumnsCount(); j++) {
                if (locations[i][j].equals(location)) {
                    return i;
                }
            }
        }
        return -1;
    }


}
