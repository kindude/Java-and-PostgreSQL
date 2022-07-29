package com.company;

import java.sql.*;

public class SimpleDb{
    public static void main(String[] args) throws Exception {
        Connection DB = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/CrossPlatform";
            String login = "postgres";
            String password = "admin";
            DB = DriverManager.getConnection(url, login, password);
            System.out.println("База данных загружена.");
            GUI Window = new GUI(DB);
        }
        catch (SQLException exc)
        {
            System.out.println(exc.getMessage());
            System.exit(1);
        }
    }
}



