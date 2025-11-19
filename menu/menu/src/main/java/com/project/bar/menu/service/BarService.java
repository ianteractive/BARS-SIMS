package com.project.bar.menu.service;

import com.project.bar.menu.model.Drink;
import com.project.bar.menu.model.Order;
import com.project.bar.menu.model.OrderItem;
import com.project.bar.menu.repository.DrinkRepository;
import com.project.bar.menu.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarService {

    private final DrinkRepository drinkRepository;
    private final OrderRepository orderRepository;

    public BarService(DrinkRepository drinkRepository, OrderRepository orderRepository) {
        this.drinkRepository = drinkRepository;
        this.orderRepository = orderRepository;
    }

    public Order processOrder(List<OrderItem> items){
        double total = 0;
        for(OrderItem item : items){
            Drink drink = drinkRepository.findById(item.getDrink().getId())
                    .orElseThrow(() -> new RuntimeException("Drink not found: " + item.getDrink().getId()));
            
            if(drink.getStock() < item.getQuantity()) throw new RuntimeException("Not enough stock for: " + drink.getName());

            drink.setStock(drink.getStock() - item.getQuantity());
            item.setTotalPrice(drink.getPrice() * item.getQuantity());
            total += item.getTotalPrice();
            drinkRepository.save(drink);
        }
        Order order = new Order();
        order.setItems(items);
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }
}
