package com.javarush.mantulin.island.entity.creature.plant;

import com.javarush.mantulin.island.entity.Location;
import com.javarush.mantulin.island.entity.creature.Creature;

public class Plant extends Creature {
    public Plant() {
        super();
    }

    // РОСТ РАСТЕНИЙ
    // void encreaseQuantity(){
    //       ++quantity
    //    }


    @Override
    public String toString() {
        return "\uD83C\uDF3F";
    }
}
