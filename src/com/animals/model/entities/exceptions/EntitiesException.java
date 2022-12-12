package com.animals.model.entities.exceptions;

public abstract class EntitiesException extends Exception{
    public EntitiesException() {
        super();
    }

    public EntitiesException(String s){
        super(s);
    }
}
