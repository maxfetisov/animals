package com.animals.model.storage;

import com.animals.model.entities.*;
import com.animals.model.entities.exceptions.EntitiesException;
import com.animals.model.entities.exceptions.IllegalFoodWeightException;
import com.animals.model.storage.exceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EntitiesStorage implements Serializable{
    private static final long serialVersionUID = 1;
    private static EntitiesStorage instance = null;
    private volatile int currentId = 0;
    private volatile ArrayList<Food> entities = new ArrayList<>();
    private EntitiesStorage(){}
    public static synchronized EntitiesStorage getInstance(){
        if(instance == null)
            instance = new EntitiesStorage();
        return instance;
    }
    public synchronized Predator createPredator(String name, float weight) throws IllegalFoodWeightException {
        Predator temp = new Predator(++currentId, name, weight);
        entities.add(temp);
        return new Predator(temp);
    }
    public synchronized Herbivore createHerbivore(String name, float weight) throws IllegalFoodWeightException {
        Herbivore temp = new Herbivore(++currentId, name, weight);
        entities.add(temp);
        return new Herbivore(temp);
    }
    public synchronized Grass createGrass(String name,float weight) throws IllegalFoodWeightException {
        Grass temp = new Grass(++currentId, name, weight);
        entities.add(temp);
        return new Grass(temp);
    }
    public Food findEntityById(int id) throws IllegalFoodWeightException {
        Food temp = entities.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
        if(temp instanceof Predator)
            return new Predator((Predator)temp);
        if(temp instanceof Herbivore)
            return new Herbivore((Herbivore)temp);
        if(temp instanceof Grass)
            return new Grass((Grass)temp);
        return null;
    }
    public synchronized void deleteEntity(int id){
        entities.removeIf(i -> i.getId() == id);
    }
    public synchronized void deleteEntity(Food food){
        entities.removeIf(i -> i.equals(food));
    }
    public synchronized void clear(){
        entities.clear();
        currentId = 0;
    }
    public synchronized void updateEntity(Food food) throws IllegalUpdateIdException {
       Food temp = entities.stream().filter(i -> i.getId() == food.getId()).findFirst().orElse(null);
       if(temp == null)
           throw new IllegalUpdateIdException(food.getId());
       temp.update(food);
    }
    public ArrayList<Food> findEntities(Predicate<Food> predicate){
        return new ArrayList<>(entities.stream().filter(predicate).collect(Collectors.toList()));
    }
    public synchronized void save(String filePath) throws IllegalFilePathException {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(this);
        }
        catch (IOException ex){
            throw new IllegalFilePathException();
        }
    }
    public synchronized void load(String filePath) throws IllegalClassVersionException, IllegalFilePathException {
        try (FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis)){
           EntitiesStorage temp = (EntitiesStorage)ois.readObject();
           currentId = temp.currentId;
           entities = temp.entities;
        }
        catch (InvalidClassException ex){
            throw new IllegalClassVersionException();
        }
        catch (IOException | ClassNotFoundException ex){
            throw new IllegalFilePathException();
        }
    }
    public synchronized void initDefault(){
        clear();
        try {
            createPredator("Волк", 50);
            createHerbivore("Заяц", 40);
            createGrass("Трава", 10);
        }
        catch (EntitiesException ex){}
    }
    public void initEmpty(){
        clear();
    }
    public void initFile(String filePath) throws IllegalFilePathException, IllegalClassVersionException {
        load(filePath);
    }
    @Override
    public String toString() {
        return "EntitiesStorage{" +
                "currentId=" + currentId +
                ", entities=" + entities +
                '}';
    }

}
