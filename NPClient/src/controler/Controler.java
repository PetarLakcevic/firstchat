/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import domain.Message;
import domain.User;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import thread.ThreadChat;
import communication.Communication;
import domain.AddUserDTO;
import enums.UseCase;
import form.FormLogin;
import form.FormMain;
import java.util.Random;
import transfer.Operation;
import transfer.Request;
import transfer.Response;
import transfer.ResponseStatus;
import session.Session;
import thread.ThreadTransferFile;
import thread.ThreadTransferFileGet;

public class Controler {

    private static Controler instance;

    ThreadChat threadChat;
    FormMain fm;
    FormLogin fl;
    String fn = "C:\\aaaa.jpg";

    private Controler() {

    }

    public static Controler getInstance() {
        if (instance == null) {
            instance = new Controler();
        }
        return instance;
    }

    public User sendLogin(String username, String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Request request = new Request(Operation.OPERATION_LOGIN, user);
        Communication.getInstance().sendRequest(request);
        Response response = Communication.getInstance().readResponse();
        if (response.getResponseStatus() == ResponseStatus.OK) {
            Session.getInstance().setCurrentUseCase(UseCase.ONLINE);
            Session.getInstance().setCurrentUser((User) response.getData());
            fl.showMsg("Login successful!");
            FormMain formMain = new FormMain();
            fm = formMain;
            formMain.setVisible(true);
            threadChat = new ThreadChat(Communication.getInstance().getSocket(), formMain);
            User urue = (User) response.getData();
            System.out.println("DATES" + urue.getRegistrationDate() + " \n" + urue.getLastLoginDate());
            return (User) response.getData();
        } else {
            fl.showMsg("Login unsuccessful");
        }
        Exception ex = (Exception) response.getError();
        throw ex;
    }

    public FormLogin getFl() {
        return fl;
    }

    public void setFl(FormLogin fl) {
        this.fl = fl;
    }

    public User sendRegister(String username, String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        Request request = new Request(Operation.OPERATION_REGISTER, user);
        Communication.getInstance().sendRequest(request);
        Response response = Communication.getInstance().readResponse();
        if (response.getResponseStatus() == ResponseStatus.OK) {
            Session.getInstance().setCurrentUseCase(UseCase.SETUP_PROFILE);
            Session.getInstance().setCurrentUser((User) response.getData());
            fl.showMsg("Register successful");
            FormMain formMain = new FormMain();
            fm = formMain;
            formMain.setVisible(true);
            threadChat = new ThreadChat(Communication.getInstance().getSocket(), formMain);

            return (User) response.getData();
        } else {
            fl.showMsg("Register unsuccessful");
        }
        Exception ex = (Exception) response.getError();
        throw ex;
    }

    public void sendUpdateStatus() throws Exception {
        Request request = new Request(Operation.OPERATION_SET_STATUS, Session.getInstance().getCurrentUser());
        Communication.getInstance().sendRequest(request);
    }

    public void sendAddFriend(String username) throws Exception {
        AddUserDTO aud = new AddUserDTO(Session.getInstance().getCurrentUser(), username);
        Request request = new Request(Operation.OPERATION_ADD_FRIEND, aud);
        Communication.getInstance().sendRequest(request);
    }

    public void sendGetFriends() throws Exception {
        Request request = new Request(Operation.OPERATION_GET_FRIENDS, Session.getInstance().getCurrentUser());
        Communication.getInstance().sendRequest(request);
    }

    public void sendLogout() throws Exception {
        Request request = new Request(Operation.OPERATION_LOGOUT, Session.getInstance().getCurrentUser());
        Communication.getInstance().sendRequest(request);
        threadChat.getSocket().close();
        threadChat.stop();

    }

    public void sendMessage(String text, User user) throws Exception {
        if (user == null) {
            return;
        }
        if (text == null) {
            return;
        }
        if (Session.getInstance().getCurrentUser() == null) {
            return;
        }
        Message m = new Message(Session.getInstance().getCurrentUser(), user, text, getCurrentTimeStamp(), false);
        Request request = new Request(Operation.OPERATION_SEND_MESSAGE, m);

        Communication.getInstance().sendRequest(request);
    }

    public static void sendGetMessages() throws Exception {
        if (Session.getInstance().getCurrentUser() == null || Session.getInstance().getSelectedFriend() == null) {
            throw new Exception("Please select the contact you wish to remove.");
        }
        Message m = new Message(Session.getInstance().getCurrentUser(), Session.getInstance().getSelectedFriend(), null, getCurrentTimeStamp(), false);
        Request request = new Request(Operation.OPERATION_GET_MESSAGES, m);

        Communication.getInstance().sendRequest(request);
    }

    public void sendRemove() throws Exception {
        if (Session.getInstance().getCurrentUser() == null || Session.getInstance().getSelectedFriend() == null) {
            throw new Exception("Please select the friend again.");
        }
        AddUserDTO aud = new AddUserDTO(Session.getInstance().getCurrentUser(), Session.getInstance().getSelectedFriend().getUsername());
        Request request = new Request(Operation.OPERATION_REMOVE_FRIEND, aud);
        Communication.getInstance().sendRequest(request);
    }

    public void sendFileData() throws Exception {
        Request request = new Request(Operation.OPERATION_SEND_FILE, Session.getInstance().getSelectedFriend());
        Communication.getInstance().sendRequest(request);
    }

    public void sendFile(String s) {
        ThreadTransferFile tsf = new ThreadTransferFile(s);
        tsf.start();
    }

    public void getFile() {
        Random r = new Random();
        int low = 10;
        int high = 100000;
        int result = r.nextInt(high - low) + low;
        String s = "C:\\Users\\ThinkPad\\Desktop\\pic" + result
                + ".jpg";
        fn = s;
        fm.showMessage(new Exception("You have a file message which will be saved here: \n" + s));
        ThreadTransferFileGet tsf = new ThreadTransferFileGet(s);
        tsf.start();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void showMessageOnFormMain(Exception e) {
        fm.showMessage(e);
    }

    public void updateFriends(ArrayList<User> friendsList) {
        fm.updateFriends(friendsList);
    }

    public void receieveMessage(Message m) {
        fm.recieveMessage(m);
    }

    public void setMessages(ArrayList<Message> messages) {
        fm.setMessages(messages);
    }

    public void logMeOut() throws Exception {
        fm.showMessage(new Exception("Server has shut down, try again later!"));
        threadChat.getSocket().shutdownInput();
        threadChat.getSocket().shutdownOutput();
        threadChat.getSocket().close();
        threadChat.stop();
        System.exit(0);
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

}
