/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoserver;

import java.net.Socket;

/**
 * Clase modelo para la tabla jugador
 * 
 * Esta clase contiene las propiedades para almacenar los registros de la tabla
 * jugador de la DB
 * 
 * @author Miguel
 */
public class Jugador {
    
    int id;
    String usuario;
    String contraseña;
    static Boolean refrescar;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
