package com.javarush.mantulin.island.entity.creature.animal;

import com.javarush.mantulin.island.entity.creature.Creature;

/**
 * Абстрактный класс для описания поведения и состояния животного по умолчанию.
 */
public abstract class Animal extends Creature {

    // ОБЩИЕ ХАРАКТЕРИСТИКИ
    // СЫТОСТЬ satiety = ? вес
    // ВЕС ЖИВОТНОГО
    // СКОРОСТЬ ПЕРЕМЕЩЕНИЯ

    void eat(Creature c) {

        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
        // КТО ИМЕННО ЭТОТ Creature БУДЕТ ВЛИЯТЬ НА ФОРМАТ ПОЕДАНИЯ
        // КОГДА СТАНЕТ ПОНЯТНО КТО КОНКРЕТНО ЭТО Creature
        // МЫ МОЖЕМ ОПРЕДЕЛИТЬ ВЕРОЯТНОСТЬ ЕГО ПОЕДАНИЯ И РЕАЛИЗОВАТЬ ЭТУ ЛОГИКУ
    }

    void move() {
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
    }

    Creature reproduce() {
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
        return null;
    }

    void die() {
        // ДЕФОЛТНАЯ РЕАЛИЗАЦИЯ
    }

    void decreaseWeight(){
    }
}
