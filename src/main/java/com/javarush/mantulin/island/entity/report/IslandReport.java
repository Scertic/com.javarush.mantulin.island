package com.javarush.mantulin.island.entity.report;

import com.javarush.mantulin.island.entity.Island;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IslandReport extends Report{
    public IslandReport(Island island) {
        super(island);
    }

    @Override
    public List<Map<String, Long>> getReport() {

        Map<String, Long> combinedReport = new HashMap<>();

        // Объединение карт
        for (Map<String, Long> map : super.getReport()) {
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                // Если ключ уже существует в combinedReport, прибавляем значения
                combinedReport.merge(entry.getKey(), entry.getValue(), Long::sum);
            }
        }

        List<Map<String, Long>> report = new ArrayList<>(1);
        report.add(combinedReport);

        return report;
    }
}
