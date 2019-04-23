package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * This class will get the connection and
 * initialized data base table
 */
public class DataBase {
    /**
     * connection to data base
     */
    private Connection connect;

    /**
     * constructor that will set up driver for data base
     */
    public DataBase() {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.print("class not found");
        }

    }

    /**
     * It will open the connection
     *
     * @return connect connection path to data base
     */
    public Connection openConnection() {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";
            connect = DriverManager.getConnection(CONNECTION_URL);
            connect.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }

    /**
     * It close the connection and decide if it needs to commit or rollback
     *
     * @param commit a boolean to tell if it can be committed
     */
    public void closeConnection(boolean commit) {
        try {
            if (commit) {
                connect.commit();
            } else {
                connect.rollback();
            }
            connect.close();
            connect = null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Unable to close database connection");
        }
    }

    /**
     * It will create tables if the tables are not exist
     *
     * @throws SQLException exception that can happen using SQL
     */
    public void createTables() throws SQLException {
        File in = new File("tables.txt");
        String content;
        try {
            content = new Scanner(in).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File error ");
            return;
        }
        openConnection();
        Statement stmt = connect.createStatement();
        try {
            stmt.executeUpdate(content);
            closeConnection(true);
        } catch (SQLException e) {
            closeConnection(false);
            System.out.println("SQL error - create table");
        } catch (Exception e) {
            System.out.println("other exceptions ");
            e.printStackTrace();
        }
    }

    /**
     * It will clear the tables if they are exists
     *
     * @throws SQLException exception that can happen using SQL
     */
    public void clearTables() throws SQLException {
        openConnection();
        Statement stmt = connect.createStatement();
        try {
            String sql = "drop table if exists events ;" +
                    "drop table if exists person ;" +
                    "drop table if exists user ;" +
                    "drop table if exists auth ";
            stmt.executeUpdate(sql);

            closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
            closeConnection(false);
            System.out.println("SQL error - clear table ");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("other exceptions  ");
            e.printStackTrace();
        }
        //createTables();
    }

}



