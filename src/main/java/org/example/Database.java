package org.example;

import java.sql.*;

import static java.lang.IO.print;
import static java.lang.IO.println;

public class Database {
    private static Database instance;
    private Connection connection;

    private Database() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:databse.db");
    }

    public static Database getInstance() throws SQLException {

        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public boolean insertPiatto(String piatto, double prezzo, int quantita) {
        try {
            if (!connection.isValid(5)) {
                throw new RuntimeException("Connessione persa");
            }
        } catch (SQLException e) {
            return false;
        }

        String query = "INSERT INTO sushi_menu (piatto, prezzo, quantita) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, piatto);
            statement.setDouble(2, prezzo);
            statement.setInt(3, quantita);
            statement.execute();
        } catch (SQLException e) {
            return false;
        }
        return false;
    }


    public void selectAll(String table) {
        try {
            if (!connection.isValid(5)) {
                throw new RuntimeException("Connessione persa");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Connessione Persa");
        }

        String query = "SELECT * FROM " + table;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            print("piatto"+ '\t');
            print("prezzo"+ '\t');
            print("quantita"+ '\t');
            println();

            while (resultSet.next()){
                print(resultSet.getString("piatto")+ '\t');
                print(resultSet.getString("prezzo")+ '\t');
                print(resultSet.getString("quantita")+ '\t');
                println();
            }
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
}
