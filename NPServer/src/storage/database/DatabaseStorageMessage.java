/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage.database;

import domain.AddUserDTO;
import domain.Message;
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
import storage.AbstractStorageMessage;
import storage.AbstractStorageUser;
import storage.database.connection.DatabaseConnection;

/**
 *
 * @author student1
 */
public class DatabaseStorageMessage extends AbstractStorageMessage {

    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

    @Override
    public void save(Message m) throws Exception {

        Connection connection = DatabaseConnection.getInstance().getConnection();
        String SQL_INSERT = "INSERT INTO direct_message (user_id_sender, user_id_receiever, message_text, msg_timestamp ) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, m.getSender().getUserId());
        statement.setLong(2, m.getReciver().getUserId());
        statement.setString(3, m.getMessage());
        statement.setTimestamp(4, getCurrentTimeStamp());

        int affectedRows = statement.executeUpdate();
        DatabaseConnection.getInstance().commitTransaction();
        if (affectedRows == 0) {
            throw new SQLException("Creating messages failed, no rows affected.");
        }
    }

    @Override
    public ArrayList<Message> getAllMessagesForUsers(User u, User u2) throws Exception {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            System.out.println("Uspostavljena je konekcija na bazu");
            // SELECT * FROM direct_message WHERE (user_id_sender='1' AND user_id_receiever='2') OR (user_id_sender='2' AND user_id_receiever='1') 
            String query = "select * from direct_message where (user_id_sender='" + u.getUserId()
                    + "' and user_id_receiever='" + u2.getUserId() + "') or (user_id_sender='" + u2.getUserId()
                    + "' and user_id_receiever='" + u.getUserId() + "');";
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<Message> listOfMessages = new ArrayList<>();

            while (resultSet.next()) {
                long senderId = resultSet.getLong("user_id_sender");
                long recieverid = resultSet.getLong("user_id_receiever");
                Message m = new Message();
                m.setRead(false);
                m.setMessage(resultSet.getString("message_text"));
                m.setTimestamp(resultSet.getTimestamp("msg_timestamp"));
                if (senderId == u.getUserId()) {
                    m.setSender(u);
                    m.setReciver(u2);
                } else {
                    m.setSender(u2);
                    m.setReciver(u);
                }
                listOfMessages.add(m);
            }
            return listOfMessages;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        throw new Exception("User does not exist");

    }

}
