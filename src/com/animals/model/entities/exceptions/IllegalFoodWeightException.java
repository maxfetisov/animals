package com.animals.model.entities.exceptions;

public class IllegalFoodWeightException extends EntitiesException{
    private static String defaultMessage = " весит ";
    private static String defaultFullMessage = "Животное мертво";

    public IllegalFoodWeightException() {
        super(defaultFullMessage);
    }

    public IllegalFoodWeightException(String s) {
        super(s);
    }

    public IllegalFoodWeightException(String name, float weight) {
        super(name + defaultMessage + weight);
    }
}
