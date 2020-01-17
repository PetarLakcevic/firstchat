/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author student1
 */
public class DatabaseConnection {

    private final Connection connection;
    private static DatabaseConnection instance;

    private DatabaseConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/chat_database";
        String dbuser = "root";
        String dbpassword = "";
        connection = DriverManager.getConnection(url, dbuser, dbpassword);
        connection.setAutoCommit(false);
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
    }

    public void rollbackTransaction() throws SQLException {
        connection.rollback();
    }
}
