package com.project.bar.menu.controller;

import com.project.bar.menu.model.Drink;
import com.project.bar.menu.service.BarService;
import com.project.bar.menu.service.DrinkService;
import com.project.bar.menu.service.FileService;
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

}
