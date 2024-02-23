package com.demo.EditGame;

public class GameCard {
    private int ID;
    private String name;
    private String img;

    private String genre;
    private float price;
    private String releaseDate;
    private String description;
    private int ageRestriction;

    public GameCard(int ID, String name, String img, String genre, float price, String releaseDate, String description, int ageRestriction) {
        this.ID = ID;
        this.name = name;
        this.img = img;
        this.genre = genre;
        this.price = price;
        this.releaseDate = releaseDate;
        this.description = description;
        this.ageRestriction = ageRestriction;
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

    public String getGenre() {
        return genre;
    }

    public float getPrice() {
        return price;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }
}
