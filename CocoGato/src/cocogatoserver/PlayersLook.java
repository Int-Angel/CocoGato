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
 * Clase PlayersLook
 * Al inicio del server se crea un hilo de esta clase, cuya función es
 * estar revisando si hay clientes nuevos que se conectan.
 * Para cada uno de los clientes conectados se crea un hilo de tipo clientListener.
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
