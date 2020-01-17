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
import java.net.Socket;

/**
 *
 * @author Petar
 */
public class ThreadTransferFile extends Thread {

    Socket socket = null;
    DataOutputStream out = null;
    DataInputStream in = null;
    String host = "127.0.0.1";
    String filename = "";

    public ThreadTransferFile(String filename) {
        this.filename = filename;

    }

    @Override
    public void run() {
        try {
            File file = new File(filename);
            socket = new Socket(host, 10000);
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
