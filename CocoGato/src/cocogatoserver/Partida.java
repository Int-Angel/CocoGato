/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoserver;

import java.net.Socket;

/**
 *
 * @author Lenovo
 */
public class Partida extends Thread {
    static int contador = 1;
    
    int idpartida;
    int idJugador1;
    int idJugador2;
    int idGanador;
    String status;
    Socket player1;
    Socket player2;
    
    public Partida(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.idpartida = contador ++;
        this.status = "Creada";
        InGame();
    }
    
    public void InGame()
    {
    }
    
    public int getnId() {
        return idpartida;
    }

    public void setId(int idpartida) {
        this.idpartida = idpartida;
    }

    public int getIdJugador1() {
        return idJugador1;
    }

    public void setIdJugador1(int idJugador1) {
        this.idJugador1 = idJugador1;
    }

    public int getIdJugador2() {
        return idJugador2;
    }

    public void setIdJugador2(int idJugador2) {
        this.idJugador2 = idJugador2;
    }

    public int getIdGanador() {
        return idGanador;
    }

    public void setIdGanador(int idGanador) {
        this.idGanador = idGanador;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
