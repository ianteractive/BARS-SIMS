package com.project.bar.menu.service;

import com.project.bar.menu.model.Drink;
import com.project.bar.menu.model.Order;
import com.project.bar.menu.model.OrderItem;
import com.project.bar.menu.repository.DrinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final BarService barService;
    private final DrinkRepository drinkRepository;

    public FileService(BarService barService, DrinkRepository drinkRepository) {
        this.barService = barService;
        this.drinkRepository = drinkRepository;
    }

    public String processOrderFile(MultipartFile file) {
        List<OrderItem> items = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){

            String line;

            while((line = reader.readLine()) != null){
                //format: drinkId, quantity
                String[] data = line.split(",");
                int drinkId = Integer.parseInt(data[0].trim());
                int qty = Integer.parseInt(data[1].trim());

                OrderItem item = new OrderItem();
                Drink drink = drinkRepository.findById(drinkId)
                                .orElseThrow(() ->  new RuntimeException("Drink not found " + drinkId));
                item.setDrink(drink);
                item.setQuantity(qty);

                items.add(item);
            }
        } catch(IOException e){
            throw new RuntimeException("Error reading file", e);
        }

        Order order = barService.processOrder(items);

        //Absolute folder path
        String folderPath = System.getProperty("user.home") + "\\Desktop\\Result";
        Path folder = Paths.get(folderPath);
        try {
            if(!Files.exists(folder)){
                Files.createDirectories(folder);
            }
        } catch(IOException e){
            throw new RuntimeException("Could not create reports folder.", e);
        }

        // Write report file
        String outputFilePath = folderPath + "\\report_" + order.getId() + ".csv";
        try(PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(outputFilePath)))){
            writer.println("Drink, Quantity, Total");
            for(OrderItem i : order.getItems()){
                writer.printf("%s,%d,%.2f%n", i.getDrink().getName(), i.getQuantity(), i.getTotalPrice());
            }
            writer.println("Total Amount:, " + order.getTotalAmount());
        } catch (IOException e){
            throw new RuntimeException("Error writing report.", e);
        }
        return outputFilePath;
    }
}
