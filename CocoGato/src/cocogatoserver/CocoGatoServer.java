/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ricar
 */


public class CocoGatoServer {
    static DataInputStream inPlayer1, inPlayer2;
    static DataOutputStream outPlayer1, outPlayer2;
 


    public static void main(String[] args) throws Exception {

        ServerSocket server;
        final int port = 471;
        DataInputStream in;
        DataOutputStream out;
        Socket player1 = null, player2 = null;
        
        
        try {
            server = new ServerSocket(port);
            System.out.println("Server iniciado");
     
            while (true) {
                if (player1 == null) {
                    player1 = server.accept();
                    inPlayer1 = new DataInputStream(player1.getInputStream());
                    outPlayer1 = new DataOutputStream(player1.getOutputStream());
                    outPlayer1.writeUTF("Esperando al Jugador 2");
                }
                if (player2 == null) {
                    player2 = server.accept();
                    inPlayer2 = new DataInputStream(player2.getInputStream());
                    outPlayer2 = new DataOutputStream(player2.getOutputStream());
                    outPlayer2.writeUTF("Esperando al Jugador 2");
                }
                
                if(player1 != null && player2 != null)
                    writeToPlayers("Jugador encontrado!");
               // String message = in.readUTF();
               // System.out.println(message);
               /* out.writeUTF("Y yo soy el server, Soy superior a ti xd");
                sc.close();
                System.out.println("Cliente desconectado");*/
            }
        } catch (IOException ex) {
            Logger.getLogger(CocoGatoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void writeToPlayers(String msg)
    {
        try {
            System.out.println(msg);
            outPlayer1.writeUTF(msg);
            outPlayer2.writeUTF(msg);
        } catch (IOException ex) {
            Logger.getLogger(CocoGatoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
