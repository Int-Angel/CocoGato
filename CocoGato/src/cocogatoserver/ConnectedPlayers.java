/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoserver;

import java.net.Socket;

/**
 *
 * @author ricar
 */
public class ConnectedPlayers {

    public ConnectedPlayers(Jugador jugador, Socket playerSockets) {
        this.jugador = jugador;
        this.playerSockets = playerSockets;
    }
        public Jugador jugador;
        public Socket playerSockets;
}
