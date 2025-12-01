package com.project.bar.menu.exception;

public class DrinkNotFoundException extends RuntimeException{
    public DrinkNotFoundException(String msg){
        super(msg);
    }
}
