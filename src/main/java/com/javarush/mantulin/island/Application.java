package com.javarush.mantulin.island;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.entity.report.IslandReport;
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

        //Исполнитель для запуска роста растений
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        PlantService plantService = new PlantService(island);
        scheduledExecutorService.scheduleWithFixedDelay(plantService, 0, 1, TimeUnit.SECONDS);

        ReportService reportService = new ReportService(new IslandReport(island));
        //Исполнитель для запуска жизненного цикла и вывода отчета
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (int i = 0; i < Settings.getInstance().getSimCount(); i++) {
            List<Future<?>> futureList = new ArrayList<>();
            //Хапускаем таски и собираем фьючеры для отслеживания оконцания "дня" жизенного цикла
            island.getLocations().forEach(x -> futureList.add(executorService.submit(new LocationAnimalService(island, x))));
            if (!futureList.isEmpty()) {
                //ожидание завершения последнего фьючера
                while (!futureList.get(futureList.size()-1).isDone()) {
                    try {
                        long count = futureList.stream().filter(Future::isDone).count();
                        System.out.print("\rwaiting " + ((count * 100)/island.getLocationCount()) + "%");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                //запуск таски отчета
                executorService.submit(reportService);
            }
        }

        //завершаем всё
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
