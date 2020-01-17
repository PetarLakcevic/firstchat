/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Petar
 */
public class User implements GeneralEntity {

    private long userId;
    private String username;
    private String password;
    private String status;
    private Timestamp registrationDate;
    private Timestamp lastLoginDate;
    private ArrayList<User> listOfFriends;

    public User() {
    }

    public User(long userId, String username, String password, String status, ArrayList<User> listOfFriends) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.status = status;
        this.listOfFriends = listOfFriends;

    }

    public User(long userId, String username, String password, String status, Timestamp registrationDate, Timestamp lastLoginDate, ArrayList<User> listOfFriends) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.status = status;
        this.registrationDate = registrationDate;
        this.lastLoginDate = lastLoginDate;
        this.listOfFriends = listOfFriends;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Timestamp getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Timestamp lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public ArrayList<User> getListOfFriends() {
        return listOfFriends;
    }

    public void setListOfFriends(ArrayList<User> listOfFriends) {
        this.listOfFriends = listOfFriends;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        } else {
            User u2 = (User) obj;
            if (u2.username.equals(getUsername()) && u2.password.equals(getPassword())) {
                return true;
            }
            return false;

        }
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + '}';
    }

    @Override
    public String getTableName() {
        return "user";
    }

    @Override
    public List<GeneralEntity> getList(ResultSet resultSet) throws Exception {
        List<GeneralEntity> list = new ArrayList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("user_id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String status = resultSet.getString("status");
            Timestamp regdate = resultSet.getTimestamp("registration_date");
            Timestamp lastlogindate = resultSet.getTimestamp("last_login_date");
            list.add(new User(id, username, password, status, regdate, lastlogindate, new ArrayList<User>()));
        }
        return list;
    }

    @Override
    public String getConstraints(Object keyword) {
        if (keyword instanceof String) {
            keyword = (String) keyword;
        }
        return " WHERE imePrezime LIKE '%" + keyword + "%' OR strucnaSprema LIKE '%" + keyword + "%'";
    }

    @Override
    public GeneralEntity getOne(ResultSet resultSet) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
