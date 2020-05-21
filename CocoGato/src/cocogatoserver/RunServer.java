/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoserver;

import java.io.BufferedReader;
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

public class RunServer {
    public static void main(String[] args) {
        try {
            Server.server = new ServerSocket(Server.port);
        } catch (IOException ex) {
            Logger.getLogger(RunServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Server iniciado");
        Server.connectedPlayers = new ArrayList<>();
        Thread playerLook = new PlayersLook();
        playerLook.run();
        Thread IniciarSesion = new IniciarSesion();
        IniciarSesion.run();
        
    }
}

