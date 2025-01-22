package com.javarush.mantulin.island;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.entity.Location;

public class Application {
    public static void main(String[] args) {
        // ТОЧКА СБОРКИ И СТАРТА МОЕГО ПРИЛОЖЕНИЯ

        Island island = new Island(Settings.getInstance().getRowsCount(), Settings.getInstance().getColumnsCount());
        long l = System.currentTimeMillis();
        for (int i = 0; i < island.locations.length; i++) {
            for (int j = 0; j <island.locations[i].length; j++) {
                island.locations[i][j] = new Location();
//                System.out.println(island.locations[i][j].getCreatureGroupBy());
                new Thread(island.locations[i][j]).start();
            }
        }
        long l1 = System.currentTimeMillis();
        System.out.println(l1-l);

    }

}
