package com.animals.view;

import com.animals.model.entities.exceptions.EntitiesException;
import com.animals.model.entities.exceptions.IllegalFoodWeightException;
import com.animals.model.storage.EntitiesStorage;
import com.animals.model.storage.exceptions.IllegalClassVersionException;
import com.animals.model.storage.exceptions.IllegalFilePathException;
import com.animals.model.storage.exceptions.StorageException;
import com.animals.view.consoleUI.StorageInitMode;
import com.animals.view.consoleUI.exceptions.ConsoleUIException;
import com.animals.view.consoleUI.exceptions.IllegalSettingNameException;
import com.animals.view.consoleUI.exceptions.IllegalStorageInitModeValueException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class UIStarter {
    protected EntitiesStorage storage;
    protected Properties properties;
    private final String propertiesFilePath = "config.properties";
    public final void startApp(){
        init();
        startScript();
    }
    protected abstract void startScript();
    private void init(){
        loadProperties();
        try {
            loadStorage();
        }
        catch (StorageException | ConsoleUIException ex){
            System.out.println(ex.getMessage());
        }
    }

    protected void editStorageInitMode(String value) throws IllegalStorageInitModeValueException {
        properties.setProperty("storageInitMode", StorageInitMode.getStorageInitMode(value).getValue());
    }

    protected void editSetting(String name, String value) throws IllegalSettingNameException, IllegalStorageInitModeValueException {
        if(!properties.containsKey(name))
            throw new IllegalSettingNameException();
        switch (name){
            case "storageInitMode" ->editStorageInitMode(value);
            default -> properties.setProperty(name, value);
        }
    }
    protected void loadProperties(){
        try (FileInputStream fis = new FileInputStream(propertiesFilePath)) {
            properties = new Properties();
            properties.load(fis);
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    protected void loadStorage() throws IllegalFilePathException, IllegalClassVersionException, IllegalStorageInitModeValueException {
        switch (StorageInitMode.getStorageInitMode(properties.getProperty("storageInitMode"))){
            case DEFAULT -> {
                storage = EntitiesStorage.getInstance();
                storage.initDefault();
            }
            case FILE -> {
                storage = EntitiesStorage.getInstance();
                storage.initFile(properties.getProperty("defaultFilePath"));
            }
            default -> {
                storage = EntitiesStorage.getInstance();
                storage.initEmpty();
            }
        }
    }
    protected void saveProperties(){
        if(properties != null) {
            try (FileOutputStream fos = new FileOutputStream(propertiesFilePath)) {
                properties.store(fos,"Autosave");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
