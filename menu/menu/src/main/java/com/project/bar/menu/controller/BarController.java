package com.project.bar.menu.controller;

import com.project.bar.menu.model.Drink;
import com.project.bar.menu.service.BarService;
import com.project.bar.menu.service.DrinkService;
import com.project.bar.menu.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/bar")
public class BarController {

    private final FileService fileService;
    private final DrinkService drinkService;

    public BarController(FileService fileService, DrinkService drinkService) {
        this.fileService = fileService;
        this.drinkService = drinkService;
    }

    @PostMapping("/upload")
    public String uploadOrders(@RequestParam("file") MultipartFile file){
        return "Report generated at " + fileService.processOrderFile(file);
    }

    @GetMapping("/drinks")
    public List<Drink> getAllDrinks(){
        return drinkService.getAllDrinks();
    }

    @GetMapping("/drinks/{id}")
    public Drink getDrinksById(@PathVariable Integer id){
        return drinkService.getDrinksById(id);
    }

    @PostMapping("/drinks/add")
    public ResponseEntity<String> addDrink(@RequestBody Drink drink){
        drinkService.addNewDrink(drink);
        return ResponseEntity.ok("Drink added successfully.");
    }

    @PutMapping("/drinks/update/{id}")
    public ResponseEntity<String> updateDrink(@PathVariable Integer id, @RequestBody Drink drink){
        drinkService.updateDrinkById(id, drink);
        return ResponseEntity.ok("Drink with ID " + id +" updated successfully.");
    }

    @DeleteMapping("/drinks/delete/{id}")
    public ResponseEntity<String> deleteDrinkById(@PathVariable Integer id){
         drinkService.deleteDrinkById(id);
         return ResponseEntity.ok("Deleted successfully!");
    }

}
