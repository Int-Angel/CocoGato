/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoclient;

import java.util.ArrayList;

/**
 *
 * @author ricar
 */
public class Jugadores {

    public Jugadores(int id, String usuario) {
        this.id = id;
        this.usuario = usuario;
    }
    public int id;
    public String usuario;
   
    public static ArrayList<Jugadores> jugadores; 
}