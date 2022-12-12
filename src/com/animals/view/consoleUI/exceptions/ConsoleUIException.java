package com.animals.view.consoleUI.exceptions;

public abstract class ConsoleUIException extends Exception{
    public ConsoleUIException() {
        super();
    }

    public ConsoleUIException(String s){
        super(s);
    }
}
