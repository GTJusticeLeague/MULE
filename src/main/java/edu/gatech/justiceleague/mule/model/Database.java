package edu.gatech.justiceleague.mule.model;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Handles database logic for the game
 */
 public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String fileName = "SavedGameData.bin";
    /**
     * Initialize the database connection
     * @return Connection
     */
    static Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            //Reads in database connection string
            BufferedReader bufferedReader = new BufferedReader(new FileReader("db_connect.txt"));
            //Save text to connect string
            String thisLine;
            StringBuilder sb = new StringBuilder();
            while ((thisLine = bufferedReader.readLine()) != null) {
                sb.append(thisLine);
            }
            bufferedReader.close();

            //Connect to the database
            //NEED CORRECT DRIVER
            //Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(sb.toString());
            System.out.println("CONNECT SUCCESS");
            return connection;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Saves the text file to the database
     * @throws SQLException
     */
     static void saveTxtToDB() throws SQLException, IOException, ClassNotFoundException {
        String insert = "INSERT INTO serialized_java_objects(serialized_id, object_name, " +
                "serialized_object) VALUES (?, ?, ?)";

         PreparedStatement psmt = null;
         try (FileInputStream fis = new FileInputStream(new File(fileName)); Connection conn = getConnection()) {
             assert conn != null;
             conn.setAutoCommit(false);
             psmt = conn.prepareStatement(insert);

             //Set the values of Prepared String
             psmt.setString(1, GamePlay.gamePlayID());
             psmt.setString(2, "GamePlay");
             psmt.setBlob(3, fis);
             //or
             //File file = new File(fileName);
             //psmt.setAsciiStream(3, fis, (int) file.length());

             psmt.executeUpdate();
             conn.commit();
             System.out.println("SUCCESSFULLY SAVED FILE TO DB");
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } finally {
             assert psmt != null;
             psmt.close();


         }
     }
}
