package com.javarush.mantulin.island.entity;

import com.javarush.mantulin.island.Settings;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.Animal;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.Horse;
import com.javarush.mantulin.island.entity.creature.animal.predator.Fox;
import com.javarush.mantulin.island.entity.creature.animal.predator.Wolf;
import com.javarush.mantulin.island.entity.creature.plant.Plant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Location {

    // ЛОКАЦИЯ ДОЛЖНА ЗНАТЬ ТЕКУЩЕЕ КОЛ-ВО ЖИВОТНЫХ КОНКРЕТНОГО ВИДА
    // НА СЕБЕ
    // МАССИВ?
    private final Map<Creature, Integer> creatures = new HashMap<>();

    /**
     * Метод для добавления создания в карту соотвествий созданий и их количества.
     * @param creature - существо для добавления
     * @return true - при успешном добавлении создания в карту, false - если количество созданий превышает допустимую норму.
     */
    public boolean addCreature(Creature creature) {
        if (!creatures.containsKey(creature)) {
            creatures.put(creature, 1);
            return true;
        }
        if (Double.compare(creatures.get(creature), getMaxNumberOfCreature(creature)) < 0) {
            creatures.put(creature, creatures.get(creature) + 1);
            return true;
        }
        return false;
    }

    /**
     * Метод поиска максимального количества допустимых созданий в локации.
     * @param creature - существо для поиска
     * @return - максимальное количество созданий данного вида в локации.
     * Возвращает 0, если такого создания не нашлось в настройках.
     */
    private Double getMaxNumberOfCreature(Creature creature) {
        Double max = Settings.maxNumbersOfCreatures.get(creature.getClass())[1];
        return max != null ? max : 0.0;
    }



    // ЛОКАЦИЮ ТОЖЕ НУЖНО ПРАВИЛЬНО СОЗДАТЬ -
    // ИНИЦИАЛИЗИРОВАВ ЕЕ НА СТАРТЕ КАКИМ-ТО КОЛ-ВОМ ЖИВОТНЫХ И РАСТЕНИЙ

    public static void main(String[] args) {
        Location location = new Location();
        Wolf c1 = new Wolf(location);
        Animal c2 = new Fox(location);
        Plant c3 = new Plant(location);
        Animal horse = new Horse(location);
        c1.eat(c2);
        c2.eat(c3);
        System.out.println(location.addCreature(c1));
        System.out.println(location.addCreature(c2));
        System.out.println(location.addCreature(c3));
        System.out.println(location.addCreature(horse));
        location.addCreature(new Wolf(location));
        for (int i = 0; i < 100; i++) {
            System.out.println(location.creatures);
            for (Creature creature : new HashSet<>(location.creatures.keySet())) {
                if (creature instanceof Animal animal) {
                    Creature creatureToEat = animal.findCreatureToEat();
                    animal.eat(creatureToEat);
                }
            }

        }

    }

    public Set<Creature> getCreatures() {
        return Set.copyOf(creatures.keySet());
    }

    public boolean removeCreature(Creature creature) {
        if (creatures.containsKey(creature)) {
            if (creatures.get(creature) != 1) {
                creatures.put(creature, creatures.get(creature)-1);
            } else {
                creatures.remove(creature);
            }
            return true;
        }
        return false;
    }

}
