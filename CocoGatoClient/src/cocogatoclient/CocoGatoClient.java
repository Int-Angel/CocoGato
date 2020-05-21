/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;

/**
 *
 * @author ricar
 */
public class CocoGatoClient {

    public static void main(String[] args) {

        // TODO code application logic here
        new TicTacToeTablero();

        String host = "25.93.46.49";
        final int puerto = 471;
        DataInputStream in;
        DataOutputStream out;

        
        try {
            Socket sc = new Socket(host, puerto);
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF("que pedo, meco");
            

    
            String message = in.readUTF();
            
            System.out.println(message);
            
            sc.close();
      
            
        } catch (IOException ex) {
            //Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR");
        }
    }
}
