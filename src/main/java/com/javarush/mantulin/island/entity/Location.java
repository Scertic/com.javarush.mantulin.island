package com.javarush.mantulin.island.entity;

import com.javarush.mantulin.island.Settings;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.Animal;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.Horse;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.Mouse;
import com.javarush.mantulin.island.entity.creature.animal.herbivore.Rabbit;
import com.javarush.mantulin.island.entity.creature.animal.predator.Eagle;
import com.javarush.mantulin.island.entity.creature.animal.predator.Fox;
import com.javarush.mantulin.island.entity.creature.animal.predator.Wolf;
import com.javarush.mantulin.island.entity.creature.plant.Plant;
import com.javarush.mantulin.island.util.AnimalFactory;

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
        if (creaturesOnLocation.contains(creature)) {
            return true;
        }
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

    public Location() {
        AnimalFactory factory = new AnimalFactory();
        List<Creature> creatureList = new ArrayList<>(15);
        for (int i = 0; i < 10; i++) {
            creatureList.add(factory.createHerbivore());
        }
        for (int i = 0; i < 5; i++) {
            creatureList.add(factory.createPredator());
        }
        Collections.shuffle(creatureList);
        creaturesOnLocation.addAll(creatureList);
    }

    // ЛОКАЦИЮ ТОЖЕ НУЖНО ПРАВИЛЬНО СОЗДАТЬ -
    // ИНИЦИАЛИЗИРОВАВ ЕЕ НА СТАРТЕ КАКИМ-ТО КОЛ-ВОМ ЖИВОТНЫХ И РАСТЕНИЙ

    public static void main(String[] args) {
        Location location = new Location();
//        Wolf c1 = new Wolf();
//        Animal c2 = new Fox();
//        Plant c3 = new Plant();
//        Animal horse = new Horse();
//        System.out.println(location.addCreature(c1));
//        System.out.println(location.addCreature(c2));
//        System.out.println(location.addCreature(c3));
//        System.out.println(location.addCreature(horse));
//        System.out.println(location.addCreature(horse));
//        location.addCreature(new Wolf());
//        location.addCreature(new Horse());
//        location.addCreature(new Horse());
//        location.addCreature(new Mouse());
//        location.addCreature(new Mouse());
//        location.addCreature(new Mouse());
//        location.addCreature(new Eagle());
//        location.addCreature(new Rabbit());
//        location.addCreature(new Rabbit());
        for (int i = 0; i < 10000; i++) {
//            System.out.println(location.creaturesOnLocation);
            Map<String, Long> collect = location.creaturesOnLocation.stream().collect(Collectors.groupingBy(x -> Settings.icoMap.get(x.getClass()), Collectors.counting()));
            System.out.println(collect);
            for (Creature creature : location.creaturesOnLocation) {
                if (creature instanceof Animal animal) {
                    //еда
                    Creature creatureToEat = location.findCreatureToEat(animal);
                    if(animal.eat(creatureToEat) != null) {
                        location.removeCreature(creatureToEat);
                    }
                    //размножение
                    if (location.creaturesOnLocation.stream().filter(x -> x.getClass() == animal.getClass()).count() > 1) {
                        Creature reproduce = animal.reproduce();
                        if (reproduce != null) {
                            location.addCreature(reproduce);
                        }

                    }
                    //проверяем на смерть
                    if (!animal.isAlive) {
                        location.removeCreature(animal);
                    }
                }
                location.addCreature(new Plant());
            }
            if (collect.keySet().size() == 1)
                break;
        }
        Map<String, Long> collect = location.creaturesOnLocation.stream().collect(Collectors.groupingBy(x -> Settings.icoMap.get(x.getClass()), Collectors.counting()));
        System.out.println(collect);
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

    /**
     * Поиск создания для еды другого создания.
     * @param creature - создание для которого осуществляется поиск
     * @return - создание, которое может съесть создание из входного параметра
     */
    public Creature findCreatureToEat(Creature creature) {
        Set<Creature> creaturesNearby = getCreaturesOnLocation();
        Map<Class<? extends Creature>, Integer> classIntegerMap = Settings.chanceMap.get(creature.getClass());
        if (classIntegerMap == null) {
            return null;
        }
        List<Class<? extends Creature>> myFavoriteCreature = new ArrayList<>(classIntegerMap.keySet());

        if (myFavoriteCreature.isEmpty()) {
            return null;
        }
        Collections.shuffle(myFavoriteCreature);
        for (Creature creature1 : creaturesNearby) {
            if(myFavoriteCreature.contains(creature1.getClass())) {
                return creature1;
            }
        }
        return null;
    }

}
