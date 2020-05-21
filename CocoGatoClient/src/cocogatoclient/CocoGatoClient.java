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
public  class CocoGatoClient {
    
        static String host = "25.93.46.49";
        final static  int puerto = 471;
        public static Socket socket;
        public static DataOutputStream out;
        public static DataInputStream in;
        static int id;
    
        
    public static void main(String[] args) {
     
        try{
            socket = new Socket(host, puerto);   
            
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            
            id = in.readInt();
            System.out.println("Id: "+id);
            
            Thread serverListener = new ServerListener(socket, id);
            serverListener.start();
            
            CrearPartida();
            
            
        }catch(UnknownHostException e){}catch(IOException a){
            System.out.println("Error al conectarse con el servidor...");
        }
        
    }
    
    static void CrearPartida(){
        try{
            out.writeUTF("c:"+id+":"+(id-1));
            TicTacToeTablero tablero = new TicTacToeTablero(socket,true,true);
            Thread listener = new ServerConfirmedPartida(tablero,socket);
            listener.start();
        }catch(IOException e){
            System.out.println("Error al crear partida");
        }
    }
    
    static void AceptarPartida(){
        try{
            out.writeUTF("a:"+id);
            TicTacToeTablero tablero = new TicTacToeTablero(socket,false,false);
            tablero.Show();
        }catch(IOException e){
            System.out.println("Error al aceptar la partida");
        }
    }

}