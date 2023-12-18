package com.demo;

import java.sql.*;

public class JDBC
{
    public static void main(String[] args)
    {
        final String url = "jdbc:mysql://localhost:3306/gameshop";
        final String username = "root";
        final String password = "";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,password);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select * from user;");

            while(result.next())
            {
                System.out.println(result.getInt(1) + result.getString(2));
            }

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
