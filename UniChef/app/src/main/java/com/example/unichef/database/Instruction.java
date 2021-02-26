package com.example.unichef.database;

public class Instruction {
    private int step;
    private String instruction;
    private int time;
    private String imageUrl;

    public Instruction(int step, String instruction, int time, String imageUrl) {
        this.step = step;
        this.instruction = instruction;
        this.time = time;
        this.imageUrl = imageUrl;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

}
