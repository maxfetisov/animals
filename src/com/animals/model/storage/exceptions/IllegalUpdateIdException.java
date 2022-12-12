package com.animals.model.storage.exceptions;

public class IllegalUpdateIdException extends StorageException{
    private static String defaultMessage = "Нет животного с id=";
    private static String defaultFullMessage = "Нет животного с таким id";

    public IllegalUpdateIdException() {
        super(defaultFullMessage);
    }

    public IllegalUpdateIdException(String s) {
        super(s);
    }

    public IllegalUpdateIdException(int id) {
        super(defaultMessage+id);
    }
}
