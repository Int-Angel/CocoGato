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
}
