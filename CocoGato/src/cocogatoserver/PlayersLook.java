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
        int contador = 1;
        while (true) {
            try {
                playerSocket = Server.server.accept();
                Jugador jugador = new Jugador();
                jugador.setId(contador);
                contador++;
                ConnectedPlayers connectedPlayer = new ConnectedPlayers(jugador, playerSocket);
                outPlayer = new DataOutputStream(playerSocket.getOutputStream());
                //outPlayer.writeUTF("Conectado al Servidor");
                outPlayer.writeInt(contador);
                System.out.println("Cliente Conectado");
                Server.connectedPlayers.add(connectedPlayer);
                                /*boolean done = true;
                while(done) {
                    System.out.println("Intentando recibir inicio de sesion");
                    byte messageType = inPlayer.readByte();
                    switch(messageType)
                    {
                    case 1: // Type A
                      System.out.println("Message A: " + inPlayer.readUTF());
                      break;
                    case 2: // Type B
                      System.out.println("Message B: " + inPlayer.readUTF());
                      break;
                    case 3: // Type C
                      System.out.println("Message C [1]: " + inPlayer.readUTF());
                      System.out.println("Message C [2]: " + inPlayer.readUTF());
                      done = false;
                      break;
                    default: 
                        done = false;
                        break;
                    }
                }*/
                
               if(playerSocket != null){
                   System.out.println("(PlayersLook): Hilo clientListener creado");
                    Thread clientListener = new ClientListener(playerSocket);
                    clientListener.run();
                    playerSocket = null;
               }
            } catch (IOException ex) {
                Logger.getLogger(PlayersLook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
