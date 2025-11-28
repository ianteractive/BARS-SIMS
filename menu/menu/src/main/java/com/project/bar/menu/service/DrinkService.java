package com.project.bar.menu.service;

import com.project.bar.menu.model.Drink;
import com.project.bar.menu.repository.DrinkRepository;
import org.springframework.stereotype.Service;

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
}
