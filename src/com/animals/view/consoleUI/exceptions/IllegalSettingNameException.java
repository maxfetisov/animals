package com.animals.view.consoleUI.exceptions;

public class IllegalSettingNameException extends ConsoleUIException{
    private static String defaultFullMessage = "Параметр не существует";

    public IllegalSettingNameException() {
        super(defaultFullMessage);
    }

    public IllegalSettingNameException(String s) {
        super(s);
    }
}
