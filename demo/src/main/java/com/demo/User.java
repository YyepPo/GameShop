package com.demo;

public class User {

    private static String userName;
    private static String userSurname;
    private static String nickname;
    private static int age;

    private static int amount = 100;

    public User(String userName, String userSurname, String nickname, int age) {
        userName = userName;
        userSurname = userSurname;
        nickname = nickname;
        age = age;
    }

    public User(){}

    public static String getUserName() {return userName;}

    public static String getUserSurname() {return userSurname;}

    public static String getNickname() {return nickname;}

    public static int getAge() {return age;}

    public static int GetAmount() {return amount;}
    public static void Test(int b) {amount -= b;}
    public static void setUserName(String newUserName) {userName = newUserName;}

    public static void setUserSurname(String newUserSurname) {userName = newUserSurname;}

    public static void setNickname(String newNickname) {nickname = newNickname;}

    public static void setAge(int newAge) {age = newAge;}
}
