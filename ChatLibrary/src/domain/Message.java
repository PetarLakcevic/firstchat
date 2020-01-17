/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.Image;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Petar
 */
public class Message implements GeneralEntity {

    private User sender;
    private User reciver;
    private String message;
    private Timestamp timestamp;
    private boolean read;

    public Message() {
    }

    public Message(User sender, User reciver, String message, Timestamp timestamp, boolean read) {
        this.sender = sender;
        this.reciver = reciver;
        this.message = message;
        this.timestamp = timestamp;
        this.read = read;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReciver() {
        return reciver;
    }

    public void setReciver(User reciver) {
        this.reciver = reciver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String getTableName() {
        return "direct_message";
    }

    @Override
    public List<GeneralEntity> getList(ResultSet resultSet) throws Exception {
        List<GeneralEntity> list = new LinkedList<>();
        while (resultSet.next()) {
            Long user_sender_id = resultSet.getLong("user_sender_id ");
            Long user_reciever_id = resultSet.getLong("user_reciever_id");
            String text = resultSet.getString("message_text");
            Timestamp ts = resultSet.getTimestamp("msg_timestamp");
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
