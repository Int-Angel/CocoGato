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

/**
 *
 * @author Propietario
 */
public class ServerListener extends Thread{
    
    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    TicTacToeTablero tablero;
    int id;
    
    public ServerListener(Socket socket){
        this.socket = socket;
        try{
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        }catch(IOException e){}
    }
    
    public ServerListener(Socket socket, int id){
        this.socket = socket;
        this.id = id;
        try{
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        }catch(IOException e){}
    }
    
    
    @Override
    public void run(){
        String msg = "";
        while (true) {
            try {
                msg = in.readUTF();
                String[] splitMsg = msg.split(":");
                if (splitMsg[0].equals("N")) {
                    //Crear notificacion de partida
                    AceptarPartida(Integer.parseInt(splitMsg[1]));
                } else if (splitMsg[0].equals("i")) {
                    if (splitMsg[1].equals("false")) {
                        System.out.println("Inicio de Sesion fallido");
                    } else {
                        System.out.println("Inicio de Sesion Exitoso");
                        Jugador.id = Integer.parseInt(splitMsg[1]);
                        Jugador.usuario = splitMsg[2];
                        Jugador.conectado = true;

                        InflatePlayers();
                        CocoGatoClient.RICK();
                    }
                } else if (splitMsg[0].equals("p")) {
                    Jugadores.jugadores = new ArrayList<Jugadores>();
                    Jugadores jugador = new Jugadores(Integer.parseInt(splitMsg[1]), splitMsg[2]);
                    Jugadores.jugadores.add(jugador);
                    System.out.println(jugador.id + ", " + jugador.usuario);
                    
                    if(jugador.id == Jugador.id)
                    {
                       jugador.id = 9999;
                       jugador.usuario = "IA";
                    }
                    TicTacToeTablero.agregarBoton(jugador);

                   // TicTacToeTablero.botonRefrescar();

                    //TicTacToeTablero.agregarBotones(Jugadores.jugadores);

                   // TicTacToeTablero.agregarBotones(Jugadores.jugadores);

                }else if(splitMsg[0].equals("z")){
                    //MOSTRAR NOTIFICACION
                    CocoGatoClient.launchInvitation(splitMsg[1]);

                }
            } catch (IOException e) {
                System.out.println("hahaha");
            }
        }
    }

    private void InflatePlayers() {
        try {
            out.writeUTF("p:0");
        } catch (IOException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    void AceptarPartida(int id2){
        try{
            out.writeUTF("z:"+id+":"+id2);
            TicTacToeTablero tablero = new TicTacToeTablero(false,false);
            tablero.Show();
        }catch(IOException e){
            System.out.println("Error al aceptar la partida");
        }
    }

    
}
