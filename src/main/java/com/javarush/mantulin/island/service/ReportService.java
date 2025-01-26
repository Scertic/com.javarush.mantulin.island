package com.javarush.mantulin.island.service;

import com.javarush.mantulin.island.entity.report.Report;

/**
 * Класс для вывода отчетов.
 */
public class ReportService implements Runnable{
    private final Report report;
    private int day = 1;
    public ReportService(Report report) {
        this.report = report;
    }

    @Override
    public void run() {
        report.reentrantLock.lock();
        try {
            System.out.println("\rДень симуляции: " + day++);
            System.out.println("\r" + report.getReport());
        } finally {
            report.reentrantLock.unlock();
        }

    }
}
