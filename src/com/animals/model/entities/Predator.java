package com.animals.model.entities;

import com.animals.model.entities.exceptions.*;

import java.util.Date;
import java.util.Random;

public class Predator extends Animal {
    private static final long serialVersionUID = 1;

    public Predator() {}
    public Predator(int id, String name, float weight) throws IllegalFoodWeightException {
        super(id, name, weight);
    }
    public Predator(Predator predator) throws IllegalFoodWeightException {super(predator);}

    @Override
    public void eat(Food food) throws AnimalDeadException, IllegalFoodException, DeadFoodException, EatHimselfException {
        if(!this.alive)
            throw new AnimalDeadException(this);
        if(!(food instanceof Animal))
            throw new IllegalFoodException(this, food);
        Animal animal = (Animal)food;
        if(!animal.alive)
            throw new DeadFoodException(this);
        if(this == animal)
            throw new EatHimselfException(this);
        if(hunt(animal)) {
            animal.die();
            this.weight += animal.weight;
            animal.weight = 0;
        }
    }
    private boolean hunt(Animal animal){
        Random rand = new Random(new Date().getTime());
        if(animal instanceof Herbivore){
            return rand.nextBoolean();
        }
        else{
            return rand.nextDouble() < weight/animal.weight;
        }
    }
}
