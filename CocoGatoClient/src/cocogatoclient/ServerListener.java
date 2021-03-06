/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * ServerListener
 * 
 * Esta clase escucha lo que el servidor le mande al juagdor y hace alguna
 * accion dependiendo del mensaje
 * 
 * @author Equipo
 */
public class ServerListener extends Thread {

    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    TicTacToeTablero tablero;
    int id;

    /**
     * ServerListener
     * constructor del hilo
     * @param socket recibe el socket del jugador
     */
    public ServerListener(Socket socket) {
        this.socket = socket;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
        }
    }

    /**
     * run
     * ejecucion del hilo, recibe los mensajes del server y las decodifica
     * y hace acciones dependiendo de la idea
     */
    @Override
    public void run() {
        String msg = "";
        while (true) {
            try {
                msg = in.readUTF();
                String[] splitMsg = msg.split(":");

                if (splitMsg[0].equals("i")) {
                    if (splitMsg[1].equals("false")) {
                        System.out.println("Inicio de Sesion fallido");
                    } else {
                        System.out.println("Inicio de Sesion Exitoso");
                        Jugador.id = Integer.parseInt(splitMsg[1]);
                        Jugador.usuario = splitMsg[2];
                        Jugador.conectado = true;
                        CocoGatoClient.frame.setVisible(false);

                        InflatePlayers();
                        CocoGatoClient.Inicialitation();
                    }
                } else if (splitMsg[0].equals("MOVIMIENTO")) {
                    System.out.println(msg);
                    RepaintBoard(splitMsg);
                } else if (splitMsg[0].equals("p")) {
                    Jugadores.jugadores = new ArrayList<Jugadores>();
                    Jugadores jugador = new Jugadores(Integer.parseInt(splitMsg[1]), splitMsg[2]);
                    Jugadores.jugadores.add(jugador);
                    System.out.println(jugador.id + ", " + jugador.usuario);

                    if (jugador.id == Jugador.id) {
                        jugador.id = 9999;
                        jugador.usuario = "IA";
                    }
                    TicTacToeTablero.agregarBoton(jugador);

                } else if (splitMsg[0].equals("TEINVITA")) {
                    System.out.println("Invitacion Recibida");
                    CocoGatoClient.launchInvitation(splitMsg[1]);

                } else if (splitMsg[0].equals("INICIARPARTIDA")) {
                    Jugador.contricanteId = Integer.parseInt(splitMsg[3]);
                    if (splitMsg[1].equals("1")) {
                        System.out.println("Taches");
                        TicTacToeTablero.Start(true);
                    } else if (splitMsg[1].equals("2")) {
                        System.out.println("Circulos");
                        TicTacToeTablero.Start(false);
                    }
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * InflatePlayers
     * Crea la lista con los jugadores obtenidos del servidor  (los jugadores 
     * conectados)
     */
    private void InflatePlayers() {
        try {
            out.writeUTF("p:0");
        } catch (IOException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * RepaintBoard
     * Reinicia el tablero para que se vean las actualizaciones de jugadas del
     * otro jugador
     * @param msg tablero con las posiciones de X o O
     */
    private void RepaintBoard(String[] msg) {
        System.out.println("Actualizando datos de partida");
        for (int i = 0; i < 9; i++) {
            TicTacToeTablero.tableroEnConsola[i] = msg[i + 3];
        }
        TicTacToeTablero.actualizarTablero();
        TicTacToeTablero.desbloquearBotonesDisponibles();

        System.out.println("Fin de actualizacion");
    }
}
