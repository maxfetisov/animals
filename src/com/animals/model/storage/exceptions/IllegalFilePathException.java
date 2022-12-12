package com.animals.model.storage.exceptions;

public class IllegalFilePathException extends StorageException{
    private static String defaultFullMessage = "Неправильный путь к файлу";
    public IllegalFilePathException() {
        super(defaultFullMessage);
    }

    public IllegalFilePathException(String s) {
        super(s);
    }
}
