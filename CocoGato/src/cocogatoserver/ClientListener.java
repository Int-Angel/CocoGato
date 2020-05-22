/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

ESTE ES EL LISTENER QUE ESTA ESPERANDO INVITACION.
 */
package cocogatoserver;

import static cocogatoserver.PlayersLook.inPlayer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Propietario
 */
public class ClientListener extends Thread {

    Socket playerSocket;
    DataInputStream in;
    DataOutputStream out;
    DB db = new DB();
    Jugador jugador = new Jugador();

    Socket socketPlayer1 = null;
    Socket socketPlayer2 = null;
    DataOutputStream socketOutput1, socketOutput2;

    public ClientListener(Socket playerSocket) {
        this.playerSocket = playerSocket;
        try {
            in = new DataInputStream(playerSocket.getInputStream());
            out = new DataOutputStream(playerSocket.getOutputStream());
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
        /*if(msg[0].equals("c"))
            {
              CrearPartida(msg[1], msg[2]);
            }*/
        if (msg[0].equals("INVITAR")) {
            if (msg[2].equals("9999")) {
                Socket socketPlayer1 = null;
                DataOutputStream socketOutput1, socketOutput2;
                System.out.println("Estamos trabajando en la IA :) ");
                try {
                    socketOutput1 = new DataOutputStream(playerSocket.getOutputStream());
                    socketOutput1.writeUTF("IA");
                    System.out.println("Se abrio tablero para la IA");
                } catch (IOException easd) {
                    System.out.println("Error Al intentar jugar con la IA");
                }
            } else
                Invitar(msg[1], msg[2]);
        }
        if (msg[0].equals("INVITACIONACEPTADA")) {
            InvitacionAceptada(msg[1], msg[2]);
        }

        //INICIAR SESION
        if (msg[0].equals("i")) {
            System.out.println("Iniciando sesion entrante");
            Jugador jugador = IniciarSesion.VerificarUsuario(msg[1], msg[2]);

            try {
                if (jugador != null) {
                    System.out.println(jugador.id + jugador.usuario + jugador.contraseña);
                    System.out.println("Iniciando Sesion");
                    out.writeUTF("i:" + jugador.id + ":" + jugador.usuario + ":" + jugador.contraseña);
                    Server.connectedPlayers.add(new ConnectedPlayers(jugador, playerSocket));
                } else {
                    System.out.println("No se que verga paso");
                    out.writeUTF("i:false");
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //ENVIAR LA LISTA DE JUGADORES
        if (msg[0].equals("p")) {
            for (ConnectedPlayers player : Server.connectedPlayers) {
                try {
                    out.writeUTF("p:" + player.jugador.id + ":" + player.jugador.usuario);
                } catch (IOException ex) {
                    Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //INSERTARJUGADOR
        if (msg[0].equals("r")) {
            jugador.setUsuario(msg[1]);
            jugador.setContraseña(msg[2]);
            db.insertAutoincrementPlayer(jugador);
            System.out.println("Jugador insertado con exito");
        }
        //DESCONECTARJUGADOR
        if (msg[0].equals("x")) {
            DisconnectPlayer(Integer.parseInt(msg[1]));
        }
    }

    /*private void CrearPartida(String id1, String id2) {
        if(!id2.equals("9999")){
            System.out.println("CreandoPartida...");
            Socket socketPlayer1 = null;
            Socket socketPlayer2 = null;

            DataOutputStream socketOutput1, socketOutput2;

            for (int i = 0; i < Server.connectedPlayers.size(); i++) {
                if (Server.connectedPlayers.get(i).jugador.id == (Integer.parseInt(id1))) {
                    socketPlayer1 = Server.connectedPlayers.get(i).playerSocket;
                } else if (Server.connectedPlayers.get(i).jugador.id == (Integer.parseInt(id2))) {
                    socketPlayer2 = Server.connectedPlayers.get(i).playerSocket;
                }
            }

            if (socketPlayer1 != null && socketPlayer2 != null) {
                try{
                    socketOutput2 = new DataOutputStream(socketPlayer2.getOutputStream());
                    socketOutput2.writeUTF("z:"+id1);
                    socketOutput1 = new DataOutputStream(socketPlayer1.getOutputStream());
                    socketOutput1.writeUTF("IN");
                }catch(IOException easd){
                    System.out.println("Error al mandar notificacion al jugador 1 o 2");
                }
            }
        }else{
            Socket socketPlayer1 = null;
            DataOutputStream socketOutput1, socketOutput2;
            System.out.println("Estamos trabajando en la IA :) ");
            try{
            socketOutput1 = new DataOutputStream(playerSocket.getOutputStream());
            socketOutput1.writeUTF("IN");
            System.out.println("Se abrio tablero para la IA");
            }catch(IOException easd){
                    System.out.println("Error Al intentar jugar con la IA");
            }
        }
    }*/
    void DisconnectPlayer(int id) {
        int index = FindPlayer(id);
        if (index == -1) {
            System.out.println("Jugador a eliminar no encontrado");
        } else {
            Server.connectedPlayers.remove(index);
        }
    }

    int FindPlayer(int id) {
        int index = 0;

        for (ConnectedPlayers player : Server.connectedPlayers) {
            if (player.jugador.id == id) {
                return index;
            }
            index++;
        }

        return -1;
    }

    private void InvitacionAceptada(String id1, String id2) {
        GetSocketsAndStreams(id1, id2);

        if (socketPlayer1 != null && socketPlayer2 != null) {
            try {
                socketOutput1.writeUTF("INICIARPARTIDA:1");
                socketOutput2.writeUTF("INICIARPARTIDA:2");
            } catch (IOException easd) {
                System.out.println("Error al mandar notificacion al jugador 1 o 2");
            }
        }

    }

    private void Invitar(String id1, String id2) {
        GetSocketsAndStreams(id1, id2);

        if (socketPlayer1 != null && socketPlayer2 != null) {
            try {
                socketOutput2.writeUTF("TEINVITA:" + id1);
                socketOutput1.writeUTF("INVITACIONENVIADA");
            } catch (IOException ex) {
                Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);

                try {
                    socketOutput2 = new DataOutputStream(socketPlayer2.getOutputStream());
                    socketOutput2.writeUTF("z:" + id1);

                    socketOutput1 = new DataOutputStream(socketPlayer1.getOutputStream());
                    socketOutput1.writeUTF("IN");

                    Thread partida = new Partida(socketPlayer1, socketPlayer2);
                    partida.start();

                } catch (IOException easd) {
                    System.out.println("Error al mandar notificacion al jugador 1 o 2");
                }
            }
        }
    }

    private void GetSocketsAndStreams(String id1, String id2) {
        for (int i = 0; i < Server.connectedPlayers.size(); i++) {
            if (Server.connectedPlayers.get(i).jugador.id == (Integer.parseInt(id1))) {
                socketPlayer1 = Server.connectedPlayers.get(i).playerSocket;
            } else if (Server.connectedPlayers.get(i).jugador.id == (Integer.parseInt(id2))) {
                socketPlayer2 = Server.connectedPlayers.get(i).playerSocket;
            }
        }
        try {
            socketOutput2 = new DataOutputStream(socketPlayer2.getOutputStream());
            socketOutput1 = new DataOutputStream(socketPlayer1.getOutputStream());
        } catch (IOException easd) {
            System.out.println("Error al mandar notificacion al jugador 1 o 2");
        }
    }
}
