package com.javarush.mantulin.island;

import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.entity.Location;

import javax.sound.sampled.FloatControl;

public class Application {
    public static void main(String[] args) {
        // ТОЧКА СБОРКИ И СТАРТА МОЕГО ПРИЛОЖЕНИЯ

        Island island = new Island(Settings.rowsCount, Settings.columnsCount);
        long l = System.currentTimeMillis();
        for (int i = 0; i < island.locations.length; i++) {
            for (int j = 0; j <island.locations[i].length; j++) {
                island.locations[i][j] = new Location();
                //new Thread(island.locations[i][j]).start();
            }
        }
        long l1 = System.currentTimeMillis();
        System.out.println(l1-l);

    }

}
