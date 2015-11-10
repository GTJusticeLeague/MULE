package edu.gatech.justiceleague.mule.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Handles database logic for the game
 */
public final class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String FILENAME = "SavedGameData.bin";

    /**
     * Do not allow an instance of this utility class to be constructed
     */
    private Database() {
        throw new UnsupportedOperationException();
    }

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

    public static ObservableList<String> queryDatabaseForGameIDs() throws SQLException, ClassNotFoundException {
        String query = "SELECT game_id FROM saved_games";
        Statement stmt;
        Connection conn = getConnection();
        ArrayList<String> list = new ArrayList<>();
        try {
            assert conn != null;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                list.add(rs.getString("game_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return FXCollections.observableArrayList(list);
    }

    public static void saveGame(String idJson, String configJson, String roundJson, String playerJson, String turnJson) {
        String insert = "INSERT INTO saved_games(game_id, game_config, game_round, current_player, game_seconds) "
                + " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement psmt;

        try (Connection conn = getConnection()) {
            assert conn != null;
            conn.setAutoCommit(false);
            psmt = conn.prepareStatement(insert);

            //Set the values of Prepared String
            psmt.setString(1, idJson);
            psmt.setString(2, configJson);
            psmt.setString(3, roundJson);
            psmt.setString(4, playerJson);
            psmt.setString(5, turnJson);

            psmt.executeUpdate();
            conn.commit();
            conn.close();
            psmt.close();
            System.out.println("SUCCESSFULLY SAVED FILE TO DB");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getGame(String loadedGameID) {
        ArrayList<String> list = new ArrayList<>();
        String query = " SELECT game_config, game_round, current_player, game_seconds FROM saved_games WHERE game_id = '" + loadedGameID + "';";

        Statement stmt;
        try {
            Connection conn = getConnection();
            assert conn != null;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                list.add(rs.getString("game_config"));
                list.add(rs.getString("game_round"));
                list.add(rs.getString("current_player"));
                list.add(rs.getString("game_seconds"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;



    }
}
