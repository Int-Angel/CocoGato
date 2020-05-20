/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoserver;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ricar
 */


public class CocoGatoServer {
    static DataInputStream inPlayer1, inPlayer2;
    static DataOutputStream outPlayer1, outPlayer2;
 


    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        DB db = new DB();
        
        ArrayList<Jugador> jugadores = new ArrayList();
        ArrayList<Integer> popo = new ArrayList();
        Jugador jugador = new Jugador();
        
        ArrayList<Partida> partidas = new ArrayList();
        Partida partida = new Partida();
        System.out.println("Server");
        
        int menu;
        do{
            
        System.out.println("***********Menu Jugador************");
        System.out.println("1_CREATE");
        System.out.println("2_READ");
        System.out.println("3_UPDATE");
        System.out.println("4_DELETE");
        System.out.println("5_EXIT");
        System.out.println("***********************************");

        menu = in.nextInt();
            switch (menu) {
                case 1:
                    System.out.println("Ingresa los datos de el nuevo usuario");
                    System.out.println("Id: ");
                    jugador.setId(in.nextInt());
                    System.out.println("Usuario: ");
                    jugador.setUsuario(in.nextLine());
                    jugador.setUsuario(in.nextLine());
                    System.out.println("Contraseña: ");
                    jugador.setContraseña(in.nextLine());
                    if(db.insertPlayer(jugador)){
                        System.out.println("Operacion exitosa");
                    }else{
                        System.out.println("Error en la insercion");
                    }
                    break;
                case 2:
                    
                    jugadores = db.selectPlayers();
                    System.out.println("Jugadores:");

                    for(int i = 0; i < jugadores.size(); i++){
                        jugador = jugadores.get(i);
                        System.out.print("Row " + (i+1) + ": ");
                        System.out.println("id " + jugador.id + " Usuario " + jugador.usuario+ " Contraseña " + jugador.contraseña);
                    }
                    break;
                case 3:
                    /*
                    System.out.println("ID del alumno a modificar");
                    alumno.id = in.nextInt();
                    System.out.println("Nueva edad: ");
                    alumno.edad = in.nextInt()
                    
                    if(db.updateAlumno(alumno)){
                        System.out.println("Operacion exitosa");
                    }else{
                        System.out.println("Error en la actulizacion");
                    }
                    */
                    jugador = db.selectPlayer(1);
                    System.out.println("Jugador 0:");

                    System.out.println("ID " + jugador.id);
                    System.out.println("Usuario " + jugador.usuario);
                    System.out.println("Contraseña " + jugador.contraseña);
                    break;
                case 4:
                    System.out.println("ID del usuario a eliminar");
                    jugador.id = in.nextInt();
                    if(db.deletePlayer(jugador)){
                        System.out.println("Operacion exitosa");
                    }else{
                        System.out.println("Error en la eliminacion");
                    }
                    break;
                case 5:

                    break;
                default:
                    System.out.println("No se eligio una opcion valida");
                    break;
            }
        }while(menu != 5);
        
        do{
            
        System.out.println("**********Menu del juego**********");
        System.out.println("1_CREATE");
        System.out.println("2_READ");
        System.out.println("3_UPDATE");
        System.out.println("4_DELETE");
        System.out.println("5_EXIT");
        System.out.println("***********************************");

        menu = in.nextInt();
            switch (menu) {
                case 1:
                    System.out.println("Ingresa los datos de el nuevo juego");
                    System.out.println("Id: ");
                    partida.setId(in.nextInt());
                    System.out.println("ID Jugador 1: ");
                    partida.setIdJugador1(in.nextInt());
                    System.out.println("ID Jugador 2: ");
                    partida.setIdJugador2(in.nextInt());
                    System.out.println("ID Ganador");
                    partida.setIdGanador(in.nextInt());
                    System.out.println("Estado");
                    partida.setStatus(in.nextLine());
                    if(db.insertPlayer(jugador)){
                        System.out.println("Operacion exitosa");
                    }else{
                        System.out.println("Error en la insercion");
                    }
                    break;
                case 2:
                    
                    partidas = db.selectGames();
                    System.out.println("Juegos:");

                    for(int i = 0; i < partidas.size(); i++){
                        partida = partidas.get(i);
                        System.out.print("Row " + (i+1) + ": ");
                        System.out.println("id " + partida.id + " ID Jugador 1 " + partida.idJugador1+ " ID Jugador 2 " + partida.idJugador2 + " ID Ganador" + partida.idGanador + " Estado " + partida.status);
                    }
                    break;
                case 3:
                    /*
                    System.out.println("ID del alumno a modificar");
                    alumno.id = in.nextInt();
                    System.out.println("Nueva edad: ");
                    alumno.edad = in.nextInt()
                    
                    if(db.updateAlumno(alumno)){
                        System.out.println("Operacion exitosa");
                    }else{
                        System.out.println("Error en la actulizacion");
                    }
                    */
                    partida = db.selectGame(1);
                    System.out.println("Partida 0:");

                    System.out.println("ID " + partida.id);
                    System.out.println("ID Jugador 1 " + partida.idJugador1);
                    System.out.println("ID Jugador 2 " + partida.idJugador2);
                    System.out.println("ID Ganador " + partida.idGanador);
                    System.out.println("Estado " + partida.status);
                    
                    break;
                case 4:
                    System.out.println("ID del juego a eliminar");
                    partida.id = in.nextInt();
                    if(db.deleteGame(partida)){
                        System.out.println("Operacion exitosa");
                    }else{
                        System.out.println("Error en la eliminacion");
                    }
                    break;
                case 5:

                    break;
                default:
                    System.out.println("No se eligio una opcion valida");
                    break;
            }
        }while(menu != 5);
    }   
/*
        ServerSocket server;
        final int port = 471;
        DataInputStream in;
        DataOutputStream out;
        Socket player1 = null, player2 = null;
        
        
        try {
            server = new ServerSocket(port);
            System.out.println("Server iniciado");
     
            while (true) {
                if (player1 == null) {
                    player1 = server.accept();
                    inPlayer1 = new DataInputStream(player1.getInputStream());
                    outPlayer1 = new DataOutputStream(player1.getOutputStream());
                    outPlayer1.writeUTF("Esperando al Jugador 2");
                }
                if (player2 == null) {
                    player2 = server.accept();
                    inPlayer2 = new DataInputStream(player2.getInputStream());
                    outPlayer2 = new DataOutputStream(player2.getOutputStream());
                    outPlayer2.writeUTF("Esperando al Jugador 2");
                }
                
                if(player1 != null && player2 != null)
                    writeToPlayers("Jugador encontrado!");
               // String message = in.readUTF();
               // System.out.println(message);
               /* out.writeUTF("Y yo soy el server, Soy superior a ti xd");
                sc.close();
                System.out.println("Cliente desconectado");
            }
        } catch (IOException ex) {
            Logger.getLogger(CocoGatoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void writeToPlayers(String msg)
    {
        try {
            System.out.println(msg);
            outPlayer1.writeUTF(msg);
            outPlayer2.writeUTF(msg);
        } catch (IOException ex) {
            Logger.getLogger(CocoGatoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
}
