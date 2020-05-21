/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoserver;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import cocogatoserver.DB;
import cocogatoserver.Jugador;
import java.util.ArrayList;

/**
 *
 * @author ricar
 */
public class PlayersLook extends Thread {
    static DataOutputStream outPlayer;
    static DataInputStream inPlayer;

    DB db = new DB();
    Jugador jugador = new Jugador();
    
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
                
                inPlayer = new DataInputStream(playerSocket.getInputStream());
                String usuario = "",password= "";
                boolean done = true;
                while(done) {
                    System.out.println("Intentando recibir inicio de sesion");
                    byte messageType = inPlayer.readByte();
                    switch(messageType)
                    {
                    case 1: // Type A
                      usuario = "" + inPlayer.readUTF();
                      password = "" + inPlayer.readUTF();
                      done = false;
                      break;
                      
                    default: 
                        done = false;
                      break;
                    }
                }
                
            ArrayList<Jugador> jugadores = db.selectPlayers();
            for(Jugador jugadorcito : jugadores) {
                if(jugadorcito.getUsuario().equals(usuario)){
                    System.out.println("Se econtro el usuario");
                    if(jugadorcito.getContraseña().equals(password)){
                        System.out.println("La contraseña coincidio");
                    }else{
                        System.out.println("Error en la contraseña");
                    }
                }
            }
                
               if(playerSocket != null){
                    Thread clientListener = new ClientListener(playerSocket);
                    clientListener.start();
               }
            } catch (IOException ex) {
                Logger.getLogger(PlayersLook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
