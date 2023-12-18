package com.demo;

public class User {

    private String userName;
    private String userSurname;
    private String nickname;
    private int age;

    private static int amount = 100;

    public User(String userName, String userSurname, String nickname, int age) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.nickname = nickname;
        this.age = age;
    }

    public User(){}

    public final String getUserName() {return userName;}

    public final String getUserSurname() {return userSurname;}

    public String getNickname() {return nickname;}

    public int getAge() {return age;}

    public static int GetAmount() {return amount;}
    public static void Test(int b) {amount -= b;}
    public void setUserName(String userName) {this.userName = userName;}

    public void setUserSurname(String userSurname) {this.userSurname = userSurname;}

    public void setNickname(String nickname) {this.nickname = nickname;}

    public void setAge(int age) {this.age = age;}
}
