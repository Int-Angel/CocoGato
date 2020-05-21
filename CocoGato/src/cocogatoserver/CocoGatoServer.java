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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ricar
 */
/*
public class CocoGatoServer extends Thread{
    static DataInputStream inPlayer1, inPlayer2;
    static DataOutputStream outPlayer1, outPlayer2;
    static ServerSocket server;
    static final int port = 471;

    public CocoGatoServer(serverThreads interfaz) {
        interfaz.function();
    }
    
    public static void CreatePartida(Socket player1, Socket player2)
    {
        Thread newPartida = new Partida(player1, player2);
    }
    
    public static void ServerLoop()
    {
        Socket player;
        Server.playerSockets = new ArrayList<>();
        server = new ServerSocket(port);
                System.out.println("Server iniciado");
        while (true) {
            try {
                
                player = server.accept();
                Server.playerSockets.add(player);
            } catch (IOException ex) {
                Logger.getLogger(CocoGatoServer.class.getName()).log(Level.SEVERE, null, ex);
            }
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
*/