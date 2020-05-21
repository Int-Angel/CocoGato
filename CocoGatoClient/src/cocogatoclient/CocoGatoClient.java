/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author ricar
 */
public class CocoGatoClient {
    
        static String host = "25.93.46.49";
        final static  int puerto = 471;
        static Socket socket;
        
    public static void main(String[] args) {
        try{
            socket = new Socket(host, puerto);
        }catch(UnknownHostException e){}catch(IOException a){}
        
        
        CrearPartida();
    }
    
    static void CrearPartida(){
        TicTacToeTablero tablero = new TicTacToeTablero(socket,true,true);
        tablero.Show();
    }
}
