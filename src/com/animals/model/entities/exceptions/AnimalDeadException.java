package com.animals.model.entities.exceptions;

import com.animals.model.entities.Animal;

public class AnimalDeadException extends EntitiesException {
    private static String defaultMessage = " уже мертв";
    private static String defaultFullMessage = "Животное мертво";

    public AnimalDeadException() {
        super(defaultFullMessage);
    }

    public AnimalDeadException(String s) {
        super(s);
    }

    public AnimalDeadException(Animal a) {
        super(a.getName() + defaultMessage);
    }
}
