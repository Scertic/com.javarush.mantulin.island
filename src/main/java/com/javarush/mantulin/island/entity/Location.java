package com.javarush.mantulin.island.entity;

import com.javarush.mantulin.island.configuration.Settings;
import com.javarush.mantulin.island.entity.creature.Creature;
import com.javarush.mantulin.island.entity.creature.animal.Animal;
import com.javarush.mantulin.island.entity.creature.plant.Plant;
import com.javarush.mantulin.island.util.CreatureFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Location implements Runnable{

    //Список всех созданий в локации
    private final List<Creature> creaturesOnLocation = new CopyOnWriteArrayList<>();
    private final ReentrantLock lock;
    private static int locationID;
    private String name;

    /**
     * Метод для добавления создания в карту соотвествий созданий и их количества.
     * @param creature - существо для добавления
     * @return true - при успешном добавлении создания в карту, false - если количество созданий превышает допустимую норму.
     */
    public boolean addCreature(Creature creature) {
        lock.lock();
        try {
            if (creaturesOnLocation.contains(creature)) {
                return true;
            }
            long count = creaturesOnLocation.stream().filter(x -> x.getClass() == creature.getClass()).count();
            if (Double.compare(count, getMaxNumberOfCreature(creature)) < 0) {
                creaturesOnLocation.add(creature);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Метод поиска максимального количества допустимых созданий в локации.
     * @param creature - существо для поиска
     * @return - максимальное количество созданий данного вида в локации.
     * Возвращает 0, если такого создания не нашлось в настройках.
     */
    public int getMaxNumberOfCreature(Creature creature) {
        int maxCountOnLocation = Settings.getInstance().getCreatureSettings().get(creature.getClass()).get("maxCountOnLocation").intValue();
        return maxCountOnLocation;
    }

    public Location() {
        CreatureFactory factory = new CreatureFactory();
        List<Creature> creatureList = new ArrayList<>(3000);
        for (Class<? extends Creature> aClass : Settings.getInstance().getCreatureSettings().keySet()) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int count = Settings.getInstance().getCreatureSettings().get(aClass).get("maxCountOnLocation").intValue();
            int maxRandom = random.nextInt(count)+1;
            for (int i = 0; i < maxRandom; i++) {
                creatureList.add(factory.getCreature(aClass));
            }
        }
        Collections.shuffle(creatureList);
        creaturesOnLocation.addAll(creatureList);
        lock = new ReentrantLock();
        locationID++;
        name = "Location-"+locationID;
    }

    // ЛОКАЦИЮ ТОЖЕ НУЖНО ПРАВИЛЬНО СОЗДАТЬ -
    // ИНИЦИАЛИЗИРОВАВ ЕЕ НА СТАРТЕ КАКИМ-ТО КОЛ-ВОМ ЖИВОТНЫХ И РАСТЕНИЙ

    private void simulateLifeCycle() {

        for (Creature creature : creaturesOnLocation) {
            if (creature instanceof Animal animal) {
                //проверяем на смерть
                if (!animal.isAlive) {
                    removeCreature(animal);
                    continue;
                }
                //еда
                Creature creatureToEat = findCreatureToEat(animal);
                if(animal.eat(creatureToEat) != null) {
                    removeCreature(creatureToEat);
                }
                //размножение
                if (creaturesOnLocation.stream().filter(x -> x.getClass() == animal.getClass()).count() > 1) {
                    Creature reproduce = animal.reproduce();
                    if (reproduce != null) {
                        addCreature(reproduce);
                    }

                }
            }
            //Спавним растение
           addCreature(new Plant());
        }
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
        lock.lock();
        try {
            if (creaturesOnLocation.contains(creature)) {
                creaturesOnLocation.remove(creature);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Поиск создания для еды другого создания.
     * @param creature - создание для которого осуществляется поиск
     * @return - создание, которое может съесть создание из входного параметра
     */
    public Creature findCreatureToEat(Creature creature) {
        Set<Creature> creaturesNearby = getCreaturesOnLocation();
        Map<Class<? extends Creature>, Integer> classIntegerMap = Settings.getInstance().getChanceMap().get(creature.getClass());
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

    public ReentrantLock getLock() {
        return lock;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        //TODO Управление днем отдать острову
//        System.out.println(getName() + " Day-" + 0);
//        System.out.println(getCreatureGroupBy());
//        for (int i = 0; i < Settings.getInstance().getSimCount(); i++) {
//            System.out.println(getName() + " Day-" + (i+1));
            simulateLifeCycle();
//            System.out.println(getCreatureGroupBy());
//            if (getCreatureGroupBy().keySet().size() == 1)
//                break;
//        }
    }

    public Map<String, Long> getCreatureGroupBy() {
        return creaturesOnLocation.stream().collect(Collectors.groupingBy(Creature::toString, Collectors.counting()));
    }
}
