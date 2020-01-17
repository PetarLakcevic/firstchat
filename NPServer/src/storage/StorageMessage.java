/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import domain.AddUserDTO;
import domain.Message;
import domain.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public interface StorageMessage {
    void save(Message m) throws Exception;
    ArrayList<Message> getAllMessagesForUsers(User u, User u2) throws Exception;
}
