package com.javarush.mantulin.island;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.service.LocationAnimalService;
import com.javarush.mantulin.island.service.PlantService;
import com.javarush.mantulin.island.service.ReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Application {
    public static void main(String[] args) {
        // ТОЧКА СБОРКИ И СТАРТА МОЕГО ПРИЛОЖЕНИЯ
        long l = System.currentTimeMillis();
        Island island = new Island(Settings.getInstance().getRowsCount(), Settings.getInstance().getColumnsCount());
        long l1 = System.currentTimeMillis();
        System.out.println("Время создания острова: " + (l1 - l));
        l = System.currentTimeMillis();


        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        PlantService plantService = new PlantService(island);
        scheduledExecutorService.scheduleWithFixedDelay(plantService, 0, 1, TimeUnit.SECONDS);

        ReportService reportService = new ReportService(island);
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (int i = 0; i < Settings.getInstance().getSimCount(); i++) {
            List<Future<?>> futureList = new ArrayList<>();
            island.getLocations().forEach(x -> futureList.add(executorService.submit(new LocationAnimalService(island, x))));
            if (!futureList.isEmpty()) {
                int dotCount = 3;
                while (!futureList.get(futureList.size()-1).isDone()) {
                    try {
                        System.out.print("\rwaiting" + ".".repeat(dotCount));
                        if (dotCount++ == 3) {
                            dotCount = 0;
                        }
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                executorService.submit(reportService);
            }
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        scheduledExecutorService.shutdown();

        l1 = System.currentTimeMillis();
        System.out.println("Время симуляции: " + (l1 - l)/1000 + " секунд.");
    }

}
