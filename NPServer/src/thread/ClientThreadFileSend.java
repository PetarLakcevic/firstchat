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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;
import session.Session;

/**
 *
 * @author student1
 */
public class ClientThreadFileSend extends Thread {

    private Socket socket;
    private User user;
    ServerThreadFileSend serverThread;

    public ClientThreadFileSend() {
    }

    public ClientThreadFileSend(Socket socket, ServerThreadFileSend serverThread) {
        this.socket = socket;
        this.serverThread = serverThread;
        start();
    }

    @Override
    public void run() {
        try {
            File file = new File(Session.getInstance().getFileAP());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            while (fis.read(buffer) > 0) {
                dos.write(buffer);
            }

            fis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
