package com.javarush.mantulin.island.entity.report;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.Island;
import com.javarush.mantulin.island.entity.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Report {
    private final Island island;
    public ReentrantLock reportLocck = new ReentrantLock();

    public Report(Island island) {
        this.island = island;
    }

    public List<Map<String, Long>> getReport() {
        List<Map<String, Long>> report = new ArrayList<>(Settings.getInstance().getColumnsCount()*Settings.getInstance().getRowsCount());
        List<Location> locations = island.getLocations();
        for (Location location : locations) {
            location.getLock().lock();
            try {
                report.add(location.getCreatureGroupBy());
            } finally {
                location.getLock().unlock();
            }
        }

        return report;
    }
}
