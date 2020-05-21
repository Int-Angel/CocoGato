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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ricar
 */
public class PlayersLook extends Thread {
    static DataOutputStream outPlayer;
    static DataInputStream inPlayer;
    @Override
    public void run() {
        Socket playerSocket;
        while (true) {
            try {
                playerSocket = Server.server.accept();
                
                System.out.println("Cliente Conectado");
             
               if(playerSocket != null){
                   outPlayer = new DataOutputStream(playerSocket.getOutputStream());
                    outPlayer.writeUTF("Conectado al Servidor");
                    Thread clientListener = new ClientListener(playerSocket);
                    clientListener.start();
               }
            } catch (IOException ex) {
                Logger.getLogger(PlayersLook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
