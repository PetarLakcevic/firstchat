/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import thread.ServerThread;
import controler.Controler;
import thread.ServerThreadFile;
import thread.ServerThreadFileSend;

/**
 *
 * @author student1
 */
public class Server {

    private Thread serverThread;
    private ServerThreadFile stf;
    private ServerThreadFileSend send;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        serverThread = new ServerThread(port);
        serverThread.start();
        send = new ServerThreadFileSend(10001);
        send.start();
        stf = new ServerThreadFile(10000);
        stf.start();
        send.join();
        stf.join();
        serverThread.join();
    }

    public void stop() {
        session.Session.getInstance().resetAll();
        Controler.getInstance().resetAll();

    }

}
