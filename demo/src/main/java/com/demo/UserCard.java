package com.demo;

public class UserCard {
    private int id;
    private String username;
    private String image;

    public UserCard(int id, String username, String image) {
        this.id = id;
        this.username = username;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }
}
