package com.project.bar.menu.repository;

import com.project.bar.menu.model.Drink;
import com.project.bar.menu.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Integer> {

}
