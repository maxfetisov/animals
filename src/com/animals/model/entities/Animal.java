package com.animals.model.entities;

import com.animals.model.entities.exceptions.*;

public abstract class Animal extends Food{
    protected boolean alive;
    public Animal() {
        alive = true;
    }

    public Animal(int id, String name, float weight) throws IllegalFoodWeightException {
        super(id, name, weight);
        alive = true;
    }
    public Animal(Animal animal) throws IllegalFoodWeightException {
        this(animal.id, animal.name, animal.weight);
    }
    public void die() throws AnimalDeadException {
        if(!alive)
            throw new AnimalDeadException(this);
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public abstract void eat(Food food) throws AnimalDeadException, IllegalFoodException, DeadFoodException, EatHimselfException;

    public Animal update(Food food){
        super.update(food);
        if(food instanceof Animal)
            alive = ((Animal)food).alive;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Animal animal = (Animal) o;
        return alive == animal.alive;
    }

    @Override
    public String toString() {
        return "id="+id+"\nname="+name+"\nweight="+weight+"\nalive="+alive+"\n\n";
    }
}
