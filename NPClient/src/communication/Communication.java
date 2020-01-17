/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import domain.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import transfer.Operation;
import transfer.Request;
import transfer.Response;

/**
 *
 * @author student1
 */
public class Communication {

    private static Communication instance;
    private Socket socket;
    private ObjectInputStream inSocket;
    private ObjectOutputStream outSocket;

    private Communication() throws Exception {
        try {
            socket = new Socket("localhost", 9000);
        } catch (IOException e) {
            Exception ex = new Exception("Couldn't connect to server!");
            throw ex;
        }
    }

    public static Communication getInstance() throws Exception {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

//    public void login(User user) throws IOException {
//        Request request = new Request(Operation.OPERATION_LOGIN, user);
//        if (outSocket == null) {
//            outSocket = new ObjectOutputStream(socket.getOutputStream());
//        }
//        outSocket.writeObject(request);
//    }

    public void sendRequest(Request request) throws IOException {
        ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
        outSocket.writeObject(request);
    }

    public Response readResponse() throws IOException, ClassNotFoundException {
        ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
        return (Response) inSocket.readObject();
    }
}
