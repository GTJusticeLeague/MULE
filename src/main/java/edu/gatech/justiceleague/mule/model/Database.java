package edu.gatech.justiceleague.mule.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by thomas on 10/25/15.
 */
public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    /**
     * Initialize the database connection
     */
    static void makeConnection() {
        try {
            FileReader fileReader = new FileReader("db_connect.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            System.out.println(bufferedReader.readLine());
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
