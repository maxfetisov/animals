package com.animals.view.consoleUI.exceptions;

public class IllegalStorageInitModeValueException extends ConsoleUIException{
    private static String defaultFullMessage = "Значение не соответствует ни одному режиму";

    public IllegalStorageInitModeValueException() {
        super(defaultFullMessage);
    }

    public IllegalStorageInitModeValueException(String s) {
        super(s);
    }

}

