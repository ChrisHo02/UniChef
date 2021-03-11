package com.example.unichef.database;

import java.io.Serializable;

public class Equipment implements Serializable {
    private String name;

    public Equipment(){}

    public Equipment(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }
}
