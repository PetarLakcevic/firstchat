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
import java.io.FileOutputStream;
import java.util.Random;
import session.Session;

/**
 *
 * @author student1
 */
public class ClientThreadFile extends Thread {

    private Socket socket;
    private User user;
    ServerThreadFile serverThread;

    public ClientThreadFile() {
    }

    public ClientThreadFile(Socket socket, ServerThreadFile serverThread) {
        this.socket = socket;
        this.serverThread = serverThread;
        start();
    }

    @Override
    public void run() {

        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            Random r = new Random();
            int low = 10;
            int high = 100000;
            int result = r.nextInt(high - low) + low;
            String s = "C:\\Users\\ThinkPad\\Desktop\\pic" + result
                    + ".jpg";
            Session.getInstance().setFileAP(s);
            FileOutputStream fos = new FileOutputStream(s);

            byte[] buffer = new byte[4096];
            int filesize = 15123;
            int read = 0;
            int totalRead = 0;
            int remaining = filesize;
            while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                System.out.println("read " + totalRead + " bytes.");
                fos.write(buffer, 0, read);
            }
            fos.close();
            dis.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
