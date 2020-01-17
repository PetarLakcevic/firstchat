/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import domain.AddUserDTO;
import domain.Message;
import domain.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import storage.StorageUser;
import storage.database.DatabaseStorageUser;
import thread.ClientThread;
import thread.ServerThread;
import transfer.Operation;
import transfer.Response;
import transfer.ResponseStatus;
import session.Session;
import storage.StorageMessage;
import storage.database.DatabaseStorageMessage;

public class Controler {

    private static Controler instance;

    private final StorageUser storageUser;
    private final StorageMessage messageStorage;

    private Controler() {
        storageUser = new DatabaseStorageUser();
        messageStorage = new DatabaseStorageMessage();
    }

    public static Controler getInstance() {
        if (instance == null) {
            instance = new Controler();
        }
        return instance;
    }

    public List<User> returnAllUsers() throws Exception {
        return storageUser.getAll();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public User logIn(User u, ClientThread ct) throws Exception {
        ArrayList<User> users = storageUser.getAll();
        for (User u1 : users) {
            if (u.equals(u1)) {
                storageUser.updateLogin(u1);
                u1.setLastLoginDate(getCurrentTimeStamp());
                ct.setUser(u1);
                return u1;
            }

        }
        throw new Exception("Login failed! Invalid username password combination");
    }

    public void sendLogout() throws Exception {
        for (ClientThread c : Session.getInstance().getThreadsList()) {
            Response r = new Response(Operation.OPERATION_LOGOUT, ResponseStatus.OK, null, null);
            c.sendResponse(r);
        }
    }

    public User register(User u, ClientThread ct) throws Exception {
        ArrayList<User> users = storageUser.getAll();
        for (User u1 : users) {
            if (u1.getUsername().equals(u.getUsername())) {
                throw new Exception("Please choose a different username!");
            }
        }

        User createdUser = storageUser.addNew(u);
        ct.setUser(createdUser);
        return createdUser;
    }

    public User updateStatus(User us) throws Exception {
        return storageUser.updateStatus(us);
    }

    public ArrayList<User> addFriend(AddUserDTO aud) throws Exception {
        if (aud.getUser().getUsername().equals(aud.getUsername())) {
            throw new Exception("You cant add yourself.");
        }

        User u = storageUser.findByUsername(aud.getUsername());
        storageUser.addFriend(aud.getUser(), u);

        ArrayList<Long> friendsIdsList = storageUser.getFriendsIds(aud.getUser());
        ArrayList<User> friendsList = new ArrayList<>();

        for (ClientThread c : Session.getInstance().getThreadsList()) {
            if (c.getUser().equals(u)) {
                Response r = new Response(Operation.OPERATION_PING_UPDATE, ResponseStatus.OK, null, null);
                c.sendResponse(r);
            }
        }

        for (Long l : friendsIdsList) {
            User u1 = storageUser.findById(l);
            friendsList.add(u1);
        }
        return friendsList;
    }

    public boolean removeFriend(AddUserDTO aud1) throws Exception {
        User u = storageUser.findByUsername(aud1.getUsername());
        storageUser.removeFriends(aud1.getUser(), u);

        for (ClientThread c : Session.getInstance().getThreadsList()) {
            if (c.getUser().equals(u) || c.getUser().equals(aud1.getUser())) {
                Response r = new Response(Operation.OPERATION_PING_UPDATE, ResponseStatus.OK, null, null);
                c.sendResponse(r);
            }
        }

        return true;
    }

    public void sendMessageTo(Message m) throws Exception {
        messageStorage.save(m);
        for (ClientThread c : Session.getInstance().getThreadsList()) {
            if (c.getUser().getUsername().equals(m.getReciver().getUsername())) {
                Response r = new Response(Operation.OPERATION_RECEIVE_MESSAGE, ResponseStatus.OK, m, null);
                c.sendResponse(r);
            }
        }

    }

    public ArrayList<Message> getAllMessages(Message m1) throws Exception {
        return messageStorage.getAllMessagesForUsers(m1.getSender(), m1.getReciver());
    }

    public ArrayList<User> getFriends(User urf) throws Exception {
        ArrayList<Long> friendsIdsList = storageUser.getFriendsIds(urf);
        ArrayList<User> friendsList = new ArrayList<>();
        System.out.println("VELICINA: " + friendsIdsList.size());
        for (Long l : friendsIdsList) {
            User u1 = storageUser.findById(l);
            friendsList.add(u1);
        }
        return friendsList;
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

    public void logout(User urfl, ClientThread ct) {
        Session.getInstance().getThreadsList().remove(ct);
    }

    public void resetAll() {

    }

    public void sendFileTo(User urfa) throws Exception  {
        for (ClientThread c : Session.getInstance().getThreadsList()) {
            if (c.getUser().getUsername().equals(urfa.getUsername())) {
                Response r = new Response(Operation.OPERATION_SEND_FILE, ResponseStatus.OK, null, null);
                c.sendResponse(r);
            }
        }
    }

}
