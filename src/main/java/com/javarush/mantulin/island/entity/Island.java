package com.javarush.mantulin.island.entity;

public class Island {

    private int columnsCount;
    private int rowsCount;

    public Island(int rowsCount, int columnsCount) {
        this.columnsCount = columnsCount;
        this.rowsCount = rowsCount;
        locations = new Location[rowsCount][columnsCount];
    }

    public Location[][] locations;

}
