package com.javarush.mantulin.island;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.entity.Location;
import com.javarush.mantulin.island.service.LocationAnimalService;
import com.javarush.mantulin.island.service.PlantService;
import com.javarush.mantulin.island.service.ReportService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Application {
    public static void main(String[] args) {
        // ТОЧКА СБОРКИ И СТАРТА МОЕГО ПРИЛОЖЕНИЯ
        long l = System.currentTimeMillis();
        Island island = new Island(Settings.getInstance().getRowsCount(), Settings.getInstance().getColumnsCount());
        long l1 = System.currentTimeMillis();
        System.out.println("Время создания острова: " + (l1 - l));


        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        PlantService plantService = new PlantService(island);
        scheduledExecutorService.scheduleAtFixedRate(plantService,0, 100, TimeUnit.MILLISECONDS);

        ReportService reportService = new ReportService(island);
        ExecutorService executorService = Executors.newFixedThreadPool(12);
        for (int i = 0; i < Settings.getInstance().getSimCount(); i++) {
            island.getLocations().forEach(x -> executorService.submit(new LocationAnimalService(island, x)));

            executorService.submit(reportService);
        }

        executorService.shutdown();
        if (executorService.isShutdown())
            scheduledExecutorService.shutdown();


    }

}
