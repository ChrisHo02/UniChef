package com.example.unichef.database;

public class Instruction {
    private int step;
    private String instruction;
    private int time;
    private byte[] image;

    public Instruction(){}

    public Instruction(int step, String instruction, int time, byte[] image) {
        this.step = step;
        this.instruction = instruction;
        this.time = time;
        this.image = image;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] imageUrl) {
        this.image = imageUrl;
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
