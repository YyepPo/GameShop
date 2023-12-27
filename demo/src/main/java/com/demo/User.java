package com.demo;

public class User {

    private static String userName;
    private static String userSurname;
    private static String nickname;
    private static int age;
    private static int userID;
    private static double amount = 100;

    public User(String userName, String userSurname, String nickname, int age) {
        userName = userName;
        userSurname = userSurname;
        nickname = nickname;
        age = age;
    }
    public static String getUserName() {return userName;}
    public static String getUserSurname() {return userSurname;}
    public static String getNickname() {return nickname;}
    public static int getAge() {return age;}
    public static double GetAmount() {return amount;}
    public static void Test(int b) {amount -= b;}
    public static void setUserName(String newUserName) {userName = newUserName;}
    public static void setUserSurname(String newUserSurname) {userName = newUserSurname;}
    public static void setNickname(String newNickname) {nickname = newNickname;}
    public static void setAge(int newAge) {age = newAge;}

    public static int GetUserID() {return userID;}
    public static void SetUserID(int newuserID) {userID = newuserID;}

    public static void SpendMoney(double moneyToSpend) {amount -= moneyToSpend;}
}
