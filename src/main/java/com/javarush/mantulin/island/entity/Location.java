package com.javarush.mantulin.island.entity;

import com.javarush.mantulin.island.Settings;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.Animal;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.Horse;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.Mouse;
import com.javarush.mantulin.island.entity.creature.animal.predator.Fox;
import com.javarush.mantulin.island.entity.creature.animal.predator.Wolf;
import com.javarush.mantulin.island.entity.creature.plant.Plant;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Location {

    // ЛОКАЦИЯ ДОЛЖНА ЗНАТЬ ТЕКУЩЕЕ КОЛ-ВО ЖИВОТНЫХ КОНКРЕТНОГО ВИДА
    // НА СЕБЕ
    // МАССИВ?

    //Карта соотвествий созданий и их количества на локации
//    private final Map<Creature, Integer> creaturesToCount = new HashMap<>();

    //Список всех созданий в локации
    private final List<Creature> creaturesOnLocation = new CopyOnWriteArrayList<>();

    /**
     * Метод для добавления создания в карту соотвествий созданий и их количества.
     * @param creature - существо для добавления
     * @return true - при успешном добавлении создания в карту, false - если количество созданий превышает допустимую норму.
     */
    public boolean addCreature(Creature creature) {
//        if (!creaturesOnLocation.contains(creature)) {
//            creaturesOnLocation.add(creature);
//            return true;
//        }
        long count = creaturesOnLocation.stream().filter(x -> x.getClass() == creature.getClass()).count();
        if (Double.compare(count, getMaxNumberOfCreature(creature)) < 0) {
            creaturesOnLocation.add(creature);
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
    public Double getMaxNumberOfCreature(Creature creature) {
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
        System.out.println(location.addCreature(c1));
        System.out.println(location.addCreature(c2));
        System.out.println(location.addCreature(c3));
        System.out.println(location.addCreature(horse));
        location.addCreature(new Wolf(location));
        location.addCreature(new Horse(location));
        location.addCreature(new Horse(location));
        location.addCreature(new Mouse(location));
        location.addCreature(new Mouse(location));
        location.addCreature(new Mouse(location));
        for (int i = 0; i < 10; i++) {
            System.out.println(location.creaturesOnLocation);
            for (Creature creature : location.creaturesOnLocation) {
                if (creature instanceof Animal animal) {
                    Creature creatureToEat = animal.findCreatureToEat();
                    animal.eat(creatureToEat);
                    Creature reproduce = animal.reproduce();
                    if (reproduce != null) {
                        location.addCreature(reproduce);
                    }
                }
                location.addCreature(new Plant(location));
            }

        }
        System.out.println(location.creaturesOnLocation);
        Map<String, Long> collect = location.creaturesOnLocation.stream().collect(Collectors.groupingBy(x -> x.getClass().getSimpleName(), Collectors.counting()));
        System.out.println(collect);
        System.out.println(location.creaturesOnLocation.stream().filter(x->x.getClass() == Wolf.class).count());
        System.out.println(location.creaturesOnLocation.stream().filter(x->x.getClass() == Mouse.class).count());
    }

    /**
     * Возвращает множество существ на локации
     * @return - множество существ на локации
     */
    public Set<Creature> getCreaturesOnLocation() {
        return new HashSet<>(creaturesOnLocation);
    }

    /**
     * Метод удаления существа с локации.
     * @param creature удаляемое существо
     * @return - истина если удаление произошло успешно, и ложь, если нет.
     */
    public boolean removeCreature(Creature creature) {
        if (creaturesOnLocation.contains(creature)) {
            creaturesOnLocation.remove(creature);
            return true;
        }
        return false;
    }

}
