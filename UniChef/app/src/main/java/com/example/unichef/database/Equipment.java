package com.example.unichef.database;

public class Equipment {
    private int equipmentId;
    private String name;

    public Equipment(){ }

    public Equipment(int equipmentId, String name){
        this.equipmentId = equipmentId;
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }
}
