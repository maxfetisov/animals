package com.animals.model.entities.exceptions;

import com.animals.model.entities.Animal;

public class DeadFoodException extends EntitiesException{
    private static String defaultMessage = " не ест падаль";
    private static String defaultFullMessage = "Животное не ест падаль";

    public DeadFoodException() {
        super(defaultFullMessage);
    }

    public DeadFoodException(String s) {
        super(s);
    }

    public DeadFoodException(Animal a){
        super(a.getName() + defaultMessage);
    }
}
