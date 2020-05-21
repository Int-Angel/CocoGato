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
 * @author carlo
 */
public class IniciarSesion{
    public static DB db = new DB();
    
    public static Jugador VerificarUsuario(String nombre, String password){
        ArrayList<Jugador> jugadores = db.selectPlayers();
            for(Jugador jugador : jugadores) {
                if(jugador.getUsuario().equals(nombre)){
                    System.out.println("Se encontro el usuario");
                    if(jugador.getContraseña().equals(password)){
                        System.out.println("Iniciando sesion...");
                        return jugador;
                    }else{
                         System.out.println("Contraseña incorrecta");
                    }
                }
            }
        return null;
    }

    /*
    @Override
    public void run() {
        Socket sesionSocket;
        int contador = 1;
        boolean done = true;
        while (done) {
            try {
                sesionSocket = Server.server.accept();
                inPlayer = new DataInputStream(sesionSocket.getInputStream());
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
                  break;
                default:
                  done = false;
                }
                
                if(sesionSocket != null){
                        Thread clientListener = new ClientListener(sesionSocket);
                        clientListener.start();
                   }
                } catch (IOException ex) {
                    Logger.getLogger(IniciarSesion.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
¨*/
}
