/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Propietario
 */
public class ClientListener extends Thread {

    // a lo mejor estaria bien que este hilo tubiera la informacion del cliente
    // al que esta escuchando
    Socket playerSocket;
    DataInputStream in;

    public ClientListener(Socket playerSocket) {
        this.playerSocket = playerSocket;
        try {
            in = new DataInputStream(playerSocket.getInputStream());
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {

        try {
            while (true) {
                String msg = in.readUTF();
                String[] splitMsg = msg.split(":");
                Decode(splitMsg);
            }
            //playerSocket = Server;
        } catch (IOException e) {
        }
    }

    void Decode(String[] msg) {
        if (msg.length == 3) {
            if (msg[0].equals("c")) {
                //Crear partida de gato con msg[1] y msg[2]
                CrearPartida(msg[1], msg[2]);

            }
        }
    }

    private void CrearPartida(String id1, String id2) {
        Socket socketPlayer1 = null;
        Socket socketPlayer2 = null;
        for (int i = 0; i < Server.connectedPlayers.size(); i++) {
            if (Server.connectedPlayers.get(i).jugador.id == (Integer.parseInt(id1))) {
                socketPlayer1 = Server.connectedPlayers.get(i).playerSocket;
            } else if (Server.connectedPlayers.get(i).jugador.id == (Integer.parseInt(id2))) {
                socketPlayer2 = Server.connectedPlayers.get(i).playerSocket;
            }
        }
        if (socketPlayer1 != null && socketPlayer2 != null) {
            Thread partidaThread = new Partida(socketPlayer1, socketPlayer2);
        }
    }
}