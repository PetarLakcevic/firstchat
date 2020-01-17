/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.Random;
import session.Session;

/**
 *
 * @author Petar
 */
public class ThreadTransferFileGet extends Thread {

    Socket socket = null;
    DataOutputStream out = null;
    DataInputStream in = null;
    String host = "127.0.0.1";
    String filename = "";

    public ThreadTransferFileGet(String filename) {
        this.filename = filename;

    }

    @Override
    public void run() {
        try {
            socket = new Socket(host, 10001);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String s = filename;
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
        } catch (Exception e) {

        }
    }

}
