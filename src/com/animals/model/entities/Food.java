package com.animals.model.entities;

import com.animals.model.entities.exceptions.IllegalFoodWeightException;

import java.io.Serializable;
import java.util.Objects;

public abstract class Food implements Serializable {
    protected final int id;
    protected String name;
    protected float weight;

    public Food() {
        id = 0;
    }

    public Food(int id, String name, float weight) throws IllegalFoodWeightException {
        if(weight <= 0)
            throw new IllegalFoodWeightException(name, weight);
        this.id = id;
        this.name = name;
        this.weight = weight;
    }
    public Food(Food food) throws IllegalFoodWeightException {
        this(food.id, food.name, food.weight);
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Food update(Food food){
        name = food.name;
        weight = food.weight;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return id == food.id && Float.compare(food.weight, weight) == 0 && Objects.equals(name, food.name);
    }

    @Override
    public String toString() {
        return "id="+id+"\nname="+name+"\nweight="+weight+"\n\n";
    }
}
