package com.demo.ManageUser;

import com.demo.E_UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class User {

    private static String userName;
    private static String userSurname;
    private static String nickname;
    private static int age;
    private static int userID;
    private static double amount = 100;

    private static E_UserType UserType;

    public User(String userName, String userSurname, String nickname, int age) {
        userName = userName;
        userSurname = userSurname;
        nickname = nickname;
        age = age;
    }

    ///
    ///Getters
    ///
    public static String getUserName() {return userName;}
    public static String getUserSurname() {return userSurname;}
    public static String getNickname() {return nickname;}
    public static int getAge() {return age;}
    public static int GetUserID() {return userID;}
    public static double GetAmount() {return amount;}

    public static int GetDollarAmount(Connection conn)
    {
        int dollarAmount = 0;
        String getDollarAmountSql = "select * from user where user_ID = " + GetUserID();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(getDollarAmountSql);
            ResultSet set = preparedStatement.executeQuery();
            if(set.next())
            {
                dollarAmount = set.getInt("dollarAmount");
                return dollarAmount;
            }
        }
        catch (SQLException e){
            throw new RuntimeException("user_game data base error (ProductController initialize)");
        }
        return 0;
    }
    ///
    ///Setters
    ///
    public static void setUserName(String newUserName) {userName = newUserName;}

    public static void SetUserID(int newuserID) {userID = newuserID;}



    public static E_UserType IsAdmin(Connection conn)
    {
        int roleIndex = 0;
        E_UserType userRole = E_UserType.User;
        String query = "SELECT ur.user_role FROM user u " +
                "JOIN user_type ur ON u.user_id = ur.user_id " +
                "WHERE u.user_id = " + GetUserID();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet set = preparedStatement.executeQuery();
            if(set.next()) roleIndex = set.getInt("user_role");
            userRole = (roleIndex == 1) ?  E_UserType.Admin :  E_UserType.User;
            System.out.println(userRole.toString());
            return userRole;
        }
        catch (SQLException e){
            throw new RuntimeException("user_game data base error (ProductController initialize)");
        }
    }


}
