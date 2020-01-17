/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage.database;

import domain.AddUserDTO;
import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jdk.nashorn.internal.ir.CatchNode;
import storage.AbstractStorageUser;
import storage.database.connection.DatabaseConnection;

/**
 *
 * @author Petar
 */
public class DatabaseStorageUser extends AbstractStorageUser {

    @Override
    public User findById(Long entityId) throws Exception {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            System.out.println("Uspostavljena je konekcija na bazu");
            String query = "select * from user where user_id=" + entityId;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Long id = resultSet.getLong("user_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String status = resultSet.getString("status");
                Timestamp regdate = resultSet.getTimestamp("registration_date");
                Timestamp lastlogindate = resultSet.getTimestamp("last_login_date");
                return new User(id, username, password, status, regdate, lastlogindate, new ArrayList<User>());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        throw new Exception("User does not exist");
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws Exception {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            System.out.println("Uspostavljena je konekcija na bazu");
            String query = "select * from user where username='" + username
                    + "' and password='" + password + "';";
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Long dbuser_id = resultSet.getLong("user_id");
                String dbusername = resultSet.getString("username");
                String dbpassword = resultSet.getString("password");
                String dbstatus = resultSet.getString("status");
                Timestamp regdate = resultSet.getTimestamp("registration_date");
                Timestamp lastlogindate = resultSet.getTimestamp("last_login_date");
                return new User(dbuser_id, dbusername, dbpassword, dbstatus, regdate, lastlogindate, new ArrayList<User>());

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        throw new Exception("User does not exist");
    }

    @Override
    public ArrayList<User> getAll() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            System.out.println("Uspostavljena je konekcija na bazu");
            String query = "select * from user;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Long dbuser_id = resultSet.getLong("user_id");
                String dbusername = resultSet.getString("username");
                String dbpassword = resultSet.getString("password");
                String dbstatus = resultSet.getString("status");
                Timestamp regdate = resultSet.getTimestamp("registration_date");
                Timestamp lastlogindate = resultSet.getTimestamp("last_login_date");
                users.add(new User(dbuser_id, dbusername, dbpassword, dbstatus, regdate, lastlogindate, new ArrayList<User>()));
            }
            return users;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        throw new Exception("User does not exist");

    }

    @Override
    public User addNew(User user) throws Exception {

        Connection connection = DatabaseConnection.getInstance().getConnection();
        String SQL_INSERT = "INSERT INTO user (username, password, status, registration_date, last_login_date) VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.setString(3, "I am using NPChat!");
        java.util.Date today = new java.util.Date();
        statement.setTimestamp(4, getCurrentTimeStamp());
        statement.setTimestamp(5, getCurrentTimeStamp());

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                connection.commit();
                user.setUserId(generatedKeys.getLong(1));
                return user;
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    @Override
    public User updateStatus(User us) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String SQL_UPDATE = "UPDATE user SET status=? WHERE username=?";
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);

        statement.setString(1, us.getStatus());
        statement.setString(2, us.getUsername());

        int a = statement.executeUpdate();
        if (a >= 0) {
            connection.commit();
            return us;
        } else {
            throw new Exception("Couldn't find the user");
        }

    }

    @Override
    public User findByUsername(String username) throws Exception {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            System.out.println("Uspostavljena je konekcija na bazu");
            String query = "select * from user where username='" + username + "';";
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Long dbuser_id = resultSet.getLong("user_id");
                String dbusername = resultSet.getString("username");
                String dbpassword = resultSet.getString("password");
                String dbstatus = resultSet.getString("status");
                return new User(dbuser_id, dbusername, dbpassword, dbstatus, new ArrayList<User>());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        throw new Exception("User does not exist");
    }

    @Override
    public boolean addFriend(User user, User u) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String SQL_INSERT = "INSERT INTO friends (user_id_1, user_id_2) VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, user.getUserId());
        statement.setLong(2, u.getUserId());
        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            connection.rollback();
            throw new SQLException("Creating user failed, no rows affected.");
        } else {
            connection.commit();
            return true;
        }
    }

    @Override
    public ArrayList<Long> getFriendsIds(User user) throws Exception {

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            System.out.println("Uspostavljena je konekcija na bazu");
            String query = "select * from friends where user_id_1='" + user.getUserId() + "' or user_id_2='" + user.getUserId() + "';";
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<Long> listOfIds = new ArrayList<>();

            while (resultSet.next()) {
                listOfIds.add(resultSet.getLong("user_id_1"));
                listOfIds.add(resultSet.getLong("user_id_2"));
            }

            ArrayList<Long> uniques = new ArrayList<>();
            for (Long l : listOfIds) {
                if (l != user.getUserId() && !uniques.contains(l)) {
                    uniques.add(l);
                }
            }
            return uniques;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        throw new Exception("User does not exist");

    }

    @Override
    public void updateLogin(User us) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String SQL_UPDATE = "UPDATE user SET last_login_date=? WHERE username=?";
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);

        statement.setTimestamp(1, getCurrentTimeStamp());
        statement.setString(2, us.getUsername());

        int a = statement.executeUpdate();
        if (a >= 0) {
            connection.commit();
        } else {
            throw new Exception("Couldn't find the user");
        }

    }

    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

    @Override
    public void removeFriends(User user, User u) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String SQL_UPDATE = "DELETE FROM friends WHERE  (user_id_1=? and user_id_2=?) or (user_id_1=? and user_id_2=?)";
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);

        statement.setLong(1, user.getUserId());
        statement.setLong(2, u.getUserId());
        statement.setLong(3, u.getUserId());
        statement.setLong(4, user.getUserId());

        int a = statement.executeUpdate();
        if (a >= 0) {
            connection.commit();
        } else {
            throw new Exception("Couldn't find the user");
        }
    }

}
