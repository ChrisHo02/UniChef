package com.example.unichef.database;

import java.io.Serializable;

public class Tag implements Serializable {
    private String name;

    public Tag(){}

    public Tag(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
