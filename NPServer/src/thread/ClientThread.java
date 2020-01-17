/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import domain.Message;
import domain.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.Operation;
import transfer.Request;
import transfer.Response;
import transfer.ResponseStatus;
import controler.Controler;
import domain.AddUserDTO;

/**
 *
 * @author student1
 */
public class ClientThread extends Thread {

    private Socket socket;
    private User user;
    ServerThread serverThread;

    public ClientThread() {
    }

    public ClientThread(Socket socket, ServerThread serverThread) {
        this.socket = socket;

        this.serverThread = serverThread;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
                System.out.println("Waiting for request...");
                Request request = (Request) inSocket.readObject();
                processRequest(request);
            } catch (Exception ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void processRequest(Request request) throws Exception {

        Response r = new Response();
        switch (request.getOperation()) {
            case Operation.OPERATION_LOGIN:
                r.setOperation(Operation.OPERATION_LOGIN);
                r.setResponseStatus(ResponseStatus.OK);

                User u = (User) request.getData();
                try {
                    System.out.println("OVDE " + u);
                    User u1 = Controler.getInstance().logIn(u, this);
                    r.setData(u1);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    r.setError(e);
                    r.setResponseStatus(ResponseStatus.ERROR);
                }
                break;
            case Operation.OPERATION_REGISTER:
                r.setOperation(Operation.OPERATION_REGISTER);
                r.setResponseStatus(ResponseStatus.OK);
                User ur = (User) request.getData();
                try {
                    User ur1 = Controler.getInstance().register(ur, this);
                    r.setData(ur1);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    r.setError(e);
                    r.setResponseStatus(ResponseStatus.ERROR);
                }

                break;
            case Operation.OPERATION_SET_STATUS:
                r.setOperation(Operation.OPERATION_SET_STATUS);
                r.setResponseStatus(ResponseStatus.OK);
                User us = (User) request.getData();
                try {
                    User ur1 = Controler.getInstance().updateStatus(us);
                    r.setData(ur1);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    r.setError(e);
                    r.setResponseStatus(ResponseStatus.ERROR);
                }

                break;
            case Operation.OPERATION_ADD_FRIEND:
                r.setOperation(Operation.OPERATION_ADD_FRIEND);
                r.setResponseStatus(ResponseStatus.OK);
                AddUserDTO aud = (AddUserDTO) request.getData();
                try {
                    ArrayList<User> ur1 = Controler.getInstance().addFriend(aud);
                    r.setData(ur1);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    r.setError(e);
                    r.setResponseStatus(ResponseStatus.ERROR);
                }

                break;
            case Operation.OPERATION_GET_FRIENDS:
                r.setOperation(Operation.OPERATION_GET_FRIENDS);
                r.setResponseStatus(ResponseStatus.OK);
                User urf = (User) request.getData();
                try {
                    ArrayList<User> users = Controler.getInstance().getFriends(urf);
                    r.setData(users);
                    System.out.println("PROSAO");
                    System.out.println(users.size());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    r.setError(e);
                    r.setResponseStatus(ResponseStatus.ERROR);
                }
                break;
            case Operation.OPERATION_SEND_FILE:
                r.setOperation(Operation.OPERATION_SEND_FILE);
                r.setResponseStatus(ResponseStatus.OK);
                User urfa = (User) request.getData();
                try {
                    Controler.getInstance().sendFileTo(urfa);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    r.setError(e);
                    r.setResponseStatus(ResponseStatus.ERROR);
                }
                break;
            case Operation.OPERATION_LOGOUT:
                r.setOperation(Operation.OPERATION_LOGOUT);
                r.setResponseStatus(ResponseStatus.OK);
                User urfl = (User) request.getData();
                try {
                    Controler.getInstance().logout(urfl, this);
                    serverThread.getUsersThread().remove(this);
                } catch (Exception e) {
                    r.setError(e);
                    r.setResponseStatus(ResponseStatus.ERROR);
                }
                socket.close();
                this.stop();
                break;
            case Operation.OPERATION_SEND_MESSAGE:
                r.setOperation(Operation.OPERATION_SEND_MESSAGE);
                r.setResponseStatus(ResponseStatus.OK);
                Message m = (Message) request.getData();
                System.out.println("M je ovde" + m);
                try {
                    Controler.getInstance().sendMessageTo(m);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    r.setError(e);
                    r.setResponseStatus(ResponseStatus.ERROR);
                }
                break;
            case Operation.OPERATION_GET_MESSAGES:
                r.setOperation(Operation.OPERATION_GET_MESSAGES);
                r.setResponseStatus(ResponseStatus.OK);
                Message m1 = (Message) request.getData();
                try {
                    ArrayList<Message> alm = Controler.getInstance().getAllMessages(m1);
                    r.setData(alm);
                } catch (Exception e) {
                    r.setError(e);
                    r.setResponseStatus(ResponseStatus.ERROR);
                }
                break;
            case Operation.OPERATION_REMOVE_FRIEND:
                r.setOperation(Operation.OPERATION_REMOVE_FRIEND);
                r.setResponseStatus(ResponseStatus.OK);
                AddUserDTO aud1 = (AddUserDTO) request.getData();
                try {
                    boolean ur1 = Controler.getInstance().removeFriend(aud1);
                    r.setData(ur1);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    r.setError(e);
                    r.setResponseStatus(ResponseStatus.ERROR);
                }

                break;
            default:
                break; // remove
        }
        if (r.getOperation() != Operation.OPERATION_LOGOUT && r.getOperation() != Operation.OPERATION_SEND_MESSAGE && r.getOperation() != Operation. OPERATION_SEND_FILE) {
            sendResponse(r);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void sendResponse(Response response) throws IOException {
        ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
        outSocket.writeObject(response);
    }
}
