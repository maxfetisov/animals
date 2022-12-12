package com.animals.view.consoleUI.exceptions;

public class IllegalQueryStringException extends ConsoleUIException{
    private static String defaultFullMessage = "Такой команды не существует";

    public IllegalQueryStringException() {
        super(defaultFullMessage);
    }

    public IllegalQueryStringException(String s) {
        super(s);
    }
}
