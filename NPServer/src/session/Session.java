/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import domain.User;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import thread.ClientThread;

public class Session {

    private static Session instance;
    private ServerSocket serverSocket;
    private List<ClientThread> threadsList;
    private List<User> usersList;
    String fileAP = null;

    private Session() {
        threadsList = new ArrayList<>();
        usersList = new ArrayList<>();
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public List<ClientThread> getThreadsList() {
        return threadsList;
    }

    public void setThreadsList(List<ClientThread> threadsList) {
        this.threadsList = threadsList;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    public void resetAll() {
        serverSocket = null;
        threadsList = new ArrayList<>();
        usersList = new ArrayList<>();
    }

    public String getFileAP() {
        return fileAP;
    }

    public void setFileAP(String fileAP) {
        this.fileAP = fileAP;
    }
}
