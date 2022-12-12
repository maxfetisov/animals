package com.animals.model.entities;

import com.animals.model.entities.exceptions.IllegalFoodWeightException;

public class Grass extends Food{
    private static final long serialVersionUID = 1;
    public Grass() {
    }

    public Grass(int id, String name, float weight) throws IllegalFoodWeightException {
        super(id, name, weight);
    }
    public Grass(Grass grass) throws IllegalFoodWeightException {super(grass);}
}
