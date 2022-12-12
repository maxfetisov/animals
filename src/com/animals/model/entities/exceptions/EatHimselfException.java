package com.animals.model.entities.exceptions;

import com.animals.model.entities.Animal;

public class EatHimselfException extends EntitiesException{
    private static String defaultMessage = " не может съесть себя";
    private static String defaultFullMessage = "Животное не ест себя";

    public EatHimselfException() {
        super(defaultFullMessage);
    }

    public EatHimselfException(String s) {
        super(s);
    }

    public EatHimselfException(Animal a){
        super(a.getName() + defaultMessage);
    }
}
