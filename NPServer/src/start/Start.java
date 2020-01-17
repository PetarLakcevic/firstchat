/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import form.FormServer;
import java.io.IOException;
import server.Server;

/**
 *
 * @author student1
 */
public class Start {

    public static void main(String[] args) throws Exception {
        Server server = new Server(9000);
        server.start();
        System.out.println("End");
    }
}
