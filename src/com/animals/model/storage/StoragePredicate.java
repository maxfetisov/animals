package com.animals.model.storage;

import com.animals.model.entities.Animal;
import com.animals.model.entities.Food;

import java.util.function.Predicate;

public class StoragePredicate {
    public static final Predicate<Food> ALL_PREDICATE = i -> true;
    public static final Predicate<Food> ALIVE_PREDICATE = i -> i instanceof Animal && ((Animal)i).isAlive();
    public static final Predicate<Food> DEAD_PREDICATE = i -> i instanceof Animal && !((Animal)i).isAlive();
    public static Predicate<Food> getMaxWeightPredicate(float weight){
        return i -> i.getWeight() <= weight;
    }
    public static Predicate<Food> getMinWeightPredicate(float weight){
        return i -> i.getWeight() >= weight;
    }
    public static Predicate<Food> getClassPredicate(Class<? extends Food> cl){
        return i -> cl.isInstance(i);
    }
    public static Predicate<Food> getNamePredicate(String name){
        return i -> i.getName().equals(name);
    }
    public static Predicate<Food> getIdPredicate(int id){
        return i -> i.getId() == id;
    }
}
