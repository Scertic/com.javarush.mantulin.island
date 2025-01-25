package com.javarush.mantulin.island.entity.creature.animal.herbivore;

import com.javarush.mantulin.island.entity.creature.Creature;

public class Caterpillar extends Herbivore{
    @Override
    protected void increaseSatiety(Creature creature) {
        //не ест - расти сытости неоткуда
    }

    @Override
    protected void decreaseSatiety() {
        //не ест - не умирает от голода
    }
}
