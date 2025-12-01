package com.project.bar.menu.service;

import com.project.bar.menu.model.Drink;
import com.project.bar.menu.repository.DrinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public List<Drink> getAllDrinks(){
        return drinkRepository.findAll();
    }

    public Drink getDrinksById(Integer id) {
        return drinkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Drink ID " + id + " not found."));
    }

    public void addNewDrink(Drink drink) {
        drinkRepository.save(drink);
    }

    public void updateDrinkById(Integer id, Drink drink) {
        Drink updateDrink = drinkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Drink ID " + id + " not found."));

        if(drink.getName() != null){
            updateDrink.setName(drink.getName());
        }
        if(drink.getPrice() != null){
            updateDrink.setPrice(drink.getPrice());
        }
        if(drink.getStock() != null){
            updateDrink.setStock(drink.getStock());
        }

        drinkRepository.save(updateDrink);
    }

    public void deleteDrinkById(Integer id) {
         drinkRepository.deleteById(id);
    }

    public String importDrinks(MultipartFile file) {
        if(file.isEmpty()) {
            throw new RuntimeException("FILE IS EMPTY");
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String line;
            while ((line = bufferedReader.readLine()) != null){
                //format: name, price, stock
                String[] data = line.split(",");

                Drink drink = new Drink();
                drink.setName(data[0].trim());
                drink.setPrice(Double.parseDouble(data[1].trim()));
                drink.setStock(Integer.parseInt(data[2].trim()));

                drinkRepository.save(drink);
            }

        } catch (IOException e){
            throw new RuntimeException("Error reading file", e);
        }

        return "Drinks imported successfully";
    }
}
