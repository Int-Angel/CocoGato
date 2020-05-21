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
        /*
        try{
            socket = new Socket(host, puerto);
            DataOutputStream out;
            DataInputStream in;
            int id;
            
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            
            id = in.readInt();
            System.out.println("Id: "+id);
            out.writeUTF("c:"+id+":"+(id-1));

            
        }catch(UnknownHostException e){}catch(IOException a){
            System.out.println("Error al conectarse con el servidor...");
        }
        */
        CrearPartida();
    }
    
    static void CrearPartida(){
        TicTacToeTablero tablero = new TicTacToeTablero(socket,true,true);
        tablero.Show();
    }
}