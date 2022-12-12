package com.animals.model.entities.exceptions;

import com.animals.model.entities.Animal;
import com.animals.model.entities.Food;

public class IllegalFoodException extends EntitiesException{
    private static String defaultMessage = " не ест ";
    private static String defaultFullMessage = "Животное не ест эту еду";

    public IllegalFoodException() {
        super(defaultFullMessage);
    }

    public IllegalFoodException(String s) {
        super(s);
    }

    public IllegalFoodException(Animal a, Food f){
        super(a.getName() + defaultMessage + f.getName());
    }
}
