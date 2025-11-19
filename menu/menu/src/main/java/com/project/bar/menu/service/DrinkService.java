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
}
