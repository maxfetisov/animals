package com.animals.view.consoleUI;

import com.animals.model.entities.Animal;
import com.animals.model.entities.Food;
import com.animals.model.entities.exceptions.*;
import com.animals.model.storage.StoragePredicate;
import com.animals.model.storage.exceptions.IllegalClassVersionException;
import com.animals.model.storage.exceptions.IllegalFilePathException;
import com.animals.model.storage.exceptions.IllegalUpdateIdException;
import com.animals.model.storage.exceptions.StorageException;
import com.animals.view.UIStarter;
import com.animals.view.consoleUI.exceptions.ConsoleUIException;
import com.animals.view.consoleUI.exceptions.IllegalQueryStringException;
import com.animals.view.consoleUI.exceptions.IllegalStorageInitModeValueException;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleScript extends UIStarter {

    protected void startScript(){
        showCommands();
        while (true) {
            System.out.println("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String query = scanner.nextLine();
            String[] qWords = query.trim().split(" ");
            try {
                switch (qWords[0].toLowerCase()) {
                    case "create" -> {
                        if (qWords.length != 4)
                            throw new IllegalQueryStringException();
                        try {
                            System.out.println(create(qWords[1], qWords[2], Float.parseFloat(qWords[3])));
                        }
                        catch (NumberFormatException ex) {
                            throw new IllegalQueryStringException();
                        }
                    }
                    case "find" -> {
                        if (qWords.length == 2) {
                            try {
                                System.out.println(find(Integer.parseInt(qWords[1])));
                            } catch (NumberFormatException ex) {
                                System.out.println(find(qWords[1]));
                            }
                            break;
                        }
                        if (qWords.length == 3) {
                            System.out.println(find(qWords[1], qWords[2]));
                            break;
                        }
                        throw new IllegalQueryStringException();
                    }
                    case "update" ->{
                        if(qWords.length != 4)
                            throw new IllegalQueryStringException();
                        try {
                            update(Integer.parseInt(qWords[1]), qWords[2], Float.parseFloat(qWords[3]));
                        }
                        catch (NumberFormatException ex){
                            throw new IllegalQueryStringException();
                        }
                    }
                    case "delete" -> {
                        if (qWords.length != 2)
                            throw new IllegalQueryStringException();
                        try {
                            delete(Integer.parseInt(qWords[1]));
                        } catch (NumberFormatException ex) {
                            throw new IllegalQueryStringException();
                        }
                    }
                    case "clear" -> {
                        if (qWords.length != 1)
                            throw new IllegalQueryStringException();
                        clear();
                    }
                    case "load" -> {
                        if (qWords.length == 1) {
                            load(properties.getProperty("defaultFilePath"));
                            break;
                        }
                        if (qWords.length == 2) {
                            load(qWords[1]);
                            break;
                        }
                        throw new IllegalQueryStringException();
                    }
                    case "save" -> {
                        if (qWords.length == 1) {
                            save(properties.getProperty("defaultFilePath"));
                            break;
                        }
                        if (qWords.length == 2) {
                            save(qWords[1]);
                            break;
                        }
                        throw new IllegalQueryStringException();
                    }
                    case "kill" ->{
                        if(qWords.length != 2)
                            throw new IllegalQueryStringException();
                        kill(Integer.parseInt(qWords[1]));
                    }
                    case "eat" ->{
                        if(qWords.length != 3)
                            throw new IllegalQueryStringException();
                        eat(Integer.parseInt(qWords[1]), Integer.parseInt(qWords[2]));
                    }
                    case "show" ->{
                        switch (qWords[1].toLowerCase()){
                            case "settings" -> {
                                if(qWords.length != 2)
                                    throw new IllegalQueryStringException();
                                showSettings();
                            }
                            default -> throw new IllegalQueryStringException();
                        }
                    }
                    case "edit" ->{
                        if(qWords.length < 2)
                            throw new IllegalQueryStringException();
                        switch (qWords[1].toLowerCase()){
                            case "setting" -> {
                                if(qWords.length != 4)
                                    throw new IllegalQueryStringException();
                                editSetting(qWords[2], qWords[3]);
                            }
                            default -> throw new IllegalQueryStringException();
                        }
                    }
                    case "reload" -> reload();
                    case "exit" -> exit();
                    case "help" -> showCommands();
                    default -> throw new IllegalQueryStringException();
                }
            }
            catch (ConsoleUIException | EntitiesException | StorageException ex){
                System.out.println("Ошибка:");
                System.out.println(ex.getMessage());
            }
        }
    }
    private Food create(String entityClass, String name, float weight) throws IllegalFoodWeightException, IllegalQueryStringException {
        return switch (entityClass.toLowerCase()) {
            case "predator" -> storage.createPredator(name, weight);
            case "herbivore" -> storage.createHerbivore(name, weight);
            case "grass" -> storage.createGrass(name, weight);
            default -> throw new IllegalQueryStringException();
        };
    }
    private Food find(int id) throws IllegalFoodWeightException {
        return storage.findEntityById(id);
    }
    private ArrayList<Food> find(String predicateName) throws IllegalQueryStringException {
        return switch (predicateName.toUpperCase()) {
            case "ALL" -> storage.findEntities(StoragePredicate.ALL_PREDICATE);
            case "ALIVE" -> storage.findEntities(StoragePredicate.ALIVE_PREDICATE);
            case "DEAD" -> storage.findEntities(StoragePredicate.DEAD_PREDICATE);
            default -> throw new IllegalQueryStringException();
        };
    }
    private ArrayList<Food> find(String predicateName, String value) throws IllegalQueryStringException {
        try {
            return switch (predicateName.toLowerCase()) {
                case "max_weight" -> storage.findEntities(StoragePredicate.getMaxWeightPredicate(Float.parseFloat(value)));
                case "min_weight" -> storage.findEntities(StoragePredicate.getMinWeightPredicate(Float.parseFloat(value)));
                case "class" -> storage.findEntities(StoragePredicate.getClassPredicate((Class<? extends Food>) Class.forName("com.animals.model.entities."+ value)));
                case "name" -> storage.findEntities(StoragePredicate.getNamePredicate(value));
                default -> throw new IllegalQueryStringException();
            };
        } catch (NumberFormatException | ClassNotFoundException ex) {
            throw new IllegalQueryStringException();
        }
    }
    private void update(int id, String name, float weight) throws IllegalUpdateIdException, IllegalFoodWeightException {
        Food temp = storage.findEntityById(id);
        if(temp == null)
            throw new IllegalUpdateIdException(id);
        temp.setName(name);
        temp.setWeight(weight);
        storage.updateEntity(temp);
    }
    private void delete(int id){
        storage.deleteEntity(id);
    }
    private void clear(){
        storage.clear();
    }
    private void load(String filePath) throws IllegalFilePathException, IllegalClassVersionException {
        storage.load(filePath);
    }
    private void save(String filePath) throws IllegalFilePathException {
        storage.save(filePath);
    }
    private void kill(int id) throws IllegalQueryStringException, AnimalDeadException, IllegalUpdateIdException, IllegalFoodWeightException {
        Food temp;
        try {
            temp = storage.findEntityById(id);
        }
        catch (NumberFormatException ex){
            throw new IllegalQueryStringException();
        }
        if(!(temp instanceof Animal))
            throw new IllegalQueryStringException();
        ((Animal) temp).die();
        storage.updateEntity(temp);
    }
    private void eat(int id1, int id2)
            throws IllegalQueryStringException, IllegalUpdateIdException, AnimalDeadException, IllegalFoodException, DeadFoodException, EatHimselfException, IllegalFoodWeightException {
        Food temp1, temp2;
        try {
            temp1 = storage.findEntityById(id1);
            temp2 = storage.findEntityById(id2);
        }
        catch (NumberFormatException ex){
            throw new IllegalQueryStringException();
        }
        if(!(temp1 instanceof Animal && temp2 != null))
            throw new IllegalQueryStringException();
        ((Animal) temp1).eat(temp2);
        storage.updateEntity(temp1);
        storage.updateEntity(temp2);
    }
    private void reload() throws IllegalFilePathException, IllegalFoodWeightException, IllegalClassVersionException, IllegalStorageInitModeValueException {
        save(properties.getProperty("defaultFilePath"));
        saveProperties();
        loadProperties();
        loadStorage();
    }
    private void exit() throws IllegalFilePathException {
        save(properties.getProperty("defaultFilePath"));
        saveProperties();
        System.exit(0);
    }
    private void showSettings(){
        properties.forEach((i, k) -> System.out.println(i + " = " + k));
    }
    private void showCommands(){
        System.out.println("Доступные команды:");
        System.out.println("create <класс> <имя> <вес>");
        System.out.println("find all|alive|dead|<id>");
        System.out.println("find max_weight|min_weight|class|name <значение>");
        System.out.println("update <id> <имя> <вес>");
        System.out.println("delete <id>");
        System.out.println("clear");
        System.out.println("load [<путь к файлу>]");
        System.out.println("save [<путь к файлу>]");
        System.out.println("kill <id>");
        System.out.println("eat <id съедающего> <id съедаемого>");
        System.out.println("show settings");
        System.out.println("edit setting <название> <значение>");
        System.out.println("reload");
        System.out.println("exit");
        System.out.println("help");
        System.out.println();
    }
}
