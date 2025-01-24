package com.javarush.mantulin.island.entity.creature.animal.herbivore;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.Animal;
import com.javarush.mantulin.island.entity.creature.plant.Plant;

import java.util.Random;

public class Duck extends Herbivore{
    @Override
    public Creature eat(Creature creature) {
        decreaseSatiety();
        if (satiety < 0) {
            this.die();
            return null;
        }
        if (satiety == 100) {
            return null;
        }
        if (creature == null) {
            //Поменять характеристики
            decreaseSatiety();
            return null;
        }
        if (creature.getClass() == Plant.class) {
            increaseSatiety(creature);
            if (Double.compare(this.forFullSatiety, Settings.getInstance().getCreatureSettings().get(Plant.class).get("weight").doubleValue()) > 0) {
                return creature;
            } else {
                ((Plant) creature).looseWeight(forFullSatiety);
                return null;
            }
        }
        if (creature instanceof Animal) {
            if (!((Animal) creature).isAlive) {
                return null;
            }
            //Найти вероятность того, что текущее существо может съесть входящее
            Integer chance = getChanceToEat(creature);
            Random random = new Random();
            if (random.nextInt(100) + 1 <= chance) {
                increaseSatiety(creature);
                return creature;
            }
        }
        decreaseSatiety();
        return null;
    }
}
