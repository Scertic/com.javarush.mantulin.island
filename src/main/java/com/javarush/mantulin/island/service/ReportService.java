package com.javarush.mantulin.island.service;

import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.entity.report.Report;

/**
 * Класс для вывода отчетов.
 */
public class ReportService implements Runnable{
    private final Report report;
    private static int day = 1;
    public ReportService(Island island) {
        report = new Report(island);
    }

    @Override
    public void run() {
        report.reportLocck.lock();
        try {
            System.out.println("День симуляции: " + day++);
            System.out.println(report.getReport());
        } finally {
            report.reportLocck.unlock();
        }

    }
}
