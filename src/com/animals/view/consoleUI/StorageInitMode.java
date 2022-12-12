package com.animals.view.consoleUI;

import com.animals.view.consoleUI.exceptions.IllegalStorageInitModeValueException;

public enum StorageInitMode {
    DEFAULT("default"),
    EMPTY("empty"),
    FILE("file");

    private final String value;
    StorageInitMode(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    private static StorageInitMode[] getArray(){
        return new StorageInitMode[] {DEFAULT, EMPTY, FILE};
    }

    public static StorageInitMode getStorageInitMode(String value) throws IllegalStorageInitModeValueException {
        for(StorageInitMode item : getArray()){
            if(item.value.equals(value))
                return item;
        }
        throw new IllegalStorageInitModeValueException();
    }
}
