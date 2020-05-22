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
 *
 * @author ricar
 */
public class Server {
    public static ArrayList<ConnectedPlayers> connectedPlayers;
    public static ServerSocket server;
    public static final int port = 472;
}

