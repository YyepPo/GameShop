package com.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {
    private static DataBaseConnection instance;

    DataBaseConnection(){
    }

    public static DataBaseConnection getInstance() {
        // Lazy initialization: create the instance if it doesn't exist
        if (instance == null) {
            instance = new DataBaseConnection();
        }
        return instance;
    }

    private static Connection connection;
    private static Statement statement;

    ///
    ///Getters
    ///
    public static Statement getStatement() {return statement;}

    public static Connection getConnection() {
        return connection;
    }

    public static void CreateConnectionWithDB()
    {
        System.out.println("Connection has been created");
        final String urll = "jdbc:mysql://localhost:3306/gameshop";
        final String username = "root";
        final String password = "";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(urll,username,password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
