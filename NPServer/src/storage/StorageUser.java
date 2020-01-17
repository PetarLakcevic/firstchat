/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import domain.AddUserDTO;
import domain.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Petar
 */
public interface StorageUser {

    User findById(Long entityId) throws Exception;

    User findByUsernameAndPassword(String username, String password) throws Exception;

    User addNew(User user) throws Exception;

    ArrayList<User> getAll() throws Exception;

    User updateStatus(User us) throws Exception;

    void updateLogin(User us) throws Exception;

    User findByUsername(String username) throws Exception;

    boolean addFriend(User user, User u) throws Exception;

    ArrayList<Long> getFriendsIds(User user) throws Exception;

    void removeFriends(User user, User u) throws Exception;
}
