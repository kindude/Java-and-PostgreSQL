package com.company;

import java.sql.*;

public class SQL {

    public SQL() {
    }

    public static ResultSet getAllSymbol(Connection DB, int d) throws SQLException {
        ResultSet rs = null;
        Statement statement = DB.createStatement();
        rs = statement.executeQuery("SELECT * FROM \"Symbol\"WHERE \"id_Document\" = "+d+ "AND \"Printable\" = true");
        return rs;
    }

    public static ResultSet getAllSymbol(Connection DB) throws SQLException {
        ResultSet rs = null;
        Statement statement = DB.createStatement();
        rs = statement.executeQuery("SELECT * FROM \"Symbol\"");
        return rs;
    }

    public static ResultSet getAllDocument(Connection DB) throws SQLException {
        ResultSet rs = null;
        Statement statement = DB.createStatement();
        rs = statement.executeQuery("SELECT * FROM \"Document\"");
        return rs;
    }
}
