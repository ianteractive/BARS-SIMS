package com.project.bar.menu.controller;

import com.project.bar.menu.model.Drink;
import com.project.bar.menu.service.DrinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/drink")
public class DrinkController {

    private final DrinkService drinkService;

    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    //GET ALL DRINKS
    @GetMapping("/drinks")
    public List<Drink> getAllDrinks(){
        return drinkService.getAllDrinks();
    }

    //GET DRINK BY ID
    @GetMapping("/drinks/{id}")
    public Drink getDrinksById(@PathVariable Integer id){
        return drinkService.getDrinksById(id);
    }

    //ADD DRINK
    @PostMapping("/drinks/add")
    public ResponseEntity<String> addDrink(@RequestBody Drink drink){
        drinkService.addNewDrink(drink);
        return ResponseEntity.ok("Drink added successfully.");
    }

    //UPDATE DRINK
    @PutMapping("/drinks/update/{id}")
    public ResponseEntity<String> updateDrink(@PathVariable Integer id, @RequestBody Drink drink){
        drinkService.updateDrinkById(id, drink);
        return ResponseEntity.ok("Drink with ID " + id +" updated successfully.");
    }

    //DELETE DRINK
    @DeleteMapping("/drinks/delete/{id}")
    public ResponseEntity<String> deleteDrinkById(@PathVariable Integer id){
        drinkService.deleteDrinkById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    //IMPORT DRINK
    @PostMapping("/import")
    public String importDrinks(@RequestParam("file")MultipartFile file){
        return drinkService.importDrinks(file);
    }

}
