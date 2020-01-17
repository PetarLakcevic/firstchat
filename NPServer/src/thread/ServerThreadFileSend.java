/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import controler.Controler;
import domain.Message;
import domain.User;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.Operation;
import transfer.Response;
import session.Session;

/**
 *
 * @author student1
 */
public class ServerThreadFileSend extends Thread {

    private ServerSocket serverSocket;
    private List<ClientThread> usersThread;

    public ServerThreadFileSend(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        usersThread = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                System.out.println("Waiting for file transfer");
                Socket socket = getServerSocket().accept();
                ClientThreadFileSend clf = new ClientThreadFileSend(socket, this);

            } catch (Exception ex) {
                Logger.getLogger(ServerThreadFileSend.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public List<ClientThread> getUsersThread() {
        return usersThread;
    }

    public void setUsersThread(List<ClientThread> usersThread) {
        this.usersThread = usersThread;
    }

}
