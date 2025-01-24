package com.javarush.mantulin.island;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.entity.Location;
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

        ExecutorService executorService = Executors.newFixedThreadPool(12);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        PlantService plantService = new PlantService(island);
        scheduledExecutorService.scheduleAtFixedRate(plantService,0, 500, TimeUnit.MILLISECONDS);// schedule(plantService, 1, TimeUnit.MILLISECONDS);
        ReportService reportService = new ReportService(island);
        for (int i = 0; i < Settings.getInstance().getSimCount(); i++) {
            island.getLocations().forEach(executorService::submit);
            executorService.submit(reportService);
        }

//        new Thread(new ReportService(island)).start();

        executorService.shutdown();
        if (executorService.isShutdown())
            scheduledExecutorService.shutdown();


    }

}
