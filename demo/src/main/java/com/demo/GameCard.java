package com.demo;

public class GameCard {
    private int ID;
    private String name;
    private String img;

    public GameCard(int ID, String name, String img) {
        this.ID = ID;
        this.name = name;
        this.img = img;
    }

    public final int getID() {
        return ID;
    }

    public final String getName() {
        return name;
    }

    public final String getImg() {
        return img;
    }
}
