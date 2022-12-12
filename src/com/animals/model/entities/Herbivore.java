package com.animals.model.entities;

import com.animals.model.entities.exceptions.AnimalDeadException;
import com.animals.model.entities.exceptions.IllegalFoodException;
import com.animals.model.entities.exceptions.IllegalFoodWeightException;

public class Herbivore extends Animal{
    private static final long serialVersionUID = 1;
    public Herbivore() {
    }

    public Herbivore(int id, String name, float weight) throws IllegalFoodWeightException {
        super(id, name, weight);
    }
    public Herbivore(Herbivore herbivore) throws IllegalFoodWeightException {super(herbivore);}

    @Override
    public void eat(Food food) throws AnimalDeadException, IllegalFoodException {
        if(!this.alive)
            throw new AnimalDeadException(this);
        if(!(food instanceof Grass))
            throw new IllegalFoodException(this, food);
        Grass grass = (Grass)food;
        this.weight += grass.weight;
        grass.weight = 0;
    }

}
