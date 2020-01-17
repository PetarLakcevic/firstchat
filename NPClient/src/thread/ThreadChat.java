/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import domain.Message;
import domain.User;
import form.FormMain;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.Operation;
import transfer.Response;
import transfer.ResponseStatus;
import session.Session;
import controler.Controler;

/**
 *
 * @author student1
 */
public class ThreadChat extends Thread {

    private Socket socket;
    private FormMain formMain;

    public ThreadChat(Socket socket, FormMain formMain) {
        this.socket = socket;
        this.formMain = formMain;
        start();
    }

    @Override
    public void run() {

        while (!isInterrupted()) {
            try {
                ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
                Response response = (Response) inSocket.readObject();
                processResponse(response);
            } catch (Exception ex) {
//                Controler.getInstance().showMessageOnFormMain(new Exception("Sorry, the server went down!"));
//                System.exit(0);
            }
        }
    }

    private synchronized void processResponse(Response response) {
        switch (response.getOperation()) {
            case Operation.OPERATION_RECEIVE_MESSAGE:

                if (response.getResponseStatus() == ResponseStatus.OK) {
                    Message m = (Message) response.getData();
                    System.out.println(m.getMessage());
                    System.out.println(m.getSender().getUsername());
                    System.out.println(m.getReciver().getUsername());
                    Controler.getInstance().receieveMessage(m);
                } else {
                    Exception e = (Exception) response.getError();
                    Controler.getInstance().showMessageOnFormMain(e);
                }

                break;
            case Operation.OPERATION_SET_STATUS:
                if (response.getResponseStatus() == ResponseStatus.OK) {

                } else {
                    Exception e = (Exception) response.getError();
                    Controler.getInstance().showMessageOnFormMain(e);
                }

                break;
            case Operation.OPERATION_SEND_FILE:
                if (response.getResponseStatus() == ResponseStatus.OK) {

                    System.out.println("OK JE, PING ME JE DA UZMEM FILE");
                    Controler.getInstance().getFile();
                } else {
                    Exception e = (Exception) response.getError();
                    Controler.getInstance().showMessageOnFormMain(e);
                }

                break;
            case Operation.OPERATION_LOGOUT:
                if (response.getResponseStatus() == ResponseStatus.OK) {
                    try {
                        Controler.getInstance().logMeOut();
                    } catch (Exception ex) {
                    }
                } else {
                    Exception e = (Exception) response.getError();
                    Controler.getInstance().showMessageOnFormMain(e);
                }

                break;
            case Operation.OPERATION_GET_FRIENDS:
                if (response.getResponseStatus() == ResponseStatus.OK) {
                    ArrayList<User> friendsList = (ArrayList<User>) response.getData();
                    System.out.println("FLS " + friendsList.size());
                    Session.getInstance().getCurrentUser().setListOfFriends(friendsList);
                    Controler.getInstance().updateFriends(friendsList);
                } else {
                    Exception e = (Exception) response.getError();
                    Controler.getInstance().showMessageOnFormMain(e);
                }
                break;

            case Operation.OPERATION_ADD_FRIEND:
                if (response.getResponseStatus() == ResponseStatus.OK) {
                    ArrayList<User> friendsList = (ArrayList<User>) response.getData();
                    Session.getInstance().getCurrentUser().setListOfFriends(friendsList);
                    Controler.getInstance().updateFriends(friendsList);
                } else {
                    Exception e = (Exception) response.getError();
                    Controler.getInstance().showMessageOnFormMain(e);
                }
                break;
            case Operation.OPERATION_PING_UPDATE:
                try {
                    Controler.getInstance().sendGetFriends();
                } catch (Exception e) {
                    Controler.getInstance().showMessageOnFormMain(e);
                }
                break;
            case Operation.OPERATION_GET_MESSAGES:
                if (response.getResponseStatus() == ResponseStatus.OK) {
                    ArrayList<Message> messages = (ArrayList<Message>) response.getData();
                    Controler.getInstance().setMessages(messages);
                } else {
                    Exception e = (Exception) response.getError();
                    formMain.showMessage(e);
                }
                break;

            default:
                break;
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public FormMain getFormMain() {
        return formMain;
    }

    public void setFormMain(FormMain formMain) {
        this.formMain = formMain;
    }
}
