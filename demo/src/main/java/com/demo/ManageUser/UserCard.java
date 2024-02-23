package com.demo.ManageUser;

public class UserCard {
    private int id;
    private String username;
    private String image;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private int dollarAmount;

    public UserCard(int id, String username, String image, String password, String email, String firstName, String lastName, int dollarAmount) {
        this.id = id;
        this.username = username;
        this.image = image;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dollarAmount = dollarAmount;
    }

    public final String getPassword() {return password;}
    public final String getEmail() {return email;}

    public final String getFirstName() {return firstName;}

    public final String getLastName() {return lastName;}

    public final int getDollarAmount() {return dollarAmount;}

    public final int getId() {return id;}

    public final String getUsername() {
        return username;
    }

    public final String getImage() {
        return image;
    }
}
