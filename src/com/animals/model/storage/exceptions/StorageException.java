package com.animals.model.storage.exceptions;

public abstract class StorageException extends Exception{
    public StorageException() {
        super();
    }

    public StorageException(String s){
        super(s);
    }
}
