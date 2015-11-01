package edu.gatech.justiceleague.mule.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles database logic for the game
 */
public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    /**
     * Initialize the database connection
     */
    static Connection getConnection() {
        try {
            FileReader fileReader = new FileReader("db_connect.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            System.out.println(bufferedReader.readLine());
            bufferedReader.close();
            String connect = "";
            Connection connection = DriverManager.getConnection(connect);
            System.out.println("CONNECT SUCCESS");
            return connection;

        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file.");
            e.printStackTrace();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
