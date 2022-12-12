package com.animals.model.storage.exceptions;

public class IllegalClassVersionException extends StorageException{
    private static String defaultFullMessage = "Не соответствуют версии классов";
    public IllegalClassVersionException() {
        super(defaultFullMessage);
    }

    public IllegalClassVersionException(String s) {
        super(s);
    }
}
