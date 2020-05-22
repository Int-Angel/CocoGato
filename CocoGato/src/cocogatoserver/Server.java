/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Clase servidor
 * 
 * Clase que se encarga de almacenar la informacion de los jugadores 
 * conectados asi como de instanciar el socket del servidor y  establecer el 
 * puerto a utilizar
 * @author ricar
 */
public class Server {
    public static ArrayList<ConnectedPlayers> connectedPlayers;
    public static ServerSocket server;
    public static final int port = 480;

}

