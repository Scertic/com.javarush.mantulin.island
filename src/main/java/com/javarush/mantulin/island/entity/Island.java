package com.javarush.mantulin.island.entity;

import com.javarush.mantulin.island.configuration.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Island {

    private int columnsCount;
    private int rowsCount;
    public Location[][] locations;

    public Island(int rowsCount, int columnsCount) {
        System.out.println("Создаем остров");
        this.columnsCount = columnsCount;
        this.rowsCount = rowsCount;
        locations = new Location[rowsCount][columnsCount];
        initIslandLocations();
        System.out.println("Количество дней симуляции: " + Settings.getInstance().getSimCount());
    }

    private void initIslandLocations() {
        for (int i = 0; i < Settings.getInstance().getRowsCount(); i++) {
            for (int j = 0; j < Settings.getInstance().getColumnsCount(); j++) {
                this.locations[i][j] = new Location();
            }
        }
    }

    public List<Location> getLocations() {
        ArrayList<Location> locationList = new ArrayList<>(locations.length);
        for (int i = 0; i < this.locations.length; i++) {
            locationList.addAll(Arrays.asList(this.locations[i]));
        }
        return locationList;
    }
}
