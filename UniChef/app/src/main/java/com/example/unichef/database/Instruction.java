package com.example.unichef.database;

import java.io.Serializable;

public class Instruction implements Serializable {
    private int step;
    private String instruction;
    private int time;

    public Instruction(){}

    public Instruction(int step, String instruction, int time) {
        this.step = step;
        this.instruction = instruction;
        this.time = time;
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
